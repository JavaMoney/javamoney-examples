// FileManagerPanel

package org.javamoney.examples.ez.money.gui.dialog.preferences;

import static org.javamoney.examples.ez.common.utility.BorderHelper.createTitledBorder;
import static org.javamoney.examples.ez.common.utility.ButtonHelper.buildButton;
import static org.javamoney.examples.ez.common.utility.I18NHelper.getSharedProperty;
import static org.javamoney.examples.ez.money.ApplicationProperties.autoBackup;
import static org.javamoney.examples.ez.money.ApplicationProperties.getBackupFile;
import static org.javamoney.examples.ez.money.ApplicationProperties.getDataFile;
import static org.javamoney.examples.ez.money.ApplicationProperties.passwordRequired;
import static org.javamoney.examples.ez.money.ApplicationProperties.setAutoBackup;
import static org.javamoney.examples.ez.money.ApplicationProperties.setBackupFile;
import static org.javamoney.examples.ez.money.ApplicationProperties.setDataFile;
import static org.javamoney.examples.ez.money.ApplicationProperties.setPasswordRequired;
import static org.javamoney.examples.ez.money.ApplicationProperties.setUseDefaultDataFile;
import static org.javamoney.examples.ez.money.ApplicationProperties.useDefaultDataFile;
import static org.javamoney.examples.ez.money.model.DataManager.read;
import static org.javamoney.examples.ez.money.model.DataManager.write;
import static org.javamoney.examples.ez.money.utility.DialogHelper.decide;
import static org.javamoney.examples.ez.money.utility.FileDialogHelper.showSaveDialog;
import static org.javamoney.examples.ez.money.utility.PasswordHelper.writePassword;

import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JLabel;
import javax.swing.JTextField;

import org.javamoney.examples.ez.money.gui.dialog.PasswordDialog;

import org.javamoney.examples.ez.common.gui.CheckBox;
import org.javamoney.examples.ez.common.gui.Link;
import org.javamoney.examples.ez.common.gui.Panel;
import org.javamoney.examples.ez.common.utility.I18NHelper;

/**
 * This class facilitates managing the programs's data file and backups.
 */
