package com.yearup.accounting_ledger;

import java.io.*;

public class LedgerApp {
    public static void main(String[] args) {

        try {
            FileReader productReader = new FileReader("transaction.csv");
            BufferedReader pReader = new BufferedReader(productReader);
            FileWriter productWriter = new FileWriter("transaction.csv");
            BufferedWriter pWriter = new BufferedWriter(productWriter);
            PrintWriter pPrint = new PrintWriter(pWriter);
            String pInput;
            pPrint.println("Inventory: ");


        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    }

}