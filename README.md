# 📋 Task Tracker CLI

> A professional command-line application to track and manage your tasks, built with **Spring Boot** and **Maven**.

[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.0-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Maven](https://img.shields.io/badge/Maven-3.8+-blue.svg)](https://maven.apache.org/)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

---

## 📖 Table of Contents

- [Overview](#-overview)
- [Features](#-features)
- [Project Structure](#-project-structure)
- [Technology Stack](#-technology-stack)
- [Architecture & Design Patterns](#-architecture--design-patterns)
- [Getting Started](#-getting-started)
  - [Prerequisites](#prerequisites)
  - [Installation](#installation)
  - [Building](#building)
- [Usage](#-usage)
  - [Quick Start Examples](#quick-start-examples)
  - [Command Reference](#command-reference)
- [Data Storage](#-data-storage)
- [Spring Boot Concepts](#-spring-boot-concepts)
- [Code Documentation](#-code-documentation)
- [Error Handling](#-error-handling)
- [Testing](#-testing)
- [Future Enhancements](#-future-enhancements)
- [Troubleshooting](#-troubleshooting)
- [Contributing](#-contributing)
- [License](#-license)

---

## 🎯 Overview

Task Tracker is a **command-line interface (CLI) application** designed to help you manage your daily tasks efficiently. Built as a learning project for Spring Boot fundamentals, it demonstrates best practices in:

- **Layered architecture** (Model-Repository-Service-CLI)
- **Dependency injection** (constructor-based)
- **File-based persistence** (JSON)
- **Modern Java features** (Java 21, Streams, Records-like patterns)
- **Error handling** (graceful degradation)

This project is ideal for:
- ✅ Learning Spring Boot basics
- ✅ Understanding layered architecture
- ✅ Practicing dependency injection
- ✅ Building CLI applications with Spring Boot
- ✅ Working with JSON persistence

---

## ✨ Features

### Core Functionality
- ✅ **Add tasks** - Create new tasks with automatic ID generation
- ✅ **Update tasks** - Modify task descriptions
- ✅ **Delete tasks** - Remove tasks permanently
- ✅ **Mark as in-progress** - Track tasks you're currently working on
- ✅ **Mark as done** - Complete tasks
- ✅ **List all tasks** - View your entire task list
- ✅ **Filter by status** - View tasks by status (todo, in-progress, done)

### Technical Features
- 🔹 **Persistent storage** - Tasks saved to JSON file
- 🔹 **Auto-incrementing IDs** - Unique task identifiers
- 🔹 **Timestamps** - Automatic creation and update tracking
- 🔹 **Input validation** - Prevents invalid data
- 🔹 **Error handling** - Graceful error messages
- 🔹 **Clean output** - No unnecessary logs or clutter

---

## 🏗️ Project Structure

```
TaskTracker/
├── src/
│   ├── main/
│   │   ├── java/com/tasktrackercli/tasktracker/
│   │   │   ├── cli/
│   │   │   │   └── TaskCLI.java              # CLI handler (CommandLineRunner)
│   │   │   ├── model/
│   │   │   │   └── Task.java                 # Task entity (POJO)
│   │   │   ├── repository/
│   │   │   │   └── TaskRepository.java       # Data access layer (JSON I/O)
│   │   │   ├── service/
│   │   │   │   └── TaskService.java          # Business logic layer
│   │   │   └── TaskTrackerApplication.java   # Main Spring Boot app
│   │   └── resources/
│   │       └── application.properties        # Spring Boot configuration
│   └── test/
│       └── java/com/tasktrackercli/tasktracker/
│           └── TaskTrackerApplicationTests.java  # Integration tests
├── tasks.json                                 # Auto-generated task storage
├── pom.xml                                    # Maven configuration
└── README.md                                  # This file
```

### Layer Responsibilities

| Layer | Class | Purpose |
|-------|-------|---------|
| **Model** | `Task.java` | Data structure representing a task |
| **Repository** | `TaskRepository.java` | Handles JSON file read/write operations |
| **Service** | `TaskService.java` | Contains business logic and validation |
| **Presentation** | `TaskCLI.java` | Processes CLI commands and displays output |
| **Application** | `TaskTrackerApplication.java` | Spring Boot entry point |

---

## 🛠️ Technology Stack

### Core Technologies
| Technology | Version | Purpose |
|------------|---------|---------|
| **Java** | 21 | Programming language |
| **Spring Boot** | 4.0.0 | Application framework |
| **Maven** | 3.8+ | Build and dependency management |
| **Jackson** | 2.17+ (with Spring Boot) | JSON serialization |

### Key Dependencies
```xml
<!-- Spring Boot Starter - Core Spring Boot features -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter</artifactId>
</dependency>

<!-- Jackson - JSON processing (included with Spring Boot) -->
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
</dependency>

<!-- Jackson JSR310 - Java 8 date/time support -->
<dependency>
    <groupId>com.fasterxml.jackson.datatype</groupId>
    <artifactId>jackson-datatype-jsr310</artifactId>
</dependency>
```

### Java Features Used
- ☕ **Java 21** - Modern LTS version
- 🔹 **Records** - Data carrier classes
- 🔹 **Switch Expressions** - Pattern matching
- 🔹 **Text Blocks** - Multi-line strings
- 🔹 **Stream API** - Functional programming
- 🔹 **Optional** - Null-safe programming
- 🔹 **LocalDateTime** - Modern date/time API

---

## 🏛️ Architecture & Design Patterns

### 1. Layered Architecture
```
┌─────────────────────────────────────┐
│   CLI Layer (TaskCLI)               │  ← User interaction
├─────────────────────────────────────┤
│   Service Layer (TaskService)       │  ← Business logic
├─────────────────────────────────────┤
│   Repository Layer (TaskRepository) │  ← Data access
├─────────────────────────────────────┤
│   Model Layer (Task)                │  ← Data structure
└─────────────────────────────────────┘
```

### 2. Design Patterns Used

#### Dependency Injection
```java
@Service
public class TaskService {
    private final TaskRepository repository;
    
    // Constructor injection - Spring Boot best practice
    public TaskService(TaskRepository repository) {
        this.repository = repository;
    }
}
```

#### Repository Pattern
```java
@Component
public class TaskRepository {
    // Abstracts data access from business logic
    public List<Task> readTasks() { /* ... */ }
    public void writeTasks(List<Task> tasks) { /* ... */ }
}
```

#### Command Pattern
```java
// Each CLI command maps to a handler method
switch (command) {
    case "add" -> handleAdd(args);
    case "update" -> handleUpdate(args);
    // ...
}
```

### 3. SOLID Principles Applied

| Principle | Implementation |
|-----------|----------------|
| **Single Responsibility** | Each class has one reason to change |
| **Open/Closed** | Easy to add new commands without modifying existing code |
| **Liskov Substitution** | Interfaces could be added without breaking existing code |
| **Interface Segregation** | Classes depend only on what they use |
| **Dependency Inversion** | High-level modules depend on abstractions (DI) |

---

## 🚀 Getting Started

### Prerequisites

Before running this application, ensure you have the following installed:

| Requirement | Version | Check Command |
|-------------|---------|---------------|
| **Java JDK** | 21+ | `java -version` |
| **Maven** | 3.8+ | `mvn -version` |
| **Git** (optional) | Any | `git --version` |

#### Installing Java 21

**Ubuntu/Debian:**
```bash
sudo apt update
sudo apt install openjdk-21-jdk
```

**macOS (Homebrew):**
```bash
brew install openjdk@21
```

**Windows:**
Download from [Oracle](https://www.oracle.com/java/technologies/downloads/) or [Adoptium](https://adoptium.net/)

### Installation

#### Option 1: Clone from Git (if hosted)
```bash
git clone https://github.com/yourusername/TaskTracker.git
cd TaskTracker
```

#### Option 2: Download and Extract
```bash
# Extract the project files to a directory
cd TaskTracker
```

### Building

#### Build with Maven
```bash
# Clean previous builds and create new executable JAR
./mvnw clean package
```

**What this does:**
- Cleans the `target/` directory
- Compiles Java source files
- Runs tests
- Packages into executable JAR: `target/TaskTracker-0.0.1-SNAPSHOT.jar`

#### Skip Tests (Faster Build)
```bash
./mvnw clean package -DskipTests
```

#### Verify Build
```bash
ls -lh target/*.jar
# Should show: TaskTracker-0.0.1-SNAPSHOT.jar
```

---

### Using Maven:

```bash
# Add a task
./mvnw spring-boot:run -Dspring-boot.run.arguments="add 'Buy groceries'"

# Update a task
./mvnw spring-boot:run -Dspring-boot.run.arguments="update 1 'Buy groceries and cook dinner'"

# Mark as in-progress
./mvnw spring-boot:run -Dspring-boot.run.arguments="mark-in-progress 1"

# Mark as done
./mvnw spring-boot:run -Dspring-boot.run.arguments="mark-done 1"

# List all tasks
./mvnw spring-boot:run -Dspring-boot.run.arguments="list"

# List tasks by status
./mvnw spring-boot:run -Dspring-boot.run.arguments="list done"
./mvnw spring-boot:run -Dspring-boot.run.arguments="list todo"
./mvnw spring-boot:run -Dspring-boot.run.arguments="list in-progress"

# Delete a task
./mvnw spring-boot:run -Dspring-boot.run.arguments="delete 1"
```

### Using the JAR directly (after building):

```bash
java -jar target/TaskTracker-0.0.1-SNAPSHOT.jar add "Buy groceries"
java -jar target/TaskTracker-0.0.1-SNAPSHOT.jar update 1 "New description"
java -jar target/TaskTracker-0.0.1-SNAPSHOT.jar mark-done 1
java -jar target/TaskTracker-0.0.1-SNAPSHOT.jar list
java -jar target/TaskTracker-0.0.1-SNAPSHOT.jar list done
java -jar target/TaskTracker-0.0.1-SNAPSHOT.jar delete 1
```

## Task Properties

Each task contains:

- **id**: Unique identifier (auto-generated)
- **description**: Task description
- **status**: One of `todo`, `in-progress`, or `done`
- **createdAt**: Timestamp when task was created
- **updatedAt**: Timestamp when task was last modified

## Example Usage

```bash
# Add some tasks
./mvnw spring-boot:run -Dspring-boot.run.arguments="add 'Buy groceries'"
# Output: Task added successfully (ID: 1)

./mvnw spring-boot:run -Dspring-boot.run.arguments="add 'Write documentation'"
# Output: Task added successfully (ID: 2)

# Mark first task as in progress
./mvnw spring-boot:run -Dspring-boot.run.arguments="mark-in-progress 1"
# Output: Task 1 marked as in-progress

# List all tasks
./mvnw spring-boot:run -Dspring-boot.run.arguments="list"
# Output:
# === Tasks ===
# [1] Buy groceries | Status: IN-PROGRESS | Created: 2025-12-10 10:30:00 | Updated: 2025-12-10 10:31:00
# [2] Write documentation | Status: TODO | Created: 2025-12-10 10:30:15 | Updated: 2025-12-10 10:30:15
```

## Error Handling

The application handles:
- Invalid task IDs
- Empty descriptions
- Non-existent tasks
- Invalid commands
- File I/O errors

## Learning Points (Spring Boot)

This project demonstrates:

1. **Layered Architecture**: Model → Repository → Service → CLI
2. **Dependency Injection**: All components use constructor injection
3. **Separation of Concerns**: Each layer has a specific responsibility
4. **Spring Annotations**: @Component, @Service, @SpringBootApplication
5. **CommandLineRunner**: Processing arguments in a Spring Boot context

## Next Steps (Optional Enhancements)

- Add task priorities
- Add due dates
- Search tasks by keyword
- Export tasks to different formats
- Add task categories/tags

## Author

Built as a learning project for Spring Boot fundamentals.

