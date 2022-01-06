package com.sandro.snake.model;

public abstract class WithCoordinates {

    private int x;
    private int y;
    private int lastX;
    private int lastY;

    public WithCoordinates() {
    }

    public WithCoordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        lastX = getX();
        this.x = x;
    }

    public void setY(int y) {
        lastY = getY();
        this.y = y;
    }

    public int getLastX() {
        return lastX;
    }

    public int getLastY() {
        return lastY;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.x;
        hash = 97 * hash + this.y;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final WithCoordinates other = (WithCoordinates) obj;
        if (this.x != other.x) {
            return false;
        }
        return this.y == other.y;
    }

    @Override
    public String toString() {
        return "WithCoordinates{" + "x=" + x + ", y=" + y + '}';
    }
}
