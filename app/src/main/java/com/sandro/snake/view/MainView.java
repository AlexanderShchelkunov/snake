package com.sandro.snake.view;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;

public class MainView {

    private final ControlPanelView controlPanelView;
    private final GameFieldView gameFieldView;
    private JFrame appFrame;

    public MainView(ControlPanelView controlPanelView, GameFieldView gameFieldView) {
        this.controlPanelView = controlPanelView;
        this.gameFieldView = gameFieldView;
    }

    public void init(Runnable closeAction) {
        appFrame = createMainFrame();
        appFrame.getContentPane().add(controlPanelView.getControlPane(), BorderLayout.PAGE_START);
        appFrame.getContentPane().add(gameFieldView.init(), BorderLayout.CENTER);
        appFrame.pack();
        appFrame.setVisible(true);
        setCloseAction(closeAction);
    }

    public void show() {
        appFrame.pack();
        appFrame.setVisible(true);
    }

    private JFrame createMainFrame() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(false);
        frame.setLayout(new BorderLayout());
        return frame;
    }

    public ControlPanelView getControlPanelView() {
        return controlPanelView;
    }

    public GameFieldView getGameFieldView() {
        return gameFieldView;
    }

    public JFrame getAppFrame() {
        return appFrame;
    }

    private void setCloseAction(Runnable action) {
        appFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                action.run();
            }
        });
    }
}
