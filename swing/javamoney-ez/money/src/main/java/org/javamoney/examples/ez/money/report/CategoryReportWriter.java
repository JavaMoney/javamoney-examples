// CategoryReportWriter

package org.javamoney.examples.ez.money.report;

import static org.javamoney.examples.ez.common.utility.I18NHelper.getSharedProperty;
import static java.util.Collections.sort;
import static org.javamoney.examples.ez.money.ApplicationProperties.UI_DATE_FORMAT;
import static org.javamoney.examples.ez.money.ApplicationProperties.getCategoryReportSortByField;
import static org.javamoney.examples.ez.money.ApplicationProperties.includeDetailsInReport;
import static org.javamoney.examples.ez.money.ApplicationProperties.includeGroupsInReport;
import static org.javamoney.examples.ez.money.model.dynamic.total.IncomeExpenseTotal.getExpenseTotal;
import static org.javamoney.examples.ez.money.model.dynamic.total.IncomeExpenseTotal.getIncomeTotal;
import static org.javamoney.examples.ez.money.utility.HTMLHelper.buildLinkTag;

import java.io.File;
import java.io.PrintStream;
import java.util.Collection;
import java.util.List;

import org.javamoney.examples.ez.money.KeywordKeys;
import org.javamoney.examples.ez.money.locale.PercentFormat;
import org.javamoney.examples.ez.money.model.dynamic.total.IncomeExpenseTotal;
import org.javamoney.examples.ez.money.model.dynamic.total.TransactionDetail;
import org.javamoney.examples.ez.money.model.persisted.transaction.Transaction;

import org.javamoney.examples.ez.common.utility.I18NHelper;

/**
 * This class facilitates generating an HTML formatted file for a category
 * report. All methods in this class are static.
 */
