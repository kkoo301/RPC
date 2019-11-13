package com.alipay.sofa.rpc.bootstrap;

import com.alipay.sofa.rpc.config.ProviderConfig;
import com.alipay.sofa.rpc.config.RegistryConfig;
import com.alipay.sofa.rpc.config.ServerConfig;
import com.alipay.sofa.rpc.ext.Extension;
import com.alipay.sofa.rpc.registry.Registry;
import com.alipay.sofa.rpc.registry.RegistryFactory;
import com.alipay.sofa.rpc.server.ProviderProxyInvoker;
import com.alipay.sofa.rpc.server.Server;

import java.util.List;

@Extension("sofa")
public class DefaultProviderBootstrap<T> implements ProviderBootstrap<T> {

    private static ProviderConfig providerConfig;

    public DefaultProviderBootstrap(ProviderConfig<T> providerConfig) {
        this.providerConfig = providerConfig;
    }

    @Override
    public void export() {
        ProviderProxyInvoker providerProxyInvoker = new ProviderProxyInvoker(providerConfig);

        List<RegistryConfig> registrys = providerConfig.getRegistrys();
        for (RegistryConfig registryConfig : registrys) {
            RegistryFactory.getRegistry(registryConfig);
        }

        List<ServerConfig> serverConfigList = providerConfig.getServer();
        for (ServerConfig serverConfig : serverConfigList) {
            Server server = serverConfig.buildIfAbsent();

            //Server server = ServerFactory.getServer(serverConfig);

            server.registerProcessor(providerConfig, providerProxyInvoker);
            server.start();
        }
        register();
    }

    private void register() {
        List<RegistryConfig> registrys = providerConfig.getRegistrys();
        for(RegistryConfig registryConfig : registrys){
            Registry registry = RegistryFactory.getRegistry(registryConfig);
            registry.start();
            registry.register(providerConfig);
        }
    }

    @Override
    public void unExport() {

    }

    @Override
    public void destroy(DestroyHook hook) {

    }

    @Override
    public void init() {

    }
}
