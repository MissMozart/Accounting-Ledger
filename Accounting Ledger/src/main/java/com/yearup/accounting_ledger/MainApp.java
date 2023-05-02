package com.yearup.accounting_ledger;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;


public class MainApp {

    public MainApp() throws IOException {
    }

    public static void main(String[] args) {
        homeScreen();
    }

    public static void homeScreen() {
        Scanner scanner = new Scanner(System.in);
        boolean screenDone = false;
        while (!screenDone) {
            String heading = """
                    Welcome! Here is our main menu:
                    D) Add Deposit
                    P) Make Payment (Debit)
                    L) Ledger
                    X) Exit Application
                    """;
            System.out.println(heading);

            // switch statement for all possible options in the main menu.
            String input = scanner.nextLine();
            switch (input.toUpperCase()) {
                case "D":
                    addDeposit();
                case "P":
                    makePayment();
                case "L":
                    Ledger.showLedger();
                case "X":
                    screenDone = true;
                    System.exit(0);
                default:
                    System.out.println("That didn't work. Please try again.");

            }
        }
    }

    public static void addDeposit() {

        boolean depositDone = false;
        // repeat adding deposits until user says n (no), then take them back to the main menu.
        while (!depositDone) {

            LocalDate date = LocalDate.now();
            LocalTime time = LocalTime.now();
            String text = time.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
            LocalTime parsedTime = LocalTime.parse(text, DateTimeFormatter.ofPattern("HH:mm:ss")); // Don't show milliseconds
            Scanner scanner = new Scanner(System.in);

            System.out.println("Enter Description:");
            String description = scanner.nextLine();
            System.out.println("Enter Vendor:");
            String vendor = scanner.nextLine();
            System.out.println("Enter Deposit Amount:");
            double amount = scanner.nextDouble();

            // write to file
            try (FileWriter fileWriter = new FileWriter("transactions.csv", true)) {
                fileWriter.write("\n" +
                        date + "|" +
                        time + "|" +
                        description + "|" +
                        vendor + "|" +
                        amount);
                fileWriter.close();
                System.out.println("Deposit added successfully!");
            } catch (
                    IOException e) {
                System.out.println("That didn't work. Please try again.");
            }

            System.out.println("Do you want to make another deposit? (y/n)");
            // flush the scanner
            scanner.nextLine();
            String input = scanner.nextLine();
            if (input.equals("y")) {
                depositDone = false;
            } else if (input.equals("n")) {
                depositDone = true;
                homeScreen();
            }
        }

    }
        public static void makePayment() {
            boolean paymentDone = false;

            do {
                LocalDate date = LocalDate.now();
                LocalTime time = LocalTime.now();
                String text = time.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
                LocalTime parsedTime = LocalTime.parse(text, DateTimeFormatter.ofPattern("HH:mm:ss")); // Don't show milliseconds
                Scanner scanner = new Scanner(System.in);

                System.out.println("Enter Description:");
                String description = scanner.nextLine();
                System.out.println("Enter Vendor:");
                String vendor = scanner.nextLine();
                System.out.println("Enter Deposit Amount:");
                double amount = scanner.nextDouble();
                // write to file
                try (FileWriter fileWriter = new FileWriter("transactions.csv", true)) {
                    fileWriter.write("\n" + date + "|" +
                            parsedTime + "|" +
                            description + "|" +
                            vendor + "|-" +
                            amount);
                    fileWriter.close();
                    System.out.println("The Payment was made successfully.");
                } catch (IOException e) {
                    System.out.println("That didn't work. Please try again.");
                }
                System.out.println("Do you want to make another payment? (y/n)");
                // flush the scanner
                scanner.nextLine();
                String input = scanner.nextLine();
                if (input.equals("y")) {
                    paymentDone = false;
                } else if (input.equals("n")) {
                    paymentDone = true;
                }
            } while (!paymentDone);

        }
    }

