// BudgetTable

package org.javamoney.examples.ez.money.gui.table;

import static org.javamoney.examples.ez.common.utility.I18NHelper.getSharedProperty;
import static org.javamoney.examples.ez.money.utility.RenderHelper.setLookFor;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellRenderer;

import org.javamoney.examples.ez.money.IconKeys;
import org.javamoney.examples.ez.money.gui.GUIConstants;
import org.javamoney.examples.ez.money.gui.table.comparator.BudgetComparator;
import org.javamoney.examples.ez.money.gui.table.model.BudgetModel;
import org.javamoney.examples.ez.money.model.dynamic.total.Budget;

import org.javamoney.examples.ez.common.gui.table.SortedDataTable;

/**
 * This class facilitates displaying budgets in a table.
 */
public
final
class
BudgetTable
extends SortedDataTable<Budget>
{
  /**
   * Constructs a new table.
   */
  public
  BudgetTable()
  {
    super(COLUMNS, new BudgetModel(), new BudgetComparator());

    // Customize table.
    setGridColor(GUIConstants.COLOR_TABLE_GRID);
    setPreferredWidths(new int[] {40, 230, 90, 100, 100, 100, 100, 40});
    setRowHeight(GUIConstants.CELL_HEIGHT);
    setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

    setSortableColumns(new int[] {BALANCE_COLUMN, BUDGET_COLUMN, CHANGE_COLUMN,
        ID_COLUMN, STARTING_BALANCE_COLUMN, TYPE_COLUMN});
  }

  /**
   * This method adds all its budgets into the table.
   */
  @Override
  public
  void
  display()
  {
    int row = 1;

    clearRows();
    sort();

    for(Budget budget : getList())
    {
      ((BudgetModel)getModel()).addRow(budget, row++);
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
      if(get(row).getType().isSummary() == true)
      {
        setText("<html><b>" + value.toString() + "</b></html>");
      }
      else
      {
        setText(value.toString());
      }

      // Icon.
      if(column == ICON_COLUMN && get(row).getBalance() < 0)
      {
        setIcon(IconKeys.WARNING.getIcon());
      }
      else
      {
        setIcon(null);
      }

      // Alignment.
      if(column == ICON_COLUMN || column == ROW_COLUMN || column == TYPE_COLUMN)
      {
        setHorizontalAlignment(SwingConstants.CENTER);
      }
      else if(column == ID_COLUMN)
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
    getSharedProperty("type"),
    getSharedProperty("budget"),
    getSharedProperty("rollover"),
    getSharedProperty("difference"),
    getSharedProperty("balance"),
    ""
  };

  /**
   * The column for the budget's balance.
   */
  public static final int BALANCE_COLUMN = 6;
  /**
   * The column for the budget's monthly-allotted budget.
   */
  public static final int BUDGET_COLUMN = 3;
  /**
   * The column for how much has changed.
   */
  public static final int CHANGE_COLUMN = 5;
  /**
   * The column for the icon.
   */
  public static final int ICON_COLUMN = 7;
  /**
   * The column for the budget's identifier.
   */
  public static final int ID_COLUMN = 1;
  /**
   * The column for the budget's row.
   */
  public static final int ROW_COLUMN = 0;
  /**
   * The column for the budget's starting balance.
   */
  public static final int STARTING_BALANCE_COLUMN = 4;
  /**
   * The column for the budget's category type.
   */
  public static final int TYPE_COLUMN = 2;
}
