package com.sandro.snake.model.exceptions;

public class FrogIsDeadException extends RuntimeException {

    public FrogIsDeadException() {
    }

    public FrogIsDeadException(String message) {
        super(message);
    }

    public FrogIsDeadException(String message, Throwable cause) {
        super(message, cause);
    }

    public FrogIsDeadException(Throwable cause) {
        super(cause);
    }

}
