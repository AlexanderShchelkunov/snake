package com.sandro.snake.model;

import static com.sandro.snake.Constants.FROG_SLEEP_TIME;
import java.util.concurrent.ThreadLocalRandom;

public class Frog extends WithCoordinates {

    public static int FROG_MAX_ID = 0;

    private final long frogSleepTime;
    private final int id;

    public Frog(int id) {
        super();
        this.id = id;
        frogSleepTime = randomSpeed();
    }

    public Frog(int id, int x, int y) {
        super(x, y);
        this.id = id;
        frogSleepTime = randomSpeed();
    }

    private long randomSpeed() {
        return FROG_SLEEP_TIME + ThreadLocalRandom.current().nextLong(FROG_SLEEP_TIME);
    }

    public int getId() {
        return id;
    }

    @Override
    public int hashCode() {
        int hash = super.hashCode();
        hash = 17 * hash + (int) (this.frogSleepTime ^ (this.frogSleepTime >>> 32));
        hash = 17 * hash + this.id;
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
        Frog other = (Frog) obj;
        if (!super.equals(other)) {
            return false;
        }
        if (this.frogSleepTime != other.frogSleepTime) {
            return false;
        }
        return this.id == other.id;
    }

    @Override
    public String toString() {
        return "Frog{id=" + id + ", x=" + getX() + ", y=" + getY() + '}';
    }
}
