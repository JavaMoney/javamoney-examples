// BudgetPanel

package org.javamoney.examples.ez.money.gui.view.totals;

import static org.javamoney.examples.ez.money.KeywordKeys.TOTAL;
import static org.javamoney.examples.ez.money.gui.GUIConstants.COLOR_BACKGROUND_FILL;
import static org.javamoney.examples.ez.money.gui.dialog.PreferencesDialog.showPreferencesDialog;
import static org.javamoney.examples.ez.money.gui.dialog.preferences.PreferencesKeys.EXPENSES;
import static org.javamoney.examples.ez.money.gui.dialog.preferences.PreferencesKeys.INCOME;
import static org.javamoney.examples.ez.money.model.dynamic.total.CategoryTotalTypeKeys.BUDGET_SUMMARY;
import static org.javamoney.examples.ez.money.model.dynamic.total.CategoryTotalTypeKeys.EXPENSE;

import java.awt.GridBagConstraints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.javamoney.examples.ez.money.gui.table.BudgetTable;
import org.javamoney.examples.ez.money.model.dynamic.total.Budget;
import org.javamoney.examples.ez.money.model.dynamic.total.TotalFilter;
import org.javamoney.examples.ez.money.model.persisted.category.Category;
import org.javamoney.examples.ez.money.report.BudgetReport;

import org.javamoney.examples.ez.common.gui.Panel;
import org.javamoney.examples.ez.common.gui.ScrollPane;

/**
 * This class facilitates management of the budget table and displaying
 * information for a select budget.
 */
public
final
class
BudgetPanel
extends TotalPanel
{
  /**
	 * 
	 */
	private static final long serialVersionUID = -4543784256981304199L;
/**
   * Constructs a new budgets panel.
   */
  public
  BudgetPanel()
  {
    setPieChartPanel(new BudgetPieChartPanel());
    setTable(new BudgetTable());

    buildPanel();

    // Add listeners.
    getTable().addMouseListener(new MouseHandler());
    getTable().getSelectionModel().addListSelectionListener(new SelectionHandler());
  }

  /**
   * This method updates the budget table for the specified date range and
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
    int row = getTable().getSelectedRow();

    updateFor(start, end, filter);

    // Select the budget that was already selected.
    if(row < getTable().getElementCount())
    {
      getTable().selectRow(row);
    }
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of private methods.
  //////////////////////////////////////////////////////////////////////////////

  private
  void
  addSummary(BudgetReport report)
  {
    Category category = new Category(TOTAL.toString());
    Budget summary = new Budget(BUDGET_SUMMARY, category);

    // Create a total budget to be displayed in the table.
    category.setBudget(report.getBudgetTotal());
    category.setHasRolloverBudget(true);

    summary.setChange(report.getChangeTotal());
    summary.setStartingBalance(report.getRolloverTotal());

    // Add all the transaction details into the total.
    for(Budget budget : getTable().getList())
    {
      summary.getTransactionDetails().addAll(budget.getTransactionDetails());
    }

    getTable().add(summary);
  }

  private
  void
  buildPanel()
  {
    // Build panel.
    setFill(GridBagConstraints.BOTH);
    add(createTablePanel(), 0, 0, 1, 1, 100, 100);
    add(getPieChartPanel(), 1, 0, 1, 4, 0, 0);
    addEmptyCellAt(0, 1);
    addEmptyCellAt(0, 2);
    add(getTransactionDetailPanel(), 0, 3, 1, 1, 0, 0);
  }

  private
  Panel
  createTablePanel()
  {
    Panel panel = new Panel();
    ScrollPane scrollPane = new ScrollPane(getTable());

    // Build scroll pane.
    scrollPane.setBackground(COLOR_BACKGROUND_FILL);

    // Build panel.
    panel.setFill(GridBagConstraints.BOTH);
    panel.add(scrollPane, 0, 0, 1, 1, 100, 100);
    panel.addEmptyCellAt(1, 0);

    return panel;
  }

  private
  BudgetPieChartPanel
  getPieChartPanel()
  {
    return itsPieChartPanel;
  }

  private
  BudgetTable
  getTable()
  {
    return itsTable;
  }

  private
  void
  setPieChartPanel(BudgetPieChartPanel panel)
  {
    itsPieChartPanel = panel;
  }

  private
  void
  setTable(BudgetTable table)
  {
    itsTable = table;
  }

  private
  void
  updateFor(Date start, Date end, TotalFilter filter)
  {
    BudgetReport report = BudgetReport.createReport(start, end, filter);

    getTable().clear();
    getPieChartPanel().clear();
    getTransactionDetailPanel().clear();

    // Add the budgets to the table.
    for(Budget budget : report.getBudgets())
    {
      getTable().add(budget);
    }

    addSummary(report);
    getTable().display();
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of inner classes.
  //////////////////////////////////////////////////////////////////////////////

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
      if(event.getButton() == MouseEvent.BUTTON1 && event.getClickCount() == 2)
      {
        Budget budget = getTable().getSelectedElement();

        if(budget != null)
        {
          if(budget.getType() == EXPENSE)
          {
            showPreferencesDialog(EXPENSES);
          }
          else
          {
            showPreferencesDialog(INCOME);
          }
        }
      }
    }
  }

  private
  class
  SelectionHandler
  implements ListSelectionListener
  {
    public
    void
    valueChanged(ListSelectionEvent event)
    {
      if(event.getValueIsAdjusting() == false && getTable().getSelectedRow() != -1)
      {
        Budget budget = getTable().get(getTable().getSelectedRow());

        getPieChartPanel().updateView(budget);
        getTransactionDetailPanel().updateView(budget.getTransactionDetails());
      }
    }
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  private BudgetPieChartPanel itsPieChartPanel;
  private BudgetTable itsTable;
}
