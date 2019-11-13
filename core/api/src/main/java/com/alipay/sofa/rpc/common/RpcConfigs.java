package com.alipay.sofa.rpc.common;

import com.alibaba.fastjson.JSON;
import com.alipay.sofa.rpc.common.utils.CompatibleTypeUtils;
import com.alipay.sofa.rpc.common.utils.FileUtils;
import com.alipay.sofa.rpc.core.exception.SofaRpcRuntimeException;
import org.apache.commons.collections4.MapUtils;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static java.nio.charset.StandardCharsets.UTF_8;

/** 配置加载器和操作入口 */
public class RpcConfigs {

  private RpcConfigs() {}

  /** 全部配置 */
  private static final ConcurrentHashMap<String, Object> CFG = new ConcurrentHashMap();

  static {
    init();
  }

  private static void init() {
    try {
      String cfgStr = FileUtils.file2String(RpcConfigs.class, "rpc-config-default.json", UTF_8);
      Map cfgMap = JSON.parseObject(cfgStr, Map.class);
      CFG.putAll(cfgMap);
    } catch (IOException e) {
      throw new SofaRpcRuntimeException("Catch Exception When Load RpcConfigs :", e);
    }
  }

  public static String getStringValue(String key) {
    String value = MapUtils.getString(CFG, key);
    if (null == value) {
      throw new SofaRpcRuntimeException("not found key : " + key);
    }
    return value;
  }

  public static <T> T getOrDefaultValue(String key, T defaultValue) {
    Object value = MapUtils.getObject(CFG, key);
    if (null == value) {
      return defaultValue;
    } else {
      Class<?> classType = defaultValue == null ? null : defaultValue.getClass();
      return (T) CompatibleTypeUtils.convert(value, classType);
    }
  }

  public static int getIntValue(String key) {
    return Integer.parseInt(getStringValue(key));
  }

  public static long getLongValue(String key) {
    return Long.parseLong(getStringValue(key));
  }

  public static boolean getBooleanValue(String key) {
    return Boolean.parseBoolean(getStringValue(key));
  }

  public static byte getByteValue(String key) {
    return Byte.parseByte(getStringValue(key));
  }
}
