package services;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The SSSContributionCalculator class provides utility methods for calculating
 * SSS contributions based on the monthly salary of an employee.
 */
public class SSSContributionCalculator {

    // Logger instance using java.util.logging
    private static final Logger logger = Logger.getLogger(SSSContributionCalculator.class.getName());

    /**
     * Calculates the SSS contribution based on the monthly salary.
     *
     * @param monthlySalary The monthly salary of the employee.
     * @return The SSS contribution amount.
     * @throws IllegalArgumentException If the salary is invalid (e.g., negative).
     */
    public static double calculateSSSContribution(double monthlySalary) {
        // Log the start of the calculation
        logger.log(Level.INFO, "Calculating SSS contribution for salary: {0}", monthlySalary);

        // Validate the salary
        if (monthlySalary < 0) {
            logger.log(Level.SEVERE, "Invalid salary: {0}. Salary cannot be negative.", monthlySalary);
            throw new IllegalArgumentException("Salary cannot be negative.");
        }

        // Determine the SSS contribution based on salary brackets
        double contribution;
        if (monthlySalary < 3_250) {
            contribution = 135.00;
        } else if (monthlySalary < 3_750) {
            contribution = 157.50;
        } else if (monthlySalary < 4_250) {
            contribution = 180.00;
        } else if (monthlySalary < 4_750) {
            contribution = 202.50;
        } else if (monthlySalary < 5_250) {
            contribution = 225.00;
        } else if (monthlySalary < 5_750) {
            contribution = 247.50;
        } else if (monthlySalary < 6_250) {
            contribution = 270.00;
        } else if (monthlySalary < 6_750) {
            contribution = 292.50;
        } else if (monthlySalary < 7_250) {
            contribution = 315.00;
        } else if (monthlySalary < 7_750) {
            contribution = 337.50;
        } else if (monthlySalary < 8_250) {
            contribution = 360.00;
        } else if (monthlySalary < 8_750) {
            contribution = 382.50;
        } else if (monthlySalary < 9_250) {
            contribution = 405.00;
        } else if (monthlySalary < 9_750) {
            contribution = 427.50;
        } else if (monthlySalary < 10_250) {
            contribution = 450.00;
        } else if (monthlySalary < 10_750) {
            contribution = 472.50;
        } else if (monthlySalary < 11_250) {
            contribution = 495.00;
        } else if (monthlySalary < 11_750) {
            contribution = 517.50;
        } else if (monthlySalary < 12_250) {
            contribution = 540.00;
        } else if (monthlySalary < 12_750) {
            contribution = 562.50;
        } else if (monthlySalary < 13_250) {
            contribution = 585.00;
        } else if (monthlySalary < 13_750) {
            contribution = 607.50;
        } else if (monthlySalary < 14_250) {
            contribution = 630.00;
        } else if (monthlySalary < 14_750) {
            contribution = 652.50;
        } else if (monthlySalary < 15_250) {
            contribution = 675.00;
        } else if (monthlySalary < 15_750) {
            contribution = 697.50;
        } else if (monthlySalary < 16_250) {
            contribution = 720.00;
        } else if (monthlySalary < 16_750) {
            contribution = 742.50;
        } else if (monthlySalary < 17_250) {
            contribution = 765.00;
        } else if (monthlySalary < 17_750) {
            contribution = 787.50;
        } else if (monthlySalary < 18_250) {
            contribution = 810.00;
        } else if (monthlySalary < 18_750) {
            contribution = 832.50;
        } else if (monthlySalary < 19_250) {
            contribution = 855.00;
        } else if (monthlySalary < 19_750) {
            contribution = 877.50;
        } else if (monthlySalary < 20_250) {
            contribution = 900.00;
        } else if (monthlySalary < 20_750) {
            contribution = 922.50;
        } else if (monthlySalary < 21_250) {
            contribution = 945.00;
        } else if (monthlySalary < 21_750) {
            contribution = 967.50;
        } else if (monthlySalary < 22_250) {
            contribution = 990.00;
        } else if (monthlySalary < 22_750) {
            contribution = 1_012.50;
        } else if (monthlySalary < 23_250) {
            contribution = 1_035.00;
        } else if (monthlySalary < 23_750) {
            contribution = 1_057.50;
        } else if (monthlySalary < 24_250) {
            contribution = 1_080.00;
        } else if (monthlySalary < 24_750) {
            contribution = 1_102.50;
        } else {
            contribution = 1_125.00;
        }

        // Log the calculated contribution
        logger.log(Level.INFO, "Calculated SSS contribution: {0}", contribution);
        return contribution;
    }

    
}