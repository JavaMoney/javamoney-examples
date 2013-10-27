// Budget

package org.javamoney.examples.ez.money.model.dynamic.total;

import static org.javamoney.examples.ez.money.importexport.QIFConstants.CATEGORY_SEPARATOR_CHAR;

import java.util.Date;

import org.javamoney.examples.ez.money.model.persisted.category.Category;

/**
 * This class facilitates managing the expenses that can be budgeted.
 */
public
final
class
Budget
extends CategoryTotal
{
  /**
   * Constructs a new budget.
   *
   * @param type The type.
   * @param category The category to reference.
   */
  public
  Budget(CategoryTotalTypeKeys type, Category category)
  {
    super(type, category.getIdentifier(), category);

    setChange(0.0);
    setMonthSpan(1);
    setStartingBalance(0.0);
  }

  /**
   * This method returns the monthly-allotted amount.
   *
   * @return The monthly-allotted amount.
   */
  public
  int
  getAmount()
  {
    return getCategory().getBudget();
  }

  /**
   * This method returns the monthly-allotted amount multiplied by the month
   * span.
   *
   * @return The monthly-allotted amount multiplied by the month span.
   */
  public
  int
  getBudget()
  {
    return getCategory().getBudget() * getMonthSpan();
  }

  /**
   * This method returns the balance.
   *
   * @return The balance.
   */
  public
  double
  getBalance()
  {
    double balance = getBudget() + getStartingBalance();

    // Budgets for income categories are negative behind the scenes and positive
    // in the UI so the sign needs to be flipped.
    if(getType() == CategoryTotalTypeKeys.INCOME)
    {
      balance += getChange();
    }
    else
    {
      // The budget summary and the expense types work the same.
      balance -= getChange();
    }

    return balance;
  }

  /**
   * This method returns the percentage that is available in respect to what has
   * been spent.
   *
   * @return The balance percentage.
   */
  public
  double
  getBalancePercentage()
  {
    double percent = getBalance() / getBudget();

    // Do not let the percent go over what is possible to display.
    if(percent > 1.0)
    {
      percent = 1.0;
    }
    else if(percent < 0.0)
    {
      percent = 0;
    }

    return percent;
  }

  /**
   * This method returns how much has changed towards the monthly-allotted
   * budget.
   *
   * @return How much has changed towards the monthly-allotted budget.
   */
  public
  double
  getChange()
  {
    return itsChange;
  }

  /**
   * This method returns the percentage on how much has changed in respect to
   * the balance.
   *
   * @return The change percentage.
   */
  public
  double
  getChangePercentage()
  {
    return 1 - getBalancePercentage();
  }

  /**
   * This method returns the month span.
   *
   * @return The month span.
   */
  public
  int
  getMonthSpan()
  {
    return itsMonthSpan;
  }

  /**
   * The method returns the date the budget should start rolling over.
   *
   * @return The date the budget should start rolling over.
   */
  public
  Date
  getRolloverStartDate()
  {
    return getCategory().getRolloverStartDate();
  }

  /**
   * This method returns the starting balance.
   *
   * @return The starting balance.
   */
  public
  double
  getStartingBalance()
  {
    return itsStartingBalance;
  }

  /**
   * This method returns true if the balance should rollover each month,
   * otherwise false.
   *
   * @return true or false.
   */
  public
  boolean
  hasRolloverBalance()
  {
    return getCategory().hasRolloverBudget();
  }

  /**
   * This method returns true if the budget is the group for the specified
   * category, otherwise false.
   *
   * @param category The category to check.
   *
   * @return true or false.
   */
  public
  boolean
  isBudgetFor(String category)
  {
    boolean result = false;

    if(category.startsWith(getIdentifier()) == true)
    {
      int len = getIdentifier().length();
      char separator = CATEGORY_SEPARATOR_CHAR;

      //  Make sure they match or the next character is the category separator.
      if(category.length() == len || category.charAt(len) == separator)
      {
        result = true;
      }
    }

    return result;
  }

  /**
   * This method sets how much has changed towards the monthly-allotted
   * budget.
   *
   * @param value How much has changed towards the monthly-allotted budget.
   */
  public
  void
  setChange(double value)
  {
    itsChange = value;
  }

  /**
   * This method sets the month span.
   *
   * @param value The month span.
   */
  public
  void
  setMonthSpan(int value)
  {
    itsMonthSpan = value;
  }

  /**
   * This method sets the starting balance.
   *
   * @param value The starting balance.
   */
  public
  void
  setStartingBalance(double value)
  {
    itsStartingBalance = value;
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  private double itsChange;
  private int itsMonthSpan;
  private double itsStartingBalance;
}
