package com.alipay.sofa.rpc.server;

import com.alipay.sofa.rpc.base.Destroyable;
import com.alipay.sofa.rpc.base.Initializable;
import com.alipay.sofa.rpc.config.ProviderConfig;
import com.alipay.sofa.rpc.ext.Extensible;
import com.alipay.sofa.rpc.invoke.Invoker;

@Extensible(singleton = false)
public interface Server extends Destroyable, Initializable {

  /** 启动 */
  void start();

  /** 停止 */
  void stop();

  /**
   * 注册服务
   *
   * @param providerConfig 服务提供者配置
   * @param instance 服务提供者实例
   */
  void registerProcessor(ProviderConfig providerConfig, Invoker instance);

  /**
   * 取消注册服务
   *
   * @param providerConfig 服务提供者配置
   * @param closeIfNoEntry 如果没有注册服务，最后一个关闭Server
   */
  void unRegisterProcessor(ProviderConfig providerConfig, boolean closeIfNoEntry);
}
