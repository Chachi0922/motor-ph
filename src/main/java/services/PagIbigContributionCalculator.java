package services;

public class PagIbigContributionCalculator {

    // Constants for Pag-IBIG contribution rates
    private static final double RATE_1 = 0.01; // 1% for employees earning at least 1,000 to 1,500
    private static final double RATE_2 = 0.02; // 2% for employees earning over 1,500
    private static final double EMPLOYER_RATE = 0.02; // 2% for employers
    private static final double MAX_CONTRIBUTION = 100.0; // Maximum contribution amount

    /**
     * Calculates the Pag-IBIG contribution for an employee.
     *
     * @param monthlyBasicSalary The employee's monthly basic salary.
     * @return An array containing:
     *         - Employee's contribution
     *         - Employer's contribution
     *         - Total contribution
     */
    public static double[] calculatePagIbigContribution(double monthlyBasicSalary) {
        double employeeContribution;
        double employerContribution;
        double totalContribution;

        if (monthlyBasicSalary >= 1000 && monthlyBasicSalary <= 1500) {
            // Employee earns at least 1,000 to 1,500
            employeeContribution = monthlyBasicSalary * RATE_1;
            employerContribution = monthlyBasicSalary * EMPLOYER_RATE;
        } else if (monthlyBasicSalary > 1500) {
            // Employee earns over 1,500
            employeeContribution = monthlyBasicSalary * RATE_2;
            employerContribution = monthlyBasicSalary * EMPLOYER_RATE;
        } else {
            // Employee earns less than 1,000 (no contribution)
            employeeContribution = 0;
            employerContribution = 0;
        }

        // Apply the maximum contribution limit
        totalContribution = employeeContribution + employerContribution;
        if (totalContribution > MAX_CONTRIBUTION) {
            employeeContribution = MAX_CONTRIBUTION * (employeeContribution / totalContribution);
            employerContribution = MAX_CONTRIBUTION * (employerContribution / totalContribution);
            totalContribution = MAX_CONTRIBUTION;
        }

        return new double[]{employeeContribution, employerContribution, totalContribution};
    }
}