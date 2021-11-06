package com.georgeisaev.fillthegaps.atomic;

import java.util.ConcurrentModificationException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * Illustrated optimistic blocking machinery via Atomic classes functionality
 *
 * @author Georgy Isaev
 */
public class Test {

    private final AtomicStampedReference<ComplexObject> stampedState;
    private final AtomicInteger stamp;

    public Test(ComplexObject initialState) {
        this.stampedState = new AtomicStampedReference<>(initialState, 0);
        this.stamp = new AtomicInteger(0);
    }

    public void updateState(ComplexObject newState) {
        int[] stampHolder = new int[1];
        ComplexObject currentState = stampedState.get(stampHolder);
        int newStamp = this.stamp.incrementAndGet();
        // Optimistically sets newState only if the current state and stamp has not been changed
        if (!stampedState.compareAndSet(currentState, newState, stampHolder[0], newStamp)) {
            throw new ConcurrentModificationException("Cannot update state since it has been modified");
        }
    }

    public String getVersion() {
        return String.valueOf(stampedState.getStamp());
    }

    static class ComplexObject {

        public ComplexObject() {
            // Quite a complex object
        }

    }

}

