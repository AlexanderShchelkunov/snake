package com.sandro.snake;

import com.sandro.snake.controller.GameController;

public class App {

    public static void main(String[] args) throws Exception {
        GameController gameController = new GameController();
        gameController.init();
        gameController.startGame();
    }
}
