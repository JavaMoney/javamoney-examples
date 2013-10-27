// RegisterTransaction

package org.javamoney.examples.ez.money.model.dynamic.transaction;

import org.javamoney.examples.ez.money.model.persisted.transaction.Transaction;

/**
 * This class is a workaround to allow the register to provide a balance column.
 */
public
final
class
RegisterTransaction
{
  /**
   * Constructs a new register transaction.
   *
   * @param trans The transaction to reference.
   * @param amount The total amount at the time the transaction was added.
   */
  public
  RegisterTransaction(Transaction trans, double amount)
  {
    setTotal(amount);
    setTransaction(trans);
  }

  /**
   * This method returns the balance of the account after the transaction had
   * been added.
   *
   * @return The balance of the account after the transaction had been added.
   */
  public
  double
  getBalance()
  {
    return (getTotal() + getStartingBalance()) * getMultiplier();
  }

  /**
   * This method returns the transaction.
   *
   * @return The transaction.
   */
  public
  Transaction
  getTransaction()
  {
    return itsTransaction;
  }

  /**
   * This method sets the multiplier. The value should be 1 or -1 and is a
   * workaround to correctly display balances for credit cards according to the
   * user preference.
   *
   * @param value The multiplier.
   */
  public
  static
  void
  setMultiplier(int value)
  {
    itsMultiplier = value;
  }

  /**
   * This method sets the starting balance of the account that the transactions
   * originated from.
   *
   * @param amount The starting balance.
   */
  public
  static
  void
  setStartingBalance(double amount)
  {
    itsStartingBalance = amount;
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of private methods.
  //////////////////////////////////////////////////////////////////////////////

  private
  static
  int
  getMultiplier()
  {
    return itsMultiplier;
  }

  private
  static
  double
  getStartingBalance()
  {
    return itsStartingBalance;
  }

  private
  double
  getTotal()
  {
    return itsTotal;
  }

  private
  void
  setTotal(double total)
  {
    itsTotal = total;
  }

  private
  void
  setTransaction(Transaction trans)
  {
    itsTransaction = trans;
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  private static int itsMultiplier;
  private static double itsStartingBalance;
  private double itsTotal;
  private Transaction itsTransaction;
}
