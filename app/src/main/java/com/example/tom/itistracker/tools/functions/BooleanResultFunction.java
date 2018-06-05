package com.example.tom.itistracker.tools.functions;

/**
 * The same as the ResultFunction but the action method returns boolean.
 */
public interface BooleanResultFunction<T> {

    boolean action(T result);

}
