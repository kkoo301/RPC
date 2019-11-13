package com.alipay.sofa.rpc.config;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class AbstractIdConfig<S extends AbstractIdConfig> implements Serializable {

  private static final AtomicInteger ID_GENERATOR = new AtomicInteger(0);

  private String id;

  public String getId() {
    if (null == id) {
      synchronized (this) {
        if (null == id) {
          id = "rpc-cfg-" + ID_GENERATOR.getAndIncrement();
        }
      }
    }
    return id;
  }

  public S setId(String id) {
    this.id = id;
    return castThis();
  }

  protected S castThis() {
    return (S) this;
  }
}
