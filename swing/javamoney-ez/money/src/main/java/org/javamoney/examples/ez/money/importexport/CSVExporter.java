// CSVExporter

package org.javamoney.examples.ez.money.importexport;

import static org.javamoney.examples.ez.common.utility.I18NHelper.getSharedProperty;
import static org.javamoney.examples.ez.money.ApplicationProperties.exportCategoryForCSV;
import static org.javamoney.examples.ez.money.ApplicationProperties.getCSVColumnOrder;
import static org.javamoney.examples.ez.money.ApplicationProperties.getImportExportCurrencyFormat;
import static org.javamoney.examples.ez.money.ApplicationProperties.getImportExportDateFormat;
import static org.javamoney.examples.ez.money.importexport.CSVColumnKeys.AMOUNT;
import static org.javamoney.examples.ez.money.importexport.CSVColumnKeys.CHECK_NUMBER;
import static org.javamoney.examples.ez.money.importexport.CSVColumnKeys.DATE;
import static org.javamoney.examples.ez.money.importexport.CSVColumnKeys.NOTES;
import static org.javamoney.examples.ez.money.importexport.CSVColumnKeys.PAYEE;
import static org.javamoney.examples.ez.money.importexport.CSVConstants.FILE_DESCRIPTION;
import static org.javamoney.examples.ez.money.importexport.CSVConstants.FILE_EXTENSION;
import static org.javamoney.examples.ez.money.importexport.CSVConstants.QUOTE;
import static org.javamoney.examples.ez.money.importexport.CSVConstants.SEPARATOR;
import static org.javamoney.examples.ez.money.importexport.ImportExportFormatKeys.CSV;
import static org.javamoney.examples.ez.money.utility.TransactionHelper.isSplit;
import static org.javamoney.examples.ez.money.utility.TransactionHelper.isTransfer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;

import org.javamoney.examples.ez.money.locale.Currency;
import org.javamoney.examples.ez.money.model.persisted.account.Account;
import org.javamoney.examples.ez.money.model.persisted.transaction.Transaction;

/**
 * This class facilitates exporting account data in the CSV format.
 */
public
final
class
CSVExporter
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
    doExport(CSV, FILE_EXTENSION, FILE_DESCRIPTION);
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
    boolean exportCategory = exportCategoryForCSV();
    boolean result = false;

    try
    {
      PrintStream stream = new PrintStream(new FileOutputStream(file));
      Currency currency = getImportExportCurrencyFormat().getCurrency();
      ImportExportDateFormatKeys dateFormat = getImportExportDateFormat();
      int[] columnOrder = getCSVColumnOrder();

      printColumnHeaders(stream, columnOrder);

      // Print the account's transactions.
      for(Transaction trans : account.getTransactions())
      {
        for(int column = 0; column < columnOrder.length; ++column)
        {
          int ordinal = columnOrder[column]; // Get column mapping.

          if(ordinal == AMOUNT.ordinal())
          {
            printField(stream, currency.format(trans.getAmount(), false));
          }
          else if(ordinal == CHECK_NUMBER.ordinal())
          {
            printField(stream, trans.getCheckNumber());
          }
          else if(ordinal == DATE.ordinal())
          {
            printField(stream, dateFormat.format(trans.getDate()));
          }
          else if(ordinal == NOTES.ordinal())
          {
            printField(stream, trans.getNotes());
          }
          else if(ordinal == PAYEE.ordinal())
          {
            // Can be either a payee or an account.
            if(isTransfer(trans) == true)
            {
              printField(stream, "[" + trans.getPayee() + "]");
            }
            else
            {
              printField(stream, trans.getPayee());
            }
          }

          if((column + 1) < columnOrder.length)
          {
            stream.print(SEPARATOR);
          }
        }

        if(exportCategory == true)
        {
          stream.print(SEPARATOR);

          if(isSplit(trans) == true)
          {
            printField(stream, SPLIT);
          }
          else if(isTransfer(trans) == true)
          {
            printField(stream, TRANSFER);
          }
          else
          {
            printField(stream, trans.getCategory());
          }
        }

        stream.println();
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
  void
  printColumnHeaders(PrintStream stream, int[] columnOrder)
  {
    for(int column = 0; column < columnOrder.length; ++column)
    {
      int ordinal = columnOrder[column]; // Get column mapping.

      if(ordinal == AMOUNT.ordinal())
      {
        stream.print(getSharedProperty("amount"));
      }
      else if(ordinal == CHECK_NUMBER.ordinal())
      {
        stream.print(getSharedProperty("check_number"));
      }
      else if(ordinal == DATE.ordinal())
      {
        stream.print(getSharedProperty("date"));
      }
      else if(ordinal == NOTES.ordinal())
      {
        stream.print(getSharedProperty("notes"));
      }
      else if(ordinal == PAYEE.ordinal())
      {
        stream.print(getSharedProperty("payee"));
      }

      if((column + 1) < columnOrder.length)
      {
        stream.print(SEPARATOR);
      }
    }

    if(exportCategoryForCSV() == true)
    {
      stream.print(SEPARATOR);
      stream.print(getSharedProperty("category"));
    }

    stream.println();
  }

  private
  static
  void
  printField(PrintStream stream, String data)
  {
    stream.print(QUOTE + data + QUOTE);
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  private static final String SPLIT = "[" + getSharedProperty("split") + "]";
  private static final String TRANSFER = "[" + getSharedProperty("transfer") + "]";
}
