package com.matiasgluck.befeatureflag.exception;

import org.hibernate.HibernateException;

public class JwtFilterException extends HibernateException {
    public JwtFilterException(String message) {
        super(message);
    }

    public String getMessage() {
        return "Invalid token: " + super.getMessage();
    }
}
