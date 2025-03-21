package com.mycompany.motor.ph;

import com.opencsv.exceptions.CsvValidationException;
import controllers.PayrollController;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Employee;
import services.EmployeeCSVReader;
import services.Worklogs;

/**
 * The MotorPh class is the main entry point for the MotorPh payroll system.
 * It provides a command-line interface for users to process payroll, view work logs,
 * and exit the system.
 */
public class MotorPh {

    // Logger instance using java.util.logging
    private static final Logger logger = Logger.getLogger(MotorPh.class.getName());

    /**
     * The main method runs the MotorPh payroll system.
     * It displays a menu to the user and processes their input to perform actions
     * such as processing payroll, viewing work logs, or exiting the system.
     *
     * @param args Command-line arguments (not used in this application).
     */
    public static void main(String[] args) {
        // File paths for employee and attendance data
        String employeesFile = "src\\main\\java\\assets\\files\\employees.csv";
        String attendanceFile = "src\\main\\java\\assets\\files\\attendance.csv";

        // Scanner for user input
        Scanner scanner = new Scanner(System.in);
        LoginSystem user = new LoginSystem();
        Boolean process = user.login();
        if(process == true) {
            // PayrollController to handle payroll processing
        PayrollController payrollController = new PayrollController();
        EmployeeCSVReader csvReader = new EmployeeCSVReader(employeesFile);
        Worklogs workLogs = new Worklogs();

        // Log system startup
        logger.info("MotorPh system started.");

        // Main loop for the system menu
        while (true) {
            // Display the menu options
            System.out.println("=== MotorPh System ===");
            System.out.println("1. Process Payroll");
            System.out.println("2. View All Employees");
            System.out.println("3. Search Employee by ID");
            System.out.println("4. View Work Logs");
            System.out.println("5. Logout");
            System.out.print("Select an option: ");

            // Read the user's choice
            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine()); // Use nextLine to avoid input issues
            } catch (NumberFormatException e) {
                logger.log(Level.WARNING, "Invalid input. Please enter a number.");
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            // Process the user's choice
            switch (choice) {
                case 1 -> {
                    logger.log(Level.INFO, "User selected: Process Payroll");
                    // Process payroll using the provided employee and attendance files
                    payrollController.processPayroll(employeesFile, attendanceFile);
                }

                case 2 -> {
                    logger.log(Level.INFO, "User selected: View All Employees");
                    try {
                        // Read and display all employees
                        List<Employee> employees = csvReader.readEmployees();
                        csvReader.displayEmployees(employees);
                    } catch (IOException | CsvValidationException e) {
                        logger.log(Level.SEVERE, "Error reading employee data: " + e.getMessage(), e);
                        System.out.println("Error reading employee data. Please check the logs.");
                    }
                }
                case 3 -> {
                    logger.log(Level.INFO, "User selected: Search Employee by ID");
                    try {
                        // Search for an employee by ID
                        List<Employee> employees = csvReader.readEmployees();
                        csvReader.searchEmployeeById(employees);
                    } catch (IOException | CsvValidationException e) {
                        logger.log(Level.SEVERE, "Error searching employee data: " + e.getMessage(), e);
                        System.out.println("Error searching employee data. Please check the logs.");
                    }
                }
                case 4 -> {
                    logger.log(Level.INFO, "User selected: View Work Logs");
                    // Read and display work logs
                    workLogs.readAndDisplayCSV(attendanceFile);
                }

                case 5 -> {
                    // Exit the program
                    logger.log(Level.INFO, "Exiting the system. Goodbye!");
                    scanner.close();
                    return;
                }
                default -> {
                    logger.log(Level.WARNING, "Invalid option selected by user");
                    System.out.println("Invalid option. Please try again.");
                }
            }
        }
        }else {
            logger.log(Level.INFO, "Exiting the system. Goodbye!");
            scanner.close();
            System.exit(0); // Exit the system automatically
        }
    }
}