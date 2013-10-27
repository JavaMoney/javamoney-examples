// QIFExporter

package org.javamoney.examples.ez.money.importexport;

import static org.javamoney.examples.ez.money.ApplicationProperties.getImportExportCurrencyFormat;
import static org.javamoney.examples.ez.money.ApplicationProperties.getImportExportDateFormat;
import static org.javamoney.examples.ez.money.importexport.ImportExportFormatKeys.QIF;
import static org.javamoney.examples.ez.money.importexport.QIFConstants.ACCOUNT_BALANCE;
import static org.javamoney.examples.ez.money.importexport.QIFConstants.ACCOUNT_DESCRIPTION;
import static org.javamoney.examples.ez.money.importexport.QIFConstants.ACCOUNT_HEADER;
import static org.javamoney.examples.ez.money.importexport.QIFConstants.ACCOUNT_NAME;
import static org.javamoney.examples.ez.money.importexport.QIFConstants.ACCOUNT_STATEMENT_DATE;
import static org.javamoney.examples.ez.money.importexport.QIFConstants.ACCOUNT_TYPE;
import static org.javamoney.examples.ez.money.importexport.QIFConstants.ACCOUNT_TYPE_CASH;
import static org.javamoney.examples.ez.money.importexport.QIFConstants.ACCOUNT_TYPE_CREDIT;
import static org.javamoney.examples.ez.money.importexport.QIFConstants.ACCOUNT_TYPE_DEPOSIT;
import static org.javamoney.examples.ez.money.importexport.QIFConstants.AMOUNT;
import static org.javamoney.examples.ez.money.importexport.QIFConstants.AMOUNT_IN_SPLIT;
import static org.javamoney.examples.ez.money.importexport.QIFConstants.CATEGORY;
import static org.javamoney.examples.ez.money.importexport.QIFConstants.CATEGORY_IN_SPLIT;
import static org.javamoney.examples.ez.money.importexport.QIFConstants.CHECK_NUMBER;
import static org.javamoney.examples.ez.money.importexport.QIFConstants.CLEARED;
import static org.javamoney.examples.ez.money.importexport.QIFConstants.CLEARED_STATUS;
import static org.javamoney.examples.ez.money.importexport.QIFConstants.DATE;
import static org.javamoney.examples.ez.money.importexport.QIFConstants.END_OF_ENTRY;
import static org.javamoney.examples.ez.money.importexport.QIFConstants.FILE_DESCRIPTION;
import static org.javamoney.examples.ez.money.importexport.QIFConstants.FILE_EXTENSION;
import static org.javamoney.examples.ez.money.importexport.QIFConstants.NEW_TYPE;
import static org.javamoney.examples.ez.money.importexport.QIFConstants.NOTES;
import static org.javamoney.examples.ez.money.importexport.QIFConstants.PAYEE;
import static org.javamoney.examples.ez.money.importexport.QIFConstants.TRANSACTION_TYPE_CASH;
import static org.javamoney.examples.ez.money.importexport.QIFConstants.TRANSACTION_TYPE_CREDIT;
import static org.javamoney.examples.ez.money.importexport.QIFConstants.TRANSACTION_TYPE_DEPOSIT;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;

import org.javamoney.examples.ez.money.locale.Currency;
import org.javamoney.examples.ez.money.model.dynamic.transaction.Split;
import org.javamoney.examples.ez.money.model.persisted.account.Account;
import org.javamoney.examples.ez.money.model.persisted.account.AccountTypeKeys;
import org.javamoney.examples.ez.money.model.persisted.transaction.Transaction;
import org.javamoney.examples.ez.money.utility.TransactionHelper;

import org.javamoney.examples.ez.common.utility.I18NHelper;

/**
 * This class facilitates exporting account data in the QIF format.
 */
