package com.sandro.snake.model.exceptions;

public class GameLostException extends RuntimeException {

    public GameLostException() {
    }

    public GameLostException(String message) {
        super(message);
    }

    public GameLostException(String message, Throwable cause) {
        super(message, cause);
    }

    public GameLostException(Throwable cause) {
        super(cause);
    }
}
