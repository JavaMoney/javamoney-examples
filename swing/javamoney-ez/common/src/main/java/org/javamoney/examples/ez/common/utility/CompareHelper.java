// CompareHelper

package org.javamoney.examples.ez.common.utility;

import java.text.Collator;
import java.util.Date;

import javax.money.MonetaryAmount;

import org.javamoney.moneta.Money;

/**
 * This class facilitates comparing. All methods in this class are static.
 */
public
final
class
CompareHelper
{
  /**
   * This method returns the result of comparing two amounts.
   *
   * @param amount1 An amount to compare.
   * @param amount2 An amount to compare.
   *
   * @return The result of comparing two amounts.
   */
  public
  static
  int
  compareAmounts(double amount1, double amount2)
  {
    return compareAmounts(amount1, amount2, false);
  }

  /**
   * TThis method returns the result of comparing two amounts.
   *
   * @param amount1 An amount to compare.
   * @param amount2 An amount to compare.
   * @param invert Whether or not to invert the sort.
   *
   * @return The result of comparing two amounts.
   */
  public
  static
  int
  compareAmounts(double amount1, double amount2, boolean invert)
  {
    Double double1 = new Double(amount1);
    Double double2 = new Double(amount2);
    int result = 0;

    if(invert == true)
    {
      result = double2.compareTo(double1);
    }
    else
    {
      result = double1.compareTo(double2);
    }

    return result;
  }
  
  /**
   * TThis method returns the result of comparing two amounts.
   *
   * @param amount1 An amount to compare.
   * @param amount2 An amount to compare.
   * @param invert Whether or not to invert the sort.
   *
   * @return The result of comparing two amounts.
   */
  public
  static
  int
  compareAmounts(MonetaryAmount amount1, MonetaryAmount amount2, boolean invert)
  {
    int result = 0;
    
    if(invert == true)
    {
      if (amount2 instanceof Money) {
    	  result = ((Money)amount2).compareTo(amount1);
      }
    }
    else
    {
    	 if (amount2 instanceof Money) {
       	  result = ((Money)amount1).compareTo(amount2);
    	 }
    }

    return result;
  }
  
  /**
   * This method returns the result of comparing two amounts.
   *
   * @param amount1 An amount to compare.
   * @param amount2 An amount to compare.
   *
   * @return The result of comparing two amounts.
   */
  public
  static
  int
  compareAmounts(MonetaryAmount amount1, MonetaryAmount amount2)
  {
    return compareAmounts(amount1, amount2, false);
  }

  /**
   * This method returns the result of comparing two booleans.
   *
   * @param bool1 A boolean to compare.
   * @param bool2 A boolean to compare.
   *
   * @return The result of comparing two booleans.
   */
  public
  static
  int
  compareBooleans(boolean bool1, boolean bool2)
  {
    return compareBooleans(bool1, bool2, false);
  }

  /**
   * This method returns the result of comparing two booleans.
   *
   * @param bool1 A boolean to compare.
   * @param bool2 A boolean to compare.
   * @param invert Whether or not to invert the sort.
   *
   * @return The result of comparing two booleans.
   */
  public
  static
  int
  compareBooleans(boolean bool1, boolean bool2, boolean invert)
  {
    return compareBooleans(new Boolean(bool1), new Boolean(bool2), invert);
  }

  /**
   * This method returns the result of comparing two booleans.
   *
   * @param bool1 A boolean to compare.
   * @param bool2 A boolean to compare.
   * @param invert Whether or not to invert the sort.
   *
   * @return The result of comparing two booleans.
   */
  public
  static
  int
  compareBooleans(Boolean bool1, Boolean bool2, boolean invert)
  {
    int result = 0;

    if(invert == true)
    {
      result = bool2.compareTo(bool1);
    }
    else
    {
      result = bool1.compareTo(bool2);
    }

    return result;
  }

  /**
   * This method returns the result of comparing two dates.
   *
   * @param date1 A date to compare.
   * @param date2 A date to compare.
   * @param invert Whether or not to invert the sort.
   *
   * @return The result of comparing two dates.
   */
  public
  static
  int
  compareDates(Date date1, Date date2, boolean invert)
  {
    int result = 0;

    if(invert == true)
    {
      result = date2.compareTo(date1);
    }
    else
    {
      result = date1.compareTo(date2);
    }

    return result;
  }

  /**
   * This method returns the result of comparing two enum keys.
   *
   * @param enum1 A key to compare.
   * @param enum2 A key to compare.
   * @param invert Whether or not to invert the sort.
   *
   * @return The result of comparing two enum keys.
   */
  public
  static
  int
  compareKeys(Enum<?> enum1, Enum<?> enum2, boolean invert)
  {
    return compareStrings(enum1.toString(), enum2.toString(), invert);
  }

  /**
   * This method returns the result of comparing two object via toString().
   *
   * @param object1 An object to compare.
   * @param object2 An object to compare.
   * @param invert Whether or not to invert the sort.
   *
   * @return The result of comparing two objects via toString().
   */
  public
  static
  int
  compareObjects(Object object1, Object object2, boolean invert)
  {
    return compareStrings(object1.toString(), object2.toString(), invert);
  }

  /**
   * This method returns the result of comparing two strings. This uses a
   * collator to account for the user's locale.
   *
   * @param str1 A string to compare.
   * @param str2 A string to compare.
   * @param invert Whether or not to invert the sort.
   *
   * @return The result of comparing two strings.
   */
  public
  static
  int
  compareStrings(String str1, String str2, boolean invert)
  {
    int result = 0;

    if(invert == true)
    {
      result = COLLATOR.compare(str2, str1);
    }
    else
    {
      result = COLLATOR.compare(str1, str2);
    }

    return result;
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  private static final Collator COLLATOR = Collator.getInstance();
}
