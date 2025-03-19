package com.mycompany.motor.ph;

import java.util.Scanner;

/**
 * The LoginSystem class handles user login and logout functionality.
 * It allows the user to attempt logging in up to 3 times with default credentials.
 * If the login is successful, the user is redirected to the dashboard.
 * If the user fails 3 times, the system exits.
 */
public class LoginSystem {

    // Default credentials
    private static final String DEFAULT_USERNAME = "validaccount";
    private static final String DEFAULT_PASSWORD = "password1234";

    // Maximum login attempts
    private static final int MAX_ATTEMPTS = 3;

    /**
     * Handles the login process.
     *
     * @return true if login is successful, false otherwise.
     */
    public boolean login() {
        Scanner scanner = new Scanner(System.in);
        int attempts = 0;

        while (attempts < MAX_ATTEMPTS) {
            System.out.println("=== Login ===");

            // Prompt for username
            System.out.print("Enter username: ");
            String username = scanner.nextLine();

            // Prompt for password
            System.out.print("Enter password: ");
            String password = scanner.nextLine();

            // Validate credentials
            if (username.equals(DEFAULT_USERNAME) && password.equals(DEFAULT_PASSWORD)) {
                System.out.println("Login successful! Redirecting to dashboard...");
                return true; // Login successful
            } else {
                attempts++;
                System.out.println("Invalid credentials. Attempts remaining: " + (MAX_ATTEMPTS - attempts));
            }
        }

        // If the user exceeds the maximum attempts
        System.out.println("Maximum login attempts reached. Exiting system...");
        return false; // Login failed
    }

}