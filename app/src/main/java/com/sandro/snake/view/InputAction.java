package com.sandro.snake.view;

import java.beans.PropertyChangeListener;
import javax.swing.Action;

@FunctionalInterface
public interface InputAction extends Action {

    @Override
    default Object getValue(String key) {
        return null;
    }

    @Override
    default void putValue(String key, Object value) {

    }

    @Override
    default void setEnabled(boolean b) {

    }

    @Override
    default boolean isEnabled() {
        return true;
    }

    @Override
    default void addPropertyChangeListener(PropertyChangeListener listener) {

    }

    @Override
    default void removePropertyChangeListener(PropertyChangeListener listener) {

    }
}
