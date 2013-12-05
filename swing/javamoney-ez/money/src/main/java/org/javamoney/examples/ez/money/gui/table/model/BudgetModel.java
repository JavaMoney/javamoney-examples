// BudgetModel

package org.javamoney.examples.ez.money.gui.table.model;

import static org.javamoney.examples.ez.money.ApplicationProperties.UI_CURRENCY_FORMAT;
import static org.javamoney.examples.ez.money.gui.table.BudgetTable.BALANCE_COLUMN;
import static org.javamoney.examples.ez.money.gui.table.BudgetTable.BUDGET_COLUMN;
import static org.javamoney.examples.ez.money.gui.table.BudgetTable.CHANGE_COLUMN;
import static org.javamoney.examples.ez.money.gui.table.BudgetTable.ICON_COLUMN;
import static org.javamoney.examples.ez.money.gui.table.BudgetTable.ID_COLUMN;
import static org.javamoney.examples.ez.money.gui.table.BudgetTable.ROW_COLUMN;
import static org.javamoney.examples.ez.money.gui.table.BudgetTable.STARTING_BALANCE_COLUMN;
import static org.javamoney.examples.ez.money.gui.table.BudgetTable.TYPE_COLUMN;

import org.javamoney.examples.ez.money.model.dynamic.total.Budget;

import org.javamoney.examples.ez.common.gui.Table;

/**
 * This class facilitates adding budgets into a table.
 */
public
final
class
BudgetModel
extends Table.NonmutableTableModel
{
  /**
   * This method adds the specified budget into the model's table.
   *
   * @param budget The budget to add.
   * @param row The row the total is in.
   */
  public
  void
  addRow(Budget budget, int row)
  {
    String[] rowData = new String[8];
    String startingBalance = "--";

    if(budget.hasRolloverBalance() == true)
    {
      startingBalance = UI_CURRENCY_FORMAT.format(budget.getStartingBalance());
    }

    rowData[BALANCE_COLUMN] = UI_CURRENCY_FORMAT.format(budget.getBalance());
    rowData[BUDGET_COLUMN] = UI_CURRENCY_FORMAT.format(budget.getBudget());
    rowData[CHANGE_COLUMN] = UI_CURRENCY_FORMAT.format(budget.getChange());
    rowData[ICON_COLUMN] = "";
    rowData[ID_COLUMN] = budget.getIdentifier();
    rowData[STARTING_BALANCE_COLUMN] = startingBalance;
    rowData[TYPE_COLUMN] = budget.getType().toString();

    if(budget.getType().isSummary() == true)
    {
      rowData[ROW_COLUMN] = "";
    }
    else
    {
      rowData[ROW_COLUMN] = "<html><b>" + row + "</b></html>";
    }

    addRow(rowData);
  }
}
