package com.egen.northwind.advice;

import com.egen.northwind.constants.ErrorConstants;

public class DuplicateException extends BadRequestAlertException {
    public DuplicateException() {
        super(ErrorConstants.EMAIL_ALREADY_USED_TYPE, "Duplicate Data Exception", "duplicate", "duplicateExists");
    }
}