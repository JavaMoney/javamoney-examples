// TotalFilter

package org.javamoney.examples.ez.money.model.dynamic.total;

import static java.util.Arrays.binarySearch;

import org.javamoney.examples.ez.money.model.persisted.account.Account;
import org.javamoney.examples.ez.money.model.persisted.category.Category;
import org.javamoney.examples.ez.money.model.persisted.transaction.Transaction;

/**
 * This class facilitates providing a way to filter totals.
 */
public
final
class
TotalFilter
{
  /**
   * Constructs a new filter.
   */
  public
  TotalFilter()
  {
    setAccounts(ALL);
    setCategories(ALL);
    setEnabled(false);
    setPayees(ALL);
    setReconciledOnly(true);
  }

  /**
   * This method returns true if the specified account can be reported on,
   * otherwise false.
   *
   * @param account The account to check.
   *
   * @return true or false.
   */
  public
  boolean
  allowsAccount(Account account)
  {
    boolean result = true;

    if(isEnabled() == true)
    {
      result = containsIn(getAccounts(), account.getIdentifier());
    }

    return result;
  }

  /**
   * This method returns true if the specified category can be reported on,
   * otherwise false.
   *
   * @param category The category to check.
   *
   * @return true or false.
   */
  public
  boolean
  allowsCategory(Category category)
  {
    boolean result = true;

    if(isEnabled())
    {
      result = containsIn(getCategories(), category.getQIFName());
    }

    return result;
  }

  /**
   * This method returns true if the specified transaction can be reported on,
   * otherwise false.
   *
   * @param trans The transaction to check.
   *
   * @return true or false.
   */
  public
  boolean
  allowsCategory(Transaction trans)
  {
    boolean result = true;

    if(isEnabled() == true)
    {
      result = containsIn(getCategories(), trans.getCategory());
    }

    return result;
  }

  /**
   * This method returns true if the specified transaction can be reported on,
   * otherwise false.
   *
   * @param trans The transaction to check.
   *
   * @return true or false.
   */
  public
  boolean
  allowsPayee(Transaction trans)
  {
    boolean result = true;

    if(isEnabled() == true)
    {
      result = containsIn(getPayees(), trans.getPayee());
    }

    return result;
  }

  /**
   * This method returns true if the specified transaction can be reported on,
   * otherwise false.
   *
   * @param trans The transaction to check.
   *
   * @return true or false.
   */
  public
  boolean
  allowsReconciledStatus(Transaction trans)
  {
    boolean result = true;

    if(isEnabled() == true && reconciledEnabled() == true)
    {
      result = reconciledOnly() == trans.isReconciled();
    }

    return result;
  }

  /**
   * This method returns the list of accounts to filter for.
   *
   * @return The list of accounts to filter for.
   */
  public
  String[]
  getAccounts()
  {
    return itsAccounts;
  }

  /**
   * This method returns the list of categories to filter for.
   *
   * @return The list of categories to filter for.
   */
  public
  String[]
  getCategories()
  {
    return itsCategories;
  }

  /**
   * This method returns the list of payees to filter for.
   *
   * @return The list of payees to filter for.
   */
  public
  String[]
  getPayees()
  {
    return itsPayees;
  }

  /**
   * This method returns true if filtering is enabled, otherwise false.
   *
   * @return true or false.
   */
  public
  boolean
  isEnabled()
  {
    return itsIsEnabled;
  }

  /**
   * This method returns true the reconciled status should be checked, otherwise
   * false.
   *
   * @return true or false.
   */
  public
  boolean
  reconciledEnabled()
  {
    return itsReconciledEnabled;
  }

  /**
   * This method returns true if only reconciled transactions can be reported
   * on, otherwise false.
   *
   * @return true or false.
   */
  public
  boolean
  reconciledOnly()
  {
    return itsReconciledOnly;
  }

  /**
   * This method sets the list of accounts to filter for.
   *
   * @param accounts The list of accounts to filter for.
   */
  public
  void
  setAccounts(String[] accounts)
  {
    itsAccounts = accounts;
  }

  /**
   * This method sets the list of categories to filter for.
   *
   * @param categories The list of categories to filter for.
   */
  public
  void
  setCategories(String[] categories)
  {
    itsCategories = categories;
  }

  /**
   * This method sets whether or not filtering is enabled.
   *
   * @param value true or false.
   */
  public
  void
  setEnabled(boolean value)
  {
    itsIsEnabled = value;
  }

  /**
   * This method sets the list of payees to filter for.
   *
   * @param payees The list of payees to filter for.
   */
  public
  void
  setPayees(String[] payees)
  {
    itsPayees = payees;
  }

  /**
   * This method sets whether or not the reconciled status should be checked.
   *
   * @param value true or false.
   */
  public
  void
  setReconciledEnabled(boolean value)
  {
    itsReconciledEnabled = value;
  }

  /**
   * This method sets whether or not only reconciled transactions can be
   * reported on.
   *
   * @param value true or false.
   */
  public
  void
  setReconciledOnly(boolean value)
  {
    itsReconciledOnly = value;
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of private methods.
  //////////////////////////////////////////////////////////////////////////////

  private
  static
  boolean
  containsIn(String[] types, String element)
  {
    return types == ALL || binarySearch(types, element) >= 0;
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  private String[] itsAccounts;
  private String[] itsCategories;
  private boolean itsIsEnabled;
  private String[] itsPayees;
  private boolean itsReconciledEnabled;
  private boolean itsReconciledOnly;

  /**
   * A constant for indicating that all values are to be allowed.
   */
  public static final String[] ALL = new String[0];
}
