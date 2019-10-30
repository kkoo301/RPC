package com.alipay.sofa.rpc.bootstrap;

import com.alipay.sofa.rpc.config.ProviderConfig;
import org.junit.Test;

public class ProviderTest {

    @Test
    public void mainTest() {
        ProviderConfig providerConfig = new ProviderConfig();
        providerConfig.setBootstrap("sofa");
        ProviderBootstrap bootstrap = Bootstraps.from(providerConfig);
        bootstrap.export();
    }
}
