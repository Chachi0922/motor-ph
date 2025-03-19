package services;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import models.Employee;

import java.io.FileReader;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The EmployeeCSVReader class is responsible for reading employee data from a CSV file
 * and creating Employee objects from that data.
 */
public class EmployeeCSVReader {
    private String filePath;
    private NumberFormat numberFormat;

    // Logger instance using java.util.logging
    private static final Logger logger = Logger.getLogger(EmployeeCSVReader.class.getName());

    /**
     * Constructs a new EmployeeCSVReader with the specified file path.
     *
     * @param filePath The path to the CSV file containing employee data.
     */
    public EmployeeCSVReader(String filePath) {
        this.filePath = filePath;
        this.numberFormat = NumberFormat.getInstance(Locale.US);
    }

    /**
     * Reads the employee data from the CSV file and returns a list of Employee objects.
     *
     * @return A list of Employee objects created from the CSV data.
     * @throws IOException If there is an error reading the file.
     * @throws CsvValidationException If there is an error validating the CSV format.
     */
    public List<Employee> readEmployees() throws IOException, CsvValidationException {
        List<Employee> employees = new ArrayList<>();

        // Log the start of the CSV reading process
        logger.log(Level.INFO, "Reading employee data from CSV file: {0}", filePath);

        try (CSVReader reader = new CSVReaderBuilder(new FileReader(filePath))
                .withSkipLines(1) // Skip header row
                .build()) {

            String[] line;
            while ((line = reader.readNext()) != null) {
                try {
                    // Parse the CSV data and create an Employee object
                    Employee employee = createEmployeeFromCSV(line);
                    employees.add(employee);
                    logger.log(Level.FINE, "Successfully parsed employee: {0}", employee.getFullname());
                } catch (ParseException e) {
                    // Log the error with details
                    logger.log(Level.SEVERE, "Error parsing data for row: {0}", String.join(", ", line));
                    logger.log(Level.SEVERE, "Error message: {0}", e.getMessage());
                    // You can choose to continue or throw the exception here
                }
            }
        } catch (IOException e) {
            // Log the error with details
            logger.log(Level.SEVERE, "Error reading CSV file: {0}", e.getMessage());
            throw e;
        }

        // Log the number of employees successfully read
        logger.log(Level.INFO, "Successfully read {0} employees from CSV file.", employees.size());
        return employees;
    }

    /**
     * Creates an Employee object from a CSV line.
     *
     * @param data The array of strings representing a line from the CSV file.
     * @return An Employee object populated with data from the CSV line.
     * @throws ParseException If there is an error parsing numeric values.
     */
    private Employee createEmployeeFromCSV(String[] data) throws ParseException {
        // Assuming the CSV columns are in the same order as the Employee constructor parameters
        // Adjust the indices based on your actual CSV structure
        String employeeNumber = data[0];
        String lastName = data[1];
        String firstName = data[2];
        String birthday = data[3];
        String address = data[4];
        String phoneNumber = data[5];
        String sssNumber = data[6];
        String philhealthNumber = data[7];
        String tinNumber = data[8];
        String pagibigNumber = data[9];
        String status = data[10];
        String position = data[11];
        String immediateSupervisor = data[12];

        // Parse numeric values using NumberFormat to handle commas
        double basicSalary = parseDoubleWithCommas(data[13]);
        double riceSubsidy = parseDoubleWithCommas(data[14]);
        double phoneAllowance = parseDoubleWithCommas(data[15]);
        double clothingAllowance = parseDoubleWithCommas(data[16]);
        double grossSemiMonthlyRate = parseDoubleWithCommas(data[17]);
        double hourlyRate = parseDoubleWithCommas(data[18]);

        return new Employee(
                employeeNumber, lastName, firstName, birthday, address,
                phoneNumber, sssNumber, philhealthNumber, tinNumber,
                pagibigNumber, status, position, immediateSupervisor,
                basicSalary, riceSubsidy, phoneAllowance, clothingAllowance,
                grossSemiMonthlyRate, hourlyRate
        );
    }

