// WebReportDialog

package org.javamoney.examples.ez.money.gui.dialog;

import static org.javamoney.examples.ez.money.IconKeys.DIALOG_REPORT;

import org.javamoney.examples.ez.common.gui.DialogHeader;
import org.javamoney.examples.ez.common.utility.I18NHelper;

/**
 * This class facilitates providing a base class for the web report dialogs.
 */
class
WebReportDialog
extends ApplicationDialog
{
  /**
   * Constructs a new dialog with the specified attributes.
   *
   * @param width The dialog's width.
   * @param height The dialog's height.
   */
  protected
  WebReportDialog(int width, int height)
  {
    super(width, height);
  }

  /**
   * This method creates and returns a dialog header.
   *
   * @return A dialog header.
   */
  protected
  static
  final
  DialogHeader
  createDialogHeader()
  {
    String description = getProperty("header.description");
    String title = getProperty("header.title");

    return new DialogHeader(title, description, DIALOG_REPORT.getIcon());
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of private methods.
  //////////////////////////////////////////////////////////////////////////////

  private
  static
  String
  getProperty(String key)
  {
    return I18NHelper.getProperty("WebReportDialog." + key);
  }
}
