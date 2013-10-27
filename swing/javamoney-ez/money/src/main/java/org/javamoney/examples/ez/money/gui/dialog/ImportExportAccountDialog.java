// ImportExportAccountDialog

package org.javamoney.examples.ez.money.gui.dialog;

import static org.javamoney.examples.ez.money.model.DataManager.getAccounts;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.Icon;
import javax.swing.ListSelectionModel;

import org.javamoney.examples.ez.money.IconKeys;
import org.javamoney.examples.ez.money.gui.GUIConstants;
import org.javamoney.examples.ez.money.gui.table.AccountChooserTable;
import org.javamoney.examples.ez.money.importexport.ImportExportTypeKeys;
import org.javamoney.examples.ez.money.model.DataElement;
import org.javamoney.examples.ez.money.model.persisted.account.Account;

import org.javamoney.examples.ez.common.gui.DialogHeader;
import org.javamoney.examples.ez.common.gui.Panel;
import org.javamoney.examples.ez.common.gui.ScrollPane;
import org.javamoney.examples.ez.common.utility.I18NHelper;

/**
 * This class provides a dialog for choosing an account for either exporting or
 * importing.
 */
public
final
class
ImportExportAccountDialog
extends ApplicationDialog
{
  /**
   * Constructs a new dialog tailored for the specified type.
   *
   * @param type The type of dialog to display.
   */
  public
  ImportExportAccountDialog(ImportExportTypeKeys type)
  {
    super(625, 450);

    setTable(new AccountChooserTable());

    // Build panel.
    getContentPane().setFill(GridBagConstraints.BOTH);
    getContentPane().add(createDialogHeader(type), 0, 0, 1, 1, 100, 0);
    getContentPane().add(createAccountTablePanel(), 0, 1, 1, 1, 0, 100);
    getContentPane().add(createOKCancelButtonPanel(new ActionHandler()), 0, 2, 1, 1, 0, 0);

    displayAccounts();

    // Determine selection mode.
    if(type == ImportExportTypeKeys.EXPORT)
    {
      getTable().setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    }
    else
    {
      getTable().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    // Add listeners.
    getTable().addMouseListener(new MouseHandler());
  }

  /**
   * This method returns an array of accounts, or null if nothing was selected
   * or the dialog was canceled.
   *
   * @return An array of accounts, or null if nothing was selected or the dialog
   * was canceled.
   */
  public
  Account[]
  showDialog()
  {
    Account[] accounts = null;

    if(getAccounts().getCollection().size() == 0)
    {
      getOKButton().setEnabled(false);
    }

    runDialog();

    if(wasAccepted() == true)
    {
      List<Account> values = getTable().getSelectedElements();
      int len = 0;

      // Convert to an array of Accounts.
      accounts = new Account[values.size()];

      for(Account account : values)
      {
        accounts[len++] = account;
      }
    }

    return accounts;
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of private methods.
  //////////////////////////////////////////////////////////////////////////////

  private
  Panel
  createAccountTablePanel()
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
  DialogHeader
  createDialogHeader(ImportExportTypeKeys type)
  {
    Icon icon = null;
    String description = null;
    String title = null;

    if(type == ImportExportTypeKeys.EXPORT)
    {
      title = getProperty("export.title");
      icon = IconKeys.DIALOG_EXPORT.getIcon();
      description = getProperty("export.description");
    }
    else
    {
      title = getProperty("import.title");
      icon = IconKeys.DIALOG_IMPORT.getIcon();
      description = getProperty("import.description");
    }

    return new DialogHeader(title, description, icon);
  }

  private
  void
  displayAccounts()
  {
    // Add all the accounts.
    for(DataElement element : getAccounts().getCollection())
    {
      getTable().add((Account)element);
    }

    getTable().display();
  }

  private
  static
  String
  getProperty(String key)
  {
    return I18NHelper.getProperty("ImportExportAccountDialog." + key);
  }

  private
  AccountChooserTable
  getTable()
  {
    return itsTable;
  }

  private
  void
  setTable(AccountChooserTable table)
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
      setAccepted(event.getActionCommand().equals(ACTION_OK));
      dispose();
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
      // Equate left double-clicks on an account as accepting the dialog.
      if(event.getClickCount() == 2 && event.getButton() == MouseEvent.BUTTON1)
      {
        setAccepted(true);
        dispose();
      }
    }
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  private AccountChooserTable itsTable;
}
