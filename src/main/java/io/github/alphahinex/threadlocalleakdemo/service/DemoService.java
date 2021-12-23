package io.github.alphahinex.threadlocalleakdemo.service;

import io.github.alphahinex.threadlocalleakdemo.context.DemoContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class DemoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DemoService.class);

    @Async
    public void addToList(int t, boolean clean) throws InterruptedException {
        DemoContext.add(oneKbBytes());
        if (t > 0) {
            Thread.sleep(t);
        }
        LOGGER.debug(Thread.currentThread().getName() + " add 1kb to context, No." + DemoContext.current());
        if (clean) {
            DemoContext.remove();
        }
    }

    private byte[] oneKbBytes() {
        byte[] bytes = new byte[1024];
        new Random().nextBytes(bytes);
        return bytes;
    }

}
