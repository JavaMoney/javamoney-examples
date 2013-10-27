// ImportTransactionModel

package org.javamoney.examples.ez.money.gui.table.model;

import static org.javamoney.examples.ez.money.ApplicationProperties.UI_CURRENCY;
import static org.javamoney.examples.ez.money.ApplicationProperties.UI_DATE_FORMAT;
import static org.javamoney.examples.ez.money.gui.table.ImportTransactionTable.AMOUNT_COLUMN;
import static org.javamoney.examples.ez.money.gui.table.ImportTransactionTable.CHECK_NUMBER_COLUMN;
import static org.javamoney.examples.ez.money.gui.table.ImportTransactionTable.DATE_COLUMN;
import static org.javamoney.examples.ez.money.gui.table.ImportTransactionTable.DUPLICATE_COLUMN;
import static org.javamoney.examples.ez.money.gui.table.ImportTransactionTable.PAYEE_COLUMN;
import static org.javamoney.examples.ez.money.gui.table.ImportTransactionTable.SELECT_COLUMN;
import static org.javamoney.examples.ez.money.gui.table.ImportTransactionTable.TYPE_COLUMN;

import javax.swing.table.DefaultTableModel;

import org.javamoney.examples.ez.money.model.dynamic.transaction.ImportTransaction;
import org.javamoney.examples.ez.money.model.persisted.transaction.Transaction;

/**
 * This class facilitates adding import transactions into a table.
 */
public
final
class
ImportTransactionModel
extends DefaultTableModel
{
  /**
   * This method adds the specified import transaction into the model's table.
   *
   * @param iTrans The import transaction to add.
   */
  public
  void
  addRow(ImportTransaction iTrans)
  {
    Transaction trans = iTrans.getTransaction();
    String[] rowData = new String[7];

    rowData[AMOUNT_COLUMN] = UI_CURRENCY.format(trans.getAmount());
    rowData[CHECK_NUMBER_COLUMN] = trans.getCheckNumber();
    rowData[DUPLICATE_COLUMN] = "";
    rowData[DATE_COLUMN] = UI_DATE_FORMAT.format(trans.getDate());
    rowData[PAYEE_COLUMN] = trans.getPayee();
    rowData[SELECT_COLUMN] = "";
    rowData[TYPE_COLUMN] = iTrans.getType().toString();

    addRow(rowData);
  }

  /**
   * This method returns true if the cell at the specified row and column is
   * editable.
   *
   * @param row The cell's row.
   * @param column The cell's column.
   *
   * @return true or false.
   */
  @Override
  public
  boolean
  isCellEditable(int row, int column)
  {
    return column == SELECT_COLUMN;
  }
}
