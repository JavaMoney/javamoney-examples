// TransactionExtracter

package org.javamoney.examples.ez.money.importexport;

import static org.javamoney.examples.ez.money.ApplicationProperties.getImportExportCurrencyFormat;
import static org.javamoney.examples.ez.money.ApplicationProperties.getImportExportDateFormat;
import static org.javamoney.examples.ez.money.importexport.QIFConstants.CATEGORY_SEPARATOR;
import static org.javamoney.examples.ez.money.model.persisted.transaction.Transaction.MAX_CHECK_LENGTH;
import static org.javamoney.examples.ez.money.model.persisted.transaction.Transaction.MAX_NOTES_LENGTH;
import static org.javamoney.examples.ez.money.utility.IDHelper.canUseIdentifier;
import static org.javamoney.examples.ez.money.utility.IDHelper.purgeIdentifier;
import static org.javamoney.examples.ez.money.utility.IDHelper.truncateID;

import java.io.BufferedReader;
import java.text.ParseException;
import java.util.Date;
import java.util.StringTokenizer;

import org.javamoney.examples.ez.money.locale.CurrencyFormat;
import org.javamoney.examples.ez.money.model.dynamic.transaction.TransactionTypeKeys;
import org.javamoney.examples.ez.money.model.persisted.transaction.Transaction;

/**
 * This class facilitates extracting transactions from a file.
 */
