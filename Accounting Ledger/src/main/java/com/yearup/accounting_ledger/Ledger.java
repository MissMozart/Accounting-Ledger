package com.yearup.accounting_ledger;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
import java.time.LocalTime;
import java.time.LocalDate;

public class Ledger {

    public static Scanner scanner = new Scanner(System.in);
    public static ArrayList<Transaction> allTransactions = getTransaction(); //declare ArrayList from getTransaction method to use it globally

    public static ArrayList<Transaction> getTransaction() { // making an ArrayList from the transactions.csv file
        ArrayList<Transaction> allTransactions = new ArrayList<>();
        // this method loads transaction objects into transactions array
        try {
            FileReader fileReader = new FileReader("transactions.csv");
            BufferedReader bufReader = new BufferedReader(fileReader);

            String input;
            while ((input = bufReader.readLine()) != null) {
                String[] details = input.split("\\|");
                LocalDate localDate = LocalDate.parse(details[0]);
                LocalTime time = LocalTime.parse(details[1]);
                String description = details[2];
                String vendor = details[3];
                double amount = Double.parseDouble(details[4]);

                Transaction t = new Transaction(localDate, time, description, vendor, amount);
                allTransactions.add(t);
            }
        } catch (IOException e) {
            System.out.println("File not found!");
            System.exit(0);
        }
        Collections.sort(allTransactions, (t1, t2) -> { // sort the ArrayList by latest date and time first
            int dateComparison = t2.getDate().compareTo(t1.getDate());
            if (dateComparison == 0) {
                return t2.getTime().compareTo(t1.getTime());
            }
            return dateComparison;
        });

        return allTransactions;
    }

    public static void showLedger() {

        boolean ledgerDone = false;
        while (!ledgerDone) { // keep showing the menu until user goes back to homeScreen().
            String ledger = """
                    You can display the following:
                     A) All
                     D) Deposits
                     P) Payments
                     R) Reports
                     H) Go back to Home menu
                     """;

            System.out.println(ledger);

            String input = scanner.nextLine();

            switch (input.toUpperCase()) {
                case "A":
                    printAll();
                    break;
                case "D":
                    printDeposits();
                    break;
                case "P":
                    printPayments();
                    break;
                case "R":
                    showReports();
                    break;
                case "H":
                    MainApp.homeScreen();
                    break;
                default:
                    System.out.println("That didn't work. Please try again.");
                    break;

            }
        }
    }

    //
    public static void printAll() {

        System.out.println("\nALL TRANSACTIONS\n");
        MainApp.header();

        for (Transaction t : allTransactions) {
            System.out.printf("%-12s %-10s %-22s %-25s %-10.2f\n", t.getDate(),
                    t.getTime(),
                    t.getDescription(),
                    t.getVendor(),
                    t.getAmount()
            );
        }
        System.out.println("\n");
    }


    public static void printDeposits() {

        System.out.println("\nALL DEPOSITS\n");
        MainApp.header();

        for (Transaction t : allTransactions) {
            if (t.getAmount() > 0) { // if the transaction amount is a positive number it prints out
                System.out.printf("%-12s %-10s %-22s %-25s %-10.2f\n", t.getDate(),
                        t.getTime(),
                        t.getDescription(),
                        t.getVendor(),
                        t.getAmount()
                );
            }
        }
        System.out.println("\n");
    }


    public static void printPayments() {

        System.out.println("\nALL PAYMENTS\n");
        MainApp.header();
        for (Transaction t : allTransactions) {
            if (t.getAmount() < 0) { // if the transaction amount is a negative number it prints out
                System.out.printf("%-12s %-10s %-22s %-25s %-10.2f\n", t.getDate(),
                        t.getTime(),
                        t.getDescription(),
                        t.getVendor(),
                        t.getAmount()
                );
            }
        }
        System.out.println("\n");
    }

