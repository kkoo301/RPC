package com.alipay.sofa.rpc.common.utils;

import com.google.common.io.Resources;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Properties;

public class FileUtils {

  /** 回车符 */
  public static final String LINE_SEPARATOR = System.getProperty("line.separator");

  public static String file2String(Class clazz, String resourcesName, Charset charset)
      throws IOException {

    URL url = Resources.getResource(clazz, resourcesName);
    List<String> lineList = Resources.readLines(url, charset);
    StringBuilder sb = new StringBuilder();
    for (String line : lineList) {
      sb.append(line).append(LINE_SEPARATOR);
    }
    return sb.toString();
  }

  public static Properties readUrl2Properties(URL url, Charset charset) throws IOException {
    List<String> lineList = Resources.readLines(url, charset);
    StringBuilder sb = new StringBuilder();
    for (String line : lineList) {
      sb.append(line).append(LINE_SEPARATOR);
    }

    Reader reader = null;
    try {
      reader = new StringReader(sb.toString());
      Properties properties = new Properties();
      properties.load(reader);
      return properties;
    } finally {
      if (null != reader) {
        reader.close();
      }
    }
  }
}
