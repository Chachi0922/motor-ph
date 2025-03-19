package models;

import org.apache.poi.ss.usermodel.Row;
import java.util.ArrayList;
import java.util.List;

/**
 * The Employee class represents an employee in the MotorPh payroll system.
 * It stores personal information, employment details, salary information, and attendance records.
 */
public class Employee {
    private String employeeNumber;
    private String lastName;
    private String firstName;
    private String birthday;
    private String address;
    private String phoneNumber;
    private String sssNumber;
    private String philhealthNumber;
    private String tinNumber;
    private String pagibigNumber;
    private String status;
    private String position;
    private String immediateSupervisor;
    private double basicSalary;
    private double riceSubsidy;
    private double phoneAllowance;
    private double clothingAllowance;
    private double grossSemiMonthlyRate;
    private double hourlyRate;
    private List<String> attendanceRecords;
    private double totalWorkedHours;

    /**
     * Constructs a new Employee object with the specified details.
     *
     * @param employeeNumber      The employee's unique identification number.
     * @param lastName            The employee's last name.
     * @param firstName           The employee's first name.
     * @param birthday            The employee's birthday.
     * @param address             The employee's address.
     * @param phoneNumber         The employee's phone number.
     * @param sssNumber           The employee's SSS number.
     * @param philhealthNumber    The employee's PhilHealth number.
     * @param tinNumber           The employee's TIN number.
     * @param pagibigNumber       The employee's Pag-IBIG number.
     * @param status              The employee's employment status.
     * @param position            The employee's job position.
     * @param immediateSupervisor The employee's immediate supervisor.
     * @param basicSalary         The employee's basic salary.
     * @param riceSubsidy         The employee's rice subsidy allowance.
     * @param phoneAllowance      The employee's phone allowance.
     * @param clothingAllowance   The employee's clothing allowance.
     * @param grossSemiMonthlyRate The employee's gross semi-monthly rate.
     * @param hourlyRate          The employee's hourly rate.
     */
    public Employee(String employeeNumber, String lastName, String firstName, String birthday, String address,
                    String phoneNumber, String sssNumber, String philhealthNumber, String tinNumber,
                    String pagibigNumber, String status, String position, String immediateSupervisor,
                    double basicSalary, double riceSubsidy, double phoneAllowance, double clothingAllowance,
                    double grossSemiMonthlyRate, double hourlyRate) {
        this.employeeNumber = employeeNumber;
        this.lastName = lastName;
        this.firstName = firstName;
        this.birthday = birthday;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.sssNumber = sssNumber;
        this.philhealthNumber = philhealthNumber;
        this.tinNumber = tinNumber;
        this.pagibigNumber = pagibigNumber;
        this.status = status;
        this.position = position;
        this.immediateSupervisor = immediateSupervisor;
        this.basicSalary = basicSalary;
        this.riceSubsidy = riceSubsidy;
        this.phoneAllowance = phoneAllowance;
        this.clothingAllowance = clothingAllowance;
        this.grossSemiMonthlyRate = grossSemiMonthlyRate;
        this.hourlyRate = hourlyRate;
        this.attendanceRecords = new ArrayList<>();
        this.totalWorkedHours = 0;
    }

    /**
     * Adds an attendance record for the employee.
     *
     * @param date        The date of the attendance.
     * @param logIn       The time the employee logged in.
     * @param logOut      The time the employee logged out.
     * @param workedHours The number of hours worked.
     */
    public void addAttendance(String date, String logIn, String logOut, double workedHours) {
        attendanceRecords.add(String.format(
            "Date: %s, Log In: %s, Log Out: %s, Worked Hours: %.2f",
            date, logIn, logOut, workedHours
        ));
        totalWorkedHours += workedHours;
    }

    /**
     * Adds an attendance record for the employee, including whether they were late.
     *
     * @param date        The date of the attendance.
     * @param logIn       The time the employee logged in.
     * @param logOut      The time the employee logged out.
     * @param workedHours The number of hours worked.
     * @param isLate      Whether the employee was late.
     */
    public void addAttendance(String date, String logIn, String logOut, double workedHours, boolean isLate) {
        String record = String.format("Date: %s, LogIn: %s, LogOut: %s, Worked Hours: %.2f, Is Late: %b",
                date, logIn, logOut, workedHours, isLate);
        attendanceRecords.add(record);
        totalWorkedHours += workedHours;
    }

    /**
     * Adds an attendance record from a CSV row.
     *
     * @param data The CSV row data.
     */
    public void addAttendanceFromCSV(String[] data) {
        String date = data[3];
        String logIn = data[4];
        String logOut = data[5];
        double workedHours = Double.parseDouble(data[6]);
        boolean isLate = Boolean.parseBoolean(data[7]);
        addAttendance(date, logIn, logOut, workedHours, isLate);
    }

