package com.alipay.sofa.rpc.config;

import com.alipay.sofa.rpc.common.RpcConfigs;
import com.alipay.sofa.rpc.common.RpcOptions;
import com.alipay.sofa.rpc.server.Server;
import com.alipay.sofa.rpc.server.ServerFactory;

import java.io.Serializable;

public class ServerConfig<T> extends AbstractInterfaceConfig<T, ProviderConfig<T>> implements Serializable {

    private String protocol;

    private String host;

    private int port;

    public String getProtocol() {
        this.protocol = RpcConfigs.getStringValue(RpcOptions.DEFAULT_PROTOCOL);
        return protocol;
    }

    public Server buildIfAbsent(){
        return ServerFactory.getServer(this);
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }
}
