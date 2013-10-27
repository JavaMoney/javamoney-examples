// RemindersPanel

package org.javamoney.examples.ez.money.gui.dialog.preferences;

import static org.javamoney.examples.ez.common.utility.BorderHelper.createTitledBorder;
import static org.javamoney.examples.ez.common.utility.ButtonHelper.buildButton;
import static org.javamoney.examples.ez.common.utility.I18NHelper.getSharedProperty;
import static org.javamoney.examples.ez.money.ApplicationProperties.UI_DATE_FORMAT;
import static org.javamoney.examples.ez.money.model.DataTypeKeys.REMINDER;
import static org.javamoney.examples.ez.money.utility.IDHelper.MessageKeys.IN_USE;
import static org.javamoney.examples.ez.money.utility.IDHelper.MessageKeys.UNABLE_TO_REMOVE;
import static org.javamoney.examples.ez.money.utility.TransactionDateHelper.showDateDialog;

import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.AbstractButton;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import org.javamoney.examples.ez.money.model.persisted.reminder.Reminder;
import org.javamoney.examples.ez.money.utility.IDHelper;

import org.javamoney.examples.ez.common.gui.CheckBox;
import org.javamoney.examples.ez.common.gui.Link;
import org.javamoney.examples.ez.common.gui.Panel;
import org.javamoney.examples.ez.common.utility.I18NHelper;

/**
 * This class facilitates managing the reminders.
 */
