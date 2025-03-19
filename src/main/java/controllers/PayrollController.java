package controllers;

import com.opencsv.exceptions.CsvValidationException;
import models.Employee;
import services.PhilHealthCalculator;
import services.SSSContributionCalculator;
import services.PagIbigContributionCalculator;

import java.io.IOException;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import services.WithholdingTaxCalculator;

/**
 * The PayrollController class handles the processing of payroll data.
 * It reads employee and attendance data from CSV or Excel files, calculates worked hours,
 * overtime, and deductions (SSS, PhilHealth, Pag-IBIG), and generates a payroll receipt.
 */
public class PayrollController {
    
    /**
     * Processes the payroll for employees based on the provided employee and attendance files.
     *
     * @param employeesFile   The path to the employee data file (CSV or Excel).
     * @param attendanceFile  The path to the attendance data file (CSV or Excel).
     */
    public void processPayroll(String employeesFile, String attendanceFile) {
        try {
            AttendanceDataReader attreader = new AttendanceDataReader();
            EmployeeDataReader empreader = new EmployeeDataReader();
            PayrollPrinter printer = new PayrollPrinter();
            // Step 1: Read employee data
            Map<String, Employee> employees =  empreader.readEmployeeData(employeesFile);

            // Step 2: Read attendance data
            attreader.readAttendanceData(attendanceFile, employees);

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
                        printer.displayReceipt(employee, totalSalaryFor4Weeks, sssContribution, philHealthEmployeeShare, pagIbigContribution, withHoldingTax, allowance);

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