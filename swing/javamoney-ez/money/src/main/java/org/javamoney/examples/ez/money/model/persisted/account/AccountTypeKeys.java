// AccountTypeKeys

package org.javamoney.examples.ez.money.model.persisted.account;

import org.javamoney.examples.ez.common.utility.I18NHelper;

/**
 * This enumerated class provides keys for account types.
 */
public
enum
AccountTypeKeys
{
  // Declared in order they appear in chooser.
  /**
   * A standard banking account.
   */
  DEPOSIT(I18NHelper.getProperty("AccountTypeKeys.deposit")),
  /**
   * An account that has a revolving line of credit.
   */
  CREDIT(I18NHelper.getProperty("AccountTypeKeys.credit")),
  /**
   * An account associated with just cash.
   */
  CASH(I18NHelper.getProperty("AccountTypeKeys.cash"));

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
  AccountTypeKeys(String identifier)
  {
    itsIdentifier = identifier;
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  private String itsIdentifier;
}
