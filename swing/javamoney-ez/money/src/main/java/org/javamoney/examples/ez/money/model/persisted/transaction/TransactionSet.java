// TransactionSet

package org.javamoney.examples.ez.money.model.persisted.transaction;

import java.util.TreeSet;

/**
 * This class facilitates providing a set specifically for containing
 * transactions.
 */
public
final
class
TransactionSet
extends TreeSet<Transaction>
{
  /**
   * This method adds the specified transaction and then returns the result of
   * the operation. If the operation fails, mostly likely because the
   * transaction is already contained in the set, this method makes the
   * transaction unique and tries a second time.
   *
   * @param trans The transaction to add.
   *
   * @return true or false.
   */
  @Override
  public
  boolean
  add(Transaction trans)
  {
    boolean result = super.add(trans);

    // The transaction may be a duplicate. Duplicates are allowed but each
    // transaction needs to be unique.
    if(result == false)
    {
      trans.makeUnique();
      result = super.add(trans);
    }

    return result;
  }
}
