// QIFTransactionExtracter

package org.javamoney.examples.ez.money.importexport;

import static org.javamoney.examples.ez.money.ApplicationProperties.UI_CURRENCY_SYMBOL;
import static org.javamoney.examples.ez.money.importexport.QIFConstants.ACCOUNT_BALANCE;
import static org.javamoney.examples.ez.money.importexport.QIFConstants.ACCOUNT_HEADER;
import static org.javamoney.examples.ez.money.importexport.QIFConstants.ACCOUNT_NAME;
import static org.javamoney.examples.ez.money.importexport.QIFConstants.ACCOUNT_TYPE;
import static org.javamoney.examples.ez.money.importexport.QIFConstants.ACCOUNT_TYPE_CASH;
import static org.javamoney.examples.ez.money.importexport.QIFConstants.ACCOUNT_TYPE_CREDIT;
import static org.javamoney.examples.ez.money.importexport.QIFConstants.AMOUNT;
import static org.javamoney.examples.ez.money.importexport.QIFConstants.AMOUNT_IN_SPLIT;
import static org.javamoney.examples.ez.money.importexport.QIFConstants.CATEGORY;
import static org.javamoney.examples.ez.money.importexport.QIFConstants.CATEGORY_IN_SPLIT;
import static org.javamoney.examples.ez.money.importexport.QIFConstants.CHECK_NUMBER;
import static org.javamoney.examples.ez.money.importexport.QIFConstants.CLEARED;
import static org.javamoney.examples.ez.money.importexport.QIFConstants.CLEARED_STATUS;
import static org.javamoney.examples.ez.money.importexport.QIFConstants.DATE;
import static org.javamoney.examples.ez.money.importexport.QIFConstants.END_OF_ENTRY;
import static org.javamoney.examples.ez.money.importexport.QIFConstants.NEW_TYPE;
import static org.javamoney.examples.ez.money.importexport.QIFConstants.NOTES;
import static org.javamoney.examples.ez.money.importexport.QIFConstants.PAYEE;
import static org.javamoney.examples.ez.money.importexport.QIFConstants.TRANSACTION_TYPE_CASH;
import static org.javamoney.examples.ez.money.importexport.QIFConstants.TRANSACTION_TYPE_CREDIT;
import static org.javamoney.examples.ez.money.model.dynamic.transaction.Split.AMOUNT_SEPARATOR;
import static org.javamoney.examples.ez.money.model.dynamic.transaction.Split.ITEM_SEPARATOR;
import static org.javamoney.examples.ez.money.model.dynamic.transaction.TransactionTypeKeys.EXPENSE;
import static org.javamoney.examples.ez.money.model.dynamic.transaction.TransactionTypeKeys.INCOME;
import static org.javamoney.examples.ez.money.model.dynamic.transaction.TransactionTypeKeys.TRANSFER;
import static org.javamoney.examples.ez.money.utility.TransactionHelper.isExpense;
import static org.javamoney.examples.ez.money.utility.TransactionHelper.isSplit;

import java.io.BufferedReader;
import java.util.Date;

import org.javamoney.examples.ez.money.model.persisted.account.AccountTypeKeys;
import org.javamoney.examples.ez.money.model.persisted.transaction.Transaction;
import org.javamoney.moneta.Money;

/**
 * This class facilitates extracting transactions from a QIF file.
 */
