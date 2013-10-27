// CategoryReportComparator

package org.javamoney.examples.ez.money.report;

import static org.javamoney.examples.ez.common.utility.CompareHelper.compareAmounts;
import static org.javamoney.examples.ez.common.utility.CompareHelper.compareStrings;
import static org.javamoney.examples.ez.money.ApplicationProperties.getCategoryReportSortByField;

import java.util.Comparator;

import org.javamoney.examples.ez.money.model.dynamic.total.IncomeExpenseTotal;

/**
 * This class facilitates comparing category totals for a category report.
 */
final
class
CategoryReportComparator
implements Comparator<IncomeExpenseTotal>
{
  /**
   * Constructs a new comparator.
   */
  public
  CategoryReportComparator()
  {
    setKey(getCategoryReportSortByField());
  }

  /**
   * This method compares two category totals.
   *
   * @param total1 A category total to compare.
   * @param total2 A category total to compare.
   *
   * @return The result of comparing two category totals.
   */
  public
  int
  compare(IncomeExpenseTotal total1, IncomeExpenseTotal total2)
  {
    int result = 0;

    if(getKey() == CategoryReportSortByKeys.AMOUNT)
    {
      result = compareAmounts(total1.getAmount(), total2.getAmount());
    }
    else if(getKey() == CategoryReportSortByKeys.CATEGORY)
    {
      result = compareStrings(total1.getCategoryIdentifier(), total2.getCategoryIdentifier(), false);
    }
    else if(getKey() == CategoryReportSortByKeys.GROUP)
    {
      result = compareStrings(total1.getGroupName(), total2.getGroupName(), false);
    }
    else
    {
      result = compareAmounts(total2.getPercent(), total1.getPercent());
    }

    return result;
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of private methods.
  //////////////////////////////////////////////////////////////////////////////

  private
  CategoryReportSortByKeys
  getKey()
  {
    return itsKey;
  }

  private
  void
  setKey(CategoryReportSortByKeys key)
  {
    itsKey = key;
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  private CategoryReportSortByKeys itsKey;
}
