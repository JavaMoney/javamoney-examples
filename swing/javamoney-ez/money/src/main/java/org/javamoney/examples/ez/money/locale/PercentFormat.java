// PercentFormat

package org.javamoney.examples.ez.money.locale;

import java.text.DecimalFormat;

/**
 * This class facilitates formatting and parsing percents for a specified
 * locale.
 */
public
final
class
PercentFormat
{
  /**
   * Constructs a new formatter for the system's locale.
   */
  public
  PercentFormat()
  {
    setDecimalFormat(new DecimalFormat("###.#%"));

    // Set the precision.
    getDecimalFormat().setMaximumFractionDigits(2);
  }

  /**
   * This method returns a string representation of the specified percent in the
   * format of ###.#% or (###.#%), depending on whether or not the amount is
   * negative.
   *
   * @param percent The percent to format.
   *
   * @return A string representation of the specified percent.
   */
  public
  String
  format(double percent)
  {
    String str = getDecimalFormat().format(percent);

    // Rounding inadequacies can sometimes cause this.
    if(str.equals("-0%") == true)
    {
      str = "0%";
    }

    if(str.charAt(0) == '-')
    {
      str = "(" + str.substring(1) + ")";
    }

    return str;
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

  /**
   * A constant for the percent symbol.
   */
  public static final String SYMBOL = "%";
}
