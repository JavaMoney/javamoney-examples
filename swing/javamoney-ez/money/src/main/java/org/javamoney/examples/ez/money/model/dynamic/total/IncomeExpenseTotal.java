// IncomeExpenseTotal

package org.javamoney.examples.ez.money.model.dynamic.total;

import static org.javamoney.examples.ez.money.model.DataManager.getExpenses;
import static org.javamoney.examples.ez.money.model.DataManager.getIncome;
import static org.javamoney.examples.ez.money.model.dynamic.total.CategoryTotalTypeKeys.EXPENSE;
import static org.javamoney.examples.ez.money.model.dynamic.total.CategoryTotalTypeKeys.EXPENSE_SUMMARY;

import org.javamoney.examples.ez.money.model.persisted.category.Category;
import org.javamoney.examples.ez.money.model.persisted.category.CategoryCollection;

/**
 * This class facilitates tracking a total that needs to calculate percentages
 * based on the total income and expense amounts.
 */
public
class
IncomeExpenseTotal
extends CategoryTotal
{
  /**
   * Constructs a new category total.
   *
   * @param type The type.
   * @param category The identifier.
   */
  public
  IncomeExpenseTotal(CategoryTotalTypeKeys type, String category)
  {
    super(type, category, getCategoryReference(type, category));

    setAmount(0.0);
  }

  /**
   * This method returns the total amount.
   *
   * @return The total amount.
   */
  public
  final
  double
  getAmount()
  {
    return itsAmount;
  }

  /**
   * This method returns the category's identifier.
   *
   * @return The category's identifier.
   */
  public
  String
  getCategoryIdentifier()
  {
    return getCategory().getIdentifier();
  }

  /**
   * This method returns the amount of all expenses.
   *
   * @return The amount of all expenses.
   */
  public
  static
  final
  double
  getExpenseTotal()
  {
    return itsExpenseTotal;
  }

  /**
   * This method returns the category group for the category total.
   *
   * @return The category group.
   */
  public
  String
  getGroupName()
  {
    return getCategory().getGroupName();
  }

  /**
   * This method returns the amount of all income.
   *
   * @return The amount of all income.
   */
  public
  static
  final
  double
  getIncomeTotal()
  {
    return itsIncomeTotal;
  }

  /**
   * This method returns the category total's percentage of either the total
   * expenses or total income, depending on the type.
   *
   * @return The percentage.
   */
  public
  double
  getPercent()
  {
    double total = 0.0;

    if(getType() == EXPENSE || getType() == EXPENSE_SUMMARY)
    {
      total = getExpenseTotal();
    }
    else
    {
      total = getIncomeTotal();
    }

    return Math.ceil(Math.abs(getAmount())) / Math.ceil(total);
  }

  /**
   * This method sets the total amount.
   *
   * @param value The amount.
   */
  public
  final
  void
  setAmount(double value)
  {
    itsAmount = value;
  }

  /**
   * This method sets the amount of all expenses.
   *
   * @param value The amount of all expenses.
   */
  public
  static
  final
  void
  setExpenseTotal(double value)
  {
    itsExpenseTotal = value;
  }

  /**
   * This method sets the amount of all income.
   *
   * @param value The amount of all income.
   */
  public
  static
  final
  void
  setIncomeTotal(double value)
  {
    itsIncomeTotal = value;
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of private methods.
  //////////////////////////////////////////////////////////////////////////////

  private
  static
  Category
  getCategoryReference(CategoryTotalTypeKeys type, String qif)
  {
    CategoryCollection collection = null;
    Category category = null;

    if(type == EXPENSE || type == EXPENSE_SUMMARY)
    {
      collection = getExpenses();
    }
    else
    {
      collection = getIncome();
    }

    category = collection.getCategoryFromQIF(qif);

    // Could be the summary or it is not categorized.
    if(category == null)
    {
      category = new Category(qif);
    }

    return category;
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  private double itsAmount;

  private static double itsExpenseTotal;
  private static double itsIncomeTotal;
}
