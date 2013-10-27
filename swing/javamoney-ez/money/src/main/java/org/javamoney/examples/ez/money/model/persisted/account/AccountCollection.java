// AccountCollection

package org.javamoney.examples.ez.money.model.persisted.account;

import static org.javamoney.examples.ez.money.model.DataTypeKeys.ACCOUNT;

import org.javamoney.examples.ez.money.model.DataCollection;
import org.javamoney.examples.ez.money.model.DataElement;

/**
 * This class facilitates managing accounts.
 */
public
final
class
AccountCollection
extends DataCollection
{
  /**
   * Constructs a new collection.
   */
  public
  AccountCollection()
  {
    super(ACCOUNT);
  }

  /**
   * This method returns true if there is at least one active account in the
   * collection, otherwise false.
   *
   * @return true or false.
   */
  public
  boolean
  hasActiveAccounts()
  {
    boolean result = false;

    for(DataElement element : getCollection())
    {
      if(((Account)element).isActive() == true)
      {
        result = true;
        break;
      }
    }

    return result;
  }
}
