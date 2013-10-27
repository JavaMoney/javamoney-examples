// AccountStatementDialog

package org.javamoney.examples.ez.money.gui.dialog;

import static org.javamoney.examples.ez.common.utility.BorderHelper.createTitledBorder;
import static org.javamoney.examples.ez.common.utility.ButtonHelper.buildButton;
import static org.javamoney.examples.ez.common.utility.I18NHelper.getSharedProperty;
import static org.javamoney.examples.ez.money.ApplicationProperties.includeCategoriesInAccountStatement;
import static org.javamoney.examples.ez.money.ApplicationProperties.includeCheckInAccountStatement;
import static org.javamoney.examples.ez.money.ApplicationProperties.includeNotesInAccountStatement;
import static org.javamoney.examples.ez.money.ApplicationProperties.includeReconciledStatusInAccountStatement;
import static org.javamoney.examples.ez.money.ApplicationProperties.setIncludeCategoriesInAccountStatement;
import static org.javamoney.examples.ez.money.ApplicationProperties.setIncludeCheckInAccountStatement;
import static org.javamoney.examples.ez.money.ApplicationProperties.setIncludeNotesInAccountStatement;
import static org.javamoney.examples.ez.money.ApplicationProperties.setIncludeReconciledStatusInAccountStatement;
import static org.javamoney.examples.ez.money.model.DataManager.getAccounts;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.javamoney.examples.ez.money.gui.chooser.DatePeriodChooser;
import org.javamoney.examples.ez.money.gui.chooser.ElementComboBoxChooser;
import org.javamoney.examples.ez.money.model.persisted.account.Account;
import org.javamoney.examples.ez.money.report.AccountStatement;
import org.javamoney.examples.ez.money.report.AccountStatementWriter;

import org.javamoney.examples.ez.common.gui.CheckBox;
import org.javamoney.examples.ez.common.gui.Panel;
import org.javamoney.examples.ez.common.utility.I18NHelper;

/**
 * This class provides a dialog for customizing an account statement.
 */
