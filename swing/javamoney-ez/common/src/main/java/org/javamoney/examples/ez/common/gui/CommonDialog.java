// CommonDialog

package org.javamoney.examples.ez.common.gui;

import static org.javamoney.examples.ez.common.CommonIconKeys.DIALOG_ACCEPT;
import static org.javamoney.examples.ez.common.CommonIconKeys.DIALOG_CANCEL;
import static org.javamoney.examples.ez.common.utility.BoundsHelper.createCenteredScreenBounds;
import static org.javamoney.examples.ez.common.utility.ButtonHelper.buildButton;
import static org.javamoney.examples.ez.common.utility.I18NHelper.getSharedProperty;

import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;

import org.javamoney.examples.ez.common.CommonIconKeys;

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
CommonDialog
extends JDialog
{
  /**
   * Constructs a new dialog.
   *
   * @param owner The frame from which the dialog is displayed.
   * @param title The text to display in the dialog's title bar.
   * @param width The dialog's width.
   * @param height The dialog's height.
   */
  public
  CommonDialog(JFrame owner, String title, int width, int height)
  {
    super(owner, title, true);

    setContentPane(new Panel());
    setResizable(false);
    setSize(width, height);
  }

  /**
   * This method returns the panel used in the dialog.
   *
   * @return The panel used in the dialog.
   */
  @Override
  public
  final
  Panel
  getContentPane()
  {
    return (Panel)super.getContentPane();
  }

  /**
   * This method returns true if the dialog was accepted, otherwise false.
   *
   * @return true or false.
   */
  public
  final
  boolean
  wasAccepted()
  {
    return itsAccepted;
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of protected methods.
  //////////////////////////////////////////////////////////////////////////////

  /**
   * This method creates and returns a customized panel that has one button for
   * signaling that the dialog was canceled.
   *
   * @param listener The action listener that listens for the button clicks.
   *
   * @return A panel with one button.
   */
  protected
  final
  Panel
  createCancelButtonPanel(ActionListener listener)
  {
    return createCancelButtonPanel("", listener);
  }

  /**
   * This method creates and returns a customized panel that has one button for
   * signaling that the dialog was canceled.
   *
   * @param cancelText The text of the cancel button.
   * @param listener The action listener that listens for the button clicks.
   *
   * @return A panel with one button.
   */
  protected
  final
  Panel
  createCancelButtonPanel(String cancelText, ActionListener listener)
  {
    Panel panel = new Panel();

    setCancelButton(createButton(cancelText, getSharedProperty("cancel"),
        listener, DIALOG_CANCEL, ACTION_CANCEL, false));

    // Build panel.
    panel.addSpacer(0, 0, 1, 1, 100, 100);
    panel.add(getCancelButton(), 1, 0, 1, 1, 0, 0);
    panel.setInsets(createButtonPanelInsets());

    return panel;
  }

  /**
   * This method creates and returns a customized panel that has one button for
   * signaling that the dialog was accepted.
   *
   * @param listener The action listener that listens for the button clicks.
   *
   * @return A panel with one button.
   */
  protected
  final
  Panel
  createOKButtonPanel(ActionListener listener)
  {
    return createOKButtonPanel("", listener);
  }

  /**
   * This method creates and returns a customized panel that has one button for
   * signaling that the dialog was accepted.
   *
   * @param okText The text of the accept button.
   * @param listener The action listener that listens for the button clicks.
   *
   * @return A panel with one button.
   */
  protected
  final
  Panel
  createOKButtonPanel(String okText, ActionListener listener)
  {
    Panel panel = new Panel();

    setOKButton(createButton(okText, getSharedProperty("ok"), listener,
        DIALOG_ACCEPT, ACTION_OK, true));

    // Build panel.
    panel.addSpacer(0, 0, 1, 1, 100, 100);
    panel.add(getOKButton(), 1, 0, 1, 1, 0, 0);
    panel.setInsets(createButtonPanelInsets());

    return panel;
  }

  /**
   * This method creates and returns a customized panel that has two buttons for
   * signaling that the dialog was either accepted or canceled.
   *
   * @param listener The action listener that listens for the button clicks.
   *
   * @return A panel with two buttons.
   */
  protected
  final
  Panel
  createOKCancelButtonPanel(ActionListener listener)
  {
    return createOKCancelButtonPanel("", "", listener);
  }

  /**
   * This method creates and returns a customized panel that has two buttons for
   * signaling that the dialog was either accepted or canceled.
   *
   * @param okText The text of the accept button.
   * @param cancelText The text of the cancel button.
   * @param listener The action listener that listens for the button clicks.
   *
   * @return A panel with two buttons.
   */
  protected
  final
  Panel
  createOKCancelButtonPanel(String okText, String cancelText, ActionListener listener)
  {
    Panel panel = new Panel();

    setCancelButton(createButton(cancelText, getSharedProperty("cancel"),
        listener, DIALOG_CANCEL, ACTION_CANCEL, false));

    setOKButton(createButton(okText, getSharedProperty("ok"), listener,
        DIALOG_ACCEPT, ACTION_OK, true));

    // Build panel.
    panel.addSpacer(0, 0, 1, 1, 100, 100);
    panel.add(getOKButton(), 1, 0, 1, 1, 0, 0);
    panel.addSpacer(2, 0, 1, 1, 0, 0);
    panel.add(getCancelButton(), 3, 0, 1, 1, 0, 0);
    panel.setInsets(createButtonPanelInsets());

    return panel;
  }

  /**
   * This method returns the cancel button.
   *
   * @return The cancel button.
   */
  protected
  final
  JButton
  getCancelButton()
  {
    return itsCancelButton;
  }

  /**
   * This method returns the OK button.
   *
   * @return The OK button.
   */
  protected
  final
  JButton
  getOKButton()
  {
    return itsOKButton;
  }

  /**
   * This method determines and sets the dialog's bounds, then makes it visible.
   */
  protected
  final
  void
  runDialog()
  {
    if(getOwner() instanceof Frame)
    {
      Frame frame = (Frame)getOwner();

      setBounds(frame.createBounds(getWidth(), getHeight()));
    }
    else
    {
      setBounds(createCenteredScreenBounds(getWidth(), getHeight()));
    }

    setVisible(true);
  }

  /**
   * This method sets whether or not the dialog was accepted.
   *
   * @param value true or false.
   */
  protected
  final
  void
  setAccepted(boolean value)
  {
    itsAccepted = value;
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of private methods.
  //////////////////////////////////////////////////////////////////////////////

  private
  JButton
  createButton(String text, String defaultText, ActionListener listener,
      CommonIconKeys key, String command, boolean defaultEnabled)
  {
    JButton button = new JButton();

    if(defaultEnabled == true)
    {
      button.setDefaultCapable(true);
      getRootPane().setDefaultButton(button);
    }

    if(text.length() == 0)
    {
      text = defaultText;
    }

    buildButton(button, text, key.getIcon(), listener, command, null);

    return button;
  }

  private
  static
  Insets
  createButtonPanelInsets()
  {
    return new Insets(10, 10, 10, 10);
  }

  private
  void
  setCancelButton(JButton button)
  {
    itsCancelButton = button;
  }

  private
  void
  setOKButton(JButton button)
  {
    itsOKButton = button;
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  private boolean itsAccepted;
  private JButton itsCancelButton;
  private JButton itsOKButton;

  /**
   * This is the action associated with the cancel button.
   */
  protected static final String ACTION_CANCEL = "Cancel";
  /**
   * This is the action associated with the OK button.
   */
  protected static final String ACTION_OK = "OK";
}
