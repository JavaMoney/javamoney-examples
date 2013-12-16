// TransactionHelper

package org.javamoney.examples.ez.money.utility;

import static org.javamoney.examples.ez.money.KeywordKeys.TRANSFER_FROM;
import static org.javamoney.examples.ez.money.KeywordKeys.TRANSFER_TO;
import static org.javamoney.examples.ez.money.importexport.QIFConstants.CATEGORY_SEPARATOR_CHAR;
import static org.javamoney.examples.ez.money.model.DataManager.getAccounts;
import static org.javamoney.examples.ez.money.utility.DialogHelper.error;

import javax.money.MonetaryAmount;

import org.javamoney.examples.ez.money.locale.CurrencyFormat;
import org.javamoney.examples.ez.money.locale.CurrencyFormatKeys;
import org.javamoney.examples.ez.money.model.DataElement;
import org.javamoney.examples.ez.money.model.dynamic.transaction.Split;
import org.javamoney.examples.ez.money.model.persisted.account.Account;
import org.javamoney.examples.ez.money.model.persisted.account.AccountCollection;
import org.javamoney.examples.ez.money.model.persisted.transaction.Transaction;
import org.javamoney.examples.ez.common.utility.I18NHelper;

/**
 * This class provides convenience methods for dealing with transactions. All
 * methods in this class are static.
 */
