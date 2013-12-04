// Transaction

package org.javamoney.examples.ez.money.model.persisted.transaction;

import static org.javamoney.examples.ez.common.utility.CompareHelper.compareAmounts;
import static org.javamoney.examples.ez.common.utility.CompareHelper.compareBooleans;

import java.util.Date;

import org.javamoney.moneta.Money;

/**
 * This class encompasses all the elements that make up a transaction.
 */
public
final
class
Transaction
implements Comparable<Transaction>
{
  /**
   * Constructs a new transaction.
   *
   * @param number The check number.
   * @param date The date.
   * @param payee The payee.
   * @param amount The amount.
   * @param category The category.
   * @param notes The notes.
   */
  public
  Transaction(String number, Date date, String payee, Money amount,
      String category, String notes)
  {
    setAmount(amount);
    setCategory(category);
    setCheckNumber(number);
    setDate(date);
    setIsReconciled(false);
    setLabel(LabelKeys.NONE);
    setNotes(notes);
    setPayee(payee);
    setUniqueKey("");
  }

  /**
   * This method creates an exact copy.
   *
   * @return An exact copy.
   */
  @Override
  public
  Transaction
  clone()
  {
    Transaction trans = new Transaction(getCheckNumber(), getDate(), getPayee(),
        getAmount(), getCategory(), getNotes());

    // Set attributes not applicable in the constructor.
    trans.setIsReconciled(isReconciled());
    trans.setLabel(getLabel());
    trans.setUniqueKey(getUniqueKey());

    return trans;
  }

  /**
   * This method returns the result of comparing the transaction with the passed
   * in transaction. Two transactions are considered equal if they have the
   * same:
   * <p>
   * <ul>
   * <li>Date</li>
   * <li>Check number</li>
   * <li>Amount</li>
   * <li>Category</li>
   * <li>Payee</li>
   * <li>Notes</li>
   * <li>Reconciled status</li>
   * <li>Unique key</li>
   * </ul>
   *
   * @param trans The transaction to compare to.
   *
   * @return The result of comparing the transaction with the passed in
   * transaction.
   */
  public
  int
  compareTo(Transaction trans)
  {
    int result = 0;

    if((result = getDate().compareTo(trans.getDate())) == 0)
    {
      if((result = getCheckNumber().compareTo(trans.getCheckNumber())) == 0)
      {
        if((result = compareAmounts(trans.getAmount(), getAmount())) == 0)
        {
          if((result = getCategory().compareTo(trans.getCategory())) == 0)
          {
            if((result = getPayee().compareTo(trans.getPayee())) == 0)
            {
              if((result = getNotes().compareTo(trans.getNotes())) == 0)
              {
                if((result = compareBooleans(isReconciled(), trans.isReconciled())) == 0)
                {
                  result = getUniqueKey().compareTo(trans.getUniqueKey());
                }
              }
            }
          }
        }
      }
    }

    return result;
  }

  /**
   * This method returns the amount.
   *
   * @return The amount.
   */
  public
  Money
  getAmount()
  {
    return itsAmount;
  }

  /**
   * This method returns the category.
   *
   * @return The category.
   */
  public
  String
  getCategory()
  {
    return itsCategory;
  }

  /**
   * This method returns the check number.
   *
   * @return The check number.
   */
  public
  String
  getCheckNumber()
  {
    return itsCheckNumber;
  }

  /**
   * This method returns the date.
   *
   * @return The date.
   */
  public
  Date
  getDate()
  {
    return itsDate;
  }

  /**
   * This method returns the label.
   *
   * @return The label.
   */
  public
  LabelKeys
  getLabel()
  {
    return itsLabel;
  }

  /**
   * This method returns the notes.
   *
   * @return The notes.
   */
  public
  String
  getNotes()
  {
    return itsNotes;
  }

  /**
   * This method returns the payee.
   *
   * @return The payee.
   */
  public
  String
  getPayee()
  {
    return itsPayee;
  }

  /**
   * This method returns true if the transaction is reconciled, otherwise
   * false.
   *
   * @return true or false.
   */
  public
  boolean
  isReconciled()
  {
    return itsIsReconciled;
  }

  /**
   * This method sets the amount.
   *
   * @param value The amount.
   */
  public
  void
  setAmount(Money value)
  {
    itsAmount = value;
  }

  /**
   * This method sets the category.
   *
   * @param category The category.
   */
  public
  void
  setCategory(String category)
  {
    itsCategory = category;
  }

  /**
   * This method sets the check number.
   *
   * @param number The check number.
   */
  public
  void
  setCheckNumber(String number)
  {
    itsCheckNumber = number;
  }

  /**
   * This method sets the date.
   *
   * @param date The date.
   */
  public
  void
  setDate(Date date)
  {
    itsDate = date;
  }

  /**
   * This method sets whether or not the transaction is reconciled.
   *
   * @param value true or false.
   */
  public
  void
  setIsReconciled(boolean value)
  {
    itsIsReconciled = value;
  }

  /**
   * This method sets the label.
   *
   * @param label The label.
   */
  public
  void
  setLabel(LabelKeys label)
  {
    itsLabel = label;
  }

  /**
   * This method sets the notes.
   *
   * @param notes The notes.
   */
  public
  void
  setNotes(String notes)
  {
    itsNotes = notes;
  }

  /**
   * This method sets the payee.
   *
   * @param payee The payee.
   */
  public
  void
  setPayee(String payee)
  {
    itsPayee = payee;
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of protected methods.
  //////////////////////////////////////////////////////////////////////////////

  /**
   * This method sets the unique key to the system's clock.
   */
  protected
  void
  makeUnique()
  {
    setUniqueKey("" + System.nanoTime());
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of private methods.
  //////////////////////////////////////////////////////////////////////////////

  private
  String
  getUniqueKey()
  {
    return itsUniqueKey;
  }

  private
  void
  setUniqueKey(String key)
  {
    itsUniqueKey = key;
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  private Money itsAmount;
  private String itsCategory;
  private String itsCheckNumber;
  private Date itsDate;
  private boolean itsIsReconciled;
  private LabelKeys itsLabel;
  private String itsNotes;
  private String itsPayee;
  private String itsUniqueKey;

  /**
   * The maximum length for the check number field.
   */
  public static final int MAX_CHECK_LENGTH = 10;
  /**
   * The maximum length for the notes field.
   */
  public static final int MAX_NOTES_LENGTH = 256;
  /**
   * The maximum length for the payee field.
   */
  public static final int MAX_PAYEE_LENGTH = 32;
}
