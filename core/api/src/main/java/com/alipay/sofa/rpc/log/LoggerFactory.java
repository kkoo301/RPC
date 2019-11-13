package com.alipay.sofa.rpc.log;

import com.alipay.sofa.rpc.common.RpcConfigs;
import com.alipay.sofa.rpc.common.RpcOptions;
import com.alipay.sofa.rpc.common.utils.ClassUtils;
import com.alipay.sofa.rpc.core.exception.SofaRpcRuntimeException;

public class LoggerFactory {

  /** 配置的实现类 */
  private static String implClass = RpcConfigs.getStringValue(RpcOptions.LOGGER_IMPL);

  public static Logger getLogger(String name) {
    try {
      Object instance =
          ClassUtils.forName(implClass, Logger.class.getClassLoader())
              .getConstructor(String.class)
              .newInstance(name);
      if (instance instanceof Logger) {
        return (Logger) instance;
      } else {
        throw new SofaRpcRuntimeException(implClass + " is not type of " + Logger.class);
      }
    } catch (Exception e) {
      throw new SofaRpcRuntimeException(
          "Error when getLogger of " + name + ", implement is " + implClass + "", e);
    }
  }

  public static Logger getLogger(Class clazz) {
    try {
      Object instance =
          ClassUtils.forName(implClass, Logger.class.getClassLoader())
              .getConstructor(Class.class)
              .newInstance(clazz);
      if (instance instanceof Logger) {
        return (Logger) instance;
      } else {
        throw new SofaRpcRuntimeException(implClass + " is not type of " + Logger.class);
      }
    } catch (Exception e) {
      throw new SofaRpcRuntimeException(
          "Error when getLogger of " + clazz.getName() + ", implement is " + implClass + "", e);
    }
  }
}
