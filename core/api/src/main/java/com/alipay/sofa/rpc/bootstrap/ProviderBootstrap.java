package com.alipay.sofa.rpc.bootstrap;

import com.alipay.sofa.rpc.base.Destroyable;
import com.alipay.sofa.rpc.base.Initializable;
import com.alipay.sofa.rpc.ext.Extensible;

@Extensible(singleton = false)
public interface ProviderBootstrap<T> extends Destroyable, Initializable {

  /** 发布一个服务 */
  void export();

  /** 取消发布一个服务 */
  void unExport();
}
