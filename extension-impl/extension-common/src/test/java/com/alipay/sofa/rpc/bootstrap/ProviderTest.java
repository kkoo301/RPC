package com.alipay.sofa.rpc.bootstrap;

import com.alipay.sofa.rpc.common.utils.StringUtils;
import com.alipay.sofa.rpc.config.ProviderConfig;
import com.alipay.sofa.rpc.config.ServerConfig;
import org.junit.Test;

import java.net.InetSocketAddress;

public class ProviderTest {

    private String str;

    private int port;

    @Test
    public void mainTest() {

        ServerConfig serverConfig = new ServerConfig();
        serverConfig.setHost("loaclhost");
        serverConfig.setPort(8080);
        serverConfig.setProtocol("bolt");

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
        System.out.println(StringUtils.isBlank(str));
        System.out.println(port);
    }
}
