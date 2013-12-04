// RegisterPanel

package org.javamoney.examples.ez.money.gui.view.register;

import static org.javamoney.examples.ez.common.utility.ButtonHelper.buildButton;
import static org.javamoney.examples.ez.common.utility.I18NHelper.getSharedProperty;
import static java.awt.event.KeyEvent.VK_BACK_SPACE;
import static java.awt.event.KeyEvent.VK_DELETE;
import static java.awt.event.KeyEvent.VK_R;
import static org.javamoney.examples.ez.money.ApplicationProperties.creditBalanceIsPositive;
import static org.javamoney.examples.ez.money.ApplicationProperties.getRegisterColumnToSort;
import static org.javamoney.examples.ez.money.ApplicationProperties.viewByMonth;
import static org.javamoney.examples.ez.money.gui.table.RegisterTable.LABEL_COLUMN;
import static org.javamoney.examples.ez.money.gui.table.RegisterTable.RECONCILED_COLUMN;
import static org.javamoney.examples.ez.money.model.dynamic.transaction.RegisterTransaction.setMultiplier;
import static org.javamoney.examples.ez.money.model.dynamic.transaction.RegisterTransaction.setStartingBalance;
import static org.javamoney.examples.ez.money.model.persisted.account.AccountTypeKeys.CREDIT;
import static org.javamoney.examples.ez.money.utility.DialogHelper.buildMessage;
import static org.javamoney.examples.ez.money.utility.DialogHelper.choose;
import static org.javamoney.examples.ez.money.utility.DialogHelper.decide;
import static org.javamoney.examples.ez.money.utility.DialogHelper.inform;
import static org.javamoney.examples.ez.money.utility.TransactionDateHelper.isInRange;
import static org.javamoney.examples.ez.money.utility.TransactionHelper.getCorrespondingTransferReference;
import static org.javamoney.examples.ez.money.utility.TransactionHelper.removeFrom;

import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.javamoney.examples.ez.money.IconKeys;
import org.javamoney.examples.ez.money.gui.GUIConstants;
import org.javamoney.examples.ez.money.gui.chooser.LabelChooser;
import org.javamoney.examples.ez.money.gui.chooser.MonthPeriodChooser;
import org.javamoney.examples.ez.money.gui.dialog.AccountStatementDialog;
import org.javamoney.examples.ez.money.gui.dialog.EditTransactionsDialog;
import org.javamoney.examples.ez.money.gui.table.RegisterTable;
import org.javamoney.examples.ez.money.model.dynamic.transaction.RegisterTransaction;
import org.javamoney.examples.ez.money.model.dynamic.transaction.TransactionFilter;
import org.javamoney.examples.ez.money.model.persisted.account.Account;
import org.javamoney.examples.ez.money.model.persisted.transaction.LabelKeys;
import org.javamoney.examples.ez.money.model.persisted.transaction.Transaction;
import org.javamoney.examples.ez.money.report.TransactionWriter;

import org.javamoney.examples.ez.common.gui.Panel;
import org.javamoney.examples.ez.common.gui.PopupMenu;
import org.javamoney.examples.ez.common.gui.ScrollPane;
import org.javamoney.examples.ez.common.utility.I18NHelper;

/**
 * This class facilitates displaying and interacting with an account's
 * transactions.
 */
