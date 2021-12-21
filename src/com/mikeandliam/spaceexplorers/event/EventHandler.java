package com.mikeandliam.spaceexplorers.event;

/**
 * Implement this to be able to receive a notification (ie. handle() gets called) when an event happens
 * @param <T> the type of data that will be passed on to the handler as method arguments when the event occurs
 */
public interface EventHandler<T> {
    void handle(T args);
}
