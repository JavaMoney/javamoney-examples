// ImportTransaction

package org.javamoney.examples.ez.money.model.dynamic.transaction;

import java.util.LinkedList;

import org.javamoney.examples.ez.money.model.dynamic.SelectableElement;
import org.javamoney.examples.ez.money.model.persisted.transaction.Transaction;

/**
 * This class facilitates maintaining data pertaining to a transaction as it is
 * being imported.
 */
public
final
class
ImportTransaction
extends SelectableElement
{
  /**
   * Constructs a new import transaction.
   *
   * @param trans The transaction to maintain a selected state for.
   * @param type The type.
   */
  public
  ImportTransaction(Transaction trans, TransactionTypeKeys type)
  {
    super("");

    setDuplicates(new LinkedList<Transaction>());
    setIsSelected(true);
    setTransaction(trans);
    setType(type);
  }

  /**
   * This method returns the list of possible duplicates.
   *
   * @return The list of possible duplicates.
   */
  public
  LinkedList<Transaction>
  getDuplicates()
  {
    return itsDuplicates;
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
   * This method returns the type.
   *
   * @return The type.
   */
  public
  TransactionTypeKeys
  getType()
  {
    return itsType;
  }

  /**
   * This method returns true if there are any possible duplicates, otherwise
   * false.
   *
   * @return true or false.
   */
  public
  boolean
  hasDuplicates()
  {
    return getDuplicates().size() != 0;
  }

  /**
   * This method returns true if the transaction is a transfer, otherwise false.
   *
   * @return true or false.
   */
  public
  boolean
  isTransfer()
  {
    return getType() == TransactionTypeKeys.TRANSFER;
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of private methods.
  //////////////////////////////////////////////////////////////////////////////

  private
  void
  setDuplicates(LinkedList<Transaction> list)
  {
    itsDuplicates = list;
  }

  private
  void
  setTransaction(Transaction trans)
  {
    itsTransaction = trans;
  }

  private
  void
  setType(TransactionTypeKeys type)
  {
    itsType = type;
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  private LinkedList<Transaction> itsDuplicates;
  private Transaction itsTransaction;
  private TransactionTypeKeys itsType;
}
