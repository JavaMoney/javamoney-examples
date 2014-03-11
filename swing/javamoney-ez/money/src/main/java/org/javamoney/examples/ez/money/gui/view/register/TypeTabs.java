// TypeTabs

package org.javamoney.examples.ez.money.gui.view.register;

import static org.javamoney.examples.ez.common.utility.I18NHelper.getSharedProperty;
import static org.javamoney.examples.ez.money.utility.TransactionHelper.isIncome;
import static org.javamoney.examples.ez.money.utility.TransactionHelper.isTransfer;

import java.awt.GridBagConstraints;

import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.javamoney.examples.ez.money.model.persisted.account.AccountTypeKeys;
import org.javamoney.examples.ez.money.model.persisted.transaction.Transaction;

import org.javamoney.examples.ez.common.gui.Panel;
import org.javamoney.examples.ez.common.utility.I18NHelper;

/**
 * This class facilitates displaying transaction types in tabs.
 */
final
class
TypeTabs
extends Panel
{
  /**
   * Constructs a new tab panel for the specified account.
   *
   * @param key The type of account to display transaction types for.
   */
  protected
  TypeTabs(AccountTypeKeys key)
  {
    setKey(key);
    setTabs(new JTabbedPane());

    createTypeCreators();

    addTabs();
    buildPanel();
  }

  /**
   * This method clears all the types' forms.
   */
  protected
  void
  clear()
  {
    for(TransactionFactory panel : getTypeCreators())
    {
      panel.clear();
      panel.enableForm(false);
    }
  }

  /**
   * This method causes the transaction type associated with the specified
   * transaction to enter edit mode.
   *
   * @param trans The transaction to edit.
   * @param startEdit Whether or not to start editing the transaction.
   */
  protected
  void
  doEdit(Transaction trans, boolean startEdit)
  {
    int index = EXPENSE;

    clear();

    if(isTransfer(trans) == true)
    {
      index = TRANSFER;
    }
    else if(isIncome(trans) == true)
    {
      index = INCOME;
    }

    getTabs().setSelectedIndex(index);
    getTypeCreators()[index].doEdit(trans, startEdit);
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of private methods.
  //////////////////////////////////////////////////////////////////////////////

  private
  void
  addTabs()
  {
    String[] titles = getTabTitles();

    // Add builders to tabs.
    for(int len = 0; len < titles.length; ++len)
    {
      String spacer = createSpacer(titles[len]);

      getTabs().addTab(spacer + titles[len] + spacer, getTypeCreators()[len]);
    }
  }

  private
  void
  buildPanel()
  {
    int height = 15;

    // Build panel.
    // This spacer prevents the forms from shifting vertically.
    addEmptyRowsAt(0, 0, height);

    setFill(GridBagConstraints.BOTH);
    add(getTabs(), 1, 0, 1, height, 100, 100);

    // Add listeners.
    getTabs().addChangeListener(new ChangeHandler());
  }

  private
  String
  createSpacer(String title)
  {
    StringBuilder builder = new StringBuilder();
    int max = (50 - title.length()) / 2;

    for(int len = 0; len < max; ++len)
    {
      builder.append(' ');
    }

    return builder.toString();
  }

  private
  void
  createTypeCreators()
  {
    itsTypeCreators = new TransactionFactory[3];

    getTypeCreators()[INCOME] = new IncomeFactory();
    getTypeCreators()[EXPENSE] = new ExpenseFactory();
    getTypeCreators()[TRANSFER] = new TransferFactory();
  }

  private
  AccountTypeKeys
  getKey()
  {
    return itsKey;
  }

  private
  static
  String
  getProperty(String key)
  {
    return I18NHelper.getProperty("TypeTabs." + key);
  }

  private
  JTabbedPane
  getTabs()
  {
    return itsTabs;
  }

  private
  String[]
  getTabTitles()
  {
    String[] titles = null;

    if(getKey() == AccountTypeKeys.CASH)
    {
      titles = new String[] {
          getProperty("spend"),
          getProperty("receive"),
          getSharedProperty("transfer")
      };
    }
    else if(getKey() == AccountTypeKeys.CREDIT)
    {
      titles = new String[] {
          getProperty("purchase"),
          getProperty("credit"),
          getProperty("payment")
      };
    }
    else
    {
      titles = new String[] {
          getProperty("withdrawal"),
          getProperty("deposit"),
          getSharedProperty("transfer")
      };
    }

    return titles;
  }

  private
  TransactionFactory[]
  getTypeCreators()
  {
    return itsTypeCreators;
  }

  private
  void
  setKey(AccountTypeKeys key)
  {
    itsKey = key;
  }

  private
  void
  setTabs(JTabbedPane tabs)
  {
    itsTabs = tabs;
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of inner classes.
  //////////////////////////////////////////////////////////////////////////////

  private
  class
  ChangeHandler
  implements ChangeListener
  {
    public
    void
    stateChanged(ChangeEvent event)
    {
      clear();
    }
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  private AccountTypeKeys itsKey;
  private JTabbedPane itsTabs;
  private TransactionFactory[] itsTypeCreators;

  private static final int EXPENSE = 0;
  private static final int INCOME = 1;
  private static final int TRANSFER = 2;
}
