package com.alipay.sofa.rpc.bootstrap;

import com.alipay.sofa.rpc.config.ProviderConfig;
import com.alipay.sofa.rpc.ext.ExtensionLoader;
import com.alipay.sofa.rpc.ext.ExtensionLoaderFactory;

public class Bootstraps {

    /**
     * 发布一个服务
     *
     * @param providerConfig 服务发布者配置
     * @param <T>            接口类型
     * @return 发布启动类
     */
    public static <T> ProviderBootstrap<T> from(ProviderConfig<T> providerConfig) {
        String bootstrap = providerConfig.getBootstrap();
        ExtensionLoader<ProviderBootstrap> extensionLoader = ExtensionLoaderFactory.getExtensionLoader(ProviderBootstrap.class);
        ProviderBootstrap extension = extensionLoader.getExtension(bootstrap, new Class[]{ProviderConfig.class}, new Object[]{providerConfig});
        extension.init();
        return extension;
    }


}
