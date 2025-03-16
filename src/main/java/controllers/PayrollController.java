package controllers;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import models.Employee;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import services.PhilHealthCalculator;
import services.SSSContributionCalculator;
import services.PagIbigContributionCalculator;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import services.WithholdingTaxCalculator;

/**
 * The PayrollController class handles the processing of payroll data.
 * It reads employee and attendance data from CSV or Excel files, calculates worked hours,
 * overtime, and deductions (SSS, PhilHealth, Pag-IBIG), and generates a payroll receipt.
 */
public class PayrollController {

    private static final LocalTime REQUIRED_LOGIN_TIME = LocalTime.of(8,11); // 8:11 AM
    private static final LocalTime REQUIRED_LOGOUT_TIME = LocalTime.of(17, 0); // 7:00 PM
    private static final Duration LUNCH_BREAK_DURATION = Duration.ofHours(1); // 1-hour lunch break

    /**
     * Calculates the number of hours worked based on login and logout times, accounting for lunch break.
     *
     * @param logIn  The login time in "H:mm" format.
     * @param logOut The logout time in "H:mm" format.
     * @return The number of hours worked as a double.
     */
    private static double calculateWorkedHours(String logIn, String logOut) {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("H:mm");
        LocalTime loginTime = LocalTime.parse(logIn, timeFormatter);
        LocalTime logoutTime = LocalTime.parse(logOut, timeFormatter);

        Duration duration = Duration.between(loginTime, logoutTime);
        double totalHours = duration.toMinutes() / 60.0;

        // Subtract 1 hour for lunch break
        totalHours -= LUNCH_BREAK_DURATION.toMinutes() / 60.0;

        return totalHours;
    }

    /**
     * Checks if the employee is late based on the login time.
     *
     * @param logIn The login time in "H:mm" format.
     * @return true if the employee is late, false otherwise.
     */
    private static boolean isLate(String logIn) {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("H:mm");
        LocalTime loginTime = LocalTime.parse(logIn, timeFormatter);

        return loginTime.isAfter(REQUIRED_LOGIN_TIME);
    }

