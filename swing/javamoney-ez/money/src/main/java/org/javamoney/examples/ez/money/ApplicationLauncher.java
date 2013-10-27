// ApplicationLauncher

package org.javamoney.examples.ez.money;

import static org.javamoney.examples.ez.common.utility.I18NHelper.ENGLISH;
import static org.javamoney.examples.ez.common.utility.I18NHelper.getLanguage;
import static org.javamoney.examples.ez.common.utility.I18NHelper.loadCommonProperties;
import static org.javamoney.examples.ez.common.utility.I18NHelper.loadProperties;
import static org.javamoney.examples.ez.common.utility.I18NHelper.setLanguage;
import static org.javamoney.examples.ez.common.utility.LookAndFeelHelper.initializeLookAndFeel;
import static javax.swing.SwingUtilities.invokeLater;
import static org.javamoney.examples.ez.money.utility.DialogHelper.error;
import static org.javamoney.examples.ez.money.utility.FileMapHelper.getFileMap;
import static org.javamoney.examples.ez.money.utility.FileMapHelper.initializeAndCache;
import static org.javamoney.examples.ez.money.utility.StartupHelper.runStartup;

import java.util.Locale;

import org.javamoney.examples.ez.common.utility.I18NHelper;

/**
 * This class, instantiated by the JVM, is the entrance point for the
 * application. This class facilitates launching the application. All methods in
 * this class are static.
 */
public
final
class
ApplicationLauncher
{
  /**
   * This method, called by the JVM, launches the application.
   *
   * @param args The passed-in arguments (ignored).
   */
  public
  static
  void
  main(final String[] args)
  {
    // Set language and load properties files.
    setLanguage(I18NHelper.ENGLISH);
    loadCommonProperties();
    loadProperties(I18N_PATH);

    // For languages other than English, set the default locale to set the JVM's
    // language.
    if(getLanguage() != ENGLISH)
    {
      Locale.setDefault(getLanguage());
    }

    try
    {
      doStartup();

      invokeLater(new ApplicationThread());
    }
    catch(Exception exception)
    {
      // Ignored.
    }
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of private methods.
  //////////////////////////////////////////////////////////////////////////////

  private
  static
  void
  doStartup()
  {
    // Initialize the document base and cache the project's files.
    initializeAndCache();

    if(getFileMap().getDocumentBase().exists() == true)
    {
      try
      {
        initializeLookAndFeel();

        // Load the program's data.
        runStartup();
      }
      catch(Exception exception)
      {
        // Something has happened that cannot be recovered from.
        showFatalErrorMessage();
        System.exit(1);
      }
    }
    else
    {
      showInitializationErrorMessage();
    }
  }

  private
  static
  String
  getProperty(String key)
  {
    return I18NHelper.getProperty("ApplicationLauncher." + key);
  }

  private
  static
  void
  showFatalErrorMessage()
  {
    error(getProperty("fatal.title"), getProperty("fatal.description"));
  }

  private
  static
  void
  showInitializationErrorMessage()
  {
    error(getProperty("init.title"), getProperty("init.description"));
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  private static final String I18N_PATH = "org/javamoney/examples/ez/money/i18n/";
}
