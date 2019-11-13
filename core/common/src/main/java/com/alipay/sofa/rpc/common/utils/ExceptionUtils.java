package com.alipay.sofa.rpc.common.utils;

import com.alipay.sofa.rpc.core.exception.SofaRpcRuntimeException;

public class ExceptionUtils {
  public static SofaRpcRuntimeException buildRuntime(
      String configKey, String configValue, String message) {
    String msg =
        "The value of config " + configKey + " [" + configValue + "] is illegal, " + message;
    return new SofaRpcRuntimeException(msg);
  }

  /**
   * 返回消息+简短堆栈信息（e.printStackTrace()的内容）
   *
   * @param e Throwable
   * @param stackLevel 堆栈层级
   * @return 异常堆栈信息
   */
  public static String toShortString(Throwable e, int stackLevel) {
    StackTraceElement[] traces = e.getStackTrace();
    StringBuilder sb = new StringBuilder(1024);
    sb.append(e.toString()).append("\t");
    if (traces != null) {
      for (int i = 0; i < traces.length; i++) {
        if (i < stackLevel) {
          sb.append("\tat ").append(traces[i]).append("\t");
        } else {
          break;
        }
      }
    }
    return sb.toString();
  }
}
