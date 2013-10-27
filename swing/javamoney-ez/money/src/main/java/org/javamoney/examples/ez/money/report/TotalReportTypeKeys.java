// TotalReportTypeKeys

package org.javamoney.examples.ez.money.report;

import org.javamoney.examples.ez.common.utility.I18NHelper;

/**
 * This enumerated class provides keys for the types of total reports.
 */
public
enum
TotalReportTypeKeys
{
  /**
   * An expense report.
   */
  EXPENSES(I18NHelper.getSharedProperty("expenses")),
  /**
   * An income report.
   */
  INCOME(I18NHelper.getSharedProperty("income")),
  /**
   * A transfer report.
   */
  TRANSFERS(I18NHelper.getSharedProperty("transfers"));

  //////////////////////////////////////////////////////////////////////////////
  // Start of public methods.
  //////////////////////////////////////////////////////////////////////////////

  /**
   * This method returns a string for the enum constant.
   *
   * @return A string.
   */
  @Override
  public
  String
  toString()
  {
    return itsIdentifier;
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of private methods.
  //////////////////////////////////////////////////////////////////////////////

  private
  TotalReportTypeKeys(String identifier)
  {
    itsIdentifier = identifier;
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  private String itsIdentifier;
}
