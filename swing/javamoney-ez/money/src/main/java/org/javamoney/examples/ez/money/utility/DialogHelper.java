// DialogHelper

package org.javamoney.examples.ez.money.utility;

import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.INFORMATION_MESSAGE;
import static javax.swing.JOptionPane.OK_CANCEL_OPTION;
import static javax.swing.JOptionPane.OK_OPTION;
import static javax.swing.JOptionPane.QUESTION_MESSAGE;
import static javax.swing.JOptionPane.YES_NO_OPTION;
import static javax.swing.JOptionPane.YES_OPTION;
import static javax.swing.JOptionPane.showConfirmDialog;
import static javax.swing.JOptionPane.showInputDialog;
import static javax.swing.JOptionPane.showMessageDialog;
import static org.javamoney.examples.ez.money.ApplicationThread.getFrame;

import org.javamoney.examples.ez.common.gui.Panel;
import org.javamoney.examples.ez.common.utility.I18NHelper;

/**
 * This class facilitates the use of customized dialogs. All methods in this
 * class are static.
 */
public
final
class
DialogHelper
{
  /**
   * This method returns a customized, HTML formatted, string that contains the
   * specified header and message for use in dialogs and the UI.
   *
   * @param header The header in the dialog.
   * @param message The message in the dialog.
   *
   * @return A customized, HTML formatted, string.
   */
  public
  static
  String
  buildMessage(String header, String message)
  {
    String text = "<html><b><u><font color=green size=4>" + header + "</font></u></b>";

    text += "<br><br>" + message + "<br></html>";

    return text;
  }

  /**
   * This method creates an option dialog. This method returns true if the user
   * selects OK, otherwise false.
   *
   * @param panel The panel that displays the options.
   *
   * @return true or false.
   */
  public
  static
  boolean
  choose(Panel panel)
  {
    int choice = 0;

    choice = showConfirmDialog(getFrame(), panel, getProperty("choose"),
        OK_CANCEL_OPTION);

    return choice == OK_OPTION;
  }

  /**
   * This method creates an option dialog. This method returns true if the user
   * selects yes, otherwise false.
   *
   * @param header The header in the dialog.
   * @param message The message in the dialog.
   *
   * @return true or false.
   */
  public
  static
  boolean
  decide(String header, String message)
  {
    int decision = showConfirmDialog(getFrame(), buildMessage(header, message),
        getProperty("decide"), YES_NO_OPTION, QUESTION_MESSAGE);

    return decision == YES_OPTION;
  }

  /**
   * This method creates an error message dialog.
   *
   * @param header The header in the dialog.
   * @param message The message in the dialog.
   */
  public
  static
  void
  error(String header, String message)
  {
    showMessageDialog(getFrame(), buildMessage(header, message),
        getProperty("message"), ERROR_MESSAGE);
  }

  /**
   * This method creates a message dialog.
   *
   * @param header The header in the dialog.
   * @param message The message in the dialog.
   */
  public
  static
  void
  inform(String header, String message)
  {
    showMessageDialog(getFrame(), buildMessage(header, message),
        getProperty("message"), INFORMATION_MESSAGE);
  }

  /**
   * This method creates an input dialog. This method returns the text entered
   * in the input field or null if the user cancels the dialog.
   *
   * @param header The header in the dialog.
   * @param message The message in the dialog.
   * @param defaultText The text to be displayed in the input field.
   *
   * @return The text entered in the input field or null if the user cancels the
   * dialog.
   */
  public
  static
  String
  prompt(String header, String message, String defaultText)
  {
    return (String)showInputDialog(getFrame(), buildMessage(header, message),
        getProperty("prompt"), QUESTION_MESSAGE, null, null, defaultText);
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of private methods.
  //////////////////////////////////////////////////////////////////////////////

  private
  static
  String
  getProperty(String key)
  {
    return I18NHelper.getProperty("DialogHelper." + key);
  }
}
