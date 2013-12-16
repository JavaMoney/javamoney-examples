// ImportTransactionTable

package org.javamoney.examples.ez.money.gui.table;

import static org.javamoney.examples.ez.common.utility.I18NHelper.getSharedProperty;
import static org.javamoney.examples.ez.money.utility.EditorHelper.createSelectCellEditor;
import static org.javamoney.examples.ez.money.utility.RenderHelper.setLookFor;

import java.awt.Component;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableCellRenderer;

import org.javamoney.examples.ez.money.IconKeys;
import org.javamoney.examples.ez.money.gui.GUIConstants;
import org.javamoney.examples.ez.money.gui.table.comparator.ImportTransactionComparator;
import org.javamoney.examples.ez.money.gui.table.model.ImportTransactionModel;
import org.javamoney.examples.ez.money.model.dynamic.transaction.ImportTransaction;
import org.javamoney.examples.ez.money.utility.HTMLHelper;

import org.javamoney.examples.ez.common.gui.CheckBox;
import org.javamoney.examples.ez.common.gui.table.SortedDataTable;

/**
 * This class facilitates displaying selectable import transactions in a table.
 */
public
final
class
ImportTransactionTable
extends SortedDataTable<ImportTransaction>
{
  /**
	 * 
	 */
	private static final long serialVersionUID = 1431239216690811629L;
/**
   * Constructs a new table.
   */
  public
  ImportTransactionTable()
  {
    super(COLUMNS, new ImportTransactionModel(), new ImportTransactionComparator());

    // Customize table.
    setGridColor(GUIConstants.COLOR_TABLE_GRID);
    setPreferredWidths(new int[] {75, 80, 100, 285, 110, 100, 50});
    setRowHeight(GUIConstants.CELL_HEIGHT);
    setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

    setSortableColumns(new int[] {AMOUNT_COLUMN, CHECK_NUMBER_COLUMN,
        DATE_COLUMN, PAYEE_COLUMN, TYPE_COLUMN});

    // Set cell editors.
    getColumn(SELECT_COLUMN).setCellEditor(createSelectCellEditor());

    // Add listeners.
    getModel().addTableModelListener(new ModelHandler());
  }

  /**
   * This method adds all its import transactions into the table.
   */
  @Override
  public
  void
  display()
  {
    clearRows();
    sort();

    for(ImportTransaction trans : getList())
    {
      ((ImportTransactionModel)getModel()).addRow(trans);
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
    return (column == SELECT_COLUMN) ? SELECT_RENDERER : RENDER_HANDLER;
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
      amount += get(rows[len]).getTransaction().getAmount().getNumber().doubleValue();
    }

    // Build tool tip.
    tip += "<html>";
    tip += "&nbsp;<b>" + getSharedProperty("sum_tip") + "</b>&nbsp;<hr>";
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

      // Icon
      if(column == DUPLICATE_COLUMN && get(row).hasDuplicates() == true)
      {
        setIcon(IconKeys.WARNING.getIcon());
      }
      else
      {
        setIcon(null);
      }

      // Alignment.
      if(column == CHECK_NUMBER_COLUMN || column == PAYEE_COLUMN)
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

  private
  class
  CheckBoxCellRenderHandler
  extends CheckBox
  implements TableCellRenderer
  {
    public
    Component
    getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
        boolean hasFocus, int row, int column)
    {
      // Customize look.
      setLookFor(this, row, isSelected);

      setHorizontalAlignment(SwingConstants.CENTER);
      setSelected(get(row).isSelected());

      return this;
    }
  }

  private
  class
  ModelHandler
  implements
  TableModelListener
  {
    public
    void
    tableChanged(TableModelEvent event)
    {
      ArrayList<ImportTransaction> transactions = getSelectedElements();

      for(ImportTransaction trans : transactions)
      {
        trans.setIsSelected(!trans.isSelected());
      }
    }
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  private final CellRenderHandler RENDER_HANDLER = new CellRenderHandler();
  private final CheckBoxCellRenderHandler SELECT_RENDERER = new CheckBoxCellRenderHandler();

  private static final String[] COLUMNS = {
    getSharedProperty("select"),
    getSharedProperty("date"),
    getSharedProperty("check_number"),
    getSharedProperty("payee"),
    getSharedProperty("amount"),
    getSharedProperty("type"),
    ""
  };

  /**
   * The column for the transaction's amount.
   */
  public static final int AMOUNT_COLUMN = 4;
  /**
   * The column for the transaction's check number.
   */
  public static final int CHECK_NUMBER_COLUMN = 2;
  /**
   * The column for the transaction's date.
   */
  public static final int DATE_COLUMN = 1;
  /**
   * The column for whether or not the transaction has duplicates.
   */
  public static final int DUPLICATE_COLUMN = 6;
  /**
   * The column for the transaction's payee.
   */
  public static final int PAYEE_COLUMN = 3;
  /**
   * The column for the selecting the transaction.
   */
  public static final int SELECT_COLUMN = 0;
  /**
   * The column for the transaction's type.
   */
  public static final int TYPE_COLUMN = 5;
}
