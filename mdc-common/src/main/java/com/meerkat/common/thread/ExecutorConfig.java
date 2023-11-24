package com.meerkat.common.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;


/**
 * 自定义线程池
 *
 *
 * @author zhujiaxing
 */
@Configuration
@EnableAsync
public class ExecutorConfig {

    private static final Logger logger = LoggerFactory.getLogger(ExecutorConfig.class);

    private static final Integer CORE_POOL_SIZE = Runtime.getRuntime().availableProcessors();

    private static final Integer MAX_POOL_SIZE = Runtime.getRuntime().availableProcessors() * 8;

    @Bean
    public Executor asyncAgentExecutor() {
        logger.info("start asyncExecutor");
        ThreadPoolTaskExecutor executor = new VisibleThreadPoolTaskExecutor();
        //配置核心线程数
        executor.setCorePoolSize(CORE_POOL_SIZE);
        //配置最大线程数
        executor.setMaxPoolSize(MAX_POOL_SIZE);
        //配置队列大小
        executor.setQueueCapacity(10000);
        //配置线程池中的线程的名称前缀
        executor.setThreadNamePrefix("async-agent-");
        // rejection-policy：当pool已经达到max size的时候，如何处理新任务
        // CALLER_RUNS：不在新线程中执行任务，而是有调用者所在的线程来执行
        // 设置拒绝策略
        executor.setRejectedExecutionHandler((r, threadPoolExecutor) -> {
            logger.warn("Reject Task..., taskCount [{}], completedTaskCount [{}], activeCount [{}], queueSize [{}]",
                    threadPoolExecutor.getTaskCount(),
                    threadPoolExecutor.getCompletedTaskCount(),
                    threadPoolExecutor.getActiveCount(),
                    threadPoolExecutor.getQueue().size());
        });
        //执行初始化
        executor.initialize();
        return executor;
    }

    @Bean
    public Executor asyncAgentExecutorSingle() {
        logger.info("start asyncAgentExecutorSingle");
        ThreadPoolTaskExecutor executor = new VisibleThreadPoolTaskExecutor();
        //配置核心线程数
        executor.setCorePoolSize(1);
        //配置最大线程数
        executor.setMaxPoolSize(1);
        //配置队列大小
        executor.setQueueCapacity(50000);
        //配置线程池中的线程的名称前缀
        executor.setThreadNamePrefix("async-agent-single-");
        // rejection-policy：当pool已经达到max size的时候，如何处理新任务
        // CALLER_RUNS：不在新线程中执行任务，而是有调用者所在的线程来执行
        // 设置拒绝策略
        executor.setRejectedExecutionHandler((r, threadPoolExecutor) -> {
            logger.warn("Reject Task..., taskCount [{}], completedTaskCount [{}], activeCount [{}], queueSize [{}]",
                    threadPoolExecutor.getTaskCount(),
                    threadPoolExecutor.getCompletedTaskCount(),
                    threadPoolExecutor.getActiveCount(),
                    threadPoolExecutor.getQueue().size());
        });
        //执行初始化
        executor.initialize();
        return executor;
    }

}