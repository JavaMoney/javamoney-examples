// BorderHelper

package org.javamoney.examples.ez.common.utility;

import static java.awt.Color.BLACK;
import static java.awt.Color.GRAY;
import static java.awt.Font.BOLD;
import static javax.swing.BorderFactory.createEtchedBorder;

import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;

/**
 * This class facilitates creating customized borders. All methods in this class
 * are static.
 */
public
final
class
BorderHelper
{
  /**
   * This method creates and returns a border.
   *
   * @param title The text displayed on the border.
   *
   * @return A border.
   */
  public
  static
  TitledBorder
  createTitledBorder(String title)
  {
    return createTitledBorder(title, true);
  }

  /**
   * This method creates and returns a border.
   *
   * @param title The text displayed on the border.
   * @param showBorder Whether or not to show the outlining of the border.
   *
   * @return A border.
   */
  public
  static
  TitledBorder
  createTitledBorder(String title, boolean showBorder)
  {
    TitledBorder border = new TitledBorder(title);

    // Build border.
    if(showBorder == true)
    {
        border.setBorder(createEtchedBorder());
    }
    else
    {
      border.setBorder(new MatteBorder(1, 0, 0, 0, GRAY));
    }

    border.setTitleColor(BLACK);
    if (border.getTitleFont() != null) {
    	border.setTitleFont(border.getTitleFont().deriveFont(BOLD, 11.0f));
    }
    return border;
  }
}
