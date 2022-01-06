package com.sandro.snake.model;

import static com.sandro.snake.Constants.*;
import java.util.ArrayList;
import java.util.List;

public class Snake {

    private final SnakeHead head = new SnakeHead();
    private final SnakeTail tail = new SnakeTail();
    private final List<SnakeBody> body = new ArrayList<>();

    public Snake(int bodySize, int x, int y) {
        head.setX(x);
        head.setY(y);
        int adjustedSize = bodySize < MIN_BODY_SIZE ? MIN_BODY_SIZE : bodySize;
        int currentCoord = 1;
        for (int i = 0; i < adjustedSize; i++) {
            body.add(new SnakeBody(x - currentCoord, y));
            currentCoord++;
        }

        tail.setX(x - currentCoord);
        tail.setY(y);
    }

    public SnakeHead getHead() {
        return head;
    }

    public SnakeTail getTail() {
        return tail;
    }

    public List<SnakeBody> getBody() {
        return body;
    }

    public int getX() {
        return head.getX();
    }

    public int getY() {
        return head.getY();
    }

    public void setX(int x) {
        head.setX(x);
    }

    public void setY(int y) {
        head.setY(y);
    }

    public void updatePosition(int newX, int newY, boolean addBodySegment) {
        int previousHeadX = head.getX();
        int previousHeadY = head.getY();
        head.setX(newX);
        head.setY(newY);

        if (!addBodySegment) {
            SnakeBody lastSegment = body.remove(body.size() - 1);
            int previousLastSegmentX = lastSegment.getX();
            int previousLastSegmentY = lastSegment.getY();
            lastSegment.setX(previousHeadX);
            lastSegment.setY(previousHeadY);
            body.add(0, lastSegment);
            tail.setX(previousLastSegmentX);
            tail.setY(previousLastSegmentY);
        } else {
            SnakeBody newBodySegment = new SnakeBody(previousHeadX, previousHeadY);
            body.add(0, newBodySegment);
        }
    }
}
