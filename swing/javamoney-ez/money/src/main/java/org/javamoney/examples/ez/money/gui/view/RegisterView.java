// RegisterView

package org.javamoney.examples.ez.money.gui.view;

import static org.javamoney.examples.ez.common.utility.ButtonHelper.buildButton;
import static org.javamoney.examples.ez.common.utility.I18NHelper.getSharedProperty;
import static org.javamoney.examples.ez.money.ApplicationThread.getFrame;
import static org.javamoney.examples.ez.money.model.DataManager.getAccounts;
import static org.javamoney.examples.ez.money.model.dynamic.transaction.TransactionFilterFieldKeys.ALL;
import static org.javamoney.examples.ez.money.model.dynamic.transaction.TransactionFilterFieldKeys.AMOUNT;
import static org.javamoney.examples.ez.money.model.dynamic.transaction.TransactionFilterFieldKeys.CHECK;
import static org.javamoney.examples.ez.money.model.dynamic.transaction.TransactionFilterFieldKeys.DATE;
import static org.javamoney.examples.ez.money.model.dynamic.transaction.TransactionFilterFieldKeys.NOTES;
import static org.javamoney.examples.ez.money.model.dynamic.transaction.TransactionFilterFieldKeys.PAYEE;

import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenuItem;

import org.javamoney.examples.ez.money.gui.chooser.ElementComboBoxChooser;
import org.javamoney.examples.ez.money.gui.view.register.RegisterPanel;
import org.javamoney.examples.ez.money.model.dynamic.transaction.TransactionFilter;
import org.javamoney.examples.ez.money.model.persisted.account.Account;
import org.javamoney.examples.ez.money.model.persisted.transaction.Transaction;

import org.javamoney.examples.ez.common.gui.Panel;
import org.javamoney.examples.ez.common.gui.PopupMenu;
import org.javamoney.examples.ez.common.gui.SearchWidget;
import org.javamoney.examples.ez.common.utility.I18NHelper;

/**
 * This class facilitates viewing an account's transactions.
 */
