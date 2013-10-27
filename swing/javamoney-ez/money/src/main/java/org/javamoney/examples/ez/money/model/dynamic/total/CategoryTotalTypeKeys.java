// CategoryTotalTypeKeys

package org.javamoney.examples.ez.money.model.dynamic.total;

import org.javamoney.examples.ez.common.utility.I18NHelper;

/**
 * This enumerated class provides keys for the types of category totals.
 */
public
enum
CategoryTotalTypeKeys
{
  /**
   * Used to indicate a budget summary.
   */
  BUDGET_SUMMARY(""),
  /**
   * An expense.
   */
  EXPENSE(I18NHelper.getSharedProperty("expense")),
  /**
   * Used to indicate a summary of expenses.
   */
  EXPENSE_SUMMARY(""),
  /**
   * An income.
   */
  INCOME(I18NHelper.getSharedProperty("income")),
  /**
   * Used to indicate a summary of income.
   */
  INCOME_SUMMARY("");

  //////////////////////////////////////////////////////////////////////////////
  // Start of public methods.
  //////////////////////////////////////////////////////////////////////////////

  /**
   * This method returns true if the total represents the total of all
   * categories of its type, otherwise false.
   *
   * @return true or false.
   */
  public
  boolean
  isSummary()
  {
    return this != EXPENSE && this != INCOME;
  }

  /**
   * This method returns a string for the enum constant.
   *
   * @return A string.
   */
  @Override
  public
  String
  toString()
  {
    return itsIdentifier;
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of private methods.
  //////////////////////////////////////////////////////////////////////////////

  private
  CategoryTotalTypeKeys(String identifier)
  {
    itsIdentifier = identifier;
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  private String itsIdentifier;
}
