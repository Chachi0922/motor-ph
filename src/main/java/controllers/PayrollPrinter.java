package controllers;

import models.Employee;

public class PayrollPrinter {

    /**
     * Displays a payroll receipt for an employee.
     *
     * @param employee                   The employee.
     * @param totalSalary                The total salary.
     * @param sssContribution            The SSS contribution.
     * @param philHealthEmployeeShare    The PhilHealth employee share.
     * @param pagIbigContribution        The Pag-IBIG contribution.
     * @param withHoldingTax             The withholding tax.
     * @param allowance                  The allowance.
     */
    public void displayReceipt(Employee employee, double totalSalary, double sssContribution,
                               double philHealthEmployeeShare, double[] pagIbigContribution, double withHoldingTax, double allowance) {
        System.out.println("=========================================");
        System.out.println("               PAYROLL RECEIPT           ");
        System.out.println("=========================================");
        System.out.printf("Employee: %s%n", employee.getFullname());
        System.out.printf("Employee Number: %s%n", employee.getEmployeeNumber());
        System.out.println("-----------------------------------------");
        System.out.printf("Total Salary: %.2f%n", totalSalary);
        System.out.println("-----------------------------------------");
        System.out.printf("SSS Contribution: %.2f%n", sssContribution);
        System.out.printf("PhilHealth Employee Share: %.2f%n", philHealthEmployeeShare);
        System.out.printf("Pag-IBIG Employee Contribution: %.2f%n", pagIbigContribution[0]);
        System.out.printf("Pag-IBIG Employer Contribution: %.2f%n", pagIbigContribution[1]);
        System.out.printf("Total Pag-IBIG Contribution: %.2f%n", pagIbigContribution[2]);
        System.out.printf("Withholding Tax : %.2f%n", withHoldingTax);
        System.out.printf("Allowance : %.2f%n", allowance);
        System.out.println("-----------------------------------------");
        System.out.printf("Net Salary: %.2f%n", (totalSalary - sssContribution - philHealthEmployeeShare - pagIbigContribution[0] - withHoldingTax) + allowance);
        System.out.println("=========================================");
    }
}