public
final
class
RegisterView
extends View
{
  /**
   * Constructs a new view.
   */
  public
  RegisterView()
  {
    super(ViewKeys.REGISTER);

    setAccount(null);
    setAccountChooser(new ElementComboBoxChooser(getAccounts()));
    setFilter(new TransactionFilter());
    setPanel(new Panel());
    setSearchWidget(new SearchWidget(createPopupMenu()));

    // Build panel.
    setFill(GridBagConstraints.BOTH);
    add(createToolsPanel(), 0, 0, 1, 1, 0, 0);
    add(getPanel(), 0, 1, 1, 1, 100, 100);

    getAccountChooser().setAllowInactiveAccounts(false);

    // Add listeners.
    getAccountChooser().addActionListener(new ActionHandler());
    getSearchWidget().addActionListener(new ActionHandler());
  }

  /**
   * This method unselects all selected transactions in the register's table.
   */
  public
  void
  clearTableSelection()
  {
    getRegisterPanel().clearTableSelection();
  }

  /**
   * This method puts all the account's transactions into the register table and
   * updates its balance.
   * <p>
   * <b>Note:</b> This method is for performance improvement over updateView()
   * when the register view is already visible.
   *
   * @param trans The transaction to scroll to.
   */
  public
  void
  displayTransactions(Transaction trans)
  {
    getRegisterPanel().updateView(getFilter(), trans);
  }

  /**
   * This method returns the account the register has a reference to or null if
   * there is not one.
   *
   * @return The account the register has a reference to.
   */
  public
  Account
  getAccount()
  {
    return itsAccount;
  }

  /**
   * This method returns true if the register has an account to reference,
   * otherwise false.
   *
   * @return true of false.
   */
  public
  boolean
  hasAccount()
  {
    return getAccount() != null;
  }

  /**
   * This method sets the account the register has a reference to.
   *
   * @param account The account to reference.
   */
  public
  void
  setAccount(Account account)
  {
    itsAccount = account;
  }

  /**
   * This method updates the view by redisplaying all the account's
   * transactions.
   */
  @Override
  public
  void
  updateView()
  {
    // Stop responding to events.
    setAllowEvents(false);

    getAccountChooser().displayElements(getAccounts());
    getPanel().removeAll();

    // Get new reference to account incase the type or collection has changed.
    setAccount(getProperAccountReference());

    if(getAccount() != null)
    {
      setRegisterPanel(new RegisterPanel(getAccount()));

      getAccountChooser().setSelectedItem(getAccount().getIdentifier());
      getFilter().updateFormats();

      // Build panel.
      getPanel().setFill(GridBagConstraints.BOTH);
      getPanel().add(getRegisterPanel(), 0, 0, 1, 1, 100, 100);

      displayTransactions(null);
    }

    // Resume responding to events.
    setAllowEvents(true);
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of private methods.
  //////////////////////////////////////////////////////////////////////////////

  private
  boolean
  allowEvents()
  {
    return itsAllowEvents;
  }

  private
  static
  JMenuItem
  createMenuItem(String command, ActionHandler handler, ButtonGroup group,
      boolean selected)
  {
    JCheckBoxMenuItem item = new JCheckBoxMenuItem();

    // Build items.
    buildButton(item, command, handler, group);

    item.setSelected(selected);

    return item;
  }

  private
  PopupMenu
  createPopupMenu()
  {
    PopupMenu menu = new PopupMenu();
    ActionHandler handler = new ActionHandler();
    ButtonGroup group = new ButtonGroup();
    JMenuItem item = new JMenuItem(getSharedProperty("search"));

    item.setEnabled(false);

    // Add items to menu.
    menu.add(item);
    menu.add(createMenuItem(ACTION_ALL, handler, group, true));
    menu.addSeparator();
    menu.add(createMenuItem(ACTION_AMOUNT, handler, group, false));
    menu.add(createMenuItem(ACTION_CHECK, handler, group, false));
    menu.add(createMenuItem(ACTION_DATE, handler, group, false));
    menu.add(createMenuItem(ACTION_NOTES, handler, group, false));
    menu.add(createMenuItem(ACTION_PAYEE, handler, group, false));
    menu.setBehaveLikeMenu(true);

    return menu;
  }

  private
  Panel
  createToolsPanel()
  {
    Panel panel = new Panel();

    getSearchWidget().setToolTipText(getProperty("search_tip"));

    // Build panel.
    panel.setAnchor(GridBagConstraints.EAST);
    panel.add(getSharedProperty("account") + ": ", 0, 1, 1, 1, 0, 100);

    panel.setFill(GridBagConstraints.HORIZONTAL);
    panel.add(getAccountChooser(), 1, 1, 1, 1, 0, 0);
    panel.add(getSearchWidget(), 3, 1, 1, 1, 0, 0);

    panel.setAnchor(GridBagConstraints.CENTER);
    panel.setFill(GridBagConstraints.NONE);
    panel.add(getSharedProperty("search"), 3, 2, 1, 1, 0, 0);

    // Aesthetic spacers.
    panel.addEmptyCellAt(1, 0, 24);
    panel.addSpacer(2, 0, 1, 1, 100, 0);
    panel.addEmptyCellAt(3, 0, 24);
    panel.addEmptyCellAt(4, 3);

    return panel;
  }

  private
  ElementComboBoxChooser
  getAccountChooser()
  {
    return itsAccountChooser;
  }

  private
  TransactionFilter
  getFilter()
  {
    return itsFilter;
  }

  private
  Panel
  getPanel()
  {
    return itsPanel;
  }

  private
  Account
  getProperAccountReference()
  {
    Account account = getAccount();

    if(account != null)
    {
      account = (Account)getAccounts().get(getAccount().getIdentifier());

      // Non-active accounts cannot be referenced.
      if(account != null && account.isActive() == false)
      {
        account = null;
      }
    }

    return account;
  }

  private
  static
  String
  getProperty(String key)
  {
    return I18NHelper.getProperty("RegisterView." + key);
  }

  private
  RegisterPanel
  getRegisterPanel()
  {
    return itsRegisterPanel;
  }

  private
  SearchWidget
  getSearchWidget()
  {
    return itsSearchWidget;
  }

  private
  void
  setAccountChooser(ElementComboBoxChooser chooser)
  {
    itsAccountChooser = chooser;
  }

  private
  void
  setAllowEvents(boolean value)
  {
    itsAllowEvents = value;
  }

  private
  void
  setFilter(TransactionFilter filter)
  {
    itsFilter = filter;
  }

  private
  void
  setPanel(Panel panel)
  {
    itsPanel = panel;
  }

  private
  void
  setRegisterPanel(RegisterPanel panel)
  {
    itsRegisterPanel = panel;
  }

  private
  void
  setSearchWidget(SearchWidget searchWidget)
  {
    itsSearchWidget = searchWidget;
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

      if(source == getAccountChooser() && allowEvents() == true)
      {
        String id = getAccountChooser().getSelectedItem();
        Account account = (Account)getAccounts().get(id);

        if(account != getAccount())
        {
          // Show the account's register.
          getFrame().getViews().openRegisterFor(account);
        }
      }
      else if(source != getAccountChooser())
      {
        if(source == getSearchWidget())
        {
          String text = getSearchWidget().getSearchText();

          if(text.length() == 0)
          {
            text = null;
          }

          getFilter().setFilterText(text);
        }
        else
        {
          String command = event.getActionCommand();

          if(command.equals(ACTION_ALL) == true)
          {
            getFilter().setFilterField(ALL);
          }
          else if(command.equals(ACTION_AMOUNT) == true)
          {
            getFilter().setFilterField(AMOUNT);
          }
          else if(command.equals(ACTION_CHECK) == true)
          {
            getFilter().setFilterField(CHECK);
          }
          else if(command.equals(ACTION_DATE) == true)
          {
            getFilter().setFilterField(DATE);
          }
          else if(command.equals(ACTION_NOTES) == true)
          {
            getFilter().setFilterField(NOTES);
          }
          else
          {
            getFilter().setFilterField(PAYEE);
          }

          // The popup takes the focus away from the search widget.
          getSearchWidget().requestFocus();
        }

        displayTransactions(null);
      }
    }
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  private Account itsAccount;
  private ElementComboBoxChooser itsAccountChooser;
  private boolean itsAllowEvents;
  private TransactionFilter itsFilter;
  private Panel itsPanel;
  private RegisterPanel itsRegisterPanel;
  private SearchWidget itsSearchWidget;

  private static final String ACTION_ALL = getSharedProperty("all");
  private static final String ACTION_AMOUNT = getSharedProperty("amount");
  private static final String ACTION_CHECK = getSharedProperty("check_number");
  private static final String ACTION_DATE = getSharedProperty("date");
  private static final String ACTION_NOTES = getSharedProperty("notes");
  private static final String ACTION_PAYEE = getSharedProperty("payee");
}
