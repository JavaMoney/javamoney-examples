// RegisterComparator

package org.javamoney.examples.ez.money.gui.table.comparator;

import static org.javamoney.examples.ez.common.utility.CompareHelper.compareAmounts;
import static org.javamoney.examples.ez.common.utility.CompareHelper.compareBooleans;
import static org.javamoney.examples.ez.money.gui.table.RegisterTable.BALANCE_COLUMN;
import static org.javamoney.examples.ez.money.gui.table.RegisterTable.CHECK_NUMBER_COLUMN;
import static org.javamoney.examples.ez.money.gui.table.RegisterTable.DATE_COLUMN;
import static org.javamoney.examples.ez.money.gui.table.RegisterTable.EXPENSE_COLUMN;
import static org.javamoney.examples.ez.money.gui.table.RegisterTable.INCOME_COLUMN;
import static org.javamoney.examples.ez.money.gui.table.RegisterTable.LABEL_COLUMN;
import static org.javamoney.examples.ez.money.gui.table.RegisterTable.PAYEE_COLUMN;
import static org.javamoney.examples.ez.money.utility.TransactionCompareHelper.compareAmounts;
import static org.javamoney.examples.ez.money.utility.TransactionCompareHelper.compareCheckNumbers;
import static org.javamoney.examples.ez.money.utility.TransactionCompareHelper.compareDates;
import static org.javamoney.examples.ez.money.utility.TransactionCompareHelper.compareLabels;
import static org.javamoney.examples.ez.money.utility.TransactionCompareHelper.comparePayees;

import org.javamoney.examples.ez.money.ApplicationProperties;
import org.javamoney.examples.ez.money.model.dynamic.transaction.RegisterTransaction;
import org.javamoney.examples.ez.money.model.persisted.transaction.Transaction;

import org.javamoney.examples.ez.common.gui.table.DataTableComparator;

/**
 * This class facilitates comparing transactions in a table.
 */
public
final
class
RegisterComparator
extends DataTableComparator<RegisterTransaction>
{
  /**
   * Constructs a new comparator.
   */
  public
  RegisterComparator()
  {
    super(ApplicationProperties.getRegisterColumnToSort());

    setInvertSort(ApplicationProperties.invertSortForRegister());
  }

  /**
   * This method compares two transactions.
   *
   * @param rTrans1 A transaction to compare.
   * @param rTrans2 A transaction to compare.
   *
   * @return The result of comparing two transactions.
   */
  @Override
  public
  int
  compare(RegisterTransaction rTrans1, RegisterTransaction rTrans2)
  {
    Transaction trans1 = rTrans1.getTransaction();
    Transaction trans2 = rTrans2.getTransaction();
    int result = 0;

    if(getColumn() == BALANCE_COLUMN)
    {
      result = compareAmounts(rTrans2.getBalance(), rTrans1.getBalance(), invertSort());
    }
    else if(getColumn() == CHECK_NUMBER_COLUMN)
    {
      result = compareCheckNumbers(trans1, trans2, invertSort());
    }
    else if(getColumn() == DATE_COLUMN)
    {
      result = compareDates(trans1, trans2, invertSort());
    }
    else if(getColumn() == EXPENSE_COLUMN)
    {
      result = compareAmounts(trans1, trans2, invertSort());
    }
    else if(getColumn() == INCOME_COLUMN)
    {
      result = compareAmounts(trans2, trans1, invertSort());
    }
    else if(getColumn() == LABEL_COLUMN)
    {
      result = compareLabels(trans2, trans1, invertSort());
    }
    else if(getColumn() == PAYEE_COLUMN)
    {
      result = comparePayees(trans1, trans2, invertSort());
    }
    else
    {
      result = compareBooleans(trans2.isReconciled(), trans1.isReconciled(), invertSort());
    }

    return result;
  }
}
