// TransactionDetail

package org.javamoney.examples.ez.money.model.dynamic.total;

import org.javamoney.examples.ez.money.model.persisted.account.Account;
import org.javamoney.examples.ez.money.model.persisted.transaction.Transaction;

/**
 * This class facilitates managing a transaction and the account the transaction
 * came from.
 */
public
final
class
TransactionDetail
{
  /**
   * Constructs a new transaction detail.
   *
   * @param trans The transaction to reference.
   * @param account The account the transaction came from.
   */
  public
  TransactionDetail(Transaction trans, Account account)
  {
    setAccount(account);
    setTransaction(trans);
  }

  /**
   * This method returns the account.
   *
   * @return The account.
   */
  public
  Account
  getAccount()
  {
    return itsAccount;
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

  //////////////////////////////////////////////////////////////////////////////
  // Start of private methods.
  //////////////////////////////////////////////////////////////////////////////

  private
  void
  setAccount(Account account)
  {
    itsAccount = account;
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

  private Account itsAccount;
  private Transaction itsTransaction;
}
