package com.alipay.sofa.rpc.registry.zk;

import com.alipay.sofa.rpc.config.ProviderConfig;
import com.alipay.sofa.rpc.config.RegistryConfig;
import com.alipay.sofa.rpc.ext.Extension;
import com.alipay.sofa.rpc.log.Logger;
import com.alipay.sofa.rpc.log.LoggerFactory;
import com.alipay.sofa.rpc.registry.Registry;
import org.apache.curator.framework.CuratorFramework;

@Extension("zookeeper")
public class ZookeeperRegistry implements Registry {

  private static final Logger LOGGER = LoggerFactory.getLogger(ZookeeperRegistry.class);

  private static RegistryConfig registryConfig;

  /** Zookeeper zkClient */
  private CuratorFramework zkClient;

  public ZookeeperRegistry(RegistryConfig registryConfig) {
    this.registryConfig = registryConfig;
  }

  @Override
  public boolean start() {
    System.out.println("start");
    return false;
  }

  @Override
  public void register(ProviderConfig config) {
    System.out.println(config);
  }

  @Override
  public void destroy(DestroyHook hook) {}

  @Override
  public void init() {
    LOGGER.info("init ====> ZookeeperRegistry" + registryConfig);

    if (null != zkClient) {
      return;
    }
  }
}
