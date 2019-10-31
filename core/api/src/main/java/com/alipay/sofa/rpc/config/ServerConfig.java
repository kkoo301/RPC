package com.alipay.sofa.rpc.config;

import com.alipay.sofa.rpc.common.RpcConfigs;

import java.io.Serializable;

public class ServerConfig<T> extends AbstractInterfaceConfig<T, ProviderConfig<T>> implements Serializable {

    private String protocol;

    private String host;

    private int port;

    public String getProtocol() {
        this.protocol = RpcConfigs.getStringValue("");
        return protocol;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }
}
