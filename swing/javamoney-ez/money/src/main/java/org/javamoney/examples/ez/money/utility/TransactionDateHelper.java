// TransactionDateHelper

package org.javamoney.examples.ez.money.utility;

import static org.javamoney.examples.ez.common.utility.DateHelper.clearClockFor;
import static org.javamoney.examples.ez.common.utility.DateHelper.createCalendar;
import static java.util.Calendar.DAY_OF_MONTH;
import static org.javamoney.examples.ez.money.ApplicationProperties.getDateWeekday;

import java.util.Date;
import java.util.GregorianCalendar;

import org.javamoney.examples.ez.money.gui.dialog.CalendarDialog;
import org.javamoney.examples.ez.money.model.persisted.transaction.Transaction;

/**
 * This class provides convenience methods for dates. All methods in this class
 * are static.
 */
public
final
class
TransactionDateHelper
{
  /**
   * This method returns true if the specified transaction occurred around the
   * specified date, otherwise false.
   *
   * @param trans The transaction to check.
   * @param days The amount of days, (+/-) inclusive, the transaction should be
   * within.
   *
   * @return true or false.
   */
  public
  static
  boolean
  isInRange(Transaction trans, Date date, int days)
  {
    GregorianCalendar end = createCalendar(date);
    GregorianCalendar start = createCalendar(date);

    end.set(DAY_OF_MONTH, end.get(DAY_OF_MONTH) + days);
    start.set(DAY_OF_MONTH, start.get(DAY_OF_MONTH) - days);

    return isInRange(trans, start.getTime(), end.getTime());
  }

  /**
   * This method returns true if the transaction occurred within the specified
   * start and end dates inclusive, otherwise false.
   *
   * @param trans The transaction to check.
   * @param start The start date in the range.
   * @param end The end date in the range.
   *
   * @return true or false.
   */
  public
  static
  boolean
  isInRange(Transaction trans, Date start, Date end)
  {
    return isOnOrAfter(trans, start) == true && isOnOrBefore(trans, end) == true;
  }

  /**
   * This method returns true if the transaction occurred on or after the
   * specified date, otherwise false.
   *
   * @param trans The transaction to check.
   * @param when The date of the occurrence.
   *
   * @return true or false.
   */
  public
  static
  boolean
  isOnOrAfter(Transaction trans, Date when)
  {
    boolean result = false;

    if(when == null || trans.getDate().compareTo(when) >= 0)
    {
      result = true;
    }

    return result;
  }

  /**
   * This method returns true if the transaction occurred on or before the
   * specified date, otherwise false.
   *
   * @param trans The transaction to check.
   * @param when The date of the occurrence.
   *
   * @return true or false.
   */
  public
  static
  boolean
  isOnOrBefore(Transaction trans, Date when)
  {
    boolean result = false;

    if(when == null || trans.getDate().compareTo(when) <= 0)
    {
      result = true;
    }

    return result;
  }

  /**
   * This method presents the user with a dialog for choosing a date that is
   * initialized with the specified date. If the specified date is null, then
   * the current date is used instead. This method returns the selected date, or
   * the initial date if the dialog was cancelled.
   *
   * @param date The date initially selected in the dialog.
   *
   * @return A date, or the initial date if the dialog was cancelled.
   */
  public
  static
  Date
  showDateDialog(Date date)
  {
    GregorianCalendar calendar = createCalendar(date);

    calendar = new CalendarDialog(calendar, getDateWeekday()).showDialog();

    if(calendar != null)
    {
      clearClockFor(calendar);
      date = calendar.getTime();
    }

    return date;
  }
}
