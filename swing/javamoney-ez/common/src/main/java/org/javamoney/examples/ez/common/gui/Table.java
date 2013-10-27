// Table

package org.javamoney.examples.ez.common.gui;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

/**
 * This class is designed to provide convenience methods for a table.
 */
public
class
Table
extends JTable
{
  /**
   * Constructs a new table.
   *
   * @param columns The columns to have in the table.
   * @param model The model to use in the table.
   */
  public
  Table(String[] columns, DefaultTableModel model)
  {
    super(model);

    for(int col = 0; col < columns.length; ++col)
    {
      ((DefaultTableModel)getModel()).addColumn(columns[col]);
    }

    getTableHeader().setReorderingAllowed(false);
    getTableHeader().setResizingAllowed(false);
  }

  /**
   * This method removes all rows in the table's model.
   */
  public
  final
  void
  clearRows()
  {
    for(int len = getModel().getRowCount() - 1; len >= 0; --len)
    {
      ((DefaultTableModel)getModel()).removeRow(len);
    }
  }

  /**
   * This method returns a table column.
   *
   * @param column The index of the column.
   *
   * @return A table column.
   */
  public
  final
  TableColumn
  getColumn(int column)
  {
    return getColumnModel().getColumn(column);
  }

  /**
   * This method returns the column index.
   *
   * @param xCoordinate The x coordinate to obtain the column index for.
   *
   * @return The column index.
   */
  public
  final
  int
  getColumnIndexAtX(int xCoordinate)
  {
    return getColumnModel().getColumnIndexAtX(xCoordinate);
  }

  /**
   * This method returns a column's index.
   *
   * @param column The column to obtain the index for.
   *
   * @return A column's index.
   */
  public
  final
  int
  getIndexForColumn(int column)
  {
    return convertColumnIndexToModel(column);
  }

  /**
   * This method removes all rows in the specified array of rows.
   * <p>
   * <b>Note:</b> Although the rows in the array do not have to be in sequence,
   * they do have to be in ascending order, such as from a call to
   * getSelectedRows().
   *
   * @param rows An array of rows to remove.
   */
  public
  final
  void
  removeRows(int[] rows)
  {
    for(int len = 0; len < rows.length; ++len)
    {
      ((DefaultTableModel)getModel()).removeRow(rows[len] - len);
    }
  }

  /**
   * The method selects a row.
   *
   * @param row The row to select.
   */
  public
  final
  void
  selectRow(int row)
  {
    selectRows(row, row);
  }

  /**
   * The method selects rows.
   *
   * @param start The starting row of the selection.
   * @param end The ending row of the selection.
   */
  public
  final
  void
  selectRows(int start, int end)
  {
    getSelectionModel().setSelectionInterval(start, end);
  }

  /**
   * This method sets the columns' preferred widths.
   * <p>
   * <b>Note:</b> The length of the preferred widths must be identical to the
   * table's column count.
   *
   * @param widths The columns' preferred widths.
   */
  public
  final
  void
  setPreferredWidths(int[] widths)
  {
    for(int len = 0; len < getModel().getColumnCount(); ++len)
    {
      getColumn(len).setPreferredWidth(widths[len]);
    }
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of protected methods.
  //////////////////////////////////////////////////////////////////////////////

  /**
   * This method returns the default object responsible for rendering table
   * column headers.
   *
   * @return The default rendering object.
   */
  protected
  static
  final
  TableCellRenderer
  getDefaultHeaderRenderer()
  {
    return new JTableHeader().getDefaultRenderer();
  }

  /**
   * This method sets the object for rendering the table's column.
   *
   * @param renderer The object to use for rendering.
   */
  protected
  final
  void
  setRendererForHeaders(TableCellRenderer renderer)
  {
    for(int len = 0; len < getColumnCount(); ++len)
    {
      getColumn(len).setHeaderRenderer(renderer);
    }
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of inner classes.
  //////////////////////////////////////////////////////////////////////////////

  /**
   * This is a convenience class since in most cases cells are not editable.
   */
  public
  static
  class
  NonmutableTableModel
  extends DefaultTableModel
  {
    /**
     * This method returns true if the cell at the specified row and column is
     * editable.
     * <p>
     * <b>Note:</b> This method always returns false.
     *
     * @param row The cell's row.
     * @param column The cell's column.
     *
     * @return true or false.
     */
    @Override
    public
    boolean
    isCellEditable(int row, int column)
    {
      return false;
    }
  }
}
