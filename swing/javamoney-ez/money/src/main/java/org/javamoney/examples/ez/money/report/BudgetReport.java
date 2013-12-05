// BudgetReport

package org.javamoney.examples.ez.money.report;

import static org.javamoney.examples.ez.money.ApplicationProperties.UI_CURRENCY_SYMBOL;
import static org.javamoney.examples.ez.common.utility.DateHelper.getMonthSpan;
import static org.javamoney.examples.ez.money.model.DataManager.getAccounts;
import static org.javamoney.examples.ez.money.model.DataManager.getExpenses;
import static org.javamoney.examples.ez.money.model.DataManager.getIncome;
import static org.javamoney.examples.ez.money.utility.TransactionDateHelper.isInRange;
import static org.javamoney.examples.ez.money.utility.TransactionDateHelper.isOnOrAfter;
import static org.javamoney.examples.ez.money.utility.TransactionHelper.isSplit;
import static org.javamoney.examples.ez.money.utility.TransactionHelper.isTransfer;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;

import org.javamoney.examples.ez.money.model.DataElement;
import org.javamoney.examples.ez.money.model.dynamic.total.Budget;
import org.javamoney.examples.ez.money.model.dynamic.total.CategoryTotalTypeKeys;
import org.javamoney.examples.ez.money.model.dynamic.total.TotalFilter;
import org.javamoney.examples.ez.money.model.dynamic.total.TransactionDetail;
import org.javamoney.examples.ez.money.model.dynamic.transaction.Split;
import org.javamoney.examples.ez.money.model.persisted.account.Account;
import org.javamoney.examples.ez.money.model.persisted.category.Category;
import org.javamoney.examples.ez.money.model.persisted.transaction.Transaction;
import org.javamoney.moneta.Money;

/**
 * This class facilitates maintaining data pertaining to a budget report.
 */
