// AccountsPanel

package org.javamoney.examples.ez.money.gui.dialog.preferences;

import static org.javamoney.examples.ez.common.utility.BorderHelper.createTitledBorder;
import static org.javamoney.examples.ez.common.utility.ButtonHelper.buildButton;
import static org.javamoney.examples.ez.common.utility.I18NHelper.getSharedProperty;
import static javax.swing.SwingConstants.TRAILING;
import static org.javamoney.examples.ez.money.ApplicationProperties.UI_CURRENCY_SYMBOL;
import static org.javamoney.examples.ez.money.ApplicationProperties.UI_CURRENCY_FORMAT;
import static org.javamoney.examples.ez.money.model.DataTypeKeys.ACCOUNT;
import static org.javamoney.examples.ez.money.utility.IDHelper.confirmRemoval;
import static org.javamoney.examples.ez.money.utility.IDHelper.promptForAdd;
import static org.javamoney.examples.ez.money.utility.IDHelper.promptForEdit;
import static org.javamoney.examples.ez.money.utility.IDHelper.showMessage;
import static org.javamoney.examples.ez.money.utility.IDHelper.MessageKeys.IN_USE;
import static org.javamoney.examples.ez.money.utility.IDHelper.MessageKeys.UNABLE_TO_REMOVE;

import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JTextField;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import org.javamoney.examples.ez.money.model.persisted.account.Account;
import org.javamoney.examples.ez.money.model.persisted.account.AccountTypeKeys;
import org.javamoney.examples.ez.money.utility.TransactionHelper;
import org.javamoney.examples.ez.common.gui.CheckBox;
import org.javamoney.examples.ez.common.gui.ComboBox;
import org.javamoney.examples.ez.common.gui.Panel;
import org.javamoney.examples.ez.common.utility.ClipboardMenuController;
import org.javamoney.examples.ez.common.utility.TextConstrainer;
import org.javamoney.moneta.Money;

/**
 * This class facilitates managing the accounts.
 */
