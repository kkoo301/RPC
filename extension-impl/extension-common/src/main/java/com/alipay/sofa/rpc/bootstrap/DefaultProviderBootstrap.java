package com.alipay.sofa.rpc.bootstrap;

import com.alipay.sofa.rpc.config.ProviderConfig;
import com.alipay.sofa.rpc.config.RegistryConfig;
import com.alipay.sofa.rpc.config.ServerConfig;
import com.alipay.sofa.rpc.context.RpcRuntimeContext;
import com.alipay.sofa.rpc.ext.Extension;
import com.alipay.sofa.rpc.registry.Registry;
import com.alipay.sofa.rpc.registry.RegistryFactory;
import com.alipay.sofa.rpc.filter.ProviderProxyInvoker;
import com.alipay.sofa.rpc.server.Server;
import com.alipay.sofa.rpc.server.ServerFactory;

import java.util.List;

@Extension("sofa")
public class DefaultProviderBootstrap<T> implements ProviderBootstrap<T> {

  private static ProviderConfig providerConfig;

  public DefaultProviderBootstrap(ProviderConfig<T> providerConfig) {
    this.providerConfig = providerConfig;
  }

  @Override
  public void export() {

    //初始化并且启动server
    List<ServerConfig> serverConfigList = providerConfig.getServer();
    for (ServerConfig serverConfig : serverConfigList) {
      Server server = ServerFactory.getServer(serverConfig);
      server.registerProcessor(providerConfig, new ProviderProxyInvoker(providerConfig));
      server.start();
    }

    //初始化注册到注册中心
    List<RegistryConfig> registrys = providerConfig.getRegistrys();
    for (RegistryConfig registryConfig : registrys) {
      Registry registry = RegistryFactory.getRegistry(registryConfig);
      //registry.start();
      //registry.register(providerConfig);
    }

    RpcRuntimeContext.cacheServer();
  }

  @Override
  public void unExport() {}

  @Override
  public void destroy(DestroyHook hook) {}

  @Override
  public void init() {}
}
