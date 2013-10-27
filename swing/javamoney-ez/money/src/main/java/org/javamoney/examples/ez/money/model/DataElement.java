// DataElement

package org.javamoney.examples.ez.money.model;

import static org.javamoney.examples.ez.common.utility.CompareHelper.compareObjects;

/**
 * This class is the base class for all elements that wish to be managed in a
 * collection.
 */
public
class
DataElement
implements Comparable<DataElement>
{
  /**
   * Constructs a new element.
   *
   * @param identifier The identifier.
   */
  public
  DataElement(String identifier)
  {
    setIdentifier(identifier);
  }

  /**
   * This method returns the result of comparing the element with the passed in
   * element. Two elements are considered equal if their identifiers are the
   * same.
   *
   * @param element The element to compare to.
   *
   * @return The result of comparing the element with the passed in element.
   */
  public
  final
  int
  compareTo(DataElement element)
  {
    return compareObjects(this, element, false);
  }

  /**
   * This method returns the identifier.
   *
   * @return The identifier.
   */
  public
  final
  String
  getIdentifier()
  {
    return itsIdentifier;
  }

  /**
   * This method sets the identifier.
   *
   * @param identifier The identifier.
   */
  public
  final
  void
  setIdentifier(String identifier)
  {
    itsIdentifier = identifier;
  }

  /**
   * This method returns the identifier.
   *
   * @return The identifier.
   */
  @Override
  public
  final
  String
  toString()
  {
    return getIdentifier();
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  private String itsIdentifier;
}
