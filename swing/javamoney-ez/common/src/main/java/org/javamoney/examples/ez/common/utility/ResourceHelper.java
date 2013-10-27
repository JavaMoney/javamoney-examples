// ResourceHelper

package org.javamoney.examples.ez.common.utility;

import static org.javamoney.examples.ez.common.CommonConstants.IS_MAC;
import static org.javamoney.examples.ez.common.CommonConstants.IS_WINDOWS;

import java.net.URL;

import javax.swing.ImageIcon;

//import com.apple.eio.FileManager;

/**
 * This class facilitates providing a convenient way to access resources. All
 * methods in this class are static.
 */
public
final
class
ResourceHelper
{
  /**
   * This method creates and returns an icon.
   *
   * @param resource The location of the icon.
   *
   * @return An icon.
   */
  public
  static
  ImageIcon
  createIcon(URL resource)
  {
    return new ImageIcon(resource);
  }

  /**
   * This method returns a url.
   * <p>
   * <b>Note:</b> The path should be relative to the package hierarchy.
   *
   * @param resource The path to the resource relative to the package.
   *
   * @return A url.
   */
  public
  static
  URL
  getResource(String resource)
  {
    return ResourceHelper.class.getClassLoader().getResource(resource);
  }

  /**
   * This method opens the specified URL with the system's default application.
   * This method returns true if it was successful, otherwise false.
   *
   * @param url The URL to open.
   *
   * @return true or false.
   */
  public
  static
  boolean
  openURL(String url)
  {
    boolean result = true;

    try
    {
      if(IS_MAC == true)
      {
//        FileManager.openURL(url);
      }
      else if(IS_WINDOWS == true)
      {
        Runtime.getRuntime().exec("rundll32 url.dll, FileProtocolHandler " + url);
      }
      else
      {
        // The following code is a hack and should not be relied upon.
        String browser = null;

        for(int len = 0; len < LINUX_BROWSERS.length; len++)
        {
          String[] command = new String[] {"which", LINUX_BROWSERS[len]};

          if(Runtime.getRuntime().exec(command).waitFor() == 0)
          {
            browser = LINUX_BROWSERS[len];
            break;
          }
        }

        Runtime.getRuntime().exec(new String[] {browser, url});
      }
    }
    catch(Exception exception)
    {
      result = false;
    }

    return result;
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  private static final String[] LINUX_BROWSERS = {
    "firefox",
    "mozilla",
    "netscape",
    "konqueror",
    "epiphany"
  };
}
