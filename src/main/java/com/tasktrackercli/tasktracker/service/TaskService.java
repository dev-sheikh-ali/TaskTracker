package com.tasktrackercli.tasktracker.service;

import com.tasktrackercli.tasktracker.model.Task;
import com.tasktrackercli.tasktracker.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service layer for task business logic
 * Uses dependency injection to get TaskRepository
 */
@Service
public class TaskService {
    private final TaskRepository taskRepository;

    // Constructor injection (Spring Boot best practice)
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    /**
     * Add a new task
     */
    public Task addTask(String description) {
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("Task description cannot be empty");
        }

        int newId = taskRepository.getNextId();
        Task task = new Task(newId, description.trim());

        List<Task> tasks = taskRepository.readTasks();
        tasks.add(task);
        taskRepository.writeTasks(tasks);

        return task;
    }

    /**
     * Update an existing task's description
     */
    public void updateTask(int id, String newDescription) {
        if (newDescription == null || newDescription.trim().isEmpty()) {
            throw new IllegalArgumentException("Task description cannot be empty");
        }

        List<Task> tasks = taskRepository.readTasks();
        Optional<Task> taskOpt = tasks.stream()
                .filter(t -> t.getId() == id)
                .findFirst();

        if (taskOpt.isEmpty()) {
            throw new IllegalArgumentException("Task with ID " + id + " does not exist");
        }

        Task task = taskOpt.get();
        task.setDescription(newDescription.trim());
        taskRepository.writeTasks(tasks);
    }

    /**
     * Delete a task by ID
     */
    public void deleteTask(int id) {
        List<Task> tasks = taskRepository.readTasks();
        boolean removed = tasks.removeIf(t -> t.getId() == id);

        if (!removed) {
            throw new IllegalArgumentException("Task with ID " + id + " does not exist");
        }

        taskRepository.writeTasks(tasks);
    }

    /**
     * Mark a task as in-progress
     */
    public void markInProgress(int id) {
        updateTaskStatus(id, "in-progress");
    }

    /**
     * Mark a task as done
     */
    public void markDone(int id) {
        updateTaskStatus(id, "done");
    }

    /**
     * Helper method to update task status
     */
    private void updateTaskStatus(int id, String status) {
        List<Task> tasks = taskRepository.readTasks();
        Optional<Task> taskOpt = tasks.stream()
                .filter(t -> t.getId() == id)
                .findFirst();

        if (taskOpt.isEmpty()) {
            throw new IllegalArgumentException("Task with ID " + id + " does not exist");
        }

        Task task = taskOpt.get();
        task.setStatus(status);
        taskRepository.writeTasks(tasks);
    }

    // ==================== Query Operations ====================

    /**
     * Retrieves all tasks from the system.
     *
     * This is a simple read-only operation that delegates directly
     * to the repository layer.
     *
     * @return List of all tasks (may be empty if no tasks exist)
     */
    public List<Task> listAllTasks() {
        return taskRepository.readTasks();
    }

    /**
     * Retrieves tasks filtered by status.
     *
     * Uses Stream API for functional-style filtering:
     * - filter() applies the status matching predicate
     * - collect() converts stream back to List
     *
     * Status matching is case-insensitive to improve user experience
     * (e.g., "TODO", "todo", "Todo" all match "todo" status).
     *
     * @param status the status to filter by ("todo", "in-progress", or "done")
     * @return List of tasks matching the specified status (may be empty)
     */
    public List<Task> listTasksByStatus(String status) {
        return taskRepository.readTasks().stream()
                .filter(t -> t.getStatus().equalsIgnoreCase(status))
                .collect(Collectors.toList());
    }
}

