// CategoryTotalModel

package org.javamoney.examples.ez.money.gui.table.model;

import static org.javamoney.examples.ez.money.ApplicationProperties.UI_CURRENCY;
import static org.javamoney.examples.ez.money.gui.table.CategoryTotalTable.AMOUNT_COLUMN;
import static org.javamoney.examples.ez.money.gui.table.CategoryTotalTable.CATEGORY_COLUMN;
import static org.javamoney.examples.ez.money.gui.table.CategoryTotalTable.GROUP_COLUMN;
import static org.javamoney.examples.ez.money.gui.table.CategoryTotalTable.ROW_COLUMN;

import org.javamoney.examples.ez.money.model.dynamic.total.IncomeExpenseTotal;

import org.javamoney.examples.ez.common.gui.Table;

/**
 * This class facilitates adding category totals into a table.
 */
public
final
class
CategoryTotalModel
extends Table.NonmutableTableModel
{
  /**
   * This method adds the specified total into the model's table.
   *
   * @param total The total to add.
   * @param row The row the total is in.
   */
  public
  void
  addRow(IncomeExpenseTotal total, int row)
  {
    String[] rowData = new String[4];

    rowData[AMOUNT_COLUMN] = UI_CURRENCY.format(Math.abs(total.getAmount()));
    rowData[CATEGORY_COLUMN] = total.getCategoryIdentifier();
    rowData[GROUP_COLUMN] = total.getGroupName();

    if(total.getType().isSummary() == true)
    {
      rowData[ROW_COLUMN] = "";
    }
    else
    {
      rowData[ROW_COLUMN] = "<html><b>" + row + "</b></html>";
    }

    addRow(rowData);
  }
}
