package ru.mail.migus_nikta.exception;

public class ConstructorNotFoundException extends RuntimeException {

    public ConstructorNotFoundException() {
        super("ConstructorNotFoundException");
    }

}
