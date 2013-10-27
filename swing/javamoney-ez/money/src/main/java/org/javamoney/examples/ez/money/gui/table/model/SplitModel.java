// SplitModel

package org.javamoney.examples.ez.money.gui.table.model;

import static org.javamoney.examples.ez.money.gui.table.SplitTable.AMOUNT_COLUMN;
import static org.javamoney.examples.ez.money.gui.table.SplitTable.CATEGORY_COLUMN;
import static org.javamoney.examples.ez.money.gui.table.SplitTable.NUMBER_COLUMN;

import javax.swing.table.DefaultTableModel;

/**
 * This class facilitates adding splits into a table.
 */
public
final
class
SplitModel
extends DefaultTableModel
{
  /**
   * This method adds the specified data into the model's table.
   *
   * @param row The table row number.
   * @param category The category.
   * @param amount The category's amount.
   */
  public
  void
  addRow(int row, String category, String amount)
  {
    String[] rowData = new String[3];

    rowData[AMOUNT_COLUMN] = amount;
    rowData[CATEGORY_COLUMN] = category;
    rowData[NUMBER_COLUMN] = "<html><b>" + (row + 1) + "</b></html>";

    addRow(rowData);
  }

  /**
   * This method returns true if the cell at the specified row and column is
   * editable.
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
    return column != NUMBER_COLUMN;
  }
}
