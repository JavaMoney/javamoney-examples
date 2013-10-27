// SplitTable

package org.javamoney.examples.ez.money.gui.table;

import static org.javamoney.examples.ez.common.utility.I18NHelper.getSharedProperty;
import static org.javamoney.examples.ez.money.ApplicationProperties.UI_CURRENCY;
import static org.javamoney.examples.ez.money.KeywordKeys.NOT_CATEGORIZED;
import static org.javamoney.examples.ez.money.model.dynamic.transaction.Split.MAX_SPLIT;
import static org.javamoney.examples.ez.money.utility.EditorHelper.createAmountCellEditor;
import static org.javamoney.examples.ez.money.utility.EditorHelper.createCategoryCellEditor;
import static org.javamoney.examples.ez.money.utility.RenderHelper.setLookFor;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellRenderer;

import org.javamoney.examples.ez.money.gui.GUIConstants;
import org.javamoney.examples.ez.money.gui.table.model.SplitModel;
import org.javamoney.examples.ez.money.model.persisted.category.Category;
import org.javamoney.examples.ez.money.model.persisted.category.CategoryCollection;

import org.javamoney.examples.ez.common.gui.Table;

/**
 * This class facilitates displaying transaction splits in a table.
 */
public
final
class
SplitTable
extends Table
{
  /**
   * Constructs a new split table that will provide the specified categories to
   * be selected.
   *
   * @param collection The categories to provide in the table.
   */
  public
  SplitTable(CategoryCollection collection)
  {
    super(COLUMNS, new SplitModel());

    // Customize table.
    setGridColor(GUIConstants.COLOR_TABLE_GRID);
    setPreferredWidths(new int[] {25, 300, 75});
    setRowHeight(GUIConstants.CELL_HEIGHT);

    // Set editors.
    getColumn(AMOUNT_COLUMN).setCellEditor(createAmountCellEditor());
    getColumn(CATEGORY_COLUMN).setCellEditor(createCategoryCellEditor(collection));

    // For some reason the table header is too small. This normalizes it.
    setRendererForHeaders(getDefaultHeaderRenderer());
  }

  /**
   * This method adds a new split to the end of the table. The split added is
   * initialized with no category and an amount of 0. This method returns true
   * if the amount of splits in the table is less than the maximum allowed
   * splits, otherwise false.
   *
   * @return true or false.
   */
  public
  boolean
  addSplit()
  {
    return addSplit(NOT_CATEGORIZED.toString(), UI_CURRENCY.format(0));
  }

  /**
   * This method adds a new split with the specified category and amount to the
   * end of the table. This method returns true if the amount of splits in the
   * table is less than the maximum allowed splits, otherwise false.
   *
   * @param category The split's category.
   * @param amount The split's amount.
   *
   * @return true or false.
   */
  public
  boolean
  addSplit(String category, String amount)
  {
    boolean result = false;

    if(getRowCount() < MAX_SPLIT)
    {
      ((SplitModel)getModel()).addRow(getRowCount(), category, amount);
      result = true;
    }

    return result;
  }

  /**
   * This method starts editing the category for the last row of the table.
   */
  public
  void
  doEditCategory()
  {
    editCellAt(getRowCount() - 1, CATEGORY_COLUMN);
  }

  /**
   * This method returns the object responsible for rendering the cell at the
   * specified row and column.
   *
   * @param row The cell's row.
   * @param column The cell's column.
   *
   * @return The object responsible for rendering the cell at the specified row
   * and column.
   */
  @Override
  public
  TableCellRenderer
  getCellRenderer(int row, int column)
  {
    return RENDER_HANDLER;
  }

  /**
   * This method removes the selected rows from the table.
   */
  public
  void
  removeSelectedRows()
  {
    int[] rows = getSelectedRows();

    if(rows.length != 0)
    {
      removeRows(rows);

      // Adjust the numbers of the splits that are left.
      for(int len = 0; len < getRowCount(); ++len)
      {
        ((SplitModel)getModel()).setValueAt("" + (len + 1), len, NUMBER_COLUMN);
      }
    }
  }

  /**
   * This method sets the amount of the split at the specified row to that of
   * the specified amount.
   *
   * @param amount The new amount the split should have.
   * @param row The row where the split is.
   */
  public
  void
  setAmountAt(String amount, int row)
  {
    ((SplitModel)getModel()).setValueAt(amount, row, AMOUNT_COLUMN);
  }

  /**
   * An IndexOutOfBoundsException is being thrown after a row is removed and
   * then the previous row is edited. This method compares the row against the
   * row count before calling the super to prevent that exception.
   *
   * @param value The value to be displayed.
   * @param row The cell's row.
   * @param column The cell's column.
   */
  @Override
  public
  void
  setValueAt(Object value, int row, int column)
  {
    if(row < getRowCount())
    {
      super.setValueAt(value, row, column);
    }
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of inner classes.
  //////////////////////////////////////////////////////////////////////////////

  private
  class
  CellRenderHandler
  extends JLabel
  implements TableCellRenderer
  {
    public
    Component
    getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
        boolean hasFocus, int row, int column)
    {
      // Customize look.
      setLookFor(this, row, isSelected);

      // Text.
      if(value instanceof Category)
      {
        setText(((Category)value).getQIFName());
      }
      else
      {
        setText(value.toString());
      }

      // Alignment
      if(column == CATEGORY_COLUMN)
      {
        setHorizontalAlignment(SwingConstants.LEFT);
      }
      else if(column == NUMBER_COLUMN)
      {
        setHorizontalAlignment(SwingConstants.CENTER);
      }
      else
      {
        setHorizontalAlignment(SwingConstants.TRAILING);
      }

      return this;
    }
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  private final CellRenderHandler RENDER_HANDLER = new CellRenderHandler();

  private static final String[] COLUMNS = {
    "",
    getSharedProperty("category"),
    getSharedProperty("amount")
  };

  /**
   * The column for the split's amount.
   */
  public static final int AMOUNT_COLUMN = 2;
  /**
   * The column for the split's category.
   */
  public static final int CATEGORY_COLUMN = 1;
  /**
   * The column for the split's number.
   */
  public static final int NUMBER_COLUMN = 0;
}