    /**
     * Reads employee data from a CSV or Excel file and returns a map of employees.
     *
     * @param filePath The path to the file (CSV or Excel).
     * @return A map of employees where the key is the employee number and the value is the Employee object.
     * @throws IOException              If an I/O error occurs while reading the file.
     * @throws CsvValidationException   If the CSV file is invalid.
     * @throws IllegalArgumentException If the file format is unsupported.
     */
    private Map<String, Employee> readEmployeeData(String filePath) throws IOException, CsvValidationException {
        Map<String, Employee> employees = new HashMap<>();

        if (filePath.endsWith(".csv")) {
            // Read CSV file using OpenCSV
            try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
                String[] nextLine;

                // Skip the header
                reader.readNext();

                // Read and process each row of data
                while ((nextLine = reader.readNext()) != null) {
                    try {
                        // Validate row length
                        if (nextLine.length < 19) {
                            System.err.println("Skipping invalid row: Missing fields");
                            continue;
                        }

                        String employeeNumber = nextLine[0];
                        String lastName = nextLine[1];
                        String firstName = nextLine[2];
                        String birthday = nextLine[3];
                        String address = nextLine[4];
                        String phoneNumber = nextLine[5];
                        String sssNumber = nextLine[6];
                        String philhealthNumber = nextLine[7];
                        String tinNumber = nextLine[8];
                        String pagibigNumber = nextLine[9];
                        String status = nextLine[10];
                        String position = nextLine[11];
                        String immediateSupervisor = nextLine[12];
                        double basicSalary = parseDouble(nextLine[13]);
                        double riceSubsidy = parseDouble(nextLine[14]);
                        double phoneAllowance = parseDouble(nextLine[15]);
                        double clothingAllowance = parseDouble(nextLine[16]);
                        double grossSemiMonthlyRate = parseDouble(nextLine[17]);
                        double hourlyRate = parseDouble(nextLine[18]);

                        employees.put(employeeNumber, new Employee(
                                employeeNumber, lastName, firstName, birthday, address, phoneNumber,
                                sssNumber, philhealthNumber, tinNumber, pagibigNumber, status,
                                position, immediateSupervisor, basicSalary, riceSubsidy, phoneAllowance,
                                clothingAllowance, grossSemiMonthlyRate, hourlyRate
                        ));
                    } catch (NumberFormatException e) {
                        System.err.println("Skipping invalid row: Invalid number format");
                    } catch (Exception e) {
                        System.err.println("Skipping invalid row: " + e.getMessage());
                    }
                }
            }
        } else if (filePath.endsWith(".xlsx")) {
            // Read Excel file using Apache POI
            try (FileInputStream file = new FileInputStream(filePath);
                 Workbook workbook = new XSSFWorkbook(file)) {

                Sheet sheet = workbook.getSheetAt(0); // Get the first sheet
                for (Row row : sheet) {
                    if (row.getRowNum() == 0) {
                        continue; // Skip the header row
                    }

                    try {
                        // Validate row length
                        if (row.getLastCellNum() < 19) {
                            System.err.println("Skipping invalid row: Missing fields in row " + row.getRowNum());
                            continue;
                        }

                        String employeeNumber = getCellValue(row.getCell(0));
                        String lastName = getCellValue(row.getCell(1));
                        String firstName = getCellValue(row.getCell(2));
                        String birthday = getCellValue(row.getCell(3));
                        String address = getCellValue(row.getCell(4));
                        String phoneNumber = getCellValue(row.getCell(5));
                        String sssNumber = getCellValue(row.getCell(6));
                        String philhealthNumber = getCellValue(row.getCell(7));
                        String tinNumber = getCellValue(row.getCell(8));
                        String pagibigNumber = getCellValue(row.getCell(9));
                        String status = getCellValue(row.getCell(10));
                        String position = getCellValue(row.getCell(11));
                        String immediateSupervisor = getCellValue(row.getCell(12));
                        double basicSalary = parseDouble(getCellValue(row.getCell(13)));
                        double riceSubsidy = parseDouble(getCellValue(row.getCell(14)));
                        double phoneAllowance = parseDouble(getCellValue(row.getCell(15)));
                        double clothingAllowance = parseDouble(getCellValue(row.getCell(16)));
                        double grossSemiMonthlyRate = parseDouble(getCellValue(row.getCell(17)));
                        double hourlyRate = parseDouble(getCellValue(row.getCell(18)));

                        employees.put(employeeNumber, new Employee(
                                employeeNumber, lastName, firstName, birthday, address, phoneNumber,
                                sssNumber, philhealthNumber, tinNumber, pagibigNumber, status,
                                position, immediateSupervisor, basicSalary, riceSubsidy, phoneAllowance,
                                clothingAllowance, grossSemiMonthlyRate, hourlyRate
                        ));
                    } catch (NumberFormatException e) {
                        System.err.println("Skipping invalid row: Invalid number format in row " + row.getRowNum());
                        e.printStackTrace();
                    } catch (Exception e) {
                        System.err.println("Skipping invalid row: " + e.getMessage() + " in row " + row.getRowNum());
                        e.printStackTrace();
                    }
                }
            }
        } else {
            throw new IllegalArgumentException("Unsupported file format. Only .csv and .xlsx files are supported.");
        }

