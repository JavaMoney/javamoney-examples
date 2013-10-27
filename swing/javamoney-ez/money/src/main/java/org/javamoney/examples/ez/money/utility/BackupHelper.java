// BackupHelper

package org.javamoney.examples.ez.money.utility;

import static org.javamoney.examples.ez.money.ApplicationThread.getFrame;
import static org.javamoney.examples.ez.money.model.DataManager.read;
import static org.javamoney.examples.ez.money.model.DataManager.write;
import static org.javamoney.examples.ez.money.utility.DialogHelper.error;
import static org.javamoney.examples.ez.money.utility.DialogHelper.inform;
import static org.javamoney.examples.ez.money.utility.FileDialogHelper.showOpenDialog;
import static org.javamoney.examples.ez.money.utility.FileDialogHelper.showSaveDialog;

import java.io.File;

import org.javamoney.examples.ez.common.utility.I18NHelper;

/**
 * This class facilities making and restoring from backups. All methods in this
 * class are static.
 */
public
final
class
BackupHelper
{
  /**
   * This method prompts the user for the specified backup file and writes to
   * it.
   */
  public
  static
  void
  makeBackup()
  {
    File file = showSaveDialog(DEFAULT_BACKUP_FILE);

    if(file != null)
    {
      if(write(file, true, false) == true)
      {
        inform(getProperty("backup.title"),
            getProperty("backup.description") + "<br><br>\"" + file.getName() + "\".");
      }
    }
  }

  /**
   * This method prompts the user for the specified backup file, reads it, and
   * then updates the views.
   * <p>
   * <b>Note:</b> This method will overwrite all existing data and does not
   * require the user to confirm the process.
   */
  public
  static
  void
  restoreFromBackup()
  {
    File file = showOpenDialog();

    if(file != null)
    {
      // Write data first incase an error occurs.
      write();

      if(read(file, false, false) == true)
      {
        getFrame().signalDataChange();

        inform(getProperty("restore.success.title"),
            getProperty("restore.success.description"));
      }
      else
      {
        // Restore data.
        read();

        error(getProperty("restore.failure.title"),
            getProperty("restore.failure.description"));
      }
    }
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of private methods.
  //////////////////////////////////////////////////////////////////////////////

  private
  static
  String
  getProperty(String key)
  {
    return I18NHelper.getProperty("BackupHelper." + key);
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  private static final String DEFAULT_BACKUP_FILE = "JavaMoney EZ.bak";
}
