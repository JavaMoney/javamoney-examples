// DataManager

package org.javamoney.examples.ez.money.model;

import static org.javamoney.examples.ez.money.ApplicationProperties.passwordRequired;
import static org.javamoney.examples.ez.money.FileKeys.DATA;
import static org.javamoney.examples.ez.money.utility.FileMapHelper.getFileMap;

import java.io.File;

import org.javamoney.examples.ez.money.model.persisted.account.AccountCollection;
import org.javamoney.examples.ez.money.model.persisted.category.CategoryCollection;
import org.javamoney.examples.ez.money.model.persisted.payee.PayeeCollection;
import org.javamoney.examples.ez.money.model.persisted.reminder.ReminderCollection;

/**
 * This class facilitates management of all the collections. All methods in this
 * class are static.
 */
public
final
class
DataManager
{
  /**
   * This method returns the collection of accounts.
   *
   * @return The collection of accounts.
   */
  public
  static
  AccountCollection
  getAccounts()
  {
    return itsAccounts;
  }

  /**
   * This method returns the collection of expense categories.
   *
   * @return The collection of expense categories.
   */
  public
  static
  CategoryCollection
  getExpenses()
  {
    return itsExpenses;
  }

  /**
   * This method returns the collection of income categories.
   *
   * @return The collection of income categories.
   */
  public
  static
  CategoryCollection
  getIncome()
  {
    return itsIncome;
  }

  /**
   * This method returns the collection of payees.
   *
   * @return The collection of payees.
   */
  public
  static
  PayeeCollection
  getPayees()
  {
    return itsPayees;
  }

  /**
   * This method returns the collection of reminders.
   *
   * @return The collection of reminders.
   */
  public
  static
  ReminderCollection
  getReminders()
  {
    return itsReminders;
  }

  /**
   * This method reads all the collections from the default file and then
   * returns the success of the operation.
   *
   * @return true or false.
   */
  public
  static
  boolean
  read()
  {
    return read(getFileMap().get(DATA), false, passwordRequired());
  }

  /**
   * This method reads all the collections from the specified file and then
   * returns the success of the operation.
   *
   * @param file The file to have the data read from.
   * @param showError Whether or not to show an error message on failure.
   * @param decrypt Whether or not the data needs to be decrypted.
   *
   * @return true or false.
   */
  public
  static
  boolean
  read(File file, boolean showError, boolean decrypt)
  {
    boolean result = false;

    // Clear current data.
    setDefaults();

    if((result = DataIOManager.read(file, showError, decrypt)) == false)
    {
      // Clear data again if an error occurs.
      setDefaults();
    }

    return result;
  }

  /**
   * This method writes all the collections to the default file and then returns
   * the success of the operation.
   *
   * @return true or false.
   */
  public
  static
  boolean
  write()
  {
    return write(getFileMap().get(DATA), false, passwordRequired());
  }

  /**
   * This method writes all the collections to the specified file and then
   * returns the success of the operation.
   *
   * @param file The file to have the data written to.
   * @param showError Whether or not to show an error message on failure.
   * @param encrypt Whether or not the data needs to be encrypted.
   *
   * @return true or false.
   */
  public
  static
  boolean
  write(File file, boolean showError, boolean encrypt)
  {
    return DataIOManager.write(file, showError, encrypt);
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of private methods.
  //////////////////////////////////////////////////////////////////////////////

  private
  static
  void
  setAccounts(AccountCollection collection)
  {
    itsAccounts = collection;
  }

  private
  static
  void
  setDefaults()
  {
    setAccounts(new AccountCollection());
    setExpenses(new CategoryCollection());
    setIncome(new CategoryCollection());
    setPayee(new PayeeCollection());
    setReminders(new ReminderCollection());
  }

  private
  static
  void
  setExpenses(CategoryCollection collection)
  {
    itsExpenses = collection;
  }

  private
  static
  void
  setIncome(CategoryCollection collection)
  {
    itsIncome = collection;
  }

  private
  static
  void
  setPayee(PayeeCollection collection)
  {
    itsPayees = collection;
  }

  private
  static
  void
  setReminders(ReminderCollection collection)
  {
    itsReminders = collection;
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  private static AccountCollection itsAccounts;
  private static CategoryCollection itsExpenses;
  private static CategoryCollection itsIncome;
  private static PayeeCollection itsPayees;
  private static ReminderCollection itsReminders;

  //////////////////////////////////////////////////////////////////////////////
  // Start of static initialization.
  //////////////////////////////////////////////////////////////////////////////

  static
  {
    setDefaults();
  }
}
