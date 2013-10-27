// TransferCreator

package org.javamoney.examples.ez.money.gui.view.register;

import static org.javamoney.examples.ez.money.utility.DialogHelper.inform;
import static org.javamoney.examples.ez.money.utility.TransactionHelper.createCorrespondingTransfer;

import org.javamoney.examples.ez.money.KeywordKeys;
import org.javamoney.examples.ez.money.model.DataManager;
import org.javamoney.examples.ez.money.model.dynamic.transaction.TransactionTypeKeys;
import org.javamoney.examples.ez.money.model.persisted.account.Account;
import org.javamoney.examples.ez.money.model.persisted.transaction.Transaction;

import org.javamoney.examples.ez.common.utility.I18NHelper;

/**
 * This class facilitates creating transfer transactions.
 */
final
class
TransferCreator
extends TypeCreator
{
  /**
   * Constructs a new transfer creator.
   */
  protected
  TransferCreator()
  {
    super(TransactionTypeKeys.TRANSFER);
  }

  /**
   * This method creates and then adds the newly created transaction to its
   * account. It then returns the transaction if it was successfully added,
   * otherwise it returns null.
   *
   * @return The transaction if successfully added, otherwise null.
   */
  @Override
  protected
  Transaction
  createAndAdd()
  {
    Account from = getAccount(getForm().getPayFrom());
    Account to = getAccount(getCategory());
    Transaction transAdded = null;

    if(from != to)
    {
      Transaction transFrom = createTransaction(TransactionTypeKeys.EXPENSE);
      Transaction transTo = null;

      transFrom.setCategory(KeywordKeys.TRANSFER_TO.toString());
      transFrom.setPayee(to.getIdentifier());

      if((transFrom = addTransactionTo(from, transFrom)) != null)
      {
        transTo = addTransactionTo(to, createCorrespondingTransfer(transFrom, from));
      }

      // Determine the correct transaction to return.
      if(getAccount() == from)
      {
        transAdded = transFrom;
      }
      else
      {
        transAdded = transTo;
      }
    }
    else
    {
      inform(getProperty("add.title"), getProperty("add.description"));
    }

    return transAdded;
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of private methods.
  //////////////////////////////////////////////////////////////////////////////

  private
  Account
  getAccount(String identifier)
  {
    return (Account)DataManager.getAccounts().get(identifier);
  }

  private
  static
  String
  getProperty(String key)
  {
    return I18NHelper.getProperty("TransferCreator." + key);
  }
}
