package models;

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
    
     public String getBirthday() {
        return this.birthday;
    }
     
    public String getAddress() {
        return this.address;
    }
    
    public String getPhoneNumber() {
        return this.phoneNumber;
    }
    
    public String getSssNumber() {
        return this.sssNumber;
    }
    
     public String  getPhilhealthNumber() {
        return this.philhealthNumber;
    }
     
     public String  getTinNumber() {
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
    
    public void addAttendance(String date, String logIn, String logOut, double workedHours, boolean isLate) {
        String record = String.format("Date: %s, LogIn: %s, LogOut: %s, Worked Hours: %.2f, Is Late: %b",
                date, logIn, logOut, workedHours, isLate);
        attendanceRecords.add(record);
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