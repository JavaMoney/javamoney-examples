// ImportTransactionComparator

package org.javamoney.examples.ez.money.gui.table.comparator;

import static org.javamoney.examples.ez.common.utility.CompareHelper.compareKeys;
import static org.javamoney.examples.ez.money.gui.table.ImportTransactionTable.AMOUNT_COLUMN;
import static org.javamoney.examples.ez.money.gui.table.ImportTransactionTable.CHECK_NUMBER_COLUMN;
import static org.javamoney.examples.ez.money.gui.table.ImportTransactionTable.DATE_COLUMN;
import static org.javamoney.examples.ez.money.gui.table.ImportTransactionTable.PAYEE_COLUMN;
import static org.javamoney.examples.ez.money.utility.TransactionCompareHelper.compareAmounts;
import static org.javamoney.examples.ez.money.utility.TransactionCompareHelper.compareCheckNumbers;
import static org.javamoney.examples.ez.money.utility.TransactionCompareHelper.compareDates;
import static org.javamoney.examples.ez.money.utility.TransactionCompareHelper.comparePayees;

import org.javamoney.examples.ez.money.model.dynamic.transaction.ImportTransaction;
import org.javamoney.examples.ez.money.model.persisted.transaction.Transaction;

import org.javamoney.examples.ez.common.gui.table.DataTableComparator;

/**
 * This class facilitates comparing import transactions in a table.
 */
public
final
class
ImportTransactionComparator
extends DataTableComparator<ImportTransaction>
{
  /**
   * Constructs a new comparator.
   */
  public
  ImportTransactionComparator()
  {
    super(DATE_COLUMN);
  }

  /**
   * This method compares two import transactions.
   *
   * @param iTrans1 A import transaction to compare.
   * @param iTrans2 A import transaction to compare.
   *
   * @return The result of comparing two import transactions.
   */
  @Override
  public
  int
  compare(ImportTransaction iTrans1, ImportTransaction iTrans2)
  {
    Transaction trans1 = iTrans1.getTransaction();
    Transaction trans2 = iTrans2.getTransaction();
    int result = 0;

    if(getColumn() == AMOUNT_COLUMN)
    {
      result = compareAmounts(trans1, trans2, invertSort());
    }
    else if(getColumn() == CHECK_NUMBER_COLUMN)
    {
      result = compareCheckNumbers(trans1, trans2, invertSort());
    }
    else if(getColumn() == DATE_COLUMN)
    {
      result = compareDates(trans1, trans2, invertSort());
    }
    else if(getColumn() == PAYEE_COLUMN)
    {
      result = comparePayees(trans1, trans2, invertSort());
    }
    else
    {
      result = compareKeys(iTrans1.getType(), iTrans2.getType(), invertSort());
    }

    return result;
  }
}
