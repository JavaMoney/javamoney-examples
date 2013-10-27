// ApplicationDialog

package org.javamoney.examples.ez.money.gui.dialog;

import static org.javamoney.examples.ez.money.ApplicationThread.getFrame;

import javax.swing.JFrame;

import org.javamoney.examples.ez.common.gui.CommonDialog;

/**
 * This class facilitates creating and using simple dialogs with only one or two
 * options. Although the text of these options can be customized, they have
 * predefined icons and can only signal that the dialog was either accepted or
 * canceled.
 * <p>
 * <b>Note:</b> This class must be extended to build and customize the dialog
 * since the functionality is protected.
 */
public
class
ApplicationDialog
extends CommonDialog
{
  /**
   * Constructs a new dialog.
   *
   * @param width The dialog's width.
   * @param height The dialog's height.
   */
  protected
  ApplicationDialog(int width, int height)
  {
    this(getFrame(), width, height);
  }

  /**
   * Constructs a new dialog.
   *
   * @param owner The frame from which the dialog is displayed.
   * @param width The dialog's width.
   * @param height The dialog's height.
   */
  protected
  ApplicationDialog(JFrame owner, int width, int height)
  {
    super(owner, "", width, height);
  }

  /**
   * Constructs a new dialog.
   *
   * @param title The text to display in the dialog's title bar.
   * @param width The dialog's width.
   * @param height The dialog's height.
   */
  public
  ApplicationDialog(String title, int width, int height)
  {
    super(getFrame(), title, width, height);
  }
}
