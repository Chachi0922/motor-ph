package com.mycompany.motor.ph;

import com.opencsv.exceptions.CsvValidationException;
import controllers.PayrollController;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import models.Employee;
import models.EmployeeDisplay;
import services.EmployeeCSVReader;
import services.Worklogs;

/**
 * The MotorPh class is the main entry point for the MotorPh payroll system.
 * It provides a command-line interface for users to process payroll, view work logs,
 * and exit the system.
 */
public class MotorPh {

    /**
     * The main method runs the MotorPh payroll system.
     * It displays a menu to the user and processes their input to perform actions
     * such as processing payroll, viewing work logs, or exiting the system.
     *
     * @param args Command-line arguments (not used in this application).
     */
    public static void main(String[] args) {
        // File paths for employee and attendance data
        String employeesFile = "C:\\Users\\reyjh\\OneDrive\\Desktop\\employees.csv";
        String attendanceFile = "C:\\Users\\reyjh\\OneDrive\\Desktop\\attendance.csv";

        // Scanner for user input
        Scanner scanner = new Scanner(System.in);

        // PayrollController to handle payroll processing
        PayrollController payrollController = new PayrollController();
        EmployeeCSVReader csvReader = new EmployeeCSVReader(employeesFile);
        Worklogs workLogs = new Worklogs();

        // Main loop for the system menu
        while (true) {
            // Display the menu options
            System.out.println("=== MotorPh System ===");
            System.out.println("1. Process Payroll");
            System.out.println("2. All Employees");
            System.out.println("3. Search Employee");
            System.out.println("4. Work Logs");
            System.out.println("5. Exit");
            System.out.print("Select an option: ");

            // Read the user's choice
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            // Process the user's choice
            switch (choice) {
                case 1 -> {
                    // Process payroll using the provided employee and attendance files
                    payrollController.processPayroll(employeesFile, attendanceFile);
                }
                case 2 -> {
                    try {
                         List<Employee> employees = csvReader.readEmployees();
                         csvReader.displayEmployees(employees);
          
                    } catch (IOException | CsvValidationException e) {
                         System.err.println("Error reading employee data: " + e.getMessage());
                         e.printStackTrace();
                   }             
                }
                case 3 -> {
                     try {
                         List<Employee> employees = csvReader.readEmployees();
                         csvReader.searchEmployeeById(employees);
          
                    } catch (IOException | CsvValidationException e) {
                         System.err.println("Error reading employee data: " + e.getMessage());
                         e.printStackTrace();
                   }             
                }
                case 4 -> {
                   
                    workLogs.readAndDisplayCSV(attendanceFile);
                }

                case 5 -> {
                    // Exit the program
                    System.out.println("Exiting the system. Goodbye!");
                    scanner.close();
                    return;
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }
}