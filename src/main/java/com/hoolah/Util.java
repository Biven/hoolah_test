package com.hoolah;

import com.hoolah.exception.ParseException;
import com.hoolah.model.TransactionType;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Yauheni Zubovich
 */
public class Util {

    public static final String DATE_FORMAT = "dd/MM/yyyy HH:mm:ss";

    public static Date parseDate(String date) throws ParseException {
        try {
            return new SimpleDateFormat(DATE_FORMAT).parse(date);
        } catch (java.text.ParseException e) {
            throw new ParseException("Failure parsing date", e);
        }
    }

    public static float parseFloat(String val) throws ParseException {
        try {
            return Float.parseFloat(val);
        } catch (NumberFormatException ex) {
            throw new ParseException("Failure parsing amount", ex);
        }
    }

    public static TransactionType parseType(String type) throws ParseException {
        for (TransactionType tt : TransactionType.values()) {
            if (tt.name().equalsIgnoreCase(type)) {
                return tt;
            }
        }
        throw new ParseException("Failure parsing type " + type);
    }
}
