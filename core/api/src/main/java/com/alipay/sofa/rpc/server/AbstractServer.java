package com.alipay.sofa.rpc.server;

import com.alipay.sofa.rpc.log.Logger;
import com.alipay.sofa.rpc.log.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class AbstractServer implements Server {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractServer.class);

    private AtomicBoolean started = new AtomicBoolean(false);

    String ip;

    int port;

    public AbstractServer(int port) {
        this(new InetSocketAddress(port).getAddress().getHostAddress(), port);
    }

    public AbstractServer(String ip, int port) {
        this.ip = ip;
        this.port = port;

    }

    @Override
    public void start() {
        if (started.compareAndSet(false, true)) {
            try {
                doInit();
                LOGGER.warn("server will start on port {}", port);
                if (doStart()) {
                    LOGGER.warn("success to started server on port {}!", port);
                } else {
                    LOGGER.warn("failed to starting server on port {}", port);
                }
            } catch (Throwable t) {
                this.stop();
                throw new IllegalStateException("fail to start server!", t);
            }
        } else {
            String errMsg = "server has already started";
            throw new IllegalStateException(errMsg);
        }
    }

    @Override
    public void stop() {
        if (started.compareAndSet(true, false)) {
            doStop();
        } else {
            throw new IllegalStateException("ERROR: The server has already stopped!");
        }
    }

    abstract void doInit();

    abstract boolean doStart() throws InterruptedException;

    abstract void doStop();
}
