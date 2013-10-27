// ImportDuplicateModel

package org.javamoney.examples.ez.money.gui.table.model;

import static org.javamoney.examples.ez.money.ApplicationProperties.UI_CURRENCY;
import static org.javamoney.examples.ez.money.ApplicationProperties.UI_DATE_FORMAT;
import static org.javamoney.examples.ez.money.gui.table.ImportDuplicateTable.AMOUNT_COLUMN;
import static org.javamoney.examples.ez.money.gui.table.ImportDuplicateTable.CHECK_NUMBER_COLUMN;
import static org.javamoney.examples.ez.money.gui.table.ImportDuplicateTable.DATE_COLUMN;
import static org.javamoney.examples.ez.money.gui.table.ImportDuplicateTable.PAYEE_COLUMN;
import static org.javamoney.examples.ez.money.gui.table.ImportDuplicateTable.RECONCILED_COLUMN;

import org.javamoney.examples.ez.money.model.persisted.transaction.Transaction;

import org.javamoney.examples.ez.common.gui.Table;

/**
 * This class facilitates adding transactions into a table.
 */
public
final
class
ImportDuplicateModel
extends Table.NonmutableTableModel
{
  /**
   * This method adds the specified transaction into the model's table.
   *
   * @param trans The transaction to add.
   */
  public
  void
  addRow(Transaction trans)
  {
    String[] rowData = new String[5];

    rowData[AMOUNT_COLUMN] = UI_CURRENCY.format(trans.getAmount());
    rowData[CHECK_NUMBER_COLUMN] = trans.getCheckNumber();
    rowData[DATE_COLUMN] = UI_DATE_FORMAT.format(trans.getDate());
    rowData[PAYEE_COLUMN] = trans.getPayee();
    rowData[RECONCILED_COLUMN] = "";

    addRow(rowData);
  }
}
