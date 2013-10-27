// NoAccountForImportException

package org.javamoney.examples.ez.money.exception;

import static org.javamoney.examples.ez.common.utility.I18NHelper.getProperty;

/**
 * This class is thrown or logged to indicate that there was no account to use
 * for importing transactions into.
 */
public
final
class
NoAccountForImportException
extends Exception
{
  /**
   * Constructs a new exception.
   */
  public
  NoAccountForImportException()
  {
    super(getProperty("NoAccountForImportException.description"));
  }
}
