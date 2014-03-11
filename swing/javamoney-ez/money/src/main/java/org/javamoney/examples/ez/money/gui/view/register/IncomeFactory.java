// IncomeCreator

package org.javamoney.examples.ez.money.gui.view.register;

import static org.javamoney.examples.ez.money.model.dynamic.transaction.TransactionTypeKeys.INCOME;

import org.javamoney.examples.ez.money.model.persisted.transaction.Transaction;

/**
 * This class facilitates creating income transactions.
 */
final
class
IncomeFactory
extends TransactionFactory
{
  /**
	 * 
	 */
	private static final long serialVersionUID = 8318425398525241074L;

/**
   * Constructs a new income creator.
   */
  protected
  IncomeFactory()
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
