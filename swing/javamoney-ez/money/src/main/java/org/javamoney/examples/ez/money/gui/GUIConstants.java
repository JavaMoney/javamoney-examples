// GUIConstants

package org.javamoney.examples.ez.money.gui;

import java.awt.Color;

/**
 * This interface provides user interface constants.
 */
public
interface
GUIConstants
{
  /**
   * The cell height for applicable UI components.
   */
  public static final int CELL_HEIGHT = 24;
  /**
   * The background color for UI components that have large background areas
   * such as lists and tables.
   */
  public static final Color COLOR_BACKGROUND_FILL = new Color(210, 217, 226);
  /**
   * The background color for selected items.
   */
  public static final Color COLOR_SELECTION_BACKGROUND = new Color(70, 95, 125);
  /**
   * The border color for selected items.
   */
  public static final Color COLOR_SELECTION_BORDER = new Color(17, 34, 45);
  /**
   * The text color for selected items.
   */
  public static final Color COLOR_SELECTION_TEXT = Color.WHITE;
  /**
   * The color a table's grid.
   */
  public static final Color COLOR_TABLE_GRID = new Color(177, 184, 199);
  /**
   * The color for the even row in a table.
   */
  public static final Color COLOR_TABLE_ROW_EVEN = Color.WHITE;
  /**
   * The color for the odd row in a table.
   */
  public static final Color COLOR_TABLE_ROW_ODD = new Color(210, 217, 226);
  /**
   * The text color in a UI component.
   */
  public static final Color COLOR_TEXT = Color.BLACK;
}
