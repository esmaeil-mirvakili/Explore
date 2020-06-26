package net.esmaeil.explore.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

@Service
public final class EventManagerImpl implements EventManager {
    private final Map<String, List<EventHandler>> eventMap;
    private final ExecutorService executorService;

    @Autowired
    public EventManagerImpl(ExecutorService executorService) {
        this.executorService = executorService;
        eventMap = new HashMap<>();
    }

    @Override
    public synchronized void subscribe(String eventKey, EventHandler eventHandler) {
        eventMap.computeIfAbsent(eventKey, s -> new LinkedList<>());
        eventMap.get(eventKey).add(eventHandler);
    }

    @Override
    public void unsubscribe(String eventKey, EventHandler eventHandler) {
        if(eventMap.containsKey(eventKey))
            eventMap.get(eventKey).remove(eventHandler);
    }

    @Override
    public void unsubscribeAll(String eventKey) {
        eventMap.remove(eventKey);
    }

    @Override
    public void event(Event event, String eventKey) {
        if(eventMap.containsKey(eventKey))
            eventMap.get(eventKey).forEach(eventHandler -> eventHandler.handle(event));
    }

    @Override
    public void call(Event event, String eventKey) {
        if(eventMap.containsKey(eventKey) && !eventMap.get(eventKey).isEmpty())
            eventMap.get(eventKey).get(0).handle(event);
    }

    @Override
    public Future<Event> asyncEvent(Event event, String eventKey) {
        return executorService.submit(() -> {
            event(event,eventKey);
            return event;
        });
    }
}