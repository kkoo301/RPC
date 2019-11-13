package com.alipay.sofa.rpc.registry;

import com.alipay.sofa.rpc.base.Destroyable;
import com.alipay.sofa.rpc.base.Initializable;
import com.alipay.sofa.rpc.config.ProviderConfig;
import com.alipay.sofa.rpc.ext.Extensible;

@Extensible(singleton = false)
public interface Registry extends Destroyable, Initializable {

  /**
   * 启动
   *
   * @return is started
   */
  boolean start();

  /**
   * 注册服务提供者
   *
   * @param config Provider配置
   */
  void register(ProviderConfig config);
}
