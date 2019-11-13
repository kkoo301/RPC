package com.alipay.sofa.rpc.context;

import java.util.ArrayDeque;
import java.util.Deque;

public class RPCInternalContext implements Cloneable {

    //
    private static final ThreadLocal<RPCInternalContext> LOCAL = new ThreadLocal<>();

    private static final ThreadLocal<Deque<RPCInternalContext>> QUE_LOCAL = new ThreadLocal<>();

    private RPCInternalContext() {
    }

    /**
     * 设置上下文
     *
     * @param context
     */
    public static void setContext(RPCInternalContext context) {
        LOCAL.set(context);
    }

    /**
     * 初始化 RPC 上下文，如果没有则新建
     *
     * @return
     */
    public static RPCInternalContext getContext() {
        RPCInternalContext context = LOCAL.get();
        if (null == context) {
            context = new RPCInternalContext();
            LOCAL.set(context);
        }
        return context;
    }

    /**
     * 将上下文 下移一层
     */
    public static void pushContext() {
        RPCInternalContext context = LOCAL.get();
        if (null != context) {
            Deque<RPCInternalContext> deque = QUE_LOCAL.get();
            if (null == deque) {
                deque = new ArrayDeque<>();
                QUE_LOCAL.set(deque);
            }
            deque.push(context);
            LOCAL.set(null);
        }
    }

    /**
     * 将上下文 上移一层
     */
    public static void popContext() {
        Deque<RPCInternalContext> deque = QUE_LOCAL.get();
        if (null != deque) {
            RPCInternalContext peek = deque.peek();
            if (null != peek) {
                LOCAL.set(deque.pop());
            }
        }

    }

    /**
     * 清理所有的上下文
     */
    public static void clearAllContext() {
        LOCAL.remove();
        QUE_LOCAL.remove();
    }


}
