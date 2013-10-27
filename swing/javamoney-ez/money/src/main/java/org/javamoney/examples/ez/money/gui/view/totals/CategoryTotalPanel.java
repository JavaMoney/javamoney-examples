// CategoryTotalPanel

package org.javamoney.examples.ez.money.gui.view.totals;

import static org.javamoney.examples.ez.money.gui.GUIConstants.COLOR_BACKGROUND_FILL;

import java.awt.GridBagConstraints;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.javamoney.examples.ez.money.gui.table.CategoryTotalTable;
import org.javamoney.examples.ez.money.model.dynamic.total.IncomeExpenseTotal;

import org.javamoney.examples.ez.common.gui.Panel;
import org.javamoney.examples.ez.common.gui.ScrollPane;

/**
 * This class facilitates viewing category totals.
 */
final
class
CategoryTotalPanel
extends TotalPanel
{
  /**
   * Constructs a new category total panel.
   */
  protected
  CategoryTotalPanel()
  {
    setPieChartPanel(new CategoryTotalPieChartPanel());
    setTable(new CategoryTotalTable());

    buildPanel();
  }

  /**
   * This method clears all the category total details.
   */
  protected
  void
  clear()
  {
    getPieChartPanel().clear();
    getTable().clear();
    getTransactionDetailPanel().clear();
  }

  /**
   * This method returns the table that maintains this panel's category totals.
   *
   * @return The table that maintains this panel's category totals.
   */
  protected
  CategoryTotalTable
  getTable()
  {
    return itsTable;
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of private methods.
  //////////////////////////////////////////////////////////////////////////////

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

    // Add listeners.
    getTable().getSelectionModel().addListSelectionListener(new SelectionHandler());
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
  CategoryTotalPieChartPanel
  getPieChartPanel()
  {
    return itsPieChartPanel;
  }

  private
  void
  setPieChartPanel(CategoryTotalPieChartPanel panel)
  {
    itsPieChartPanel = panel;
  }

  private
  void
  setTable(CategoryTotalTable table)
  {
    itsTable = table;
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of inner classes.
  //////////////////////////////////////////////////////////////////////////////

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
        IncomeExpenseTotal total = getTable().get(getTable().getSelectedRow());

        // Create a new total of the same type. The identifier is not needed.
        total = new IncomeExpenseTotal(total.getType(), "");

        // Sum the selected totals.
        for(int row : getTable().getSelectedRows())
        {
          IncomeExpenseTotal temp = getTable().get(row);

          if(temp.getType().isSummary() == false)
          {
            total.setAmount(total.getAmount() + temp.getAmount());
            total.getTransactionDetails().addAll(temp.getTransactionDetails());
          }
        }

        getPieChartPanel().updateView(total);
        getTransactionDetailPanel().updateView(total.getTransactionDetails());
      }
    }
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  private CategoryTotalPieChartPanel itsPieChartPanel;
  private CategoryTotalTable itsTable;
}
