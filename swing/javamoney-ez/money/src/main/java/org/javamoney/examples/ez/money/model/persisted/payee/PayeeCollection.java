// PayeeCollection

package org.javamoney.examples.ez.money.model.persisted.payee;

import static org.javamoney.examples.ez.money.model.DataTypeKeys.PAYEE;

import org.javamoney.examples.ez.money.model.DataCollection;

/**
 * This class facilitates managing payees.
 */
public
final
class
PayeeCollection
extends DataCollection
{
  /**
   * Constructs a new collection.
   */
  public
  PayeeCollection()
  {
    super(PAYEE);
  }
}