public
final
class
TransactionHelper
{
  /**
   * This method creates a new transfer transaction that will correspond to the
   * passed in transaction and will have the specified payee.
   *
   * @param trans The transaction the transfer will correspond to.
   * @param payee The account that either received or sent the transfer.
   *
   * @return A transfer transaction tailored for the recipient account.
   */
  public
  static
  Transaction
  createCorrespondingTransfer(Transaction trans, Account payee)
  {
    String category = null;

    if(trans.getCategory().equals(TRANSFER_TO.toString()) == true)
    {
      category = TRANSFER_FROM.toString();
    }
    else
    {
      category = TRANSFER_TO.toString();
    }

    trans = trans.clone();
    trans.setAmount(trans.getAmount().negate());
    trans.setCategory(category);
    trans.setPayee(payee.getIdentifier());

    return trans;
  }

  /**
   * This method returns the reference to the transaction that will correspond
   * to the passed in transaction and will have the specified payee, or null if
   * the passed in transaction is not a transfer, or the payee no longer exists.
   *
   * @param trans The transaction to create a recipient transfer with.
   * @param payee The account that either received or sent the transfer.
   *
   * @return The reference to the transaction that corresponds to the passed in
   * transaction
   */
  public
  static
  Transaction
  getCorrespondingTransferReference(Transaction trans, Account payee)
  {
    Transaction transAt = null;

    if(isTransfer(trans) == true)
    {
      Account account = (Account)getAccounts().get(trans.getPayee());

      if(account != null)
      {
        trans = createCorrespondingTransfer(trans, payee);

        // Iterate all transactions until a match is found.
        for(Transaction nextTrans : account.getTransactions())
        {
          if(nextTrans.compareTo(trans) == 0)
          {
            transAt = nextTrans;
            break;
          }
        }
      }
    }

    return transAt;
  }

  /**
   * This method returns true if the amount is considered an expense, otherwise
   * false.
   *
   * @param amount The amount to check.
   *
   * @return true or false.
   */
  public
  static
  boolean
  isExpense(double amount)
  {
    return amount <= 0.0;
  }
  
  /**
   * This method returns true if the amount is considered an expense, otherwise
   * false.
   *
   * @param amount The amount to check.
   *
   * @return true or false.
   */
  public
  static
  boolean
  isExpense(MonetaryAmount amount) {
	  return isExpense(amount.getNumber().doubleValue());
  }

  /**
   * This method returns true if the transaction is considered an expense,
   * otherwise false.
   *
   * @param trans The transaction to check.
   *
   * @return true or false.
   */
  public
  static
  boolean
  isExpense(Transaction trans)
  {
    return isExpense(trans.getAmount());
  }

  /**
   * This method returns true if the amount is considered an income, otherwise
   * false.
   *
   * @param amount The amount to check.
   *
   * @return true or false.
   */
  public
  static
  boolean
  isIncome(double amount)
  {
    return isExpense(amount) == false;
  }
  
  /**
   * This method returns true if the amount is considered an income, otherwise
   * false.
   *
   * @param amount The amount to check.
   *
   * @return true or false.
   */
  public
  static
  boolean
  isIncome(MonetaryAmount amount)
  {
	  return isIncome(amount.getNumber().doubleValue());
  }

  /**
   * This method returns true if the transaction is considered an income,
   * otherwise false.
   *
   * @param trans The transaction to check.
   *
   * @return true or false.
   */
  public
  static
  boolean
  isIncome(Transaction trans)
  {
    return isIncome(trans.getAmount());
  }

  /**
   * This method returns true if the category is a split, otherwise false.
   *
   * @param category The category to check.
   *
   * @return true or false.
   */
  public
  static
  boolean
  isSplit(String category)
  {
    return category.length() != 0 && category.charAt(0) == Split.ITEM_SEPARATOR_CHAR;
  }

  /**
   * This method returns true if the transaction has a split, otherwise false.
   *
   * @param trans The transaction to check.
   *
   * @return true or false.
   */
  public
  static
  boolean
  isSplit(Transaction trans)
  {
    return isSplit(trans.getCategory());
  }

  /**
   * This method returns true if the category is a transfer, otherwise false.
   *
   * @param category The category to check.
   *
   * @return true or false.
   */
  public
  static
  boolean
  isTransfer(String category)
  {
    boolean result = false;

    // All transactions that are transfers have their category start with a '['.
    if(category.length() != 0 && category.charAt(0) == '[')
    {
      result = true;
    }

    return result;
  }

  /**
   * This method returns true if the transaction is a transfer, otherwise false.
   *
   * @param trans The transaction to check.
   *
   * @return true or false.
   */
  public
  static
  boolean
  isTransfer(Transaction trans)
  {
    return isTransfer(trans.getCategory());
  }

  /**
   * This method iterates over all the transactions for all the accounts,
   * replacing the old value with the new value when it occurs in the specified
   * field.
   *
   * @param key The field to mass update.
   * @param oldValue The value to look for.
   * @param newValue The value to change the old one with.
   */
  public
  static
  void
  massUpdate(MassUpdateFieldKeys key, String oldValue, String newValue)
  {
    AccountCollection collection = getAccounts();

    // Iterate all accounts.
    for(DataElement account : collection.getCollection())
    {
      massUpdateFor((Account)account, key, oldValue, newValue);
    }
  }

  /**
   * This method removes the transaction from its account. If the transaction is
   * a transfer, then the corresponding transaction is also removed.
   *
   * @param trans The transaction to remove.
   */
  public
  static
  void
  removeFrom(Account account, Transaction trans)
  {
    if(account.removeTransaction(trans) == true)
    {
      // Apply to transfer if applicable.
      if(isTransfer(trans) == true)
      {
        Account recipient = (Account)getAccounts().get(trans.getPayee());

        if(recipient != null)
        {
          recipient.removeTransaction(createCorrespondingTransfer(trans, account));
        }
      }
    }
    else
    {
      error(getProperty("remove.title"),
          getProperty("remove.description") + " \"" + account.getIdentifier() + "\".");
    }
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of private methods.
  //////////////////////////////////////////////////////////////////////////////

  private
  static
  String
  getProperty(String key)
  {
    return I18NHelper.getProperty("TransactionHelper." + key);
  }

  /*
   * This method iterates over all the transactions for the specified account,
   * replacing the old value with the new value when it occurs in the specified
   * field.
   */
  private
  static
  void
  massUpdateFor(Account account, MassUpdateFieldKeys key, String oldValue, String newValue)
  {
    for(Transaction trans : account.getTransactions())
    {
      if(key == MassUpdateFieldKeys.ACCOUNT && isTransfer(trans) == true)
      {
        // For transfers, the payee is the account.
        if(trans.getPayee().equals(oldValue) == true)
        {
          trans.setPayee(newValue);
        }
      }
      else if(key == MassUpdateFieldKeys.PAYEE && isTransfer(trans) == false)
      {
        if(trans.getPayee().equals(oldValue) == true)
        {
          trans.setPayee(newValue);
        }
      }
      else if(key == MassUpdateFieldKeys.EXPENSE && isExpense(trans) == true)
      {
        if(isSplit(trans) == true)
        {
          updateSplit(trans, oldValue, newValue);
        }
        else
        {
          String category = updateCategory(trans.getCategory(), oldValue, newValue);

          trans.setCategory(category);
        }
      }
      else if(key == MassUpdateFieldKeys.INCOME && isIncome(trans) == true)
      {
        if(isSplit(trans) == true)
        {
          updateSplit(trans, oldValue, newValue);
        }
        else
        {
          String category = updateCategory(trans.getCategory(), oldValue, newValue);

          trans.setCategory(category);
        }
      }
    }
  }

  /*
   * This method updates the specified category if it equals the old value or
   * the old value is apart of the category's group. If neither applies, then
   * the category is returned unchanged. 1 of 2 things needs to be true to
   * update the category. Either the category equals the old value, or the
   * category starts with the old value including the category separator.
   */
  private
  static
  String
  updateCategory(String category, String oldValue, String newValue)
  {
    if(category.startsWith(oldValue) == true)
    {
      int len = oldValue.length();
      char separator = CATEGORY_SEPARATOR_CHAR;

      //  Make sure they match or the next character is the category separator.
      if(category.length() == len || category.charAt(len) == separator)
      {
        if(newValue.length() == 0)
        {
          category = newValue;
        }
        else
        {
          category = newValue + category.substring(len);
        }
      }
    }

    return category;
  }

  private
  static
  void
  updateSplit(Transaction trans, String oldSplit, String newSplit)
  {
    Split split = new Split(trans);
    CurrencyFormat dollar = CurrencyFormatKeys.US_DOLLAR.getFormat();
    String category = "";

    for(int index = 0; index < split.size(); ++index)
    {
      category += Split.ITEM_SEPARATOR + updateCategory(split.getCategory(index),
          oldSplit, newSplit);

      // Amounts in splits are always positive.
      category += Split.AMOUNT_SEPARATOR + dollar.format(Math.abs(split.getAmount(index)));
    }

    trans.setCategory(category);
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of inner classes.
  //////////////////////////////////////////////////////////////////////////////

  /**
   * This enumerated class provides keys for transaction fields that are mass
   * updatable.
   */
  public
  enum
  MassUpdateFieldKeys
  {
    /**
     * The transaction's field that contains the account.
     */
    ACCOUNT,
    /**
     * The transaction's field that contains the amount that is below zero
     * inclusive.
     */
    EXPENSE,
    /**
     * The transaction's field that contains the amount that is above zero
     * exclusive.
     */
    INCOME,
    /**
     * The transaction's field that contains the payee that is not a transfer.
     */
    PAYEE;
  }
}
