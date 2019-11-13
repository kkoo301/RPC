package com.alipay.sofa.rpc.config;

import java.io.Serializable;

public class ApplicationConfig implements Serializable {

  private String appId;

  private String insId;

  private String appName;

  public String getAppId() {
    return appId;
  }

  public ApplicationConfig setAppId(String appId) {
    this.appId = appId;
    return this;
  }

  public String getInsId() {
    return insId;
  }

  public ApplicationConfig setInsId(String insId) {
    this.insId = insId;
    return this;
  }

  public String getAppName() {
    return appName;
  }

  public ApplicationConfig setAppName(String appName) {
    this.appName = appName;
    return this;
  }
}
