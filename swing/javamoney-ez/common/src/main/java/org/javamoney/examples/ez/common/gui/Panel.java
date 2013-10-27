// Panel

package org.javamoney.examples.ez.common.gui;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * This class facilitates adding constrained components in a panel where its
 * layout manger is a grid.
 */
public
class
Panel
extends JPanel
{
  /**
   * Constructs a new panel.
   */
  public
  Panel()
  {
    super(new GridBagLayout(), true);

    setConstraints(new GridBagConstraints());
  }

  /**
   * This method adds a component.
   *
   * @param component The component to add.
   * @param column The starting column.
   * @param row The starting row.
   * @param columnSpan The amount of columns to span.
   * @param rowSpan The amount of rows to span.
   * @param width The width, measured as a percentage, the column uses in the
   * grid.
   * @param height The height, measured as a percentage, the row uses in the
   * grid.
   */
  public
  final
  void
  add(Component component, int column, int row, int columnSpan, int rowSpan,
      int width, int height)
  {
    getConstraints().gridx = column;
    getConstraints().gridy = row;
    getConstraints().gridwidth = columnSpan;
    getConstraints().gridheight = rowSpan;
    getConstraints().weightx = width;
    getConstraints().weighty = height;

    getLayout().setConstraints(component, getConstraints());

    add(component);
  }

  /**
   * This method adds an icon.
   *
   * @param icon The icon to add.
   * @param column The starting column.
   * @param row The starting row.
   * @param columnSpan The amount of columns to span.
   * @param rowSpan The amount of rows to span.
   * @param width The width, measured as a percentage, the column uses in the
   * grid.
   * @param height The height, measured as a percentage, the row uses in the
   * grid.
   */
  public
  final
  void
  add(Icon icon, int column, int row, int columnSpan, int rowSpan, int width,
      int height)
  {
    add(new JLabel(icon), column, row, columnSpan, rowSpan, width, height);
  }

  /**
   * This method adds text.
   *
   * @param text The text to add.
   * @param column The starting column.
   * @param row The starting row.
   * @param columnSpan The amount of columns to span.
   * @param rowSpan The amount of rows to span.
   * @param width The width, measured as a percentage, the column uses in the
   * grid.
   * @param height The height, measured as a percentage, the row uses in the
   * grid.
   */
  public
  final
  void
  add(String text, int column, int row, int columnSpan, int rowSpan, int width,
      int height)
  {
    add(new JLabel(text), column, row, columnSpan, rowSpan, width, height);
  }

  /**
   * This method adds a transparent cell.
   *
   * @param column The location of the column.
   * @param row The location of the row.
   */
  public
  final
  void
  addEmptyCellAt(int column, int row)
  {
    addEmptyCellAt(column, row, 0);
  }

  /**
   * This method adds a transparent cell.
   *
   * @param column The location of the column.
   * @param row The location of the row.
   * @param width The width to use.
   */
  public
  final
  void
  addEmptyCellAt(int column, int row, int width)
  {
    JLabel label = new JLabel();
    String spaces = "#";

    for(int len = 1; len < width; ++len)
    {
      spaces += "#";
    }

    label.setForeground(getBackground());
    label.setText(spaces);

    add(label, column, row, 1, 1, 0, 0);
  }

  /**
   * This method adds transparent cells.
   *
   * @param column The location of the column.
   * @param row The location of the starting row.
   * @param height The amount of rows to insert.
   */
  public
  final
  void
  addEmptyRowsAt(int column, int row, int height)
  {
    for(int len = 0; len < height; ++len)
    {
      addEmptyCellAt(column, row + len);
    }
  }

  /**
   * This method adds a transparent area.
   *
   * @param column The starting column.
   * @param row The starting row.
   * @param columnSpan The amount of columns to span.
   * @param rowSpan The amount of rows to span.
   * @param width The width, measured as a percentage, the column uses in the
   * grid.
   * @param height The height, measured as a percentage, the row uses in the
   * grid.
   */
  public
  final
  void
  addSpacer(int column, int row, int columnSpan, int rowSpan, int width, int height)
  {
    add(new JPanel(), column, row, columnSpan, rowSpan, width, height);
  }

  /**
   * This method returns the insets.
   *
   * @return The insets.
   */
  @Override
  public
  final
  Insets
  getInsets()
  {
    return (itsInsets == null) ? super.getInsets() : itsInsets;
  }

  /**
   * This method returns the layout manager.
   *
   * @return The layout manager.
   */
  @Override
  public
  final
  GridBagLayout
  getLayout()
  {
    return (GridBagLayout)super.getLayout();
  }

  /**
   * This method sets the anchor constant to be used when adding components. The
   * value should be one of the anchor constants from GridBagConstraints.
   *
   * @param anchor The anchor constant.
   */
  public
  final
  void
  setAnchor(int anchor)
  {
    getConstraints().anchor = anchor;
  }

  /**
   * This method sets the fill constant to be used when adding components. The
   * value should be one of the fill constants from GridBagConstraints.
   *
   * @param fill The fill constant.
   */
  public
  final
  void
  setFill(int fill)
  {
    getConstraints().fill = fill;
  }

  /**
   * This method sets the insets.
   *
   * @param insets The insets.
   */
  public
  final
  void
  setInsets(Insets insets)
  {
    itsInsets = insets;
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of private methods.
  //////////////////////////////////////////////////////////////////////////////

  private
  GridBagConstraints
  getConstraints()
  {
    return itsConstraints;
  }

  private
  void
  setConstraints(GridBagConstraints constraints)
  {
    itsConstraints = constraints;
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  private GridBagConstraints itsConstraints;
  private Insets itsInsets;
}
