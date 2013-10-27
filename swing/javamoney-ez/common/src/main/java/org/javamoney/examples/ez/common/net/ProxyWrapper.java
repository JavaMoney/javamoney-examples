// ProxyWrapper

package org.javamoney.examples.ez.common.net;

import static java.net.Proxy.NO_PROXY;
import static java.net.Proxy.Type.HTTP;
import static org.javamoney.examples.ez.common.net.ProxyConnectionTypeKeys.DIRECT;
import static org.javamoney.examples.ez.common.net.ProxyConnectionTypeKeys.MANUAL;

import java.net.InetSocketAddress;
import java.net.Proxy;

/**
 * This class facilitates wrapping together the necessary elements of connecting
 * to the Internet through a proxy.
 */
public
final
class
ProxyWrapper
{
  /**
   * Constructs a new generic proxy wrapper.
   */
  public
  ProxyWrapper()
  {
    setAddress("");
    setConnectionType(DIRECT);
    setPort(-1);
    setType(HTTP);
  }

  /**
   * This method creates and returns a proxy according to the configuration.
   *
   * @return A proxy according to the configuration.
   */
  public
  Proxy
  createProxy()
  {
    Proxy proxy = null;

    if(getConnectionType() == MANUAL)
    {
      InetSocketAddress address = new InetSocketAddress(getAddress(), getPort());

      proxy = new Proxy(getType(), address);
    }
    else
    {
      proxy = NO_PROXY;
    }

    return proxy;
  }

  /**
   * This method returns the proxy's address.
   *
   * @return The proxy's address.
   */
  public
  String
  getAddress()
  {
    return itsProxyAddress;
  }

  /**
   * This method returns the proxy connection type.
   *
   * @return The proxy connection type.
   */
  public
  ProxyConnectionTypeKeys
  getConnectionType()
  {
    return itsProxyConnectionType;
  }

  /**
   * This method returns the proxy's port.
   *
   * @return The proxy's port.
   */
  public
  int
  getPort()
  {
    return itsProxyPort;
  }

  /**
   * This method returns the proxy's type.
   *
   * @return The proxy's type.
   */
  public
  Proxy.Type
  getType()
  {
    return itsProxyType;
  }

  /**
   * This method sets the proxy's address.
   *
   * @param address The proxy's address.
   */
  public
  void
  setAddress(String address)
  {
    itsProxyAddress = address;
  }

  /**
   * This method sets the proxy connection type.
   *
   * @param key The proxy connection type.
   */
  public
  void
  setConnectionType(ProxyConnectionTypeKeys key)
  {
    itsProxyConnectionType = key;
  }

  /**
   * This method sets the proxy's port.
   *
   * @param port The proxy's port.
   */
  public
  void
  setPort(int port)
  {
    itsProxyPort = port;
  }

  /**
   * This method sets the proxy's type.
   *
   * @param type The proxy's type.
   */
  public
  void
  setType(Proxy.Type type)
  {
    itsProxyType = type;
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  private String itsProxyAddress;
  private ProxyConnectionTypeKeys itsProxyConnectionType;
  private int itsProxyPort;
  private Proxy.Type itsProxyType;
}
