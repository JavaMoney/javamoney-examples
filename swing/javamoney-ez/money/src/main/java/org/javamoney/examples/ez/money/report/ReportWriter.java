// ReportWriter

package org.javamoney.examples.ez.money.report;

import static org.javamoney.examples.ez.common.utility.DateHelper.createCalendar;
import static org.javamoney.examples.ez.common.utility.I18NHelper.getSharedProperty;
import static org.javamoney.examples.ez.common.utility.ResourceHelper.openURL;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;
import static org.javamoney.examples.ez.money.ApplicationProperties.UI_DATE_FORMAT;
import static org.javamoney.examples.ez.money.KeywordKeys.NOT_CATEGORIZED;
import static org.javamoney.examples.ez.money.importexport.QIFConstants.CATEGORY_SEPARATOR_CHAR;
import static org.javamoney.examples.ez.money.utility.DialogHelper.decide;
import static org.javamoney.examples.ez.money.utility.DialogHelper.error;
import static org.javamoney.examples.ez.money.utility.FileDialogHelper.showSaveDialog;
import static org.javamoney.examples.ez.money.utility.TransactionHelper.isExpense;
import static org.javamoney.examples.ez.money.utility.TransactionHelper.isSplit;
import static org.javamoney.examples.ez.money.utility.TransactionHelper.isTransfer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.text.DateFormatSymbols;
import java.util.Date;
import java.util.GregorianCalendar;

import org.javamoney.examples.ez.money.model.dynamic.transaction.Split;
import org.javamoney.examples.ez.money.model.persisted.transaction.Transaction;
import org.javamoney.examples.ez.money.utility.HTMLHelper;

import org.javamoney.examples.ez.common.utility.I18NHelper;

/**
 * This class provides common functionality and class members for the report
 * writers. All methods in this class are static.
 */
