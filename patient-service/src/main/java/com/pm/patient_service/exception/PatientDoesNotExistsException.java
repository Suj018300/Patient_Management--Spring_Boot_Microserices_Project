package com.pm.patient_service.exception;

public class PatientDoesNotExistsException extends RuntimeException {
    public PatientDoesNotExistsException(String message) {
        super(message);
    }
}
