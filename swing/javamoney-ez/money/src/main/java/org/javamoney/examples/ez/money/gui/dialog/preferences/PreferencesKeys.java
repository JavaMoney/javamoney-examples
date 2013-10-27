// PreferencesKeys

package org.javamoney.examples.ez.money.gui.dialog.preferences;

import javax.swing.Icon;

import org.javamoney.examples.ez.money.IconKeys;

import org.javamoney.examples.ez.common.utility.I18NHelper;

/**
 * This enumerated class provides keys for convenient access to the preferences
 * panels.
 */
public
enum
PreferencesKeys
{
  // Declared in order they appear in tabs.
  /**
   * The panel for the general options.
   */
  GENERAL(getProperty("general"), IconKeys.PREFERENCES_GENERAL),
  /**
   * The panel for managing accounts.
   */
  ACCOUNTS(I18NHelper.getSharedProperty("accounts"), IconKeys.PREFERENCES_ACCOUNTS),
  /**
   * The panel for managing payees.
   */
  PAYEES(I18NHelper.getSharedProperty("payees"), IconKeys.PREFERENCES_PAYEES),
  /**
   * The panel for managing income.
   */
  INCOME(I18NHelper.getSharedProperty("income"), IconKeys.PREFERENCES_CATEGORIES),
  /**
   * The panel for managing expenses.
   */
  EXPENSES(I18NHelper.getSharedProperty("expenses"), IconKeys.PREFERENCES_CATEGORIES),
  /**
   * The panel for managing reminders.
   */
  REMINDERS(I18NHelper.getSharedProperty("reminders"), IconKeys.PREFERENCES_REMINDERS),
  /**
   * The panel for configuring the network.
   */
  NETWORK(getProperty("network"), IconKeys.PREFERENCES_NETWORK);

  //////////////////////////////////////////////////////////////////////////////
  // Start of public methods.
  //////////////////////////////////////////////////////////////////////////////

  /**
   * This method returns the enum constant's description.
   *
   * @return The enum constant's description.
   */
  public
  String
  getDescription()
  {
    String description = "";

    if(this == ACCOUNTS)
    {
      description = getProperty("accounts.description");
    }
    else if(this == EXPENSES)
    {
      description = getProperty("expenses.description");
    }
    else if(this == GENERAL)
    {
      description = getProperty("general.description");
    }
    else if(this == INCOME)
    {
      description = getProperty("income.description");
    }
    else if(this == NETWORK)
    {
      description = getProperty("network.description");
    }
    else if(this == PAYEES)
    {
      description = getProperty("payees.description");
    }
    else if(this == REMINDERS)
    {
      description = getProperty("reminders.description");
    }

    return description;
  }

  /**
   * This method returns the icon.
   *
   * @return The icon.
   */
  public
  Icon
  getIcon()
  {
    return itsIcon;
  }

  /**
   * This method returns an enum instance for the specified index.
   *
   * @param index The index of the enum.
   *
   * @return An enum instance for the specified index.
   */
  public
  static
  PreferencesKeys
  getKey(int index)
  {
    PreferencesKeys keyAt = GENERAL;

    for(PreferencesKeys key : values())
    {
      if(key.ordinal() == index)
      {
        keyAt = key;
        break;
      }
    }

    return keyAt;
  }

  /**
   * This method returns the enum constant's title.
   *
   * @return The enum constant's title.
   */
  public
  String
  getTitle()
  {
    return getProperty("title") + " " + toString() + ".";
  }

  /**
   * This method returns a string for the enum constant.
   *
   * @return A string.
   */
  @Override
  public
  String
  toString()
  {
    return itsIdentifier;
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of private methods.
  //////////////////////////////////////////////////////////////////////////////

  private
  PreferencesKeys(String identifier, IconKeys key)
  {
    itsIcon = key.getIcon();
    itsIdentifier = identifier;
  }

  private
  static
  String
  getProperty(String key)
  {
    return I18NHelper.getProperty("PreferencesKeys." + key);
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  private Icon itsIcon;
  private String itsIdentifier;
}
