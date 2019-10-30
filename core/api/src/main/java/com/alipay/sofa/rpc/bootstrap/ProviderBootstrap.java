package com.alipay.sofa.rpc.bootstrap;

import com.alipay.sofa.rpc.config.ProviderConfig;
import com.alipay.sofa.rpc.ext.Extensible;

@Extensible(singleton = false)
public abstract class ProviderBootstrap<T> {

    private ProviderConfig<T> providerConfig;

    public ProviderBootstrap(ProviderConfig<T> providerConfig) {
        this.providerConfig = providerConfig;
    }

    public ProviderConfig<T> getProviderConfig() {
        return providerConfig;
    }

    /**
     * 发布一个服务
     */
    abstract void export();

    /**
     * 取消发布一个服务
     */
    abstract void unExport();

}
