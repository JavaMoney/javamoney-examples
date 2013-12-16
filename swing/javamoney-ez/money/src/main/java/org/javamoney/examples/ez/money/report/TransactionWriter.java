// TransactionWriter

package org.javamoney.examples.ez.money.report;

import static org.javamoney.examples.ez.common.utility.I18NHelper.getSharedProperty;
import static org.javamoney.examples.ez.money.ApplicationProperties.UI_DATE_FORMAT;

import java.io.File;
import java.io.PrintStream;

import org.javamoney.examples.ez.money.model.persisted.transaction.Transaction;

/**
 * This class facilitates generating an HTML formatted file for a collection of
 * transactions. All methods in this class are static.
 */
public
final
class
TransactionWriter
extends ReportWriter
{
  /**
   * This method writes HTML formatted data to a user specified file.
   *
   * @param transactions The transactions to generate the file for.
   */
  public
  static
  void
  generate(Transaction[] transactions)
  {
    File file = chooseFile(getSharedProperty("report"));

    if(file != null)
    {
      generate(transactions, file);
    }
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of private methods.
  //////////////////////////////////////////////////////////////////////////////

  private
  static
  void
  generate(Transaction[] transactions, File file)
  {
    try
    {
      PrintStream stream = createPrintStream(file);

      stream.println("<html>");
      printHeadTag(stream);
      stream.println("<body>");

      printPreparedOn(stream);
      stream.println(TABLE_BREAK);
      printSectionHeader(stream, getSharedProperty("transaction_details"));
      printTransactions(stream, transactions);

      stream.println("</body></html>");

      stream.flush();
      stream.close();

      showSuccessMessage(file);
    }
    catch(Exception exception)
    {
      showErrorMessage(exception);
    }
  }

  private
  static
  void
  printTransactions(PrintStream stream, Transaction[] transactions)
  {
    stream.println("<table>");
    printTransactionsHeader(stream);

    for(Transaction trans : transactions)
    {
      printTransaction(stream, trans);
    }

    stream.println("</table>");
  }

  private
  static
  void
  printTransaction(PrintStream stream, Transaction trans)
  {
    stream.println("<tr>");
    stream.println("<td align=center>" + UI_DATE_FORMAT.format(trans.getDate()) + "</td>");
    stream.println("<td>&nbsp;</td>");
    stream.println("<td align=center>" + formatAmount(trans.getAmount().getNumber().doubleValue()) + "</td>");
    stream.println("</tr>");

    printTransactionDetailField(stream, PAYEE, trans.getPayee());
    printCategory(stream, trans);
    printTransactionDetailField(stream, CHECK, trans.getCheckNumber());
    printTransactionDetailField(stream, NOTES, trans.getNotes());
    printFlags(stream, trans);
    stream.println(TABLE_BREAK);
    stream.println(TABLE_BREAK);
  }
}
