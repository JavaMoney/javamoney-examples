// DateHelper

package org.javamoney.examples.ez.common.utility;

import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.DAY_OF_YEAR;
import static java.util.Calendar.HOUR_OF_DAY;
import static java.util.Calendar.MILLISECOND;
import static java.util.Calendar.MINUTE;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.SECOND;
import static java.util.Calendar.YEAR;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * This class facilitates providing convenience methods for dates. All methods
 * in this class are static.
 */
public
final
class
DateHelper
{
  /**
   * This method sets the clock to 00:00:00:000.
   *
   * @param calendar The calendar to clear the clock for.
   */
  public
  static
  void
  clearClockFor(Calendar calendar)
  {
    // Set time to 00:00:00:000
    calendar.set(HOUR_OF_DAY, 0);
    calendar.set(MILLISECOND, 0);
    calendar.set(MINUTE, 0);
    calendar.set(SECOND, 0);
  }

  /**
   * This method creates and returns a new calendar.
   *
   * @return A new calendar.
   */
  public
  static
  GregorianCalendar
  createCalendar()
  {
    return createCalendar(null);
  }

  /**
   * This method creates and returns a new calendar.
   *
   * @param date The date to initialize the calendar to.
   *
   * @return A new calendar.
   */
  public
  static
  GregorianCalendar
  createCalendar(Date date)
  {
    GregorianCalendar calendar = new GregorianCalendar();

    if(date == null)
    {
      date = new Date();
    }

    calendar.setTime(date);

    clearClockFor(calendar);

    return calendar;
  }

  /**
   * This method returns a date set to the end of the current month.
   *
   * @return A date.
   */
  public
  static
  Date
  getEndOfCurrentMonth()
  {
    GregorianCalendar calendar = createCalendar();

    // Set start.
    calendar.set(DAY_OF_MONTH, 1);

    // Set end.
    calendar.set(DAY_OF_MONTH, calendar.getActualMaximum(DAY_OF_MONTH));

    return calendar.getTime();
  }

  /**
   * This method returns a date set to the end of the current year.
   *
   * @return A date.
   */
  public
  static
  Date
  getEndOfCurrentYear()
  {
    GregorianCalendar calendar = createCalendar();

    // Set start.
    calendar.set(DAY_OF_YEAR, 1);

    // Set end.
    calendar.set(DAY_OF_YEAR, calendar.getActualMaximum(DAY_OF_YEAR));

    return calendar.getTime();
  }

  /**
   * This method returns a date set to the end of last month.
   *
   * @return A date.
   */
  public
  static
  Date
  getEndOfLastMonth()
  {
    GregorianCalendar calendar = createCalendar();

    // Set start.
    calendar.set(DAY_OF_MONTH, 1);
    calendar.set(MONTH, calendar.get(MONTH) - 1);

    // Set end.
    calendar.set(DAY_OF_MONTH, calendar.getActualMaximum(DAY_OF_MONTH));

    return calendar.getTime();
  }

  /**
   * This method returns a date set to the end of last year.
   *
   * @return A date.
   */
  public
  static
  Date
  getEndOfLastYear()
  {
    GregorianCalendar calendar = createCalendar();

    // Set start.
    calendar.set(DAY_OF_YEAR, 1);
    calendar.set(YEAR, calendar.get(YEAR) - 1);

    // Set end.
    calendar.set(DAY_OF_YEAR, calendar.getActualMaximum(DAY_OF_YEAR));

    return calendar.getTime();
  }

  /**
   * This method returns a date set to the end of the specified month and year.
   *
   * @param month The date's month.
   * @param year The date's year.
   *
   * @return A date.
   */
  public
  static
  Date
  getEndOfMonth(int month, int year)
  {
    GregorianCalendar calendar = createCalendar();

    calendar.set(MONTH, month);
    calendar.set(YEAR, year);
    calendar.set(DAY_OF_MONTH, calendar.getActualMaximum(DAY_OF_MONTH));

    return calendar.getTime();
  }

  /**
   * This method returns a date set to the end of the specified year.
   *
   * @param year The year to create the date for.
   *
   * @return A date.
   */
  public
  static
  Date
  getEndOfYear(int year)
  {
    return getEndOfMonth(11, year);
  }

  /**
   * This method returns the number of months between the specified dates.
   *
   * @param start The start date.
   * @param end The end date.
   *
   * @return The number of months between the specified dates.
   */
  public
  static
  int
  getMonthSpan(Date start, Date end)
  {
    GregorianCalendar first = createCalendar(start);
    GregorianCalendar last = createCalendar(end);
    int monthSpan = 0;

    first.set(DAY_OF_MONTH, 1);

    monthSpan += (last.get(MONTH) - first.get(MONTH));
    monthSpan += 12 * (last.get(YEAR) - first.get(YEAR));

    if(monthSpan < 0)
    {
      monthSpan = 0;
    }

    return monthSpan;
  }

  /**
   * This method returns a date set to the start of the current month.
   *
   * @return A date.
   */
  public
  static
  Date
  getStartOfCurrentMonth()
  {
    GregorianCalendar calendar = createCalendar();

    // Set start.
    calendar.set(DAY_OF_MONTH, 1);

    return calendar.getTime();
  }

  /**
   * This method returns a date set to the start of the current year.
   *
   * @return A date.
   */
  public
  static
  Date
  getStartOfCurrentYear()
  {
    GregorianCalendar calendar = createCalendar();

    // Set start.
    calendar.set(DAY_OF_YEAR, 1);

    return calendar.getTime();
  }

  /**
   * This method returns the starting date for a fortnight where the end of the
   * fortnight equals today.
   *
   * @return A date.
   */
  public
  static
  Date
  getStartOfFortnight()
  {
    GregorianCalendar calendar = createCalendar();

    // Set start.
    calendar.set(DAY_OF_MONTH, calendar.get(DAY_OF_MONTH) - 14);

    return calendar.getTime();
  }

  /**
   * This method returns a date set to the start of last month.
   *
   * @return A date.
   */
  public
  static
  Date
  getStartOfLastMonth()
  {
    GregorianCalendar calendar = createCalendar();

    // Set start.
    calendar.set(DAY_OF_MONTH, 1);
    calendar.set(MONTH, calendar.get(MONTH) - 1);

    return calendar.getTime();
  }

  /**
   * This method returns a date set to the start of the specified month and
   * year.
   *
   * @param month The date's month.
   * @param year The date's year.
   *
   * @return A date.
   */
  public
  static
  Date
  getStartOfMonth(int month, int year)
  {
    GregorianCalendar calendar = createCalendar();

    calendar.set(DAY_OF_MONTH, 1);
    calendar.set(MONTH, month);
    calendar.set(YEAR, year);

    return calendar.getTime();
  }

  /**
   * This method returns a date set to the start of last year.
   *
   * @return A date.
   */
  public
  static
  Date
  getStartOfLastYear()
  {
    GregorianCalendar calendar = createCalendar();

    // Set start.
    calendar.set(DAY_OF_YEAR, 1);
    calendar.set(YEAR, calendar.get(YEAR) - 1);

    return calendar.getTime();
  }

  /**
   * This method returns a date set to the start of the specified year.
   *
   * @param year The year to create the date for.
   *
   * @return A date.
   */
  public
  static
  Date
  getStartOfYear(int year)
  {
    return getStartOfMonth(0, year);
  }
}
