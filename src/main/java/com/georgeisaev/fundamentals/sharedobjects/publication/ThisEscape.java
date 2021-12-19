package com.georgeisaev.fundamentals.sharedobjects.publication;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class ThisEscape {

    private String name;

    public ThisEscape() {
        this.name = "ThisEscape " + Thread.currentThread().getName();
    }

    public ThisEscape(EventSource source) {
        this();
        source.registerListener(
                new EventListener() {
                    public void onEvent(Event e) {
                        log.info("Event {}", e);
                        doSomething(e);
                    }
                });
    }

    void doSomething(Event event) {
        log.info("Event {}", event);
    }

    public static void main(String[] args) {
        ThisEscape t = new ThisEscape(new EventSource());
    }

}

@Slf4j
@Data
class EventSource {

    void registerListener(EventListener eventListener) {
        log.info("EventListener {}", eventListener);
    }

}

interface EventListener {

    void onEvent(Event e);

}

@Slf4j
@Data
class Event {

    private String name;

}