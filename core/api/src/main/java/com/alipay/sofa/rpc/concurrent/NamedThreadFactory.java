package com.alipay.sofa.rpc.concurrent;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class NamedThreadFactory implements ThreadFactory {

    private static final AtomicInteger POOL_COUNT = new AtomicInteger(1);

    private final AtomicInteger THREAD_COUNT = new AtomicInteger(1);
    /**
     * 线程组
     */
    private ThreadGroup threadGroup;

    /**
     * 线程前缀
     */
    private String namePrefix;

    /**
     * 是否启用守护线程
     */
    private boolean daemon;

    public NamedThreadFactory(String prefix, boolean daemon) {
        SecurityManager securityManager = System.getSecurityManager();
        threadGroup = (securityManager != null) ? securityManager.getThreadGroup() : Thread.currentThread().getThreadGroup();
        namePrefix = prefix + "-" + POOL_COUNT.getAndIncrement() + "-T";
        this.daemon = daemon;
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(threadGroup, r, namePrefix + THREAD_COUNT.getAndIncrement(), 0);
        if (thread.isDaemon() != daemon) {
            thread.setDaemon(daemon);
        }
        if (thread.getPriority() != Thread.NORM_PRIORITY) {
            thread.setPriority(Thread.NORM_PRIORITY);
        }
        return thread;
    }
}