    public static void showReports() {

        boolean reportsDone = false;
        while (!reportsDone) { // keep showing the menu until user goes back to ledger menu
            String report = """
                    You can display the following:
                    1) Month To Date
                    2) Previous Month
                    3) Year To Date
                    4) Previous Year
                    5) Search by Vendor
                    0) Go Back
                    """;
            System.out.println(report);

            String input = scanner.nextLine();
            switch (input.toUpperCase()) {
                case "1":
                    monthToDate();
                    break;
                case "2":
                    previousMonth();
                    break;
                case "3":
                    yearToDate();
                    break;
                case "4":
                    previousYear();
                    break;
                case "5":
                    searchByVendor();
                    break;
                case "0":
                    showLedger();
                    break;
                default:
                    System.out.println("That didn't work. Please try again.");
                    break;
            }
        }
    }

    public static void monthToDate() { // show all transactions from first of this month to current date

        LocalDate today = LocalDate.now();
        System.out.println("\nALL TRANSACTIONS OF THIS MONTH TO DATE\n");
        MainApp.header();

        for (Transaction t : allTransactions) {
            LocalDate transactionDate = t.getDate();
            if (transactionDate.getMonth() == today.getMonth() && transactionDate.getYear() == today.getYear()) {
                System.out.printf("%-12s %-10s %-22s %-25s %-10.2f\n", t.getDate(),
                        t.getTime(),
                        t.getDescription(),
                        t.getVendor(),
                        t.getAmount()
                );
            }
        }
        System.out.println("\n");
    }


    public static void previousMonth() {

        LocalDate today = LocalDate.now();
        int prevMonthValue = today.minusMonths(1).getMonthValue();

        System.out.println("\nTRANSACTIONS OF THE PREVIOUS MONTH\n");
        MainApp.header();

        for (Transaction t : allTransactions) {
            LocalDate transactionDate = t.getDate();
            if (transactionDate.getMonthValue() == prevMonthValue && transactionDate.getYear() == today.getYear()) {
                System.out.printf("%-12s %-10s %-22s %-25s %-10.2f\n", t.getDate(),
                        t.getTime(),
                        t.getDescription(),
                        t.getVendor(),
                        t.getAmount()
                );
            }
        }
        System.out.println("\n");
    }

    public static void yearToDate() { // show all transactions of this year
        LocalDate today = LocalDate.now();

        System.out.println("\nALL TRANSACTIONS OF THIS YEAR TO DATE\n");
        MainApp.header();

        for (Transaction t : allTransactions) {
            LocalDate transactionDate = t.getDate();
            if (transactionDate.getYear() == today.getYear()) {
                System.out.printf("%-12s %-10s %-22s %-25s %-10.2f\n", t.getDate(),
                        t.getTime(),
                        t.getDescription(),
                        t.getVendor(),
                        t.getAmount()
                );
            }
        }
        System.out.println("\n");
    }

    public static void previousYear() {
        LocalDate today = LocalDate.now();

        System.out.println("\nALL TRANSACTIONS OF LAST YEAR\n");
        MainApp.header();

        for (Transaction t : allTransactions) {
            LocalDate transactionDate = t.getDate();
            if (transactionDate.getYear() == today.getYear() - 1) {
                System.out.printf("%-12s %-10s %-22s %-25s %-10.2f\n", t.getDate(),
                        t.getTime(),
                        t.getDescription(),
                        t.getVendor(),
                        t.getAmount()
                );
            }
        }
        System.out.println("\n");
    }

    public static void searchByVendor() {

        System.out.println("Which vendor transactions do you want to see?");
        String vendor = scanner.nextLine().toUpperCase();

        System.out.println("\nTRANSACTIONS FOR " + vendor + "\n");
        MainApp.header();

        for (Transaction t : allTransactions) {
            if (t.getVendor().equalsIgnoreCase(vendor)) {
                System.out.printf("%-12s %-10s %-22s %-25s %-10.2f\n", t.getDate(),
                        t.getTime(),
                        t.getDescription(),
                        t.getVendor(),
                        t.getAmount()
                );
            }
        }
        System.out.println("\n");
    }

}
