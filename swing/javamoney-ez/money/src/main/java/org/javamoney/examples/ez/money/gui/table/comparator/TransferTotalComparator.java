// TransferTotalComparator

package org.javamoney.examples.ez.money.gui.table.comparator;

import static org.javamoney.examples.ez.common.utility.CompareHelper.compareAmounts;
import static org.javamoney.examples.ez.common.utility.CompareHelper.compareObjects;
import static org.javamoney.examples.ez.money.gui.table.TransferTotalTable.FROM_COLUMN;
import static org.javamoney.examples.ez.money.gui.table.TransferTotalTable.ID_COLUMN;

import org.javamoney.examples.ez.money.model.dynamic.total.TransferTotal;

import org.javamoney.examples.ez.common.gui.table.DataTableComparator;

/**
 * This class facilitates comparing transfer totals in a table.
 */
public
final
class
TransferTotalComparator
extends DataTableComparator<TransferTotal>
{
  /**
   * Constructs a new comparator.
   */
  public
  TransferTotalComparator()
  {
    super(ID_COLUMN);
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
  compare(TransferTotal total1, TransferTotal total2)
  {
    int result = 0;

    if(getColumn() == ID_COLUMN)
    {
      result = compareObjects(total1, total2, invertSort());
    }
    else if(getColumn() == FROM_COLUMN)
    {
      result = compareAmounts(total2.getFromTotal(), total1.getFromTotal(),
          invertSort());
    }
    else
    {
      result = compareAmounts(total2.getToTotal(), total1.getToTotal(),
          invertSort());
    }

    return result;
  }
}
