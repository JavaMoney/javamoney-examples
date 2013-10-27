// Reminder

package org.javamoney.examples.ez.money.model.persisted.reminder;

import static org.javamoney.examples.ez.common.utility.DateHelper.createCalendar;
import static org.javamoney.examples.ez.common.utility.DateHelper.getEndOfCurrentMonth;
import static org.javamoney.examples.ez.common.utility.DateHelper.getEndOfLastMonth;
import static org.javamoney.examples.ez.common.utility.DateHelper.getMonthSpan;
import static java.util.Calendar.DAY_OF_MONTH;

import java.util.Date;
import java.util.GregorianCalendar;

import org.javamoney.examples.ez.money.model.DataElement;

/**
 * This class facilitates reminding a user of a monthly task.
 */
public
final
class
Reminder
extends DataElement
{
  /**
   * Constructs a new reminder.
   *
   * @param toDo The to do action.
   */
  public
  Reminder(String toDo)
  {
    this(toDo, DEFAULT_DAYS_TO_ALARM);
  }

  /**
   * Constructs a new reminder.
   *
   * @param toDo The to do action.
   * @param daysToAlarm The numbers of days in advance to the due date to start
   * alarming the user.
   */
  public
  Reminder(String toDo, int daysToAlarm)
  {
    super(toDo);

    setDaysToAlarm(daysToAlarm);

    // Set defaults.
    setDueBy(getEndOfCurrentMonth());
  }

  /**
   * This method returns true if the user should be made aware of it, otherwise
   * false.
   *
   * @return true or false.
   */
  public
  boolean
  canAlarm()
  {
    boolean result = false;

    if(getDaysToAlarm() != 0 && isComplete() == false)
    {
      // The due date cannot be after the current month. If the due date is in a
      // month before the current one, or the difference in days meets what the
      // user specified, then raise an alarm.
      if(getEndOfLastMonth().compareTo(getDueBy()) >= 0)
      {
        result = true;
      }
      else if(getMonthSpan(NOW, getDueBy()) == 0)
      {
        GregorianCalendar dueDate = createCalendar(getDueBy());
        GregorianCalendar today = createCalendar();
        int days = dueDate.get(DAY_OF_MONTH) - today.get(DAY_OF_MONTH);

        result = days <= getDaysToAlarm();
      }
    }

    return result;
  }

  /**
   * This method returns the numbers of days in advance to the due date to start
   * alarming the user.
   *
   * @return The numbers of days.
   */
  public
  int
  getDaysToAlarm()
  {
    return itsDaysToAlarm;
  }

  /**
   * This method returns the due by date.
   *
   * @return The due by date.
   */
  public
  Date
  getDueBy()
  {
    return itsDueBy;
  }

  /**
   * This method returns true if the reminder's to do action is complete,
   * otherwise false.
   *
   * @return true or false.
   */
  public
  boolean
  isComplete()
  {
    return itsIsComplete;
  }

  /**
   * This method returns true it the reminder is overdue, otherwise false.
   *
   * @return true or false.
   */
  public
  boolean
  isOverdue()
  {
    return NOW.compareTo(getDueBy()) >= 0 && isComplete() == false;
  }

  /**
   * This method sets the numbers of days in advance to the due date to start
   * alarming the user.
   *
   * @param value The numbers of days.
   */
  public
  void
  setDaysToAlarm(int value)
  {
    itsDaysToAlarm = value;
  }

  /**
   * This method sets the due by date.
   *
   * @param dueBy The due by date.
   */
  public
  void
  setDueBy(Date dueBy)
  {
    itsDueBy = dueBy;
  }

  /**
   * This method sets whether or not the reminder's to do action is complete.
   *
   * @param isComplete true or false.
   */
  public
  void
  setIsComplete(boolean isComplete)
  {
    itsIsComplete = isComplete;
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  private int itsDaysToAlarm;
  private Date itsDueBy;
  private boolean itsIsComplete;

  private static final Date NOW = new Date();

  /**
   * A constant for the default numbers of days prior to the due date to start
   * alarming the user.
   */
  public static final int DEFAULT_DAYS_TO_ALARM = 5;
}
