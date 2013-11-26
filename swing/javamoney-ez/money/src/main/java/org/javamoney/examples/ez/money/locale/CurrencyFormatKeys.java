// CurrencyFormatKeys

package org.javamoney.examples.ez.money.locale;

import org.javamoney.examples.ez.common.utility.I18NHelper;

/**
 * This enumerated class provides keys for the currency formats.
 */
public
enum
CurrencyFormatKeys
{
  // Declared in order they should appear in a chooser.
  /**
   * A currency format of #,###.##.
   */
  US_DOLLAR("1,000.00", new CurrencyFormat(I18NHelper.ENGLISH)),
  /**
   * A currency format of #.###,##.
   */
  OTHER("1.000,00", new CurrencyFormat(I18NHelper.PORTUGUESE));

  //////////////////////////////////////////////////////////////////////////////
  // Start of public methods.
  //////////////////////////////////////////////////////////////////////////////

  /**
   * This method returns the enum constant's currency.
   *
   * @return The enum constant's currency.
   */
  public
  CurrencyFormat
  getCurrency()
  {
    return itsCurrency;
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
  CurrencyFormatKeys(String identifier, CurrencyFormat currency)
  {
    itsCurrency = currency;
    itsIdentifier = identifier;
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  private CurrencyFormat itsCurrency;
  private String itsIdentifier;
}