abstract
class
TransactionExtracter
{
  /**
   * Constructs a new transaction extractor.
   */
  protected
  TransactionExtracter()
  {
    setCurrency(getImportExportCurrencyFormat().getCurrency());
    setDateFormat(getImportExportDateFormat());
    setType(null);
  }

  /**
   * This method extracts the account from the incoming text.
   *
   * @param line The incoming text from the file stream.
   *
   * @return The account from the incoming text.
   */
  protected
  static
  final
  String
  extractAccount(String line)
  {
    if(line.startsWith("*") == true)
    {
      // Quicken puts this in front of accounts it doesn't create during import.
      line = line.substring(1);
    }
    else
    {
      // The QIF format specifies that all accounts be enclosed within '[' ']'.
      line = line.substring(1, line.length() - 1);
    }

    return purgeIdentifier(line);
  }

  /**
   * This method extracts the amount from the incoming text.
   *
   * @param line The incoming text from the file stream.
   *
   * @return The amount from the incoming text.
   *
   * @throws ParseException If an error occurs during parsing.
   */
  protected
  final
  double
  extractAmount(String line)
  throws ParseException
  {
    line = removeNonmoneyValues(line);

    if(line.length() == 0)
    {
      line = "0";
    }

    return getCurrency().parse(line);
  }

  /**
   * This method extracts the amount from the incoming text to be used in a
   * split.
   * <p>
   * <b>Note:</b> This method differs from extractAmount() in that it returns an
   * amount to be used in a split, which is always positive.
   *
   * @param line The incoming text from the file stream.
   *
   * @return The amount from the incoming text.
   *
   * @throws ParseException If an error occurs during parsing.
   */
  protected
  final
  String
  extractAmountForSplit(String line)
  throws ParseException
  {
    double amount = extractAmount(line);

    // Amounts in splits are always positive.
    amount = Math.abs(amount);

    return getCurrency().format(amount);
  }

  /**
   * This method extracts the category from the incoming text.
   *
   * @param line The incoming text from the file stream.
   *
   * @return The category from the incoming text.
   */
  protected
  static
  final
  String
  extractCategory(String line)
  {
    StringTokenizer tokens = new StringTokenizer(line, CATEGORY_SEPARATOR);
    String category = "";

    // Break category down into its tokens to verify, and then reconstruct it.
    while(tokens.hasMoreTokens() == true)
    {
      String token = tokens.nextToken();

      if(canUseIdentifier(token) == false)
      {
        break;
      }

      // Reconstruct category.
      category += truncateID(token);

      if(tokens.hasMoreTokens() == true)
      {
        category += CATEGORY_SEPARATOR;
      }
    }

    return category;
  }

  /**
   * This method extracts the check number from the incoming text.
   *
   * @param line The incoming text from the file stream.
   *
   * @return The check number from the incoming text.
   */
  protected
  static
  final
  String
  extractCheckNumber(String line)
  {
    if(line.length() > MAX_CHECK_LENGTH)
    {
      line = line.substring(0, MAX_CHECK_LENGTH);
    }

    return line;
  }

  /**
   * This method extracts the date from the incoming text.
   *
   * @param line The incoming text from the file stream.
   *
   * @return The date from the incoming text.
   *
   * @throws ParseException If an error occurs during parsing.
   */
  protected
  final
  Date
  extractDate(String line)
  throws ParseException
  {
    StringBuffer buffer = new StringBuffer(line);

    for(int len = 0; len < buffer.length(); ++len)
    {
      // MS Money formats dates as MM/DD'YYYY
      if(buffer.charAt(len) == '\'')
      {
        buffer.setCharAt(len, '/');
      }
    }

    return getDateFormat().parse(buffer.toString());
  }

  /**
   * This method extracts the notes from the incoming text.
   *
   * @param line The incoming text from the file stream.
   *
   * @return The notes from the incoming text.
   */
  protected
  final
  static
  String
  extractNotes(String line)
  {
    if(line.length() > MAX_NOTES_LENGTH)
    {
      line = line.substring(0, MAX_NOTES_LENGTH);
    }

    return line;
  }

  /**
   * This method extracts the payee from the incoming text.
   *
   * @param line The incoming text from the file stream.
   *
   * @return The payee from the incoming text.
   */
  protected
  final
  static
  String
  extractPayee(String line)
  {
    // Purging also takes care of the max length requirements.
    return purgeIdentifier(line);
  }

  /**
   * This method returns the currency being used to parse amounts.
   *
   * @return The currency being used to parse amounts.
   */
  protected
  final
  CurrencyFormat
  getCurrency()
  {
    return itsCurrency;
  }

  /**
   * This method returns the last extracted transaction's type.
   *
   * @return The last extracted transaction's type.
   */
  protected
  final
  TransactionTypeKeys
  getType()
  {
    return itsType;
  }

  /**
   * This method returns true if the incoming text contains an account,
   * otherwise false.
   *
   * @param line The incoming text from the file stream.
   *
   * @return true or false.
   */
  protected
  static
  final
  boolean
  isAccount(String line)
  {
    boolean result = false;

    // The QIF format specifies that all accounts be enclosed within '[' ']'.
    if(line.startsWith("[") == true && line.endsWith("]") == true)
    {
      result = true;
    }
    else if(line.startsWith("*") == true)
    {
      // Quicken puts this in front of accounts it doesn't create during import.
      result = true;
    }

    return result;
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
  protected
  abstract
  Transaction
  next(BufferedReader stream)
  throws Exception;

  /**
   * This method returns a string amount, based on the specified parameter, that
   * can be parsed.
   *
   * @param line The string that contains an amount.
   *
   * @return A string amount that can be parsed.
   */
  protected
  static
  String
  removeNonmoneyValues(String line)
  {
    StringBuffer buffer = new StringBuffer(line.length());

    // Remove non-money values incase file is formatted incorrectly.
    for(int len = 0; len < line.length(); ++len)
    {
      char ch = line.charAt(len);

      if(isMoneyValue(ch) == true)
      {
        buffer.append(ch);
      }
    }

    return buffer.toString();
  }

  /**
   * This method sets the last extracted transaction's type.
   *
   * @param type The last extracted transaction's type.
   */
  protected
  final
  void
  setType(TransactionTypeKeys type)
  {
    itsType = type;
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of private methods.
  //////////////////////////////////////////////////////////////////////////////

  private
  ImportExportDateFormatKeys
  getDateFormat()
  {
    return itsDateFormat;
  }

  private
  static
  boolean
  isMoneyValue(char ch)
  {
    boolean result = Character.isDigit(ch);

    if(result == false)
    {
      // Ensure we don't lose money signs and decimal indicators.
      if(ch == '-' || ch == '.' || ch == ',')
      {
        result = true;
      }
    }

    return result;
  }

  private
  void
  setCurrency(CurrencyFormat currency)
  {
    itsCurrency = currency;
  }

  private
  void
  setDateFormat(ImportExportDateFormatKeys key)
  {
    itsDateFormat = key;
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  private CurrencyFormat itsCurrency; // TODO use actual CurrencyUnit here
  private ImportExportDateFormatKeys itsDateFormat;
  private TransactionTypeKeys itsType;
}
