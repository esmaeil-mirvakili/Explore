package net.esmaeil.explore.event;

public class Event<P, R> {
    private P payload;
    private R result;

    public Event(P payload) {
        this.payload = payload;
    }

    public P getPayload() {
        return payload;
    }

    public void setPayload(P payload) {
        this.payload = payload;
    }

    public R getResult() {
        return result;
    }

    public void setResult(R result) {
        this.result = result;
    }

    public static <T> Event<T, ?> newEvent(T payload) {
        return new Event<>(payload);
    }
}
