// LookAndFeelHelper

package org.javamoney.examples.ez.common.utility;

import static javax.swing.UIManager.getSystemLookAndFeelClassName;
import static javax.swing.UIManager.setLookAndFeel;

/**
 * This class facilitates initializing the look and feel. All methods in this
 * class are static.
 */
public
final
class
LookAndFeelHelper
{
  /**
   * This method initializes the look and feel to the system's native look.
   * <p>
   * <b>Note:</b> This method should be called prior to the toolkit being
   * loaded, which is prior to the creation of any components.
   */
  public
  static
  void
  initializeLookAndFeel()
  {
    try
    {
      // Set Mac specific properties.
      System.getProperties().setProperty("apple.laf.useScreenMenuBar", "true");

      setLookAndFeel(getSystemLookAndFeelClassName());
    }
    catch(Exception exception)
    {
      // Ignored.
    }
  }
}
