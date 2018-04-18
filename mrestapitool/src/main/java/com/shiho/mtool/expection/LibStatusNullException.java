package com.shiho.mtool.expection;

import java.io.IOException;

public class LibStatusNullException extends IOException {

    private String errorMsg;

    public LibStatusNullException(String message) {
        super(message);
        errorMsg = message;
    }
}
