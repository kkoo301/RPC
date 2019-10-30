package com.alipay.sofa.rpc.config;

import java.io.Serializable;
import java.util.concurrent.ExecutorService;

public final class ProviderConfig<T> extends AbstractInterfaceConfig<T, ProviderConfig<T>> implements Serializable {

    private static final long serialVersionUID = -3058073881775315962L;

    private String bootstrap = "";

    private ExecutorService executorService;


    public String getBootstrap() {
        return bootstrap;
    }

    public ProviderConfig setBootstrap(String bootstrap) {
        this.bootstrap = bootstrap;
        return castThis();
    }

    public ExecutorService getExecutorService() {
        return executorService;
    }

    public ProviderConfig setExecutorService(ExecutorService executorService) {
        this.executorService = executorService;
        return castThis();
    }
}
