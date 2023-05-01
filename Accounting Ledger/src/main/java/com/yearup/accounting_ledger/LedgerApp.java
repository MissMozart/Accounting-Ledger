package com.yearup.accounting_ledger;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LedgerApp {

    public static void main(String[] args) {
        HomeScreen();
    }

    private static void loadTransaction() {

    }

    public static void HomeScreen() {
        Scanner scanner = new Scanner(System.in);
        boolean screenDone = false;
        while (!screenDone) {
            String heading = """
                    Welcome! Here is our main menu:
                    D) Add Deposit
                    P) Make Payment (Debit)
                    L) Ledger
                    X) Exit
                    """;
            System.out.println(heading);

            // switch statement for all possible options in the main menu.
            String input = scanner.nextLine();
            switch (input.toUpperCase()) {
                case "D":
                    addDeposit();
                    break;
                case "P":
                    makePayment();
                    break;
                case "L":
                    showLedger();
                    break;
                case "X":
                    screenDone = true;
                default:
                    badInput();

            }
        }
    }

    public static void addDeposit() {
        try {
            FileWriter writer = new FileWriter("transactions.csv", true);
            writer.write("transactions.csv");
            writer.close();
            System.out.println("Deposit added successfully.");
        } catch (IOException e) {
            System.out.println("Failed to add deposit. Please try again.");
        }
    }

    public static void showLedger() {
        String ledger = """
                A) All
                D) Deposit
                P) Payments
                R) Reports
                """;
    }

    public static void makePayment() {

    }

    private static void badInput() {
        System.out.println("I didn't understand. Please try again.");
    }
}

