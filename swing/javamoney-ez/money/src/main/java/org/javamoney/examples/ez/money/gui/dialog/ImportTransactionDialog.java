// ImportTransactionDialog

package org.javamoney.examples.ez.money.gui.dialog;

import static org.javamoney.examples.ez.common.utility.ButtonHelper.buildButton;
import static org.javamoney.examples.ez.common.utility.I18NHelper.getSharedProperty;
import static org.javamoney.examples.ez.money.utility.DialogHelper.inform;
import static org.javamoney.examples.ez.money.utility.TransactionDateHelper.isInRange;
import static org.javamoney.examples.ez.money.utility.TransactionHelper.getCorrespondingTransferReference;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collection;

import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.javamoney.examples.ez.money.IconKeys;
import org.javamoney.examples.ez.money.gui.GUIConstants;
import org.javamoney.examples.ez.money.gui.table.ImportDuplicateTable;
import org.javamoney.examples.ez.money.gui.table.ImportTransactionTable;
import org.javamoney.examples.ez.money.model.dynamic.transaction.ImportTransaction;
import org.javamoney.examples.ez.money.model.persisted.account.Account;
import org.javamoney.examples.ez.money.model.persisted.transaction.Transaction;

import org.javamoney.examples.ez.common.gui.DialogHeader;
import org.javamoney.examples.ez.common.gui.Panel;
import org.javamoney.examples.ez.common.gui.PopupMenu;
import org.javamoney.examples.ez.common.gui.ScrollPane;
import org.javamoney.examples.ez.common.utility.I18NHelper;

/**
 * This class provides a dialog for choosing transactions to import.
 */
