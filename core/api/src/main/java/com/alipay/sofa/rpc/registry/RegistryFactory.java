package com.alipay.sofa.rpc.registry;

import com.alipay.sofa.rpc.config.RegistryConfig;
import com.alipay.sofa.rpc.ext.ExtensionLoaderFactory;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

public class RegistryFactory {

  private static final Cache<RegistryConfig, Registry> ALL_REGISTRY =
      CacheBuilder.newBuilder().build();

  public static Registry getRegistry(RegistryConfig registryConfig) {
    Registry registry = ALL_REGISTRY.getIfPresent(registryConfig);
    if (null == registry) {
      Registry ext =
          ExtensionLoaderFactory.getExtensionLoader(Registry.class)
              .getExtension(
                  registryConfig.getProtocol(),
                  new Class[] {RegistryConfig.class},
                  new Object[] {registryConfig});
      ext.init();
      ALL_REGISTRY.put(registryConfig, ext);
    }
    return registry;
  }
}
