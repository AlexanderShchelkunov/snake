package com.sandro.snake.model;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum Direction {

    UP(0),
    DOWN(1),
    RIGHT(2),
    LEFT(3);

    private static final Map<Integer, Direction> MAPPED_VALUES = Stream.of(values()).collect(Collectors.toMap(Direction::getId, Function.identity()));
    private final int id;

    private Direction(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static Direction getDirectionById(int id) {
        return MAPPED_VALUES.getOrDefault(id, UP);
    }
}
