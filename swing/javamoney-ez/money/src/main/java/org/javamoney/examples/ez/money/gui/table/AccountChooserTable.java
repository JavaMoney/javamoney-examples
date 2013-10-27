// AccountChooserTable

package org.javamoney.examples.ez.money.gui.table;

import static org.javamoney.examples.ez.common.utility.I18NHelper.getSharedProperty;
import static org.javamoney.examples.ez.money.utility.RenderHelper.setLookFor;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellRenderer;

import org.javamoney.examples.ez.money.IconKeys;
import org.javamoney.examples.ez.money.gui.GUIConstants;
import org.javamoney.examples.ez.money.gui.table.comparator.AccountChooserComparator;
import org.javamoney.examples.ez.money.gui.table.model.AccountChooserModel;
import org.javamoney.examples.ez.money.model.persisted.account.Account;

import org.javamoney.examples.ez.common.gui.table.SortedDataTable;

/**
 * This class facilitates displaying accounts in a table that can be selected.
 */
public
final
class
AccountChooserTable
extends SortedDataTable<Account>
{
  /**
   * Constructs a new table.
   */
  public
  AccountChooserTable()
  {
    super(COLUMNS, new AccountChooserModel(), new AccountChooserComparator());

    // Customize table.
    setGridColor(GUIConstants.COLOR_TABLE_GRID);
    setPreferredWidths(new int[] {350, 175, 75});
    setRowHeight(GUIConstants.CELL_HEIGHT);
    setSortableColumns(new int[] {ID_COLUMN, KEY_COLUMN});
  }

  /**
   * This method adds all its accounts into the table.
   */
  @Override
  public
  void
  display()
  {
    clearRows();
    sort();

    for(Account account : getList())
    {
      ((AccountChooserModel)getModel()).addRow(account);
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
      Account account = get(row);

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
      if(column == ACTIVE_COLUMN || column == KEY_COLUMN)
      {
        setHorizontalAlignment(SwingConstants.CENTER);
      }
      else
      {
        setHorizontalAlignment(SwingConstants.LEFT);
      }

      return this;
    }
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  private final CellRenderHandler RENDER_HANDLER = new CellRenderHandler();

  private static final String[] COLUMNS = {
    getSharedProperty("account"),
    getSharedProperty("type"),
    getSharedProperty("active")
  };

  /**
   * The column for the account's active status.
   */
  public static final int ACTIVE_COLUMN = 2;
  /**
   * The column for the account's identifier.
   */
  public static final int ID_COLUMN = 0;
  /**
   * The column for the account's key.
   */
  public static final int KEY_COLUMN = 1;
}
