package com.alipay.sofa.rpc.bootstrap;

import com.alipay.sofa.rpc.config.ProviderConfig;
import com.alipay.sofa.rpc.config.ServerConfig;
import org.junit.Test;

import java.net.InetSocketAddress;

public class ProviderTest {

    @Test
    public void mainTest() {

        ServerConfig serverConfig = new ServerConfig();
        serverConfig.setHost("loaclhost");
        serverConfig.setPort(8080);
        serverConfig.setProtocol("unknow");

        ProviderConfig providerConfig = new ProviderConfig();
        providerConfig.setBootstrap("sofa");
        providerConfig.setServer(serverConfig);
        ProviderBootstrap bootstrap = Bootstraps.from(providerConfig);
        bootstrap.export();
    }

    @Test
    public void addrTest(){
        String hostAddress = new InetSocketAddress(8080).getAddress().getHostAddress();
        System.out.println(hostAddress);
    }
}
