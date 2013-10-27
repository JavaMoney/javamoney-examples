// FileDialogHelper

package org.javamoney.examples.ez.money.utility;

import static java.awt.FileDialog.LOAD;
import static java.awt.FileDialog.SAVE;
import static java.io.File.separator;
import static org.javamoney.examples.ez.money.ApplicationProperties.getLastSelectedDirectory;
import static org.javamoney.examples.ez.money.ApplicationProperties.setLastSelectedDirectory;
import static org.javamoney.examples.ez.money.ApplicationThread.getFrame;

import java.awt.FileDialog;
import java.io.File;

/**
 * This class facilitates allowing a user to choose files. All methods in this
 * class are static.
 */
public
final
class
FileDialogHelper
{
  /**
   * This method prompts a user with a file dialog and returns the selected
   * file, or null if the dialog was canceled.
   *
   * @return The selected file.
   */
  public
  static
  File
  showOpenDialog()
  {
    FileDialog chooser = new FileDialog(getFrame(), "", LOAD);
    File file = null;
    String fileName = null;

    chooser.setDirectory(getLastSelectedDirectory().toString());
    chooser.setVisible(true);

    fileName = chooser.getFile();

    if(fileName != null)
    {
      file = new File(chooser.getDirectory() + separator + fileName);

      // Save the last selected file.
      setLastSelectedDirectory(file.getParentFile());
    }

    chooser.dispose();

    return file;
  }

  /**
   * This method prompts a user with a file dialog and returns the selected
   * file, or null if the dialog was canceled.
   *
   * @param defaultFile The file initially selected.
   *
   * @return The selected file.
   */
  public
  static
  File
  showSaveDialog(String defaultFile)
  {
    return showSaveDialog(defaultFile, "");
  }

  /**
   * This method prompts a user with a file dialog and returns the selected
   * file, or null if the dialog was canceled.
   *
   * @param defaultFile The file initially selected.
   * @param ext The extension the file needs to have.
   *
   * @return The selected file.
   */
  public
  static
  File
  showSaveDialog(String defaultFile, String ext)
  {
    FileDialog chooser = new FileDialog(getFrame(), "", SAVE);
    File file = null;
    String fileName = null;

    chooser.setDirectory(getLastSelectedDirectory().toString());
    chooser.setFile(defaultFile);
    chooser.setVisible(true);

    fileName = chooser.getFile();

    if(fileName != null)
    {
      // If an extension is specified, make sure the file has it.
      if(ext.length() != 0 && fileName.endsWith(ext) == false)
      {
        fileName += ext;
      }

      file = new File(chooser.getDirectory() + separator + fileName);

      // Save the last selected file.
      setLastSelectedDirectory(file.getParentFile());
    }

    chooser.dispose();

    return file;
  }
}
