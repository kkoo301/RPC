package com.alipay.sofa.rpc.server;

import com.alipay.sofa.rpc.config.ServerConfig;

import java.util.concurrent.*;

public class BusinessPool {

    public static ThreadPoolExecutor initPool(ServerConfig config) {
        int coreThreadSize = config.getCoreThread();
        int maxPoolSize = config.getMaxThread();
        int queueSize = config.getQueues();
        int aliveTime = config.getAliveTime();
        BlockingQueue blockingQueue = null;
        if (queueSize > 0) {
            blockingQueue = new LinkedBlockingQueue(queueSize);
        } else {
            blockingQueue = new SynchronousQueue();
        }
        return new ServerThreadPoolExecutor(coreThreadSize, maxPoolSize, aliveTime, TimeUnit.MILLISECONDS, blockingQueue);
    }

}
