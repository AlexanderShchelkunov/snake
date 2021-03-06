package com.sandro.snake.controller;

import static com.sandro.snake.Constants.*;
import com.sandro.snake.model.FieldCell;
import com.sandro.snake.model.FieldContent;
import com.sandro.snake.model.Frog;
import com.sandro.snake.model.Snake;
import com.sandro.snake.model.SnakeBody;
import com.sandro.snake.model.exceptions.GameLostException;
import com.sandro.snake.view.ControlPanelView;
import com.sandro.snake.view.GameFieldView;
import com.sandro.snake.view.MainView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GameController {

    private SnakeController snakeController;
    private Map<Integer, FrogController> frogControllers;
    private InputController inputController;
    private ScheduledExecutorService executorService;
    private final ExecutorService gameExecutor = Executors.newSingleThreadExecutor();

    private int score = 0;
    private volatile boolean paused = false;

    private MainView mainView;
    private ControlPanelView controlPanelView;
    private GameFieldView gameFieldView;

    private FieldCell[][] cells;
    private Snake snake;
    private List<Frog> frogs;

    private Future<?> snakeFuture;

    public void init() {
        executorService = Executors.newScheduledThreadPool(1 + FROG_NUMBER);
        initViews();
        initModelAndControllers();
    }

    private void initViews() {
        controlPanelView = new ControlPanelView();
        controlPanelView.init(this::start, this::stop, this::pause);
        gameFieldView = new GameFieldView();
        gameFieldView.init();
        mainView = new MainView(controlPanelView, gameFieldView);
        mainView.init(this::shutdownExecutorServices);
    }

    private void initModelAndControllers() {
        createCells();
        createSnake();
        createFrogs();
        snakeController = new SnakeController(this, gameFieldView, snake, cells);
        inputController = new InputController(snakeController);
        inputController.bindKeys(mainView.getAppFrame());
        createFrogControllers();
    }

    public void startGame() {
        score = 0;
        paused = false;
        controlPanelView.updateScore(score);
        snakeFuture = executorService.submit(snakeController);
        frogControllers.values().stream().map(executorService::submit).collect(Collectors.toList());
        updateGameState();
    }

    private void updateGameState() {
        gameExecutor.submit(() -> {
            while (true) {
                try {
                    Thread.sleep(10);
                    if (paused) {
                        synchronized (this) {
                            this.wait();
                        }
                    }
                } catch (InterruptedException ex) {
                    shutdownExecutorService();
                    throw new RuntimeException("Finishing");
                }

                if (snakeFuture.isDone()) {
                    logSnakeException();
                    endGame();
                    return;
                }
            }
        });
    }

    private void endGame() {
        controlPanelView.disableStopButton();
        shutdownExecutorService();
    }

    private void logSnakeException() {
        try {
            snakeFuture.get();
        } catch (Exception e) {
            Throwable cause = e.getCause();
            if (cause != null) {
                if (cause instanceof GameLostException) {
                    System.out.println(cause.getMessage());
                } else {
                    cause.printStackTrace();
                }
            } else {
                e.printStackTrace();
            }
        }
    }

    private void respawnFrog() {
        List<Integer> possibleFrogCells = IntStream.range(SNAKE_INITIAL_SIZE, CELL_COUNT_HORIZONTALLY * CELL_COUNT_VERTICALLY - 1)
                .boxed()
                .collect(Collectors.toList());
        Collections.shuffle(possibleFrogCells);
        for (Integer cellNumber : possibleFrogCells) {
            int frogY = cellNumber / CELL_COUNT_HORIZONTALLY;
            int frogX = cellNumber - frogY * CELL_COUNT_HORIZONTALLY;
            FieldCell cell = cells[frogX][frogY];
            Lock lock = cell.getLock();
            boolean locked = lock.tryLock();
            if (locked) {
                try {
                    Frog frog = new Frog(Frog.FROG_MAX_ID++, frogX, frogY);
                    FrogController frogController = new FrogController(frog, cells, gameFieldView);
                    cell.setContent(frog);
                    addFrogController(frogController);
                    executorService.schedule(frogController, FROG_RESPAWN_DELAY, TimeUnit.SECONDS);
                    break;
                } finally {
                    lock.unlock();
                }
            }
        }
    }

    public void verifyLost(Snake snake, int newX, int newY) {
        if (newX < 0 || newY < 0 || newX >= CELL_COUNT_HORIZONTALLY || newY >= CELL_COUNT_VERTICALLY) {
            throw new GameLostException("Hit a border.");
        }

        List<SnakeBody> bodyParts = snake.getBody();
        if (bodyParts.size() > 2) {
            for (SnakeBody body : bodyParts) {
                if (body.getX() == snake.getX() && body.getY() == snake.getY()) {
                    throw new GameLostException("Hit a body.");
                }
            }
        }

        if (snake.getTail().getX() == snake.getX() && snake.getTail().getY() == snake.getY()) {
            throw new GameLostException("Hit the tail.");
        }
    }

    public void killFrog(Frog frog) {
        controlPanelView.updateScore(++score);
        FrogController frogController = frogControllers.remove(frog.getId());
        if (frogController != null) {
            frogController.setIsDead(true);
        } else {
            System.err.println("Controller for frog not found. Id = " + frog.getId());
        }

        respawnFrog();
    }

    private void addFrogController(FrogController frogController) {
        frogControllers.put(frogController.getId(), frogController);
    }

    private void createCells() {
        cells = new FieldCell[CELL_COUNT_HORIZONTALLY][CELL_COUNT_VERTICALLY];
        for (int i = 0; i < CELL_COUNT_HORIZONTALLY; i++) {
            for (int j = 0; j < CELL_COUNT_VERTICALLY; j++) {
                cells[i][j] = new FieldCell(i, j);
            }
        }
    }

    private void createSnake() {
        snake = new Snake(2, 3, 0);
        cells[0][0].setContentType(FieldContent.SNAKE);
        cells[0][1].setContentType(FieldContent.SNAKE);
        cells[0][2].setContentType(FieldContent.SNAKE);
        cells[0][3].setContentType(FieldContent.SNAKE);
        gameFieldView.drawSnake(snake);
    }

    private void createFrogs() {
        frogs = new ArrayList<>(FROG_NUMBER);
        List<Integer> possibleCells = IntStream.range(SNAKE_INITIAL_SIZE, CELL_COUNT_HORIZONTALLY * CELL_COUNT_VERTICALLY - 1)
                .boxed()
                .collect(Collectors.toList());
        Collections.shuffle(possibleCells);
        for (int i = 0; i < FROG_NUMBER; i++) {
            int cellNumber = possibleCells.get(i);
            int frogY = cellNumber / CELL_COUNT_HORIZONTALLY;
            int frogX = cellNumber - frogY * CELL_COUNT_HORIZONTALLY;
            Frog frog = new Frog(Frog.FROG_MAX_ID++, frogX, frogY);
            frogs.add(frog);
            cells[frogX][frogY].setContent(frog);
        }

        gameFieldView.drawFrogs(frogs);
    }

    private void createFrogControllers() {
        List<FrogController> controllers = frogs.stream()
                .map(frog -> new FrogController(frog, cells, gameFieldView))
                .collect(Collectors.toList());
        frogControllers = controllers.stream().collect(Collectors.toMap(FrogController::getId, Function.identity()));
    }

    private void shutdownExecutorService() {
        executorService.shutdownNow();
    }

    private void shutdownExecutorServices() {
        executorService.shutdownNow();
        gameExecutor.shutdownNow();
    }

    private void stop() {
        if (paused) {
            unPause();
        }

        endGame();
    }

    private void start() {
        if (paused) {
            unPause();
        } else {
            restartGame();
        }
    }

    private void unPause() {
        paused = false;
        synchronized (this) {
            this.notifyAll();
        }

        snakeController.upPause();
        frogControllers.values().forEach(Pausable::upPause);
    }

    private void restartGame() {
        executorService = Executors.newScheduledThreadPool(1 + FROG_NUMBER);
        gameFieldView.clear();
        initModelAndControllers();
        startGame();
    }

    private void pause() {
        snakeController.pause();
        frogControllers.values().forEach(Pausable::pause);
        paused = true;
    }
}
