package com.alipay.sofa.rpc.ext;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

public class ExtensionLoaderFactory {

  public ExtensionLoaderFactory() {}

  private static final Cache<Class, ExtensionLoader> LOADER_MAP = CacheBuilder.newBuilder().build();

  /**
   * Get extension loader by extensible class with listener
   *
   * @param clazz Extensible class
   * @param listener Listener of ExtensionLoader
   * @param <T> Class
   * @return ExtensionLoader of this class
   */
  public static <T> ExtensionLoader<T> getExtensionLoader(
      Class<T> clazz, ExtensionLoaderListener<T> listener) {
    ExtensionLoader<T> loader = LOADER_MAP.getIfPresent(clazz);
    if (null == loader) {
      synchronized (ExtensionLoaderFactory.class) {
        loader = LOADER_MAP.getIfPresent(clazz);
        if (null == loader) {
          loader = new ExtensionLoader<T>(clazz, listener);
          LOADER_MAP.put(clazz, loader);
        }
      }
    }
    return loader;
  }

  /**
   * Get extension loader by extensible class without listener
   *
   * @param clazz Extensible class
   * @param <T> Class
   * @return ExtensionLoader of this class
   */
  public static <T> ExtensionLoader<T> getExtensionLoader(Class<T> clazz) {
    return getExtensionLoader(clazz, null);
  }
}
