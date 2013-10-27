// TransactionDetailModel

package org.javamoney.examples.ez.money.gui.table.model;

import static org.javamoney.examples.ez.money.ApplicationProperties.UI_CURRENCY;
import static org.javamoney.examples.ez.money.ApplicationProperties.UI_DATE_FORMAT;
import static org.javamoney.examples.ez.money.gui.table.TransactionDetailTable.ACCOUNT_COLUMN;
import static org.javamoney.examples.ez.money.gui.table.TransactionDetailTable.AMOUNT_COLUMN;
import static org.javamoney.examples.ez.money.gui.table.TransactionDetailTable.DATE_COLUMN;
import static org.javamoney.examples.ez.money.gui.table.TransactionDetailTable.PAYEE_COLUMN;

import org.javamoney.examples.ez.money.model.dynamic.total.TransactionDetail;

import org.javamoney.examples.ez.common.gui.Table;

/**
 * This class facilitates adding transaction details into a table.
 */
public
final
class
TransactionDetailModel
extends Table.NonmutableTableModel
{
  /**
   * This method adds the specified transaction detail into the model's table.
   *
   * @param detail The transaction detail to add.
   */
  public
  void
  addRow(TransactionDetail detail)
  {
    String[] rowData = new String[4];

    rowData[ACCOUNT_COLUMN] = detail.getAccount().getIdentifier();
    rowData[AMOUNT_COLUMN] = UI_CURRENCY.format(Math.abs(detail.getTransaction().getAmount()));
    rowData[DATE_COLUMN] = UI_DATE_FORMAT.format(detail.getTransaction().getDate());
    rowData[PAYEE_COLUMN] = detail.getTransaction().getPayee();

    addRow(rowData);
  }
}
