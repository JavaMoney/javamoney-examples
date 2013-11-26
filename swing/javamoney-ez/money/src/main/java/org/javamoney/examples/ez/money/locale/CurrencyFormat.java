// CurrencyFormat

package org.javamoney.examples.ez.money.locale;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.Locale;

/**
 * This class facilitates formatting and parsing amounts for a specified locale.
 */
public
final
class
CurrencyFormat
{
  /**
   * Constructs a new currency for the specified locale.
   *
   * @param locale The locale for determining how to format and parse.
   */
  public
  CurrencyFormat(Locale locale)
  {
    setDecimalFormat(new DecimalFormat("#,###.##", new DecimalFormatSymbols(locale)));

    // Set the precision.
    getDecimalFormat().setMinimumFractionDigits(2);
    getDecimalFormat().setMaximumFractionDigits(2);
  }

  /**
   * This method returns a string representation of the specified amount in the
   * format of #,###.## or (#,###.##), depending on whether or not the amount is
   * negative.
   *
   * @param amount The amount to format.
   *
   * @return A string representation of the specified amount.
   */
  public
  String
  format(double amount)
  {
    return format(amount, true);
  }

  /**
   * This method returns a string representation of the specified amount in the
   * format of #,###.## or (#,###.##), depending on whether or not to use
   * parentheses if the amount is negative.
   * <p>
   * <b>Note:</b> Parentheses are more aesthetic in the UI than the negative
   * symbol and should always be used.
   *
   * @param amount The amount to format.
   * @param useParentheses true or false.
   *
   * @return A string representation of the specified amount.
   */
  public
  String
  format(double amount, boolean useParentheses)
  {
    String str = getDecimalFormat().format(amount);

    // Rounding inadequacies can sometimes cause this.
    if(str.equals("-0.00") == true)
    {
      str = "0.00";
    }
    else if(str.equals("-0,00") == true)
    {
      str = "0,00";
    }

    if(str.charAt(0) == '-' && useParentheses == true)
    {
      str = "(" + str.substring(1) + ")";
    }

    return str;
  }

  /**
   * This method returns the currency's format symbols.
   *
   * @return The currency's format symbols.
   */
  public
  DecimalFormatSymbols
  getDecimalFormatSymbols()
  {
    return getDecimalFormat().getDecimalFormatSymbols();
  }

  /**
   * This method parses the specified string amount. This method expects the
   * format to be in #,###.## or (#,###.##).
   * <p>
   * <b>Note:</b> String amounts within parentheses will be interpreted as
   * negative.
   *
   * @param amount The string amount to parse.
   *
   * @return The real number value of the string amount.
   *
   * @throws ParseException If the string amount is not in the format of
   * #,###.## or (#,###.##).
   */
  public
  double
  parse(String amount)
  throws ParseException
  {
    // Remove parentheses.
    if(amount.charAt(0) == '(')
    {
      amount = "-" + amount.substring(1, amount.length() - 1);
    }

    return getDecimalFormat().parse(amount).doubleValue();
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of private methods.
  //////////////////////////////////////////////////////////////////////////////

  private
  DecimalFormat
  getDecimalFormat()
  {
    return itsDecimalFormat;
  }

  private
  void
  setDecimalFormat(DecimalFormat format)
  {
    itsDecimalFormat = format;
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  private DecimalFormat itsDecimalFormat;
}
