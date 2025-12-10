# 📋 Task Tracker CLI

> A professional command-line application to track and manage your tasks, built with **Spring Boot** and **Maven**.

[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.0-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Maven](https://img.shields.io/badge/Maven-3.8+-blue.svg)](https://maven.apache.org/)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)
[![roadmap.sh](https://img.shields.io/badge/roadmap.sh-Task%20Tracker-purple.svg)](https://roadmap.sh/projects/task-tracker)

> **📌 Project Source:** This project implements the [Task Tracker](https://roadmap.sh/projects/task-tracker) challenge from [roadmap.sh](https://roadmap.sh) - a comprehensive platform for developer roadmaps and practical project-based learning. **All requirements and specifications are defined in the [original assignment](https://roadmap.sh/projects/task-tracker).**

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

### 📚 Project Origin

This project is based on the **[Task Tracker](https://roadmap.sh/projects/task-tracker)** project from [roadmap.sh](https://roadmap.sh), a fantastic resource for:
- 🎯 Structured learning paths for developers
- 💡 Practical, hands-on projects
- 📖 Backend development best practices
- 🚀 Real-world skill development

**All project requirements, specifications, and acceptance criteria** are defined in the [original roadmap.sh assignment](https://roadmap.sh/projects/task-tracker). This implementation follows those requirements while demonstrating Spring Boot best practices.

### 🎓 What You'll Learn

This project is ideal for:
- ✅ Learning Spring Boot basics
- ✅ Understanding layered architecture
- ✅ Practicing dependency injection
- ✅ Building CLI applications with Spring Boot
- ✅ Working with JSON persistence
- ✅ Implementing CRUD operations
- ✅ Following industry best practices

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

## 📚 Usage

### Quick Start

```bash
# Build the project first
./mvnw clean package

# Now you can use the application
java -jar target/TaskTracker-0.0.1-SNAPSHOT.jar add "Buy groceries"
java -jar target/TaskTracker-0.0.1-SNAPSHOT.jar list
```

### Command Examples

#### Using JAR (Recommended - Faster)
```bash
# Add tasks
java -jar target/TaskTracker-0.0.1-SNAPSHOT.jar add "Buy groceries"
java -jar target/TaskTracker-0.0.1-SNAPSHOT.jar add "Write documentation"

# Update task
java -jar target/TaskTracker-0.0.1-SNAPSHOT.jar update 1 "Buy groceries and cook dinner"

# Mark task status
java -jar target/TaskTracker-0.0.1-SNAPSHOT.jar mark-in-progress 1
java -jar target/TaskTracker-0.0.1-SNAPSHOT.jar mark-done 1

# List tasks
java -jar target/TaskTracker-0.0.1-SNAPSHOT.jar list
java -jar target/TaskTracker-0.0.1-SNAPSHOT.jar list done
java -jar target/TaskTracker-0.0.1-SNAPSHOT.jar list todo
java -jar target/TaskTracker-0.0.1-SNAPSHOT.jar list in-progress

# Delete task
java -jar target/TaskTracker-0.0.1-SNAPSHOT.jar delete 1
```

#### Using Maven (Slower - Good for Development)
```bash
./mvnw spring-boot:run -Dspring-boot.run.arguments="add 'Buy groceries'"
./mvnw spring-boot:run -Dspring-boot.run.arguments="list"
./mvnw spring-boot:run -Dspring-boot.run.arguments="mark-done 1"
```

### Sample Workflow

```bash
# Day 1: Plan your tasks
$ java -jar target/TaskTracker-0.0.1-SNAPSHOT.jar add "Review pull requests"
Task added successfully (ID: 1)

$ java -jar target/TaskTracker-0.0.1-SNAPSHOT.jar add "Update documentation"
Task added successfully (ID: 2)

$ java -jar target/TaskTracker-0.0.1-SNAPSHOT.jar add "Deploy to staging"
Task added successfully (ID: 3)

# Start working
$ java -jar target/TaskTracker-0.0.1-SNAPSHOT.jar mark-in-progress 1
Task 1 marked as in-progress

# Check your progress
$ java -jar target/TaskTracker-0.0.1-SNAPSHOT.jar list

=== Tasks ===
[1] Review pull requests | Status: IN-PROGRESS | Created: 2025-12-10 09:00:00 | Updated: 2025-12-10 09:15:00
[2] Update documentation | Status: TODO | Created: 2025-12-10 09:01:00 | Updated: 2025-12-10 09:01:00
[3] Deploy to staging | Status: TODO | Created: 2025-12-10 09:02:00 | Updated: 2025-12-10 09:02:00

# Complete task
$ java -jar target/TaskTracker-0.0.1-SNAPSHOT.jar mark-done 1
Task 1 marked as done

# View only completed tasks
$ java -jar target/TaskTracker-0.0.1-SNAPSHOT.jar list done

=== Tasks ===
[1] Review pull requests | Status: DONE | Created: 2025-12-10 09:00:00 | Updated: 2025-12-10 10:30:00
```

---

## 💾 Data Storage

### JSON File Structure

Tasks are stored in `tasks.json` in the project root directory:

```json
[
  {
    "id": 1,
    "description": "Buy groceries",
    "status": "todo",
    "createdAt": "2025-12-10T15:30:00.123456789",
    "updatedAt": "2025-12-10T15:30:00.123456789"
  },
  {
    "id": 2,
    "description": "Write documentation",
    "status": "in-progress",
    "createdAt": "2025-12-10T15:31:00.987654321",
    "updatedAt": "2025-12-10T16:45:00.456789123"
  }
]
```

### Task Properties

| Property | Type | Description | Example |
|----------|------|-------------|---------|
| **id** | Integer | Unique identifier (auto-generated) | `1` |
| **description** | String | Task description | `"Buy groceries"` |
| **status** | String | Current status | `"todo"`, `"in-progress"`, `"done"` |
| **createdAt** | LocalDateTime | Creation timestamp (ISO-8601) | `"2025-12-10T15:30:00"` |
| **updatedAt** | LocalDateTime | Last update timestamp (ISO-8601) | `"2025-12-10T16:45:00"` |

### File Management

- ✅ **Auto-creation**: File is created automatically on first task addition
- ✅ **Pretty-printed**: JSON is formatted for readability
- ✅ **Persistent**: Data survives application restarts
- ✅ **Portable**: Copy `tasks.json` to backup or transfer tasks

---

## 🎓 Spring Boot Concepts

This project demonstrates key Spring Boot concepts:

### 1. @SpringBootApplication
```java
@SpringBootApplication
public class TaskTrackerApplication {
    // Entry point - combines @Configuration, @EnableAutoConfiguration, @ComponentScan
}
```

### 2. Component Scanning
```java
@Component   // TaskRepository, TaskCLI
@Service     // TaskService (specialized @Component for business logic)
```

### 3. Dependency Injection
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

### 4. CommandLineRunner
```java
@Component
public class TaskCLI implements CommandLineRunner {
    @Override
    public void run(String... args) {
        // Executed after Spring Boot startup
    }
}
```

### 5. Configuration Management
```properties
# application.properties
logging.level.root=ERROR
logging.level.org.springframework=ERROR
```

---

## 📖 Code Documentation

All classes are **fully documented** with:
- ✅ **JavaDoc comments** - Class-level and method-level documentation
- ✅ **Inline comments** - Explaining complex logic
- ✅ **Best practices** - Following Java and Spring Boot conventions

### Documentation Coverage

| File | Lines | Documentation |
|------|-------|---------------|
| `Task.java` | ~150 | ✅ Complete |
| `TaskRepository.java` | ~120 | ✅ Complete |
| `TaskService.java` | ~180 | ✅ Complete |
| `TaskCLI.java` | ~200 | ✅ Complete |
| `TaskTrackerApplication.java` | ~60 | ✅ Complete |

---

## ⚠️ Error Handling

The application gracefully handles all common error scenarios:

### Validation Errors

```bash
# Empty description
$ java -jar target/TaskTracker-0.0.1-SNAPSHOT.jar add ""
Error: Task description cannot be empty

# Invalid task ID
$ java -jar target/TaskTracker-0.0.1-SNAPSHOT.jar delete 999
Error: Task with ID 999 does not exist

# Non-numeric ID
$ java -jar target/TaskTracker-0.0.1-SNAPSHOT.jar update abc "New task"
Error: Invalid task ID. Please provide a valid number.
```

### Command Errors

```bash
# Unknown command
$ java -jar target/TaskTracker-0.0.1-SNAPSHOT.jar invalid
Unknown command: invalid

Task Tracker CLI - Available Commands:
add "description"              - Add a new task
...

# Missing arguments
$ java -jar target/TaskTracker-0.0.1-SNAPSHOT.jar add
Usage: add "task description"
```

### File I/O Errors
- Handles missing `tasks.json` (creates new file)
- Handles corrupted JSON (returns empty list)
- Handles permission errors (displays error message)

---

## 🧪 Testing

### Run Tests

```bash
# Run all tests
./mvnw test

# Run with coverage
./mvnw clean test jacoco:report
```

### Test Coverage

- ✅ **Context Loading Test** - Verifies Spring Boot configuration
- 📝 **Future**: Add unit tests for TaskService
- 📝 **Future**: Add integration tests for file operations

---

## 🚀 Future Enhancements

Ideas for extending this project:

### Features
- [ ] Task priorities (High, Medium, Low)
- [ ] Due dates and reminders
- [ ] Task categories/tags
- [ ] Search tasks by keyword
- [ ] Task notes/comments
- [ ] Recurring tasks
- [ ] Export to CSV/PDF

### Technical Improvements
- [ ] Add Spring Data JPA for database storage
- [ ] Create REST API with Spring Web
- [ ] Add Spring Security for authentication
- [ ] Implement caching with Spring Cache
- [ ] Add scheduled tasks with @Scheduled
- [ ] Create Docker container
- [ ] Add comprehensive unit tests

### UI Enhancements
- [ ] Colored console output (ANSI colors)
- [ ] Table formatting for task lists
- [ ] Progress bars for task completion
- [ ] Interactive mode (menu-driven)

---

## 🔧 Troubleshooting

### Common Issues

**Problem:** `java: command not found`
```bash
# Solution: Install Java 21
sudo apt install openjdk-21-jdk  # Ubuntu/Debian
brew install openjdk@21          # macOS
```

**Problem:** `mvnw: Permission denied`
```bash
# Solution: Make Maven wrapper executable
chmod +x mvnw
```

**Problem:** `Error: Could not find or load main class`
```bash
# Solution: Rebuild the project
./mvnw clean package
```

**Problem:** Tasks not persisting
```bash
# Check if tasks.json exists and is writable
ls -la tasks.json
chmod 644 tasks.json  # Fix permissions if needed
```

---

## 🤝 Contributing

Contributions are welcome! To contribute:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

### Code Style
- Follow Java naming conventions
- Add JavaDoc comments for all public methods
- Keep methods focused (Single Responsibility Principle)
- Write unit tests for new features

---

## 📄 License

This project is open-source and available under the [MIT License](LICENSE).

---

## 👨‍💻 Author

**Task Tracker CLI** - Built as a learning project for Spring Boot fundamentals.

### Learning Resources
- [Spring Boot Documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/)
- [Spring Framework Reference](https://docs.spring.io/spring-framework/reference/)
- [Maven Getting Started](https://maven.apache.org/guides/getting-started/)
- [Java 21 Documentation](https://docs.oracle.com/en/java/javase/21/)

---

## 🌟 Acknowledgments

- **[roadmap.sh](https://roadmap.sh)** - For the excellent [Task Tracker project](https://roadmap.sh/projects/task-tracker) requirements and providing a structured learning path for backend developers
- **[Spring Boot](https://spring.io/projects/spring-boot)** team - For the amazing framework that makes Java development enjoyable
- **[Jackson](https://github.com/FasterXML/jackson)** library - For robust JSON processing
- **[Maven](https://maven.apache.org/)** - For reliable dependency management and build automation

---

<div align="center">

**⭐ Star this repository if you found it helpful!**

Made with ❤️ using Spring Boot

</div>