public
final
class
BudgetReport
extends Report
{
  /**
   * This method creates a budget report according to the specified criteria.
   *
   * @param start The report's start date.
   * @param end The report's end date.
   * @param filter The filter to use.
   *
   * @return A budget report.
   */
  public
  static
  BudgetReport
  createReport(Date start, Date end, TotalFilter filter)
  {
    BudgetReport report = new BudgetReport();

    // Initialize budget.
    report.setBalanceTotal(0.0);
    report.setBudgets(new LinkedList<Budget>());
    report.setBudgetTotal(0);
    report.setChangeTotal(0.0);
    report.setEndDate(end);
    report.setFilter(filter);
    report.setRolloverTotal(0.0);
    report.setStartDate(start);

    report.addBudgets();

    // Iterate all accounts and their transactions.
    for(DataElement element : getAccounts().getCollection())
    {
      Account account = (Account)element;

      if(filter.allowsAccount(account) == false)
      {
        continue;
      }

      for(Transaction trans : account.getTransactions())
      {
        if(isTransfer(trans) == false)
        {
          if(filter.allowsPayee(trans) == true && filter.allowsReconciledStatus(trans) == true)
          {
            if(isSplit(trans) == true)
            {
              report.addSplit(account, trans);
            }
            else
            {
              report.addCategory(account, trans);
            }
          }
        }
      }
    }

    report.calculateTotals();

    return report;
  }

  /**
   * This method returns the report's balance total.
   *
   * @return The balance total.
   */
  public
  double
  getBalanceTotal()
  {
    return itsBalanceTotal;
  }

  /**
   * This method returns the collection of budgets the report pertains to.
   *
   * @return The collection of budgets the report pertains to.
   */
  public
  Collection<Budget>
  getBudgets()
  {
    return itsBudgets;
  }

  /**
   * This method returns the report's budget total.
   *
   * @return The budget total.
   */
  public
  int
  getBudgetTotal()
  {
    return itsBudgetTotal;
  }

  /**
   * This method returns the report's change total.
   *
   * @return The change total.
   */
  public
  double
  getChangeTotal()
  {
    return itsChangeTotal;
  }

  /**
   * This method returns the report's rollover total.
   *
   * @return The rollover total.
   */
  public
  double
  getRolloverTotal()
  {
    return itsRolloverTotal;
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of private methods.
  //////////////////////////////////////////////////////////////////////////////

  private
  void
  addBudgets()
  {
    // Add all budgeted categories.
    for(DataElement element : getExpenses().getCollection())
    {
      Category category = (Category)element;

      if(category.isBudgeted() == true)
      {
        getBudgets().add(new Budget(CategoryTotalTypeKeys.EXPENSE, category));
      }
    }

    // Add all budgeted income.
    for(DataElement element : getIncome().getCollection())
    {
      Category category = (Category)element;

      if(category.isBudgeted() == true)
      {
        getBudgets().add(new Budget(CategoryTotalTypeKeys.INCOME, category));
      }
    }
  }

  private
  void
  addCategory(Account account, Transaction trans)
  {
    Budget budget = getBudgetAt(trans.getCategory());

    if(budget != null)
    {
      if(getFilter().allowsCategory(trans) == true)
      {
        if(isInRange(trans, getStartDate(), getEndDate()) == true)
        {
          TransactionDetail detail = new TransactionDetail(trans, account);

          budget.setChange(budget.getChange() + Math.abs(trans.getAmount().doubleValue()));
          budget.getTransactionDetails().add(detail);
        }
        else if(budget.hasRolloverBalance() == true)
        {
          if(trans.getDate().before(getStartDate()) == true)
          {
            if(isOnOrAfter(trans, budget.getRolloverStartDate()) == true)
            {
              budget.setStartingBalance(budget.getStartingBalance() + trans.getAmount().doubleValue());
            }
          }
        }
      }
    }
  }

  private
  void
  addSplit(Account account, Transaction trans)
  {
    Split split = new Split(trans);

    for(int index = 0; index < split.size(); ++index)
    {
      trans = trans.clone();
      trans.setAmount(Money.of(UI_CURRENCY_SYMBOL.getCurrency(), split.getAmount(index)));
      trans.setCategory(split.getCategory(index));

      addCategory(account, trans);
    }
  }

  private
  void
  calculateTotals()
  {
    int monthSpan = 1 + getMonthSpan(getStartDate(), getEndDate());

    for(Budget budget : getBudgets())
    {
      // Set the proper starting balance.
      if(budget.hasRolloverBalance() == true)
      {
        int startSpan = getMonthSpan(budget.getRolloverStartDate(), getStartDate());
        double startingBalance = budget.getAmount() * startSpan;

        budget.setStartingBalance(budget.getStartingBalance() + startingBalance);
      }

      // Set span so budget properly computes its data.
      budget.setMonthSpan(monthSpan);

      setBalanceTotal(getBalanceTotal() + budget.getBalance());
      setBudgetTotal(getBudgetTotal() + budget.getBudget());
      setChangeTotal(getChangeTotal() + budget.getChange());
      setRolloverTotal(getRolloverTotal() + budget.getStartingBalance());
    }
  }

  private
  Budget
  getBudgetAt(String category)
  {
    Budget budgetAt = null;

    for(Budget budget : getBudgets())
    {
      if(budget.isBudgetFor(category) == true)
      {
        budgetAt = budget;
        break;
      }
    }

    return budgetAt;
  }

  private
  void
  setBalanceTotal(double amount)
  {
    itsBalanceTotal = amount;
  }

  private
  void
  setBudgets(Collection<Budget> collection)
  {
    itsBudgets = collection;
  }

  private
  void
  setBudgetTotal(int amount)
  {
    itsBudgetTotal = amount;
  }

  private
  void
  setChangeTotal(double amount)
  {
    itsChangeTotal = amount;
  }

  private
  void
  setRolloverTotal(double amount)
  {
    itsRolloverTotal = amount;
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  private double itsBalanceTotal;
  private Collection<Budget> itsBudgets;
  private int itsBudgetTotal;
  private double itsChangeTotal;
  private double itsRolloverTotal;
}