public
final
class
CategoryReportWriter
extends ReportWriter
{
  /**
   * This method writes HTML formatted data to a user specified file.
   *
   * @param type The type of category report to generate.
   * @param report The report to generate the file for.
   */
  public
  static
  void
  generate(TotalReportTypeKeys type, CategoryReport report)
  {
    File file = chooseFile(getSharedProperty("report"));

    if(file != null)
    {
      generate(type, report, file);
    }
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of private methods.
  //////////////////////////////////////////////////////////////////////////////

  private
  static
  void
  generate(TotalReportTypeKeys type, CategoryReport report, File file)
  {
    try
    {
      PrintStream stream = createPrintStream(file);
      Collection<IncomeExpenseTotal> groups = getGroupTotals(report, type);
      Collection<IncomeExpenseTotal> totals = getTotals(report, type);

      stream.println("<html>");
      printHeadTag(stream);
      stream.println("<body>");

      printPreparedOn(stream);
      printPeriodInfo(stream, report, type);
      printSectionHeader(stream, getSharedProperty("period_summary"));
      printReportSummary(stream);

      if(includeGroupsInReport() == true && totals.size() !=  0)
      {
        printSectionHeader(stream, getProperty("group_totals"));
        printTotals(stream, groups, false);
      }

      printSectionHeader(stream, type.toString());
      printTotals(stream, totals, includeDetailsInReport());

      if(includeDetailsInReport() == true && totals.size() !=  0)
      {
        printSectionHeader(stream, getSharedProperty("transaction_details"));
        printDetails(stream, totals);
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
  String
  getCategoryUID(IncomeExpenseTotal total)
  {
    String category = total.getCategoryIdentifier();

    if(category.length() == 0)
    {
      category = KeywordKeys.NOT_CATEGORIZED.toString();
    }

    return category;
  }

  private
  static
  Collection<IncomeExpenseTotal>
  getTotals(CategoryReport report, TotalReportTypeKeys type)
  {
    Collection<IncomeExpenseTotal> totals = null;

    if(type == TotalReportTypeKeys.EXPENSES)
    {
      totals = report.getExpenses();
    }
    else
    {
      totals = report.getIncome();
    }

    // Sort the report according to the user's preference.
    sort((List<IncomeExpenseTotal>)totals, new CategoryReportComparator());

    return totals;
  }

  private
  static
  Collection<IncomeExpenseTotal>
  getGroupTotals(CategoryReport report, TotalReportTypeKeys type)
  {
    Collection<IncomeExpenseTotal> totals = null;

    if(type == TotalReportTypeKeys.EXPENSES)
    {
      totals = report.getExpenseGroups();
    }
    else
    {
      totals = report.getIncomeGroups();
    }

    // Sort the report according to the user's preference.
    sort((List<IncomeExpenseTotal>)totals, new CategoryReportComparator());

    return totals;
  }

  private
  static
  String
  getProperty(String key)
  {
    return I18NHelper.getProperty("CategoryReportWriter." + key);
  }

  private
  static
  void
  printDetails(PrintStream stream, Collection<IncomeExpenseTotal> totals)
  {
    for(IncomeExpenseTotal total : totals)
    {
      printTotalDetail(stream, total);

      stream.println("<table>");

      printTransactionsHeader(stream);

      for(TransactionDetail detail : total.getTransactionDetails())
      {
        Transaction trans = detail.getTransaction();

        stream.println("<tr>");
        stream.println("<td align=center>" + UI_DATE_FORMAT.format(trans.getDate()) + "</td>");
        stream.println("<td>&nbsp;</td>");
        stream.println("<td align=center>" + formatAmount(-trans.getAmount().getNumber().doubleValue()) + "</td>");
        stream.println("</tr>");

        printTransactionDetailField(stream, ACCOUNT, detail.getAccount().getIdentifier());
        printTransactionDetailField(stream, PAYEE, trans.getPayee());
        printTransactionDetailField(stream, CATEGORY, getCategory(trans.getCategory()));

        stream.println(TABLE_BREAK);
      }

      stream.println("</table>");

      if(total.getTransactionDetails().size() == 0)
      {
        printNoTransactionsMessage(stream);
      }

      stream.println("<br>");
    }
  }

  private
  static
  void
  printReportSummary(PrintStream stream)
  {
    double net = getIncomeTotal() - getExpenseTotal();

    stream.println("<table>");

    // Income total.
    stream.println("<tr>");
    stream.println("<td>" + getSharedProperty("income") + ":</td>");
    stream.println("<td>" + formatAmount(getIncomeTotal()) + "</td>");
    stream.println("</tr>");

    // Total spent.
    stream.println("<tr>");
    stream.println("<td>" + getSharedProperty("expenses") + ":</td>");
    stream.println("<td>" + formatAmount(getExpenseTotal()) + "</td>");
    stream.println("</tr>");

    stream.println("<tr>");
    stream.println("<td colspan=2><hr></td>");
    stream.println("</tr>");

    // Total available balance.
    stream.println("<tr>");
    stream.println("<td>" + getSharedProperty("net") + ":</td>");
    stream.println("<td>" + formatAmount(net) + "</td>");
    stream.println("</tr>");

    stream.println(TABLE_BREAK);

    stream.println("</table>");
  }

  private
  static
  void
  printTotalDetail(PrintStream stream, IncomeExpenseTotal total)
  {
    String category = getCategoryUID(total);

    stream.println("<table>");

    stream.println(TABLE_BREAK);

    stream.println("<tr>");
    stream.println("<td colspan=2>");
    stream.println("<a id=\"" + category + "\">&nbsp;</a>");
    stream.println("-- <font color=#006600><b>" + category + "</b></font> --");
    stream.println("</td>");
    stream.println("</tr>");

    stream.println("<tr>");
    stream.println("<td>" + AMOUNT + ":</td>");
    stream.println("<td>" + formatAmount(Math.abs(total.getAmount())) + "</td>");
    stream.println("</tr>");

    stream.println("<tr>");
    stream.println("<td>" + GROUP + ":</td>");
    stream.println("<td>" + total.getGroupName() + "</td>");
    stream.println("</tr>");

    stream.println("<tr>");
    stream.println("<td>" + PercentFormat.SYMBOL + ":</td>");
    stream.println("<td>" + PERCENT_FORMAT.format(total.getPercent()) + "</td>");
    stream.println("</tr>");

    stream.println("<tr>");
    stream.println("<td>" + TRANSACTIONS + ":</td>");
    stream.println("<td>" + total.getTransactionDetails().size() + "</td>");
    stream.println("</tr>");

    stream.println(TABLE_BREAK);

    stream.println("</table>");
  }

  private
  static
  void
  printTotalHeader(PrintStream stream)
  {
    CategoryReportSortByKeys key = getCategoryReportSortByField();
    String gray = "#CCCCFF";
    String amountColor = (key == CategoryReportSortByKeys.AMOUNT) ? gray : "#FFFFFF";
    String categoryColor = (key == CategoryReportSortByKeys.CATEGORY) ? gray : "#FFFFFF";
    String groupColor = (key == CategoryReportSortByKeys.GROUP) ? gray : "#FFFFFF";
    String percentColor = (key == CategoryReportSortByKeys.PERCENT) ? gray : "#FFFFFF";

    stream.println("<tr>");

    // Number column.
    stream.println("<td width=10>&nbsp;</td>");

    // Category column.
    stream.println("<td bgcolor=" + categoryColor + " align=center width=200>");
    stream.println("<b>" + CATEGORY + "</b>");
    stream.println("</td>");

    // Group column.
    stream.println("<td bgcolor=" + groupColor + " align=center width=225>");
    stream.println("<b>" + GROUP + "</b>");
    stream.println("</td>");

    // Amount column.
    stream.println("<td bgcolor=" + amountColor + " align=center width=90>");
    stream.println("<b>" + AMOUNT + "</b>");
    stream.println("</td>");

    // Percent column.
    stream.println("<td bgcolor=" + percentColor + " align=center width=50>");
    stream.println("<b>" + PercentFormat.SYMBOL + "</b>");
    stream.println("</td>");

    stream.println("</tr>");

    stream.println("<tr><td colspan=5><hr></td></tr>");
  }

  private
  static
  void
  printTotals(PrintStream stream, Collection<IncomeExpenseTotal> totals,
      boolean includeDetails)
  {
    int count = 1;

    stream.println("<table>");

    printTotalHeader(stream);

    for(IncomeExpenseTotal total : totals)
    {
      String category = getCategoryUID(total);

      stream.println("<tr>");

      stream.println("<td align=right>" + (count++) + ")</td>");

      stream.println("<td>");

      if(includeDetails == true)
      {
        stream.println(buildLinkTag("#" + category, category));
      }
      else
      {
        stream.println(category);
      }

      stream.println("</td>");

      stream.println("<td>" + total.getGroupName() + "</td>");
      stream.println("<td align=center>" + formatAmount(Math.abs(total.getAmount())) + "</td>");
      stream.println("<td align=center>" + PERCENT_FORMAT.format(total.getPercent()) + "</td>");
      stream.println("</tr>");
    }

    stream.println(TABLE_BREAK);
    stream.println("</table>");

    if(totals.size() == 0)
    {
      printNoTransactionsMessage(stream);
    }
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  private static final PercentFormat PERCENT_FORMAT = new PercentFormat();

  private static final String AMOUNT = getSharedProperty("amount");
  private static final String GROUP = getSharedProperty("group");
}
