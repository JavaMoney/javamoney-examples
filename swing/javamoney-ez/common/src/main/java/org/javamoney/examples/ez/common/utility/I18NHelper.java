// I18NHelper

package org.javamoney.examples.ez.common.utility;

import static org.javamoney.examples.ez.common.utility.ResourceHelper.getResource;

import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Properties;

/**
 * This class facilitates accessing internationalized properties. All methods in
 * this class are static.
 */
public
final
class
I18NHelper
{
  /**
   * This method returns the language being used for loading properties files.
   *
   * @return The language being used for loading properties files.
   */
  public
  static
  Locale
  getLanguage()
  {
    return itsLanguage;
  }

  /**
   * This method the available languages.
   *
   * @return An array of the available languages.
   */
  public
  static
  Locale[]
  getLanguages()
  {
    Locale[] languages = {
        ENGLISH,
        FINNISH,
        ITALIAN,
        PORTUGUESE,
        RUSSIAN
    };

    return languages;
  }

  /**
   * This method returns a value from the properties.
   *
   * @param key The key that references the value.
   *
   * @return A value from the properties.
   */
  public
  static
  String
  getProperty(String key)
  {
    return PROPERTIES.getProperty(key, DEFAULT_PROPERTY);
  }

  /**
   * This method returns a value from the properties where the key is prefixed
   * with "shared.".
   * <p>
   * <b>Note:</b> It is good practice to use this scheme for properties that are
   * shared among numerous classes. Otherwise, the protocol is to prefix the key
   * with the class' simple name.
   *
   * @param key The key that references the value.
   *
   * @return A value from the properties.
   */
  public
  static
  String
  getSharedProperty(String key)
  {
    return getProperty("shared." + key);
  }

  /**
   * This method loads the common properties for the currently set language.
   */
  public
  static
  void
  loadCommonProperties()
  {
    loadProperties("org/javamoney/examples/ez/common/i18n/");
  }

  /**
   * This method reads in the properties file for the currently set language.
   * <p>
   * <b>Note:</b> This method is expecting the properties file name to equal
   * that of the set language's "ISO 639-2/T" code. Example, for English, the
   * file name would be "eng.properties".
   *
   * @param folder The folder that contains the properties files.
   */
  public
  static
  void
  loadProperties(String folder)
  {
    try
    {
      URL url = getResource(folder + getPropertiesFile());

      PROPERTIES.load(url.openStream());
    }
    catch(Exception exception)
    {
      // Ignored.
    }
  }

  /**
   * This method sets the language.
   *
   * @param locale The language to use.
   */
  public
  static
  void
  setLanguage(Locale locale)
  {
    itsLanguage = locale;
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of private methods.
  //////////////////////////////////////////////////////////////////////////////

  private
  static
  String
  getPropertiesFile()
  {
    ArrayList<String> languages = new ArrayList<String>(getLanguages().length);
    String iso3 = getLanguage().getISO3Language();
    String file = null;

    // Add available language properties files.
    for(Locale locale : getLanguages())
    {
      languages.add(locale.getISO3Language());
    }

    // If the language does not have a properties file, default to English.
    if(languages.contains(iso3) == true)
    {
      file = iso3;
    }
    else
    {
      file = ENGLISH.getISO3Language();
    }

    return file + ".properties";
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  private static Locale itsLanguage;

  private static final Properties PROPERTIES = new Properties();

  /**
   * The default value for a property when a key is not found.
   */
  public static final String DEFAULT_PROPERTY = "null";
  /**
   * A constant for the English language.
   */
  public static final Locale ENGLISH = Locale.US;
  /**
   * A constant for the Finnish language.
   */
  public static final Locale FINNISH = new Locale("fi", "fi");
  /**
   * A constant for the Italian language.
   */
  public static final Locale ITALIAN = Locale.ITALY;
  /**
   * A constant for the Portuguese (Brazil) language.
   */
  public static final Locale PORTUGUESE = new Locale("pt", "br");
  /**
   * A constant for the Russian language.
   */
  public static final Locale RUSSIAN = new Locale("ru", "ru");
}
