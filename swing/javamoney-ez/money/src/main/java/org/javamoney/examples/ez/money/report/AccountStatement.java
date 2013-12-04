// AccountStatement

package org.javamoney.examples.ez.money.report;

import static org.javamoney.examples.ez.money.utility.TransactionDateHelper.isInRange;
import static org.javamoney.examples.ez.money.utility.TransactionHelper.isExpense;
import static org.javamoney.examples.ez.money.utility.TransactionHelper.isTransfer;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;

import org.javamoney.examples.ez.money.model.persisted.account.Account;
import org.javamoney.examples.ez.money.model.persisted.transaction.Transaction;

/**
 * This class facilitates maintaining data pertaining to an account statement.
 */
public
final
class
AccountStatement
extends Report
{
  /**
   * This method creates an account statement based on the specified criteria.
   *
   * @param account The account the statement pertains to.
   * @param start The statement's start date.
   * @param end The statement's end date.
   *
   * @return An account statement.
   */
  public
  static
  AccountStatement
  createStatement(Account account, Date start, Date end)
  {
    AccountStatement statement = new AccountStatement();

    statement.setAccount(account);
    statement.setEndDate(end);
    statement.setStartDate(start);
    statement.setTransactions(new LinkedList<Transaction>());

    // Gather data.
    for(Transaction trans : statement.getAccount().getTransactions())
    {
      if(isInRange(trans, start, end) == true)
      {
        statement.getTransactions().add(trans);

        if(isExpense(trans) == true)
        {
          if(isTransfer(trans) == true)
          {
            statement.setTransferredFromTotal(statement.getTransferredFromTotal() - trans.getAmount().doubleValue());
          }
          else
          {
            statement.setExpenseTotal(statement.getExpenseTotal() - trans.getAmount().doubleValue());
          }
        }
        else
        {
          if(isTransfer(trans) == true)
          {
            statement.setTransferredToTotal(statement.getTransferredToTotal() + trans.getAmount().doubleValue());
          }
          else
          {
            statement.setIncomeTotal(statement.getIncomeTotal() + trans.getAmount().doubleValue());
          }
        }
      }
    }

    return statement;
  }

  /**
   * This method returns the account the statement pertains to.
   *
   * @return The account the statement pertains to.
   */
  public
  Account
  getAccount()
  {
    return itsAccount;
  }

  /**
   * This method returns the expense total for this statement.
   *
   * @return The expense total for this statement.
   */
  public
  double
  getExpenseTotal()
  {
    return itsExpenseTotal;
  }

  /**
   * This method returns the income total for this statement.
   *
   * @return The income total for this statement.
   */
  public
  double
  getIncomeTotal()
  {
    return itsIncomeTotal;
  }

  /**
   * This method returns the statement's transactions.
   *
   * @return The statement's transactions.
   */
  public
  Collection<Transaction>
  getTransactions()
  {
    return itsTransactions;
  }

  /**
   * This method returns the total amount transferred from the account
   * pertaining to this statement.
   *
   * @return The total amount transferred from the account pertaining to this
   * statement.
   */
  public
  double
  getTransferredFromTotal()
  {
    return itsTransferredFromTotal;
  }

  /**
   * This method returns the total amount transferred to the account pertaining
   * to this statement.
   *
   * @return The total amount transferred to the account pertaining to this
   * statement.
   */
  public
  double
  getTransferredToTotal()
  {
    return itsTransferredToTotal;
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of private methods.
  //////////////////////////////////////////////////////////////////////////////

  private
  void
  setAccount(Account account)
  {
    itsAccount = account;
  }

  private
  void
  setExpenseTotal(double amount)
  {
    itsExpenseTotal = amount;
  }

  private
  void
  setIncomeTotal(double amount)
  {
    itsIncomeTotal = amount;
  }

  private
  void
  setTransactions(Collection<Transaction> collection)
  {
    itsTransactions = collection;
  }

  private
  void
  setTransferredFromTotal(double amount)
  {
    itsTransferredFromTotal = amount;
  }

  private
  void
  setTransferredToTotal(double amount)
  {
    itsTransferredToTotal = amount;
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  private Account itsAccount;
  private double itsExpenseTotal;
  private double itsIncomeTotal;
  private Collection<Transaction> itsTransactions;
  private double itsTransferredFromTotal;
  private double itsTransferredToTotal;
}
