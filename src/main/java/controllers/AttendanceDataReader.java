package controllers;

import com.opencsv.CSVReader;
import models.Employee;
import com.opencsv.exceptions.CsvValidationException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import static org.apache.poi.ss.usermodel.CellType.BOOLEAN;
import static org.apache.poi.ss.usermodel.CellType.NUMERIC;
import static org.apache.poi.ss.usermodel.CellType.STRING;

public class AttendanceDataReader {
    
    private static final LocalTime REQUIRED_LOGIN_TIME = LocalTime.of(8,11); // 8:11 AM
    private static final LocalTime REQUIRED_LOGOUT_TIME = LocalTime.of(17, 0); // 7:00 PM
    private static final Duration LUNCH_BREAK_DURATION = Duration.ofHours(1); // 1-hour lunch break
    
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
    
     private static boolean isLate(String logIn) {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("H:mm");
        LocalTime loginTime = LocalTime.parse(logIn, timeFormatter);

        return loginTime.isAfter(REQUIRED_LOGIN_TIME);
    }
    
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

    public void readAttendanceData(String filePath, Map<String, Employee> employees) throws IOException, CsvValidationException {
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

}