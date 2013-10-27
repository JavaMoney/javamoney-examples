// TransactionDetailComparator

package org.javamoney.examples.ez.money.gui.table.comparator;

import static org.javamoney.examples.ez.common.utility.CompareHelper.compareObjects;
import static org.javamoney.examples.ez.money.gui.table.TransactionDetailTable.ACCOUNT_COLUMN;
import static org.javamoney.examples.ez.money.gui.table.TransactionDetailTable.AMOUNT_COLUMN;
import static org.javamoney.examples.ez.money.gui.table.TransactionDetailTable.DATE_COLUMN;
import static org.javamoney.examples.ez.money.utility.TransactionCompareHelper.compareAmounts;
import static org.javamoney.examples.ez.money.utility.TransactionCompareHelper.compareDates;
import static org.javamoney.examples.ez.money.utility.TransactionCompareHelper.comparePayees;

import org.javamoney.examples.ez.money.model.dynamic.total.TransactionDetail;
import org.javamoney.examples.ez.money.model.persisted.transaction.Transaction;

import org.javamoney.examples.ez.common.gui.table.DataTableComparator;

/**
 * This class facilitates comparing transaction details in a table.
 */
public
final
class
TransactionDetailComparator
extends DataTableComparator<TransactionDetail>
{
  /**
   * Constructs a new comparator.
   */
  public
  TransactionDetailComparator()
  {
    super(DATE_COLUMN);
  }

  /**
   * This method compares two transaction details.
   *
   * @param detail1 A transaction detail to compare.
   * @param detail2 A transaction detail to compare.
   *
   * @return The result of comparing two transaction details.
   */
  @Override
  public
  int
  compare(TransactionDetail detail1, TransactionDetail detail2)
  {
    Transaction trans1 = detail1.getTransaction();
    Transaction trans2 = detail2.getTransaction();
    int result = 0;

    if(getColumn() == ACCOUNT_COLUMN)
    {
      result = compareObjects(detail1.getAccount(), detail2.getAccount(), invertSort());
    }
    else if(getColumn() == AMOUNT_COLUMN)
    {
      result = compareAmounts(trans1, trans2, invertSort());
    }
    else if(getColumn() == DATE_COLUMN)
    {
      result = compareDates(trans1, trans2, invertSort());
    }
    else
    {
      result = comparePayees(trans1, trans2, invertSort());
    }

    return result;
  }
}
