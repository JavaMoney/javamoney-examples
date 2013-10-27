// ImportExportFileChooser

package org.javamoney.examples.ez.money.importexport;

import static org.javamoney.examples.ez.common.CommonConstants.IS_MAC;
import static org.javamoney.examples.ez.money.ApplicationProperties.getLastSelectedDirectory;
import static org.javamoney.examples.ez.money.ApplicationThread.getFrame;
import static org.javamoney.examples.ez.money.utility.DialogHelper.decide;

import java.awt.Dimension;
import java.io.File;

import javax.swing.JFileChooser;

import org.javamoney.examples.ez.common.utility.I18NHelper;

/**
 * This class facilities providing a customized file dialog for importing and
 * exporting.
 */
final
class
ImportExportFileChooser
extends JFileChooser
{
  /**
   * This method overrides the super class', so that when in save mode it
   * provides confirmation before overwriting files and appends an extension
   * if specified.
   */
  @Override
  public
  void
  approveSelection()
  {
    File file = getSelectedFile();
    String path = file.getPath();

    if(getMode() == ModeKeys.SAVE)
    {
      if(getForcedExtension() != null)
      {
        if(path.toLowerCase().endsWith(getForcedExtension().toLowerCase()) == false)
        {
          path += getForcedExtension();
        }

        file = new File(path);

        setSelectedFile(file);
      }

      if(file.exists() == false || confirmFileReplace(file) == true)
      {
        super.approveSelection();
      }
    }
    else
    {
      super.approveSelection();
    }
  }

  /**
   * This method displays the dialog and returns true if a file was accepted,
   * otherwise false.
   *
   * @param buttonText The text to display for the approve button.
   *
   * @return true or false.
   */
  public
  boolean
  showDialog(String buttonText)
  {
    return showDialog(getFrame(), buttonText) == APPROVE_OPTION;
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of protected methods.
  //////////////////////////////////////////////////////////////////////////////

  /**
   * Constructs a new dialog with the specified attributes.
   *
   * @param mode The mode of the dialog, either open or save.
   * @param type The action type.
   * @param format The format the file is in.
   */
  protected
  ImportExportFileChooser(ModeKeys mode, ImportExportTypeKeys type,
      ImportExportFormatKeys format)
  {
    setMode(mode);

    setAccessory(new AccessoryPanel(type, format));
    setCurrentDirectory(getLastSelectedDirectory());

    // The dialog is always too small on the Mac..
    if(IS_MAC == true)
    {
      setPreferredSize(new Dimension(700, 500));
    }
  }

  /**
   * This method sets the extension to automatically append to the end of the
   * chosen file.
   *
   * @param extension The extension to automatically append to the end of the
   * chosen file.
   */
  protected
  void
  setForcedExtension(String extension)
  {
    itsForcedExtension = extension;
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of private methods.
  //////////////////////////////////////////////////////////////////////////////

  public
  static
  boolean
  confirmFileReplace(File file)
  {
    return decide("\"" + file.getName() + "\" " + getProperty("replace.title"),
        getProperty("replace.description"));
  }

  private
  String
  getForcedExtension()
  {
    return itsForcedExtension;
  }

  private
  static
  String
  getProperty(String key)
  {
    return I18NHelper.getProperty("FileChooser." + key);
  }

  private
  ModeKeys
  getMode()
  {
    return itsMode;
  }

  private
  void
  setMode(ModeKeys mode)
  {
    itsMode = mode;
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of inner classes.
  //////////////////////////////////////////////////////////////////////////////

  /**
   * This enumerated class provides type keys for the file dialog.
   */
  protected
  enum
  ModeKeys
  {
    /**
     * Signals that the dialog is in open mode.
     */
    OPEN,
    /**
     * Signals that the dialog is in save mode.
     */
    SAVE;
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  private String itsForcedExtension;
  private ModeKeys itsMode;
}
