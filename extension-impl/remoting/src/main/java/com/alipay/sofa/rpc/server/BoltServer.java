package com.alipay.sofa.rpc.server;

import com.alipay.sofa.rpc.config.ProviderConfig;
import com.alipay.sofa.rpc.config.ServerConfig;
import com.alipay.sofa.rpc.ext.Extension;
import com.alipay.sofa.rpc.invoke.Invoker;

import java.util.concurrent.ThreadPoolExecutor;

@Extension("bolt")
public class BoltServer implements Server {

    ThreadPoolExecutor poolExecutor = null;

    @Override
    public void init(ServerConfig serverConfig) {
        poolExecutor = initThreadPool(serverConfig);

        System.out.println(this);

    }

    private ThreadPoolExecutor initThreadPool(ServerConfig serverConfig) {
        ThreadPoolExecutor pool = BusinessPool.initPool(serverConfig);
        //pool.setThreadFactory();
        //pool.setRejectedExecutionHandler();
        pool.prestartAllCoreThreads();
        return pool;
    }

    @Override
    public void start() {
        System.out.println("start");
    }

    @Override
    public boolean isStarted() {
        return false;
    }

    @Override
    public boolean hasNoEntry() {
        return false;
    }

    @Override
    public void stop() {

    }

    @Override
    public void registerProcessor(ProviderConfig providerConfig, Invoker instance) {

    }

    @Override
    public void unRegisterProcessor(ProviderConfig providerConfig, boolean closeIfNoEntry) {

    }

    @Override
    public void destroy(DestroyHook hook) {

    }
}
