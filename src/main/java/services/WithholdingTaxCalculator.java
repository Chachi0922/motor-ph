/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;


public class WithholdingTaxCalculator {

    public static double calculateWithholdingTax(double monthlySalary) {
        if (monthlySalary <= 20_832) {
            // No withholding tax
            return 0;
        } else if (monthlySalary <= 33_333) {
            // 20% of the amount in excess of 20,833
            return 0.20 * (monthlySalary - 20_833);
        } else if (monthlySalary <= 66_667) {
            // 2,500 + 25% of the amount in excess of 33,333
            return 2_500 + 0.25 * (monthlySalary - 33_333);
        } else if (monthlySalary <= 166_667) {
            // 10,833 + 30% of the amount in excess of 66,667
            return 10_833 + 0.30 * (monthlySalary - 66_667);
        } else if (monthlySalary <= 666_667) {
            // 40,833.33 + 32% of the amount in excess of 166,667
            return 40_833.33 + 0.32 * (monthlySalary - 166_667);
        } else {
            // 200,833.33 + 35% of the amount in excess of 666,667
            return 200_833.33 + 0.35 * (monthlySalary - 666_667);
        }
    }
}