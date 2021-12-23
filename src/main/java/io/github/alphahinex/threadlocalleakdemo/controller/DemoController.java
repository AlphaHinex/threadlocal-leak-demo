package io.github.alphahinex.threadlocalleakdemo.controller;

import io.github.alphahinex.threadlocalleakdemo.context.DemoContext;
import io.github.alphahinex.threadlocalleakdemo.service.DemoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DemoController.class);

    private final DemoService service;

    public DemoController(DemoService service) {
        this.service = service;
    }

    @GetMapping("/leak/{n}")
    public String leak(@PathVariable int n) throws InterruptedException {
        return fillContext(n, 0, false);
    }

    private String fillContext(int n, int t, boolean clean) throws InterruptedException {
        DemoContext.reset();
        LOGGER.debug("Request handled by " + Thread.currentThread().getName());
        Assert.isTrue(n > 0, "Loop times must greater than zero");
        for (int i = 0; i < n; i++) {
            service.addToList(t, clean);
        }
        return "Start leak " + n + "KB memory done";
    }

    @GetMapping("/leak/slow/{n}")
    public String slowLeak(@PathVariable int n) throws InterruptedException {
        return fillContext(n, 50, false);
    }

    @GetMapping("/{n}")
    public String normal(@PathVariable int n) throws InterruptedException {
        return fillContext(n, 0, true);
    }

    @GetMapping("/slow/{n}")
    public String slow(@PathVariable int n) throws InterruptedException {
        return fillContext(n, 50, true);
    }

}
