package com.alipay.sofa.rpc.core.exception;

public class SofaRpcRuntimeException extends RuntimeException {

  protected SofaRpcRuntimeException() {}

  public SofaRpcRuntimeException(String message) {
    super(message);
  }

  public SofaRpcRuntimeException(String message, Throwable cause) {
    super(message, cause);
  }

  public SofaRpcRuntimeException(Throwable cause) {
    super(cause);
  }
}
