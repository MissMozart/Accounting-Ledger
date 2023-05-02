package com.yearup.accounting_ledger;

import java.io.*;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalTime;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Comparator;

public class Ledger {

    public static ArrayList<Transaction> allTransactions = getTransaction();

    public static ArrayList<Transaction> getTransaction() {
        ArrayList<Transaction> allTransactions = new ArrayList<>();
        // this method loads transaction objects into transactions array
        try {
            FileReader fileReader = new FileReader("transactions.csv");
            BufferedReader bufReader = new BufferedReader(fileReader);

            String input;
            while ((input = bufReader.readLine()) != null) {
                String[] details = input.split("\\|");
                LocalDate date = LocalDate.parse(details[0]);
                LocalTime time = LocalTime.parse(details[1]);
                String description = details[2];
                String vendor = details[3];
                double amount = Double.parseDouble(details[4]);

                Transaction transaction = new Transaction(date, time, description, vendor, amount);
                allTransactions.add(transaction);
            }
        } catch (IOException e) {
            System.out.println("File not found!");
            System.exit(0);
        }
        return allTransactions;
    }

    public static void showLedger() {
        Scanner scanner = new Scanner(System.in);
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
            case "D":
                printDeposits();
            case "P":
                printPayments();
            case "R":
                printReports();
            case "H":
                MainApp.homeScreen();
            default:
                System.out.println("That didn't work. Please try again.");

        }
    }

    //
    public static void printAll() {
        // sort the list in reverse order
        Collections.sort(allTransactions, new Comparator<Transaction>() {
            public int compare(Transaction t1, Transaction t2) {
                return t2.getDate().compareTo(t1.getDate());
            }
        });
        LocalDate currently = LocalDate.now();
        int start = 0;
        int end = 1;
        for (int i = 0; i < allTransactions.size(); i++) {

            if (allTransactions.get(i).getDate().compareTo(currently) != 0) { // date has changed
                List<Transaction> sub =
                        allTransactions.subList(start, end); // this is the previous sublist when the date changes
                Collections.sort(sub, new Comparator<Transaction>() {
                    public int compare(Transaction t1, Transaction t2) {
                        return t2.getTime().compareTo(t1.getTime());
                    }
                });
                start = i;
                end = i;
            } else {
                end = i;
                continue;
            }
        }


        for (Transaction currentItem : allTransactions) {
            System.out.println(currentItem.getDate() + " " +
                    currentItem.getTime() + " " +
                    currentItem.getDescription() + " " +
                    currentItem.getVendor() + " " +
                    currentItem.getAmount());
        }
    }


    public static void printDeposits() {
        Collections.sort(allTransactions, new Comparator<Transaction>() {
            public int compare(Transaction t1, Transaction t2) {
                return t2.getDate().compareTo(t1.getDate());
            }
        });

        LocalDate currently = LocalDate.now();
        int start = 0;
        int end = 1;
        for (int i = 0; i < allTransactions.size(); i++) {

            if (allTransactions.get(i).getDate().compareTo(currently) != 0) { // date has changed
                List<Transaction> sub =
                        allTransactions.subList(start, end); // this is the previous sublist when the date changes
                Collections.sort(sub, new Comparator<Transaction>() {
                    public int compare(Transaction t1, Transaction t2) {
                        return t2.getTime().compareTo(t1.getTime());
                    }
                });
                start = i;
                end = i;
            } else {
                end = i;
                continue;
            }
        }

        for (Transaction deposit : allTransactions) {
            if (deposit.getAmount() > 0) {
                System.out.println(deposit.getDate() + " " +
                        deposit.getTime() + " " +
                        deposit.getDescription() + " " +
                        deposit.getVendor() + " " +
                        deposit.getAmount());
            }
        }
    }

    public static void printPayments() {

    }

    public static void printReports() {

    }
}