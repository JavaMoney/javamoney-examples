// TransactionFilterFieldKeys

package org.javamoney.examples.ez.money.model.dynamic.transaction;

/**
 * This enumerated class provides keys for the transaction fields that can be
 * filtered.
 */
public
enum
TransactionFilterFieldKeys
{
  /**
   * Indicates all fields.
   */
  ALL,
  /**
   * The transaction's amount field.
   */
  AMOUNT,
  /**
   * The transaction's check number field.
   */
  CHECK,
  /**
   * The transaction's date field.
   */
  DATE,
  /**
   * The transaction's notes field.
   */
  NOTES,
  /**
   * The transaction's payee field.
   */
  PAYEE;
}
