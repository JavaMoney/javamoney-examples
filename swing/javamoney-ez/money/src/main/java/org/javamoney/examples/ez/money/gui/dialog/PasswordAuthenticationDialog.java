// PasswordAuthenticationDialog

package org.javamoney.examples.ez.money.gui.dialog;

import static org.javamoney.examples.ez.common.utility.I18NHelper.getSharedProperty;
import static org.javamoney.examples.ez.money.IconKeys.DIALOG_PASSWORD;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JPasswordField;

import org.javamoney.examples.ez.common.gui.DialogHeader;
import org.javamoney.examples.ez.common.gui.Panel;
import org.javamoney.examples.ez.common.utility.I18NHelper;

/**
 * This class facilitates allowing the user to authenticate the password.
 */
public
final
class
PasswordAuthenticationDialog
extends ApplicationDialog
{
  /**
   * Constructs a new dialog with the specified owner frame and that will
   * authenticate against the specified password.
   *
   * @param owner The frame from which the dialog is displayed.
   * @param password The password required for authentication.
   */
  public
  PasswordAuthenticationDialog(JFrame owner, String password)
  {
    super(owner, 500, 250);

    setField(new JPasswordField());
    setPassword(password);

    buildPanel();

    // Add listeners.
    getField().addKeyListener(new KeyHandler());
  }

  /**
   * This method returns true if the user was authenticated, otherwise false.
   *
   * @return true or false.
   */
  public
  boolean
  showDialog()
  {
    boolean authenticated = false;

    getOKButton().setEnabled(false);

    runDialog();

    if(wasAccepted() == true)
    {
      authenticated = true;
    }

    return authenticated;
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of private methods.
  //////////////////////////////////////////////////////////////////////////////

  private
  void
  buildPanel()
  {
    Panel panel = getContentPane();

    // Build panel.
    panel.setFill(GridBagConstraints.BOTH);
    panel.add(createDialogHeader(), 0, 0, 1, 1, 0, 0);
    panel.add(createPasswordPanel(), 0, 1, 1, 1, 100, 100);
    panel.add(createOKCancelButtonPanel(new ActionHandler()), 0, 2, 1, 1, 0, 0);
  }

  private
  static
  DialogHeader
  createDialogHeader()
  {
    String description = getProperty("header.description");
    String title = getProperty("header.title");

    return new DialogHeader(title, description, DIALOG_PASSWORD.getIcon());
  }

  private
  Panel
  createPasswordPanel()
  {
    Panel panel = new Panel();

    // Build panel.
    panel.setAnchor(GridBagConstraints.EAST);
    panel.add(getSharedProperty("password") + ": ", 0, 0, 1, 1, 0, 100);

    panel.setFill(GridBagConstraints.HORIZONTAL);
    panel.add(getField(), 1, 0, 1, 1, 100, 0);

    panel.setInsets(new Insets(10, 10, 10, 100));

    return panel;
  }

  private
  JPasswordField
  getField()
  {
    return itsField;
  }

  private
  String
  getPassword()
  {
    return itsPassword;
  }

  private
  static
  String
  getProperty(String key)
  {
    return I18NHelper.getProperty("PasswordAuthenticationDialog." + key);
  }

  private
  void
  setField(JPasswordField field)
  {
    itsField = field;
  }

  private
  void
  setPassword(String password)
  {
    itsPassword = password;
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of inner classes.
  //////////////////////////////////////////////////////////////////////////////

  private
  class
  ActionHandler
  implements ActionListener
  {
    public
    void
    actionPerformed(ActionEvent event)
    {
      setAccepted(event.getActionCommand().equals(ACTION_OK));
      dispose();
    }
  }

  private
  class
  KeyHandler
  extends KeyAdapter
  {
    @Override
    public
    void
    keyReleased(KeyEvent event)
    {
      String input = new String(getField().getPassword());

      getOKButton().setEnabled(input.equals(getPassword()));
    }
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  private JPasswordField itsField;
  private String itsPassword;
}
