package com.tasktrackercli.tasktracker.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.tasktrackercli.tasktracker.model.Task;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Repository for handling JSON file operations
 * Uses Jackson (comes with Spring Boot) for JSON serialization/deserialization
 */
@Component
public class TaskRepository {
    private static final String FILE_PATH = "tasks.json";
    private final ObjectMapper objectMapper;

    public TaskRepository() {
        this.objectMapper = new ObjectMapper();
        // Register JavaTimeModule to handle LocalDateTime
        this.objectMapper.registerModule(new JavaTimeModule());
        // Serialize dates as ISO strings instead of arrays
        this.objectMapper.disable(com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    /**
     * Reads all tasks from the JSON file.
     *
     * Process:
     * 1. Checks if tasks.json exists
     * 2. If not, returns empty list (allowing fresh start)
     * 3. If exists, deserializes JSON array to List<Task>
     * 4. Handles I/O errors gracefully, returning empty list on failure
     *
     * TypeReference is used to preserve generic type information during
     * deserialization, as Java's type erasure would otherwise lose the
     * List<Task> type at runtime.
     *
     * @return List of all tasks from the JSON file, or empty list if file
     *         doesn't exist or an error occurs
     */
    public List<Task> readTasks() {
        File file = new File(FILE_PATH);

        // Create empty list if file doesn't exist (first run scenario)
        if (!file.exists()) {
            return new ArrayList<>();
        }

        try {
            // TypeReference preserves generic type information for deserialization
            List<Task> tasks = objectMapper.readValue(file, new TypeReference<List<Task>>() {});
            // Defensive programming: handle null case (though unlikely with Jackson)
            return tasks != null ? tasks : new ArrayList<>();
        } catch (IOException e) {
            // Log error but don't crash - return empty list to allow recovery
            System.err.println("Error reading tasks from file: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Writes all tasks to the JSON file.
     *
     * Process:
     * 1. Serializes the entire task list to JSON
     * 2. Uses pretty printing for human-readable JSON (indented, formatted)
     * 3. Overwrites existing file content (complete replacement strategy)
     * 4. Creates file if it doesn't exist
     *
     * Error Handling:
     * - Logs error message if write fails
     * - Does not crash the application
     * - Prints to stderr to distinguish from normal output
     *
     * @param tasks List of tasks to persist to the JSON file
     */
    public void writeTasks(List<Task> tasks) {
        try {
            // writerWithDefaultPrettyPrinter() formats JSON with indentation and line breaks
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(FILE_PATH), tasks);
        } catch (IOException e) {
            // Log error but don't crash - allows CLI to continue running
            System.err.println("Error writing tasks to file: " + e.getMessage());
        }
    }

    /**
     * Generates the next available task ID.
     *
     * Algorithm:
     * 1. Reads all existing tasks
     * 2. Finds the maximum ID currently in use
     * 3. Returns max ID + 1 (or 1 if no tasks exist)
     *
     * This ensures:
     * - IDs are always unique
     * - IDs are sequential (in order of creation)
     * - No ID conflicts even after deletions
     *
     * Stream API usage:
     * - mapToInt() extracts ID from each task
     * - max() finds the largest ID
     * - orElse(0) handles empty list case (returns 0, then +1 = 1)
     *
     * @return the next available task ID (starting from 1)
     */
    public int getNextId() {
        List<Task> tasks = readTasks();
        return tasks.stream()
                .mapToInt(Task::getId)  // Extract ID from each task
                .max()                   // Find maximum ID
                .orElse(0) + 1;         // If no tasks, start at 1 (0 + 1)
    }
}