    /**
     * Parses a string that may contain commas as a double.
     *
     * @param value The string value to parse.
     * @return The parsed double value.
     * @throws ParseException If the string cannot be parsed as a number.
     */
    private double parseDoubleWithCommas(String value) throws ParseException {
        if (value == null || value.trim().isEmpty()) {
            return 0.0;
        }

        // Remove any non-numeric characters except for decimal points and commas
        String cleanedValue = value.trim().replaceAll("[^0-9.,]", "");

        // Parse the value with NumberFormat which handles commas
        return numberFormat.parse(cleanedValue).doubleValue();
    }

    /**
     * Displays the employee data in a formatted table.
     *
     * @param employees The list of employees to display.
     */
    public void displayEmployees(List<Employee> employees) {
        // Log the start of the display process
        logger.log(Level.INFO, "Displaying employee data for {0} employees.", employees.size());

        System.out.println("======================= EMPLOYEE DATA =======================");
        System.out.printf("%-10s %-20s %-15s %-15s %-15s%n",
                "EMP #", "NAME", "BIRTHDAY", "BASIC SALARY", "HOURLY RATE");
        System.out.println("============================================================");

        for (Employee employee : employees) {
            System.out.printf("%-10s %-20s %-15s ₱%-14.2f ₱%-14.2f%n",
                    employee.getEmployeeNumber(),
                    employee.getFullname(),
                    employee.getBirthday(),
                    employee.getBasicSalary(),
                    employee.getHourlyRate());
        }
        System.out.println("============================================================");
    }

    /**
     * Finds an employee by their employee number.
     *
     * @param employees The list of employees to search.
     * @param employeeNumber The employee number to search for.
     * @return Optional containing the employee if found, empty otherwise.
     */
    public Optional<Employee> findEmployeeById(List<Employee> employees, String employeeNumber) {
        // Log the search operation
        logger.log(Level.INFO, "Searching for employee with ID: {0}", employeeNumber);

        return employees.stream()
                .filter(employee -> employee.getEmployeeNumber().equals(employeeNumber))
                .findFirst();
    }

    /**
     * Displays detailed information for a specific employee.
     *
     * @param employee The employee to display information for.
     */
    public void displayEmployeeDetails(Employee employee) {
        if (employee == null) {
            logger.log(Level.WARNING, "No employee data available.");
            System.out.println("No employee data available.");
            return;
        }

        // Log the display of employee details
        logger.log(Level.INFO, "Displaying details for employee: {0}", employee.getFullname());

        System.out.println("\n================= EMPLOYEE DETAILED INFO =================");
        System.out.println("Employee Number: " + employee.getEmployeeNumber());
        System.out.println("Name: " + employee.getFullname());
        System.out.println("Birthday: " + employee.getBirthday());
        System.out.println("Address: " + employee.getAddress());
        System.out.println("Phone Number: " + employee.getPhoneNumber());
        System.out.println("SSS Number: " + employee.getSssNumber());
        System.out.println("PhilHealth Number: " + employee.getPhilhealthNumber());
        System.out.println("TIN Number: " + employee.getTinNumber());

        // Add additional information if available
        try {
            String position = (String) employee.getClass().getMethod("getPosition").invoke(employee);
            System.out.println("Position: " + position);
        } catch (Exception e) {
            // Position getter not available
            logger.log(Level.FINE, "Position getter not available for employee: {0}", employee.getFullname());
        }

        System.out.println("\nSalary Information:");
        System.out.printf("Basic Salary: ₱%.2f\n", employee.getBasicSalary());
        System.out.printf("Hourly Rate: ₱%.2f\n", employee.getHourlyRate());
        System.out.println("===========================================================");
    }

    /**
     * Interactive method that allows users to search for employees by ID.
     *
     * @param employees The list of employees to search through.
     */
    public void searchEmployeeById(List<Employee> employees) {
        Scanner scanner = new Scanner(System.in);
        boolean continueSearch = true;

        // Log the start of the interactive search
        logger.log(Level.INFO, "Starting interactive employee search.");

        while (continueSearch) {
            System.out.print("\nEnter employee ID to search (or 'exit' to quit): ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("exit")) {
                continueSearch = false;
                logger.log(Level.INFO, "User exited interactive search.");
                continue;
            }

            Optional<Employee> foundEmployee = findEmployeeById(employees, input);

            if (foundEmployee.isPresent()) {
                displayEmployeeDetails(foundEmployee.get());
            } else {
                logger.log(Level.WARNING, "No employee found with ID: {0}", input);
                System.out.println("No employee found with ID: " + input);
            }
        }
    }
}