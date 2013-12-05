// OFXTransactionExtracter

package org.javamoney.examples.ez.money.importexport;

import static org.javamoney.examples.ez.money.ApplicationProperties.UI_CURRENCY_SYMBOL;
import static org.javamoney.examples.ez.money.model.dynamic.transaction.TransactionTypeKeys.EXPENSE;
import static org.javamoney.examples.ez.money.model.dynamic.transaction.TransactionTypeKeys.INCOME;
import static org.javamoney.examples.ez.money.utility.TransactionHelper.isExpense;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PushbackReader;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.javamoney.examples.ez.money.locale.CurrencyFormat;
import org.javamoney.examples.ez.money.locale.CurrencyFormatKeys;
import org.javamoney.examples.ez.money.model.persisted.transaction.Transaction;
import org.javamoney.moneta.Money;

/**
 * This class facilitates extracting transactions from an OFX file.
 */
final
class
OFXTransactionExtracter
extends TransactionExtracter
{
  /**
   * This method returns the account balance specified in the file.
   *
   * @return The account balance specified in the file.
   */
  protected
  Money
  getAccountBalance()
  {
    return itsAccountBalance;
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
    PushbackReader reader = new PushbackReader(stream, 32);
    Transaction trans = null;
    String tag = null;

    while((tag = getNextTag(reader)) != null)
    {
      if(tag.equals(TRANSACTION_START) == true)
      {
        trans = getNextTransaction(reader);
        break;
      }
      else if(tag.equals(ACCOUNT_BALANCE) == true)
      {
        String value = getNextValue(reader);

        value = removeNonmoneyValues(value);
        setAccountBalance(Money.of(UI_CURRENCY_SYMBOL.getCurrency(), getCurrencyFormat(value).parse(value)));
      }
    }

    return trans;
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of private methods.
  //////////////////////////////////////////////////////////////////////////////

  private
  static
  CurrencyFormat
  getCurrencyFormat(String value)
  {
    CurrencyFormatKeys formatKey = null;

    // OFX specifies strict rules for the amounts. No need to specify the
    // decimal format.
    if(value.lastIndexOf('.') != -1)
    {
    	formatKey = CurrencyFormatKeys.US_DOLLAR;
    }
    else
    {
    	formatKey = CurrencyFormatKeys.OTHER;
    }

    return formatKey.getFormat();
  }

  private
  static
  String
  getNextTag(PushbackReader stream)
  throws IOException
  {
    String tag = null;
    int ch = 0;

    while((ch = stream.read()) != -1)
    {
      if(ch == START_TAG)
      {
        tag = getTagName(stream);
        break;
      }
    }

    return tag;
  }

  private
  Transaction
  getNextTransaction(PushbackReader stream)
  throws Exception
  {
    Transaction trans = null;
    String tag = null;
    Date date = new Date();
    String notes = "";
    String number = "";
    String payee = "";
    double amount = 0.0;

    while((tag = getNextTag(stream)) != null)
    {
      if(tag.equals(TRANSACTION_END) == true)
      {
        break;
      }
      else if(tag.equals(TRANSACTION_AMOUNT) == true)
      {
        String value = getNextValue(stream);

        value = removeNonmoneyValues(value);
        amount = getCurrencyFormat(value).parse(value);

        if(isExpense(amount) == true)
        {
          setType(EXPENSE);
        }
        else
        {
          setType(INCOME);
        }
      }
      else if(tag.equals(TRANSACTION_DATE) == true)
      {
        // OFX dates contain extra information that needs to be truncated.
        date = DATE_FORMAT.parse(getNextValue(stream).substring(0, 8));
      }
      else if(tag.equals(TRANSACTION_CHECK_NUMBER) == true)
      {
        number = extractCheckNumber(getNextValue(stream));
      }
      else if(tag.equals(TRANSACTION_NOTES) == true)
      {
        notes = extractNotes(getNextValue(stream));
      }
      else if(tag.equals(TRANSACTION_PAYEE) == true)
      {
        payee = extractPayee(getNextValue(stream));
      }
    }

    // Create the transaction and assume it is reconciled.
    trans = new Transaction(number, date, payee, Money.of(UI_CURRENCY_SYMBOL.getCurrency(), amount), "", notes);
    trans.setIsReconciled(true);

    return trans;
  }

  private
  static
  String
  getNextValue(PushbackReader stream)
  throws IOException
  {
    StringBuffer value = new StringBuffer();
    int ch = 0;

    while((ch = stream.read()) != -1)
    {
      if(ch == START_TAG)
      {
        stream.unread(ch);
        break;
      }

      value.append((char)ch);
    }

    return value.toString().trim();
  }

  private
  static
  String
  getTagName(PushbackReader stream)
  throws IOException
  {
    StringBuffer tag = new StringBuffer();
    int ch = 0;

    while((ch = stream.read()) != -1)
    {
      if(ch == END_TAG)
      {
        break;
      }

      tag.append((char)ch);
    }

    return tag.toString().trim();
  }

  private
  void
  setAccountBalance(Money value)
  {
    itsAccountBalance = value;
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  private Money itsAccountBalance;

  private static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMdd");

  private static final int END_TAG = '>';
  private static final int START_TAG = '<';

  private static final String ACCOUNT_BALANCE = "BALAMT";
  private static final String TRANSACTION_AMOUNT = "TRNAMT";
  private static final String TRANSACTION_DATE = "DTPOSTED";
  private static final String TRANSACTION_CHECK_NUMBER = "CHECKNUM";
  private static final String TRANSACTION_END = "/STMTTRN";
  private static final String TRANSACTION_PAYEE = "NAME";
  private static final String TRANSACTION_NOTES = "MEMO";
  private static final String TRANSACTION_START = "STMTTRN";
}
