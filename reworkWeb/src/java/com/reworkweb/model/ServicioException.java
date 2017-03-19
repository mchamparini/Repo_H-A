package com.reworkweb.model;

public class ServicioException extends Exception {

    public ServicioException(String message) {
        super(message);
    }

    public ServicioException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
