// AccountChooserModel

package org.javamoney.examples.ez.money.gui.table.model;

import static org.javamoney.examples.ez.money.gui.table.AccountChooserTable.ACTIVE_COLUMN;
import static org.javamoney.examples.ez.money.gui.table.AccountChooserTable.ID_COLUMN;
import static org.javamoney.examples.ez.money.gui.table.AccountChooserTable.KEY_COLUMN;

import org.javamoney.examples.ez.money.model.persisted.account.Account;

import org.javamoney.examples.ez.common.gui.Table;

/**
 * This class facilitates adding accounts into a table.
 */
public
final
class
AccountChooserModel
extends Table.NonmutableTableModel
{
  /**
   * This method adds the specified account into the model's table.
   *
   * @param account The account to add.
   */
  public
  void
  addRow(Account account)
  {
    String[] rowData = new String[3];

    rowData[ACTIVE_COLUMN] = "";
    rowData[ID_COLUMN] = account.getIdentifier();
    rowData[KEY_COLUMN] = account.getType().toString();

    addRow(rowData);
  }
}
