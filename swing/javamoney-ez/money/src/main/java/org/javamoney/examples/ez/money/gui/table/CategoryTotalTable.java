// CategoryTotalTable

package org.javamoney.examples.ez.money.gui.table;

import static org.javamoney.examples.ez.common.utility.I18NHelper.getSharedProperty;
import static org.javamoney.examples.ez.money.utility.RenderHelper.setLookFor;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellRenderer;

import org.javamoney.examples.ez.money.gui.GUIConstants;
import org.javamoney.examples.ez.money.gui.table.comparator.CategoryTotalComparator;
import org.javamoney.examples.ez.money.gui.table.model.CategoryTotalModel;
import org.javamoney.examples.ez.money.model.dynamic.total.IncomeExpenseTotal;
import org.javamoney.examples.ez.money.utility.HTMLHelper;

import org.javamoney.examples.ez.common.gui.table.SortedDataTable;
import org.javamoney.examples.ez.common.utility.I18NHelper;

/**
 * This class facilitates displaying category totals in a table.
 */
public
final
class
CategoryTotalTable
extends SortedDataTable<IncomeExpenseTotal>
{
  /**
   * Constructs a new table.
   */
  public
  CategoryTotalTable()
  {
    super(COLUMNS, new CategoryTotalModel(), new CategoryTotalComparator());

    // Customize table.
    setGridColor(GUIConstants.COLOR_TABLE_GRID);
    setPreferredWidths(new int[] {40, 335, 300, 125});
    setRowHeight(GUIConstants.CELL_HEIGHT);
    setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    setSortableColumns(new int[] {AMOUNT_COLUMN, CATEGORY_COLUMN, GROUP_COLUMN});
  }

  /**
   * This method adds all its category totals into the table.
   */
  @Override
  public
  void
  display()
  {
    int row = 1;

    clearRows();
    sort();

    for(IncomeExpenseTotal categoryTotal : getList())
    {
      ((CategoryTotalModel)getModel()).addRow(categoryTotal, row++);
    }
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

  //////////////////////////////////////////////////////////////////////////////
  // Start of private methods.
  //////////////////////////////////////////////////////////////////////////////

  private
  String
  buildSumToolTipText()
  {
    int[] rows = getSelectedRows();
    String tip = "";
    double amount = 0.0;

    // Add up the sum of all the selected transactions.
    for(int len = 0; len < rows.length; ++len)
    {
      IncomeExpenseTotal total = get(rows[len]);

      if(total.getType().isSummary() == false)
      {
        amount += Math.abs(total.getAmount());
      }
    }

    // Build tool tip.
    tip += "<html>";
    tip += "&nbsp;<b>" + getProperty("sum_tip") + "</b>&nbsp;<hr>";
    tip += "&nbsp;" + HTMLHelper.formatAmount(amount);
    tip += "</html>";

    return tip;
  }

  private
  String
  buildTooltipText(int row)
  {
    String toolTip = null;

    if(isRowSelected(row) == true && getSelectedRowCount() > 1)
    {
      toolTip = buildSumToolTipText();
    }

    return toolTip;
  }

  private
  static
  String
  getProperty(String key)
  {
    return I18NHelper.getProperty("CategoryTotalTable." + key);
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

      setToolTipText(buildTooltipText(row));

      // Text.
      if(get(row).getType().isSummary() == true)
      {
        setText("<html><b>" + value.toString() + "</b></html>");
      }
      else
      {
        setText(value.toString());
      }

      // Alignment.
      if(column == ROW_COLUMN)
      {
        setHorizontalAlignment(SwingConstants.CENTER);
      }
      else if(column == CATEGORY_COLUMN || column == GROUP_COLUMN)
      {
        setHorizontalAlignment(SwingConstants.LEFT);
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
    getSharedProperty("group"),
    getSharedProperty("amount")
  };

  /**
   * The column for the total's amount.
   */
  public static final int AMOUNT_COLUMN = 3;
  /**
   * The column for the total's category.
   */
  public static final int CATEGORY_COLUMN = 1;
  /**
   * The column for the total's group.
   */
  public static final int GROUP_COLUMN = 2;
  /**
   * The column for the total's row.
   */
  public static final int ROW_COLUMN = 0;
}
