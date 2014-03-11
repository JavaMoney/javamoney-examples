// TransactionFactory

package org.javamoney.examples.ez.money.gui.view.register;

import static org.javamoney.examples.ez.money.ApplicationProperties.UI_CURRENCY_SYMBOL;
import static org.javamoney.examples.ez.money.ApplicationProperties.UI_CURRENCY_FORMAT;
import static org.javamoney.examples.ez.money.ApplicationProperties.UI_DATE_FORMAT;
import static org.javamoney.examples.ez.money.ApplicationProperties.newTransactionsArePending;
import static org.javamoney.examples.ez.money.ApplicationProperties.setNewTransactionsArePending;
import static org.javamoney.examples.ez.money.ApplicationThread.getFrame;
import static org.javamoney.examples.ez.money.KeywordKeys.TRANSFER_TO;
import static org.javamoney.examples.ez.money.gui.view.register.FormButtonKeys.CANCEL;
import static org.javamoney.examples.ez.money.gui.view.register.FormButtonKeys.DATE_PICKER;
import static org.javamoney.examples.ez.money.gui.view.register.FormButtonKeys.EDIT;
import static org.javamoney.examples.ez.money.gui.view.register.FormButtonKeys.ENTER;
import static org.javamoney.examples.ez.money.gui.view.register.FormButtonKeys.NEW;
import static org.javamoney.examples.ez.money.gui.view.register.FormButtonKeys.NEXT;
import static org.javamoney.examples.ez.money.gui.view.register.FormButtonKeys.PENDING;
import static org.javamoney.examples.ez.money.gui.view.register.FormButtonKeys.SPLIT;
import static org.javamoney.examples.ez.money.gui.view.register.FormFieldKeys.AMOUNT;
import static org.javamoney.examples.ez.money.gui.view.register.FormFieldKeys.CHECK_NUMBER;
import static org.javamoney.examples.ez.money.gui.view.register.FormFieldKeys.DATE;
import static org.javamoney.examples.ez.money.gui.view.register.FormFieldKeys.NOTES;
import static org.javamoney.examples.ez.money.model.DataManager.getAccounts;
import static org.javamoney.examples.ez.money.model.DataManager.getExpenses;
import static org.javamoney.examples.ez.money.model.DataManager.getIncome;
import static org.javamoney.examples.ez.money.model.DataManager.getPayees;
import static org.javamoney.examples.ez.money.model.dynamic.transaction.TransactionTypeKeys.EXPENSE;
import static org.javamoney.examples.ez.money.model.dynamic.transaction.TransactionTypeKeys.INCOME;
import static org.javamoney.examples.ez.money.model.dynamic.transaction.TransactionTypeKeys.TRANSFER;
import static org.javamoney.examples.ez.money.model.persisted.account.AccountTypeKeys.CREDIT;
import static org.javamoney.examples.ez.money.utility.DialogHelper.inform;
import static org.javamoney.examples.ez.money.utility.IDHelper.purgeIdentifier;
import static org.javamoney.examples.ez.money.utility.TransactionHelper.isSplit;
import static org.javamoney.examples.ez.money.utility.TransactionHelper.removeFrom;

import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.money.MonetaryAmount;

import org.javamoney.examples.ez.money.gui.dialog.SplitDialog;
import org.javamoney.examples.ez.money.gui.view.RegisterView;
import org.javamoney.examples.ez.money.gui.view.ViewKeys;
import org.javamoney.examples.ez.money.model.dynamic.transaction.TransactionTypeKeys;
import org.javamoney.examples.ez.money.model.persisted.account.Account;
import org.javamoney.examples.ez.money.model.persisted.payee.Payee;
import org.javamoney.examples.ez.money.model.persisted.transaction.Transaction;
import org.javamoney.examples.ez.money.utility.TransactionDateHelper;
import org.javamoney.examples.ez.common.gui.Panel;
import org.javamoney.examples.ez.common.utility.I18NHelper;
import org.javamoney.moneta.Money;

/**
 * This class facilitates creating transactions.
 */
