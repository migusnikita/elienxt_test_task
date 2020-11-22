package ru.mail.migus_nikta.exception;

public class TooManyConstructorsException extends RuntimeException {

    public TooManyConstructorsException() {
        super("TooManyConstructorsException");
    }

}
