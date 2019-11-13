package com.alipay.sofa.rpc.invoke;

import com.alipay.sofa.rpc.core.request.SofaRequest;
import com.alipay.sofa.rpc.core.response.SofaResponse;

public interface Invoker {

  SofaResponse invoker(SofaRequest sofaRequest);
}
