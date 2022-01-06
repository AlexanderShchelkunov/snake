package com.sandro.snake.controller;

import static com.sandro.snake.Constants.*;
import com.sandro.snake.model.Direction;
import com.sandro.snake.model.FieldCell;
import com.sandro.snake.model.FieldContent;
import com.sandro.snake.model.Frog;
import com.sandro.snake.model.Snake;
import com.sandro.snake.view.GameFieldView;
import java.util.concurrent.locks.Lock;

public class SnakeController extends Pausable {

    private Direction currentDirection = Direction.RIGHT;
    private Direction nextDirection = Direction.RIGHT;
    private final Snake snake;
    private final GameController gameController;
    private final FieldCell[][] cells;
    private final GameFieldView gameFieldView;

    public SnakeController(GameController gameController, GameFieldView gameFieldView, Snake snake, FieldCell[][] cells) {
        this.gameController = gameController;
        this.gameFieldView = gameFieldView;
        this.snake = snake;
        this.cells = cells;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(SNAKE_SLEEP_TIME);
            } catch (InterruptedException ex) {
                throw new RuntimeException("The snake controller was interrupted");
            }

            pauseIfNeeded();
            moveSnake();
        }
    }

    public Snake getSnake() {
        return snake;
    }

    private void moveSnake() {
        if (nextDirection != currentDirection) {
            if (isAllowedToChangeDirection(nextDirection)) {
                currentDirection = nextDirection;
            }
        }

        int newX = getNewX();
        int newY = getNewY();
        gameController.verifyLost(snake, newX, newY);
        FieldCell newHeadCell = cells[newX][newY];
        Lock lock = newHeadCell.getLock();
        try {
            lock.lock();
            boolean hitFrog = newHeadCell.getContentType() == FieldContent.FROG;
            snake.updatePosition(newX, newY, hitFrog);
            if (hitFrog) {
                gameController.killFrog((Frog) newHeadCell.getContent());
            } else {
                cells[snake.getTail().getLastX()][snake.getTail().getLastY()].setContentType(FieldContent.EMPTY);
            }

            cells[snake.getX()][snake.getY()].setContentType(FieldContent.SNAKE);
            gameFieldView.reDrawSnake(snake);
        } finally {
            lock.unlock();
        }
    }

    private int getNewX() {
        switch (currentDirection) {
            case RIGHT: {
                return snake.getX() + 1;
            }
            case LEFT: {
                return snake.getX() - 1;
            }
            default: {
                return snake.getX();
            }
        }
    }

    private int getNewY() {
        switch (currentDirection) {
            case UP: {
                return snake.getY() - 1;
            }

            case DOWN: {
                return snake.getY() + 1;
            }
            default: {
                return snake.getY();
            }
        }
    }

    public void setMovementDirection(Direction direction) {
        nextDirection = direction;
    }

    private boolean isAllowedToChangeDirection(Direction direction) {
        if (currentDirection == Direction.DOWN && direction != Direction.UP) {
            return true;
        }

        if (currentDirection == Direction.UP && direction != Direction.DOWN) {
            return true;
        }

        if (currentDirection == Direction.RIGHT && direction != Direction.LEFT) {
            return true;
        }

        if (currentDirection == Direction.LEFT && direction != Direction.RIGHT) {
            return true;
        }

        return false;
    }
}
