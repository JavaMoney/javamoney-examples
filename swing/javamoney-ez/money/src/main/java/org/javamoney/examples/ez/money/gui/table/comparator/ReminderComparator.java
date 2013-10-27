// ReminderComparator

package org.javamoney.examples.ez.money.gui.table.comparator;

import static org.javamoney.examples.ez.common.utility.CompareHelper.compareDates;
import static org.javamoney.examples.ez.common.utility.CompareHelper.compareObjects;
import static org.javamoney.examples.ez.money.gui.table.ReminderTable.DUE_BY_COLUMN;
import static org.javamoney.examples.ez.money.gui.table.ReminderTable.ID_COLUMN;

import org.javamoney.examples.ez.money.model.persisted.reminder.Reminder;

import org.javamoney.examples.ez.common.gui.table.DataTableComparator;

/**
 * This class facilitates comparing reminders in a table.
 */
public
final
class
ReminderComparator
extends DataTableComparator<Reminder>
{
  /**
   * Constructs a new comparator.
   */
  public
  ReminderComparator()
  {
    super(DUE_BY_COLUMN);
  }

  /**
   * This method compares two reminders.
   *
   * @param reminder1 A reminder to compare.
   * @param reminder2 A reminder to compare.
   *
   * @return The result of comparing two reminders.
   */
  @Override
  public
  int
  compare(Reminder reminder1, Reminder reminder2)
  {
    int result = 0;

    if(getColumn() == DUE_BY_COLUMN)
    {
      result = compareDates(reminder1.getDueBy(), reminder2.getDueBy(), invertSort());
    }
    else if(getColumn() == ID_COLUMN)
    {
      result = compareObjects(reminder1, reminder2, invertSort());
    }

    return result;
  }
}