        return employees;
    }

    /**
     * Reads attendance data from a CSV or Excel file and updates the employee records.
     *
     * @param filePath  The path to the file (CSV or Excel).
     * @param employees A map of employees where the key is the employee number and the value is the Employee object.
     * @throws IOException              If an I/O error occurs while reading the file.
     * @throws CsvValidationException   If the CSV file is invalid.
     * @throws IllegalArgumentException If the file format is unsupported.
     */
    private void readAttendanceData(String filePath, Map<String, Employee> employees) throws IOException, CsvValidationException {
        if (filePath.endsWith(".csv")) {
            // Read CSV file using OpenCSV
            try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
                String[] nextLine;

                // Skip the header
                reader.readNext();

                // Read and process each row of data
                while ((nextLine = reader.readNext()) != null) {
                    try {
                        // Validate row length
                        if (nextLine.length < 7) {
                            System.err.println("Skipping invalid row: Missing fields");
                            continue;
                        }

                        String employeeNumber = nextLine[0];
                        String lastName = nextLine[1];
                        String firstName = nextLine[2];
                        String date = nextLine[3];
                        String logIn = nextLine[4];
                        String logOut = nextLine[5];
                        String totalWorkedHoursDaily = nextLine[6];

                        Employee employee = employees.get(employeeNumber);

                        if (employee != null) {
                            double workedHours = calculateWorkedHours(logIn, logOut);
                            boolean isLate = isLate(logIn);
                            employee.addAttendance(date, logIn, logOut, workedHours, isLate);
                        } else {
                            System.out.println("Employee not found for Employee #: " + employeeNumber);
                        }
                    } catch (Exception e) {
                        System.err.println("Skipping invalid row: " + e.getMessage());
                    }
                }
            }
        } else if (filePath.endsWith(".xlsx")) {
            // Read Excel file using Apache POI
            try (FileInputStream file = new FileInputStream(filePath);
                 Workbook workbook = new XSSFWorkbook(file)) {

                Sheet sheet = workbook.getSheetAt(0); // Get the first sheet
                for (Row row : sheet) {
                    if (row.getRowNum() == 0) {
                        continue; // Skip the header row
                    }

                    try {
                        // Validate row length
                        if (row.getLastCellNum() < 7) {
                            System.err.println("Skipping invalid row: Missing fields");
                            continue;
                        }

                        String employeeNumber = getCellValue(row.getCell(0));
                        String lastName = getCellValue(row.getCell(1));
                        String firstName = getCellValue(row.getCell(2));
                        String date = getCellValue(row.getCell(3));
                        String logIn = getCellValue(row.getCell(4));
                        String logOut = getCellValue(row.getCell(5));
                        String totalWorkedHoursDaily = getCellValue(row.getCell(6));

                        Employee employee = employees.get(employeeNumber);

                        if (employee != null) {
                            double workedHours = calculateWorkedHours(logIn, logOut);
                            boolean isLate = isLate(logIn);
                            employee.addAttendance(date, logIn, logOut, workedHours, isLate);
                        } else {
                            System.out.println("Employee not found for Employee #: " + employeeNumber);
                        }
                    } catch (Exception e) {
                        System.err.println("Skipping invalid row: " + e.getMessage());
                    }
                }
            }
        } else {
            throw new IllegalArgumentException("Unsupported file format. Only .csv and .xlsx files are supported.");
        }
    }

    /**
     * Parses a string to a double, handling commas and invalid inputs.
     *
     * @param value The string value to parse.
     * @return The parsed double value.
     * @throws NumberFormatException If the value cannot be parsed.
     */
    private double parseDouble(String value) throws NumberFormatException {
        if (value == null || value.trim().isEmpty()) {
            throw new NumberFormatException("Empty or null value");
        }
        return Double.parseDouble(value.replace(",", ""));
    }

    /**
     * Gets the string value of a cell in an Excel sheet.
     *
     * @param cell The cell to read.
     * @return The cell's value as a string.
     */
    private String getCellValue(Cell cell) {
        if (cell == null) {
            return ""; // Return empty string for null cells
        }
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return String.valueOf(cell.getNumericCellValue());
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            default:
                return "";
        }
    }

    /**
     * Displays a receipt-like output for the payroll.
     *
     * @param employee                   The employee.
     * @param totalSalary                The total salary for the period.
     * @param sssContribution            The SSS contribution.
     * @param philHealthEmployeeShare    The PhilHealth employee share.
     * @param pagIbigContribution        The Pag-IBIG contribution.
     */
    private void displayReceipt(Employee employee, double totalSalary, double sssContribution,
                               double philHealthEmployeeShare, double[] pagIbigContribution, double withHoldingTax, double allowance) {
        System.out.println("=========================================");
        System.out.println("               PAYROLL RECEIPT           ");
        System.out.println("=========================================");
        System.out.printf("Employee: %s%n", employee.getFullname());
        System.out.printf("Employee Number: %s%n", employee.getEmployeeNumber());
        System.out.println("-----------------------------------------");
        System.out.printf("Total Salary: %.2f%n", totalSalary);
        System.out.println("-----------------------------------------");
        System.out.printf("SSS Contribution: %.2f%n", sssContribution);
        System.out.printf("PhilHealth Employee Share: %.2f%n", philHealthEmployeeShare);
        System.out.printf("Pag-IBIG Employee Contribution: %.2f%n", pagIbigContribution[0]);
        System.out.printf("Pag-IBIG Employer Contribution: %.2f%n", pagIbigContribution[1]);
        System.out.printf("Total Pag-IBIG Contribution: %.2f%n", pagIbigContribution[2]);
        System.out.printf("Withholding Tax : %.2f%n", withHoldingTax);
        System.out.printf("Allowance : %.2f%n", allowance);
        System.out.println("-----------------------------------------");
        System.out.printf("Net Salary: %.2f%n", (totalSalary - sssContribution - philHealthEmployeeShare - pagIbigContribution[0] - withHoldingTax) + allowance);
        System.out.println("=========================================");
    }

    /**
     * Processes the payroll for employees based on the provided employee and attendance files.
     *
     * @param employeesFile   The path to the employee data file (CSV or Excel).
     * @param attendanceFile  The path to the attendance data file (CSV or Excel).
     */
    public void processPayroll(String employeesFile, String attendanceFile) {
        try {
            // Step 1: Read employee data
            Map<String, Employee> employees = readEmployeeData(employeesFile);

            // Step 2: Read attendance data
            readAttendanceData(attendanceFile, employees);

            // Step 3: Calculate salary, overtime, and deductions for every 4 weeks
            for (Employee employee : employees.values()) {
                System.out.println(employee);
                System.out.println("Records:");

                // Group attendance records into chunks of 4 weeks (20 working days)
                List<String> attendanceRecords = employee.getAttendanceRecords();
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

                int chunkSize = 20; // 4 weeks = 20 working days (assuming 5 working days per week)
                double totalHoursFor4Weeks = 0; // Accumulate hours for 4 weeks
                double totalOvertimePayFor4Weeks = 0; // Accumulate overtime pay for 4 weeks
                int chunkCounter = 0;

                // Variables for weekly calculations
                double totalWeeklyHours = 0; // Accumulate hours for the current week
                int weekCounter = 0; // Track the number of weeks processed

                for (int i = 0; i < attendanceRecords.size(); i++) {
                    String record = attendanceRecords.get(i);
                    String[] parts = record.split(", ");
                    String dateStr = parts[0].replace("Date: ", "");
                    double workedHours = Double.parseDouble(parts[3].replace("Worked Hours: ", ""));
                    boolean isLate = Boolean.parseBoolean(parts[4].replace("Is Late: ", ""));
                    System.out.println("Worked Hours for " + dateStr + ": " + workedHours + ", Is Late: " + isLate);

                    // Add hours to the weekly total
                    totalWeeklyHours += workedHours;
                    totalHoursFor4Weeks += workedHours; // Accumulate hours for the current 4-week chunk
                    chunkCounter++;

                    // Check if a week (5 working days) has been completed
                    if (chunkCounter % 5 == 0 || i == attendanceRecords.size() - 1) {
                        weekCounter++;

                        // Calculate overtime for the week
                        double overtimeHours = Math.max(totalWeeklyHours - 40, 0); // Hours beyond 40
                        double overtimePay = overtimeHours * employee.getHourlyRate() * 1.25; // Overtime rate is 25% more

                        // Add overtime pay to the total for 4 weeks
                        totalOvertimePayFor4Weeks += overtimePay;

                        System.out.printf("Week %d: Total Hours = %.2f, Overtime Hours = %.2f, Overtime Pay = %.2f%n",
                                weekCounter, totalWeeklyHours, overtimeHours, overtimePay);

                        // Reset weekly hours for the next week
                        totalWeeklyHours = 0;
                    }

                    // If we have processed 20 days or reached the end of the list
                    if (chunkCounter == chunkSize || i == attendanceRecords.size() - 1) {
                        double salaryFor4Weeks = totalHoursFor4Weeks * employee.getHourlyRate();

                        // Add overtime pay to the salary
                        double totalSalaryFor4Weeks = salaryFor4Weeks + totalOvertimePayFor4Weeks;

                        // Calculate deductions
                        double withHoldingTax = WithholdingTaxCalculator.calculateWithholdingTax(employee.getBasicSalary());
                        double sssContribution = SSSContributionCalculator.calculateSSSContribution(employee.getBasicSalary());
                        double philHealthEmployeeShare = PhilHealthCalculator.calculateEmployeeShare(employee.getBasicSalary());
                        double[] pagIbigContribution = PagIbigContributionCalculator.calculatePagIbigContribution(employee.getBasicSalary());
                        double allowance = employee.getBasicSalary() / 4;

                        // Display the receipt
                        displayReceipt(employee, totalSalaryFor4Weeks, sssContribution, philHealthEmployeeShare, pagIbigContribution, withHoldingTax, allowance);

                        // Reset for the next 4-week chunk
                        totalHoursFor4Weeks = 0;
                        totalOvertimePayFor4Weeks = 0;
                        chunkCounter = 0;
                    }
                }

                System.out.println("-----------------------------");
            }
        } catch (IOException | CsvValidationException e) {
            System.err.println("Error processing payroll: " + e.getMessage());
        }
    }
}