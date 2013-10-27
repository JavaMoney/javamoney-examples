// RegisterModel

package org.javamoney.examples.ez.money.gui.table.model;

import static org.javamoney.examples.ez.money.ApplicationProperties.UI_CURRENCY;
import static org.javamoney.examples.ez.money.ApplicationProperties.UI_DATE_FORMAT;
import static org.javamoney.examples.ez.money.gui.table.RegisterTable.BALANCE_COLUMN;
import static org.javamoney.examples.ez.money.gui.table.RegisterTable.CHECK_NUMBER_COLUMN;
import static org.javamoney.examples.ez.money.gui.table.RegisterTable.DATE_COLUMN;
import static org.javamoney.examples.ez.money.gui.table.RegisterTable.EXPENSE_COLUMN;
import static org.javamoney.examples.ez.money.gui.table.RegisterTable.INCOME_COLUMN;
import static org.javamoney.examples.ez.money.gui.table.RegisterTable.LABEL_COLUMN;
import static org.javamoney.examples.ez.money.gui.table.RegisterTable.PAYEE_COLUMN;
import static org.javamoney.examples.ez.money.gui.table.RegisterTable.RECONCILED_COLUMN;
import static org.javamoney.examples.ez.money.utility.TransactionHelper.isExpense;

import org.javamoney.examples.ez.money.model.dynamic.transaction.RegisterTransaction;
import org.javamoney.examples.ez.money.model.persisted.transaction.Transaction;

import org.javamoney.examples.ez.common.gui.Table;

/**
 * This class facilitates adding transactions into a table.
 */
public
final
class
RegisterModel
extends Table.NonmutableTableModel
{
  /**
   * This method adds the specified transaction into the model's table.
   *
   * @param rTrans The transaction to add.
   */
  public
  void
  addRow(RegisterTransaction rTrans)
  {
    Transaction trans = rTrans.getTransaction();
    String[] rowData = new String[8];

    rowData[BALANCE_COLUMN] = UI_CURRENCY.format(rTrans.getBalance());
    rowData[CHECK_NUMBER_COLUMN] = trans.getCheckNumber();
    rowData[DATE_COLUMN] = UI_DATE_FORMAT.format(trans.getDate());
    rowData[LABEL_COLUMN] = "";
    rowData[PAYEE_COLUMN] = trans.getPayee();
    rowData[RECONCILED_COLUMN] = "";

    // Expenses and incomes are in separate columns and are always positive.
    if(isExpense(trans) == true)
    {
      rowData[EXPENSE_COLUMN] = UI_CURRENCY.format(-trans.getAmount());
      rowData[INCOME_COLUMN] = "";
    }
    else
    {
      rowData[EXPENSE_COLUMN] = "";
      rowData[INCOME_COLUMN] = UI_CURRENCY.format(trans.getAmount());
    }

    addRow(rowData);
  }
}
