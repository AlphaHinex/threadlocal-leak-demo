package io.github.alphahinex.threadlocalleakdemo.context;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class DemoContext {

    private static final ThreadLocal<List<Object>> CONTEXT = new ThreadLocal<>();

    private static final AtomicInteger COUNT = new AtomicInteger(0);

    public static int current() {
        return COUNT.incrementAndGet();
    }

    public static void reset() {
        COUNT.set(0);
    }

    public static void add(Object item) {
        List<Object> l = CONTEXT.get();
        if (l == null) {
            CONTEXT.set(new ArrayList<>());
        }
        CONTEXT.get().add(item);
    }

    public static void remove() {
        CONTEXT.remove();
    }

}
