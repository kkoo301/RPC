package com.alipay.sofa.rpc.server;

import com.alipay.sofa.rpc.config.ServerConfig;
import com.alipay.sofa.rpc.ext.ExtensionLoaderFactory;
import com.alipay.sofa.rpc.log.Logger;
import com.alipay.sofa.rpc.log.LoggerFactory;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.ArrayList;
import java.util.List;

public final class ServerFactory {

    /**
     * slf4j Logger for this class
     */
    private final static Logger LOGGER = LoggerFactory.getLogger(ServerFactory.class);

    private final static Cache<Integer, Server> SERVER_CACHE = CacheBuilder.newBuilder().build();

    public static Server getServer(ServerConfig serverConfig) {
        int port = serverConfig.getPort();
        Server server = SERVER_CACHE.getIfPresent(port);
        if (null == server) {
            server = ExtensionLoaderFactory.getExtensionLoader(Server.class).getExtension(serverConfig.getProtocol());
            server.init(serverConfig);
            SERVER_CACHE.put(serverConfig.getPort(), server);
        }
        return server;
    }

    public static List<Server> getAllServers() {
        return new ArrayList<>(SERVER_CACHE.asMap().values());
    }

    public static void destoryAllServers() {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("begin destory all server");
        }
        for (Server server : getAllServers()) {
            try {
                server.destroy(null);
            } catch (Exception e) {
                if (LOGGER.isErrorEnabled()) {
                    LOGGER.error("destory server error ", e);
                }
            }
        }
        SERVER_CACHE.cleanUp();
    }

    public static void destoryServer(ServerConfig serverConfig) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("begin destory server host:{} port:{} protocol:{}", serverConfig.getHost(), serverConfig.getPort(), serverConfig.getProtocol());
        }
        Server server = SERVER_CACHE.getIfPresent(serverConfig.getPort());
        if (null == server) {
            return;
        }
        try {
            server.destroy(null);
            SERVER_CACHE.invalidate(serverConfig.getPort());
        } catch (Exception e) {
            if (LOGGER.isErrorEnabled()) {
                LOGGER.error("destory server error ", e);
            }
        }
    }

}
