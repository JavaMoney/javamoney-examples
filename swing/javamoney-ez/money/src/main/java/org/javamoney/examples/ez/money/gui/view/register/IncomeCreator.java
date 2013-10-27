// IncomeCreator

package org.javamoney.examples.ez.money.gui.view.register;

import static org.javamoney.examples.ez.money.model.dynamic.transaction.TransactionTypeKeys.INCOME;

import org.javamoney.examples.ez.money.model.persisted.transaction.Transaction;

/**
 * This class facilitates creating income transactions.
 */
final
class
IncomeCreator
extends TypeCreator
{
  /**
   * Constructs a new income creator.
   */
  protected
  IncomeCreator()
  {
    super(INCOME);
  }

  /**
   * This method creates and then adds the newly created transaction to its
   * account. It then returns the transaction if it was successfully added,
   * otherwise it returns null.
   *
   * @return The transaction if successfully added, otherwise null.
   */
  @Override
  protected
  Transaction
  createAndAdd()
  {
    return addTransaction(createTransaction(INCOME));
  }
}
