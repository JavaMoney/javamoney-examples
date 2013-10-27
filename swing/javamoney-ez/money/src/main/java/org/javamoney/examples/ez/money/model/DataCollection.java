// DataCollection

package org.javamoney.examples.ez.money.model;

import java.util.Collection;
import java.util.TreeSet;

/**
 * This class facilitates storing and managing data elements.
 */
public
abstract
class
DataCollection
{
  /**
   * Constructs a new collection.
   *
   * @param type The type of data to store.
   */
  public
  DataCollection(DataTypeKeys type)
  {
    setCollection(new TreeSet<DataElement>());
    setType(type);
  }

  /**
   * This method adds the element, then returns the success of the operation.
   *
   * @param element The element to add.
   *
   * @return true or false.
   */
  public
  final
  boolean
  add(DataElement element)
  {
    return getCollection().add(element);
  }

  /**
   * This method changes the identifier of a currently stored element. This
   * method returns the success of the operation.
   *
   * @param element The element to change.
   * @param newId The new identifier.
   *
   * @return true or false.
   */
  public
  final
  boolean
  changeIdentifier(DataElement element, String newId)
  {
    DataElement temp = new DataElement(newId);
    boolean result = false;

    // Is there an element with the new identifier already?
    if(getCollection().contains(temp) == false)
    {
      result = remove(element);

      // Make sure the element is removed before adding it again.
      if(result == true)
      {
        element.setIdentifier(newId);
        result = add(element);
      }
    }

    return result;
  }

  /**
   * This method returns a reference to the element that has the specified
   * identifier.
   *
   * @param identifier The element to look for.
   *
   * @return A reference to the element.
   */
  public
  final
  DataElement
  get(String identifier)
  {
    DataElement elementAt = null;

    // Iterate until found.
    for(DataElement element : getCollection())
    {
      if(element.getIdentifier().equals(identifier) == true)
      {
        elementAt = element;
        break;
      }
    }

    return elementAt;
  }

  /**
   * This method returns the collection.
   *
   * @return The collection.
   */
  public
  final
  Collection<DataElement>
  getCollection()
  {
    return itsCollection;
  }

  /**
   * This method returns type.
   *
   * @return This type.
   */
  public
  final
  DataTypeKeys
  getType()
  {
    return itsType;
  }

  /**
   * This method removes the element then returns the success of the operation.
   *
   * @param element The element to remove.
   *
   * @return true or false.
   */
  public
  final
  boolean
  remove(DataElement element)
  {
    return getCollection().remove(element);
  }

  /**
   * This method returns the element count.
   *
   * @return The element count.
   */
  public
  final
  int
  size()
  {
    return getCollection().size();
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of private methods.
  //////////////////////////////////////////////////////////////////////////////

  private
  void
  setCollection(Collection<DataElement> collection)
  {
    itsCollection = collection;
  }

  private
  void
  setType(DataTypeKeys type)
  {
    itsType = type;
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  private Collection<DataElement> itsCollection;
  private DataTypeKeys itsType;
}