public
final
class
RemindersPanel
extends DataElementPanel
{
  /**
   * Constructs a new preferences panel.
   */
  public
  RemindersPanel()
  {
    super(PreferencesKeys.REMINDERS);

    setSpinner(new JSpinner(new SpinnerNumberModel(0, 0, 21, 1)));

    createButtons();

    buildPanel();

    // Initialize the panel with the selected reminder.
    displayReminderInfo();

    // Prevent users from entering arbitrary data in the spinner.
    ((JSpinner.NumberEditor)getSpinner().getEditor()).getTextField().setEditable(false);

    // Add listeners.
    getChooser().addTreeSelectionListener(new SelectionHandler());
    getSpinner().addChangeListener(new ChangeHandler());
  }

  /**
   * This method updates this panel's view.
   */
  @Override
  public
  void
  updateView()
  {
    displayCollectables();
    getChooser().selectFirst();
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of protected methods.
  //////////////////////////////////////////////////////////////////////////////

  /**
   * This method prompts the user for a new unique identifier.
   */
  @Override
  protected
  void
  edit()
  {
    Reminder reminder = (Reminder)getChooser().getSelectedElement();
    String identifier = IDHelper.promptForEdit(REMINDER, reminder.getIdentifier());

    if(identifier != null)
    {
      if(getCollection().changeIdentifier(reminder, identifier) == true)
      {
        // Update the view, and select the edited element.
        displayCollectables();
        getChooser().setSelectedCollectable(reminder);
      }
      else
      {
        // It is safe to assume it already existed.
        IDHelper.showMessage(IN_USE, REMINDER);
      }
    }
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of private methods.
  //////////////////////////////////////////////////////////////////////////////

  private
  void
  add()
  {
    String identifier = IDHelper.promptForAdd(REMINDER);

    if(identifier != null)
    {
      Reminder reminder = new Reminder(identifier);

      if(getCollection().add(reminder) == true)
      {
        // Update view and select the new element.
        displayCollectables();
        getChooser().setSelectedCollectable(reminder);
      }
      else
      {
        // It is safe to assume it already existed.
        IDHelper.showMessage(IN_USE, REMINDER);
      }
    }
  }

  private
  void
  buildPanel()
  {
    ActionHandler handler = new ActionHandler();

    // Build panel.
    setFill(GridBagConstraints.BOTH);
    add(createChooserPanel(handler), 0, 0, 1, 1, 100, 100);
    add(createOptionsPanel(), 0, 1, 1, 1, 0, 0);
    add(createButtonPanel(handler), 0, 2, 1, 1, 0, 0);
  }

  private
  void
  createButtons()
  {
    itsButtons = new AbstractButton[2];

    ActionHandler handler = new ActionHandler();

    getButtons()[COMPLETED] = new CheckBox();
    getButtons()[DUE_BY] = new Link();

    // Build buttons.
    buildButton(getButtons()[COMPLETED], getProperty("completed"), handler);
    buildButton(getButtons()[DUE_BY], "", handler, "", getSharedProperty("date_tip"));
  }

  private
  Panel
  createOptionsPanel()
  {
    Panel panel = new Panel();

    // Build panel.
    panel.setAnchor(GridBagConstraints.EAST);
    panel.add(getSharedProperty("due_by") + ": ", 0, 0, 1, 1, 0, 50);
    panel.add(getProperty("days_to_alarm") + ": ", 2, 0, 1, 1, 0, 0);

    panel.setAnchor(GridBagConstraints.WEST);
    panel.add(getButtons()[DUE_BY], 1, 0, 1, 1, 0, 0);
    panel.add(getSpinner(), 3, 0, 1, 1, 100, 0);

    panel.addEmptyCellAt(1, 1, 12);

    panel.setFill(GridBagConstraints.HORIZONTAL);
    panel.add(getButtons()[COMPLETED], 0, 2, 3, 1, 0, 50);

    panel.setBorder(createTitledBorder(getProperty("title")));

    return panel;
  }

  private
  void
  displayCollectables()
  {
    getChooser().displayCollectables();
    enableLinks();
    showProperChooserPanel();
  }

  private
  void
  displayReminderInfo()
  {
    Reminder reminder = (Reminder)getChooser().getSelectedElement();
    String date = "";
    int daysToAlarm = Reminder.DEFAULT_DAYS_TO_ALARM;
    boolean done = false;

    if(reminder != null)
    {
      date = UI_DATE_FORMAT.format(reminder.getDueBy());
      daysToAlarm = reminder.getDaysToAlarm();
      done = reminder.isComplete();
    }

    // Enable or disable.
    getButtons()[DUE_BY].setEnabled(reminder != null);
    getButtons()[DUE_BY].setText(date);
    getButtons()[COMPLETED].setEnabled(reminder != null);
    getButtons()[COMPLETED].setSelected(done);
    getSpinner().setEnabled(reminder != null);
    getSpinner().setValue(new Integer(daysToAlarm));
  }

  private
  AbstractButton[]
  getButtons()
  {
    return itsButtons;
  }

  private
  static
  String
  getProperty(String key)
  {
    return I18NHelper.getProperty("RemindersPanel." + key);
  }

  private
  JSpinner
  getSpinner()
  {
    return itsSpinner;
  }

  private
  void
  remove()
  {
    if(IDHelper.confirmRemoval(REMINDER) == true)
    {
      Reminder reminder = (Reminder)getChooser().getSelectedElement();

      if(getCollection().remove(reminder) == true)
      {
        // Update the view.
        displayCollectables();

        if(getChooser().length() != 0)
        {
          getChooser().selectFirst();
        }
        else
        {
          displayReminderInfo();
        }
      }
      else
      {
        IDHelper.showMessage(UNABLE_TO_REMOVE, REMINDER);
      }
    }
  }

  private
  void
  setSpinner(JSpinner spinner)
  {
    itsSpinner = spinner;
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
      String command = event.getActionCommand();
      Object source = event.getSource();

      if(source == getButtons()[COMPLETED])
      {
        Reminder reminder = (Reminder)getChooser().getSelectedElement();

        reminder.setIsComplete(!reminder.isComplete());
      }
      else if(source == getButtons()[DUE_BY])
      {
        Reminder reminder = (Reminder)getChooser().getSelectedElement();
        Date date = showDateDialog(reminder.getDueBy());

        if(date != null)
        {
          reminder.setDueBy(date);
          getButtons()[DUE_BY].setText(UI_DATE_FORMAT.format(date));
        }
      }
      else if(command.equals(ACTION_ADD) == true)
      {
        add();
      }
      else if(command.equals(ACTION_EDIT) == true)
      {
        edit();
      }
      else if(command.equals(ACTION_REMOVE) == true)
      {
        remove();
      }
    }
  }

  private
  class
  ChangeHandler
  implements ChangeListener
  {
    public
    void
    stateChanged(ChangeEvent event)
    {
      Reminder reminder = (Reminder)getChooser().getSelectedElement();

      if(reminder != null)
      {
        int value = Integer.parseInt(getSpinner().getValue().toString());

        reminder.setDaysToAlarm(value);
      }
    }
  }

  private
  class
  SelectionHandler
  implements TreeSelectionListener
  {
    public
    void
    valueChanged(TreeSelectionEvent event)
    {
      displayReminderInfo();
    }
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  private AbstractButton[] itsButtons;
  private JSpinner itsSpinner;

  private static final int COMPLETED = 0;
  private static final int DUE_BY = 1;
}
