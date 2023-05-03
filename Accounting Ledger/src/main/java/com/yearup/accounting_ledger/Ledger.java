package com.yearup.accounting_ledger;

import java.io.*;
import java.util.*;
import java.time.LocalTime;
import java.time.LocalDate;

public class Ledger {

    public static ArrayList<Transaction> allTransactions = getTransaction(); //declare ArrayList from getTransaction method

    public static ArrayList<Transaction> getTransaction() {
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
        Collections.sort(allTransactions, (t1, t2) -> {
            int dateComparison = t2.getDate().compareTo(t1.getDate());
            if (dateComparison == 0) {
                return t2.getTime().compareTo(t1.getTime());
            }
            return dateComparison;
        });

        return allTransactions;
    }

    public static void showLedger() {
        Scanner scanner = new Scanner(System.in);
        boolean ledgerDone = false;
        while (!ledgerDone) {
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
            // switch statement for all possible options in the ledger menu.
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
                    printReports();
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
        for (Transaction currentItem : allTransactions) {
            System.out.println(currentItem.getDate() + " " +
                    currentItem.getTime() + " " +
                    currentItem.getDescription() + " " +
                    currentItem.getVendor() + " " +
                    currentItem.getAmount());
        }
    }


    public static void printDeposits() {

        System.out.println("\nDEPOSITS\n");

        for (Transaction deposit : allTransactions) {
            if (deposit.getAmount() > 0) {
                System.out.println(deposit.getDate() + " " +
                        deposit.getTime() + " " +
                        deposit.getDescription() + " " +
                        deposit.getVendor() + " " +
                        deposit.getAmount());
            }
        }
        MainApp.homeScreen();
    }


    public static void printPayments() {

        System.out.println("\nPAYMENTS\n");

        for (Transaction deposit : allTransactions) {
            if (deposit.getAmount() < 0) {
                System.out.println(deposit.getDate() + " " +
                        deposit.getTime() + " " +
                        deposit.getDescription() + " " +
                        deposit.getVendor() + " " +
                        deposit.getAmount());
            }
        }
        MainApp.homeScreen();
    }

    public static void printReports() {
        Scanner scanner = new Scanner(System.in);

        String report = """
                1) Month To Date
                2) Previous Month
                3) Year To Date
                4) Previous Year
                5) Search by Vendor
                0) Go Back
                """;
        System.out.println(report);
        // switch statement for all possible options in the main menu.
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

    public static void monthToDate() { // show all transactions from first of this month to current date

        LocalDate today = LocalDate.now();

        System.out.println("\nALL TRANSACTIONS OF THIS MONTH TO DATE\n");

        for (Transaction t : allTransactions) {
            LocalDate transactionDate = t.getDate();
            if (transactionDate.getMonth() == today.getMonth() && transactionDate.getYear() == today.getYear()) {
                System.out.println(t.getDate() + " " +
                        t.getTime() + " " +
                        t.getDescription() + " " +
                        t.getVendor() + " " +
                        t.getAmount()
                );
            }
        }
    }


    public static void previousMonth() {

        LocalDate today = LocalDate.now();
        int prevMonthValue = today.minusMonths(1).getMonthValue();
        System.out.println("TRANSACTIONS FROM THE PREVIOUS MONTH");
        for(Transaction t: allTransactions) {
            LocalDate transactionDate = t.getDate();
            if (transactionDate.getMonthValue() == prevMonthValue && transactionDate.getYear() == today.getYear()) {
                System.out.println(t.getDate() + " " +
                        t.getTime() + " " +
                        t.getDescription() + " " +
                        t.getVendor() + " " +
                        t.getAmount()
                );
            }
        }
    }

    public static void yearToDate() {
        LocalDate today = LocalDate.now();

        System.out.println("\nALL TRANSACTIONS OF THIS YEAR TO DATE\n");

        for (Transaction t : allTransactions) {
            LocalDate transactionDate = t.getDate();
            if (transactionDate.getYear() == today.getYear()) {
                System.out.println(t.getDate() + " " +
                        t.getTime() + " " +
                        t.getDescription() + " " +
                        t.getVendor() + " " +
                        t.getAmount()
                );
            }
        }
    }

    public static void previousYear() {
        LocalDate today = LocalDate.now();

        System.out.println("\nALL TRANSACTIONS OF LAST YEAR\n");

        for (Transaction t : allTransactions) {
            LocalDate transactionDate = t.getDate();
            if (transactionDate.getYear() == today.getYear()-1) {
                System.out.println(t.getDate() + " " +
                        t.getTime() + " " +
                        t.getDescription() + " " +
                        t.getVendor() + " " +
                        t.getAmount()
                );
            }
        }
    }

    public static void searchByVendor() {
        Scanner input = new Scanner(System.in);
        System.out.println("Which vendor transactions do you want to see?");
        String vendor = input.nextLine().toUpperCase();
        System.out.println("TRANSACTIONS FOR " + vendor);

        for (Transaction t: allTransactions) {
            if(t.getVendor().equalsIgnoreCase(vendor)) {
                System.out.println(t.getDate() + " " +
                        t.getTime() + " " +
                        t.getDescription() + " " +
                        t.getVendor() + " " +
                        t.getAmount());
            } else {
                System.out.println("NO TRANSACTIONS FOUND FOR " + vendor);
            }
        }
    }
}
