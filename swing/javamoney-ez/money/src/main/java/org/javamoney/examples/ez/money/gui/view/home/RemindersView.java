// RemindersView

package org.javamoney.examples.ez.money.gui.view.home;

import static org.javamoney.examples.ez.common.utility.BorderHelper.createTitledBorder;
import static org.javamoney.examples.ez.common.utility.I18NHelper.getSharedProperty;
import static org.javamoney.examples.ez.money.gui.GUIConstants.COLOR_BACKGROUND_FILL;
import static org.javamoney.examples.ez.money.model.DataManager.getReminders;

import java.awt.Dimension;
import java.awt.GridBagConstraints;

import org.javamoney.examples.ez.money.gui.table.ReminderTable;
import org.javamoney.examples.ez.money.model.DataElement;
import org.javamoney.examples.ez.money.model.persisted.reminder.Reminder;

import org.javamoney.examples.ez.common.gui.Panel;
import org.javamoney.examples.ez.common.gui.ScrollPane;

/**
 * This class provides a view for displaying a reminder table.
 */
public
final
class
RemindersView
extends Panel
{
  /**
   * Constructs a new reminders view.
   */
  public
  RemindersView()
  {
    setReminderTable(new ReminderTable());

    buildPanel();

    // For some reason, the reminders table likes to consume the entire frame.
    // This prevents that.
    setPreferredSize(new Dimension(0, 0));
  }

  /**
   * This method updates the view.
   */
  public
  void
  updateView()
  {
    getReminderTable().clear();

    // Display reminders.
    for(DataElement element : getReminders().getCollection())
    {
      getReminderTable().add((Reminder)element);
    }

    getReminderTable().display();
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of private methods.
  //////////////////////////////////////////////////////////////////////////////

  private
  void
  buildPanel()
  {
    ScrollPane scrollPane = new ScrollPane(getReminderTable());

    // Build scroll pane.
    scrollPane.setBackground(COLOR_BACKGROUND_FILL);

    // Build panel.
    setFill(GridBagConstraints.BOTH);
    add(scrollPane, 0, 0, 1, 1, 100, 100);

    setBorder(createTitledBorder(getSharedProperty("reminders"), false));
  }

  private
  ReminderTable
  getReminderTable()
  {
    return itsReminderTable;
  }

  private
  void
  setReminderTable(ReminderTable table)
  {
    itsReminderTable = table;
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  private ReminderTable itsReminderTable;
}
