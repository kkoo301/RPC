package com.alipay.sofa.rpc.core.exception;

public class SofaTimeOutExcetion extends SofaRpcExcetion {

    public SofaTimeOutExcetion(String message) {
        super(RpcErrorType.CLIENT_TIMEOUT, message);
    }

    public SofaTimeOutExcetion(Throwable cause) {
        super(RpcErrorType.CLIENT_TIMEOUT, cause);
    }

    public SofaTimeOutExcetion(int errorType, String message, Throwable cause) {
        super(errorType, message, cause);
    }

}
