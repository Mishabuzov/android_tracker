package com.example.tom.itistracker.tools.functions;

/**
 * When we use this function - we don't have the result param when we transfer action.
 * Anyway we can transfer it to use in future, when we'll get the result.
 *
 * @param <T> - type of the result.
 */
public interface ResultFunction<T> {

    void action(T result);

}
