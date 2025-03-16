package services;

public class PhilHealthCalculator {

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
        if (monthlyBasicSalary <= MIN_SALARY_FOR_MIN_PREMIUM) {
            return MIN_MONTHLY_PREMIUM;
        } else if (monthlyBasicSalary >= MAX_SALARY_FOR_MAX_PREMIUM) {
            return MAX_MONTHLY_PREMIUM;
        } else {
            return monthlyBasicSalary * PREMIUM_RATE;
        }
    }

    /**
     * Calculates the employee's share of the PhilHealth premium (50% of the total premium).
     *
     * @param monthlyBasicSalary The monthly basic salary of the employee.
     * @return The employee's share of the PhilHealth premium.
     * @throws IllegalArgumentException If the salary is invalid.
     */
    public static double calculateEmployeeShare(double monthlyBasicSalary) {
        double totalPremium = calculateMonthlyPremium(monthlyBasicSalary);
        return totalPremium / 2;
    }

    /**
     * Calculates the employer's share of the PhilHealth premium (50% of the total premium).
     *
     * @param monthlyBasicSalary The monthly basic salary of the employee.
     * @return The employer's share of the PhilHealth premium.
     * @throws IllegalArgumentException If the salary is invalid.
     */
    public static double calculateEmployerShare(double monthlyBasicSalary) {
        double totalPremium = calculateMonthlyPremium(monthlyBasicSalary);
        return totalPremium / 2;
    }

    // Example usage
    public static void main(String[] args) {
        double monthlyBasicSalary = 25000.0; // Example monthly basic salary

        double totalPremium = calculateMonthlyPremium(monthlyBasicSalary);
        double employeeShare = calculateEmployeeShare(monthlyBasicSalary);
        double employerShare = calculateEmployerShare(monthlyBasicSalary);

        System.out.println("Monthly Basic Salary: " + monthlyBasicSalary);
        System.out.println("Total Monthly Premium: " + totalPremium);
        System.out.println("Employee Share (50%): " + employeeShare);
        System.out.println("Employer Share (50%): " + employerShare);
    }
}