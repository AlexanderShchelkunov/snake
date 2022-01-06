package com.sandro.snake.controller;

import static com.sandro.snake.Constants.*;
import com.sandro.snake.model.FieldCell;
import com.sandro.snake.model.FieldContent;
import com.sandro.snake.model.Frog;
import com.sandro.snake.model.exceptions.FrogIsDeadException;
import com.sandro.snake.view.GameFieldView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

public class FrogController extends Pausable {

    private final Frog frog;
    private final FieldCell[][] cells;
    private final GameFieldView field;
    private volatile boolean isDead = false;

    public FrogController(Frog frog, FieldCell[][] cells, GameFieldView field) {
        this.frog = frog;
        this.cells = cells;
        this.field = field;
    }

    @Override
    public void run() {
        while (true) {
            if (Thread.currentThread().isInterrupted()) {
                throw new RuntimeException("The game was interrupted");
            }

            pauseIfNeeded();
            checkIfDead();

            try {
                Thread.sleep(FROG_SLEEP_TIME);
            } catch (InterruptedException ex) {
                throw new RuntimeException("The game was interrupted");
            }

            checkIfDead();
            moveFrog();
        }
    }

    private void checkIfDead() throws RuntimeException {
        if (isDead) {
            throw new FrogIsDeadException("Frog is dead FeelsBadMan");
        }
    }

    public int getId() {
        return frog.getId();
    }

    private void moveFrog() {
        FieldCell currentFrogCell = cells[frog.getX()][frog.getY()];
        currentFrogCell.getLock().lock();
        try {
            List<FieldCell> cellsToMove = getCellsToMove();
            Collections.shuffle(cellsToMove);
            boolean moved = false;
            for (FieldCell cell : cellsToMove) {
                Lock lock = cell.getLock();
                boolean locked = lock.tryLock(FROG_LOCK_WAIT_TIME, TimeUnit.MILLISECONDS);
                if (locked) {
                    try {
                        if (cell.getContentType() == FieldContent.EMPTY) {
                            frog.setX(cell.getX());
                            frog.setY(cell.getY());
                            currentFrogCell.setContentType(FieldContent.EMPTY);
                            cell.setContent(frog);
                            field.reDrawFrog(frog);
                            moved = true;
                            break;
                        } else {
//                        System.out.println("Cannot move to cell " + cell + " because it has " + cell.getContent());
                        }
                    } finally {
                        lock.unlock();
                    }
                } else {
                    System.err.println("Frog cannot get lock: " + frog + " cell " + cell);
                }
            }

            if (!moved) {
                System.err.println("Frog was not moved! " + frog);
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        } finally {
            currentFrogCell.getLock().unlock();
        }
    }

    public boolean isIsDead() {
        return isDead;
    }

    public void setIsDead(boolean isDead) {
        this.isDead = isDead;
    }

    private List<FieldCell> getCellsToMove() {
        List<FieldCell> cellsToMove = new ArrayList<>();
        int x = frog.getX();
        int y = frog.getY();
        if (y - 1 >= 0) {
            cellsToMove.add(cells[x][y - 1]);
        }

        if (y + 1 < CELL_COUNT_VERTICALLY) {
            cellsToMove.add(cells[x][y + 1]);
        }

        if (x - 1 >= 0) {
            cellsToMove.add(cells[x - 1][y]);
        }

        if (x + 1 < CELL_COUNT_HORIZONTALLY) {
            cellsToMove.add(cells[x + 1][y]);
        }

        return cellsToMove;
    }
}
