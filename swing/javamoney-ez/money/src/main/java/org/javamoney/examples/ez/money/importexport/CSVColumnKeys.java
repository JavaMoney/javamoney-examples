// CSVColumnKeys

package org.javamoney.examples.ez.money.importexport;

import org.javamoney.examples.ez.common.utility.I18NHelper;

/**
 * This enumerated class provides keys for the types of CSV columns.
 */
public
enum
CSVColumnKeys
{
  AMOUNT(I18NHelper.getSharedProperty("amount")),
  CHECK_NUMBER(I18NHelper.getSharedProperty("check_number")),
  DATE(I18NHelper.getSharedProperty("date")),
  PAYEE(I18NHelper.getSharedProperty("payee")),
  NOTES(I18NHelper.getSharedProperty("notes"));

  /**
   * This method returns the enum constant who's ordinal equals the specified
   * index.
   *
   * @param index The enum constant's ordinal.
   *
   * @return The enum constant who's ordinal equals the specified index.
   */
  protected
  static
  CSVColumnKeys
  valueOf(int index)
  {
    CSVColumnKeys key = null;

    if(index == AMOUNT.ordinal())
    {
      key = AMOUNT;
    }
    else if(index == DATE.ordinal())
    {
      key = DATE;
    }
    else if(index == CHECK_NUMBER.ordinal())
    {
      key = CHECK_NUMBER;
    }
    else if(index == PAYEE.ordinal())
    {
      key = PAYEE;
    }
    else
    {
      key = NOTES;
    }

    return key;
  }

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
  CSVColumnKeys(String identifier)
  {
    itsIdentifier = identifier;
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  private String itsIdentifier;
}
