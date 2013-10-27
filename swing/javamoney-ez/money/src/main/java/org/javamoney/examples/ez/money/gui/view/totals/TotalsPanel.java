// TotalsPanel

package org.javamoney.examples.ez.money.gui.view.totals;

import static org.javamoney.examples.ez.money.KeywordKeys.TOTAL;
import static org.javamoney.examples.ez.money.model.dynamic.total.CategoryTotalTypeKeys.EXPENSE_SUMMARY;
import static org.javamoney.examples.ez.money.model.dynamic.total.CategoryTotalTypeKeys.INCOME_SUMMARY;
import static org.javamoney.examples.ez.money.report.TotalReportTypeKeys.EXPENSES;
import static org.javamoney.examples.ez.money.report.TotalReportTypeKeys.INCOME;
import static org.javamoney.examples.ez.money.report.TotalReportTypeKeys.TRANSFERS;

import java.awt.CardLayout;
import java.awt.GridBagConstraints;
import java.util.Date;

import javax.swing.JPanel;

import org.javamoney.examples.ez.money.model.dynamic.total.IncomeExpenseTotal;
import org.javamoney.examples.ez.money.model.dynamic.total.TotalFilter;
import org.javamoney.examples.ez.money.model.dynamic.total.TransferTotal;
import org.javamoney.examples.ez.money.report.CategoryReport;
import org.javamoney.examples.ez.money.report.TotalReportTypeKeys;

import org.javamoney.examples.ez.common.gui.Panel;

/**
 * This class facilitates viewing the totals.
 */
public
final
class
TotalsPanel
extends Panel
{
  /**
   * Constructs a new totals panel.
   */
  public
  TotalsPanel()
  {
    setCards(new JPanel(new CardLayout()));
    setExpensePanel(new CategoryTotalPanel());
    setIncomePanel(new CategoryTotalPanel());
    setTransferPanel(new TransferTotalPanel());

    // Add panels to cards.
    getCards().add(getExpensePanel(), EXPENSES.toString());
    getCards().add(getIncomePanel(), INCOME.toString());
    getCards().add(getTransferPanel(), TRANSFERS.toString());

    // Build panel.
    setFill(GridBagConstraints.BOTH);
    add(getCards(), 0, 0, 1, 1, 100, 100);
  }

  /**
   * This method shows the totals for the specified key.
   *
   * @param key The type of totals to show.
   */
  public
  void
  showTotalsFor(TotalReportTypeKeys key)
  {
    ((CardLayout)getCards().getLayout()).show(getCards(), key.toString());
  }

  /**
   * This method updates the totals for transactions that occurred between the
   * start and end dates inclusive, and that are accepted by the specified
   * filter.
   *
   * @param start The start date.
   * @param end The end date.
   * @param filter The filter to use.
   */
  public
  void
  updateView(Date start, Date end, TotalFilter filter)
  {
    // Clear data.
    getExpensePanel().clear();
    getIncomePanel().clear();
    getTransferPanel().clear();

    createReportAndDisplayResults(start, end, filter);
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of private methods.
  //////////////////////////////////////////////////////////////////////////////

  private
  void
  createReportAndDisplayResults(Date start, Date end, TotalFilter filter)
  {
    CategoryReport report = CategoryReport.createReport(start, end, filter);
    IncomeExpenseTotal expenses = new IncomeExpenseTotal(EXPENSE_SUMMARY, TOTAL.toString());
    IncomeExpenseTotal income = new IncomeExpenseTotal(INCOME_SUMMARY, TOTAL.toString());

    // Add expense totals.
    for(IncomeExpenseTotal total : report.getExpenses())
    {
      getExpensePanel().getTable().add(total);

      // Combine all the expense totals into the total.
      expenses.setAmount(expenses.getAmount() + total.getAmount());
    }

    getExpensePanel().getTable().add(expenses);

    // Add income totals.
    for(IncomeExpenseTotal total : report.getIncome())
    {
      getIncomePanel().getTable().add(total);

      // Combine all the income totals into the total.
      income.setAmount(income.getAmount() + total.getAmount());
    }

    getIncomePanel().getTable().add(income);

    // Add transfer totals.
    for(TransferTotal total : report.getTransfers())
    {
      getTransferPanel().getTable().add(total);
    }

    // Display results.
    getExpensePanel().getTable().display();
    getIncomePanel().getTable().display();
    getTransferPanel().getTable().display();
  }

  private
  JPanel
  getCards()
  {
    return itsCards;
  }

  private
  CategoryTotalPanel
  getExpensePanel()
  {
    return itsExpensePanel;
  }

  private
  CategoryTotalPanel
  getIncomePanel()
  {
    return itsIncomePanel;
  }

  private
  TransferTotalPanel
  getTransferPanel()
  {
    return itsTransferPanel;
  }

  private
  void
  setCards(JPanel panel)
  {
    itsCards = panel;
  }

  private
  void
  setExpensePanel(CategoryTotalPanel panel)
  {
    itsExpensePanel = panel;
  }

  private
  void
  setIncomePanel(CategoryTotalPanel panel)
  {
    itsIncomePanel = panel;
  }

  private
  void
  setTransferPanel(TransferTotalPanel panel)
  {
    itsTransferPanel = panel;
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  private JPanel itsCards;
  private CategoryTotalPanel itsExpensePanel;
  private CategoryTotalPanel itsIncomePanel;
  private TransferTotalPanel itsTransferPanel;
}