public
final
class
AccountsPanel
extends DataElementPanel
{
  /**
	 * 
	 */
	private static final long serialVersionUID = -6962300921859527548L;
/**
   * Constructs a new preferences panel.
   */
  public
  AccountsPanel()
  {
    super(PreferencesKeys.ACCOUNTS);

    setCheckBox(new CheckBox());
    setField(new JTextField());
    setTypeChooser(new ComboBox(AccountTypeKeys.values()));

    buildPanel();

    // Add listeners.
    new ClipboardMenuController(getField());
    getChooser().addTreeSelectionListener(new SelectionHandler());
    getField().addFocusListener(new FocusHandler());
    getField().addKeyListener(new KeyHandler());
    getTypeChooser().addActionListener(new ActionHandler());
  }

  /**
   * This method updates this panel's collection.
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
    Account account = (Account)getChooser().getSelectedElement();
    String identifier = promptForEdit(ACCOUNT, account.getIdentifier());

    if(identifier != null)
    {
      String temp = account.getIdentifier(); // Store for mass update.

      if(getCollection().changeIdentifier(account, identifier) == true)
      {
        // Update all transactions, the view, and select the edited element.
        TransactionHelper.massUpdate(TransactionHelper.MassUpdateFieldKeys.ACCOUNT,
            temp, identifier);

        displayCollectables();
        getChooser().setSelectedCollectable(account);
      }
      else
      {
        // It is safe to assume it already existed.
        showMessage(IN_USE, ACCOUNT);
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
    String identifier = promptForAdd(ACCOUNT);

    if(identifier != null)
    {
      Account account = new Account(AccountTypeKeys.DEPOSIT, identifier);

      if(getCollection().add(account) == true)
      {
        // Update view and select the new element.
        displayCollectables();
        getChooser().setSelectedCollectable(account);
      }
      else
      {
        // It is safe to assume it already existed.
        showMessage(IN_USE, ACCOUNT);
      }
    }
  }

  private
  boolean
  allowEvents()
  {
    return itsAllowEvents;
  }

  private
  void
  buildPanel()
  {
    ActionHandler handler = new ActionHandler();

    getField().setDocument(new TextConstrainer(13, "0123456789,.-"));
    getField().setHorizontalAlignment(TRAILING);

    // Build panel.
    setFill(GridBagConstraints.BOTH);
    add(createChooserPanel(handler), 0, 0, 1, 1, 100, 100);
    add(createInfoPanel(), 0, 1, 1, 1, 0, 0);
    add(createButtonPanel(handler), 0, 2, 1, 1, 0, 0);

    // Initialize the panel with the selected account.
    displayAccountInfo();
  }

  private
  Panel
  createInfoPanel()
  {
    Panel panel = new Panel();
    String gap = ": ";

    // Build check box.
    buildButton(getCheckBox(), getSharedProperty("active"), new ActionHandler());

    // Build panel.
    panel.setAnchor(GridBagConstraints.EAST);
    panel.add(getSharedProperty("type") + gap, 0, 0, 1, 1, 0, 50);
    panel.add(getSharedProperty("balance") + gap, 2, 0, 1, 1, 100, 0);

    panel.setFill(GridBagConstraints.HORIZONTAL);
    panel.add(getTypeChooser(), 1, 0, 1, 1, 0, 0);
    panel.add(getField(), 3, 0, 1, 1, 0, 0);
    panel.add(getCheckBox(), 0, 1, 2, 1, 0, 50);

    // Aesthetic spacers.
    panel.addEmptyCellAt(1, 1, 20);
    panel.addEmptyCellAt(3, 1, 15);

    panel.setBorder(createTitledBorder(getSharedProperty("account_info")));

    return panel;
  }

  private
  void
  displayAccountInfo()
  {
    Account account = (Account)getChooser().getSelectedElement();
    AccountTypeKeys type = AccountTypeKeys.DEPOSIT;
    String balance = "";
    boolean isActive = false;

    // Stop responding to events.
    setAllowEvents(false);

    if(account != null)
    {
      balance = UI_CURRENCY_FORMAT.format(account.getBalance().doubleValue(), false);
      isActive = account.isActive();
      type = account.getType();
    }

    // Display account data.
    getCheckBox().setEnabled(account != null);
    getCheckBox().setSelected(isActive);

    getField().setEnabled(isActive);
    getField().setText(balance);

    getTypeChooser().setEnabled(isActive);
    getTypeChooser().setSelectedItem(type);

    // Resume responding to events.
    setAllowEvents(true);
  }

  private
  void
  displayCollectables()
  {
    // Stop responding to events.
    setAllowEvents(false);

    getChooser().displayCollectables();

    enableLinks();
    showProperChooserPanel();

    // Resume responding to events.
    setAllowEvents(true);
  }

  private
  CheckBox
  getCheckBox()
  {
    return itsCheckBox;
  }

  private
  JTextField
  getField()
  {
    return itsField;
  }

  private
  ComboBox
  getTypeChooser()
  {
    return itsTypeChooser;
  }

  private
  void
  remove()
  {
    if(confirmRemoval(ACCOUNT) == true)
    {
      Account account = (Account)getChooser().getSelectedElement();

      // Transactions that are transfers and reference this account are not
      // updated.
      if(getCollection().remove(account) == true)
      {
        // Update view.
        displayCollectables();

        if(getChooser().length() != 0)
        {
          getChooser().selectFirst();
        }
        else
        {
          displayAccountInfo();
        }
      }
      else
      {
        showMessage(UNABLE_TO_REMOVE, ACCOUNT);
      }
    }
  }

  private
  void
  setAccountType()
  {
    Account account = (Account)getChooser().getSelectedElement();
    AccountTypeKeys type = (AccountTypeKeys)getTypeChooser().getSelectedItem();

    if(type != account.getType())
    {
      if(getCollection().remove(account) == true)
      {
        Account newAccount = new Account(type, account.getIdentifier(), account.getBalance());

        // Copy the transactions.
        newAccount.addAll(account);

        getCollection().add(newAccount);
        displayCollectables();
        getChooser().setSelectedCollectable(newAccount);
      }
    }
  }

  private
  void
  setActive()
  {
    Account account = (Account)getChooser().getSelectedElement();

    account.setIsActive(!account.isActive());

    displayAccountInfo();
  }

  private
  void
  setAllowEvents(boolean value)
  {
    itsAllowEvents = value;
  }

  private
  void
  setBalance()
  {
    Account account = (Account)getChooser().getSelectedElement();
    double balance = 0.0;

    try
    {
      balance = UI_CURRENCY_FORMAT.parse(getField().getText());
    }
    catch(Exception exception)
    {
      // Ignored.
    }

    account.setBalance(Money.of(UI_CURRENCY_SYMBOL.getCurrency(), balance));
  }

  private
  void
  setCheckBox(CheckBox checkBox)
  {
    itsCheckBox = checkBox;
  }

  private
  void
  setField(JTextField field)
  {
    itsField = field;
  }

  private
  void
  setTypeChooser(ComboBox comboBox)
  {
    itsTypeChooser = comboBox;
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

      if(source == getCheckBox())
      {
        setActive();
      }
      else if(source == getTypeChooser())
      {
        if(allowEvents() == true)
        {
          setAccountType();
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
  FocusHandler
  extends FocusAdapter
  {
    @Override
    public
    void
    focusLost(FocusEvent event)
    {
      Account account = (Account)getChooser().getSelectedElement();

      getField().setText(UI_CURRENCY_FORMAT.format(account.getBalance().doubleValue(), false));
    }
  }

  private
  class
  KeyHandler
  extends KeyAdapter
  {
    @Override
    public
    void
    keyReleased(KeyEvent event)
    {
      setBalance();
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
      if(allowEvents() == true)
      {
        displayAccountInfo();
      }
    }
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  private boolean itsAllowEvents;
  private CheckBox itsCheckBox;
  private JTextField itsField;
  private ComboBox itsTypeChooser;
}
