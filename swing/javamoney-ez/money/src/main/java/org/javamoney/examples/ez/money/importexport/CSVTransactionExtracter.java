// CSVTransactionExtracter

package org.javamoney.examples.ez.money.importexport;

import static org.javamoney.examples.ez.money.ApplicationProperties.UI_CURRENCY_SYMBOL;
import static org.javamoney.examples.ez.money.ApplicationProperties.getCSVColumnOrder;
import static org.javamoney.examples.ez.money.importexport.CSVColumnKeys.AMOUNT;
import static org.javamoney.examples.ez.money.importexport.CSVColumnKeys.CHECK_NUMBER;
import static org.javamoney.examples.ez.money.importexport.CSVColumnKeys.DATE;
import static org.javamoney.examples.ez.money.importexport.CSVColumnKeys.NOTES;
import static org.javamoney.examples.ez.money.importexport.CSVColumnKeys.PAYEE;
import static org.javamoney.examples.ez.money.importexport.CSVConstants.QUOTE;
import static org.javamoney.examples.ez.money.importexport.CSVConstants.SEPARATOR;
import static org.javamoney.examples.ez.money.model.dynamic.transaction.TransactionTypeKeys.EXPENSE;
import static org.javamoney.examples.ez.money.model.dynamic.transaction.TransactionTypeKeys.INCOME;
import static org.javamoney.examples.ez.money.model.dynamic.transaction.TransactionTypeKeys.TRANSFER;
import static org.javamoney.examples.ez.money.utility.TransactionHelper.isExpense;

import java.io.BufferedReader;
import java.util.Date;
import java.util.StringTokenizer;

import org.javamoney.examples.ez.money.model.persisted.transaction.Transaction;
import org.javamoney.moneta.Money;

/**
 * This class facilitates extracting transactions from a CSV file.
 */
final
class
CSVTransactionExtracter
extends TransactionExtracter
{
  /**
   * Constructs a new transaction extractor.
   */
  protected
  CSVTransactionExtracter()
  {
    setColumnOrder(getCSVColumnOrder());
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
    double amount = 0.0;
    Date date = new Date();
    String line = "";
    String notes = "";
    String number = "";
    String payee = "";

    setType(null);

    if((line = stream.readLine()) != null)
    {
      StringTokenizer tokens = new StringTokenizer(line, SEPARATOR, true);
      int column = 0;

      while(tokens.hasMoreTokens() == true && column < getColumnOrder().length)
      {
        int ordinal = getColumnOrder()[column]; // Get column mapping.

        line = getNextToken(tokens);

        if(ordinal == AMOUNT.ordinal())
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
        else if(ordinal == CHECK_NUMBER.ordinal())
        {
          number = extractCheckNumber(line);
        }
        else if(ordinal == DATE.ordinal())
        {
          date = extractDate(line);
          System.out.println(date);
        }
        else if(ordinal == NOTES.ordinal())
        {
          notes = extractNotes(line);
        }
        else if(ordinal == PAYEE.ordinal())
        {
          // Can be either a payee or an account.
          if(isAccount(line) == true)
          {
            payee = extractAccount(line);
            setType(TRANSFER);
          }
          else
          {
            payee = extractPayee(line);
          }
        }
        else
        {
          // Undefined ordinal. For now it is ignored.
        }

        column += 1;
      }

      // Create the transaction and assume it is reconciled.
      trans = new Transaction(number, date, payee, Money.of(UI_CURRENCY_SYMBOL.getCurrency(), amount), "", notes);
      trans.setIsReconciled(true);
    }

    return trans;
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of private methods.
  //////////////////////////////////////////////////////////////////////////////

  private
  int[]
  getColumnOrder()
  {
    return itsColumnOrder;
  }

  private
  String
  getNextToken(StringTokenizer tokens)
  {
    String token = tokens.nextToken();

    // If the next token is a token, then there was no data.
    if(token.equals(SEPARATOR) == true)
    {
      token = "";
    }
    else
    {
      // This indicates the delimiter needs to be included in the field value.
      if(token.startsWith(QUOTE) == true)
      {
        // The delimiter was not in the token, so remove the quotes.
        if(token.endsWith(QUOTE) == true)
        {
          token = token.substring(1, token.length() - 1);
        }
        else
        {
          token = token.substring(1); // Remove the first quote.

          // Keep appending the tokens until the last quote is found.
          while(tokens.hasMoreTokens() == true)
          {
            token += tokens.nextToken();

            if(token.endsWith(QUOTE) == true)
            {
              break;
            }

            // Append the token that is supposed to be there.
            token += SEPARATOR;
          }

          // The token should end in a quote, so remove it. If not, then the
          // data is invalid.
          if(token.endsWith(QUOTE) == true)
          {
            token = token.substring(0, token.length() - 1);
          }
        }
      }

      // The token delimiters are being returned and need to be removed for the
      // next iteration. This is necessary to identify empty fields.
      if(tokens.hasMoreTokens() == true)
      {
        tokens.nextToken();
      }
    }

    return token.trim();
  }

  private
  void
  setColumnOrder(int[] values)
  {
    itsColumnOrder = values;
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  private int[] itsColumnOrder;
}
