package com.yearup.accounting_ledger;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
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
        while (!screenDone) { // while screenDone is false, repeat showing the menu.
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
            Scanner scanner = new Scanner(System.in);

            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            String date = now.format(dateFormatter);
            String time = now.format(timeFormatter);

            System.out.println("ADD DEPOSIT\nEnter Description:");
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

            System.out.println("\nDo you want to make another deposit? (y/n)");
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
            // repeat adding payments until the user says n (no), then take them back to the main menu
            while (!paymentDone) {
                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                String date = now.format(dateFormatter);
                String time = now.format(timeFormatter);

                Scanner scanner = new Scanner(System.in);

                System.out.println("MAKE PAYMENT\nEnter Description:");
                String description = scanner.nextLine();
                System.out.println("Enter Vendor:");
                String vendor = scanner.nextLine();
                System.out.println("Enter Deposit Amount:");
                double amount = scanner.nextDouble();
                // write to file
                try (FileWriter fileWriter = new FileWriter("transactions.csv", true)) {
                    fileWriter.write( "\n" + date + "|" +
                            time + "|" +
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
                    homeScreen();
                }
            }
            System.out.println("\n");
        }
    }