public
final
class
QIFExporter
extends Exporter
{
  /**
   * This method presents the user with a dialog for choosing the accounts to
   * export and then a dialog for choosing the files to export them to.
   */
  public
  void
  doExport()
  {
    doExport(QIF, FILE_EXTENSION, FILE_DESCRIPTION);
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of protected methods.
  //////////////////////////////////////////////////////////////////////////////

  /**
   * This method exports the specified account's data to the specified file.
   * This method returns true if the operation was successful, otherwise false.
   *
   * @param account The account to export.
   * @param file The file to export to.
   *
   * @return true or false.
   */
  @Override
  protected
  boolean
  exportAccount(Account account, File file)
  {
    boolean result = false;

    try
    {
      PrintStream stream = new PrintStream(new FileOutputStream(file));
      Currency currency = getImportExportCurrencyFormat().getCurrency();
      ImportExportDateFormatKeys dateFormat = getImportExportDateFormat();

      // Print the account's information.
      stream.println(NEW_TYPE + ACCOUNT_HEADER);
      stream.println(ACCOUNT_NAME + account.getIdentifier());
      stream.println(ACCOUNT_TYPE + getAccountType(account));
      stream.println(ACCOUNT_DESCRIPTION + getProperty("description"));
      stream.println(ACCOUNT_STATEMENT_DATE);
      stream.println(ACCOUNT_BALANCE + currency.format(account.getBalance(), false));
      stream.println(END_OF_ENTRY);

      // Print the account's transactions.
      stream.println(NEW_TYPE + getTransactionType(account));

      for(Transaction trans : account.getTransactions())
      {
        stream.println(DATE + dateFormat.format(trans.getDate()));
        stream.println(NOTES + trans.getNotes());
        stream.println(AMOUNT + currency.format(trans.getAmount(), false));
        stream.println(CHECK_NUMBER + trans.getCheckNumber());
        stream.println(PAYEE + trans.getPayee());

        if(TransactionHelper.isTransfer(trans) == true)
        {
          stream.println(CATEGORY + "[" + trans.getPayee() + "]");
        }
        else if(TransactionHelper.isSplit(trans) == true)
        {
          printSplit(stream, trans, currency);
        }
        else
        {
          stream.println(CATEGORY + trans.getCategory());
        }

        if(trans.isReconciled() == true)
        {
          stream.println(CLEARED_STATUS + CLEARED);
        }

        stream.println(END_OF_ENTRY);
      }

      stream.close();
      result = true;
    }
    catch(Exception exception)
    {
      // Ignored.
    }

    return result;
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of private methods.
  //////////////////////////////////////////////////////////////////////////////

  private
  static
  String
  getAccountType(Account account)
  {
    String type = null;

    if(account.getType() == AccountTypeKeys.CASH)
    {
      type = ACCOUNT_TYPE_CASH;
    }
    else if(account.getType() == AccountTypeKeys.CREDIT)
    {
      type = ACCOUNT_TYPE_CREDIT;
    }
    else
    {
      type = ACCOUNT_TYPE_DEPOSIT;
    }

    return type;
  }

  private
  static
  String
  getProperty(String key)
  {
    return I18NHelper.getProperty("QIFExporter." + key);
  }

  private
  static
  String
  getTransactionType(Account account)
  {
    String type = null;

    if(account.getType() == AccountTypeKeys.CASH)
    {
      type = TRANSACTION_TYPE_CASH;
    }
    else if(account.getType() == AccountTypeKeys.CREDIT)
    {
      type = TRANSACTION_TYPE_CREDIT;
    }
    else
    {
      type = TRANSACTION_TYPE_DEPOSIT;
    }

    return type;
  }

  private
  static
  void
  printSplit(PrintStream stream, Transaction trans, Currency currency)
  {
    Split split = new Split(trans);

    // Use the first category in the split as the main category.
    stream.println(CATEGORY + split.getCategory(0));

    for(int index = 0; index < split.size(); ++index)
    {
      stream.println(CATEGORY_IN_SPLIT + split.getCategory(index));
      stream.println(AMOUNT_IN_SPLIT + currency.format(split.getAmount(index), false));
    }
  }
}
