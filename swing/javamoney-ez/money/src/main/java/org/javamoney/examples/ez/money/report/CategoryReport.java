// CategoryReport

package org.javamoney.examples.ez.money.report;

import static org.javamoney.examples.ez.money.ApplicationProperties.UI_CURRENCY_SYMBOL;
import static org.javamoney.examples.ez.money.KeywordKeys.NOT_CATEGORIZED;
import static org.javamoney.examples.ez.money.importexport.QIFConstants.CATEGORY_SEPARATOR;
import static org.javamoney.examples.ez.money.importexport.QIFConstants.CATEGORY_SEPARATOR_CHAR;
import static org.javamoney.examples.ez.money.model.DataManager.getAccounts;
import static org.javamoney.examples.ez.money.model.dynamic.total.CategoryTotalTypeKeys.EXPENSE;
import static org.javamoney.examples.ez.money.model.dynamic.total.CategoryTotalTypeKeys.INCOME;
import static org.javamoney.examples.ez.money.model.dynamic.total.IncomeExpenseTotal.getExpenseTotal;
import static org.javamoney.examples.ez.money.model.dynamic.total.IncomeExpenseTotal.getIncomeTotal;
import static org.javamoney.examples.ez.money.model.dynamic.total.IncomeExpenseTotal.setExpenseTotal;
import static org.javamoney.examples.ez.money.model.dynamic.total.IncomeExpenseTotal.setIncomeTotal;
import static org.javamoney.examples.ez.money.utility.TransactionDateHelper.isInRange;
import static org.javamoney.examples.ez.money.utility.TransactionHelper.isIncome;
import static org.javamoney.examples.ez.money.utility.TransactionHelper.isSplit;
import static org.javamoney.examples.ez.money.utility.TransactionHelper.isTransfer;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.StringTokenizer;

import org.javamoney.examples.ez.money.model.DataElement;
import org.javamoney.examples.ez.money.model.dynamic.total.CategoryTotalTypeKeys;
import org.javamoney.examples.ez.money.model.dynamic.total.IncomeExpenseTotal;
import org.javamoney.examples.ez.money.model.dynamic.total.TotalFilter;
import org.javamoney.examples.ez.money.model.dynamic.total.TransactionDetail;
import org.javamoney.examples.ez.money.model.dynamic.total.TransferTotal;
import org.javamoney.examples.ez.money.model.dynamic.transaction.Split;
import org.javamoney.examples.ez.money.model.persisted.account.Account;
import org.javamoney.examples.ez.money.model.persisted.transaction.Transaction;
import org.javamoney.moneta.Money;

/**
 * This class facilitates maintaining data pertaining to a category report.
 */
