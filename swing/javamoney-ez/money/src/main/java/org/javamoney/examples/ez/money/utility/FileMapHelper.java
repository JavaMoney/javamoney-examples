// FileMapHelper

package org.javamoney.examples.ez.money.utility;

import org.javamoney.examples.ez.money.FileKeys;
import org.javamoney.examples.ez.money.FileMap;

/**
 * This class facilitates initializing the project's file map and providing
 * access to those files.
 */
public
final
class
FileMapHelper
{
  /**
   * This method returns the file map that contains the project's required
   * files.
   *
   * @return The file map that contains the project's required files.
   */
  public
  static
  FileMap<FileKeys>
  getFileMap()
  {
    return itsFileMap;
  }

  /**
   * This method creates the project's document base and then caches the files
   * that are required by the project into the global file map.
   */
  public
  static
  void
  initializeAndCache()
  {
    setFileMap(new FileMap<FileKeys>(FileKeys.class, ".javamoney"));

    for(FileKeys key : FileKeys.values())
    {
      getFileMap().put(key);
    }
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of private methods.
  //////////////////////////////////////////////////////////////////////////////

  private
  static
  void
  setFileMap(FileMap<FileKeys> map)
  {
    itsFileMap = map;
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  private static FileMap<FileKeys> itsFileMap;
}
