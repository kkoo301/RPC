package com.alipay.sofa.rpc;

import com.google.common.eventbus.Subscribe;

public class ListenerTest {

  @Subscribe
  public void doAction(String ac) {
    System.out.println("doAction1: " + ac);
  }

  @Subscribe
  public void doAction2(String ac) {
    System.out.println("doAction2: " + ac);
  }
}
