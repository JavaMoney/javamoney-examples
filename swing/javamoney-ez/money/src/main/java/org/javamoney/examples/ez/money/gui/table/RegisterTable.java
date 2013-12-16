// RegisterTable

package org.javamoney.examples.ez.money.gui.table;

import static org.javamoney.examples.ez.common.utility.I18NHelper.getSharedProperty;
import static org.javamoney.examples.ez.money.ApplicationProperties.setInvertSortForRegister;
import static org.javamoney.examples.ez.money.ApplicationProperties.setRegisterColumnToSort;
import static org.javamoney.examples.ez.money.ApplicationProperties.viewBalanceColumn;
import static org.javamoney.examples.ez.money.utility.RenderHelper.setLookFor;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellRenderer;

import org.javamoney.examples.ez.money.IconKeys;
import org.javamoney.examples.ez.money.gui.GUIConstants;
import org.javamoney.examples.ez.money.gui.table.comparator.RegisterComparator;
import org.javamoney.examples.ez.money.gui.table.model.RegisterModel;
import org.javamoney.examples.ez.money.model.dynamic.transaction.RegisterTransaction;
import org.javamoney.examples.ez.money.model.persisted.transaction.Transaction;
import org.javamoney.examples.ez.money.utility.HTMLHelper;

import org.javamoney.examples.ez.common.gui.table.SortedDataTable;

/**
 * This class facilitates displaying transactions in a table.
 */
public
final
class
RegisterTable
extends SortedDataTable<RegisterTransaction>
{
  /**
	 * 
	 */
	private static final long serialVersionUID = 4972222391968037432L;
/**
   * Constructs a new table.
   */
  public
  RegisterTable()
  {
    super(COLUMNS, new RegisterModel(), new RegisterComparator());

    // Customize table.
    setGridColor(GUIConstants.COLOR_TABLE_GRID);
    setRowHeight(GUIConstants.CELL_HEIGHT);

    if(viewBalanceColumn() == true)
    {
      setPreferredWidths(new int[] {50, 80, 100, 289, 85, 85, 85, 26});
    }
    else
    {
      // Set the preferred widths before removing the column.
      setPreferredWidths(new int[] {50, 80, 100, 374, 85, 85, 0, 26});
      removeColumn(getColumn(BALANCE_COLUMN));
    }
  }

  /**
   * This method adds all its transactions into the table.
   */
  @Override
  public
  void
  display()
  {
    // Store sorting properties.
    setRegisterColumnToSort(getComparator().getColumn());
    setInvertSortForRegister(getComparator().invertSort());

    clearRows();
    sort();

    for(RegisterTransaction trans : getList())
    {
      ((RegisterModel)getModel()).addRow(trans);
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
      amount += get(rows[len]).getTransaction().getAmount().getNumber().doubleValue();
    }

    // Build tool tip.
    tip = "<html>";
    tip += "&nbsp;<b>" + getSharedProperty("sum_tip") + "</b>&nbsp;<hr>";
    tip += "&nbsp;" + HTMLHelper.formatAmount(amount);
    tip += "</html>";

    return tip;
  }

  private
  String
  buildTooltipText(int row)
  {
    String tip = null;

    if(isRowSelected(row) == true && getSelectedRowCount() > 1)
    {
      tip = buildSumToolTipText();
    }

    return tip;
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
    /**
	 * 
	 */
	private static final long serialVersionUID = -5377532078515748547L;

	public
    Component
    getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
        boolean hasFocus, int row, int column)
    {
      Transaction trans = get(row).getTransaction();

      column = getIndexForColumn(column);

      // Customize look.
      setLookFor(this, row, isSelected);

      setIcon(null);
      setText(value.toString());
      setToolTipText(buildTooltipText(row));

      // Icon.
      if(column == RECONCILED_COLUMN && trans.isReconciled() == true)
      {
        setIcon(IconKeys.TABLE_RECONCILED.getIcon());
      }
      else if(column == LABEL_COLUMN)
      {
        setIcon(trans.getLabel().getIcon());
      }

      // Alignment.
      if(column == CHECK_NUMBER_COLUMN || column == PAYEE_COLUMN)
      {
        setHorizontalAlignment(SwingConstants.LEFT);
      }
      else if(column == EXPENSE_COLUMN || column == INCOME_COLUMN)
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
    getSharedProperty("reconciled_short"),
    getSharedProperty("date"),
    getSharedProperty("check_number"),
    getSharedProperty("payee"),
    getSharedProperty("income"),
    getSharedProperty("expense"),
    getSharedProperty("balance"),
    ""
  };

  /**
   * The column for the account's balance after the transaction has been added.
   */
  public static final int BALANCE_COLUMN = 6;
  /**
   * The column for the transaction's check number.
   */
  public static final int CHECK_NUMBER_COLUMN = 2;
  /**
   * The column for the transaction's date.
   */
  public static final int DATE_COLUMN = 1;
  /**
   * The column for the transaction's amount that is below zero inclusive.
   */
  public static final int EXPENSE_COLUMN = 5;
  /**
   * The column for the transaction's amount that is above zero exclusive.
   */
  public static final int INCOME_COLUMN = 4;
  /**
   * The column for the transaction's color label.
   */
  public static final int LABEL_COLUMN = 7;
  /**
   * The column for the transaction's payee.
   */
  public static final int PAYEE_COLUMN = 3;
  /**
   * The column for the transaction's reconciled state.
   */
  public static final int RECONCILED_COLUMN = 0;
}