abstract
class
TransactionFactory
extends Panel
{
  /**
	 * 
	 */
	private static final long serialVersionUID = 6592068985005342909L;
/**
   * Constructs a new transaction creator for the specified transaction type.
   *
   * @param key The type of transactions to create.
   */
  protected
  TransactionFactory(TransactionTypeKeys key)
  {
    setForm(new Form(key, new ActionHandler()));
    setLastUsedDate(new Date());

    // Build panel.
    setFill(GridBagConstraints.BOTH);
    add(getForm(), 0, 0, 1, 1, 100, 100);

    clear();
    enableForm(false);
  }

  /**
   * This method adds the specified transaction to the current account. It then
   * returns the transaction if it was successfully added, otherwise it returns
   * null.
   *
   * @param trans The transaction to add.
   *
   * @return The transaction if successfully added, otherwise null.
   */
  protected
  final
  Transaction
  addTransaction(Transaction trans)
  {
    return addTransactionTo(getAccount(), trans);
  }

  /**
   * This method adds the specified transaction to the specified account. It
   * then returns the transaction if it was successfully added, otherwise it
   * returns null.
   *
   * @param account The account to add the transaction to.
   * @param trans The transaction to add.
   *
   * @return The transaction if successfully added, otherwise null.
   */
  protected
  final
  Transaction
  addTransactionTo(Account account, Transaction trans)
  {
    boolean result = account.addTransaction(trans);

    if(result == false)
    {
      trans = null;

      inform(getProperty("error.title") + " \"" + account + "\".",
         getProperty("error.description"));
    }

    return trans;
  }

  /**
   * This method clears its form and defaults all its values.
   */
  protected
  final
  void
  clear()
  {
    getForm().clearFields();

    getForm().getButton(PENDING).setSelected(newTransactionsArePending());
    getForm().getField(DATE).setText(UI_DATE_FORMAT.format(getLastUsedDate()));

    setEditModeTransaction(null);
    setIsInEditMode(false);
    setSplit(null);
  }

  /**
   * This method creates and then adds the newly created transaction to its
   * account. It then returns the transaction if it was successfully added,
   * otherwise it returns null.
   *
   * @return The transaction if successfully added, otherwise null.
   */
  protected
  abstract
  Transaction
  createAndAdd();

  /**
   * This method creates and returns a new transaction from the criteria
   * provided by the form. The specified key is used to determine the proper
   * amount; expenses are negative and incomes are positive.
   *
   * @param type The type of transaction to create.
   *
   * @return A new transaction.
   */
  protected
  final
  Transaction
  createTransaction(TransactionTypeKeys type)
  {
    Transaction trans = null;
    String payee = getForm().getPayFrom();
    double amount = parseAmount();

    // Put amount in proper form.
    if((type == INCOME && amount < 0.0) || (type == EXPENSE && amount >= 0.0))
    {
      amount = -amount;
    }

    // Put payee in proper form.
    payee = purgeIdentifier(payee);

    trans = new Transaction(getForm().getField(CHECK_NUMBER).getText(), parseDate(),
        payee, Money.of(UI_CURRENCY_SYMBOL.getCurrency(), amount), getCategory(), getForm().getField(NOTES).getText());

    // Set attributes not applicable in the constructor.
    trans.setIsReconciled(getForm().getButton(PENDING).isSelected() == false);

    if(isInEditMode() == true)
    {
      trans.setLabel(getEditModeTransaction().getLabel());
    }

    return trans;
  }

  /**
   * The method causes the creator to enter edit mode with the specified
   * transaction.
   *
   * @param trans The transaction to edit.
   * @param startEdit Whether or not to start editing the transaction.
   */
  protected
  final
  void
  doEdit(Transaction trans, boolean startEdit)
  {
    enableForm(false);
    getForm().getButton(EDIT).setEnabled(true);

    fillFormWith(trans);

    setIsInEditMode(true);
    setEditModeTransaction(trans);

    if(startEdit == true)
    {
      enableForm(true);
      getForm().getButton(EDIT).setEnabled(false);
      getForm().getButton(PENDING).requestFocus();
    }
  }

  /**
   * This method causes the creator to enter new mode.
   */
  protected
  void
  doNew()
  {
    getRegisterView().clearTableSelection();
    clear();
    enableForm(true);
    getForm().getButton(EDIT).setEnabled(false);
    getForm().getField(CHECK_NUMBER).requestFocus();
  }

  /**
   * This method enables or disables the elements of the from depending of the
   * specified value.
   *
   * @param value true or false.
   */
  protected
  final
  void
  enableForm(boolean value)
  {
    getForm().enableForm(value);

    if(getForm().getType() == TRANSFER)
    {
      // Initialize both chooser with the current account. At least one will be
      // correct.
      if(isInEditMode() == false)
      {
        getForm().getPayFromChooser().setSelectedItem(getAccount().getIdentifier());
        getForm().getPayToChooser().setSelectedItem(getAccount().getIdentifier());
      }

      // Enforce that making a payment to a credit card is a transfer to.
      if(getAccount().getType() == CREDIT)
      {
        getForm().getPayToChooser().setEnabled(false);
      }
    }
  }

  /**
   * This method returns the account this form is referencing.
   *
   * @return The account this form is referencing.
   */
  protected
  final
  Account
  getAccount()
  {
    return getRegisterView().getAccount();
  }

  /**
   * This method returns the category.
   *
   * @return The category.
   */
  protected
  final
  String
  getCategory()
  {
    String category = null;


    if(getSplit() != null)
    {
      category = getSplit();
    }
    else
    {
      category = getForm().getPayTo();
    }

    return category;
  }

  /**
   * This method returns the creator's form.
   *
   * @return The creator's form.
   */
  protected
  final
  Form
  getForm()
  {
    return itsForm;
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of private methods.
  //////////////////////////////////////////////////////////////////////////////

  private
  void
  addPayeeToCollection()
  {
    if(getForm().getType() != TRANSFER)
    {
      Payee payee = new Payee(getForm().getPayFrom());

      if(payee.getIdentifier().length() != 0)
      {
        getPayees().add(payee);
        getForm().getPayFromChooser().displayElements();
      }
    }
  }

  private
  void
  applyChanges()
  {
    Transaction transAdded = createAndAdd();

    if(transAdded != null)
    {
      if(isInEditMode() == true)
      {
        removeFrom(getAccount(), getEditModeTransaction());
      }
      else
      {
        setLastUsedDate(transAdded.getDate());
        setNewTransactionsArePending(getForm().getButton(PENDING).isSelected());
      }

      addPayeeToCollection();
      clear();
      enableForm(false);

      getRegisterView().displayTransactions(transAdded);

      getForm().getButton(NEW).requestFocus();
    }
  }

  private
  void
  fillFormWith(Transaction trans)
  {
    getForm().getButton(PENDING).setSelected(trans.isReconciled() == false);
    getForm().getField(CHECK_NUMBER).setText(trans.getCheckNumber());
    getForm().getField(DATE).setText(UI_DATE_FORMAT.format(trans.getDate()));
    getForm().getField(NOTES).setText(trans.getNotes());

    setAmount(trans.getAmount());

    if(getForm().getType() == TRANSFER)
    {
      if(trans.getCategory().equals(TRANSFER_TO.toString()) == true)
      {
        getForm().getPayFromChooser().setSelectedItem(getAccount().getIdentifier());
        getForm().getPayToChooser().setSelectedItem(trans.getPayee());
      }
      else
      {
        getForm().getPayFromChooser().setSelectedItem(trans.getPayee());
        getForm().getPayToChooser().setSelectedItem(getAccount().getIdentifier());
      }
    }
    else
    {
      getForm().getPayFromChooser().setSelectedItem(trans.getPayee());

      if(isSplit(trans) == true)
      {
        getForm().setPayToIsSplit(true);
        setSplit(trans.getCategory());
      }
      else
      {
        getForm().getPayToChooser().setSelectedItem(trans.getCategory());
      }
    }
  }

  private
  Transaction
  getEditModeTransaction()
  {
    return itsEditModeTransaction;
  }

  private
  Date
  getLastUsedDate()
  {
    return itsLastUsedDate;
  }

  private
  static
  String
  getProperty(String key)
  {
    return I18NHelper.getProperty("TypeCreator." + key);
  }

  private
  RegisterView
  getRegisterView()
  {
    return (RegisterView)getFrame().getViews().getView(ViewKeys.REGISTER);
  }

  private
  String
  getSplit()
  {
    return itsSplit;
  }

  private
  boolean
  isInEditMode()
  {
    return itsIsInEditMode;
  }

  private
  String
  nextCheckNumber()
  {
    Account account = null;
    int max = 0;

    if(getForm().getType() == TRANSFER)
    {
      account = (Account)getAccounts().get(getForm().getPayFrom());
    }
    else
    {
      account = getAccount();
    }

    for(Transaction trans : account.getTransactions())
    {
      if(trans.getCheckNumber().length() != 0)
      {
        try
        {
          int number = Integer.parseInt(trans.getCheckNumber());

          if(max < number)
          {
            max = number;
          }
        }
        catch(Exception exception)
        {
          // Ignored.
        }
      }
    }

    return "" + (max + 1);
  }

  private
  double
  parseAmount()
  {
    double amount = 0.0;

    try
    {
      String str = getForm().getField(AMOUNT).getText();

      if(str.length() != 0)
      {
        amount = UI_CURRENCY_FORMAT.parse(str);
      }
    }
    catch(Exception exception)
    {
      // Ignored.
    }

    return amount;
  }

  private
  Date
  parseDate()
  {
    Date date = getLastUsedDate();
    String text = getForm().getField(DATE).getText();

    try
    {
      date = UI_DATE_FORMAT.parse(text);
    }
    catch(Exception exception)
    {
      // Ignored.
    }

    return date;
  }

  private
  void
  setAmount(double value)
  {
    getForm().getField(AMOUNT).setText(UI_CURRENCY_FORMAT.format(Math.abs(value)));
  }

  private
  void
  setAmount(MonetaryAmount value)
  {
	  setAmount(value.getNumber().doubleValue());
  }
  
  private
  void
  setEditModeTransaction(Transaction trans)
  {
    itsEditModeTransaction = trans;
  }

  private
  void
  setForm(Form panel)
  {
    itsForm = panel;
  }

  private
  void
  setIsInEditMode(boolean value)
  {
    itsIsInEditMode = value;
  }

  private
  void
  setLastUsedDate(Date date)
  {
    itsLastUsedDate = date;
  }

  private
  void
  setSplit(String split)
  {
    itsSplit = split;
  }

  private
  void
  showDateDialog()
  {
    Date date = TransactionDateHelper.showDateDialog(parseDate());

    getForm().getField(DATE).setText(UI_DATE_FORMAT.format(date));
    getForm().getPayFromChooser().requestFocus();
  }

  private
  void
  showSplitDialog()
  {
    SplitDialog split = null;

    if(getForm().getType() == EXPENSE)
    {
      split = new SplitDialog(getExpenses(), getCategory(), parseAmount());
    }
    else
    {
      split = new SplitDialog(getIncome(), getCategory(), parseAmount());
    }

    if(split.showDialog() == true)
    {
      setAmount(split.getTotal());
      setSplit(split.getSplit());

      getForm().setPayToIsSplit(getSplit() != null);
    }
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

      if(source == getForm().getButton(CANCEL))
      {
        clear();
        enableForm(false);
        getRegisterView().clearTableSelection();
      }
      else if(source == getForm().getButton(DATE_PICKER))
      {
        showDateDialog();
      }
      else if(source == getForm().getButton(EDIT))
      {
        enableForm(true);
        getForm().getButton(EDIT).setEnabled(false);
        getForm().getButton(PENDING).requestFocus();
      }
      else if(source == getForm().getButton(ENTER))
      {
        applyChanges();
      }
      else if(source == getForm().getButton(NEW))
      {
        doNew();
      }
      else if(source == getForm().getButton(NEXT))
      {
        getForm().getField(CHECK_NUMBER).setText(nextCheckNumber());
        getForm().getField(DATE).requestFocus();
      }
      else if(source == getForm().getButton(SPLIT))
      {
        showSplitDialog();
      }
    }
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  private Transaction itsEditModeTransaction;
  private Form itsForm;
  private boolean itsIsInEditMode;
  private Date itsLastUsedDate;
  private String itsSplit;
}
