package net.esmaeil.explore.spring;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

@Configuration
class Termination implements DisposableBean {
    private final ExecutorService executorService;

    @Autowired
    public Termination(ExecutorService executorService) {
        this.executorService = executorService;
    }

    @Override
    public void destroy() throws Exception {
        executorService.shutdown();
        executorService.awaitTermination(30,TimeUnit.SECONDS);
    }
}