abstract
class
ReportWriter
{
  /**
   * This method returns the select a file if the user accepts the dialog,
   * otherwise null.
   *
   * @param fileName The initially selected file.
   *
   * @return The select a file if the user accepts the dialog, otherwise null.
   */
  protected
  final
  static
  File
  chooseFile(String fileName)
  {
    return showSaveDialog(fileName + HTML_EXT, HTML_EXT);
  }

  /**
   * This method returns a print stream that will print to the specified file.
   *
   * @param file The file to write to.
   *
   * @return A print stream that will print to the specified file.
   *
   * @throws FileNotFoundException If the file cannot be found.
   * @throws UnsupportedEncodingException If the stream is opened with an
   * invalid encoding type.
   */
  protected
  final
  static
  PrintStream
  createPrintStream(File file)
  throws FileNotFoundException, UnsupportedEncodingException
  {
    return new PrintStream(new FileOutputStream(file), false, "UTF-16");
  }

  /**
   * This method returns an HTML formatted string representation of the
   * specified amount in the format of #,###.## or (#,###.##), depending on
   * whether or not the amount is negative.
   *
   * @param amount The amount to format.
   *
   * @return An HTML formatted string representation of the specified amount.
   */
  protected
  final
  static
  String
  formatAmount(double amount)
  {
    return HTMLHelper.formatAmount(amount, false);
  }

  /**
   * This method returns the category for the specified QIF string.
   *
   * @param qif The QIF formatted string to obtain the category from.
   *
   * @return The category for the specified QIF string.
   */
  protected
  static
  String
  getCategory(String qif)
  {
    int index = qif.lastIndexOf(CATEGORY_SEPARATOR_CHAR);

    if(index != -1)
    {
      qif = qif.substring(index + 1);
    }

    return qif;
  }

  /**
   *
   *
   * @param stream
   * @param trans
   */
  protected
  static
  void
  printCategory(PrintStream stream, Transaction trans)
  {
    String category = trans.getCategory();

    if(isTransfer(trans) == true)
    {
      if(isExpense(trans) == true)
      {
        category = TRANSFER_TO;
      }
      else
      {
        category = TRANSFER_FROM;
      }
    }
    else if(isSplit(trans) == true)
    {
      category = SPLIT;
    }
    else
    {
      category = getCategory(category);
    }

    printTransactionDetailField(stream, CATEGORY, category);

    if(isSplit(trans) == true)
    {
      printSplit(stream, trans);
    }
  }

  /**
   *
   *
   * @param stream
   * @param trans
   */
  protected
  static
  void
  printFlags(PrintStream stream, Transaction trans)
  {
    if(trans.isReconciled() == true)
    {
      printTransactionDetailField(stream, RECONCILED, YES);
    }
    else
    {
      printTransactionDetailField(stream, RECONCILED, NO);
    }
  }

  /**
   * This method prints common elements for reports pertaining to the head tag
   * on the specified stream.
   *
   * @param stream The stream to print on.
   */
  protected
  final
  static
  void
  printHeadTag(PrintStream stream)
  {
    stream.println("<head>");
    stream.println("<style type=\"text/css\">");
    stream.println("a{border-bottom-color:white;border-bottom-style:solid;color:black;text-decoration:none;}");
    stream.println("a:link{border-bottom-color:white;border-bottom-style:solid;color:black;}");
    stream.println("a:visited{border-bottom-color:white;border-bottom-style:solid;color:black;}");
    stream.println("a:hover{border-bottom-color:#8187BA;border-bottom-style:groove;}");
    stream.println("body{color:black;font-family:arial;font-size:11px}");
    stream.println("hr{background-color:black;border-style:none;color:black;height:1px;}");
    stream.println("table{color:black;font-family:arial;font-size:11px}");
    stream.println("table.header{border-style:solid}");
    stream.println("</style>");
    stream.println("</head>");
  }

  /**
   * This method prints a message on the specified stream informing the user
   * that there are no transactions for the period.
   *
   * @param stream The stream to print on.
   */
  protected
  final
  static
  void
  printNoTransactionsMessage(PrintStream stream)
  {
    stream.println("<table width=595>");
    stream.println("<tr><td align=center><b>" + getProperty("no_transactions") + "</b></td></tr>");
    stream.println("</table>");
  }

  /**
   * This method prints the period information on the specified stream for the
   * specified account statement.
   *
   * @param stream The stream to print on.
   * @param statement The account statement the period pertains to.
   */
  protected
  final
  static
  void
  printPeriodInfo(PrintStream stream, AccountStatement statement)
  {
    stream.println(getProperty("period_from.statement"));
    stream.println("<b>" + formatDate(statement.getStartDate()) + "</b>");
    stream.println(getSharedProperty("thru"));
    stream.println("<b>" + formatDate(statement.getEndDate()) + "</b><br>");
  }

  /**
   * This method prints the period information on the specified stream for the
   * specified budget report.
   *
   * @param stream The stream to print on.
   * @param report The budget report statement the period pertains to.
   */
  protected
  final
  static
  void
  printPeriodInfo(PrintStream stream, BudgetReport report)
  {
    GregorianCalendar end = createCalendar(report.getEndDate());
    GregorianCalendar start = createCalendar(report.getStartDate());
    String[] months = new DateFormatSymbols().getMonths();

    stream.println(getProperty("period_from.budget"));
    stream.println("<b>" + months[start.get(MONTH)]);
    stream.println(" " + start.get(YEAR) + "</b>");

    if(start.get(MONTH) != end.get(MONTH))
    {
      stream.println(" " + getSharedProperty("thru") + " ");
      stream.println("<b>" + months[end.get(MONTH)]);
      stream.println(" " + end.get(YEAR) + "</b>");
    }

    stream.println("<br>");
  }

  /**
   * This method prints the period information on the specified stream for the
   * specified category report.
   *
   * @param stream The stream to print on.
   * @param report The category report the period pertains to.
   * @param type The category report's type.
   */
  protected
  final
  static
  void
  printPeriodInfo(PrintStream stream, CategoryReport report,
      TotalReportTypeKeys type)
  {
    if(type == TotalReportTypeKeys.EXPENSES)
    {
      stream.println(getProperty("period_from.expenses"));
    }
    else
    {
      stream.println(getProperty("period_from.income"));
    }

    stream.println("<b>" + formatDate(report.getStartDate()) + "</b>");
    stream.println(getSharedProperty("thru"));
    stream.println("<b>" + formatDate(report.getEndDate()) + "</b><br>");
  }

  /**
   * This method prints a table on the specified stream detailing when the
   * report was prepared.
   *
   * @param stream The stream to print on.
   */
  protected
  final
  static
  void
  printPreparedOn(PrintStream stream)
  {
    stream.println("<table>");
    stream.println("<tr><td align=right width=595>");
    stream.println(getProperty("prepared") + " " + formatDate(new Date()));
    stream.println(" " + getProperty("by") + "</td></tr>");
    stream.println("</table>");
  }

  /**
   * This method prints a table with the specified title on the specified stream
   * to act as a section header.
   *
   * @param stream The stream to print on.
   * @param title The title for the section.
   */
  protected
  final
  static
  void
  printSectionHeader(PrintStream stream, String title)
  {
    stream.println("<br>");
    stream.println("<table class=\"header\" width=595>");
    stream.println("<tr><td>" + title + "</td></tr>");
    stream.println("</table>");
  }

  /**
   * This method prints a transaction's field and value on the specified stream.
   *
   * @param stream The stream to print on.
   * @param key The field's key.
   * @param value The field's value.
   */
  protected
  final
  static
  void
  printTransactionDetailField(PrintStream stream, String key, String value)
  {
    if(value.length() == 0)
    {
      value = NO_DATA;
    }

    stream.println("<tr><td>&nbsp;</td>");
    stream.println("<td colspan=2 style=\"max-width:375px\">");
    stream.println("<font color=#000066><u>" + key + "</font></u>: <i>" + value + "</i>");
    stream.println("</td></tr>");
  }

  /**
   * This method prints a heading that will precede a list of transactions on
   * the specified stream.
   *
   * @param stream The stream to print on.
   */
  protected
  final
  static
  void
  printTransactionsHeader(PrintStream stream)
  {
    stream.println("<tr>");
    stream.println("<td align=center width=75><b>" + getSharedProperty("date") + "</b></td>");
    stream.println("<td width=375><b>" + "" + "</b></td>");
    stream.println("<td align=center width=125><b>" + getSharedProperty("amount") + "</b></td>");
    stream.println("</tr>");
    stream.println("<tr><td colspan=3><hr></td></tr>");
  }

  /**
   * This method logs the specified exception and displays a message to the user
   * that the process failed.
   *
   * @param exception The error that occurred.
   */
  protected
  final
  static
  void
  showErrorMessage(Exception exception)
  {
    error(getProperty("error.title"),
        getProperty("error.description") +  " " + exception.getLocalizedMessage());
  }

  /**
   * This method displays a message to the user that the process succeeded and
   * asks whether or not to view the report now.
   *
   * @param file The file where the report was written.
   */
  protected
  final
  static
  void
  showSuccessMessage(File file)
  {
    if(decide(getProperty("success.title"),
        getProperty("success.description")) == true)
    {
      openURL(file.toURI().toString());
    }
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of private methods.
  //////////////////////////////////////////////////////////////////////////////

  private
  static
  String
  formatDate(Date date)
  {
    String str = ALL;

    if(date != null)
    {
      str = UI_DATE_FORMAT.format(date);
    }

    return str;
  }

  private
  static
  String
  getProperty(String key)
  {
    return I18NHelper.getProperty("ReportWriter." + key);
  }

  static
  private
  void
  printSplit(PrintStream stream, Transaction trans)
  {
    Split split = new Split(trans);

    stream.println("<tr><td>&nbsp;</td>");
    stream.println("<td colspan=2><ol>");

    // Print the categories in a list.
    for(int index = 0; index < split.size(); ++index)
    {
      String category = getCategory(split.getCategory(index));
      double amount = Math.abs(split.getAmount(index));

      if(category.length() == 0)
      {
        category = NOT_CATEGORIZED.toString();
      }

      stream.println("<li><i>" + category + " = " + formatAmount(amount) + "</i></li>");
    }

    stream.println("</ol></td></tr>");
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  /**
   * A constant for the word.
   */
  protected static final String ACCOUNT = I18NHelper.getSharedProperty("account");
  /**
   * A constant for the word.
   */
  protected static final String BALANCE = I18NHelper.getSharedProperty("balance");
  /**
   * A constant for the word.
   */
  protected static final String CATEGORY = I18NHelper.getSharedProperty("category");
  /**
   * A constant for the word.
   */
  protected static final String CHECK = I18NHelper.getSharedProperty("check_number");
  /**
   * A constant for the word.
   */
  protected static final String NOTES = I18NHelper.getSharedProperty("notes");
  /**
   * A constant for the word.
   */
  protected static final String PAYEE = I18NHelper.getSharedProperty("payee");
  /**
   * A constant for an empty row in a table to act as a break.
   */
  protected static final String TABLE_BREAK = "<tr><td>&nbsp;</td></tr>";
  /**
   * A constant for the word.
   */
  protected static final String TRANSACTIONS = I18NHelper.getSharedProperty("transactions");

  private static final String ALL = I18NHelper.getSharedProperty("all");
  private static final String HTML_EXT = ".html";
  private static final String NO = I18NHelper.getSharedProperty("no");
  private static final String NO_DATA = getProperty("no_data");
  private static final String RECONCILED = I18NHelper.getSharedProperty("reconciled");
  private static final String SPLIT = I18NHelper.getSharedProperty("split");
  private static final String TRANSFER_FROM = getProperty("transfer_from");
  private static final String TRANSFER_TO = getProperty("transfer_to");
  private static final String YES = I18NHelper.getSharedProperty("yes");
}
