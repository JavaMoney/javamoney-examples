// CategoryCollection

package org.javamoney.examples.ez.money.model.persisted.category;

import static org.javamoney.examples.ez.money.importexport.QIFConstants.CATEGORY_SEPARATOR;
import static org.javamoney.examples.ez.money.model.DataTypeKeys.CATEGORY;

import java.util.StringTokenizer;

import org.javamoney.examples.ez.money.model.DataCollection;
import org.javamoney.examples.ez.money.model.DataElement;

/**
 * This class facilitates managing categories and their subcategories.
 */
public
final
class
CategoryCollection
extends DataCollection
{
  /**
   * Constructs a new collection.
   */
  public
  CategoryCollection()
  {
    super(CATEGORY);
  }

  /**
   * This method adds the category to the group's collection. This method
   * returns the success of the operation.
   *
   * @param group The group category to have the category added to.
   * @param category The category to add.
   *
   * @return true or false.
   */
  public
  boolean
  addToGroup(Category group, Category category)
  {
    boolean result = group.getSubcategories().add(category);

    if(result == true)
    {
      // This category may currently have a group. If so, remove it.
      if(category.getGroup() != null)
      {
        category.getGroup().getSubcategories().remove(category);
      }

      category.setGroup(group);
    }

    return result;
  }

  /**
   * This method changes the category's identifier, unless an element already
   * exists with that identifier in the group's collection. If the group is
   * null, then this method assumes the category is in this root collection.
   * This method returns the success of the operation.
   *
   * @param group The group category.
   * @param category The category to change.
   * @param newId The new identifier.
   *
   * @return true or false.
   */
  public
  boolean
  changeIdentifier(Category group, Category category, String newId)
  {
    boolean result = false;

    // If there is no group, then the category belongs to the underlying
    // collection.
    if(group == null)
    {
      result = super.changeIdentifier(category, newId);
    }
    else
    {
      Category temp = new Category(newId);

      // Is there a category with the new identifier already?
      if(group.getSubcategories().contains(temp) == false)
      {
        result = group.getSubcategories().remove(category);

        // Make sure the category was removed before adding it back.
        if(result == true)
        {
          category.setIdentifier(newId);
          result = group.getSubcategories().add(category);
        }
      }
    }

    return result;
  }

  /**
   * This method returns a reference to the category indicated by the QIF
   * string.
   *
   * @param qif The QIF string.
   *
   * @return A reference to the category that matches the QIF string.
   */
  public
  Category
  getCategoryFromQIF(String qif)
  {
    StringTokenizer tokens = new StringTokenizer(qif, CATEGORY_SEPARATOR);
    Category categoryAt = null;

    while(tokens.hasMoreTokens() == true)
    {
      categoryAt = (Category)getFromGroup(categoryAt, tokens.nextToken());
    }

    return categoryAt;
  }

  /**
   * This method returns a reference to the category from the group category's
   * collection that has the specified identifier.
   *
   * @param group The group category.
   * @param identifier The unique identifier to look for.
   *
   * @return A reference to the category.
   */
  public
  DataElement
  getFromGroup(Category group, String identifier)
  {
    DataElement categoryAt = null;

    if(group == null)
    {
      categoryAt = get(identifier);
    }
    else
    {
      // Iterate until found.
      for(DataElement category : group.getSubcategories())
      {
        if(category.getIdentifier().equals(identifier) == true)
        {
          categoryAt = category;
          break;
        }
      }
    }

    return categoryAt;
  }

  /**
   * This method removes the category then returns the success of the operation.
   *
   * @param category The category to remove.
   *
   * @return true or false.
   */
  public
  boolean
  remove(Category category)
  {
    boolean result = false;

    if(category.getGroup() == null)
    {
      result = super.remove(category);
    }
    else
    {
      result = category.getGroup().getSubcategories().remove(category);

      // Make sure the category was removed before changing its group.
      if(result == true)
      {
        category.setGroup(null);
      }
    }

    return result;
  }
}
