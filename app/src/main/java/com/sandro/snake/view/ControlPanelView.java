package com.sandro.snake.view;

import static com.sandro.snake.Constants.CONTROL_PANEL_HEIGHT;
import static com.sandro.snake.Constants.CONTROL_PANEL_WIDTH;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ControlPanelView {

    private static final String PAUSE_BUTTON_LABEL = "Pause";
    private static final String STOP_BUTTON_LABEL = "Stop";
    private static final String START_BUTTON_LABEL = "Start";
    private static final String SCORE_LABEL_TEXT = "Score: ";
    private static final String INITIAL_SCORE = "0";

    private JPanel controlPane;
    private JButton startButton;
    private JButton stopButton;
    private JButton pauseButton;
    private JLabel scoreLabel;

    public JPanel init(Runnable startAction, Runnable stopAction, Runnable pauseAction) {
        initControlPane();
        initStartButton(startAction);
        initStopButton(stopAction);
        initPauseButton(pauseAction);
        initScoreLabel();
        return controlPane;
    }

    private void initScoreLabel() {
        scoreLabel = new JLabel();
        scoreLabel.setText(SCORE_LABEL_TEXT + INITIAL_SCORE);
        controlPane.add(scoreLabel);
    }

    public void updateScore(int score) {
        scoreLabel.setText(SCORE_LABEL_TEXT + score);
    }

    public JPanel getControlPane() {
        return controlPane;
    }

    public void disableStopButton() {
        stopButton.setEnabled(false);
        pauseButton.setEnabled(false);
        startButton.setEnabled(true);
    }

    private void initStopButton(Runnable stopAction) {
        stopButton = new JButton();
        stopButton.setText(STOP_BUTTON_LABEL);
        controlPane.add(stopButton);
        stopButton.addActionListener(e -> {
            stopButton.setEnabled(false);
            pauseButton.setEnabled(false);
            startButton.setEnabled(true);
            stopAction.run();
        });
    }

    private void initStartButton(Runnable startAction) {
        startButton = new JButton();
        controlPane.add(startButton);
        startButton.setText(START_BUTTON_LABEL);
        startButton.setEnabled(false);
        startButton.addActionListener(e -> {
            startButton.setEnabled(false);
            stopButton.setEnabled(true);
            pauseButton.setEnabled(true);
            startAction.run();
        });
    }

    private void initPauseButton(Runnable pauseAction) {
        pauseButton = new JButton();
        pauseButton.setText(PAUSE_BUTTON_LABEL);
        controlPane.add(pauseButton);
        pauseButton.addActionListener(e -> {
            pauseButton.setEnabled(false);
            startButton.setEnabled(true);
            stopButton.setEnabled(true);
            pauseAction.run();
        });
    }

    private void initControlPane() {
        controlPane = new JPanel();
        controlPane.setLayout(new FlowLayout());
        controlPane.setPreferredSize(new Dimension(CONTROL_PANEL_WIDTH, CONTROL_PANEL_HEIGHT));
    }
}
