// CheckBox

package org.javamoney.examples.ez.common.gui;

import javax.swing.JCheckBox;

/**
 * This class extends Swing's check box for the sole purpose to not have its
 * focus border painted.
 */
public
class
CheckBox
extends JCheckBox
{
  /**
   * Constructs a new check box.
   */
  public
  CheckBox()
  {
    this("");
  }

  /**
   * Constructs a new check box.
   *
   * @param text The text to display.
   */
  public
  CheckBox(String text)
  {
    super(text);

    setFocusPainted(false);
  }
}
