package com.sandro.snake.view;

import static com.sandro.snake.Constants.GAME_FIELD_HEIGHT;
import static com.sandro.snake.Constants.GAME_FIELD_WIDTH;
import com.sandro.snake.model.Frog;
import com.sandro.snake.model.Snake;
import java.awt.Dimension;
import java.util.List;

public class GameFieldView {

    private FieldPanel fieldPanel;

    public FieldPanel init() {
        fieldPanel = new FieldPanel();
        fieldPanel.setPreferredSize(new Dimension(GAME_FIELD_WIDTH, GAME_FIELD_HEIGHT));
        return fieldPanel;
    }

    public void reDrawSnake(Snake snake) {
        fieldPanel.reDrawSnake(snake);
    }

    public void drawFrogs(List<Frog> frogs) {
        fieldPanel.drawFrogs(frogs);
    }

    public void reDrawFrog(Frog frog) {
        fieldPanel.reDrawFrog(frog);
    }

    public void drawSnake(Snake snake) {
        fieldPanel.drawSnake(snake);
    }

    public void clear() {
        fieldPanel.clear();
    }
}
