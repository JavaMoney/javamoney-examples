// CategoryTotalComparator

package org.javamoney.examples.ez.money.gui.table.comparator;

import static org.javamoney.examples.ez.common.utility.CompareHelper.compareAmounts;
import static org.javamoney.examples.ez.common.utility.CompareHelper.compareStrings;
import static org.javamoney.examples.ez.money.gui.table.CategoryTotalTable.AMOUNT_COLUMN;
import static org.javamoney.examples.ez.money.gui.table.CategoryTotalTable.CATEGORY_COLUMN;

import org.javamoney.examples.ez.money.model.dynamic.total.IncomeExpenseTotal;

import org.javamoney.examples.ez.common.gui.table.DataTableComparator;

/**
 * This class facilitates comparing category totals in a table.
 */
public
final
class
CategoryTotalComparator
extends DataTableComparator<IncomeExpenseTotal>
{
  /**
   * Constructs a new comparator.
   */
  public
  CategoryTotalComparator()
  {
    super(AMOUNT_COLUMN);
  }

  /**
   * This method compares two totals.
   *
   * @param total1 A total to compare.
   * @param total2 A total to compare.
   *
   * @return The result of comparing two totals.
   */
  @Override
  public
  int
  compare(IncomeExpenseTotal total1, IncomeExpenseTotal total2)
  {
    int result = 0;

    // Ensure the summary is always at the bottom.
    if(total1.getType().isSummary() == true)
    {
      result = 1;
    }
    else if(total2.getType().isSummary() == true)
    {
      result = -1;
    }
    else if(getColumn() == AMOUNT_COLUMN)
    {
      result = compareAmounts(total1.getAmount(), total2.getAmount(),
          invertSort());
    }
    else if(getColumn() == CATEGORY_COLUMN)
    {
      result = compareStrings(total1.getCategoryIdentifier(),
          total2.getCategoryIdentifier(), invertSort());
    }
    else
    {
      result = compareStrings(total1.getGroupName(), total2.getGroupName(),
          invertSort());
    }

    return result;
  }
}
