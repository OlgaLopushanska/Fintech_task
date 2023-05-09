package com.fintech.task.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class DateNotCorrectFormatException extends RuntimeException {
    private String resourceName;
    private String fieldName;
    private String fieldValue;

    public DateNotCorrectFormatException(String resourceName, String fieldName, String fieldValue) {
        super(String.format("%s not found with %s : %s. Specify correct date format like dd.MM.yyyy", resourceName, fieldName,fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
}
