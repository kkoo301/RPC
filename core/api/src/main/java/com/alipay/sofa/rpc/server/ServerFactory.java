package com.alipay.sofa.rpc.server;

import com.alipay.sofa.rpc.common.RpcConfigs;
import com.alipay.sofa.rpc.common.RpcOptions;
import com.alipay.sofa.rpc.common.SystemInfo;
import com.alipay.sofa.rpc.common.utils.NetUtils;
import com.alipay.sofa.rpc.common.utils.StringUtils;
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
            resolveServerConfig(serverConfig);
            server = ExtensionLoaderFactory.getExtensionLoader(Server.class)
                    .getExtension(serverConfig.getProtocol(), new Class[]{ServerConfig.class}, new Object[]{serverConfig});
            server.init();
            SERVER_CACHE.put(serverConfig.getPort(), server);
        }
        return server;
    }

    private static void resolveServerConfig(ServerConfig serverConfig) {
        String boundHost = serverConfig.getBoundHost();
        if (StringUtils.isBlank(boundHost)) {
            String host = serverConfig.getHost();
            if (StringUtils.isBlank(host)) {
                host = SystemInfo.getLocalHost();
                serverConfig.setHost(host);
                boundHost = SystemInfo.isWindows() ? host : NetUtils.ANYHOST;
            } else {
                boundHost = host;
            }
            serverConfig.setBoundHost(boundHost);
        }

        if (serverConfig.isAdaptivePort()) {
            int oriPort = serverConfig.getPort();
            int port = NetUtils.getAvailablePort(boundHost, oriPort, RpcConfigs.getIntValue(RpcOptions.SERVER_PORT_END));
            if (port != oriPort) {
                if (LOGGER.isWarnEnabled()) {
                    LOGGER.warn("Changed port from {} to {} because the config port is disabled", oriPort, port);
                }
                serverConfig.setPort(port);
            }
        }
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
