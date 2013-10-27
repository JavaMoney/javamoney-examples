// Category

package org.javamoney.examples.ez.money.model.persisted.category;

import static org.javamoney.examples.ez.money.importexport.QIFConstants.CATEGORY_SEPARATOR;

import java.util.Collection;
import java.util.Date;
import java.util.TreeSet;

import org.javamoney.examples.ez.money.model.DataElement;

/**
 * This class represents the category for a transaction. Categories can have
 * subcategories. A Category that has subcategories is considered a group.
 */
public
final
class
Category
extends DataElement
{
  /**
   * Constructs a new category.
   *
   * @param identifier The identifier.
   */
  public
  Category(String identifier)
  {
    super(identifier);

    setBudget(0);
    setGroup(null);
    setHasRolloverBudget(false);
    setIsBudgeted(false);
    setRolloverStartDate(new Date());
    setSubcategories(new TreeSet<DataElement>());
  }

  /**
   * This method returns true if the category can be budgeted, otherwise false.
   * <p>
   * <b>Note:</b> Only top-level categories can be budgeted.
   *
   * @return true or false.
   */
  public
  boolean
  canBeBudgeted()
  {
    return hasGroup() == false;
  }

  /**
   * This method returns the monthly budget.
   *
   * @return The monthly budget.
   */
  public
  int
  getBudget()
  {
    return itsBudget;
  }

  /**
   * This method returns the group, or null if it does not have one.
   *
   * @return The group.
   */
  public
  Category
  getGroup()
  {
    return itsGroup;
  }

  /**
   * This method returns the group name.
   *
   * @return The group name.
   */
  public
  String
  getGroupName()
  {
    String name = "";

    if(getGroup() != null)
    {
      name = getGroup().getQIFName();
    }

    return name;
  }

  /**
   * This method returns the "Quicken Interchange Format" name for the category.
   *
   * @return The "Quicken Interchange Format" name.
   */
  public
  String
  getQIFName()
  {
    String name = "";

    // Recursively build the QIF name.
    if(getGroup() != null)
    {
      name = getGroup().getQIFName() + CATEGORY_SEPARATOR;
    }

    return name + getIdentifier();
  }

  /**
   * The method returns the date the budget should start rolling over.
   *
   * @return The date the budget should start rolling over.
   */
  public
  Date
  getRolloverStartDate()
  {
    return itsRolloverStartDate;
  }

  /**
   * This method returns the collection of subcategories.
   *
   * @return The subcategories.
   */
  public
  Collection<DataElement>
  getSubcategories()
  {
    return itsSubcategories;
  }

  /**
   * This method returns true if the category has a group, otherwise false.
   *
   * @return true or false.
   */
  public
  boolean
  hasGroup()
  {
    return getGroup() != null;
  }

  /**
   * This method returns true if the budget should rollover each month,
   * otherwise false.
   *
   * @return true or false.
   */
  public
  boolean
  hasRolloverBudget()
  {
    return itsHasRolloverBudget;
  }

  /**
   * This method returns true if the category is budgeted, otherwise false.
   *
   * @return true or false.
   */
  public
  boolean
  isBudgeted()
  {
    return itsIsBudgeted;
  }

  /**
   * This method returns true if the category is a group, otherwise false.
   *
   * @return true or false.
   */
  public
  boolean
  isGroup()
  {
    return getSubcategories().size() != 0;
  }

  /**
   * This method returns true if the category is the group for the specified
   * category, otherwise false.
   *
   * @param category The category in question.
   *
   * @return true or false.
   */
  public
  boolean
  isGroupFor(Category category)
  {
    boolean result = false;

    if(isGroup() == true)
    {
      String name = category.getQIFName() + CATEGORY_SEPARATOR;

      result = name.startsWith(getQIFName());
    }

    return result;
  }

  /**
   * This method returns true if the category or its group category equals the
   * specified group, otherwise false.
   *
   * @param group The category in question.
   *
   * @return true or false.
   */
  public
  boolean
  isInGroup(Category group)
  {
    return this == group || getGroup() == group;
  }

  /**
   * This method sets the monthly budget.
   *
   * @param value The monthly budget.
   */
  public
  void
  setBudget(int value)
  {
    itsBudget = value;
  }

  /**
   * This method sets whether or not the category's budget should rollover each
   * month.
   *
   * @param value true or false.
   */
  public
  void
  setHasRolloverBudget(boolean value)
  {
    itsHasRolloverBudget = value;
  }

  /**
   * This method sets whether or not the category should be budgeted.
   *
   * @param value true or false.
   */
  public
  void
  setIsBudgeted(boolean value)
  {
    itsIsBudgeted = value;
  }

  /**
   * The method sets the date the budget should start rolling over.
   *
   * @param date The date the budget should start rolling over.
   */
  public
  void
  setRolloverStartDate(Date date)
  {
    itsRolloverStartDate = date;
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of protected methods.
  //////////////////////////////////////////////////////////////////////////////

  /**
   * This method sets the group.
   *
   * @param group The group.
   */
  protected
  void
  setGroup(Category group)
  {
    itsGroup = group;
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of private methods.
  //////////////////////////////////////////////////////////////////////////////

  private
  void
  setSubcategories(Collection<DataElement> collection)
  {
    itsSubcategories = collection;
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  private int itsBudget;
  private Category itsGroup;
  private boolean itsHasRolloverBudget;
  private boolean itsIsBudgeted;
  private Date itsRolloverStartDate;
  private Collection<DataElement> itsSubcategories;
}
