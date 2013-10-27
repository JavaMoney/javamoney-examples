// CurrencySymbolKeys

package org.javamoney.examples.ez.money.locale;

import org.javamoney.examples.ez.common.utility.I18NHelper;

/**
 * This enumerated class provides keys for the currency symbols.
 */
public
enum
CurrencySymbolKeys
{
  // Declared in order they should appear in a chooser.
  /**
   * No symbol.
   */
  NONE(I18NHelper.getSharedProperty("none"), ""),
  /**
   * The Euro.
   */
  EURO("\u20ac "),
  /**
   * The US Dollar.
   */
  DOLLAR("$ "),
  /**
   * The English Pound.
   */
  POUND("\u00a3 "),
  /**
   * The South African Rand.
   */
  RAND("R "),
  /**
   * The Brazilian Real.
   */
  REAL("R$ "),
  /**
   * The Russian Ruble.
   */
  RUBLE(" \u0440.");

  //////////////////////////////////////////////////////////////////////////////
  // Start of public methods.
  //////////////////////////////////////////////////////////////////////////////

  /**
   * This method returns a string for the enum constant.
   *
   * @return A string.
   */
  public
  String
  getSymbol()
  {
    return itsSymbol;
  }

  /**
   * This method returns the currency symbol.
   *
   * @return The currency symbol.
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
  CurrencySymbolKeys(String symbol)
  {
    this(symbol, symbol);
  }

  private
  CurrencySymbolKeys(String identifier, String symbol)
  {
    itsIdentifier = identifier;
    itsSymbol = symbol;
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  private String itsIdentifier;
  private String itsSymbol;
}
