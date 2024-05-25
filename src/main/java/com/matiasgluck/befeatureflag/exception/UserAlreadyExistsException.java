package com.matiasgluck.befeatureflag.exception;

import org.hibernate.HibernateException;

public class UserAlreadyExistsException extends HibernateException {
    public UserAlreadyExistsException(String message) {
        super(message);
    }

    public String getMessage() {
        return "User already exists: " + super.getMessage();
    }
}
