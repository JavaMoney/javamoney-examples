// Total

package org.javamoney.examples.ez.money.model.dynamic.total;

import java.util.Collection;
import java.util.LinkedList;

import org.javamoney.examples.ez.money.model.DataElement;

/**
 * This class facilitates tracking a group of related transactions.
 */
class
Total
extends DataElement
{
  /**
   * This method returns a list of transaction details.
   *
   * @return A list of transaction details.
   */
  public
  final
  Collection<TransactionDetail>
  getTransactionDetails()
  {
    return itsTransactionDetails;
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of protected methods.
  //////////////////////////////////////////////////////////////////////////////

  /**
   * Constructs a new total.
   *
   * @param identifier The identifier.
   */
  protected
  Total(String identifier)
  {
    super(identifier);

    setTransactionDetails(new LinkedList<TransactionDetail>());
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of private methods.
  //////////////////////////////////////////////////////////////////////////////

  private
  void
  setTransactionDetails(Collection<TransactionDetail> collection)
  {
    itsTransactionDetails = collection;
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  private Collection<TransactionDetail> itsTransactionDetails;
}
