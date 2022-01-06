package com.sandro.snake;

public class Constants {

    public static final int CELL_SIZE = 16;
    public static final int GAME_FIELD_WIDTH = 50 * CELL_SIZE;
    public static final int GAME_FIELD_HEIGHT = GAME_FIELD_WIDTH;

    public static final int CONTROL_PANEL_HEIGHT = 40;
    public static final int CONTROL_PANEL_WIDTH = GAME_FIELD_WIDTH;
    public static final int HALF_CELL = CELL_SIZE / 2;

    public static final int CELL_COUNT_HORIZONTALLY = GAME_FIELD_HEIGHT / CELL_SIZE;
    public static final int CELL_COUNT_VERTICALLY = GAME_FIELD_HEIGHT / CELL_SIZE;

    public static final int SNAKE_INITIAL_SIZE = 4;
    public static final int HEAD_SIZE = CELL_SIZE;
    public static final int HEAD_COORD_ADJUSTMENT = HALF_CELL - HEAD_SIZE / 2;

    public static final int BODY_SIZE = CELL_SIZE * 2 / 3;
    public static final int BODY_COORD_ADJUSTMENT = HALF_CELL - BODY_SIZE / 2;

    public static final int TAIL_SIZE = CELL_SIZE / 2;
    public static final int TAIL_COORD_ADJUSTMENT = HALF_CELL - TAIL_SIZE / 2;

    public static final int FROG_SIZE = CELL_SIZE * 2 / 3;
    public static final int FROG_COORD_ADJUSTMENT = HALF_CELL - FROG_SIZE / 2;
    public static final int FROG_NUMBER = 200;

    public static final int MIN_BODY_SIZE = 2;

    public static final long SNAKE_SLEEP_TIME = 250;
    public static final long FROG_SLEEP_TIME = SNAKE_SLEEP_TIME * 2;
    public static final long FROG_LOCK_WAIT_TIME = 10;
}
