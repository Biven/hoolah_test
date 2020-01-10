package com.hoolah;

import com.hoolah.model.Report;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * @author Yauheni Zubovich
 */
public class TransactionReporterTest {

    private TransactionReporter transactionReporter = new TransactionReporter();

    @Test
    public void testReportKwikEMart() throws IOException {
        Report report = transactionReporter.buildReport(this.getClass().getResourceAsStream("/hoolah.csv"),
                "Kwik-E-Mart",
                "20/08/2018 12:00:00",
                "20/08/2018 13:00:00");
        assertEquals(1, report.getTransactionsCount());
        assertEquals(59.99, report.getAverageValue(), 0.00001);
    }

    @Test
    public void testReportKwikEMart14() throws IOException {
        Report report = transactionReporter.buildReport(this.getClass().getResourceAsStream("/hoolah.csv"),
                "Kwik-E-Mart",
                "20/08/2018 12:00:00",
                "20/08/2018 14:00:00");
        assertEquals(2, report.getTransactionsCount());
        assertEquals(32.495, report.getAverageValue(), 0.00001);
    }

    @Test
    public void testReportMacLaren() throws IOException {
        Report report = transactionReporter.buildReport(this.getClass().getResourceAsStream("/hoolah.csv"),
                "MacLaren",
                "20/08/2018 12:00:00",
                "20/08/2018 15:00:00");
        assertEquals(2, report.getTransactionsCount());
        assertEquals(52.25, report.getAverageValue(), 0.00001);
    }

    @Test
    public void testEmptyReport() throws IOException {
        Report report = transactionReporter.buildReport(this.getClass().getResourceAsStream("/hoolah.csv"),
                "MacLaren",
                "20/08/2019 12:00:00",
                "20/08/2019 15:00:00");
        assertEquals(0, report.getTransactionsCount());
        assertEquals(0, report.getAverageValue(), 0.00001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWrongDateFormat() throws IOException {
        transactionReporter.buildReport(this.getClass().getResourceAsStream("/hoolah.csv"),
                "Kwik-E-Mart",
                "20-08-2018 12:00:00",
                "20-08-2018 14:00:00");
    }
}
