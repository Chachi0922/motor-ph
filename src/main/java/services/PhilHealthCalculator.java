package services;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The PhilHealthCalculator class provides utility methods for calculating
 * PhilHealth premiums based on the monthly basic salary of an employee.
 */
public class PhilHealthCalculator {

    // Logger instance using java.util.logging
    private static final Logger logger = Logger.getLogger(PhilHealthCalculator.class.getName());

    // Constants for PhilHealth calculation
    private static final double PREMIUM_RATE = 0.03; // 3%
    private static final double MIN_MONTHLY_PREMIUM = 300.0; // ₱300
    private static final double MAX_MONTHLY_PREMIUM = 1800.0; // ₱1,800
    private static final double MIN_SALARY_FOR_MIN_PREMIUM = 10000.0; // ₱10,000
    private static final double MAX_SALARY_FOR_MAX_PREMIUM = 60000.0; // ₱60,000

    /**
     * Calculates the monthly PhilHealth premium based on the monthly basic salary.
     *
     * @param monthlyBasicSalary The monthly basic salary of the employee.
     * @return The total monthly PhilHealth premium.
     * @throws IllegalArgumentException If the salary is invalid.
     */
    public static double calculateMonthlyPremium(double monthlyBasicSalary) {
        // Log the start of the calculation
        logger.log(Level.INFO, "Calculating monthly premium for salary: {0}", monthlyBasicSalary);

        if (monthlyBasicSalary < 0) {
            logger.log(Level.SEVERE, "Invalid salary: {0}. Salary cannot be negative.", monthlyBasicSalary);
            throw new IllegalArgumentException("Salary cannot be negative.");
        }

        double premium;
        if (monthlyBasicSalary <= MIN_SALARY_FOR_MIN_PREMIUM) {
            premium = MIN_MONTHLY_PREMIUM;
            logger.log(Level.FINE, "Salary <= {0}. Using minimum premium: {1}", 
                new Object[]{MIN_SALARY_FOR_MIN_PREMIUM, premium});
        } else if (monthlyBasicSalary >= MAX_SALARY_FOR_MAX_PREMIUM) {
            premium = MAX_MONTHLY_PREMIUM;
            logger.log(Level.FINE, "Salary >= {0}. Using maximum premium: {1}", 
                new Object[]{MAX_SALARY_FOR_MAX_PREMIUM, premium});
        } else {
            premium = monthlyBasicSalary * PREMIUM_RATE;
            logger.log(Level.FINE, "Salary between {0} and {1}. Calculated premium: {2}", 
                new Object[]{MIN_SALARY_FOR_MIN_PREMIUM, MAX_SALARY_FOR_MAX_PREMIUM, premium});
        }

        // Log the calculated premium
        logger.log(Level.INFO, "Calculated monthly premium: {0}", premium);
        return premium;
    }

    /**
     * Calculates the employee's share of the PhilHealth premium (50% of the total premium).
     *
     * @param monthlyBasicSalary The monthly basic salary of the employee.
     * @return The employee's share of the PhilHealth premium.
     * @throws IllegalArgumentException If the salary is invalid.
     */
    public static double calculateEmployeeShare(double monthlyBasicSalary) {
        // Log the start of the calculation
        logger.log(Level.INFO, "Calculating employee share for salary: {0}", monthlyBasicSalary);

        double totalPremium = calculateMonthlyPremium(monthlyBasicSalary);
        double employeeShare = totalPremium / 2;

        // Log the calculated employee share
        logger.log(Level.INFO, "Calculated employee share: {0}", employeeShare);
        return employeeShare;
    }

    /**
     * Calculates the employer's share of the PhilHealth premium (50% of the total premium).
     *
     * @param monthlyBasicSalary The monthly basic salary of the employee.
     * @return The employer's share of the PhilHealth premium.
     * @throws IllegalArgumentException If the salary is invalid.
     */
    public static double calculateEmployerShare(double monthlyBasicSalary) {
        // Log the start of the calculation
        logger.log(Level.INFO, "Calculating employer share for salary: {0}", monthlyBasicSalary);

        double totalPremium = calculateMonthlyPremium(monthlyBasicSalary);
        double employerShare = totalPremium / 2;

        // Log the calculated employer share
        logger.log(Level.INFO, "Calculated employer share: {0}", employerShare);
        return employerShare;
    }

    // Example usage
    public static void main(String[] args) {
        double monthlyBasicSalary = 25000.0; // Example monthly basic salary

        // Log the example usage
        logger.log(Level.INFO, "Example usage with salary: {0}", monthlyBasicSalary);

        double totalPremium = calculateMonthlyPremium(monthlyBasicSalary);
        double employeeShare = calculateEmployeeShare(monthlyBasicSalary);
        double employerShare = calculateEmployerShare(monthlyBasicSalary);

        // Display results
        System.out.println("Monthly Basic Salary: " + monthlyBasicSalary);
        System.out.println("Total Monthly Premium: " + totalPremium);
        System.out.println("Employee Share (50%): " + employeeShare);
        System.out.println("Employer Share (50%): " + employerShare);
    }
}