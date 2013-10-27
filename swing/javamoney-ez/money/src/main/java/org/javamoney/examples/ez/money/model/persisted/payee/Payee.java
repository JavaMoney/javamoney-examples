// Payee

package org.javamoney.examples.ez.money.model.persisted.payee;

import org.javamoney.examples.ez.money.model.DataElement;

/**
 * This class represents where money was received and where money was paid.
 */
public
final
class
Payee
extends DataElement
{
  /**
   * Constructs a new payee.
   *
   * @param identifier The identifier.
   */
  public
  Payee(String identifier)
  {
    super(identifier);
  }
}