final
class
QIFTransactionExtracter
extends TransactionExtracter
{
  /**
   * Constructs a new transaction extractor.
   */
  protected
  QIFTransactionExtracter()
  {
    setAccountBalance(Money.of(UI_CURRENCY_SYMBOL.getCurrency(), Double.valueOf(0)));
    setAccountKey(null);
    setAccountUID(null);
  }

  /**
   * This method returns the account's balance.
   *
   * @return The account's balance.
   */
  protected
  Money
  getAccountBalance()
  {
    return itsAccountBalance;
  }

  /**
   * This method returns the account's unique identifier for importing the
   * transactions into, or null if one was not specified in the file.
   *
   * @return The account's key or null if one was not specified in the file.
   */
  protected
  AccountTypeKeys
  getAccountKey()
  {
    return itsAccountKey;
  }

  /**
   * This method returns the account's unique identifier for importing the
   * transactions into, or null if one was not specified in the file.
   *
   * @return The account's unique identifier or null if one was not specified in
   * the file.
   */
  protected
  String
  getAccountUID()
  {
    return itsAccountUID;
  }

  /**
   * This method returns true if an account for importing the transactions into
   * has been specified from within the file, otherwise false.
   *
   * @return true or false.
   */
  protected
  boolean
  hasAccountForImport()
  {
    return getAccountUID() != null && getAccountKey() != null;
  }

  /**
   * This method extracts and returns the next transaction from the stream. If
   * there are no more transactions in the stream, then null is returned.
   *
   * @param stream The input stream that has the transactions.
   *
   * @return The next transaction or null if there are no more transactions in
   * the stream.
   *
   * @throws Exception If an error occurs.
   */
  @Override
  protected
  Transaction
  next(BufferedReader stream)
  throws Exception
  {
    Transaction trans = null;
    String category = "";
    Date date = new Date();
    String line = "";
    String notes = "";
    String number = "";
    String payee = "";
    double amount = 0.0;
    boolean reconciled = false;
    char key = 0;

    setType(null);

    while((line = stream.readLine()) != null)
    {
      // Skip over any blank lines.
      if(line.length() == 0)
      {
        continue;
      }

      key = Character.toUpperCase(line.charAt(0));

      // Remove the key from the line.
      line = line.substring(1);

      if(key == END_OF_ENTRY)
      {
        break;
      }
      else if(key == NEW_TYPE)
      {
        if(line.equalsIgnoreCase(ACCOUNT_HEADER) == true)
        {
          extractAccountInfo(stream);
          break;
        }
      }
      else if(key == AMOUNT_IN_SPLIT)
      {
        category += extractAmountForSplit(line);
      }
      else if(key == AMOUNT)
      {
        amount = extractAmount(line);

        // Don't change the type if it is already a transfer.
        if(getType() == null)
        {
          if(isExpense(amount) == true)
          {
            setType(EXPENSE);
          }
          else
          {
            setType(INCOME);
          }
        }
      }
      else if(key == CATEGORY)
      {
        // Can be either a category or an account.
        if(isAccount(line) == true)
        {
          payee = extractAccount(line);
          setType(TRANSFER);
        }
        else
        {
          category = extractCategory(line);
        }
      }
      else if(key == CATEGORY_IN_SPLIT)
      {
        // If the category isn't already formatted to be a split, then reset it.
        if(isSplit(category) == false)
        {
          category = "";
        }

        category += ITEM_SEPARATOR + extractCategory(line) + AMOUNT_SEPARATOR;
      }
      else if(key == CHECK_NUMBER)
      {
        number = extractCheckNumber(line);
      }
      else if(key == DATE)
      {
        date = extractDate(line);
      }
      else if(key == NOTES)
      {
        notes = extractNotes(line);
      }
      else if(key == PAYEE)
      {
        payee = extractPayee(line);
      }
      else if(key == CLEARED_STATUS)
      {
        reconciled = line.equals(CLEARED);
      }
      else
      {
        // Ignored.
      }
    }

    if(line != null)
    {
      // Ensure a split ends in a money value.
      if(category.endsWith(AMOUNT_SEPARATOR) == true)
      {
        category += getCurrency().format(0, false);
      }

      // Create the transaction.
      trans = new Transaction(number, date, payee, Money.of(UI_CURRENCY_SYMBOL.getCurrency(), amount), category, notes);
      trans.setIsReconciled(reconciled);
    }

    return trans;
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of private methods.
  //////////////////////////////////////////////////////////////////////////////

  private
  void
  extractAccountInfo(BufferedReader stream)
  throws Exception
  {
    String line = "";
    char key;

    while((line = stream.readLine()) != null)
    {
      // Skip over any blank lines.
      if(line.length() == 0)
      {
        continue;
      }

      key = Character.toUpperCase(line.charAt(0));

      // Remove the key from the line.
      line = line.substring(1);

      if(key == END_OF_ENTRY)
      {
        break;
      }
      else if(key == ACCOUNT_BALANCE)
      {
        setAccountBalance(Money.of(UI_CURRENCY_SYMBOL.getCurrency(), extractAmount(line)));
      }
      else if(key == ACCOUNT_NAME)
      {
        setAccountUID(extractPayee(line));
      }
      else if(key == ACCOUNT_TYPE)
      {
        setAccountKey(extractAccountKey(line));
      }
      else
      {
        // Ignored.
      }
    }
  }

  private
  static
  AccountTypeKeys
  extractAccountKey(String line)
  {
    AccountTypeKeys type = null;

    if(line.equals(ACCOUNT_TYPE_CASH) == true ||
        line.equals(TRANSACTION_TYPE_CASH) == true)
    {
      type = AccountTypeKeys.CASH;
    }
    else if(line.equals(ACCOUNT_TYPE_CREDIT) == true ||
        line.equals(TRANSACTION_TYPE_CREDIT) == true)
    {
      type = AccountTypeKeys.CREDIT;
    }
    else
    {
      type = AccountTypeKeys.DEPOSIT;
    }

    return type;
  }

  private
  void
  setAccountBalance(Money balance)
  {
    itsAccountBalance = balance;
  }

  private
  void
  setAccountKey(AccountTypeKeys key)
  {
    itsAccountKey = key;
  }

  private
  void
  setAccountUID(String uid)
  {
    itsAccountUID = uid;
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  private Money itsAccountBalance;
  private String itsAccountUID;
  private AccountTypeKeys itsAccountKey;
}
