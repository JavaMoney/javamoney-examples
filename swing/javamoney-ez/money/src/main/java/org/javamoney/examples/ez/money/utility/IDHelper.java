// IDHelper

package org.javamoney.examples.ez.money.utility;

import static org.javamoney.examples.ez.common.utility.I18NHelper.getSharedProperty;
import static org.javamoney.examples.ez.money.model.persisted.transaction.Transaction.MAX_PAYEE_LENGTH;
import static org.javamoney.examples.ez.money.utility.DialogHelper.decide;
import static org.javamoney.examples.ez.money.utility.DialogHelper.error;
import static org.javamoney.examples.ez.money.utility.DialogHelper.inform;
import static org.javamoney.examples.ez.money.utility.DialogHelper.prompt;

import org.javamoney.examples.ez.money.model.DataTypeKeys;

import org.javamoney.examples.ez.common.utility.I18NHelper;

/**
 * This class facilitates managing identifiers through the UI or otherwise, and
 * determining if identifiers are acceptable for use. All methods in this class
 * are static.
 */
public
final
class
IDHelper
{
  /**
   * This method returns true if the specified identifier can be used, otherwise
   * false. Identifiers that can be used are ones that are not empty and do not
   * contain any of the predefined special characters.
   *
   * @param identifier The identifier in question.
   *
   * @return true or false.
   */
  public
  static
  boolean
  canUseIdentifier(String identifier)
  {
    boolean result = false;

    if(identifier.length() != 0)
    {
      result = true;

      for(int index = 0; index < identifier.length(); ++index)
      {
        if(isSpecialCharacter(identifier.charAt(index)) == true)
        {
          result = false;
          break;
        }
      }
    }

    return result;
  }

  /**
   * This method returns true if the user decides to remove merge the elements,
   * otherwise false.
   *
   * @return true or false.
   */
  public
  static
  boolean
  confirmMerge()
  {
    return decide(getProperty("in_use.title"), getProperty("merge"));
  }

  /**
   * This method returns true if the user decides to remove the element,
   * otherwise false.
   *
   * @param key The element's key for customizing the dialog.
   *
   * @return true or false.
   */
  public
  static
  boolean
  confirmRemoval(DataTypeKeys key)
  {
    String description = null;
    String title = null;
    String type = " " + key.toString().toLowerCase();

    title = getSharedProperty("remove") + type + "?";
    description = getProperty("remove.description.prefix") + type + " " + getProperty("remove.description");

    return decide(title, description);
  }

  /**
   * This method prompts the user for a new unique identifier for the specified
   * key. The entered value is returned, or null if the dialog was canceled.
   *
   * @param key The element's key for customizing the dialog.
   *
   * @return The entered value, or null if the dialog was canceled.
   */
  public
  static
  String
  promptForAdd(DataTypeKeys key)
  {
    return promptForUID(getProperty("add.title"), key, "");
  }

  /**
   * This method prompts the user for a unique identifier for the specified key
   * with the specified unique identifier already displayed. The entered value
   * is returned, or null if it is the same as the current unique identifier or
   * if the dialog was canceled.
   *
   * @param key The element's key for customizing the dialog.
   * @param identifier The current unique identifier.
   *
   * @return The entered value, or null if it is the same as the current unique
   * identifier or if the dialog was canceled.
   */
  public
  static
  String
  promptForEdit(DataTypeKeys key, String identifier)
  {
    return promptForUID(getSharedProperty("edit"), key, identifier);
  }

  /**
   * This method purges the specified identifier by removing any special
   * characters and trimming it to the maximum allowed length.
   *
   * @param identifier The identifier to purge.
   *
   * @return A identifier free of any special characters and within the maximum
   * length.
   */
  public
  static
  String
  purgeIdentifier(String identifier)
  {
    StringBuffer buffer = new StringBuffer();

    for(int index = 0; index < identifier.length(); ++index)
    {
      char ch = identifier.charAt(index);

      if(isSpecialCharacter(ch) == false)
      {
        buffer.append(ch);
      }
    }

    return IDHelper.truncateID(buffer.toString());
  }

  /**
   * This method displays a custom dialog depending on the specified message
   * regarding the specified key.
   *
   * @param message The message to use.
   * @param key The element's key for customizing the dialog.
   */
  public
  static
  void
  showMessage(MessageKeys message, DataTypeKeys key)
  {
    String type = key.toString();

    if(message == MessageKeys.IN_USE)
    {
      inform(getProperty("in_use.title"), getProperty("in_use.description"));
    }
    else
    {
      error(getProperty("unable_to_remove.title"),
          getProperty("unable_to_remove.description") + " " + type + ".");
    }
  }

  /**
   * This method truncates the specified unique identifier if it exceeds the
   * maximum allowed length.
   *
   * @param identifier The unique identifier to potentially truncate.
   *
   * @return The unique identifier, or a truncated version if it exceeds the
   * maximum allowed length.
   */
  public
  static
  String
  truncateID(String identifier)
  {
    if(identifier.length() > MAX_PAYEE_LENGTH)
    {
      identifier = identifier.substring(0, MAX_PAYEE_LENGTH);
    }

    return identifier;
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of private methods.
  //////////////////////////////////////////////////////////////////////////////

  private
  static
  String
  getProperty(String key)
  {
    return I18NHelper.getProperty("IDHelper." + key);
  }

  private
  static
  boolean
  isSpecialCharacter(char ch)
  {
    boolean result = false;

    for(char special : SPECIAL_CHARACTERS)
    {
      if(special == ch)
      {
        result = true;
        break;
      }
    }

    return result;
  }

  private
  static
  String
  promptForUID(String action, DataTypeKeys key, String identifier)
  {
    String type = key.toString().toLowerCase();
    String description = getProperty("add.description") + " " + type + ".";
    String title = action + " " + type + ".";
    String newID = prompt(title, description, identifier);

    if(newID != null)
    {
      // Make sure the ID is OK to use.
      newID = truncateID(newID);
      newID = purgeIdentifier(newID);

      // If the new identifier cannot be used or if it is the same as the old,
      // set it to null.
      if(newID.equals(identifier) == true)
      {
        newID = null;
      }
    }

    return newID;
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of inner classes.
  //////////////////////////////////////////////////////////////////////////////

  /**
   * This enumerated class provides keys for predefined messages.
   */
  public
  static
  enum
  MessageKeys
  {
    /**
     * A message for already in use.
     */
    IN_USE,
    /**
     * A message for unable to remove.
     */
    UNABLE_TO_REMOVE;
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  private static final char[] SPECIAL_CHARACTERS = {'*', ':', ';', '=', '\\', '[', ']'};
}
