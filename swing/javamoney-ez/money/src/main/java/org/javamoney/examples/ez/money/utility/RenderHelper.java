// RenderHelper

package org.javamoney.examples.ez.money.utility;

import static javax.swing.BorderFactory.createLineBorder;
import static org.javamoney.examples.ez.money.gui.GUIConstants.COLOR_SELECTION_BACKGROUND;
import static org.javamoney.examples.ez.money.gui.GUIConstants.COLOR_SELECTION_BORDER;
import static org.javamoney.examples.ez.money.gui.GUIConstants.COLOR_SELECTION_TEXT;
import static org.javamoney.examples.ez.money.gui.GUIConstants.COLOR_TABLE_ROW_EVEN;
import static org.javamoney.examples.ez.money.gui.GUIConstants.COLOR_TABLE_ROW_ODD;
import static org.javamoney.examples.ez.money.gui.GUIConstants.COLOR_TEXT;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.border.Border;

/**
 * This class helps ensure all the various components maintain a consistent look
 * by providing a common render. All methods in this class are static.
 */
public
final
class
RenderHelper
{
  /**
   * This method customizes the component with a predefined look.
   *
   * @param component The component to customize.
   * @param row The row the component is in, or index the component is at.
   * @param isSelected Whether or not the component is currently selected.
   */
  public
  static
  void
  setLookFor(JComponent component, int row, boolean isSelected)
  {
    setLookFor(component, row, isSelected, true);
  }

  /**
   * This method customizes the component with a predefined look.
   *
   * @param component The component to customize.
   * @param row The row the component is in, or index the component is at.
   * @param isSelected Whether or not the component is currently selected.
   * @param useBorder Whether or not to put a border around the component.
   */
  public
  static
  void
  setLookFor(JComponent component, int row, boolean isSelected, boolean useBorder)
  {
    Border border = null;
    Color bgColor = null;
    Color fgColor = null;

    if(isSelected == true)
    {
      border = SELECTED;
      bgColor = COLOR_SELECTION_BACKGROUND;
      fgColor = COLOR_SELECTION_TEXT;
    }
    else
    {
      if((row % 2) == 0)
      {
        border = NOT_SELECTED_EVEN;
        bgColor = COLOR_TABLE_ROW_EVEN;
      }
      else
      {
        border = NOT_SELECTED_ODD;
        bgColor = COLOR_TABLE_ROW_ODD;
      }

      fgColor = COLOR_TEXT;
    }

    // Customize component.
    component.setBackground(bgColor);
    component.setFont(FONT);
    component.setForeground(fgColor);
    component.setOpaque(true);

    if(useBorder == true)
    {
      component.setBorder(border);
    }
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  private static final Font FONT = new JLabel().getFont().deriveFont(Font.PLAIN);
  private static final Border NOT_SELECTED_EVEN = createLineBorder(COLOR_TABLE_ROW_EVEN);
  private static final Border NOT_SELECTED_ODD = createLineBorder(COLOR_TABLE_ROW_ODD);
  private static final Border SELECTED = createLineBorder(COLOR_SELECTION_BORDER);
}