public
final
class
CategoryReport
extends Report
{
  /**
   * This method creates a category report according to the specified criteria.
   * <p>
   * <b>Note:</b> The report is not sorted according to the sorting preference
   * selected in the category report dialog. It is up to the classes that
   * utilize the report to sort it accordingly.
   *
   * @param start The report's start date.
   * @param end The report's end date.
   * @param filter The filter to use.
   *
   * @return A category report.
   */
  public
  static
  CategoryReport
  createReport(Date start, Date end, TotalFilter filter)
  {
    CategoryReport report = new CategoryReport();

    report.setEndDate(end);
    report.setExpenseGroups(new LinkedList<IncomeExpenseTotal>());
    report.setExpenses(new LinkedList<IncomeExpenseTotal>());
    report.setFilter(filter);
    report.setIncome(new LinkedList<IncomeExpenseTotal>());
    report.setIncomeGroups(new LinkedList<IncomeExpenseTotal>());
    report.setStartDate(start);
    report.setTransfers(new LinkedList<TransferTotal>());

    // Clear previous totals.
    setExpenseTotal(0.0);
    setIncomeTotal(0.0);

    // Iterate all the accounts and calculate the totals.
    for(DataElement element : getAccounts().getCollection())
    {
      Account account = (Account)element;
      TransferTotal transferTotal = new TransferTotal(account);

      if(filter.allowsAccount(account) == false)
      {
        continue;
      }

      // Iterate all transactions and calculate the totals.
      for(Transaction trans : account.getTransactions())
      {
        if(isInRange(trans, start, end) == true)
        {
          if(filter.allowsPayee(trans) == true && filter.allowsReconciledStatus(trans) == true)
          {
            if(isTransfer(trans) == false)
            {
              if(isSplit(trans) == true)
              {
                report.addSplit(trans, account);
              }
              else
              {
                report.addCategory(trans, account);
              }
            }
            else
            {
              report.addTransfer(transferTotal, trans);
            }
          }
        }
      }

      report.getTransfers().add(transferTotal);
    }

    return report;
  }

  /**
   * This method returns the report's expense groups.
   *
   * @return The report's expense groups.
   */
  public
  Collection<IncomeExpenseTotal>
  getExpenseGroups()
  {
    return itsExpenseGroups;
  }

  /**
   * This method returns the report's expense categories.
   *
   * @return The report's expense categories.
   */
  public
  Collection<IncomeExpenseTotal>
  getExpenses()
  {
    return itsExpenses;
  }

  /**
   * This method returns the report's income categories.
   *
   * @return The report's income categories.
   */
  public
  Collection<IncomeExpenseTotal>
  getIncome()
  {
    return itsIncome;
  }

  /**
   * This method returns the report's income groups.
   *
   * @return The report's income groups.
   */
  public
  Collection<IncomeExpenseTotal>
  getIncomeGroups()
  {
    return itsIncomeGroups;
  }

  /**
   * This method returns the report's transfer totals.
   *
   * @return The report's transfer totals.
   */
  public
  Collection<TransferTotal>
  getTransfers()
  {
    return itsTransfers;
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of private methods.
  //////////////////////////////////////////////////////////////////////////////

  private
  void
  addCategory(Transaction trans, Account account)
  {
    if(getFilter().allowsCategory(trans) == true)
    {
      if(isIncome(trans) == true)
      {
        addCategoryTotalToGroup(getIncomeGroups(), INCOME, trans, account);
        addCategoryTotalToSet(getIncome(), INCOME, trans, account);
        setIncomeTotal(getIncomeTotal() + trans.getAmount().getNumber().doubleValue());
      }
      else
      {
        addCategoryTotalToGroup(getExpenseGroups(), EXPENSE, trans, account);
        addCategoryTotalToSet(getExpenses(), EXPENSE, trans, account);
        setExpenseTotal(getExpenseTotal() - trans.getAmount().getNumber().doubleValue());
      }
    }
  }

  private
  void
  addCategoryTotalToGroup(Collection<IncomeExpenseTotal> group, CategoryTotalTypeKeys key,
      Transaction trans, Account account)
  {
    String qif = trans.getCategory();
    int index = qif.lastIndexOf(CATEGORY_SEPARATOR_CHAR);

    if(qif.length() != 0 && index != -1)
    {
      StringTokenizer tokens = null;
      IncomeExpenseTotal total = null;
      double amount = trans.getAmount().getNumber().doubleValue();

      // Get the group name.
      qif = qif.substring(0, index);

      tokens = new StringTokenizer(qif, CATEGORY_SEPARATOR);
      qif = "";

      while(tokens.hasMoreTokens() == true)
      {
        qif += tokens.nextToken();
        total = getCategoryTotal(group, qif);

        if(total == null)
        {
          total = new IncomeExpenseTotal(key, qif);
          group.add(total);
        }

        total.setAmount(total.getAmount() + amount);
        total.getTransactionDetails().add(new TransactionDetail(trans, account));

        qif += CATEGORY_SEPARATOR;
      }
    }
  }

  private
  void
  addCategoryTotalToSet(Collection<IncomeExpenseTotal> collection, CategoryTotalTypeKeys type,
      Transaction trans, Account account)
  {
    IncomeExpenseTotal total = null;
    String qif = trans.getCategory();
    double amount = trans.getAmount().getNumber().doubleValue();

    // If the transaction is not categorized, then show it as so.
    if(qif.length() == 0)
    {
      qif = NOT_CATEGORIZED.toString();
    }

    total = getCategoryTotal(collection, qif);

    if(total == null)
    {
      total = new IncomeExpenseTotal(type, qif);
      collection.add(total);
    }

    total.setAmount(total.getAmount() + amount);
    total.getTransactionDetails().add(new TransactionDetail(trans, account));
  }

  private
  void
  addSplit(Transaction trans, Account account)
  {
    Split split = new Split(trans);

    for(int len = 0; len < split.size(); ++len)
    {
      trans = trans.clone();
      trans.setAmount(Money.of(UI_CURRENCY_SYMBOL.getCurrency(), split.getAmount(len)));
      trans.setCategory(split.getCategory(len));

      addCategory(trans, account);
    }
  }

  private
  void
  addTransfer(TransferTotal total, Transaction trans)
  {
    if(isIncome(trans) == true)
    {
      total.setToTotal(total.getToTotal() + trans.getAmount().getNumber().doubleValue());
    }
    else
    {
      total.setFromTotal(total.getFromTotal() + Math.abs(trans.getAmount().getNumber().doubleValue()));
    }

    total.getTransactionDetails().add(new TransactionDetail(trans, total.getAccount()));
  }

  private
  IncomeExpenseTotal
  getCategoryTotal(Collection<IncomeExpenseTotal> collection, String identifier)
  {
    IncomeExpenseTotal totalAt = null;

    for(IncomeExpenseTotal total : collection)
    {
      if(total.getIdentifier().equals(identifier) == true)
      {
        totalAt = total;
        break;
      }
    }

    return totalAt;
  }

  private
  void
  setExpenseGroups(Collection<IncomeExpenseTotal> collection)
  {
    itsExpenseGroups = collection;
  }

  private
  void
  setExpenses(Collection<IncomeExpenseTotal> collection)
  {
    itsExpenses = collection;
  }

  private
  void
  setIncome(Collection<IncomeExpenseTotal> collection)
  {
    itsIncome = collection;
  }

  private
  void
  setIncomeGroups(Collection<IncomeExpenseTotal> collection)
  {
    itsIncomeGroups = collection;
  }

  private
  void
  setTransfers(Collection<TransferTotal> collection)
  {
    itsTransfers = collection;
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  private Collection<IncomeExpenseTotal> itsExpenseGroups;
  private Collection<IncomeExpenseTotal> itsExpenses;
  private Collection<IncomeExpenseTotal> itsIncome;
  private Collection<IncomeExpenseTotal> itsIncomeGroups;
  private Collection<TransferTotal> itsTransfers;
}
