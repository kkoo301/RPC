package com.alipay.sofa.rpc.config;

import com.alipay.sofa.rpc.common.RpcConfigs;
import com.alipay.sofa.rpc.common.RpcOptions;
import com.alipay.sofa.rpc.server.Server;
import com.alipay.sofa.rpc.server.ServerFactory;

import java.io.Serializable;

public class ServerConfig<T> extends AbstractInterfaceConfig<T, ProviderConfig<T>>
    implements Serializable {

  private String protocol = RpcConfigs.getStringValue(RpcOptions.DEFAULT_PROTOCOL);

  private String host = RpcConfigs.getStringValue(RpcOptions.SERVER_HOST);

  private int port = RpcConfigs.getIntValue(RpcOptions.SERVER_PORT_START);

  private int coreThread = RpcConfigs.getIntValue(RpcOptions.SERVER_POOL_CORE);

  private int maxThread = RpcConfigs.getIntValue(RpcOptions.SERVER_POOL_MAX);

  private int queues = RpcConfigs.getIntValue(RpcOptions.SERVER_POOL_QUEUE);

  private int aliveTime = RpcConfigs.getIntValue(RpcOptions.SERVER_POOL_ALIVETIME);

  /** 绑定的地址。是某个网卡，还是全部地址 */
  private transient String boundHost;

  /** The Adaptive port. */
  protected boolean adaptivePort = RpcConfigs.getBooleanValue(RpcOptions.SEVER_ADAPTIVE_PORT);

  public String getProtocol() {
    return protocol;
  }

  public Server buildIfAbsent() {
    return ServerFactory.getServer(this);
  }

  public String getHost() {
    return host;
  }

  public void setHost(String host) {
    this.host = host;
  }

  public int getPort() {
    return port;
  }

  public void setPort(int port) {
    this.port = port;
  }

  public void setProtocol(String protocol) {
    this.protocol = protocol;
  }

  public int getCoreThread() {
    return coreThread;
  }

  public void setCoreThread(int coreThread) {
    this.coreThread = coreThread;
  }

  public int getMaxThread() {
    return maxThread;
  }

  public void setMaxThread(int maxThread) {
    this.maxThread = maxThread;
  }

  public int getQueues() {
    return queues;
  }

  public void setQueues(int queues) {
    this.queues = queues;
  }

  public int getAliveTime() {
    return aliveTime;
  }

  public void setAliveTime(int aliveTime) {
    this.aliveTime = aliveTime;
  }

  public String getBoundHost() {
    return boundHost;
  }

  public void setBoundHost(String boundHost) {
    this.boundHost = boundHost;
  }

  public boolean isAdaptivePort() {
    return adaptivePort;
  }

  public void setAdaptivePort(boolean adaptivePort) {
    this.adaptivePort = adaptivePort;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((host == null) ? 0 : host.hashCode());
    result = prime * result + port;
    result = prime * result + ((protocol == null) ? 0 : protocol.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    ServerConfig other = (ServerConfig) obj;
    if (host == null) {
      if (other.host != null) {
        return false;
      }
    } else if (!host.equals(other.host)) {
      return false;
    }
    if (port != other.port) {
      return false;
    }
    if (protocol == null) {
      if (other.protocol != null) {
        return false;
      }
    } else if (!protocol.equals(other.protocol)) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "ServerConfig [protocol=" + protocol + ", port=" + port + ", host=" + host + "]";
  }
}
