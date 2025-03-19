package controllers;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import models.Employee;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class EmployeeDataReader {

    // Helper method to parse double with a default value
    private double parseDoubleWithDefault(String value, double defaultValue) {
        if (value == null || value.trim().isEmpty()) {
            return defaultValue; // Return default value for empty or null strings
        }
        try {
            // Remove commas from the string
            String cleanedValue = value.replace(",", "");
            return Double.parseDouble(cleanedValue);
        } catch (NumberFormatException e) {
            System.err.println("Invalid number format for value: " + value);
            return defaultValue;
        }
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
        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue();
            case NUMERIC -> String.valueOf(cell.getNumericCellValue());
            case BOOLEAN -> String.valueOf(cell.getBooleanCellValue());
            default -> "";
        };
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
    public Map<String, Employee> readEmployeeData(String filePath) throws IOException, CsvValidationException {
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
                        // Debugging: Print raw row data
                        System.out.println("Raw row data: " + Arrays.toString(nextLine));

                        // Validate row length
                        if (nextLine.length < 19) {
                            System.err.println("Skipping invalid row: Missing fields");
                            continue;
                        }

                        // Trim whitespace from all fields
                        String employeeNumber = nextLine[0].trim();
                        String lastName = nextLine[1].trim();
                        String firstName = nextLine[2].trim();
                        String birthday = nextLine[3].trim();
                        String address = nextLine[4].trim();
                        String phoneNumber = nextLine[5].trim();
                        String sssNumber = nextLine[6].trim();
                        String philhealthNumber = nextLine[7].trim();
                        String tinNumber = nextLine[8].trim();
                        String pagibigNumber = nextLine[9].trim();
                        String status = nextLine[10].trim();
                        String position = nextLine[11].trim();
                        String immediateSupervisor = nextLine[12].trim();

                        // Parse numeric fields with validation
                        double basicSalary = parseDoubleWithDefault(nextLine[13].trim(), 0.0);
                        double riceSubsidy = parseDoubleWithDefault(nextLine[14].trim(), 0.0);
                        double phoneAllowance = parseDoubleWithDefault(nextLine[15].trim(), 0.0);
                        double clothingAllowance = parseDoubleWithDefault(nextLine[16].trim(), 0.0);
                        double grossSemiMonthlyRate = parseDoubleWithDefault(nextLine[17].trim(), 0.0);
                        double hourlyRate = parseDoubleWithDefault(nextLine[18].trim(), 0.0);

                        // Create and add the employee
                        employees.put(employeeNumber, new Employee(
                                employeeNumber, lastName, firstName, birthday, address, phoneNumber,
                                sssNumber, philhealthNumber, tinNumber, pagibigNumber, status,
                                position, immediateSupervisor, basicSalary, riceSubsidy, phoneAllowance,
                                clothingAllowance, grossSemiMonthlyRate, hourlyRate
                        ));
                    } catch (Exception e) {
                        System.err.println("Skipping invalid row: " + e.getMessage());
                        e.printStackTrace();
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
                        if (row.getPhysicalNumberOfCells() < 19) {
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
                        double basicSalary = parseDoubleWithDefault(getCellValue(row.getCell(13)), 0.0);
                        double riceSubsidy = parseDoubleWithDefault(getCellValue(row.getCell(14)), 0.0);
                        double phoneAllowance = parseDoubleWithDefault(getCellValue(row.getCell(15)), 0.0);
                        double clothingAllowance = parseDoubleWithDefault(getCellValue(row.getCell(16)), 0.0);
                        double grossSemiMonthlyRate = parseDoubleWithDefault(getCellValue(row.getCell(17)), 0.0);
                        double hourlyRate = parseDoubleWithDefault(getCellValue(row.getCell(18)), 0.0);

                        // Create and add the employee
                        employees.put(employeeNumber, new Employee(
                                employeeNumber, lastName, firstName, birthday, address, phoneNumber,
                                sssNumber, philhealthNumber, tinNumber, pagibigNumber, status,
                                position, immediateSupervisor, basicSalary, riceSubsidy, phoneAllowance,
                                clothingAllowance, grossSemiMonthlyRate, hourlyRate
                        ));
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
}