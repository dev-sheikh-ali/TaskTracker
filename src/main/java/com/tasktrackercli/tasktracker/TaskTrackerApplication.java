package com.tasktrackercli.tasktracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main Spring Boot Application class for Task Tracker CLI.
 *
 * This is the entry point for the entire application. When you run the JAR file
 * or execute via Maven, this main() method is invoked.
 *
 * The @SpringBootApplication annotation is a convenience annotation that combines:
 * 1. @Configuration - Marks this class as a source of bean definitions
 * 2. @EnableAutoConfiguration - Enables Spring Boot's auto-configuration
 * 3. @ComponentScan - Scans for components in this package and sub-packages
 *
 * Component Scanning finds and registers:
 * - TaskCLI (in cli package)
 * - Task (model - though it's a POJO, not a component)
 * - TaskRepository (in repository package)
 * - TaskService (in service package)
 *
 * Application Flow:
 * 1. main() method starts Spring Boot
 * 2. Spring creates application context and discovers components
 * 3. Spring instantiates beans and resolves dependencies
 * 4. TaskCLI.run() is automatically called (implements CommandLineRunner)
 * 5. Command is processed, output is displayed
 * 6. Application exits via System.exit(0)
 *
 * This architecture makes the CLI application behave like a traditional
 * command-line tool while leveraging Spring Boot's powerful features.
 *
 * @author Task Tracker Team
 * @version 1.0
 * @since 2025-12-10
 */
@SpringBootApplication
public class TaskTrackerApplication {

    /**
     * Main entry point for the Task Tracker CLI application.
     *
     * Configuration:
     * - Banner mode is disabled for cleaner CLI output (no Spring Boot ASCII art)
     * - System.exit(0) ensures the application terminates after processing
     *   (prevents Spring Boot from keeping the JVM alive)
     *
     * Command-line arguments are automatically passed to TaskCLI.run()
     * via the CommandLineRunner interface.
     *
     * @param args command-line arguments (e.g., ["add", "Buy groceries"])
     */
    public static void main(String[] args) {
        // Create Spring Boot application instance
        SpringApplication app = new SpringApplication(TaskTrackerApplication.class);

        // Disable banner for clean CLI output
        // Without this, Spring Boot prints ASCII art banner at startup
        app.setBannerMode(org.springframework.boot.Banner.Mode.OFF);

        // Run the application (starts Spring context, executes CommandLineRunners)
        app.run(args);

        // Exit after processing command
        // This ensures the application terminates cleanly instead of waiting
        // for Spring Boot's default shutdown hooks
        System.exit(0);
    }

}
