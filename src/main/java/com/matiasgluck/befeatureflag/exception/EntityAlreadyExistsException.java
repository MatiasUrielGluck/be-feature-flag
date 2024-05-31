package com.matiasgluck.befeatureflag.exception;

import org.hibernate.HibernateException;

public class EntityAlreadyExistsException extends HibernateException {
    public EntityAlreadyExistsException(String message) {
        super(message);
    }

    public String getMessage() {
        return super.getMessage();
    }
}
