package com.jeromepenaflor.mybank.springboot.dto;

import java.util.List;

public class ErrorDto {
    private String message;
    private List<String> invalidFields;

    public ErrorDto(String message, List<String> invalidFields) {
        this.message = message;
        this.invalidFields = invalidFields;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getInvalidFields() {
        return invalidFields;
    }
    public void setInvalidFields(String invalidFields) {
        this.invalidFields.add(invalidFields);
    }
}
