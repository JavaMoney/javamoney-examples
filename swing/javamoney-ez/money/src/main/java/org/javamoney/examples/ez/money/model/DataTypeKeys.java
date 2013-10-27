// DataTypeKeys

package org.javamoney.examples.ez.money.model;

import org.javamoney.examples.ez.common.utility.I18NHelper;

/**
 * This enumerated class provides keys for the types of data elements.
 */
public
enum
DataTypeKeys
{
  /**
   * User accounts.
   */
  ACCOUNT(I18NHelper.getSharedProperty("account")),
  /**
   * Expenses and incomes.
   */
  CATEGORY(I18NHelper.getSharedProperty("category")),
  /**
   * Where money was received or where money was paid.
   */
  PAYEE(I18NHelper.getProperty("DataTypeKeys.payee")),
  /**
   * User defined reminders.
   */
  REMINDER(I18NHelper.getProperty("DataTypeKeys.reminder"));

  //////////////////////////////////////////////////////////////////////////////
  // Start of public methods.
  //////////////////////////////////////////////////////////////////////////////

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
  DataTypeKeys(String identifier)
  {
    itsIdentifier = identifier;
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  private String itsIdentifier;
}
