// PasswordDialog

package org.javamoney.examples.ez.money.gui.dialog;

import static org.javamoney.examples.ez.common.utility.I18NHelper.getSharedProperty;
import static org.javamoney.examples.ez.money.IconKeys.DIALOG_PASSWORD;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JPasswordField;

import org.javamoney.examples.ez.money.IconKeys;

import org.javamoney.examples.ez.common.gui.DialogHeader;
import org.javamoney.examples.ez.common.gui.Panel;
import org.javamoney.examples.ez.common.utility.I18NHelper;
import org.javamoney.examples.ez.common.utility.TextConstrainer;

/**
 * This class facilitates allowing a user to enter a password.
 */
public
final
class
PasswordDialog
extends ApplicationDialog
{
  /**
   * Constructs a new dialog.
   */
  public
  PasswordDialog()
  {
    super(525, 300);

    createFields();

    buildPanel();
  }

  /**
   * This method displays a a dialog allowing a user to enter in a password.
   * This method returns the password entered or null if the dialog was
   * cancelled.
   *
   * @return The password entered or null if the dialog was cancelled.
   */
  public
  String
  showDialog()
  {
    String password = null;

    getOKButton().setEnabled(false);

    runDialog();

    if(wasAccepted() == true)
    {
      password = getPassword(PASSWORD);
    }

    return password;
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
    panel.add(createWarningPanel(), 0, 2, 1, 1, 0, 0);
    panel.add(createOKCancelButtonPanel(new ActionHandler()), 0, 3, 1, 1, 0, 0);
  }

  private
  void
  createFields()
  {
    itsFields = new JPasswordField[2];

    for(int len = 0; len < getFields().length; ++len)
    {
      getFields()[len] = new JPasswordField();
      getFields()[len].addKeyListener(new KeyHandler());
      getFields()[len].setDocument(new TextConstrainer(16));
    }
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
    String gap = ": ";

    // Build panel.
    panel.setAnchor(GridBagConstraints.EAST);
    panel.add(getSharedProperty("password") + gap, 0, 0, 1, 1, 0, 50);
    panel.add(getProperty("retype") + gap, 0, 1, 1, 1, 0, 50);

    panel.setFill(GridBagConstraints.HORIZONTAL);
    panel.add(getFields()[PASSWORD], 1, 0, 1, 1, 100, 0);
    panel.add(getFields()[PASSWORD_CONFIRM], 1, 1, 1, 1, 0, 0);

    panel.setInsets(new Insets(10, 10, 10, 150));

    return panel;
  }

  private
  Panel
  createWarningPanel()
  {
    Panel panel = new Panel();

    // Build panel.
    panel.setAnchor(GridBagConstraints.EAST);
    panel.add(IconKeys.WARNING.getIcon(), 0, 0, 1, 1, 25, 100);
    panel.addEmptyCellAt(1, 0);

    panel.setAnchor(GridBagConstraints.WEST);
    panel.add(getProperty("warning"), 2, 0, 1, 1, 75, 0);

    return panel;
  }

  private
  JPasswordField[]
  getFields()
  {
    return itsFields;
  }

  private
  String
  getPassword(int field)
  {
    return new String(getFields()[field].getPassword());
  }

  private
  static
  String
  getProperty(String key)
  {
    return I18NHelper.getProperty("PasswordDialog." + key);
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
      String pass1 = getPassword(PASSWORD);
      String pass2 = getPassword(PASSWORD_CONFIRM);

      getOKButton().setEnabled(pass1.equals(pass2) && pass1.length() != 0);
    }
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  private JPasswordField[] itsFields;

  private static final int PASSWORD = 0;
  private static final int PASSWORD_CONFIRM = 1;
}
