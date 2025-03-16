package models;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import static java.lang.Double.parseDouble;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * The EmployeeDisplay class is responsible for displaying employee data from a CSV or Excel file.
 * It provides methods to display all employees or a specific employee by their ID.
 */
public class EmployeeDisplay {

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
    
    // Helper method to get cell value as string
    private String getCellValue(Cell cell) {
        if (cell == null) {
            return "";
        }
        
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                } else {
                    return String.valueOf(cell.getNumericCellValue());
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            default:
                return "";
        }
    }
    
    /**
     * Displays all employee data from the specified file.
     * 
     * @param filePath The path to the CSV or Excel file containing employee data
     * @throws IOException if there's an error reading the file
     * @throws CsvValidationException if there's an error validating the CSV file
     */
    public void displayAllEmployees(String filePath) throws IOException, CsvValidationException {
        Map<String, Employee> employees = readEmployeeData(filePath);
        
        if (employees.isEmpty()) {
            System.out.println("No employee data found in the file.");
            return;
        }
        
        System.out.println("\n===== All Employees Data =====");
        System.out.println("Total employees: " + employees.size());
        
        for (Employee employee : employees.values()) {
            displayEmployeeDetails(employee);
            System.out.println("-----------------------------------------");
        }
    }
    
    /**
     * Displays data for a specific employee identified by their employee number.
     * 
     * @param filePath The path to the CSV or Excel file containing employee data
     * @param employeeId The employee number to search for
     * @throws IOException if there's an error reading the file
     * @throws CsvValidationException if there's an error validating the CSV file
     */
    public void displayEmployeeById(String filePath, String employeeId) throws IOException, CsvValidationException {
        Map<String, Employee> employees = readEmployeeData(filePath);
        
        if (employees.isEmpty()) {
            System.out.println("No employee data found in the file.");
            return;
        }
        
        Employee employee = employees.get(employeeId);
        
        if (employee == null) {
            System.out.println("Employee with ID " + employeeId + " not found.");
            return;
        }
        
        System.out.println("\n===== Employee Details =====");
        displayEmployeeDetails(employee);
    }
    
    /**
     * Helper method to display the details of an employee.
     * 
     * @param employee The employee whose details to display
     */
    private void displayEmployeeDetails(Employee employee) {
        System.out.println("Employee Number: " + employee.getEmployeeNumber());
        System.out.println("Name: " + employee.getFullname());
        System.out.println("Birthday: " + employee);
        System.out.println("Address: " + employee.getAddress());
        System.out.println("Phone Number: " + employee.getPhoneNumber());
        System.out.println("SSS Number: " + employee.getSssNumber());
        System.out.println("PhilHealth Number: " + employee.getPhilhealthNumber());

        System.out.println("Basic Salary: " + employee.getBasicSalary());    
        System.out.println("Hourly Rate: " + employee.getHourlyRate());
    }
    
    
   
}