public
final
class
RegisterPanel
extends Panel
{
  /**
	 * 
	 */
	private static final long serialVersionUID = 6315609604386251385L;
/**
   * Constructs a new register panel that will reference the specified account.
   *
   * @param account The account to reference.
   */
  public
  RegisterPanel(Account account)
  {
    setAccount(account);
    setMonthPeriodChooser(new MonthPeriodChooser());
    setRegisterBalancePanel(new RegisterBalancePanel());
    setTable(new RegisterTable());
    setScrollPane(new ScrollPane(getTable()));
    setTypeTabs(new TypeTabs(account.getType()));

    buildPanel();

    // Add listeners.
    getMonthPeriodChooser().addActionListener(new ActionHandler());
    getTable().addKeyListener(new KeyHandler());
    getTable().addMouseListener(createPopupMenu());
    getTable().addMouseListener(new MouseHandler());
    getTable().getSelectionModel().addListSelectionListener(new SelectionHandler());
  }

  /**
   * This method unselects all selected transactions in the register's table.
   */
  public
  void
  clearTableSelection()
  {
    getTable().clearSelection();
  }

  /**
   * This method puts all the account's transactions into the register table and
   * updates its balance.
   *
   * @param filter The filter to determine what transactions can be added.
   */
  public
  void
  updateView(TransactionFilter filter)
  {
    updateView(filter, null);
  }

  /**
   * This method puts all the account's transactions into the register table and
   * updates its balance.
   *
   * @param filter The filter to determine what transactions can be added.
   * @param scrollTo The transaction to scroll to.
   */
  public
  void
  updateView(TransactionFilter filter, Transaction scrollTo)
  {
    double total = 0.0;
    double pending = 0.0;

    setFilter(filter);

    getMonthPeriodChooser().setVisible(viewByMonth());
    getTable().clear();

    for(Transaction trans : getAccount().getTransactions())
    {
      total += trans.getAmount().doubleValue();

      if(trans.isReconciled() == false)
      {
        pending += trans.getAmount().doubleValue();
      }

      if(canAddTransaction(trans) == true)
      {
        getTable().add(new RegisterTransaction(trans, total));
      }
    }

    // Set up the register transactions so that they display the proper balance.
    if(getAccount().getType() == CREDIT && creditBalanceIsPositive() == true)
    {
      setMultiplier(-1);
    }
    else
    {
      setMultiplier(1);
    }

    setStartingBalance(getAccount().getBalance().doubleValue() - total);

    // Display data.
    getRegisterBalancePanel().displayFor(getAccount(), pending);
    getTable().display();
    getTypeTabs().clear();

    // Scroll to the transaction if applicable.
    if(scrollTo != null)
    {
      scrollToRow(scrollTo);
    }
    else
    {
      scrollToRow(getTable().getElementCount());
    }
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of private methods.
  //////////////////////////////////////////////////////////////////////////////

  private
  void
  buildPanel()
  {
    // Build scroll pane.
    getScrollPane().setBackground(GUIConstants.COLOR_BACKGROUND_FILL);

    // Build panel.
    setFill(GridBagConstraints.BOTH);
    add(createFilterPanel(), 0, 0, 1, 1, 0, 0);
    add(getScrollPane(), 0, 1, 1, 1, 100, 100);
    add(createOptionsPanel(), 0, 2, 1, 1, 0, 0);
    add(getRegisterBalancePanel(), 0, 3, 1, 1, 0, 0);
    add(getTypeTabs(), 0, 4, 1, 1, 0, 0);
  }

  private
  boolean
  canAddTransaction(Transaction trans)
  {
    boolean result = true;

    if(getMonthPeriodChooser().isVisible() == true)
    {
      result = isInRange(trans, getMonthPeriodChooser().getStartDate(),
          getMonthPeriodChooser().getEndDate());
    }

    if(result == true)
    {
      result = getFilter().test(trans);
    }

    return result;
  }

  private
  LabelKeys
  chooseLabel()
  {
    LabelChooser chooser = new LabelChooser();
    LabelKeys label = null;

    if(choose(createLabelPanel(chooser)) == true)
    {
      label = chooser.getSelectedLabel();
    }

    return label;
  }

  private
  Panel
  createFilterPanel()
  {
    Panel panel = new Panel();

    // Build panel.
    panel.setAnchor(GridBagConstraints.EAST);
    panel.add(getMonthPeriodChooser(), 0, 0, 1, 1, 100, 100);

    return panel;
  }

  private
  Panel
  createLabelPanel(LabelChooser chooser)
  {
    Panel panel = new Panel();
    String message = buildMessage(getProperty("label.title"),
        getProperty("label.description") + ": ");

    // Build panel.
    panel.setAnchor(GridBagConstraints.WEST);
    panel.add(message, 0, 0, 1, 1, 100, 50);

    panel.setFill(GridBagConstraints.HORIZONTAL);
    panel.add(chooser, 0, 1, 1, 1, 100, 50);

    return panel;
  }

  private
  static
  JMenuItem
  createMenuItem(String command, ActionHandler handler)
  {
    JMenuItem item = new JMenuItem();

    // Build items.
    buildButton(item, command, handler);

    return item;
  }

  private
  Panel
  createOptionsPanel()
  {
    Panel panel = new Panel();
    PopupMenu menu = createPopupMenu();
    JLabel options = new JLabel(getProperty("options"));

    menu.setBehaveLikeMenu(true);

    options.addMouseListener(menu);
    options.setHorizontalTextPosition(SwingConstants.LEFT);
    options.setIcon(IconKeys.MENU_ARROW.getIcon());

    // Build panel.
    panel.setFill(GridBagConstraints.BOTH);
    panel.addSpacer(0, 0, 1, 1, 100, 100);
    panel.add(IconKeys.ACTIONS.getIcon(), 1, 0, 1, 1, 0, 0);
    panel.add(options, 2, 0, 1, 1, 0, 0);
    panel.addEmptyCellAt(3, 0, 15);

    return panel;
  }

  private
  PopupMenu
  createPopupMenu()
  {
    PopupMenu menu = new PopupMenu();
    ActionHandler handler = new ActionHandler();

    // Add items to menu.
    menu.add(createMenuItem(ACTION_RECONCILED, handler));
    menu.add(createMenuItem(ACTION_PENDING, handler));
    menu.addSeparator();
    menu.add(createMenuItem(ACTION_REMOVE, handler));
    menu.addSeparator();
    menu.add(createMenuItem(ACTION_LABEL, handler));
    menu.addSeparator();
    menu.add(createMenuItem(ACTION_MASS_UPDATE, handler));
    menu.addSeparator();
    menu.add(createMenuItem(ACTION_STATEMENT, handler));
    menu.add(createMenuItem(ACTION_PRINT, handler));

    return menu;
  }

  private
  Account
  getAccount()
  {
    return itsAccount;
  }

  private
  TransactionFilter
  getFilter()
  {
    return itsFilter;
  }

  private
  MonthPeriodChooser
  getMonthPeriodChooser()
  {
    return itsMonthPeriodChooser;
  }

  private
  static
  String
  getProperty(String key)
  {
    return I18NHelper.getProperty("RegisterPanel." + key);
  }

  private
  RegisterBalancePanel
  getRegisterBalancePanel()
  {
    return itsRegisterBalancePanel;
  }

  private
  ScrollPane
  getScrollPane()
  {
    return itsScrollPane;
  }

  private
  RegisterTable
  getTable()
  {
    return itsTable;
  }

  private
  TypeTabs
  getTypeTabs()
  {
    return itsTypeTabs;
  }

  private
  void
  print()
  {
    ArrayList<RegisterTransaction> list = getTable().getSelectedElements();
    Transaction[] transactions = new Transaction[list.size()];

    for(int len = 0; len < transactions.length; ++len)
    {
      transactions[len] = list.get(len).getTransaction();
    }

    TransactionWriter.generate(transactions);
  }

  private
  void
  massUpdate()
  {
    ArrayList<RegisterTransaction> list = getTable().getSelectedElements();

    if(list.size() == 1)
    {
      getTypeTabs().doEdit(list.get(0).getTransaction(), true);
    }
    else
    {
      if(new EditTransactionsDialog(list).showDialog() == true)
      {
        updateView(getFilter());
      }
    }
  }

  private
  void
  remove()
  {
    int[] rows = getTable().getSelectedRows();
    String description = null;
    String quantity = null;
    String title = null;

    if(rows.length > 1)
    {
      quantity = getProperty("transactions");
    }
    else
    {
      quantity = getProperty("transaction");
    }

    // Construct the dialog's text.
    title = getSharedProperty("remove") + " " + quantity + "?";
    description = getProperty("remove.description.prefix") + " " + quantity + " " + getProperty("remove.description");

    if(decide(title, description) == true)
    {
      for(RegisterTransaction trans : getTable().getSelectedElements())
      {
        removeFrom(getAccount(), trans.getTransaction());
      }

      updateView(getFilter());
    }
  }

  private
  void
  scrollToRow(int row)
  {
    getScrollPane().scroll(row * getTable().getRowHeight());
  }

  private
  void
  scrollToRow(Transaction scrollTo)
  {
    int index = getTable().getList().size() - 1;

    // Find the last index.
    while(index > 0)
    {
      if(getTable().get(index).getTransaction() == scrollTo)
      {
        break;
      }

      --index;
    }

    getTable().selectRow(index);

    // Scrolling puts the view at the top of the row. If the row is the last
    // element, scroll to the end of the table.
    if(index == getTable().getElementCount() - 1)
    {
      index = getTable().getElementCount();
    }

    scrollToRow(index);
  }

  private
  void
  setAccount(Account account)
  {
    itsAccount = account;
  }

  private
  void
  setFilter(TransactionFilter filter)
  {
    itsFilter = filter;
  }

  private
  void
  setMonthPeriodChooser(MonthPeriodChooser chooser)
  {
    itsMonthPeriodChooser = chooser;
  }

  private
  void
  setLabel()
  {
    LabelKeys label = chooseLabel();

    if(label != null)
    {
      for(int row : getTable().getSelectedRows())
      {
        Transaction trans = getTable().get(row).getTransaction();

        trans.setLabel(label);

        // Apply to transfer if applicable.
        trans = getCorrespondingTransferReference(trans, getAccount());

        if(trans != null)
        {
          trans.setLabel(label);
        }

      }

      // Update the table if the transactions are being sorted by their label.
      if(getRegisterColumnToSort() == LABEL_COLUMN)
      {
        updateView(getFilter());
      }
      else
      {
        getTable().repaint();
      }
    }
  }

  private
  void
  setReconciled(boolean reconciled)
  {
    for(int row : getTable().getSelectedRows())
    {
      Transaction trans = getTable().get(row).getTransaction();
      Transaction transfer = getCorrespondingTransferReference(trans, getAccount());

      trans.setIsReconciled(reconciled);

      // Apply to transfer if applicable.
      if(transfer != null)
      {
        transfer.setIsReconciled(reconciled);
      }
    }

    updateView(getFilter());
  }

  private
  void
  setRegisterBalancePanel(RegisterBalancePanel panel)
  {
    itsRegisterBalancePanel = panel;
  }

  private
  void
  setScrollPane(ScrollPane pane)
  {
    itsScrollPane = pane;
  }

  private
  void
  setTable(RegisterTable table)
  {
    itsTable = table;
  }

  private
  void
  setTypeTabs(TypeTabs tabs)
  {
    itsTypeTabs = tabs;
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
      String command = event.getActionCommand();

      if(source == getMonthPeriodChooser())
      {
        updateView(getFilter());
      }
      else if(command.equals(ACTION_STATEMENT) == true)
      {
        new AccountStatementDialog(getAccount()).showDialog();
      }
      else if(getTable().getSelectedRow() != -1)
      {
        if(command.equals(ACTION_LABEL) == true)
        {
          setLabel();
        }
        else if(command.equals(ACTION_MASS_UPDATE) == true)
        {
          massUpdate();
        }
        else if(command.equals(ACTION_PENDING) == true)
        {
          setReconciled(false);
        }
        else if(command.equals(ACTION_PRINT) == true)
        {
          print();
        }
        else if(command.equals(ACTION_RECONCILED) == true)
        {
          setReconciled(true);
        }
        else if(command.equals(ACTION_REMOVE) == true)
        {
          remove();
        }
      }
      else
      {
        inform(getProperty("select.title"), getProperty("select.description"));
      }
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
      char ch = Character.toUpperCase(event.getKeyChar());

      if(ch == VK_BACK_SPACE || ch == VK_DELETE || ch == VK_R)
      {
        remove();
      }
    }
  }

  private
  class
  MouseHandler
  extends MouseAdapter
  {
    @Override
    public
    void
    mouseClicked(MouseEvent event)
    {
      if(event.getButton() == MouseEvent.BUTTON1 && event.getClickCount() == 2)
      {
        RegisterTransaction trans = getTable().getSelectedElement();

        if(trans != null)
        {
          int column = getTable().getIndexForColumn(getTable().getColumnIndexAtX(event.getX()));

          if(column == LABEL_COLUMN)
          {
            setLabel();
          }
          else if(column == RECONCILED_COLUMN)
          {
            setReconciled(!trans.getTransaction().isReconciled());
          }
        }
      }
    }
  }

  private
  class
  SelectionHandler
  implements ListSelectionListener
  {
    public
    void
    valueChanged(ListSelectionEvent event)
    {
      if(event.getValueIsAdjusting() == false)
      {
        RegisterTransaction trans = getTable().getSelectedElement();

        if(trans != null)
        {
          getTypeTabs().doEdit(trans.getTransaction(), false);
        }
      }
    }
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  private Account itsAccount;
  private TransactionFilter itsFilter;
  private MonthPeriodChooser itsMonthPeriodChooser;
  private RegisterBalancePanel itsRegisterBalancePanel;
  private ScrollPane itsScrollPane;
  private RegisterTable itsTable;
  private TypeTabs itsTypeTabs;

  private static final String ACTION_LABEL = getProperty("option.label");
  private static final String ACTION_MASS_UPDATE = getSharedProperty("edit") + "...";
  private static final String ACTION_PENDING = getSharedProperty("mark_pending");
  private static final String ACTION_PRINT = getSharedProperty("print_transactions");
  private static final String ACTION_RECONCILED = getSharedProperty("mark_reconciled");
  private static final String ACTION_REMOVE = getSharedProperty("remove");
  private static final String ACTION_STATEMENT = getProperty("option.statement");
}
