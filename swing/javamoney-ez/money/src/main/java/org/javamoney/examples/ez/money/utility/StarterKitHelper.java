// StarterKitHelper

package org.javamoney.examples.ez.money.utility;

import static org.javamoney.examples.ez.money.model.DataManager.getAccounts;
import static org.javamoney.examples.ez.money.model.DataManager.getExpenses;
import static org.javamoney.examples.ez.money.model.DataManager.getIncome;
import static org.javamoney.examples.ez.money.model.DataManager.getReminders;
import static org.javamoney.examples.ez.money.model.persisted.account.AccountTypeKeys.CREDIT;
import static org.javamoney.examples.ez.money.model.persisted.account.AccountTypeKeys.DEPOSIT;

import org.javamoney.examples.ez.money.model.persisted.account.Account;
import org.javamoney.examples.ez.money.model.persisted.category.Category;
import org.javamoney.examples.ez.money.model.persisted.reminder.Reminder;

import org.javamoney.examples.ez.common.utility.I18NHelper;

/**
 * This class facilitates creating a starter kit. All methods in this class are
 * static.
 */
public
final
class
StarterKitHelper
{
  /**
   * This method creates some generic accounts and categories to get the user
   * started with the least amount of effort.
   */
  public
  static
  void
  createStarterKit()
  {
    // Accounts.
    getAccounts().add(new Account(CREDIT, getAccount("credit")));
    getAccounts().add(new Account(DEPOSIT, getAccount("checking")));
    getAccounts().add(new Account(DEPOSIT, getAccount("savings")));

    // Expenses.
    Category automotive = createExpense("automotive");
    Category bills = createExpense("bills");
    Category clothing = createExpense("clothing");
    Category computer = createExpense("computer");
    Category food = createExpense("food");
    Category insurance = createExpense("insurance");
    Category leisure = createExpense("leisure");
    Category personal = createExpense("personal");

    automotive.setBudget(100);
    automotive.setIsBudgeted(true);
    food.setBudget(500);
    food.setIsBudgeted(true);
    leisure.setBudget(250);
    leisure.setIsBudgeted(true);
    personal.setBudget(100);
    personal.setIsBudgeted(true);

    getExpenses().add(automotive);
    getExpenses().add(bills);
    getExpenses().add(clothing);
    getExpenses().add(computer);
    getExpenses().add(food);
    getExpenses().add(insurance);
    getExpenses().add(leisure);
    getExpenses().add(personal);
    getExpenses().add(createExpense("cash"));
    getExpenses().add(createExpense("gifts"));
    getExpenses().add(createExpense("healthcare"));
    getExpenses().add(createExpense("other"));
    getExpenses().add(createExpense("travel"));
    getExpenses().addToGroup(automotive, createExpense("automotive.gas"));
    getExpenses().addToGroup(automotive, createExpense("automotive.maintenance"));
    getExpenses().addToGroup(clothing, createExpense("clothing.shoes"));
    getExpenses().addToGroup(computer, createExpense("computer.hardware"));
    getExpenses().addToGroup(computer, createExpense("computer.other"));
    getExpenses().addToGroup(computer, createExpense("computer.software"));
    getExpenses().addToGroup(bills, createExpense("bills.cable"));
    getExpenses().addToGroup(bills, createExpense("bills.energy"));
    getExpenses().addToGroup(bills, createExpense("bills.internet"));
    getExpenses().addToGroup(bills, createExpense("bills.mortgage"));
    getExpenses().addToGroup(bills, createExpense("bills.rent"));
    getExpenses().addToGroup(bills, createExpense("bills.telephone"));
    getExpenses().addToGroup(bills, createExpense("bills.water"));
    getExpenses().addToGroup(insurance, createExpense("insurance.automobile"));
    getExpenses().addToGroup(insurance, createExpense("insurance.health"));
    getExpenses().addToGroup(insurance, createExpense("insurance.home"));
    getExpenses().addToGroup(leisure, createExpense("leisure.books"));
    getExpenses().addToGroup(leisure, createExpense("leisure.dining"));
    getExpenses().addToGroup(leisure, createExpense("leisure.movies"));
    getExpenses().addToGroup(personal, createExpense("personal.body"));

    // Income.
    Category other = createIncome("other");

    getIncome().add(other);
    getIncome().add(createIncome("net_pay"));
    getIncome().addToGroup(other, createIncome("other.bonus"));
    getIncome().addToGroup(other, createIncome("other.gift"));

    // Reminders.
    getReminders().add(new Reminder(getProperty("reminder.credit"), 0));
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of private methods.
  //////////////////////////////////////////////////////////////////////////////

  private
  static
  Category
  createExpense(String key)
  {
    return new Category(getProperty("expense." + key));
  }

  private
  static
  Category
  createIncome(String key)
  {
    return new Category(getProperty("income." + key));
  }

  private
  static
  String
  getAccount(String key)
  {
    return getProperty("account." + key);
  }

  private
  static
  String
  getProperty(String key)
  {
    return I18NHelper.getProperty("StarterKitHelper." + key);
  }
}
