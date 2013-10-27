// ReminderModel

package org.javamoney.examples.ez.money.gui.table.model;

import static org.javamoney.examples.ez.money.ApplicationProperties.UI_DATE_FORMAT;
import static org.javamoney.examples.ez.money.gui.table.ReminderTable.DUE_BY_COLUMN;
import static org.javamoney.examples.ez.money.gui.table.ReminderTable.ID_COLUMN;
import static org.javamoney.examples.ez.money.gui.table.ReminderTable.STATUS_COLUMN;

import org.javamoney.examples.ez.money.model.persisted.reminder.Reminder;

import org.javamoney.examples.ez.common.gui.Table;

/**
 * This class facilitates adding reminders into a table.
 */
public
final
class
ReminderModel
extends Table.NonmutableTableModel
{
  /**
   * This method adds the specified reminder into the model's table.
   *
   * @param reminder The reminder to add.
   */
  public
  void
  addRow(Reminder reminder)
  {
    String[] rowData = new String[3];

    rowData[DUE_BY_COLUMN] = UI_DATE_FORMAT.format(reminder.getDueBy());
    rowData[ID_COLUMN] = reminder.getIdentifier();
    rowData[STATUS_COLUMN] = "";

    addRow(rowData);
  }
}
