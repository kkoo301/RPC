package com.alipay.sofa.rpc.server;

import com.alipay.sofa.rpc.config.ProviderConfig;
import com.alipay.sofa.rpc.config.ServerConfig;
import com.alipay.sofa.rpc.ext.Extension;
import com.alipay.sofa.rpc.invoke.Invoker;

@Extension("bolt")
public class BoltServer implements Server {

    @Override
    public void init(ServerConfig serverConfig) {

    }

    @Override
    public void start() {

    }

    @Override
    public boolean isStarted() {
        return false;
    }

    @Override
    public boolean hasNoEntry() {
        return false;
    }

    @Override
    public void stop() {

    }

    @Override
    public void registerProcessor(ProviderConfig providerConfig, Invoker instance) {

    }

    @Override
    public void unRegisterProcessor(ProviderConfig providerConfig, boolean closeIfNoEntry) {

    }

    @Override
    public void destroy(DestroyHook hook) {

    }
}
