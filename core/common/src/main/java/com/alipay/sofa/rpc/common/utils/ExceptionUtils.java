package com.alipay.sofa.rpc.common.utils;

import com.alipay.sofa.rpc.core.exception.SofaRpcRuntimeException;

public class ExceptionUtils {
    public static SofaRpcRuntimeException buildRuntime(String configKey, String configValue, String message) {
        String msg = "The value of config " + configKey + " [" + configValue + "] is illegal, " + message;
        return new SofaRpcRuntimeException(msg);
    }
}