public
final
class
ImportTransactionDialog
extends ApplicationDialog
{
  /**
   * Constructs a new dialog for choosing transactions to import.
   *
   * @param account The account to import the transactions into.
   * @param collection The transactions to import.
   */
  public
  ImportTransactionDialog(Account account, Collection<ImportTransaction> collection)
  {
    super(800, 600);

    setAccount(account);
    setDuplicates(new ImportDuplicateTable());
    setTable(new ImportTransactionTable());
    setTransactions(collection);

    buildPanel();

    // Add listeners.
    getDuplicates().addMouseListener(createPopupMenuForDuplicates());
    getDuplicates().addMouseListener(new MouseHandler());
    getTable().addMouseListener(createPopupMenu());
    getTable().getSelectionModel().addListSelectionListener(new SelectionHandler());
  }

  /**
   * This method returns the collection of transactions to import or null if the
   * dialog was canceled.
   *
   * @return The collection of transactions to import or null if the dialog was
   * canceled.
   */
  public
  Collection<ImportTransaction>
  showDialog()
  {
    ProcessWorker processWorker = new ProcessWorker();
    Collection<ImportTransaction> transactions = getTransactions();

    if(transactions.size() != 0)
    {
      // Compute possible duplicates as a process.
      processWorker.showDialog();

      if(processWorker.wasAccepted() == true)
      {
        // Display all the available transactions.
        getTable().display();

        if(getTable().getRowCount() != 0)
        {
          getTable().selectRow(0);
        }

        runDialog();
      }

      if(wasAccepted() != true || transactions.size() == 0)
      {
        transactions = null;
      }
    }
    else
    {
      inform(getProperty("no_transactions.title"),
          getProperty("no_transactions.description"));

      transactions = null;
    }

    return transactions;
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
    panel.add(createDialogHeader(), 0, 0, 1, 1, 100, 0);
    panel.add(createTablePanel(), 0, 1, 1, 1, 0, 50);
    panel.addEmptyCellAt(0, 2);
    panel.add(createDuplicatesPanel(), 0, 3, 1, 1, 0, 50);
    panel.add(createOKCancelButtonPanel(new ActionHandler()), 0, 4, 1, 1, 0, 0);
  }

  private
  static
  DialogHeader
  createDialogHeader()
  {
    String description = getProperty("header.description");
    String title = getProperty("header.title");

    return new DialogHeader(title, description, IconKeys.DIALOG_IMPORT.getIcon());
  }

  private
  Panel
  createDuplicatesPanel()
  {
    Panel panel = new Panel();
    ScrollPane scrollPane = new ScrollPane(getDuplicates());
    JTabbedPane tabs = new JTabbedPane();

    // Build scroll pane.
    scrollPane.setBackground(GUIConstants.COLOR_BACKGROUND_FILL);

    // Build tab.
    tabs.addTab(getProperty("duplicates"), scrollPane);

    // Build panel.
    panel.setFill(GridBagConstraints.BOTH);
    panel.add(tabs, 0, 0, 1, 1, 100, 100);

    panel.setInsets(new Insets(5, 5, 0, 5));

    return panel;
  }

  private
  Panel
  createImportPanel()
  {
    Panel panel = new Panel();
    String account = "<html><b>" + getAccount().getIdentifier() + "</b></html>";

    // Build panel.
    panel.setAnchor(GridBagConstraints.EAST);
    panel.addEmptyCellAt(0, 0);
    panel.add(getProperty("account") + ": ", 0, 1, 1, 1, 0, 100);

    panel.setAnchor(GridBagConstraints.WEST);
    panel.add(account, 1, 1, 1, 1, 100, 0);
    panel.addEmptyCellAt(0, 2);

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
  PopupMenu
  createPopupMenu()
  {
    PopupMenu menu = new PopupMenu();
    ActionHandler handler = new ActionHandler();

    // Add items to menu.
    menu.add(createMenuItem(ACTION_SELECT_ALL, handler));
    menu.addSeparator();
    menu.add(createMenuItem(ACTION_SELECT_NON_DUPLICATES, handler));

    return menu;
  }

  private
  PopupMenu
  createPopupMenuForDuplicates()
  {
    PopupMenu menu = new PopupMenu();
    ActionHandler handler = new ActionHandler();

    // Add items to menu.
    menu.add(createMenuItem(ACTION_RECONCILED, handler));
    menu.add(createMenuItem(ACTION_PENDING, handler));

    return menu;
  }

  private
  Panel
  createTablePanel()
  {
    Panel panel = new Panel();
    ScrollPane scrollPane = new ScrollPane(getTable());

    // Build scroll pane.
    scrollPane.setBackground(GUIConstants.COLOR_BACKGROUND_FILL);

    // Build panel.
    panel.setFill(GridBagConstraints.BOTH);
    panel.add(createImportPanel(), 0, 0, 1, 1, 100, 0);
    panel.add(scrollPane, 0, 1, 1, 1, 0, 100);

    panel.setInsets(new Insets(5, 5, 0, 5));

    return panel;
  }

  private
  Account
  getAccount()
  {
    return itsAccount;
  }

  private
  ImportDuplicateTable
  getDuplicates()
  {
    return itsDuplicates;
  }

  private
  static
  String
  getProperty(String key)
  {
    return I18NHelper.getProperty("ImportTransactionDialog." + key);
  }

  private
  ImportTransactionTable
  getTable()
  {
    return itsTable;
  }

  private
  Collection<ImportTransaction>
  getTransactions()
  {
    return itsTransactions;
  }

  private
  static
  boolean
  hasPossibleDuplicate(Transaction trans, Transaction iTrans)
  {
    boolean result = false;

    // A possible duplicate is one that occurred within 7 days (+/-) and has the
    // same amount.
    if(isInRange(iTrans, trans.getDate(), 7) == true)
    {
      if(trans.getAmount() == iTrans.getAmount())
      {
        result = true;
      }
    }

    return result;
  }

  private
  void
  selectAll()
  {
    for(ImportTransaction trans : getTable().getList())
    {
      trans.setIsSelected(true);
    }

    getTable().repaint();
  }

  private
  void
  selectNonDuplicates()
  {
    for(ImportTransaction trans : getTable().getList())
    {
      trans.setIsSelected(trans.hasDuplicates() == false);
    }

    getTable().repaint();
  }

  private
  void
  setAccount(Account account)
  {
    itsAccount = account;
  }

  private
  void
  setDuplicates(ImportDuplicateTable table)
  {
    itsDuplicates = table;
  }

  private
  void
  setTable(ImportTransactionTable table)
  {
    itsTable = table;
  }

  private
  void
  setReconciled(boolean reconciled)
  {
    for(int row : getDuplicates().getSelectedRows())
    {
      Transaction trans = getDuplicates().get(row);
      Transaction transfer = getCorrespondingTransferReference(trans, getAccount());

      trans.setIsReconciled(reconciled);

      // Apply to transfer if applicable.
      if(transfer != null)
      {
        transfer.setIsReconciled(reconciled);
      }
    }

    getDuplicates().repaint();
  }

  private
  void
  setTransactions(Collection<ImportTransaction> collection)
  {
    itsTransactions = collection;
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

      if(command.equals(ACTION_PENDING) == true)
      {
        setReconciled(false);
      }
      else if(command.equals(ACTION_RECONCILED) == true)
      {
        setReconciled(true);
      }
      else if(command.equals(ACTION_SELECT_ALL) == true)
      {
        selectAll();
      }
      else if(command.equals(ACTION_SELECT_NON_DUPLICATES) == true)
      {
        selectNonDuplicates();
      }
      else
      {
        setAccepted(command.equals(ACTION_OK));
        dispose();
      }
    }
  }

  private
  class
  ProcessWorker
  extends ProcessDialog
  {
    protected
    ProcessWorker()
    {
      super(ImportTransactionDialog.getProperty("processing"),
          getTransactions().size());
    }

    @Override
    protected
    void
    doProcess()
    {
      for(ImportTransaction iTrans : getTransactions())
      {
        // Put the transactions into the table.
        getTable().add(iTrans);

        for(Transaction trans : getAccount().getTransactions())
        {
          if(hasPossibleDuplicate(trans, iTrans.getTransaction()) == true)
          {
            iTrans.getDuplicates().add(trans);
          }

          // Check to see if the user canceled the process.
          if(canProcess() == false)
          {
            break;
          }
        }

        iTrans.setIsSelected(iTrans.hasDuplicates() == false);

        setValue(getValue() + 1);

        // Check to see if the user canceled the process.
        if(canProcess() == false)
        {
          break;
        }
      }

      signalProcessIsDone();
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
        Transaction trans = getDuplicates().getSelectedElement();

        if(trans != null)
        {
          int column = getDuplicates().getIndexForColumn(getTable().getColumnIndexAtX(event.getX()));

          if(column == ImportDuplicateTable.RECONCILED_COLUMN)
          {
            setReconciled(!trans.isReconciled());
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
      if(event.getValueIsAdjusting() == false && getTable().getSelectedRow() != -1)
      {
        ImportTransaction iTrans = getTable().getSelectedElement();

        getDuplicates().clear();

        for(Transaction trans : iTrans.getDuplicates())
        {
          getDuplicates().add(trans);
        }

        getDuplicates().display();
      }
    }
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  private Account itsAccount;
  private ImportDuplicateTable itsDuplicates;
  private ImportTransactionTable itsTable;
  private Collection<ImportTransaction> itsTransactions;

  private static final String ACTION_PENDING = getSharedProperty("mark_pending");
  private static final String ACTION_RECONCILED = getSharedProperty("mark_reconciled");
  private static final String ACTION_SELECT_ALL = getProperty("select_all");
  private static final String ACTION_SELECT_NON_DUPLICATES = getProperty("select_non_duplicates");
}
