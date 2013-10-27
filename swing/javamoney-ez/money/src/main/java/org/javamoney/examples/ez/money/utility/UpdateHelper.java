// UpdateHelper

package org.javamoney.examples.ez.money.utility;

import static org.javamoney.examples.ez.common.CommonConstants.IS_MAC;
import static org.javamoney.examples.ez.common.utility.I18NHelper.ENGLISH;
import static org.javamoney.examples.ez.common.utility.I18NHelper.FINNISH;
import static org.javamoney.examples.ez.common.utility.I18NHelper.ITALIAN;
import static org.javamoney.examples.ez.common.utility.I18NHelper.getLanguage;
import static org.javamoney.examples.ez.common.utility.I18NHelper.getSharedProperty;
import static org.javamoney.examples.ez.money.utility.DialogHelper.decide;
import static org.javamoney.examples.ez.money.utility.DialogHelper.error;
import static org.javamoney.examples.ez.money.utility.DialogHelper.inform;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;

import org.javamoney.examples.ez.money.ApplicationProperties;
import org.javamoney.examples.ez.money.gui.dialog.ProcessDialog;

import org.javamoney.examples.ez.common.net.ProxyWrapper;
import org.javamoney.examples.ez.common.utility.I18NHelper;
import org.javamoney.examples.ez.common.utility.ResourceHelper;

/**
 * This class facilitates checking for program updates. All methods in this
 * class are static.
 */
public
final
class
UpdateHelper
{
  /**
   * This method connects to the Internet and checks for program updates.
   */
  public
  static
  void
  checkForUpdates()
  {
    new ProcessWorker().showDialog();
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of private methods.
  //////////////////////////////////////////////////////////////////////////////

  private
  static
  String
  getURL()
  {
    String url = VERSION_URL;

    if(IS_MAC == true)
    {
      if(getLanguage() == ENGLISH)
      {
        url += MAC_ENG;
      }
      else if(getLanguage() == FINNISH)
      {
        url += MAC_FIN;
      }
      else if(getLanguage() == ITALIAN)
      {
        url += MAC_ITA;
      }
      else
      {
        url += MAC_POR;
      }
    }
    else
    {
      if(getLanguage() == ENGLISH)
      {
        url += WIN_ENG;
      }
      else if(getLanguage() == FINNISH)
      {
        url += WIN_FIN;
      }
      else if(getLanguage() == ITALIAN)
      {
        url += WIN_ITA;
      }
      else
      {
        url += WIN_POR;
      }
    }

    return url;
  }

  private
  static
  String
  readVersion(URLConnection connection)
  throws IOException
  {
    InputStreamReader reader = new InputStreamReader(connection.getInputStream());
    BufferedReader stream = new BufferedReader(reader);
    String line = stream.readLine();

    stream.close();

    return line;
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of inner classes.
  //////////////////////////////////////////////////////////////////////////////

  private
  static
  class
  ProcessWorker
  extends ProcessDialog
  {
    protected
    ProcessWorker()
    {
      super(getProperty("processing"), 0);
    }

    @Override
    protected
    void
    doProcess()
    {
      try
      {
        ProxyWrapper proxyWrapper = ApplicationProperties.getProxy();
        URL url = new URL(getURL());
        URLConnection connection = url.openConnection(proxyWrapper.createProxy());
        String latestVersion = null;

        connection.setConnectTimeout(TIMEOUT);
        connection.setDefaultUseCaches(false);
        connection.setReadTimeout(TIMEOUT);

        latestVersion = readVersion(connection);

        signalProcessIsDone();

        if(CURRENT_VERSION.equals(latestVersion) == true)
        {
          inform(getProperty("no_update.title"),
              getProperty("no_update.description"));
        }
        else
        {
          if(decide(getProperty("update.title"),
              getProperty("update.description")) == true)
          {
            ResourceHelper.openURL(getSharedProperty("url"));
          }
        }
      }
      catch(Exception exception)
      {
        // Ignore errors if the dialog was canceled.
        if(canProcess() == true)
        {
          signalProcessIsDone();

          if(exception instanceof SocketTimeoutException)
          {
            error(getProperty("error.title"),
                getProperty("network_error.description"));
          }
          else
          {
            error(getProperty("error.title"),
                getProperty("no_service_error.description"));
          }
        }
      }
    }

    ////////////////////////////////////////////////////////////////////////////
    // Start of private methods.
    ////////////////////////////////////////////////////////////////////////////

    private
    static
    String
    getProperty(String key)
    {
      return I18NHelper.getProperty("UpdateHelper." + key);
    }
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  private static final int TIMEOUT = 8000;

  private static final String MAC_ENG = "mac_eng.txt";
  private static final String MAC_FIN = "mac_fin.txt";
  private static final String MAC_ITA = "mac_ita.txt";
  private static final String MAC_POR = "mac_por.txt";
  private static final String WIN_ENG = "win_eng.txt";
  private static final String WIN_FIN = "win_fin.txt";
  private static final String WIN_ITA = "win_ita.txt";
  private static final String WIN_POR = "win_por.txt";
  private static final String VERSION_URL = "http://www.adoracom.com/ezmoney/version_";

  /**
   * The current program version.
   */
  public static final String CURRENT_VERSION = "2.5-SNAPSHOT";
}
