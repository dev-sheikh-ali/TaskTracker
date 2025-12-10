package com.tasktrackercli.tasktracker.cli;

import com.tasktrackercli.tasktracker.model.Task;
import com.tasktrackercli.tasktracker.service.TaskService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * CLI handler that processes command-line arguments
 * Implements CommandLineRunner to execute after Spring Boot starts
 */
@Component
public class TaskCLI implements CommandLineRunner {
    private final TaskService taskService;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public TaskCLI(TaskService taskService) {
        this.taskService = taskService;
    }

    /**
     * Main entry point for command-line argument processing.
     *
     * This method is automatically called by Spring Boot after the application
     * context is fully initialized. It receives all command-line arguments
     * passed to the application.
     *
     * Command Format:
     * java -jar TaskTracker.jar <command> [arguments...]
     *
     * Supported Commands:
     * - add "description"
     * - update <id> "description"
     * - delete <id>
     * - mark-in-progress <id>
     * - mark-done <id>
     * - list
     * - list <status>
     *
     * Error Handling Strategy:
     * - IllegalArgumentException: Business logic errors (e.g., invalid ID)
     * - Exception: Unexpected errors (catch-all for robustness)
     * - Invalid commands: Shows help message
     *
     * @param args command-line arguments passed to the application
     * @throws Exception if an unrecoverable error occurs (rare)
     */
    @Override
    public void run(String... args) throws Exception {
        // Handle case where no command is provided
        if (args.length == 0) {
            printHelp();
            return;
        }

        // Extract and normalize command (case-insensitive)
        String command = args[0].toLowerCase();

        try {
            // Route to appropriate handler using modern switch expression (Java 14+)
            switch (command) {
                case "add" -> handleAdd(args);
                case "update" -> handleUpdate(args);
                case "delete" -> handleDelete(args);
                case "mark-in-progress" -> handleMarkInProgress(args);
                case "mark-done" -> handleMarkDone(args);
                case "list" -> handleList(args);
                default -> {
                    // Unknown command: inform user and show help
                    System.out.println("Unknown command: " + command);
                    printHelp();
                }
            }
        } catch (IllegalArgumentException e) {
            // Business logic errors (e.g., task not found, invalid input)
            // Print to stderr to distinguish from normal output
            System.err.println("Error: " + e.getMessage());
        } catch (Exception e) {
            // Unexpected errors: provide generic message
            System.err.println("An unexpected error occurred: " + e.getMessage());
        }
    }

    private void handleAdd(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: add \"task description\"");
            return;
        }

        String description = args[1];
        Task task = taskService.addTask(description);
        System.out.println("Task added successfully (ID: " + task.getId() + ")");
    }

    private void handleUpdate(String[] args) {
        if (args.length < 3) {
            System.out.println("Usage: update <id> \"new description\"");
            return;
        }

        try {
            int id = Integer.parseInt(args[1]);
            String description = args[2];
            taskService.updateTask(id, description);
            System.out.println("Task " + id + " updated successfully");
        } catch (NumberFormatException e) {
            System.err.println("Invalid task ID. Please provide a valid number.");
        }
    }

    private void handleDelete(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: delete <id>");
            return;
        }

        try {
            int id = Integer.parseInt(args[1]);
            taskService.deleteTask(id);
            System.out.println("Task " + id + " deleted successfully");
        } catch (NumberFormatException e) {
            System.err.println("Invalid task ID. Please provide a valid number.");
        }
    }

    private void handleMarkInProgress(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: mark-in-progress <id>");
            return;
        }

        try {
            int id = Integer.parseInt(args[1]);
            taskService.markInProgress(id);
            System.out.println("Task " + id + " marked as in-progress");
        } catch (NumberFormatException e) {
            System.err.println("Invalid task ID. Please provide a valid number.");
        }
    }

    private void handleMarkDone(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: mark-done <id>");
            return;
        }

        try {
            int id = Integer.parseInt(args[1]);
            taskService.markDone(id);
            System.out.println("Task " + id + " marked as done");
        } catch (NumberFormatException e) {
            System.err.println("Invalid task ID. Please provide a valid number.");
        }
    }

    private void handleList(String[] args) {
        List<Task> tasks;

        if (args.length == 1) {
            // List all tasks
            tasks = taskService.listAllTasks();
        } else {
            // List tasks by status
            String status = args[1].toLowerCase();
            tasks = taskService.listTasksByStatus(status);
        }

        if (tasks.isEmpty()) {
            System.out.println("No tasks found.");
            return;
        }

        System.out.println("\n=== Tasks ===");
        for (Task task : tasks) {
            System.out.println(formatTask(task));
        }
        System.out.println();
    }

    // ==================== Utility Methods ====================

    /**
     * Formats a single task for display.
     *
     * Format: [ID] Description | Status: STATUS | Created: datetime | Updated: datetime
     * Example: [1] Buy groceries | Status: TODO | Created: 2025-12-10 15:30:00 | Updated: 2025-12-10 15:30:00
     *
     * Uses String.format() for clean, aligned output.
     * Status is displayed in uppercase for better visibility.
     * Timestamps are formatted using DATE_FORMATTER pattern.
     *
     * @param task the task to format
     * @return formatted string representation of the task
     */
    private String formatTask(Task task) {
        return String.format("[%d] %s | Status: %s | Created: %s | Updated: %s",
                task.getId(),
                task.getDescription(),
                task.getStatus().toUpperCase(),  // Uppercase for visual emphasis
                task.getCreatedAt().format(DATE_FORMATTER),
                task.getUpdatedAt().format(DATE_FORMATTER));
    }

    /**
     * Displays help message showing all available commands and their usage.
     *
     * Called when:
     * - No command is provided
     * - An unknown/invalid command is entered
     *
     * Uses text block (Java 15+) for clean multi-line string formatting.
     * The """ delimiter makes the help text easy to read and maintain.
     */
    private void printHelp() {
        System.out.println("""
                
                Task Tracker CLI - Available Commands:
                
                add "description"              - Add a new task
                update <id> "description"      - Update a task's description
                delete <id>                    - Delete a task
                mark-in-progress <id>          - Mark a task as in progress
                mark-done <id>                 - Mark a task as done
                list                           - List all tasks
                list done                      - List all done tasks
                list todo                      - List all todo tasks
                list in-progress               - List all in-progress tasks
                
                """);
    }
}

