package com.alipay.sofa.rpc;

import com.google.common.eventbus.EventBus;
import org.junit.Test;

import java.util.ArrayDeque;
import java.util.Deque;

public class EventBusTest {

  @Test
  public void reg() {
    EventBus eventBus = new EventBus("Test");
    eventBus.register(new ListenerTest());
    eventBus.post("sssssssssš");
  }

  @Test
  public void peek() {
    Deque<String> queue = new ArrayDeque<>();
    queue.push("1");
    queue.push("2");
    queue.push("3");
    queue.push("4");
    queue.push("5");
    queue.push("6");

    String peek = queue.peek();
    System.out.println(peek);
    System.out.println("============");

    String pop = queue.pop();
    System.out.println(pop);
    System.out.println("============");

    for (String str : queue) {
      System.out.println(str);
    }
  }
}
