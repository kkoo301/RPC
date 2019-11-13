package com.alipay.sofa.rpc.server;

import com.alipay.sofa.rpc.config.ProviderConfig;
import com.alipay.sofa.rpc.invoke.Invoker;
import com.alipay.sofa.rpc.log.Logger;
import com.alipay.sofa.rpc.log.LoggerFactory;
import com.alipay.sofa.rpc.utils.NettyEventLoopUtil;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;

import java.net.InetSocketAddress;

public class RPCServer extends AbstractServer {

  private static final Logger LOGGER = LoggerFactory.getLogger(RPCServer.class);

  /** server bootstrap */
  private ServerBootstrap bootstrap;

  private ChannelFuture channelFuture;

  private final EventLoopGroup bossGroup =
      NettyEventLoopUtil.newEvenLoopGroup(
          1, new ThreadFactoryBuilder().setNameFormat("boss-thread-%s").setDaemon(false).build());

  private final EventLoopGroup workerGroup =
      NettyEventLoopUtil.newEvenLoopGroup(
          Runtime.getRuntime().availableProcessors() * 2,
          new ThreadFactoryBuilder().setNameFormat("worker-thread-%s").setDaemon(false).build());

  public RPCServer(int port) {
    super(port);
  }

  public RPCServer(String ip, int port) {
    super(ip, port);
  }

  @Override
  void doInit() {
    this.bootstrap = new ServerBootstrap();
    this.bootstrap
        .group(bossGroup, workerGroup)
        .channel(NettyEventLoopUtil.getServerSocketChannelClass());

    NettyEventLoopUtil.enableTriggeredMode(bootstrap);

    this.bootstrap.childHandler(
        new ChannelInitializer() {
          @Override
          protected void initChannel(Channel channel) throws Exception {
            ChannelPipeline pipeline = channel.pipeline();
          }
        });
  }

  @Override
  boolean doStart() throws InterruptedException {
    this.channelFuture = this.bootstrap.bind(new InetSocketAddress(ip, port)).sync();
    return channelFuture.isSuccess();
  }

  @Override
  void doStop() {}

  @Override
  public void destroy(DestroyHook hook) {}

  public void registerProcessor(ProviderConfig providerConfig, Invoker instance) {}

  public void unRegisterProcessor(ProviderConfig providerConfig, boolean closeIfNoEntry) {}

  @Override
  public void init() {}
}
