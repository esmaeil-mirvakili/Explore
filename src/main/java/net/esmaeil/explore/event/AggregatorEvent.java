package net.esmaeil.explore.event;

import java.util.LinkedList;
import java.util.List;

public class AggregatorEvent<P,R> extends Event<P,List<R>> {
    public AggregatorEvent(P payload) {
        super(payload);
        setResult(new LinkedList<>());
    }

    public void addResult(R result) {
        if(getResult() == null)
            setResult(new LinkedList<>());
        getResult().add(result);
    }
}
