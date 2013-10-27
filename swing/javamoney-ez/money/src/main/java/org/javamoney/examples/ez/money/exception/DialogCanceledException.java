// DialogCanceledException

package org.javamoney.examples.ez.money.exception;

/**
 * This class is thrown to indicate that a dialog was canceled.
 */
public
final
class
DialogCanceledException
extends Exception
{
  /**
   * Constructs a new exception.
   */
  public
  DialogCanceledException()
  {
    super(DialogCanceledException.class.getSimpleName());
  }
}
