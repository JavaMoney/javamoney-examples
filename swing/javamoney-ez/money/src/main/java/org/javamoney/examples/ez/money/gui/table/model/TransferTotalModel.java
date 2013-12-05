// TransferTotalModel

package org.javamoney.examples.ez.money.gui.table.model;

import static org.javamoney.examples.ez.money.ApplicationProperties.UI_CURRENCY_FORMAT;
import static org.javamoney.examples.ez.money.gui.table.TransferTotalTable.ACTIVE_COLUMN;
import static org.javamoney.examples.ez.money.gui.table.TransferTotalTable.FROM_COLUMN;
import static org.javamoney.examples.ez.money.gui.table.TransferTotalTable.ID_COLUMN;
import static org.javamoney.examples.ez.money.gui.table.TransferTotalTable.ROW_COLUMN;
import static org.javamoney.examples.ez.money.gui.table.TransferTotalTable.TO_COLUMN;

import org.javamoney.examples.ez.money.model.dynamic.total.TransferTotal;

import org.javamoney.examples.ez.common.gui.Table;

/**
 * This class facilitates adding transfer totals into a table.
 */
public
final
class
TransferTotalModel
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
  addRow(TransferTotal total, int row)
  {
    String[] rowData = new String[5];

    rowData[ACTIVE_COLUMN] = "";
    rowData[FROM_COLUMN] = UI_CURRENCY_FORMAT.format(total.getFromTotal());
    rowData[ID_COLUMN] = total.getIdentifier();
    rowData[ROW_COLUMN] = "<html><b>" + row + "</b></html>";
    rowData[TO_COLUMN] = UI_CURRENCY_FORMAT.format(total.getToTotal());

    addRow(rowData);
  }
}
