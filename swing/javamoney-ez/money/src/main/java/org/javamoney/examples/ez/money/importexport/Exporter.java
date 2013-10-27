// Exporter

package org.javamoney.examples.ez.money.importexport;

import static org.javamoney.examples.ez.money.ApplicationProperties.setLastSelectedDirectory;
import static org.javamoney.examples.ez.money.importexport.ImportExportFileChooser.ModeKeys.SAVE;
import static org.javamoney.examples.ez.money.importexport.ImportExportTypeKeys.EXPORT;
import static org.javamoney.examples.ez.money.utility.DialogHelper.error;
import static org.javamoney.examples.ez.money.utility.DialogHelper.inform;

import java.io.File;

import org.javamoney.examples.ez.money.exception.DialogCanceledException;
import org.javamoney.examples.ez.money.gui.dialog.ImportExportAccountDialog;
import org.javamoney.examples.ez.money.model.persisted.account.Account;

import org.javamoney.examples.ez.common.utility.I18NHelper;

/**
 * This class facilitates exporting account data to a file.
 */
abstract
class
Exporter
{
  /**
   * This method presents the user with a dialog for choosing the accounts to
   * export and then a dialog for choosing the files to export them to.
   *
   * @param format The format the file will be in.
   * @param ext The file types to display.
   * @param desc The description of the file types.
   */
  protected
  final
  void
  doExport(ImportExportFormatKeys format, String ext, String desc)
  {
    boolean success = false;

    try
    {
      Account[] accounts = new ImportExportAccountDialog(EXPORT).showDialog();

      if(accounts != null)
      {
        success = true;

        for(Account account : accounts)
        {
          success = exportAccount(account, format, ext, desc);
        }
      }

      if(success == true)
      {
        inform(getProperty("success.title"), getProperty("success.description"));
      }
    }
    catch(DialogCanceledException dialogCanceledException)
    {
      // Ignored.
    }
  }

  /**
   * This method exports the specified account's data to the specified file.
   * This method returns true if the operation was successful, otherwise false.
   *
   * @param account The account to export.
   * @param file The file to export to.
   *
   * @return true or false.
   */
  protected
  abstract
  boolean
  exportAccount(Account account, File file);

  //////////////////////////////////////////////////////////////////////////////
  // Start of private methods.
  //////////////////////////////////////////////////////////////////////////////

  private
  boolean
  exportAccount(Account account, ImportExportFormatKeys format, String ext,
      String desc)
  throws DialogCanceledException
  {
    ImportExportFileChooser chooser = new ImportExportFileChooser(SAVE, EXPORT, format);
    boolean result = false;

    chooser.setFileFilter(new ImportExportFileFilter(ext, desc));
    chooser.setForcedExtension(ext);
    chooser.setSelectedFile(new File(account.getIdentifier()));

    if(chooser.showDialog(getProperty("title")) == true)
    {
      File file = chooser.getSelectedFile();

      setLastSelectedDirectory(file.getParentFile());

      result = exportAccount(account, file);

      if(result == false)
      {
        error(getProperty("failure.title"),
            getProperty("failure.description") + ": " + account.getIdentifier());
      }
    }
    else
    {
      throw new DialogCanceledException();
    }

    return result;
  }

  private
  static
  String
  getProperty(String key)
  {
    return I18NHelper.getProperty("Exporter." + key);
  }
}
