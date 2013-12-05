// DialogCanceledException

package org.javamoney.examples.ez.money.exception;

import javax.money.MonetaryException;

/**
 * This class is thrown to indicate that a dialog was canceled.
 */
public
final
class
DialogCanceledException
extends MonetaryException
{
  /**
	 * 
	 */
	private static final long serialVersionUID = 5320783062413147906L;

/**
   * Constructs a new exception.
   */
  public
  DialogCanceledException()
  {
    super(DialogCanceledException.class.getSimpleName());
  }
}
