package com.alipay.sofa.rpc.config;

import com.alipay.sofa.rpc.common.RpcConfigs;
import com.alipay.sofa.rpc.common.RpcOptions;

public abstract class AbstractInterfaceConfig<T, S extends AbstractInterfaceConfig>
    extends AbstractIdConfig<S> {

  /** 应用信息 */
  private ApplicationConfig applicationConfig = new ApplicationConfig();

  private String uniqueId = RpcConfigs.getStringValue(RpcOptions.DEFAULT_UNIQUEID);

  /**
   * Get uniqueId
   *
   * @return uniqueId
   */
  public String getUniqueId() {
    return uniqueId;
  }

  /**
   * Set uniqueId
   *
   * @param uniqueId
   * @return
   */
  public S setUniqueId(String uniqueId) {
    ConfigValueChecker.checkNormalWithCommaColon("uniqueId", uniqueId);
    this.uniqueId = uniqueId;
    return castThis();
  }

  /**
   * Get ApplicationConfig
   *
   * @return
   */
  public ApplicationConfig getApplicationConfig() {
    return applicationConfig;
  }

  /**
   * Set ApplicationConfig
   *
   * @param applicationConfig
   * @return
   */
  public AbstractInterfaceConfig setApplicationConfig(ApplicationConfig applicationConfig) {
    if (null == applicationConfig) {
      applicationConfig = new ApplicationConfig();
    }
    this.applicationConfig = applicationConfig;
    return castThis();
  }
}
