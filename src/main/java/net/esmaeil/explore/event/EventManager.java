package net.esmaeil.explore.event;

import java.util.concurrent.Future;

public interface EventManager {
    void subscribe(String eventKey, EventHandler eventHandler);

    void unsubscribe(String eventKey, EventHandler eventHandler);

    void unsubscribeAll(String eventKey);

    void event(Event event, String eventKey);

    void call(Event event, String eventKey);

    Future<Event> asyncEvent(Event event, String eventKey);
}