public
final
class
AccountStatementDialog
extends WebReportDialog
{
  /**
   * Constructs a new dialog for customizing an account statement.
   */
  public
  AccountStatementDialog()
  {
    this(null);
  }

  /**
   * Constructs a new dialog for customizing an account statement.
   *
   * @param account The account initially selected in the account chooser.
   */
  public
  AccountStatementDialog(Account account)
  {
    super(600, 415);

    setAccountChooser(new ElementComboBoxChooser(getAccounts()));
    setDatePeriodChooser(new DatePeriodChooser());

    createCheckBoxes();

    buildPanel();

    if(account != null)
    {
      getAccountChooser().setSelectedItem(account.getIdentifier());
    }
  }

  /**
   * This method shows a dialog for customizing an account statement.
   */
  public
  void
  showDialog()
  {
    if(getAccounts().getCollection().size() == 0)
    {
      getOKButton().setEnabled(false);
    }

    runDialog();
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of private methods.
  //////////////////////////////////////////////////////////////////////////////

  private
  void
  buildPanel()
  {
    Panel panel = getContentPane();

    // Build panel.
    panel.setFill(GridBagConstraints.BOTH);
    panel.add(createDialogHeader(), 0, 0, 1, 1, 0, 0);
    panel.add(createCustomizeStatementPanel(), 0, 1, 1, 1, 100, 100);

    panel.add(createOKCancelButtonPanel(getSharedProperty("create"), getSharedProperty("close"),
        new ActionHandler()), 0, 2, 1, 1, 0, 0);
  }

  private
  Panel
  createAccountChooserPanel()
  {
    Panel panel = new Panel();

    // Build panel.
    panel.setAnchor(GridBagConstraints.EAST);
    panel.addEmptyCellAt(0, 0);
    panel.add(getSharedProperty("account") + ": ", 0, 1, 1, 1, 0, 100);

    panel.setFill(GridBagConstraints.HORIZONTAL);
    panel.add(getAccountChooser(), 1, 1, 1, 1, 0, 0);

    // Aesthetic spacers.
    panel.addSpacer(2, 1, 1, 1, 100, 0);
    panel.addEmptyCellAt(1, 2, 25);
    panel.addEmptyCellAt(0, 3);

    return panel;
  }

  private
  void
  createCheckBoxes()
  {
    ActionHandler handler = new ActionHandler();

    itsCheckBoxes = new CheckBox[4];

    for(int len = 0; len < getCheckBoxes().length; ++len)
    {
      getCheckBoxes()[len] = new CheckBox();
    }

    // Build check boxes.
    buildButton(getCheckBoxes()[INCLUDE_CATEGORIES],
        getSharedProperty("include_categories"), handler);

    buildButton(getCheckBoxes()[INCLUDE_CHECK], getProperty("check"), handler);
    buildButton(getCheckBoxes()[INCLUDE_NOTES], getProperty("notes"), handler);
    buildButton(getCheckBoxes()[INCLUDE_RECONCILED], getProperty("reconciled"), handler);

    getCheckBoxes()[INCLUDE_CATEGORIES].setSelected(includeCategoriesInAccountStatement());
    getCheckBoxes()[INCLUDE_CHECK].setSelected(includeCheckInAccountStatement());
    getCheckBoxes()[INCLUDE_NOTES].setSelected(includeNotesInAccountStatement());
    getCheckBoxes()[INCLUDE_RECONCILED].setSelected(includeReconciledStatusInAccountStatement());
  }

  private
  Panel
  createCustomizeStatementPanel()
  {
    Panel panel = new Panel();

    // Build panel.
    panel.setFill(GridBagConstraints.BOTH);
    panel.add(createAccountChooserPanel(), 0, 0, 1, 1, 0, 0);

    panel.setAnchor(GridBagConstraints.WEST);
    panel.setFill(GridBagConstraints.NONE);
    panel.add(getDatePeriodChooser(), 0, 1, 1, 1, 100, 100);
    panel.addEmptyCellAt(0, 2);

    panel.setFill(GridBagConstraints.BOTH);
    panel.add(createMoreOptionsPanel(), 0, 3, 1, 1, 0, 0);

    panel.setInsets(new Insets(10, 15, 5, 15));

    getDatePeriodChooser().setBorder(createTitledBorder(getProperty("statement_period"), false));

    return panel;
  }

  private
  Panel
  createMoreOptionsPanel()
  {
    Panel panel = new Panel();

    // Build panel.
    panel.setAnchor(GridBagConstraints.WEST);
    panel.add(getCheckBoxes()[INCLUDE_CHECK], 0, 0, 1, 1, 25, 50);
    panel.add(getCheckBoxes()[INCLUDE_CATEGORIES], 0, 1, 1, 1, 0, 50);
    panel.add(getCheckBoxes()[INCLUDE_NOTES], 1, 0, 1, 1, 75, 0);
    panel.add(getCheckBoxes()[INCLUDE_RECONCILED], 1, 1, 1, 1, 0, 0);

    panel.setBorder(createTitledBorder(getSharedProperty("more_options"), false));

    return panel;
  }

  private
  AccountStatement
  createStatement()
  {
    Account account = (Account)getAccounts().get(getAccountChooser().getSelectedItem());

    return AccountStatement.createStatement(account, getDatePeriodChooser().getStartDate(),
        getDatePeriodChooser().getEndDate());
  }

  private
  ElementComboBoxChooser
  getAccountChooser()
  {
    return itsAccountChooser;
  }

  private
  CheckBox[]
  getCheckBoxes()
  {
    return itsCheckBoxes;
  }

  private
  DatePeriodChooser
  getDatePeriodChooser()
  {
    return itsDatePeriodChooser;
  }

  private
  static
  String
  getProperty(String key)
  {
    return I18NHelper.getProperty("AccountStatementDialog." + key);
  }

  private
  void
  setAccountChooser(ElementComboBoxChooser chooser)
  {
    itsAccountChooser = chooser;
  }

  private
  void
  setDatePeriodChooser(DatePeriodChooser chooser)
  {
    itsDatePeriodChooser = chooser;
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
      Object source = event.getSource();

      if(source == getCheckBoxes()[INCLUDE_CATEGORIES])
      {
        setIncludeCategoriesInAccountStatement(!includeCategoriesInAccountStatement());
      }
      else if(source == getCheckBoxes()[INCLUDE_CHECK])
      {
        setIncludeCheckInAccountStatement(!includeCheckInAccountStatement());
      }
      else if(source == getCheckBoxes()[INCLUDE_NOTES])
      {
        setIncludeNotesInAccountStatement(!includeNotesInAccountStatement());
      }
      else if(source == getCheckBoxes()[INCLUDE_RECONCILED])
      {
        setIncludeReconciledStatusInAccountStatement(!includeReconciledStatusInAccountStatement());
      }
      else
      {
        setAccepted(event.getActionCommand().equals(ACTION_OK));

        if(wasAccepted() == true)
        {
          AccountStatementWriter.generate(createStatement());
        }
        else
        {
          dispose();
        }
      }
    }
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  private ElementComboBoxChooser itsAccountChooser;
  private CheckBox[] itsCheckBoxes;
  private DatePeriodChooser itsDatePeriodChooser;

  private static final int INCLUDE_CATEGORIES = 0;
  private static final int INCLUDE_CHECK = 1;
  private static final int INCLUDE_NOTES = 2;
  private static final int INCLUDE_RECONCILED = 3;
}
