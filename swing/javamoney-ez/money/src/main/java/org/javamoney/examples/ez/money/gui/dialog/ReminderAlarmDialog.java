// ReminderAlarmDialog

package org.javamoney.examples.ez.money.gui.dialog;

import static org.javamoney.examples.ez.money.ApplicationThread.getFrame;
import static org.javamoney.examples.ez.money.IconKeys.DIALOG_REMINDER_ALARM;
import static org.javamoney.examples.ez.money.model.DataManager.getReminders;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.javamoney.examples.ez.money.gui.GUIConstants;
import org.javamoney.examples.ez.money.gui.table.ReminderTable;
import org.javamoney.examples.ez.money.gui.view.HomeView;
import org.javamoney.examples.ez.money.gui.view.ViewKeys;
import org.javamoney.examples.ez.money.model.DataElement;
import org.javamoney.examples.ez.money.model.persisted.reminder.Reminder;

import org.javamoney.examples.ez.common.gui.DialogHeader;
import org.javamoney.examples.ez.common.gui.Panel;
import org.javamoney.examples.ez.common.gui.ScrollPane;
import org.javamoney.examples.ez.common.utility.I18NHelper;

/**
 * This class facilitates informing the user of reminders that are about to
 * become overdue.
 */
public
final
class
ReminderAlarmDialog
extends ApplicationDialog
{
  /**
   * Constructs a new dialog for reminders that are about to become overdue.
   */
  public
  ReminderAlarmDialog()
  {
    super(565, 325);

    setTable(new ReminderTable());

    buildPanel();
  }

  /**
   * This method shows a dialog for reminders that are about to become overdue.
   */
  public
  void
  showDialog()
  {
    addReminders();

    if(getTable().getList().size() != 0)
    {
      getTable().display();
      runDialog();

      // Update the reminders view on the home page incase a reminder was
      // modified.
      ((HomeView)getFrame().getViews().getView(ViewKeys.HOME)).updateRemindersView();
    }
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of private methods.
  //////////////////////////////////////////////////////////////////////////////

  private
  void
  addReminders()
  {
    for(DataElement element : getReminders().getCollection())
    {
      Reminder reminder = (Reminder)element;

      if(reminder.canAlarm() == true)
      {
        getTable().add(reminder);
      }
    }
  }

  private
  void
  buildPanel()
  {
    Panel panel = getContentPane();

    // Build panel.
    panel.setFill(GridBagConstraints.BOTH);
    panel.add(createDialogHeader(), 0, 0, 1, 1, 0, 0);
    panel.add(createReminderTablePanel(), 0, 1, 1, 1, 100, 100);
    panel.add(createOKButtonPanel(new ActionHandler()), 0, 2, 1, 1, 0, 0);
  }

  private
  static
  DialogHeader
  createDialogHeader()
  {
    String description = getProperty("header.description");
    String title = getProperty("header.title");

    return new DialogHeader(title, description, DIALOG_REMINDER_ALARM.getIcon());
  }

  private
  Panel
  createReminderTablePanel()
  {
    Panel panel = new Panel();
    ScrollPane scrollPane = new ScrollPane(getTable());

    // Build scroll pane.
    scrollPane.setBackground(GUIConstants.COLOR_BACKGROUND_FILL);

    // Build panel.
    panel.setFill(GridBagConstraints.BOTH);
    panel.add(scrollPane, 0, 0, 1, 1, 100, 100);

    panel.setInsets(new Insets(10, 10, 10, 10));

    return panel;
  }

  private
  static
  String
  getProperty(String key)
  {
    return I18NHelper.getProperty("ReminderAlarmDialog." + key);
  }

  private
  ReminderTable
  getTable()
  {
    return itsTable;
  }

  private
  void
  setTable(ReminderTable table)
  {
    itsTable = table;
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of inner classes.
  //////////////////////////////////////////////////////////////////////////////

  private
  class
  ActionHandler
  implements ActionListener
  {
    public
    void
    actionPerformed(ActionEvent event)
    {
      dispose();
    }
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  private ReminderTable itsTable;
}
