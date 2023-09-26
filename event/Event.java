package com.mikeandliam.spaceexplorers.event;

import java.util.ArrayList;

/**
 * (observer pattern)
 * An event that either the GUI or command line can subscribe to to receive a notification when different things happen
 *
 * @param <T> The type of argument that this event passes to its listeners
 *            <p>
 *            examples:
 *            to "invoke" an event and send out a notification to all subscribers (this is done by the GameEnvironment or something that produces events)
 *            event.invoke(new EventArgs(...));
 *            <p>
 *            to listen to an event (this can be done from the CommandlineManager or GUI, etc):
 *            gameEnvironment.getEvent().addListener(args -> <code here>);
 */
public class Event<T> {
    private ArrayList<EventHandler<T>> handlers = new ArrayList<>();

    /**
     *  If a handler wants to remove or clear event handlers inside its handle lambda,
     *  we cannot remove the handlers directly, because they are being iterated over in
     *  the invoke mehod, causing a ConcurrentModificationException.
     *
     *  Instead, we store a list of all the handlers to remove,
     *  and do so after they have all been invoked.
     */
    private ArrayList<EventHandler<T>> handlersToRemove = new ArrayList<>();

    /**
     * invoke the event (ie. send out the notification to the eventHandlers that the event was called)
     *
     * @param args the arguments to pass on to the eventHandlers
     */
    public void invoke(T args) {
        for (EventHandler<T> handler : handlers) {
            handler.handle(args);
        }

        //remove any handlers that have been requested to be removed during this invoke()
        handlers.removeAll(handlersToRemove);

        handlersToRemove.clear();
    }

    /**
     * Add a handler that will be notified whenever this event occurs
     */
    public void addHandler(EventHandler<T> handler) {
        handlers.add(handler);
    }

    /**
     * remove/unsubscribe an event handler.
     *
     * @param handler the handler to remove. It will no longer be called when the event happens
     */
    public void removeHandler(EventHandler<T> handler) {
        handlersToRemove.add(handler);
    }

    /**
     * Clears all the handlers for this event, so none of them will be called when the event happens
     */
    public void clearHandlers() {
        //remove all handlers
        handlersToRemove = handlers;
    }
}
