// ImportDuplicateComparator

package org.javamoney.examples.ez.money.gui.table.comparator;

import static org.javamoney.examples.ez.money.gui.table.ImportDuplicateTable.AMOUNT_COLUMN;
import static org.javamoney.examples.ez.money.gui.table.ImportDuplicateTable.CHECK_NUMBER_COLUMN;
import static org.javamoney.examples.ez.money.gui.table.ImportDuplicateTable.DATE_COLUMN;
import static org.javamoney.examples.ez.money.utility.TransactionCompareHelper.compareAmounts;
import static org.javamoney.examples.ez.money.utility.TransactionCompareHelper.compareCheckNumbers;
import static org.javamoney.examples.ez.money.utility.TransactionCompareHelper.compareDates;
import static org.javamoney.examples.ez.money.utility.TransactionCompareHelper.comparePayees;

import org.javamoney.examples.ez.money.model.persisted.transaction.Transaction;

import org.javamoney.examples.ez.common.gui.table.DataTableComparator;

/**
 * This class facilitates comparing transactions in a table.
 */
public
final
class
ImportDuplicateComparator
extends DataTableComparator<Transaction>
{
  /**
   * Constructs a new comparator.
   */
  public
  ImportDuplicateComparator()
  {
    super(DATE_COLUMN);
  }

  /**
   * This method compares two transactions.
   *
   * @param trans1 A transaction to compare.
   * @param trans2 A transaction to compare.
   *
   * @return The result of comparing two transactions.
   */
  @Override
  public
  int
  compare(Transaction trans1, Transaction trans2)
  {
    int result = 0;

    if(getColumn() == CHECK_NUMBER_COLUMN)
    {
      result = compareCheckNumbers(trans1, trans2, invertSort());
    }
    else if(getColumn() == DATE_COLUMN)
    {
      result = compareDates(trans1, trans2, invertSort());
    }
    else if(getColumn() == AMOUNT_COLUMN)
    {
      result = compareAmounts(trans1, trans2, invertSort());
    }
    else
    {
      result = comparePayees(trans1, trans2, invertSort());
    }

    return result;
  }
}
