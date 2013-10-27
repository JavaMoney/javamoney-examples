// RadioButton

package org.javamoney.examples.ez.common.gui;

import javax.swing.JRadioButton;

/**
 * This class extends Swing's radio button for the sole purpose to not have its
 * focus border painted.
 */
public
class
RadioButton
extends JRadioButton
{
  /**
   * Constructs a new radio button.
   */
  public
  RadioButton()
  {
    this("");
  }

  /**
   * Constructs a new radio button.
   *
   * @param text The text to display.
   */
  public
  RadioButton(String text)
  {
    super(text);

    setFocusPainted(false);
  }
}
