package com.alipay.sofa.rpc.bootstrap;

import com.alipay.sofa.rpc.config.ProviderConfig;
import org.junit.Test;

import java.net.InetSocketAddress;

public class ProviderTest {

    @Test
    public void mainTest() {
        ProviderConfig providerConfig = new ProviderConfig();
        providerConfig.setBootstrap("sofa");
        ProviderBootstrap bootstrap = Bootstraps.from(providerConfig);
        bootstrap.export();
    }

    @Test
    public void addrTest(){
        String hostAddress = new InetSocketAddress(8080).getAddress().getHostAddress();
        System.out.println(hostAddress);
    }
}
