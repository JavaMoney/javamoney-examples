// PreferencesPanel

package org.javamoney.examples.ez.money.gui.dialog.preferences;

import java.awt.Insets;

import org.javamoney.examples.ez.common.gui.Panel;

/**
 * This is the base class for all panels in the preferences dialog.
 */
public
class
PreferencesPanel
extends Panel
{
  /**
   * By default, this method does nothing. It is up to the extending classes to
   * implement this method if they choose to do so. This method is called prior
   * to the preferences dialog closing.
   */
  public
  void
  doClose()
  {
    //Ignored.
  }

  /**
   * By default, this method does nothing. It is up to the extending classes to
   * implement this method if they choose to do so. This method is called each
   * time the tab the panel belongs to is selected.
   */
  public
  void
  updateView()
  {
    // Ignored.
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of protected methods.
  //////////////////////////////////////////////////////////////////////////////

  /**
   * Constructs a new preferences panel with the specified key.
   *
   * @param key The panel's key.
   */
  protected
  PreferencesPanel(PreferencesKeys key)
  {
    setKey(key);

    setInsets(new Insets(0, 15, 0, 15));
  }

  /**
   * This method returns this panel's key.
   *
   * @return This panel's key.
   */
  protected
  final
  PreferencesKeys
  getKey()
  {
    return itsKey;
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of private methods.
  //////////////////////////////////////////////////////////////////////////////

  private
  void
  setKey(PreferencesKeys key)
  {
    itsKey = key;
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  private PreferencesKeys itsKey;
}
