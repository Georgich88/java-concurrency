package com.georgeisaev.fillthegaps.sharedvariables.deadlock;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Deadlock calling the same method concurrently
 */
@Slf4j
public class ConcurrentHashMapDeadlock {

    public static final String K1_SAME_HASH = "AaAa";
    public static final String K2_SAME_HASH = "BBBB";
    public static final int INITIAL_CAPACITY = 16;

    public static void main(String[] args) {
        try {
            examineCompute();
        } catch (Exception e) {
            log.error("ConcurrentHashMap#compute", e);
        }
        try {
            examineComputeIfAbsent();
        } catch (Exception e) {
            log.error("ConcurrentHashMap#computeIfAbsent", e);
        }
    }

    public static void examineCompute() {
        log.info("ConcurrentHashMap#compute");
        Map<String, Integer> map = new ConcurrentHashMap<>(INITIAL_CAPACITY);
        map.compute(K1_SAME_HASH,
                (k1, v1) -> map.compute(K2_SAME_HASH,
                        (k2, v2) -> 42));
    }

    public static void examineComputeIfAbsent() {
        log.info("ConcurrentHashMap#computeIfAbsent");
        Map<String, Integer> map = new ConcurrentHashMap<>(INITIAL_CAPACITY);
        map.computeIfAbsent(K1_SAME_HASH,
                key -> map.computeIfAbsent(K2_SAME_HASH,
                        key2 -> 42));
    }

}
