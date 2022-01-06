package com.sandro.snake.controller;

import com.sandro.snake.view.InputAction;
import com.sandro.snake.model.Direction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.KeyStroke;

public class InputController {

    private static final String UP_ACTION = "UP";
    private static final String DOWN_ACTION = "DOWN";
    private static final String LEFT_ACTION = "LEFT";
    private static final String RIGHT_ACTION = "RIGHT";

    private final SnakeController snakeController;

    public InputController(SnakeController snakeController) {
        this.snakeController = snakeController;
    }

    public void bindKeys(JFrame frame) {
        InputMap inputMap = frame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        inputMap.put(KeyStroke.getKeyStroke(UP_ACTION), UP_ACTION);
        inputMap.put(KeyStroke.getKeyStroke("w"), UP_ACTION);
        inputMap.put(KeyStroke.getKeyStroke(DOWN_ACTION), DOWN_ACTION);
        inputMap.put(KeyStroke.getKeyStroke("s"), DOWN_ACTION);
        inputMap.put(KeyStroke.getKeyStroke(LEFT_ACTION), LEFT_ACTION);
        inputMap.put(KeyStroke.getKeyStroke("a"), LEFT_ACTION);
        inputMap.put(KeyStroke.getKeyStroke(RIGHT_ACTION), RIGHT_ACTION);
        inputMap.put(KeyStroke.getKeyStroke("d"), RIGHT_ACTION);

        ActionMap actionMap = frame.getRootPane().getActionMap();
        actionMap.put(UP_ACTION, (InputAction) (e) -> snakeController.setMovementDirection(Direction.UP));
        actionMap.put(DOWN_ACTION, (InputAction) (e) -> snakeController.setMovementDirection(Direction.DOWN));
        actionMap.put(LEFT_ACTION, (InputAction) (e) -> snakeController.setMovementDirection(Direction.LEFT));
        actionMap.put(RIGHT_ACTION, (InputAction) (e) -> snakeController.setMovementDirection(Direction.RIGHT));
    }
}
