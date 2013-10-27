// ImportExportFileFilter

package org.javamoney.examples.ez.money.importexport;

import java.io.File;
import java.util.StringTokenizer;

import javax.swing.filechooser.FileFilter;

/**
 * This class facilitates filtering files in a file dialog chooser.
 */
final
class
ImportExportFileFilter
extends FileFilter
{
  /**
   * This method returns true if the filter accepts the specified file,
   * otherwise false.
   *
   * @param file The file to check.
   *
   * @return true or false.
   */
  @Override
  public
  boolean
  accept(File file)
  {
    boolean result = true;

    if(file.isDirectory() == false)
    {
      StringTokenizer tokens = new StringTokenizer(getFileType(), ",");

      while(tokens.hasMoreTokens() == true)
      {
        if(file.getName().toLowerCase().endsWith(tokens.nextToken()) == false)
        {
          result = false;
        }
        else
        {
          result = true;
          break;
        }
      }
    }

    return result;
  }

  /**
   * This method returns the description for the file filter.
   *
   * @return The description.
   */
  @Override
  public
  String
  getDescription()
  {
    return itsDescription;
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of protected methods.
  //////////////////////////////////////////////////////////////////////////////

  /**
   * Constructs a new file filter.
   * <p>
   * <b>Note:</b> For multiple file extensions, separate the extensions with a
   * ','.
   *
   * @param type The filer's file type.
   * @param description The filter's file description.
   */
  protected
  ImportExportFileFilter(String type, String description)
  {
    setDescription(description);
    setFileType(type);
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of private methods.
  //////////////////////////////////////////////////////////////////////////////

  private
  String
  getFileType()
  {
    return itsFileType;
  }

  private
  void
  setDescription(String description)
  {
    itsDescription = description;
  }

  private
  void
  setFileType(String fileType)
  {
    itsFileType = fileType;
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  private String itsDescription;
  private String itsFileType;
}
