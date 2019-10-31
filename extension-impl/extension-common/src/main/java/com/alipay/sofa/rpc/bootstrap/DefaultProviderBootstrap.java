package com.alipay.sofa.rpc.bootstrap;

import com.alipay.sofa.rpc.config.ProviderConfig;
import com.alipay.sofa.rpc.config.ServerConfig;
import com.alipay.sofa.rpc.ext.Extension;
import com.alipay.sofa.rpc.server.Server;

import java.util.List;

@Extension("sofa")
public class DefaultProviderBootstrap<T> extends ProviderBootstrap<T> {

    public DefaultProviderBootstrap(ProviderConfig<T> providerConfig) {
        super(providerConfig);
    }

    @Override
    public void export() {
        List<ServerConfig> serverConfigList = getProviderConfig().getServer();
        for(ServerConfig serverConfig : serverConfigList){
            Server server = serverConfig.buildIfAbsent();
            server.init(serverConfig);
            server.start();
        }
    }

    @Override
    public void unExport() {

    }
}
