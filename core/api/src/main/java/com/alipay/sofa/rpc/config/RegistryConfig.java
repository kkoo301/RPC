package com.alipay.sofa.rpc.config;

import com.alipay.sofa.rpc.common.RpcConfigs;
import com.alipay.sofa.rpc.common.RpcOptions;

import java.io.Serializable;

public class RegistryConfig<T> extends AbstractIdConfig<RegistryConfig> implements Serializable {

    /**
     * 注册地址
     */
    private String address;

    /**
     * 协议
     */
    private String protocol = RpcConfigs.getStringValue(RpcOptions.DEFAULT_PROTOCOL);

    public String getAddress() {
        return address;
    }

    public RegistryConfig setAddress(String address) {
        this.address = address;
        return castThis();
    }

    public String getProtocol() {
        return protocol;
    }

    public RegistryConfig setProtocol(String protocol) {
        this.protocol = protocol;
        return castThis();
    }
}
