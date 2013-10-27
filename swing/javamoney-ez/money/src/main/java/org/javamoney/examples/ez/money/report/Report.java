// Report

package org.javamoney.examples.ez.money.report;

import java.util.Date;

import org.javamoney.examples.ez.money.model.dynamic.total.TotalFilter;

/**
 * This class represents the foundation for a report.
 */
abstract
class
Report
{
  /**
   * This method returns the report's end date.
   *
   * @return The report's end date.
   */
  public
  Date
  getEndDate()
  {
    return itsEndDate;
  }

  /**
   * This method returns the filter being used to filter the transactions.
   *
   * @return The filter being used to filter the transactions.
   */
  public
  TotalFilter
  getFilter()
  {
    return itsFilter;
  }

  /**
   * This method returns the report'sstart date.
   *
   * @return The report's start date.
   */
  public
  Date
  getStartDate()
  {
    return itsStartDate;
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of protected methods.
  //////////////////////////////////////////////////////////////////////////////

  /**
   * This method sets the report's end date.
   *
   * @param date The report's end date.
   */
  protected
  void
  setEndDate(Date date)
  {
    itsEndDate = date;
  }

  /**
   * This method sets the filter to use for filtering the transactions.
   *
   * @param filter The filter to use for filtering the transactions.
   */
  protected
  void
  setFilter(TotalFilter filter)
  {
    itsFilter = filter;
  }

  /**
   * This method sets the report'sstart date.
   *
   * @param date The report's start date.
   */
  protected
  void
  setStartDate(Date date)
  {
    itsStartDate = date;
  }

  /////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  private Date itsEndDate;
  private TotalFilter itsFilter;
  private Date itsStartDate;
}
