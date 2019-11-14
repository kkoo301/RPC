package com.alipay.sofa.rpc.filter;

import com.alipay.sofa.rpc.core.request.SofaRequest;
import com.alipay.sofa.rpc.core.response.SofaResponse;
import com.alipay.sofa.rpc.ext.ExtensionLoaderFactory;
import com.alipay.sofa.rpc.invoke.Invoker;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

public class FilterChain implements Invoker {

  public static Cache<String, Filter> PROVIDER_SIDE = CacheBuilder.newBuilder().build();

  public static Cache<String, Filter> CONSUMER_SIDE = CacheBuilder.newBuilder().build();

  static {
    ExtensionLoaderFactory.getExtensionLoader(Filter.class, (extensionClass) -> {
      Class<? extends Filter> clazz = extensionClass.getClazz();
      System.out.println("listener init!");
    });
  }

  @Override
  public SofaResponse invoker(SofaRequest sofaRequest) {
    return null;
  }
}
