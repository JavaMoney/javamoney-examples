// CurrencySymbolKeys

package org.javamoney.examples.ez.money.locale;

import javax.money.CurrencyUnit;

import org.javamoney.examples.ez.common.utility.I18NHelper;
import org.javamoney.moneta.MoneyCurrency;
import org.javamoney.moneta.function.CurrencySupplier;

/**
 * This enumerated class provides keys for the currency symbols and values.
 */
public
enum
CurrencySymbolKeys implements CurrencySupplier
{
  // Declared in order they should appear in a chooser.
  /**
   * No symbol.
   */
  NONE(I18NHelper.getSharedProperty("none"), "", MoneyCurrency.of("XXX")),
  /**
   * The Euro.
   */
  EURO("\u20ac ", MoneyCurrency.of("EUR")),
  /**
   * The US Dollar.
   */
  DOLLAR("$ ", MoneyCurrency.of("USD")),
  /**
   * The English Pound.
   */
  POUND("\u00a3 ", MoneyCurrency.of("GBP")),
  /**
   * The South African Rand.
   */
  RAND("R ", MoneyCurrency.of("ZAR")),
  /**
   * The Brazilian Real.
   */
  REAL("R$ ", MoneyCurrency.of("BRL")),
  /**
   * The Russian Ruble.
   */
  RUBLE(" \u0440.", MoneyCurrency.of("RUB"));

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
   * This method returns a currency unit for the enum constant.
   *
   * @return A currency.
   */
  public
  CurrencyUnit
  getCurrency()
  {
    return itsCurrency;
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
  CurrencySymbolKeys(String symbol, CurrencyUnit currency)
  {
    this(symbol, symbol, currency);
  }

  private
  CurrencySymbolKeys(String identifier, String symbol, CurrencyUnit currency)
  {
    itsIdentifier = identifier;
    itsSymbol = symbol;
    itsCurrency = currency;
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  private final String itsIdentifier;
  private final String itsSymbol;
  private final CurrencyUnit itsCurrency;
}
