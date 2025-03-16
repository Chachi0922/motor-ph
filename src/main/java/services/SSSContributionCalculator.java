/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;


public class SSSContributionCalculator {

    // Method to calculate SSS contribution based on monthly salary
    public static double calculateSSSContribution(double monthlySalary) {
        if (monthlySalary < 3_250) {
            return 135.00;
        } else if (monthlySalary >= 3_250 && monthlySalary < 3_750) {
            return 157.50;
        } else if (monthlySalary >= 3_750 && monthlySalary < 4_250) {
            return 180.00;
        } else if (monthlySalary >= 4_250 && monthlySalary < 4_750) {
            return 202.50;
        } else if (monthlySalary >= 4_750 && monthlySalary < 5_250) {
            return 225.00;
        } else if (monthlySalary >= 5_250 && monthlySalary < 5_750) {
            return 247.50;
        } else if (monthlySalary >= 5_750 && monthlySalary < 6_250) {
            return 270.00;
        } else if (monthlySalary >= 6_250 && monthlySalary < 6_750) {
            return 292.50;
        } else if (monthlySalary >= 6_750 && monthlySalary < 7_250) {
            return 315.00;
        } else if (monthlySalary >= 7_250 && monthlySalary < 7_750) {
            return 337.50;
        } else if (monthlySalary >= 7_750 && monthlySalary < 8_250) {
            return 360.00;
        } else if (monthlySalary >= 8_250 && monthlySalary < 8_750) {
            return 382.50;
        } else if (monthlySalary >= 8_750 && monthlySalary < 9_250) {
            return 405.00;
        } else if (monthlySalary >= 9_250 && monthlySalary < 9_750) {
            return 427.50;
        } else if (monthlySalary >= 9_750 && monthlySalary < 10_250) {
            return 450.00;
        } else if (monthlySalary >= 10_250 && monthlySalary < 10_750) {
            return 472.50;
        } else if (monthlySalary >= 10_750 && monthlySalary < 11_250) {
            return 495.00;
        } else if (monthlySalary >= 11_250 && monthlySalary < 11_750) {
            return 517.50;
        } else if (monthlySalary >= 11_750 && monthlySalary < 12_250) {
            return 540.00;
        } else if (monthlySalary >= 12_250 && monthlySalary < 12_750) {
            return 562.50;
        } else if (monthlySalary >= 12_750 && monthlySalary < 13_250) {
            return 585.00;
        } else if (monthlySalary >= 13_250 && monthlySalary < 13_750) {
            return 607.50;
        } else if (monthlySalary >= 13_750 && monthlySalary < 14_250) {
            return 630.00;
        } else if (monthlySalary >= 14_250 && monthlySalary < 14_750) {
            return 652.50;
        } else if (monthlySalary >= 14_750 && monthlySalary < 15_250) {
            return 675.00;
        } else if (monthlySalary >= 15_250 && monthlySalary < 15_750) {
            return 697.50;
        } else if (monthlySalary >= 15_750 && monthlySalary < 16_250) {
            return 720.00;
        } else if (monthlySalary >= 16_250 && monthlySalary < 16_750) {
            return 742.50;
        } else if (monthlySalary >= 16_750 && monthlySalary < 17_250) {
            return 765.00;
        } else if (monthlySalary >= 17_250 && monthlySalary < 17_750) {
            return 787.50;
        } else if (monthlySalary >= 17_750 && monthlySalary < 18_250) {
            return 810.00;
        } else if (monthlySalary >= 18_250 && monthlySalary < 18_750) {
            return 832.50;
        } else if (monthlySalary >= 18_750 && monthlySalary < 19_250) {
            return 855.00;
        } else if (monthlySalary >= 19_250 && monthlySalary < 19_750) {
            return 877.50;
        } else if (monthlySalary >= 19_750 && monthlySalary < 20_250) {
            return 900.00;
        } else if (monthlySalary >= 20_250 && monthlySalary < 20_750) {
            return 922.50;
        } else if (monthlySalary >= 20_750 && monthlySalary < 21_250) {
            return 945.00;
        } else if (monthlySalary >= 21_250 && monthlySalary < 21_750) {
            return 967.50;
        } else if (monthlySalary >= 21_750 && monthlySalary < 22_250) {
            return 990.00;
        } else if (monthlySalary >= 22_250 && monthlySalary < 22_750) {
            return 1_012.50;
        } else if (monthlySalary >= 22_750 && monthlySalary < 23_250) {
            return 1_035.00;
        } else if (monthlySalary >= 23_250 && monthlySalary < 23_750) {
            return 1_057.50;
        } else if (monthlySalary >= 23_750 && monthlySalary < 24_250) {
            return 1_080.00;
        } else if (monthlySalary >= 24_250 && monthlySalary < 24_750) {
            return 1_102.50;
        } else {
            return 1_125.00;
        }
    }

   
}