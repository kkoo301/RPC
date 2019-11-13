package com.alipay.sofa.rpc.bootstrap;

import com.alipay.sofa.rpc.common.utils.StringUtils;
import com.alipay.sofa.rpc.config.ProviderConfig;
import com.alipay.sofa.rpc.config.RegistryConfig;
import com.alipay.sofa.rpc.config.ServerConfig;
import org.junit.Test;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

public class ProviderTest {

    private String str;

    private int port;

    @Test
    public void mainTest() {

        List<RegistryConfig> registryConfigList = new ArrayList<>();

        ServerConfig serverConfig = new ServerConfig();
        serverConfig.setHost("loaclhost");
        serverConfig.setPort(8080);
        serverConfig.setProtocol("bolt");

        RegistryConfig registryConfig = new RegistryConfig().setProtocol("zookeeper").setAddress("127.0.0.1:2181");
        registryConfigList.add(registryConfig);

        ProviderConfig providerConfig = new ProviderConfig();
        providerConfig.setBootstrap("sofa");
        providerConfig.setServer(serverConfig);
        providerConfig.setRegistrys(registryConfigList);

        ProviderBootstrap bootstrap = Bootstraps.from(providerConfig);
        bootstrap.export();
    }

    @Test
    public void addrTest() {
        String hostAddress = new InetSocketAddress(8080).getAddress().getHostAddress();
        System.out.println(hostAddress);
        System.out.println(StringUtils.isBlank(str));
        System.out.println(port);
    }
}
