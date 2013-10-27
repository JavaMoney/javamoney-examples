// QIFConstants

package org.javamoney.examples.ez.money.importexport;

import org.javamoney.examples.ez.common.utility.I18NHelper;

/**
 * This class provides constants pertaining to the "Quicken Interchange Format".
 */
public
interface
QIFConstants
{
  /**
   * The key for the account's balance.
   */
  public static final char ACCOUNT_BALANCE = 'B';
  /**
   * The key for the account's description.
   */
  public static final char ACCOUNT_DESCRIPTION = 'D';
  /**
   * The account header.
   */
  public static final String ACCOUNT_HEADER = "Account";
  /**
   * The key for the account's name.
   */
  public static final char ACCOUNT_NAME = 'N';
  /**
   * The key for the account's statement date.
   */
  public static final char ACCOUNT_STATEMENT_DATE = 'X';
  /**
   * The key for the account's type.
   */
  public static final char ACCOUNT_TYPE = 'T';
  /**
   * The type used for cash accounts.
   */
  public static final String ACCOUNT_TYPE_CASH = "Cash";
  /**
   * The type used for credit accounts.
   */
  public static final String ACCOUNT_TYPE_CREDIT = "CCard";
  /**
   * The type used for deposit accounts.
   */
  public static final String ACCOUNT_TYPE_DEPOSIT = "Bank";
  /**
   * The key for an amount.
   */
  public static final char AMOUNT = 'T';
  /**
   * The key for an amount in a split.
   */
  public static final char AMOUNT_IN_SPLIT = '$';
  /**
   * The key for a category.
   */
  public static final char CATEGORY = 'L';
  /**
   * The key for a category in a split.
   */
  public static final char CATEGORY_IN_SPLIT = 'S';
  /**
   * The symbol used to separate subcategories.
   */
  public static final String CATEGORY_SEPARATOR = ":";
  /**
   * The symbol used to separate subcategories.
   */
  public static final char CATEGORY_SEPARATOR_CHAR = ':';
  /**
   * The key for a check number.
   */
  public static final char CHECK_NUMBER = 'N';
  /**
   * The key for the transaction is reconciled.
   */
  public static final String CLEARED = "X";
  /**
   * The key for the cleared status.
   */
  public static final char CLEARED_STATUS = 'C';
  /**
   * The key for a date.
   */
  public static final char DATE = 'D';
  /**
   * The key to signal the end of an entry.
   */
  public static final char END_OF_ENTRY = '^';
  /**
   * The QIF file extension.
   */
  public static final String FILE_EXTENSION = ".qif";
  /**
   * The QIF file description.
   */
  public static final String FILE_DESCRIPTION = I18NHelper.getProperty("QIFConstants.file_description");
  /**
   * TThe key to indicate either a new account or transaction type.
   */
  public static final char NEW_TYPE = '!';
  /**
   * The key for notes.
   */
  public static final char NOTES = 'M';
  /**
   * The key for a payee.
   */
  public static final char PAYEE = 'P';
  /**
   * The type used for cash accounts.
   */
  public static final String TRANSACTION_TYPE_CASH = "Type:Cash";
  /**
   * The type used for credit accounts.
   */
  public static final String TRANSACTION_TYPE_CREDIT = "Type:CCard";
  /**
   * The type used for deposit accounts.
   */
  public static final String TRANSACTION_TYPE_DEPOSIT = "Type:Bank";
}