    /**
     * Adds an attendance record from an Excel row.
     *
     * @param row The Excel row.
     */
    public void addAttendanceFromExcelRow(Row row) {
        String date = row.getCell(3).getStringCellValue();
        String logIn = row.getCell(4).getStringCellValue();
        String logOut = row.getCell(5).getStringCellValue();
        double workedHours = row.getCell(6).getNumericCellValue();
        boolean isLate = row.getCell(7).getBooleanCellValue();
        addAttendance(date, logIn, logOut, workedHours, isLate);
    }

    /**
     * Creates an Employee object from a CSV row.
     *
     * @param data The CSV row data.
     * @return The created Employee object.
     */
    public static Employee fromCSV(String[] data) {
        return new Employee(
                data[0], // employeeNumber
                data[1], // lastName
                data[2], // firstName
                data[3], // birthday
                data[4], // address
                data[5], // phoneNumber
                data[6], // sssNumber
                data[7], // philhealthNumber
                data[8], // tinNumber
                data[9], // pagibigNumber
                data[10], // status
                data[11], // position
                data[12], // immediateSupervisor
                Double.parseDouble(data[13]), // basicSalary
                Double.parseDouble(data[14]), // riceSubsidy
                Double.parseDouble(data[15]), // phoneAllowance
                Double.parseDouble(data[16]), // clothingAllowance
                Double.parseDouble(data[17]), // grossSemiMonthlyRate
                Double.parseDouble(data[18])  // hourlyRate
        );
    }

    /**
     * Creates an Employee object from an Excel row.
     *
     * @param row The Excel row.
     * @return The created Employee object.
     */
    public static Employee fromExcelRow(Row row) {
        return new Employee(
                row.getCell(0).getStringCellValue(), // employeeNumber
                row.getCell(1).getStringCellValue(), // lastName
                row.getCell(2).getStringCellValue(), // firstName
                row.getCell(3).getStringCellValue(), // birthday
                row.getCell(4).getStringCellValue(), // address
                row.getCell(5).getStringCellValue(), // phoneNumber
                row.getCell(6).getStringCellValue(), // sssNumber
                row.getCell(7).getStringCellValue(), // philhealthNumber
                row.getCell(8).getStringCellValue(), // tinNumber
                row.getCell(9).getStringCellValue(), // pagibigNumber
                row.getCell(10).getStringCellValue(), // status
                row.getCell(11).getStringCellValue(), // position
                row.getCell(12).getStringCellValue(), // immediateSupervisor
                row.getCell(13).getNumericCellValue(), // basicSalary
                row.getCell(14).getNumericCellValue(), // riceSubsidy
                row.getCell(15).getNumericCellValue(), // phoneAllowance
                row.getCell(16).getNumericCellValue(), // clothingAllowance
                row.getCell(17).getNumericCellValue(), // grossSemiMonthlyRate
                row.getCell(18).getNumericCellValue()  // hourlyRate
        );
    }

    /**
     * Returns the list of attendance records for the employee.
     *
     * @return A list of attendance records as strings.
     */
    public List<String> getAttendanceRecords() {
        return attendanceRecords;
    }

    /**
     * Returns the full name of the employee.
     *
     * @return The employee's full name in the format "FirstName LastName".
     */
    public String getFullname() {
        return this.firstName + " " + this.lastName;
    }

    /**
     * Returns the employee's birthday.
     *
     * @return The birthday.
     */
    public String getBirthday() {
        return this.birthday;
    }

    /**
     * Returns the employee's address.
     *
     * @return The address.
     */
    public String getAddress() {
        return this.address;
    }

    /**
     * Returns the employee's phone number.
     *
     * @return The phone number.
     */
    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    /**
     * Returns the employee's SSS number.
     *
     * @return The SSS number.
     */
    public String getSssNumber() {
        return this.sssNumber;
    }

    /**
     * Returns the employee's PhilHealth number.
     *
     * @return The PhilHealth number.
     */
    public String getPhilhealthNumber() {
        return this.philhealthNumber;
    }

    /**
     * Returns the employee's TIN number.
     *
     * @return The TIN number.
     */
    public String getTinNumber() {
        return this.tinNumber;
    }

    /**
     * Returns the employee's unique identification number.
     *
     * @return The employee number.
     */
    public String getEmployeeNumber() {
        return this.employeeNumber;
    }

    /**
     * Returns the total number of hours worked by the employee.
     *
     * @return The total worked hours.
     */
    public double getTotalWorkedHours() {
        return totalWorkedHours;
    }

    /**
     * Returns the employee's hourly rate.
     *
     * @return The hourly rate.
     */
    public double getHourlyRate() {
        return hourlyRate;
    }

    /**
     * Returns the employee's basic salary.
     *
     * @return The basic salary.
     */
    public double getBasicSalary() {
        return basicSalary;
    }

    /**
     * Returns a string representation of the employee.
     *
     * @return A string containing the employee's number, name, position, basic salary, and hourly rate.
     */
    @Override
    public String toString() {
        return String.format(
            "Employee #: %s, Name: %s %s, Position: %s, Basic Salary: %.2f, Hourly Rate: %.2f",
            employeeNumber, firstName, lastName, position, basicSalary, hourlyRate
        );
    }
}