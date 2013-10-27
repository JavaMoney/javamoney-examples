// BudgetComparator

package org.javamoney.examples.ez.money.gui.table.comparator;

import static org.javamoney.examples.ez.common.utility.CompareHelper.compareAmounts;
import static org.javamoney.examples.ez.common.utility.CompareHelper.compareKeys;
import static org.javamoney.examples.ez.common.utility.CompareHelper.compareObjects;
import static org.javamoney.examples.ez.money.gui.table.BudgetTable.BALANCE_COLUMN;
import static org.javamoney.examples.ez.money.gui.table.BudgetTable.BUDGET_COLUMN;
import static org.javamoney.examples.ez.money.gui.table.BudgetTable.CHANGE_COLUMN;
import static org.javamoney.examples.ez.money.gui.table.BudgetTable.ID_COLUMN;
import static org.javamoney.examples.ez.money.gui.table.BudgetTable.STARTING_BALANCE_COLUMN;
import static org.javamoney.examples.ez.money.gui.table.BudgetTable.TYPE_COLUMN;

import org.javamoney.examples.ez.money.model.dynamic.total.Budget;

import org.javamoney.examples.ez.common.gui.table.DataTableComparator;

/**
 * This class facilitates comparing budgets in a table.
 */
public
final
class
BudgetComparator
extends DataTableComparator<Budget>
{
  /**
   * Constructs a new comparator.
   */
  public
  BudgetComparator()
  {
    super(TYPE_COLUMN);
  }

  /**
   * This method compares two budgets.
   *
   * @param budget1 A budget to compare.
   * @param budget2 A budget to compare.
   *
   * @return The result of comparing two budgets.
   */
  @Override
  public
  int
  compare(Budget budget1, Budget budget2)
  {
    int result = 0;

    // Ensure the summary is always at the bottom.
    if(budget1.getType().isSummary() == true)
    {
      result = 1;
    }
    else if(budget2.getType().isSummary() == true)
    {
      result = -1;
    }
    else if(getColumn() == BALANCE_COLUMN)
    {
      result = compareAmounts(budget2.getBalance(), budget1.getBalance(), invertSort());
    }
    else if(getColumn() == BUDGET_COLUMN)
    {
      result = compareAmounts(budget2.getAmount(), budget1.getAmount(), invertSort());
    }
    else if(getColumn() == CHANGE_COLUMN)
    {
      result = compareAmounts(budget2.getChange(), budget1.getChange(), invertSort());
    }
    else if(getColumn() == ID_COLUMN)
    {
      result = compareObjects(budget1, budget2, invertSort());
    }
    else if(getColumn() == STARTING_BALANCE_COLUMN)
    {
      result = compareAmounts(budget2.getStartingBalance(),
          budget1.getStartingBalance(), invertSort());
    }
    else
    {
      result = compareKeys(budget1.getType(), budget2.getType(), invertSort());
    }

    return result;
  }
}
