package com.hoolah;

import com.hoolah.exception.ParseException;
import com.hoolah.model.Report;
import com.hoolah.model.Transaction;
import com.hoolah.model.TransactionType;
import com.opencsv.CSVReaderHeaderAware;
import com.opencsv.CSVReaderHeaderAwareBuilder;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.hoolah.Util.*;

/**
 * @author Yauheni Zubovich
 */
public class TransactionReporter {

    private static final String ID = "ID";
    private static final String DATE = "Date";
    private static final String AMOUNT = "Amount";
    private static final String MERCHANT = "Merchant";
    private static final String TYPE = "Type";
    private static final String RELATED_TRANSACTION = "Related Transaction";

    private String merchant;
    private Date fromDate;
    private Date toDate;

    public Report buildReport(InputStream csvInputStream, String merchant, String fromDate, String toDate) throws IOException {
        validateInput(merchant, fromDate, toDate);

        InputStreamReader reader = new InputStreamReader(csvInputStream);
        Map<String, String> line;
        try (CSVReaderHeaderAware aware = new CSVReaderHeaderAwareBuilder(reader).build()) {
            List<Transaction> merchantTransactions = new ArrayList<>();
            while ((line = aware.readMap()) != null) {
                Transaction transaction = parse(line);
                if (isMerchantReportTransaction(transaction)) {
                    merchantTransactions.add(transaction);
                } else if (isMerchantReversalTransaction(transaction)) {
                    merchantTransactions = merchantTransactions.stream()
                            .filter(tr -> !tr.getId().equals(transaction.getRelatedTransaction()))
                            .collect(Collectors.toList());
                }
            }
            return new Report(merchantTransactions.size(), merchantTransactions.stream().mapToDouble(Transaction::getAmount).average().orElse(0));
        }
    }

    private void validateInput(String merchant, String fromDate, String toDate) {
        if (StringUtils.isBlank(merchant)) {
            throw new IllegalArgumentException("Merchant should be non empty");
        }
        this.merchant = merchant;
        try {
            this.fromDate = parseDate(fromDate);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid fromDate value. Format should be " + DATE_FORMAT);
        }
        try {
            this.toDate = parseDate(toDate);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid toDate value. Format should be " + DATE_FORMAT);
        }
    }

    private Transaction parse(Map<String, String> line) throws ParseException {
        return new Transaction()
                .withId(line.get(ID))
                .withDate(parseDate(line.get(DATE)))
                .withAmount(parseFloat(line.get(AMOUNT)))
                .withMerchant(line.get(MERCHANT))
                .withType(parseType(line.get(TYPE)))
                .withRelatedTransaction(line.get(RELATED_TRANSACTION));
    }

    /**
     * Check if transaction is owned by merchant what we need AND transaction is in needed date range AND transaction is not REVERSAL.
     * By other words it checks should we include transaction to report or not
     */
    private boolean isMerchantReportTransaction(Transaction transaction) {
        return merchant.equals(transaction.getMerchant())
                && transaction.getType() == TransactionType.PAYMENT
                && transaction.getDate().after(fromDate)
                && transaction.getDate().before(toDate);
    }

    /**
     * Check if transaction is merchant REVERSAL transaction
     */
    private boolean isMerchantReversalTransaction(Transaction transaction) {
        return transaction.getType() == TransactionType.REVERSAL && merchant.equals(transaction.getMerchant());
    }

}
