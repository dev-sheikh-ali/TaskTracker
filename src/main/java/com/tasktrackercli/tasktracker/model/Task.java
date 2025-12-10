package com.tasktrackercli.tasktracker.model;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Task model representing a single task with all required properties
 */
public class Task {
    private int id;
    private String description;
    private String status; // "todo", "in-progress", "done"
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Default constructor for JSON deserialization
    public Task() {
    }

    // Constructor for creating new tasks
    public Task(int id, String description) {
        this.id = id;
        this.description = description;
        this.status = "todo";
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        this.updatedAt = LocalDateTime.now();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
        this.updatedAt = LocalDateTime.now();
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    // ==================== Object Methods ====================

    /**
     * Compares this task to another object for equality.
     * Two tasks are considered equal if they have the same ID.
     *
     * This follows the principle that ID is the primary key and uniquely
     * identifies a task, regardless of other field values.
     *
     * @param o the object to compare with
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id;
    }

    /**
     * Returns a hash code value for this task.
     * Hash code is based solely on the task ID, consistent with equals().
     *
     * This is important for using Task objects in hash-based collections
     * like HashSet or HashMap.
     *
     * @return hash code value for this task
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /**
     * Returns a string representation of this task.
     * Useful for debugging and logging purposes.
     *
     * Format: Task{id=1, description='...', status='...', createdAt=..., updatedAt=...}
     *
     * @return string representation of this task
     */
    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}

