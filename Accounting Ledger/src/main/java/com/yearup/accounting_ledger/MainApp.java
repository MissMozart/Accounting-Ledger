package com.yearup.accounting_ledger;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;


public class MainApp {

    public MainApp() {
    }
    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        homeScreen();
    }

    public static void homeScreen() {

        boolean screenDone = false;
        while (!screenDone) { // while screenDone is false, repeat showing the menu.
            String heading = """
                    Welcome! Here is our main menu:
                    D) Add Deposit
                    P) Make Payment (Debit)
                    L) Ledger
                    X) Exit Application
                    """;
            System.out.println(heading);

            String input = scanner.nextLine();
            switch (input.toUpperCase()) {
                case "D":
                    addDeposit();
                    break;
                case "P":
                    makePayment();
                    break;
                case "L":
                    Ledger.showLedger();
                case "X":
                    System.out.println("Goodbye!");
                    System.exit(0);
                default:
                    System.out.println("That didn't work. Please try again.\n");

            }
        }
    }

    public static void addDeposit() {

        boolean depositDone = false;
        // repeat adding deposits until user says n (no), then take them back to the main menu.
        while (!depositDone) {



            System.out.println("ADD DEPOSIT\nEnter Description:");
            String description = scanner.nextLine();
            System.out.println("Enter Vendor:");
            String vendor = scanner.nextLine();
            System.out.println("Enter Deposit Amount:");
            double amount = scanner.nextDouble();

            // write to file
            try (FileWriter fileWriter = new FileWriter("transactions.csv", true)) {

                fileWriter.write("\n" +
                        LocalDate.now() + "|" +
                        LocalTime.now().truncatedTo(ChronoUnit.SECONDS) + "|" +
                        description + "|" +
                        vendor + "|" +
                        amount);
                fileWriter.close();
                System.out.println("Deposit added successfully!");
            } catch (
                    IOException e) {
                System.out.println("That didn't work. Please try again.");
            }

            System.out.println("\nDo you want to make another deposit? (y/n)");

            scanner.nextLine(); // flush the scanner
            String input = scanner.nextLine();
            if (input.equals("y")) {
                depositDone = false;
            } else if (input.equals("n")) {
                depositDone = true;
            }
        }
        System.out.println("\n");
    }
        public static void makePayment() {
            boolean paymentDone = false;
            // repeat adding payments until the user says n (no), then take them back to the main menu
            while (!paymentDone) {


                System.out.println("MAKE PAYMENT\nEnter Description:");
                String description = scanner.nextLine();
                System.out.println("Enter Vendor:");
                String vendor = scanner.nextLine();
                System.out.println("Enter Payment Amount:");
                double amount = scanner.nextDouble();
                // write to file
                try (FileWriter fileWriter = new FileWriter("transactions.csv", true)) {
                    fileWriter.write( "\n" + LocalDate.now() + "|" +
                            LocalTime.now().truncatedTo(ChronoUnit.SECONDS) + "|" +
                            description + "|" +
                            vendor + "|-" +
                            amount);
                    fileWriter.close();
                    System.out.println("The Payment was made successfully.");
                } catch (IOException e) {
                    System.out.println("That didn't work. Please try again.");
                }
                System.out.println("Do you want to make another payment? (y/n)");

                scanner.nextLine(); // flush the scanner
                String input = scanner.nextLine();
                if (input.equals("y")) {
                    paymentDone = false;
                } else if (input.equals("n")) {
                    paymentDone = true;
                }
            }
            System.out.println("\n");
        }
        public static void header() {
            System.out.println("DATE           TIME        DESCRIPTION         VENDOR                     AMOUNT");
            System.out.println("-----------------------------------------------------------------------------------");
        }
    }