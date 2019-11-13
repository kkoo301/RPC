package com.alipay.sofa.rpc.core.exception;

public class SofaRpcExcetion extends RuntimeException {

  protected int errorType = RpcErrorType.UNKNOWN;

  protected SofaRpcExcetion() {}

  public SofaRpcExcetion(int errorType, String message) {
    super(message);
    this.errorType = errorType;
  }

  public SofaRpcExcetion(int errorType, Throwable cause) {
    super(cause);
    this.errorType = errorType;
  }

  public SofaRpcExcetion(int errorType, String message, Throwable cause) {
    super(message, cause);
    this.errorType = errorType;
  }

  public int getErrorType() {
    return this.errorType;
  }
}
