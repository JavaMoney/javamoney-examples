// TransactionDetailTable

package org.javamoney.examples.ez.money.gui.table;

import static org.javamoney.examples.ez.common.utility.I18NHelper.getSharedProperty;
import static org.javamoney.examples.ez.money.utility.HTMLHelper.formatAmount;
import static org.javamoney.examples.ez.money.utility.RenderHelper.setLookFor;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellRenderer;

import org.javamoney.examples.ez.money.gui.GUIConstants;
import org.javamoney.examples.ez.money.gui.table.comparator.TransactionDetailComparator;
import org.javamoney.examples.ez.money.gui.table.model.TransactionDetailModel;
import org.javamoney.examples.ez.money.model.dynamic.total.TransactionDetail;

import org.javamoney.examples.ez.common.gui.table.SortedDataTable;

/**
 * This class facilitates displaying transaction details in a table.
 */
public
final
class
TransactionDetailTable
extends SortedDataTable<TransactionDetail>
{
  /**
   * Constructs a new table.
   */
  public
  TransactionDetailTable()
  {
    super(COLUMNS, new TransactionDetailModel(), new TransactionDetailComparator());

    // Customize table.
    setGridColor(GUIConstants.COLOR_TABLE_GRID);
    setPreferredWidths(new int[] {275, 125, 275, 125});
    setRowHeight(GUIConstants.CELL_HEIGHT);

    setSortableColumns(new int[] {ACCOUNT_COLUMN, AMOUNT_COLUMN, DATE_COLUMN,
        PAYEE_COLUMN});
  }

  /**
   * This method adds all its transaction details into the table.
   */
  @Override
  public
  void
  display()
  {
    clearRows();
    sort();

    for(TransactionDetail detail : getList())
    {
      ((TransactionDetailModel)getModel()).addRow(detail);
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
    String tip = null;
    double amount = 0.0;

    // Add up the sum of all the selected transactions.
    for(int len = 0; len < rows.length; ++len)
    {
      amount += Math.abs(get(rows[len]).getTransaction().getAmount());
    }

    // Build tool tip.
    tip = "<html>";
    tip += "&nbsp;<b>" + getSharedProperty("sum_tip") + "</b>&nbsp;<hr>";
    tip += "&nbsp;" + formatAmount(amount);
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

      setIcon(null);
      setText(value.toString());
      setToolTipText(buildTooltipText(row));

      // Alignment.
      if(column == PAYEE_COLUMN)
      {
        setHorizontalAlignment(SwingConstants.LEFT);
      }
      else if(column == AMOUNT_COLUMN)
      {
        setHorizontalAlignment(SwingConstants.TRAILING);
      }
      else
      {
        setHorizontalAlignment(SwingConstants.CENTER);
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
    getSharedProperty("date"),
    getSharedProperty("payee"),
    getSharedProperty("amount")
  };

  /**
   * The column for the transaction's account.
   */
  public static final int ACCOUNT_COLUMN = 0;
  /**
   * The column for the transaction's amount.
   */
  public static final int AMOUNT_COLUMN = 3;
  /**
   * The column for the transaction's date.
   */
  public static final int DATE_COLUMN = 1;
  /**
   * The column for the transaction's payee.
   */
  public static final int PAYEE_COLUMN = 2;
}
