package com.sandro.snake.controller;

public abstract class Pausable implements Runnable {

    private volatile boolean paused = false;

    public boolean isPaused() {
        return paused;
    }

    public void upPause() {
        paused = false;
        synchronized (this) {
            this.notifyAll();
        }
    }

    public void pause() {
        paused = true;
    }

    public void pauseIfNeeded() {
        if (paused) {
            synchronized (this) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
