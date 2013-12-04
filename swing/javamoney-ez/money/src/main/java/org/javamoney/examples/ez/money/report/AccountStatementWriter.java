// AccountStatementWriter

package org.javamoney.examples.ez.money.report;

import static org.javamoney.examples.ez.common.utility.I18NHelper.getSharedProperty;
import static org.javamoney.examples.ez.money.ApplicationProperties.UI_DATE_FORMAT;
import static org.javamoney.examples.ez.money.ApplicationProperties.includeCategoriesInAccountStatement;
import static org.javamoney.examples.ez.money.ApplicationProperties.includeCheckInAccountStatement;
import static org.javamoney.examples.ez.money.ApplicationProperties.includeNotesInAccountStatement;
import static org.javamoney.examples.ez.money.ApplicationProperties.includeReconciledStatusInAccountStatement;

import java.io.File;
import java.io.PrintStream;

import org.javamoney.examples.ez.money.model.persisted.account.AccountTypeKeys;
import org.javamoney.examples.ez.money.model.persisted.transaction.Transaction;

import org.javamoney.examples.ez.common.utility.I18NHelper;

/**
 * This class facilitates generating an HTML formatted file for an account
 * statement. All methods in this class are static.
 */
public
final
class
AccountStatementWriter
extends ReportWriter
{
  /**
   * This method writes HTML formatted data to a user specified file.
   *
   * @param statement The statement to generate the file for.
   */
  public
  static
  void
  generate(AccountStatement statement)
  {
    File file = chooseFile(statement.getAccount().toString());

    if(file != null)
    {
      generate(statement, file);
    }
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of private methods.
  //////////////////////////////////////////////////////////////////////////////

  private
  static
  void
  generate(AccountStatement statement, File file)
  {
    try
    {
      PrintStream stream = createPrintStream(file);

      stream.println("<html>");
      printHeadTag(stream);
      stream.println("<body>");

      printPreparedOn(stream);
      printPeriodInfo(stream, statement);
      printSectionHeader(stream, getSharedProperty("account_info"));
      printAccountInformation(stream, statement);
      printSectionHeader(stream, getProperty("summary"));
      printStatementSummary(stream, statement);
      printSectionHeader(stream, getSharedProperty("transaction_details"));
      printTransactions(stream, statement);

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
  String
  getProperty(String key)
  {
    return I18NHelper.getProperty("AccountStatementWriter." + key);
  }

  private
  static
  void
  printAccountInformation(PrintStream stream, AccountStatement statement)
  {
    stream.println("<table>");

    // Name.
    stream.println("<tr>");
    stream.println("<td>" + ACCOUNT + ":</td>");
    stream.println("<td><b>" + statement.getAccount() + "</b></td>");
    stream.println("</tr>");

    // Type.
    stream.println("<tr>");
    stream.println("<td>" + getSharedProperty("type") + ":</td>");
    stream.println("<td>" + statement.getAccount().getType() + "</td>");
    stream.println("</tr>");

    // Balance.
    stream.println("<tr>");
    stream.println("<td>" + BALANCE + ":</td>");
    stream.println("<td>" + formatAmount(statement.getAccount().getBalanceForUI()) + "</td>");
    stream.println("</tr>");

    stream.println("</table>");
  }

  private
  static
  void
  printStatementSummary(PrintStream stream, AccountStatement statement)
  {
    String expenses = null;
    String income = null;
    String tTo = null;

    if(statement.getAccount().getType() == AccountTypeKeys.CASH)
    {
      expenses = getProperty("purchases");
      income = getSharedProperty("income");
      tTo = getSharedProperty("transferred_to");
    }
    else if(statement.getAccount().getType() == AccountTypeKeys.CREDIT)
    {
      expenses = getProperty("purchases");
      income = getProperty("credits");
      tTo = getProperty("payments");
    }
    else
    {
      expenses = getSharedProperty("expenses");
      income = getSharedProperty("income");
      tTo = getSharedProperty("transferred_to");
    }

    stream.println("<table>");

    if(statement.getAccount().getType() == AccountTypeKeys.CREDIT)
    {
      stream.println("<tr>");
      stream.println("<td>" + TRANSACTIONS + ":</td>");
      stream.println("<td width=150>" + statement.getTransactions().size() + "</td>");
      stream.println("</tr>");

      stream.println("<tr>");
      stream.println("<td>" + expenses + ":</td>");
      stream.println("<td>" + formatAmount(statement.getExpenseTotal()) + "</td>");
      stream.println("<td>" + income + ":</td>");
      stream.println("<td>" + formatAmount(statement.getIncomeTotal()) + "</td>");
      stream.println("</tr>");

      stream.println("<tr>");
      stream.println("<td>" + tTo + ":</td>");
      stream.println("<td>" + formatAmount(statement.getTransferredToTotal()) + "</td>");
      stream.println("</tr>");
    }
    else
    {
      stream.println("<tr>");
      stream.println("<td>" + TRANSACTIONS + ":</td>");
      stream.println("<td width=150>" + statement.getTransactions().size() + "</td>");
      stream.println("</tr>");

      stream.println("<tr>");
      stream.println("<td>" + income + ":</td>");
      stream.println("<td>" + formatAmount(statement.getIncomeTotal()) + "</td>");
      stream.println("<td>" + tTo + ":</td>");
      stream.println("<td>" + formatAmount(statement.getTransferredToTotal()) + "</td>");
      stream.println("</tr>");

      stream.println("<tr>");
      stream.println("<td>" + expenses + ":</td>");
      stream.println("<td>" + formatAmount(statement.getExpenseTotal()) + "</td>");

      stream.println("<td>" + getSharedProperty("transferred_from") + ":</td>");
      stream.println("<td>" + formatAmount(statement.getTransferredFromTotal()) + "</td>");
      stream.println("</tr>");
    }

    stream.println("</table>");
  }

  private
  static
  void
  printTransaction(PrintStream stream, Transaction trans)
  {
    boolean includeCategories = includeCategoriesInAccountStatement();
    boolean includeCheck = includeCheckInAccountStatement();
    boolean includeFlags = includeReconciledStatusInAccountStatement();
    boolean includeNotes = includeNotesInAccountStatement();

    stream.println("<tr>");
    stream.println("<td align=center>" + UI_DATE_FORMAT.format(trans.getDate()) + "</td>");
    stream.println("<td>&nbsp;</td>");
    stream.println("<td align=center>" + formatAmount(trans.getAmount().doubleValue()) + "</td>");
    stream.println("</tr>");

    printTransactionDetailField(stream, PAYEE, trans.getPayee());

    if(includeCategories == true)
    {
      printCategory(stream, trans);
    }

    if(includeCheck == true)
    {
      printTransactionDetailField(stream, CHECK, trans.getCheckNumber());
    }

    if(includeNotes == true)
    {
      printTransactionDetailField(stream, NOTES, trans.getNotes());
    }

    if(includeFlags == true)
    {
      printFlags(stream, trans);
    }

    stream.println(TABLE_BREAK);
    stream.println(TABLE_BREAK);
  }

  private
  static
  void
  printTransactions(PrintStream stream, AccountStatement statement)
  {
    stream.println("<table>");
    printTransactionsHeader(stream);

    for(Transaction trans : statement.getTransactions())
    {
      printTransaction(stream, trans);
    }

    stream.println("</table>");

    if(statement.getTransactions().size() == 0)
    {
      printNoTransactionsMessage(stream);
    }
  }
}
