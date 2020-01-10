package com.hoolah.exception;

import java.io.IOException;

/**
 * @author Yauheni Zubovich
 */
public class ParseException extends IOException {

    public ParseException(String message) {
        super(message);
    }

    public ParseException(String message, Throwable cause) {
        super(message, cause);
    }
}
