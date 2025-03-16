package models;

import com.opencsv.bean.CsvBindByName;

/**
 * Represents a work log entry for an employee, including details such as employee ID,
 * name, date, log-in and log-out times, and total worked hours.
 * This class is used to map CSV data to Java objects using OpenCSV annotations.
 */
public class WorkLogEntry {
    /** The employee's ID. */
    @CsvBindByName(column = "Employee #")
    private String employeeId;

    /** The employee's last name. */
    @CsvBindByName(column = "Last Name")
    private String lastName;

    /** The employee's first name. */
    @CsvBindByName(column = "First Name")
    private String firstName;

    /** The date of the work log entry. */
    @CsvBindByName(column = "Date")
    private String date;

    /** The time the employee logged in. */
    @CsvBindByName(column = "Log In")
    private String logIn;

    /** The time the employee logged out. */
    @CsvBindByName(column = "Log Out")
    private String logOut;

    /** The total hours worked by the employee for the day. */
    @CsvBindByName(column = "Total Worked Hours \nDaily")
    private String totalWorkedHours;

    /**
     * Default no-argument constructor required by OpenCSV for bean mapping.
     */
    public WorkLogEntry() {
    }

    /**
     * Gets the employee's ID.
     *
     * @return The employee's ID.
     */
    public String getEmployeeId() {
        return employeeId;
    }

    /**
     * Sets the employee's ID. Logs a warning if the provided ID is empty or null.
     *
     * @param employeeId The employee's ID.
     */
    public void setEmployeeId(String employeeId) {
        if (isEmpty(employeeId)) {
            System.out.println("Warning: Employee ID is required but is empty.");
        }
        this.employeeId = employeeId;
    }

    /**
     * Gets the employee's last name.
     *
     * @return The employee's last name.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the employee's last name. Logs a warning if the provided last name is empty or null.
     *
     * @param lastName The employee's last name.
     */
    public void setLastName(String lastName) {
        if (isEmpty(lastName)) {
            System.out.println("Warning: Last Name is required but is empty.");
        }
        this.lastName = lastName;
    }

    /**
     * Gets the employee's first name.
     *
     * @return The employee's first name.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the employee's first name. Logs a warning if the provided first name is empty or null.
     *
     * @param firstName The employee's first name.
     */
    public void setFirstName(String firstName) {
        if (isEmpty(firstName)) {
            System.out.println("Warning: First Name is required but is empty.");
        }
        this.firstName = firstName;
    }

    /**
     * Gets the date of the work log entry.
     *
     * @return The date of the work log entry.
     */
    public String getDate() {
        return date;
    }

    /**
     * Sets the date of the work log entry. Logs a warning if the provided date is empty or null.
     *
     * @param date The date of the work log entry.
     */
    public void setDate(String date) {
        if (isEmpty(date)) {
            System.out.println("Warning: Date is required but is empty.");
        }
        this.date = date;
    }

    /**
     * Gets the time the employee logged in.
     *
     * @return The log-in time.
     */
    public String getLogIn() {
        return logIn;
    }

    /**
     * Sets the time the employee logged in. Logs a warning if the provided log-in time is empty or null.
     *
     * @param logIn The log-in time.
     */
    public void setLogIn(String logIn) {
        if (isEmpty(logIn)) {
            System.out.println("Warning: Log In time is required but is empty.");
        }
        this.logIn = logIn;
    }

    /**
     * Gets the time the employee logged out.
     *
     * @return The log-out time.
     */
    public String getLogOut() {
        return logOut;
    }

    /**
     * Sets the time the employee logged out. Logs a warning if the provided log-out time is empty or null.
     *
     * @param logOut The log-out time.
     */
    public void setLogOut(String logOut) {
        if (isEmpty(logOut)) {
            System.out.println("Warning: Log Out time is required but is empty.");
        }
        this.logOut = logOut;
    }

    /**
     * Gets the total hours worked by the employee for the day.
     *
     * @return The total hours worked.
     */
    public String getTotalWorkedHours() {
        return totalWorkedHours;
    }

    /**
     * Sets the total hours worked by the employee for the day. Logs a warning if the provided value is empty or null.
     *
     * @param totalWorkedHours The total hours worked.
     */
    public void setTotalWorkedHours(String totalWorkedHours) {
        if (isEmpty(totalWorkedHours)) {
            System.out.println("Warning: Total Worked Hours is required but is empty.");
        }
        this.totalWorkedHours = totalWorkedHours;
    }

    /**
     * Helper method to check if a string is empty or null.
     *
     * @param value The string to check.
     * @return {@code true} if the string is null or empty (after trimming), otherwise {@code false}.
     */
    private boolean isEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    /**
     * Returns a string representation of the work log entry.
     *
     * @return A string containing all fields of the work log entry.
     */
    @Override
    public String toString() {
        return "WorkLogEntry{" +
                "employeeId='" + employeeId + '\'' +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", date='" + date + '\'' +
                ", logIn='" + logIn + '\'' +
                ", logOut='" + logOut + '\'' +
                ", totalWorkedHours='" + totalWorkedHours + '\'' +
                '}';
    }
}