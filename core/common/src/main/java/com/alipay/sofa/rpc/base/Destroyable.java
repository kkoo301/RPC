package com.alipay.sofa.rpc.base;

public interface Destroyable {

  void destroy(DestroyHook hook);

  /** 销毁钩子 */
  interface DestroyHook {
    /** 销毁前要做的事情 */
    void preDestroy();

    /** 銷毀后要做的事情 */
    void postDestroy();
  }
}
