// FileMap

package org.javamoney.examples.ez.money;

import static java.io.File.separator;

import java.io.File;
import java.util.EnumMap;

/**
 * This class facilitates organizing the files needed by the application.
 */
public
final
class
FileMap<K extends Enum<K>>
{
  /**
   * Constructs a file map.
   * <p>
   * <b>Note:</b> The document base is created in the file system.
   *
   * @param keyClass The enum class used as the keys.
   * @param documentBase The top level directory of the application.
   */
  public
  FileMap(Class<K> keyClass, String documentBase)
  {
    setDocumentBase(documentBase);
    setFileMap(new EnumMap<K, File>(keyClass));

    // Make the directory.
    getDocumentBase().mkdir();
  }

  /**
   * This method returns the file associated with the key.
   *
   * @param key The key.
   *
   * @return The file associated with the key.
   */
  public
  File
  get(K key)
  {
    return getFileMap().get(key);
  }

  /**
   * This method returns the document base.
   *
   * @return The document base.
   */
  public
  File
  getDocumentBase()
  {
    return new File(getDocumentBasePath());
  }

  /**
   * This method stores a file in the file map.
   * <p>
   * <b>Note:</b> This method assumes that the toString() method of the enum
   * constant returns the file name.
   *
   * @param key The key.
   */
  public
  void
  put(K key)
  {
    put(key, key.toString());
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of private methods.
  //////////////////////////////////////////////////////////////////////////////

  private
  String
  getDocumentBasePath()
  {
    String path = System.getProperty("user.home") + separator;

    return path + itsDocumentBase;
  }

  private
  EnumMap<K, File>
  getFileMap()
  {
    return itsFileMap;
  }

  private
  void
  put(K key, String path)
  {
    File file = new File(getDocumentBasePath() + separator + path);

    getFileMap().put(key, file);
  }

  private
  void
  setDocumentBase(String folder)
  {
    itsDocumentBase = folder;
  }

  private
  void
  setFileMap(EnumMap<K, File> map)
  {
    itsFileMap = map;
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  private String itsDocumentBase;
  private EnumMap<K, File> itsFileMap;
}
