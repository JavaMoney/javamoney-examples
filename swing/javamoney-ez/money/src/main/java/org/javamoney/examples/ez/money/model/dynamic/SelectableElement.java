// SelectableElement

package org.javamoney.examples.ez.money.model.dynamic;

import org.javamoney.examples.ez.money.model.DataElement;

/**
 * This class facilitates maintaining a selected state for an element where the
 * identifier is the key for that element.
 */
public
class
SelectableElement
extends DataElement
{
  /**
   * Constructs a new selectable element.
   *
   * @param identifier The identifier.
   */
  public
  SelectableElement(String identifier)
  {
    super(identifier);
  }

  /**
   * This method returns true if the element is selected, otherwise false.
   *
   * @return true or false.
   */
  public
  boolean
  isSelected()
  {
    return itsIsSelected;
  }

  /**
   * This method sets whether or not the element is selected.
   *
   * @param value true or false.
   */
  public
  void
  setIsSelected(boolean value)
  {
    itsIsSelected = value;
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  private boolean itsIsSelected;
}
