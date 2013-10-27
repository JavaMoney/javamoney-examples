// TransactionTypeKeys

package org.javamoney.examples.ez.money.model.dynamic.transaction;

import org.javamoney.examples.ez.common.utility.I18NHelper;

/**
 * This enumerated class provides keys for transaction types.
 */
public
enum
TransactionTypeKeys
{
  /**
   * A transaction where the amount is below zero inclusive.
   */
  EXPENSE(I18NHelper.getSharedProperty("expense")),
  /**
   * A transaction where the amount is above zero exclusive.
   */
  INCOME(I18NHelper.getSharedProperty("income")),
  /**
   * A transaction that is linked between two accounts.
   */
  TRANSFER(I18NHelper.getSharedProperty("transfer"));

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
  TransactionTypeKeys(String identifier)
  {
    itsIdentifier = identifier;
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  private String itsIdentifier;
}
