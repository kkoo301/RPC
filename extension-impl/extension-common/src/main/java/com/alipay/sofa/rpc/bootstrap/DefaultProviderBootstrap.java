package com.alipay.sofa.rpc.bootstrap;

import com.alipay.sofa.rpc.config.ProviderConfig;
import com.alipay.sofa.rpc.ext.Extension;

@Extension("sofa")
public class DefaultProviderBootstrap<T> extends ProviderBootstrap<T> {

    public DefaultProviderBootstrap(ProviderConfig<T> providerConfig) {
        super(providerConfig);
    }

    @Override
    public void export() {

    }

    @Override
    public void unExport() {

    }
}
