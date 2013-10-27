// TransactionCompareHelper

package org.javamoney.examples.ez.money.utility;

import org.javamoney.examples.ez.money.model.persisted.transaction.Transaction;

import org.javamoney.examples.ez.common.utility.CompareHelper;

/**
 * This class provides convenience methods for comparing. All methods in this
 * class are static.
 */
public
final
class
TransactionCompareHelper
{

  /**
   * This method compares two transaction's amounts.
   *
   * @param trans1 A transaction to compare.
   * @param trans2 A transaction to compare.
   * @param invert Whether or not to invert the sort.
   *
   * @return The result of comparing two transaction's amounts.
   */
  public
  static
  int
  compareAmounts(Transaction trans1, Transaction trans2, boolean invert)
  {
    return CompareHelper.compareAmounts(trans1.getAmount(), trans2.getAmount(), invert);
  }

  /**
   * This method compares two transaction's check numbers.
   *
   * @param trans1 A transaction to compare.
   * @param trans2 A transaction to compare.
   * @param invert Whether or not to invert the sort.
   *
   * @return The result of comparing two transaction's check numbers.
   */
  public
  static
  int
  compareCheckNumbers(Transaction trans1, Transaction trans2, boolean invert)
  {
    return CompareHelper.compareStrings(trans1.getCheckNumber(), trans2.getCheckNumber(), invert);
  }

  /**
   * This method compares two transaction's dates.
   *
   * @param trans1 A transaction to compare.
   * @param trans2 A transaction to compare.
   * @param invert Whether or not to invert the sort.
   *
   * @return The result of comparing two transaction's dates.
   */
  public
  static
  int
  compareDates(Transaction trans1, Transaction trans2, boolean invert)
  {
    return CompareHelper.compareDates(trans1.getDate(), trans2.getDate(), invert);
  }

  /**
   * This method compares two transaction's labels.
   *
   * @param trans1 A transaction to compare.
   * @param trans2 A transaction to compare.
   * @param invert Whether or not to invert the sort.
   *
   * @return The result of comparing two transaction's labels.
   */
  public
  static
  int
  compareLabels(Transaction trans1, Transaction trans2, boolean invert)
  {
    return CompareHelper.compareKeys(trans1.getLabel(), trans2.getLabel(), invert);
  }

  /**
   * This method compares two transaction's payees.
   *
   * @param trans1 A transaction to compare.
   * @param trans2 A transaction to compare.
   * @param invert Whether or not to invert the sort.
   *
   * @return The result of comparing two transaction's payees.
   */
  public
  static
  int
  comparePayees(Transaction trans1, Transaction trans2, boolean invert)
  {
    return CompareHelper.compareStrings(trans1.getPayee(), trans2.getPayee(), invert);
  }
}
