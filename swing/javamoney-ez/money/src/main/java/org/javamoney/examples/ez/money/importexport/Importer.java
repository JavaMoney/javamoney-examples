// Importer

package org.javamoney.examples.ez.money.importexport;

import static org.javamoney.examples.ez.money.ApplicationProperties.setLastSelectedDirectory;
import static org.javamoney.examples.ez.money.ApplicationThread.getFrame;
import static org.javamoney.examples.ez.money.importexport.ImportExportFileChooser.ModeKeys.OPEN;
import static org.javamoney.examples.ez.money.importexport.ImportExportTypeKeys.IMPORT;
import static org.javamoney.examples.ez.money.model.DataManager.getAccounts;
import static org.javamoney.examples.ez.money.utility.DialogHelper.error;
import static org.javamoney.examples.ez.money.utility.DialogHelper.inform;
import static org.javamoney.examples.ez.money.utility.TransactionHelper.createCorrespondingTransfer;
import static org.javamoney.examples.ez.money.utility.TransactionHelper.isExpense;

import java.io.File;

import org.javamoney.examples.ez.money.KeywordKeys;
import org.javamoney.examples.ez.money.exception.DialogCanceledException;
import org.javamoney.examples.ez.money.gui.dialog.ImportExportAccountDialog;
import org.javamoney.examples.ez.money.model.DataManager;
import org.javamoney.examples.ez.money.model.dynamic.transaction.ImportTransaction;
import org.javamoney.examples.ez.money.model.persisted.account.Account;
import org.javamoney.examples.ez.money.model.persisted.account.AccountTypeKeys;
import org.javamoney.examples.ez.money.model.persisted.transaction.Transaction;

import org.javamoney.examples.ez.common.utility.I18NHelper;

/**
 * This class facilitates importing transactions from a file that the user
 * selects from a dialog.
 */
abstract
class
Importer
{
  /**
   * This method presents the user with a file dialog for choosing files to
   * import, and then imports the transactions from the selected files.
   *
   * @param format The format the file is in.
   * @param ext The file types to display.
   * @param desc The description of the file types.
   */
  protected
  final
  void
  doImport(ImportExportFormatKeys format, String ext, String desc)
  {
    ImportExportFileChooser chooser = new ImportExportFileChooser(OPEN, IMPORT, format);

    chooser.setFileFilter(new ImportExportFileFilter(ext, desc));
    chooser.setMultiSelectionEnabled(true);

    if(chooser.showDialog(getProperty("title")) == true)
    {
      // Write data incase an error occurs.
      DataManager.write();

      try
      {
        File[] files = chooser.getSelectedFiles();

        setLastSelectedDirectory(files[0].getParentFile());

        for(File file : files)
        {
          importTransactions(file);
        }

        inform(getProperty("success.title"), getProperty("success.description"));
      }
      catch(DialogCanceledException ignored)
      {
        // Restore data.
        DataManager.read();
      }
      catch(Exception exception)
      {
        // Restore data.
        DataManager.read();

        error(getProperty("failure.title"),
            getProperty("failure.description") + "<br>" + exception.getLocalizedMessage());
      }

      getFrame().signalDataChange();
    }
  }

  /**
   * This method imports the transactions from the specified file and adds them
   * to the account specified in the file. If an account is not specified in the
   * file, a dialog will appear to allow the user to select one from a list of
   * available accounts.
   *
   * @param file The file to import transactions from.
   */
  protected
  abstract
  void
  importTransactions(File file)
  throws Exception;

  /**
   * This method adds the specified transaction to the specified account.
   *
   * @param account The account to add the transaction to.
   * @param trans The transaction to add.
   *
   * @throws Exception If an exception occurs.
   */
  protected
  final
  static
  void
  addTransaction(Account account, ImportTransaction trans)
  throws Exception
  {
    if(trans.isTransfer() == true)
    {
      addTransfer(account, trans.getTransaction());
    }
    else
    {
      account.addTransaction(trans.getTransaction());
    }
  }

  /**
   * This method returns the account to import the transactions into.
   *
   * @return The account to import the transactions into.
   *
   * @throws DialogCanceledException If the dialog was canceled.
   */
  protected
  final
  static
  Account
  chooseAccount()
  throws DialogCanceledException
  {
    Account[] accounts = new ImportExportAccountDialog(IMPORT).showDialog();
    Account choice = null;

    if(accounts == null)
    {
      throw new DialogCanceledException();
    }
    else if(accounts.length != 0)
    {
      choice = accounts[0];
    }

    return choice;
  }

  /**
   * This method returns the account to import the transactions into. This
   * method first checks to see if the account, specified by the identifier,
   * already exists. If not, a new account is created with the specified type.
   * If the account does exist, this method ensures that the account is of the
   * correct type. An account can get an incorrect type if it was created from a
   * previous import where it was the transfer account referenced in a
   * transaction.
   *
   * @param identifier The account's identifier.
   * @param type The account's type.
   *
   * @return The account to import the transactions into.
   */
  protected
  final
  static
  Account
  getAccountForImport(String identifier, AccountTypeKeys type)
  {
    Account account = (Account)getAccounts().get(identifier);

    if(account == null)
    {
      getAccounts().add(new Account(type, identifier));
    }
    else if(account.getType() != type)
    {
      Account newAccount = new Account(type, account.getIdentifier(), account.getBalance());

      // Copy the transactions.
      newAccount.addAll(account);

      getAccounts().remove(account);
      getAccounts().add(newAccount);
    }

    // Get proper account reference.
    return (Account)getAccounts().get(identifier);
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of private methods.
  //////////////////////////////////////////////////////////////////////////////

  private
  static
  void
  addTransfer(Account account, Transaction trans)
  throws Exception
  {
    Account recipient = (Account)getAccounts().get(trans.getPayee());

    if(account != recipient)
    {
      if(isExpense(trans) == true)
      {
        trans.setCategory(KeywordKeys.TRANSFER_TO.toString());
        account.addTransaction(trans);
        recipient.addTransaction(createCorrespondingTransfer(trans, account));
      }
      else
      {
        trans.setCategory(KeywordKeys.TRANSFER_FROM.toString());
        account.addTransaction(trans);
        recipient.addTransaction(createCorrespondingTransfer(trans, account));
      }
    }
  }

  private
  static
  String
  getProperty(String key)
  {
    return I18NHelper.getProperty("Importer." + key);
  }
}
