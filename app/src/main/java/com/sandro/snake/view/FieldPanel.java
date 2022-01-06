package com.sandro.snake.view;

import static com.sandro.snake.Constants.*;
import com.sandro.snake.model.Frog;
import com.sandro.snake.model.Snake;
import com.sandro.snake.model.SnakeBody;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.List;
import javax.swing.JPanel;

public class FieldPanel extends JPanel {

    private final BufferedImage image;
    private final Graphics2D graphics;

    public FieldPanel() {
        image = new BufferedImage(GAME_FIELD_WIDTH, GAME_FIELD_HEIGHT, BufferedImage.TYPE_INT_RGB);
        graphics = image.createGraphics();
        drawBorders();
    }

    private void drawBorders() {
        drawHorizontalBorders();
        drawVericalBorders();
        repaint();
    }

    public void clear() {
        graphics.setColor(Color.BLACK);
        graphics.fillRect(0, 0, GAME_FIELD_WIDTH, GAME_FIELD_HEIGHT);
        drawBorders();
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        graphics.drawImage(image, 0, 0, this);
    }

    public void drawSnake(Snake snake) {
        drawHead(snake.getX(), snake.getY());
        for (SnakeBody bodySegment : snake.getBody()) {
            drawBody(bodySegment.getX(), bodySegment.getY());
        }

        drawTail(snake.getTail().getX(), snake.getTail().getY());
    }

    public synchronized void reDrawSnake(Snake snake) {
        clearCell(snake.getTail().getLastX(), snake.getTail().getLastY());
        List<SnakeBody> body = snake.getBody();
        SnakeBody firstBodySegment = body.get(0);
        clearCell(firstBodySegment.getLastX(), firstBodySegment.getLastY());
        clearCell(snake.getHead().getLastX(), snake.getHead().getLastY());

        drawHead(snake.getX(), snake.getY());
        for (SnakeBody bodySegment : body) {
            drawBody(bodySegment.getX(), bodySegment.getY());
        }

        drawTail(snake.getTail().getX(), snake.getTail().getY());
        repaint();
    }

    public void drawFrogs(List<Frog> frogs) {
        for (Frog frog : frogs) {
            drawFrog(frog.getX(), frog.getY());
        }
    }

    public synchronized void reDrawFrog(Frog frog) {
        clearCell(frog.getLastX(), frog.getLastY());
        drawFrog(frog.getX(), frog.getY());
        repaint();
    }

    private void drawFrog(int xCellNumber, int yCellNumber) {
        graphics.setColor(Color.GREEN);
        graphics.fillOval(
                xCellNumber * CELL_SIZE + FROG_COORD_ADJUSTMENT,
                0 + yCellNumber * CELL_SIZE + FROG_COORD_ADJUSTMENT,
                FROG_SIZE,
                FROG_SIZE);
    }

    private void clearCell(int xCellNumber, int yCellNumber) {
        graphics.setColor(Color.BLACK);
        int xCoord = xCellNumber * CELL_SIZE;
        int yCoord = 0 + yCellNumber * CELL_SIZE;
        graphics.fillRect(xCoord, yCoord, CELL_SIZE - 1, CELL_SIZE - 1);
        drawBorders(xCoord, yCoord);
    }

    private void drawBorders(int xCoord, int yCoord) {
        graphics.setColor(Color.GRAY);
        graphics.drawLine(xCoord, yCoord, xCoord + CELL_SIZE, yCoord);
        graphics.drawLine(xCoord, yCoord + CELL_SIZE, xCoord + CELL_SIZE, yCoord + CELL_SIZE);

        graphics.drawLine(xCoord, yCoord, xCoord, yCoord + CELL_SIZE);
        graphics.drawLine(xCoord + CELL_SIZE, yCoord, xCoord + CELL_SIZE, yCoord + CELL_SIZE);
    }

    private void drawHead(int xCellNumber, int yCellNumber) {
        graphics.setColor(Color.YELLOW);
        graphics.fillOval(xCellNumber * CELL_SIZE + HEAD_COORD_ADJUSTMENT,
                0 + yCellNumber * CELL_SIZE + HEAD_COORD_ADJUSTMENT,
                HEAD_SIZE - 1,
                HEAD_SIZE - 1);
    }

    private void drawBody(int xCellNumber, int yCellNumber) {
        graphics.setColor(Color.YELLOW);
        graphics.fillOval(xCellNumber * CELL_SIZE + BODY_COORD_ADJUSTMENT,
                0 + yCellNumber * CELL_SIZE + BODY_COORD_ADJUSTMENT,
                BODY_SIZE,
                BODY_SIZE);
    }

    private void drawTail(int xCellNumber, int yCellNumber) {
        graphics.setColor(Color.YELLOW);
        graphics.fillOval(xCellNumber * CELL_SIZE + TAIL_COORD_ADJUSTMENT,
                0 + yCellNumber * CELL_SIZE + TAIL_COORD_ADJUSTMENT,
                TAIL_SIZE,
                TAIL_SIZE);
    }

    private void drawVericalBorders() {
        graphics.setColor(Color.GRAY);
        int numberOfCellsVertically = GAME_FIELD_HEIGHT / CELL_SIZE;
        int currentX = 0;
        for (int i = 0; i < numberOfCellsVertically; i++) {
            graphics.drawLine(currentX, 0, currentX, GAME_FIELD_HEIGHT);
            currentX += CELL_SIZE - 1;
            graphics.drawLine(currentX, 0, currentX, GAME_FIELD_HEIGHT);
            currentX++;
        }
    }

    private void drawHorizontalBorders() {
        graphics.setColor(Color.GRAY);
        int numberOfCellsHorizontally = GAME_FIELD_HEIGHT / CELL_SIZE;
        int currentY = 0;
        for (int i = 0; i < numberOfCellsHorizontally; i++) {
            graphics.drawLine(0, currentY, GAME_FIELD_WIDTH, currentY);
            currentY += CELL_SIZE - 1;
            graphics.drawLine(0, currentY, GAME_FIELD_WIDTH, currentY);
            currentY++;
        }
    }
}
