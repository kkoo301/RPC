package com.alipay.sofa.rpc;

import com.alipay.sofa.rpc.concurrent.NamedThreadFactory;
import com.alipay.sofa.rpc.log.Logger;
import com.alipay.sofa.rpc.log.LoggerFactory;
import org.junit.Test;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TestServer {

  private static final Logger LOGGER = LoggerFactory.getLogger(TestServer.class);

  @Test
  public void testServer() {
    // RPCServer server = new RPCServer(8080);
    // server.start();

    LOGGER.error("1");
    ThreadPoolExecutor poolExecutor =
        new ThreadPoolExecutor(10, 20, 5, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(100));
    // (ThreadPoolExecutor) Executors.newFixedThreadPool(30);
    LOGGER.error("2");
    poolExecutor.setThreadFactory(new NamedThreadFactory("myTest", false));
    LOGGER.error("3");
    poolExecutor.execute(
        new Runnable() {
          @Override
          public void run() {
            System.out.println("s");
            LOGGER.error("sdfdsf");
            System.out.println("s");
            System.out.println("s");
          }
        });
    LOGGER.error("4");
  }
}