final
class
FileManagerPanel
extends Panel
{
  /**
   * Constructs a new preferences panel.
   */
  public
  FileManagerPanel()
  {
    createCheckBoxes();
    createFields();
    createLinks();

    buildPanel();
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of private methods.
  //////////////////////////////////////////////////////////////////////////////

  private
  void
  buildPanel()
  {
    // Build panel.
    setFill(GridBagConstraints.BOTH);
    add(createBackupPanel(), 0, 0, 1, 1, 100, 50);
    addEmptyCellAt(0, 1);
    add(createDataFilePanel(), 0, 2, 1, 1, 0, 50);
  }

  private
  void
  changeBackupFile()
  {
    File file = showSaveDialog(getBackupFile().getName());

    if(file != null)
    {
      setBackupFile(file);
      setText(BACKUP, file.toString());
    }
    else if(getBackupFile().toString().length() == 0)
    {
      setAutoBackup(false);
      setText(BACKUP, "");
      getCheckBoxes()[BACKUP].setSelected(false);
      getLinks()[BACKUP].setEnabled(false);
    }
  }

  private
  void
  changeDataFile()
  {
    File file = showSaveDialog(getDataFile().getName());

    if(file != null)
    {
      setDataFile(file);
      setText(DATA, file.toString());

      if(file.exists() == true)
      {
        if(decide(getProperty("load.title"),
            getProperty("load.description")) == true)
        {
          // Write data incase an error occurs.
          write();

          if(read(file, true, passwordRequired()) == false)
          {
            // Restore data.
            read();
          }
        }
      }
    }
    else if(getDataFile().toString().length() == 0)
    {
      setText(DATA, "");
      setUseDefaultDataFile(true);
      getCheckBoxes()[DATA].setSelected(true);
      getLinks()[DATA].setEnabled(false);
    }
  }

  private
  Panel
  createBackupPanel()
  {
    Panel panel = new Panel();

    // Build panel.
    panel.setFill(GridBagConstraints.HORIZONTAL);
    panel.add(getCheckBoxes()[BACKUP], 0, 0, 2, 1, 0, 50);
    panel.add(new JLabel(getProperty("location") + ": "), 0, 2, 1, 1, 0, 50);
    panel.add(getFields()[BACKUP], 1, 2, 1, 1, 100, 0);

    panel.setAnchor(GridBagConstraints.WEST);
    panel.setFill(GridBagConstraints.NONE);
    panel.add(getLinks()[BACKUP], 2, 2, 1, 1, 0, 0);
    panel.addEmptyCellAt(2, 0, 10);

    panel.setBorder(createTitledBorder(getProperty("title.backup"), false));

    // Set defaults.
    setText(BACKUP, getBackupFile().toString());
    getCheckBoxes()[BACKUP].setSelected(autoBackup());
    getLinks()[BACKUP].setEnabled(getCheckBoxes()[BACKUP].isSelected());

    return panel;
  }

  private
  void
  createCheckBoxes()
  {
    ActionHandler handler = new ActionHandler();

    itsCheckBoxes = new CheckBox[3];

    for(int len = 0; len < getCheckBoxes().length ;++len)
    {
      getCheckBoxes()[len] = new CheckBox();
    }

    buildButton(getCheckBoxes()[BACKUP], getProperty("auto_backup"), handler);
    buildButton(getCheckBoxes()[DATA], getProperty("default"), handler);
    buildButton(getCheckBoxes()[PASSWORD], getProperty("password"), handler);
  }

  private
  Panel
  createDataFilePanel()
  {
    Panel panel = new Panel();

    // Build panel.
    panel.setFill(GridBagConstraints.HORIZONTAL);
    panel.add(getCheckBoxes()[PASSWORD], 0, 1, 2, 1, 0, 33);
    panel.add(getCheckBoxes()[DATA], 0, 2, 2, 1, 0, 34);
    panel.add(new JLabel(getProperty("location") + ": "), 0, 3, 1, 1, 0, 33);
    panel.add(getFields()[DATA], 1, 3, 1, 1, 100, 0);

    panel.setAnchor(GridBagConstraints.WEST);
    panel.setFill(GridBagConstraints.NONE);
    panel.add(getLinks()[DATA], 2, 3, 1, 1, 0, 0);
    panel.addEmptyCellAt(2, 2, 10);

    panel.setBorder(createTitledBorder(getProperty("title.file"), false));

    // Set defaults.
    setText(DATA, getDataFile().toString());
    getCheckBoxes()[DATA].setSelected(useDefaultDataFile());
    getCheckBoxes()[PASSWORD].setSelected(passwordRequired());
    getLinks()[DATA].setEnabled(getCheckBoxes()[DATA].isSelected() == false);

    return panel;
  }

  private
  void
  createFields()
  {
    itsFields = new JTextField[2];

    for(int len = 0; len < getFields().length ;++len)
    {
      getFields()[len] = new JTextField();
      getFields()[len].setEditable(false);
    }
  }

  private
  void
  createLinks()
  {
    ActionHandler handler = new ActionHandler();

    itsLinks  = new Link[2];

    for(int len = 0; len < getLinks().length ;++len)
    {
      getLinks()[len] = new Link();
    }

    buildButton(getLinks()[BACKUP], getSharedProperty("change"), handler);
    buildButton(getLinks()[DATA], getSharedProperty("change"), handler);
  }

  private
  CheckBox[]
  getCheckBoxes()
  {
    return itsCheckBoxes;
  }

  private
  JTextField[]
  getFields()
  {
    return itsFields;
  }

  private
  Link[]
  getLinks()
  {
    return itsLinks;
  }

  private
  static
  String
  getProperty(String key)
  {
    return I18NHelper.getProperty("FileManagerPanel." + key);
  }

  private
  void
  promptForPassword()
  {
    String password = new PasswordDialog().showDialog();

    if(password != null)
    {
      if(writePassword(password) == true)
      {
        setPasswordRequired(true);
      }
    }
    else
    {
      getCheckBoxes()[PASSWORD].setSelected(false);
      setPasswordRequired(false);
    }
  }

  private
  void
  setText(int field, String text)
  {
    getFields()[field].setText(text);
    getFields()[field].setCaretPosition(text.length());
  }

  /////////////////////////////////////////////////////////////////////////////
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
      Object source = event.getSource();

      if(source == getCheckBoxes()[BACKUP])
      {
        setAutoBackup(!autoBackup());
        getLinks()[BACKUP].setEnabled(getCheckBoxes()[BACKUP].isSelected());

        if(getCheckBoxes()[BACKUP].isSelected() == true)
        {
            changeBackupFile();
        }
        else
        {
          setText(BACKUP, "");
          setBackupFile(new File(""));
        }
      }
      else if(source == getCheckBoxes()[DATA])
      {
        setUseDefaultDataFile(!useDefaultDataFile());
        getLinks()[DATA].setEnabled(getCheckBoxes()[DATA].isSelected() == false);

        if(getCheckBoxes()[DATA].isSelected() == true)
        {
          setDataFile(new File(""));
          setText(DATA, "");
        }
        else
        {
          changeDataFile();
        }
      }
      else if(source == getCheckBoxes()[PASSWORD])
      {
        if(getCheckBoxes()[PASSWORD].isSelected() == true)
        {
          promptForPassword();
        }
        else
        {
          setPasswordRequired(false);
        }
      }
      else if(source == getLinks()[BACKUP])
      {
        changeBackupFile();
      }
      else
      {
        changeDataFile();
      }
    }
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  private CheckBox[] itsCheckBoxes;
  private JTextField[] itsFields;
  private Link[] itsLinks;

  private static final int BACKUP = 0;
  private static final int DATA = 1;
  private static final int PASSWORD = 2;
}
