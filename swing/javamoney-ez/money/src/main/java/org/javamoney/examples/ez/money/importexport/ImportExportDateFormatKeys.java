// ImportExportDateFormatKeys

package org.javamoney.examples.ez.money.importexport;

import static org.javamoney.examples.ez.common.utility.DateHelper.createCalendar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * This enumerated class provides keys for date formats when importing and
 * exporting.
 */
public
enum
ImportExportDateFormatKeys
{
  // Declared in order they should appear in a chooser.
  /**
   * A simple date format of MM/dd/yy.
   */
  MONTH_FIRST("M/D/Y", new SimpleDateFormat("MM/dd/yy")),
  /**
   * A simple date format of dd/MM/yy.
   */
  DAY_FIRST("D/M/Y", new SimpleDateFormat("dd/MM/yy"));

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
  ImportExportDateFormatKeys(String identifier, SimpleDateFormat dateFormat)
  {
    itsDateFormat = dateFormat;
    itsIdentifier = identifier;
  }

  private
  SimpleDateFormat
  getDateFormat()
  {
    return itsDateFormat;
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  private SimpleDateFormat itsDateFormat;
  private String itsIdentifier;
}
