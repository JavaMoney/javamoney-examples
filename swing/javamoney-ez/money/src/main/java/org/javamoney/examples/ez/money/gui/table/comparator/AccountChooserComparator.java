// AccountChooserComparator

package org.javamoney.examples.ez.money.gui.table.comparator;

import static org.javamoney.examples.ez.common.utility.CompareHelper.compareKeys;
import static org.javamoney.examples.ez.common.utility.CompareHelper.compareObjects;
import static org.javamoney.examples.ez.money.gui.table.AccountChooserTable.ID_COLUMN;

import org.javamoney.examples.ez.money.model.persisted.account.Account;

import org.javamoney.examples.ez.common.gui.table.DataTableComparator;

/**
 * This class facilitates comparing accounts in a table.
 */
public
final
class
AccountChooserComparator
extends DataTableComparator<Account>
{
  /**
   * Constructs a new comparator.
   */
  public
  AccountChooserComparator()
  {
    super(ID_COLUMN);
  }

  /**
   * This method compares two accounts.
   *
   * @param account1 An account to compare.
   * @param account2 An account to compare.
   *
   * @return The result of comparing two accounts.
   */
  @Override
  public
  int
  compare(Account account1, Account account2)
  {
    int result = 0;

    if(getColumn() == ID_COLUMN)
    {
      result = compareObjects(account1, account2, invertSort());
    }
    else
    {
      result = compareKeys(account1.getType(), account2.getType(), invertSort());
    }

    return result;
  }
}
