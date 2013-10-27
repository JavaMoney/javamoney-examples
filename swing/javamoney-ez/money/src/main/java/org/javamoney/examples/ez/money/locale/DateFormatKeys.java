// DateFormatKeys

package org.javamoney.examples.ez.money.locale;

import static org.javamoney.examples.ez.common.utility.DateHelper.createCalendar;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import org.javamoney.examples.ez.common.utility.I18NHelper;

/**
 * This enumerated class provides keys for the date formats.
 */
public
enum
DateFormatKeys
{
  // Declared in order they should appear in a chooser.
  /**
   * A date format of M/d/yy.
   */
  MONTH_FIRST(getProperty("month_first"), new SimpleDateFormat("M/d/yy")),
  /**
   * A date format of M.d.yy.
   */
  MONTH_FIRST_DOT(getProperty("month_first_dot"), new SimpleDateFormat("M.d.yy")),
  /**
   * A date format of d/M/yy.
   */
  DAY_FIRST(getProperty("day_first"), new SimpleDateFormat("d/M/yy")),
  /**
   * A date format of d.M.yy.
   */
  DAY_FIRST_DOT(getProperty("day_first_dot"), new SimpleDateFormat("d.M.yy"));

  //////////////////////////////////////////////////////////////////////////////
  // Start of public methods.
  //////////////////////////////////////////////////////////////////////////////

  /**
   * This method returns a string representation of the specified date.
   *
   * @param date The date to format.
   *
   * @return A string representation of the specified date.
   */
  public
  String
  format(Date date)
  {
    return getDateFormat().format(date);
  }

  /**
   * This method returns a new date object parsed from the specified formatted
   * date.
   *
   * @param date The formatted date.
   *
   * @return A new date object parsed from the specified formatted date.
   *
   * @throws ParseException If the date string is not in the correct format.
   */
  public
  Date
  parse(String date)
  throws ParseException
  {
    GregorianCalendar calendar = createCalendar(getDateFormat().parse(date));

    return calendar.getTime();
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
  DateFormatKeys(String identifier, DateFormat dateFormat)
  {
    itsDateFormat = dateFormat;
    itsIdentifier = identifier;
  }

  private
  DateFormat
  getDateFormat()
  {
    return itsDateFormat;
  }

  private
  static
  String
  getProperty(String key)
  {
    return I18NHelper.getProperty("DateFormatKeys." + key);
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  private DateFormat itsDateFormat;
  private String itsIdentifier;
}
