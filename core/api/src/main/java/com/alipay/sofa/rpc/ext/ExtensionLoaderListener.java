package com.alipay.sofa.rpc.ext;

/** 当扩展点加载时，可以做一些事情，例如解析code，初始化等动作 */
public interface ExtensionLoaderListener<T> {

  /**
   * 当扩展点加载时，触发的事件
   *
   * @param extensionClass 扩展点类对象
   */
  void onLoad(ExtensionClass<T> extensionClass);
}
