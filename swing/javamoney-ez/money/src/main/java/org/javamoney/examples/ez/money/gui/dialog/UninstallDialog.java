// UninstallDialog

package org.javamoney.examples.ez.money.gui.dialog;

import static org.javamoney.examples.ez.money.IconKeys.CHECK_BOX;
import static org.javamoney.examples.ez.money.IconKeys.DIALOG_UNINSTALL;
import static org.javamoney.examples.ez.money.utility.DialogHelper.decide;
import static org.javamoney.examples.ez.money.utility.FileMapHelper.getFileMap;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import org.javamoney.examples.ez.common.gui.DialogHeader;
import org.javamoney.examples.ez.common.gui.Panel;
import org.javamoney.examples.ez.common.utility.I18NHelper;

/**
 * This class facilitates removing the project's saved files on the user's
 * system and exiting the program.
 */
public
final
class
UninstallDialog
extends ApplicationDialog
{
  /**
   * Constructs a new dialog.
   */
  public
  UninstallDialog()
  {
    super(500, 300);

    buildPanel();
  }

  /**
   * This method shows a dialog for uninstalling.
   */
  public
  void
  showDialog()
  {
    runDialog();
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
    panel.add(createDetailsPanel(), 0, 1, 1, 1, 100, 100);
    panel.add(createOKCancelButtonPanel(new ActionHandler()), 0, 2, 1, 1, 0, 0);
  }

  private
  Panel
  createDetailsPanel()
  {
    Panel panel = new Panel();

    // Build panel.
    panel.setAnchor(GridBagConstraints.WEST);
    panel.add(getProperty("details"), 0, 0, 2, 1, 0, 0);
    panel.addEmptyCellAt(0, 1);

    panel.setAnchor(GridBagConstraints.EAST);
    panel.add(CHECK_BOX.getIcon(), 0, 2, 1, 1, 0, 0);
    panel.add(CHECK_BOX.getIcon(), 0, 3, 1, 1, 0, 100);

    panel.setAnchor(GridBagConstraints.WEST);
    panel.add(getProperty("data"), 1, 2, 1, 1, 100, 0);
    panel.add(getProperty("preferences"), 1, 3, 1, 1, 0, 0);

    panel.setInsets(new Insets(15, 100, 25, 0));

    return panel;
  }

  private
  static
  DialogHeader
  createDialogHeader()
  {
    String description = getProperty("header.description");
    String title = getProperty("header.title");

    return new DialogHeader(title, description, DIALOG_UNINSTALL.getIcon());
  }

  private
  static
  void
  deleteFile(File file)
  {
    if(file.isDirectory() == true)
    {
      for(File temp : file.listFiles())
      {
        deleteFile(temp);
      }
    }

    file.delete();
  }

  private
  static
  String
  getProperty(String key)
  {
    return I18NHelper.getProperty("UninstallDialog." + key);
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

      if(wasAccepted() == true)
      {
        if(decide(getProperty("confirm.title"),
            getProperty("confirm.description")) == true)
        {
          deleteFile(getFileMap().getDocumentBase());
          System.exit(0);
        }
      }
      else
      {
        dispose();
      }
    }
  }
}
