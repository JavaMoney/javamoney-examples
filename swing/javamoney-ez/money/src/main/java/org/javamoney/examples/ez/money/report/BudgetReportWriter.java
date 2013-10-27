// BudgetReportWriter

package org.javamoney.examples.ez.money.report;

import static org.javamoney.examples.ez.common.utility.I18NHelper.getSharedProperty;
import static org.javamoney.examples.ez.money.ApplicationProperties.UI_DATE_FORMAT;
import static org.javamoney.examples.ez.money.ApplicationProperties.includeDetailsInReport;
import static org.javamoney.examples.ez.money.utility.HTMLHelper.buildLinkTag;

import java.io.File;
import java.io.PrintStream;

import org.javamoney.examples.ez.money.model.dynamic.total.Budget;
import org.javamoney.examples.ez.money.model.dynamic.total.TransactionDetail;
import org.javamoney.examples.ez.money.model.persisted.transaction.Transaction;

/**
 * This class facilitates generating an HTML formatted file for a budget report.
 * All methods in this class are static.
 */
public
final
class
BudgetReportWriter
extends ReportWriter
{
  /**
   * This method writes HTML formatted data to a user specified file.
   *
   * @param report The report to generate the file for.
   */
  public
  static
  void
  generate(BudgetReport report)
  {
    File file = chooseFile(getSharedProperty("report"));

    if(file != null)
    {
      generate(report, file);
    }
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of private methods.
  //////////////////////////////////////////////////////////////////////////////

  private
  static
  void
  generate(BudgetReport report, File file)
  {
    try
    {
      PrintStream stream = createPrintStream(file);

      stream.println("<html>");
      printHeadTag(stream);
      stream.println("<body>");

      printPreparedOn(stream);
      printPeriodInfo(stream, report);
      printSectionHeader(stream, getSharedProperty("period_summary"));
      printReportSummary(stream, report);
      printSectionHeader(stream, getSharedProperty("budgets"));
      printBudgets(stream, report);

      if(includeDetailsInReport() == true)
      {
        printSectionHeader(stream, getSharedProperty("transaction_details"));
        printDetails(stream, report);
      }

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
  printBudgetDetail(PrintStream stream, Budget budget)
  {
    stream.println("<table>");

    stream.println(TABLE_BREAK);

    stream.println("<tr>");
    stream.println("<td colspan=2>");
    stream.println("<a id=\"" + budget.getIdentifier() + "\">&nbsp;</a>");
    stream.println("-- <font color=#006600><b>" + budget.getIdentifier() + "</b></font> --");
    stream.println("</td>");
    stream.println("</tr>");

    stream.println("<tr>");
    stream.println("<td width=75px>" + TRANSACTIONS + ":</td>");
    stream.println("<td>" + budget.getTransactionDetails().size() + "</td>");
    stream.println("</tr>");

    stream.println(TABLE_BREAK);

    stream.println("</table>");
  }

  private
  static
  void
  printBudgetHeader(PrintStream stream)
  {
    stream.println("<tr>");
    stream.println("<td align=center width=170><b>" + CATEGORY + "</b></td>");
    stream.println("<td align=center width=45><b>" + getSharedProperty("type") + "</b></td>");
    stream.println("<td align=center width=90><b>" + getSharedProperty("budget") + "</b></td>");
    stream.println("<td align=center width=90><b>" + getSharedProperty("rollover") + "</b></td>");
    stream.println("<td align=center width=90><b>" + getSharedProperty("change") + "</b></td>");
    stream.println("<td align=center width=90><b>" + BALANCE + "</b></td>");
    stream.println("</tr>");
    stream.println("<tr><td colspan=6><hr></td></tr>");
  }

  private
  static
  void
  printBudgets(PrintStream stream, BudgetReport report)
  {
    stream.println("<table>");

    printBudgetHeader(stream);

    for(Budget budget : report.getBudgets())
    {
      String startingBalance = "--";

      if(budget.hasRolloverBalance() == true)
      {
        startingBalance = formatAmount(budget.getStartingBalance());
      }

      stream.println("<tr>");
      stream.println("<td>");

      if(includeDetailsInReport() == true)
      {
        stream.println(buildLinkTag("#" + budget.getIdentifier(), budget.getIdentifier()));
      }
      else
      {
        stream.println(budget.getIdentifier());
      }

      stream.println("</td>");

      stream.println("<td>" + budget.getType() + "</td>");
      stream.println("<td align=center>" + formatAmount(budget.getBudget()) + "</td>");
      stream.println("<td align=center>" + startingBalance + "</td>");
      stream.println("<td align=center>" + formatAmount(budget.getChange()) + "</td>");
      stream.println("<td align=center>" + formatAmount(budget.getBalance()) + "</td>");
      stream.println("</tr>");

      stream.println(TABLE_BREAK);
    }

    stream.println("</table>");
  }

  private
  static
  void
  printDetails(PrintStream stream, BudgetReport report)
  {
    for(Budget budget : report.getBudgets())
    {
      printBudgetDetail(stream, budget);

      stream.println("<table>");

      printTransactionsHeader(stream);

      for(TransactionDetail detail : budget.getTransactionDetails())
      {
        Transaction trans = detail.getTransaction();

        stream.println("<tr>");
        stream.println("<td align=center>" + UI_DATE_FORMAT.format(trans.getDate()) + "</td>");
        stream.println("<td>&nbsp;</td>");
        stream.println("<td align=center>" + formatAmount(-trans.getAmount()) + "</td>");
        stream.println("</tr>");

        printTransactionDetailField(stream, ACCOUNT, detail.getAccount().getIdentifier());
        printTransactionDetailField(stream, PAYEE, trans.getPayee());
        printTransactionDetailField(stream, CATEGORY, getCategory(trans.getCategory()));

        stream.println(TABLE_BREAK);
      }

      stream.println("</table>");

      if(budget.getTransactionDetails().size() == 0)
      {
        printNoTransactionsMessage(stream);
      }

      stream.println("<br>");
    }
  }

  private
  static
  void
  printReportSummary(PrintStream stream, BudgetReport report)
  {
    stream.println("<table>");

    // Budget count.
    stream.println("<tr>");
    stream.println("<td>" + getSharedProperty("budgets") + ":</td>");
    stream.println("<td>" + report.getBudgets().size() + "</td>");
    stream.println("</tr>");

    stream.println(TABLE_BREAK);

    // Total monthly budget.
    stream.println("<tr>");
    stream.println("<td>" + getSharedProperty("budget") + ":</td>");
    stream.println("<td>" + formatAmount(report.getBudgetTotal()) + "</td>");
    stream.println("</tr>");

    // Total rollover.
    stream.println("<tr>");
    stream.println("<td>" + getSharedProperty("rollover") + ":</td>");
    stream.println("<td>" + formatAmount(report.getRolloverTotal()) + "</td>");
    stream.println("</tr>");

    // Total change.
    stream.println("<tr>");
    stream.println("<td>" + getSharedProperty("change") + ":</td>");
    stream.println("<td>" + formatAmount(report.getChangeTotal()) + "</td>");
    stream.println("</tr>");

    stream.println("<tr>");
    stream.println("<td colspan=2><hr></td>");
    stream.println("</tr>");

    // Total available balance.
    stream.println("<tr>");
    stream.println("<td>" + BALANCE + ":</td>");
    stream.println("<td>" + formatAmount(report.getBalanceTotal()) + "</td>");
    stream.println("</tr>");

    stream.println(TABLE_BREAK);

    stream.println("</table>");
  }
}
