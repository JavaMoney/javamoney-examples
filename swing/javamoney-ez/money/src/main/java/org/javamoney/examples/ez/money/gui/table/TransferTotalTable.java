// TransferTotalTable

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
import org.javamoney.examples.ez.money.gui.table.comparator.TransferTotalComparator;
import org.javamoney.examples.ez.money.gui.table.model.TransferTotalModel;
import org.javamoney.examples.ez.money.model.dynamic.total.TransferTotal;
import org.javamoney.examples.ez.money.model.persisted.account.Account;

import org.javamoney.examples.ez.common.gui.table.SortedDataTable;

/**
 * This class facilitates displaying transfer totals in a table.
 */
public
final
class
TransferTotalTable
extends SortedDataTable<TransferTotal>
{
  /**
   * Constructs a new table.
   */
  public
  TransferTotalTable()
  {
    super(COLUMNS, new TransferTotalModel(), new TransferTotalComparator());

    // Customize table.
    setGridColor(GUIConstants.COLOR_TABLE_GRID);
    setPreferredWidths(new int[] {40, 400, 150, 150, 60});
    setRowHeight(GUIConstants.CELL_HEIGHT);
    setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    setSortableColumns(new int[] {FROM_COLUMN, ID_COLUMN, TO_COLUMN});
  }

  /**
   * This method adds all its transfer totals into the table.
   */
  @Override
  public
  void
  display()
  {
    int row = 1;

    clearRows();
    sort();

    for(TransferTotal total : getList())
    {
      ((TransferTotalModel)getModel()).addRow(total, row++);
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
      Account account = get(row).getAccount();

      // Customize look.
      setLookFor(this, row, isSelected);

      setText(value.toString());

      // Icon.
      if(column == ACTIVE_COLUMN && account.isActive() == true)
      {
        setIcon(IconKeys.TABLE_RECONCILED.getIcon());
      }
      else
      {
        setIcon(null);
      }

      // Alignment.
      if(column ==  ACTIVE_COLUMN || column == ROW_COLUMN)
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
    getSharedProperty("account"),
    getSharedProperty("transferred_to"),
    getSharedProperty("transferred_from"),
    getSharedProperty("active")
  };

  /**
   * The column for the account's active state.
   */
  public static final int ACTIVE_COLUMN = 4;
  /**
   * The column for the account's transferred from total.
   */
  public static final int FROM_COLUMN = 3;
  /**
   * The column for the account's identifier.
   */
  public static final int ID_COLUMN = 1;
  /**
   * The column for the account's row in the table.
   */
  public static final int ROW_COLUMN = 0;
  /**
   * The column for the account's transferred to total.
   */
  public static final int TO_COLUMN = 2;
}
