// CategoryTotal

package org.javamoney.examples.ez.money.model.dynamic.total;

import org.javamoney.examples.ez.money.model.persisted.category.Category;

/**
 * This class facilitates tracking a group of related transactions for a
 * specific category.
 */
class
CategoryTotal
extends Total
{
  /**
   * This method returns the type.
   *
   * @return The type.
   */
  public
  final
  CategoryTotalTypeKeys
  getType()
  {
    return itsType;
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of protected methods.
  //////////////////////////////////////////////////////////////////////////////

  /**
   * Constructs a new category total.
   *
   * @param type The type.
   * @param identifier The identifier.
   * @param category The category being totaled.
   */
  protected
  CategoryTotal(CategoryTotalTypeKeys type, String identifier, Category category)
  {
    super(identifier);

    setCategory(category);
    setType(type);
  }

  /**
   * This method returns the category being totaled.
   *
   * @return The category being totaled.
   */
  protected
  final
  Category
  getCategory()
  {
    return itsCategory;
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of private methods.
  //////////////////////////////////////////////////////////////////////////////

  private
  void
  setCategory(Category category)
  {
    itsCategory = category;
  }

  private
  void
  setType(CategoryTotalTypeKeys type)
  {
    itsType = type;
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  private Category itsCategory;
  private CategoryTotalTypeKeys itsType;
}
