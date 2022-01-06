package com.sandro.snake.model;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FieldCell extends WithCoordinates {

    private FieldContent contentType = FieldContent.EMPTY;
    private WithCoordinates content;
    private final Lock lock = new ReentrantLock();

    public FieldCell() {
    }

    public FieldCell(int x, int y) {
        super(x, y);
    }

    public void setContent(WithCoordinates content) {
        this.content = content;
        if (content == null) {
            contentType = FieldContent.EMPTY;
        } else if (content instanceof SnakeBody || content instanceof SnakeHead || content instanceof SnakeTail) {
            contentType = FieldContent.SNAKE;
        } else if (content instanceof Frog) {
            contentType = FieldContent.FROG;
        } else {
            throw new IllegalArgumentException("Unknown content type o_0");
        }
    }

    public WithCoordinates getContent() {
        return content;
    }

    public FieldContent getContentType() {
        return contentType;
    }

    public void setContentType(FieldContent contentType) {
        this.contentType = contentType;
    }

    public Lock getLock() {
        return lock;
    }
}
