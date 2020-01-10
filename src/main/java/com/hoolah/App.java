package com.hoolah;

import com.hoolah.model.Report;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * Hello world!
 */
public class App {

    public static void main(String[] args) throws IOException {
        String csvPath = System.console().readLine("Enter csv path:");
        String fromDate = System.console().readLine("Enter from date(format: " + Util.DATE_FORMAT + "):");
        String toDate = System.console().readLine("Enter to date(format: " + Util.DATE_FORMAT + "):");
        String merchant = System.console().readLine("Enter merchant:");

        TransactionReporter transactionReporter = new TransactionReporter();
        try (FileInputStream fileInputStream = new FileInputStream(csvPath)) {
            Report report = transactionReporter.buildReport(fileInputStream, merchant, fromDate, toDate);
            System.out.println("\nNumber of transactions = " + report.getTransactionsCount());
            System.out.printf("Average Transaction Value %.2f ", report.getAverageValue());
        }
    }
}
