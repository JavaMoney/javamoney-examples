// TransferTotal

package org.javamoney.examples.ez.money.model.dynamic.total;

import org.javamoney.examples.ez.money.model.persisted.account.Account;

/**
 * This class facilitates tracking an account's transfer totals.
 */
public
final
class
TransferTotal
extends Total
{
  /**
   * Constructs a new transfer total.
   *
   * @param account The account to keep track of its transfer totals.
   */
  public
  TransferTotal(Account account)
  {
    super(account.getIdentifier());

    setAccount(account);
  }

  /**
   * This method returns the account the transfer total is referencing.
   *
   * @return The account the transfer total is referencing.
   */
  public
  Account
  getAccount()
  {
    return itsAccount;
  }

  /**
   * This method returns how much has been transferred out.
   *
   * @return How much has been transferred out.
   */
  public
  double
  getFromTotal()
  {
    return itsFromTotal;
  }

  /**
   * This method returns how much has been transferred in.
   *
   * @return How much has been transferred in.
   */
  public
  double
  getToTotal()
  {
    return itsToTotal;
  }

  /**
   * This method sets how much has been transferred out.
   *
   * @param value How much has been transferred out.
   */
  public
  void
  setFromTotal(double value)
  {
    itsFromTotal = value;
  }

  /**
   * This method sets how much has been transferred in.
   *
   * @param value How much has been transferred in.
   */
  public
  void
  setToTotal(double value)
  {
    itsToTotal = value;
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

  //////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  private Account itsAccount;
  private double itsFromTotal;
  private double itsToTotal;
}
