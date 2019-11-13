package com.alipay.sofa.rpc.server;

import com.alipay.sofa.rpc.config.ProviderConfig;
import com.alipay.sofa.rpc.core.request.SofaRequest;
import com.alipay.sofa.rpc.core.response.SofaResponse;
import com.alipay.sofa.rpc.invoke.Invoker;

public class ProviderProxyInvoker implements Invoker {

  private final ProviderConfig providerConfig;

  public <T> ProviderProxyInvoker(ProviderConfig<T> providerConfig) {
    this.providerConfig = providerConfig;
  }

  @Override
  public SofaResponse invoker(SofaRequest sofaRequest) {
    return null;
  }
}
