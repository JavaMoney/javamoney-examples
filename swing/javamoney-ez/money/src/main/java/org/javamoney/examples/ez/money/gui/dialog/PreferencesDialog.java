// PreferencesDialog

package org.javamoney.examples.ez.money.gui.dialog;

import static org.javamoney.examples.ez.common.utility.I18NHelper.getSharedProperty;
import static org.javamoney.examples.ez.money.ApplicationProperties.getLastUsedPreferencePanelIndex;
import static org.javamoney.examples.ez.money.ApplicationProperties.setLastUsedPreferencePanelIndex;
import static org.javamoney.examples.ez.money.ApplicationThread.getFrame;
import static org.javamoney.examples.ez.money.gui.dialog.preferences.PreferencesKeys.ACCOUNTS;
import static org.javamoney.examples.ez.money.gui.dialog.preferences.PreferencesKeys.EXPENSES;
import static org.javamoney.examples.ez.money.gui.dialog.preferences.PreferencesKeys.GENERAL;
import static org.javamoney.examples.ez.money.gui.dialog.preferences.PreferencesKeys.INCOME;
import static org.javamoney.examples.ez.money.gui.dialog.preferences.PreferencesKeys.NETWORK;
import static org.javamoney.examples.ez.money.gui.dialog.preferences.PreferencesKeys.PAYEES;
import static org.javamoney.examples.ez.money.gui.dialog.preferences.PreferencesKeys.REMINDERS;

import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.javamoney.examples.ez.money.gui.dialog.preferences.AccountsPanel;
import org.javamoney.examples.ez.money.gui.dialog.preferences.ExpensesPanel;
import org.javamoney.examples.ez.money.gui.dialog.preferences.GeneralOptionsPanel;
import org.javamoney.examples.ez.money.gui.dialog.preferences.IncomePanel;
import org.javamoney.examples.ez.money.gui.dialog.preferences.NetworkPanel;
import org.javamoney.examples.ez.money.gui.dialog.preferences.PayeesPanel;
import org.javamoney.examples.ez.money.gui.dialog.preferences.PreferencesKeys;
import org.javamoney.examples.ez.money.gui.dialog.preferences.PreferencesPanel;
import org.javamoney.examples.ez.money.gui.dialog.preferences.RemindersPanel;

import org.javamoney.examples.ez.common.gui.DialogHeader;
import org.javamoney.examples.ez.common.gui.Panel;
import org.javamoney.examples.ez.common.utility.I18NHelper;

/**
 * This class is the dialog that provides access to all the data collections and
 * other configurable options.
 */
public
final
class
PreferencesDialog
extends ApplicationDialog
{
  /**
   * Constructs a new dialog initially displaying the accounts panel.
   */
  public
  PreferencesDialog()
  {
    this(ACCOUNTS);
  }

  /**
   * Constructs a new dialog initially displaying the specified panel.
   *
   * @param key The panel initially displayed.
   */
  public
  PreferencesDialog(PreferencesKeys key)
  {
    super(650, 550);

    setDialogHeader(new DialogHeader(key.getTitle(), key.getDescription(), key.getIcon()));
    setPanelChooser(new JTabbedPane());
    setTitle(getProperty("title"));

    // Initialize the panel array.
    createPanels();

    // Build dialog.
    buildPanel(key);

    // Add listeners.
    getPanelChooser().addChangeListener(new ChangeHandler());
  }

  /**
   * This method starts the settings dialog.
   */
  public
  void
  showDialog()
  {
    runDialog();

    // Do any finalizing.
    for(PreferencesPanel panel : getPanels())
    {
      panel.doClose();
    }

    // Save the last selected index.
    setLastUsedPreferencePanelIndex(getPanelChooser().getSelectedIndex());

    getFrame().signalDataChange();
  }

  /**
   * This method displays the preferences dialog.
   */
  public
  static
  void
  showPreferencesDialog()
  {
    int index = getLastUsedPreferencePanelIndex();

    showPreferencesDialog(PreferencesKeys.getKey(index));
  }

  /**
   * This method displays the preferences dialog initially displayed with the
   * specified preferences panel.
   *
   * @param key The preferences panel to initially display.
   */
  public
  static
  void
  showPreferencesDialog(PreferencesKeys key)
  {
    PreferencesDialog dialog = new PreferencesDialog(key);

    dialog.showDialog();
    dialog.dispose();
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of private methods.
  //////////////////////////////////////////////////////////////////////////////

  private
  void
  buildPanel(PreferencesKeys initialPanel)
  {
    Panel panel = getContentPane();

    // Put the panels into the cards.
    for(PreferencesKeys key : PreferencesKeys.values())
    {
      getPanelChooser().addTab(key.toString(), getPanels()[key.ordinal()]);
    }

    // Make the initial panel the selected card.
    getPanelChooser().setSelectedIndex(initialPanel.ordinal());

    // Build panel.
    panel.setFill(GridBagConstraints.BOTH);
    panel.add(getDialogHeader(), 0, 0, 1, 1, 0, 0);
    panel.addEmptyCellAt(0, 1);
    panel.add(getPanelChooser(), 0, 2, 1, 1, 100, 100);

    panel.add(createCancelButtonPanel(getSharedProperty("close"), new ActionHandler()),
        0, 3, 1, 1, 0, 0);
  }

  private
  void
  changePanel()
  {
    int index = getPanelChooser().getSelectedIndex();
    PreferencesKeys key = PreferencesKeys.getKey(index);

    // Update panels.
    getDialogHeader().setIcon(key.getIcon());
    getDialogHeader().setText(key.getTitle(), key.getDescription());
    getPanels()[index].updateView();
  }

  private
  void
  createPanels()
  {
    itsPanels = new PreferencesPanel[PreferencesKeys.values().length];

    getPanels()[ACCOUNTS.ordinal()] = new AccountsPanel();
    getPanels()[EXPENSES.ordinal()] = new ExpensesPanel();
    getPanels()[GENERAL.ordinal()] = new GeneralOptionsPanel();
    getPanels()[INCOME.ordinal()] = new IncomePanel();
    getPanels()[NETWORK.ordinal()] = new NetworkPanel();
    getPanels()[PAYEES.ordinal()] = new PayeesPanel();
    getPanels()[REMINDERS.ordinal()] = new RemindersPanel();
  }

  private
  DialogHeader
  getDialogHeader()
  {
    return itsDialogHeader;
  }

  private
  JTabbedPane
  getPanelChooser()
  {
    return itsPanelChooser;
  }

  private
  PreferencesPanel[]
  getPanels()
  {
    return itsPanels;
  }

  private
  static
  String
  getProperty(String key)
  {
    return I18NHelper.getProperty("PreferencesDialog." + key);
  }

  private
  void
  setDialogHeader(DialogHeader panel)
  {
    itsDialogHeader = panel;
  }

  private
  void
  setPanelChooser(JTabbedPane pane)
  {
    itsPanelChooser = pane;
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

  private
  class
  ChangeHandler
  implements ChangeListener
  {
    public
    void
    stateChanged(ChangeEvent event)
    {
      changePanel();
    }
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  private DialogHeader itsDialogHeader;
  private JTabbedPane itsPanelChooser;
  private PreferencesPanel[] itsPanels;
}
