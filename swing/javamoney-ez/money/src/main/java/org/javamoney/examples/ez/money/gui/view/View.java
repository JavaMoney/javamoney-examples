// View

package org.javamoney.examples.ez.money.gui.view;

import org.javamoney.examples.ez.common.gui.Panel;

/**
 * This class represents the base class for all classes that provide a view.
 */
public
abstract
class
View
extends Panel
{
  /**
   * Constructs a new view with the specified key.
   *
   * @param key The view's key.
   */
  public
  View(ViewKeys key)
  {
    setKey(key);
  }

  /**
   * This method returns the view's key.
   *
   * @return The view's key.
   */
  public
  ViewKeys
  getKey()
  {
    return itsKey;
  }

  /**
   * This method updates the view.
   */
  public
  abstract
  void
  updateView();

  //////////////////////////////////////////////////////////////////////////////
  // Start of private methods.
  //////////////////////////////////////////////////////////////////////////////

  private
  void
  setKey(ViewKeys key)
  {
    itsKey = key;
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  private ViewKeys itsKey;
}
