package net.esmaeil.explore.spring;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

@Configuration
class Config {

    @Bean
    public ExecutorService executorService(
            @Value("${explore.thread.pool.size}")
                    int threadPoolSize,
                    ThreadFactory threadFactory
    ) {
        return Executors.newFixedThreadPool(threadPoolSize,threadFactory);
    }

    @Bean
    public ThreadFactory threadFactory(@Value("${explore.thread.prefix}") String threadPrefix) {
        ThreadFactory threadFactory = new CustomizableThreadFactory();
        ((CustomizableThreadFactory) threadFactory).setThreadNamePrefix(threadPrefix);
        return threadFactory;
    }

}
