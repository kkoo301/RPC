package com.alipay.sofa.rpc;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.junit.Test;

public class CacheTest {

  private static final Cache<String, String> cache = CacheBuilder.newBuilder().build();

  @Test
  public void cahcheTest1() {
    cache.put("1", "1=1");

    String string = cache.getIfPresent("1");
    System.out.println(string);
    cache.invalidateAll();
    string = cache.getIfPresent("1");
    System.out.println(string);
  }
}
