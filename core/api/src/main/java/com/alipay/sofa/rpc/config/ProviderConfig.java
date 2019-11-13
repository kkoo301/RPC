package com.alipay.sofa.rpc.config;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

public final class ProviderConfig<T> extends AbstractInterfaceConfig<T, ProviderConfig<T>>
    implements Serializable {

  private static final long serialVersionUID = -3058073881775315962L;

  private String bootstrap = "";

  private ExecutorService executorService;

  private List<ServerConfig> server;

  private List<RegistryConfig> registrys;

  public String getBootstrap() {
    return bootstrap;
  }

  public ProviderConfig setBootstrap(String bootstrap) {
    this.bootstrap = bootstrap;
    return castThis();
  }

  public ExecutorService getExecutorService() {
    return executorService;
  }

  public ProviderConfig setExecutorService(ExecutorService executorService) {
    this.executorService = executorService;
    return castThis();
  }

  public List<ServerConfig> getServer() {
    return server;
  }

  public ProviderConfig<T> setServer(List<ServerConfig> server) {
    this.server = server;
    return this;
  }

  public ProviderConfig<T> setServer(ServerConfig serverConfig) {
    if (null == this.server) {
      this.server = new ArrayList<>();
    }
    server.add(serverConfig);
    return this;
  }

  public List<RegistryConfig> getRegistrys() {
    return registrys;
  }

  public ProviderConfig setRegistrys(List<RegistryConfig> registrys) {
    this.registrys = registrys;
    return castThis();
  }
}
