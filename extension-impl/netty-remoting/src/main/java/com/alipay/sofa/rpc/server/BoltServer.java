package com.alipay.sofa.rpc.server;

import com.alipay.sofa.rpc.config.ProviderConfig;
import com.alipay.sofa.rpc.config.ServerConfig;
import com.alipay.sofa.rpc.ext.Extension;
import com.alipay.sofa.rpc.invoke.Invoker;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicBoolean;

@Extension("bolt")
public class BoltServer implements Server {

  private ThreadPoolExecutor poolExecutor;

  private AtomicBoolean started = new AtomicBoolean(false);

  public BoltServer(ServerConfig serverConfig) {
    poolExecutor = initThreadPool(serverConfig);
  }

  private ThreadPoolExecutor initThreadPool(ServerConfig serverConfig) {
    ThreadPoolExecutor pool = BusinessPool.initPool(serverConfig);
    // pool.setThreadFactory();
    // pool.setRejectedExecutionHandler();
    pool.prestartAllCoreThreads();
    return pool;
  }

  @Override
  public void start() {
    RPCServer server = new RPCServer(8080);
    server.start();
  }

  @Override
  public void stop() {}

  @Override
  public void registerProcessor(ProviderConfig providerConfig, Invoker instance) {}

  @Override
  public void unRegisterProcessor(ProviderConfig providerConfig, boolean closeIfNoEntry) {}

  @Override
  public void destroy(DestroyHook hook) {}

  @Override
  public void init() {}
}
