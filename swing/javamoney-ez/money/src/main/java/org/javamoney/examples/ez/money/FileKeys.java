// FileKeys

package org.javamoney.examples.ez.money;

/**
 * This enumerated class provides keys for the files that the program needs.
 */
public
enum
FileKeys
{
  /**
   * Where the program's data is stored.
   */
  DATA("data"),
  /**
   * Where the password is stored.
   */
  PASSWORD("pass.txt"),
  /**
   * Where the program's preferences are stored.
   */
  PREFERENCES("preferences.xml");

  //////////////////////////////////////////////////////////////////////////////
  // Start of public methods.
  //////////////////////////////////////////////////////////////////////////////

  /**
   * This method returns a string for the enum constant.
   *
   * @return A string.
   */
  @Override
  public
  String
  toString()
  {
    return itsIdentifier;
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of private methods.
  //////////////////////////////////////////////////////////////////////////////

  private
  FileKeys(String identifier)
  {
    itsIdentifier = identifier;
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  private String itsIdentifier;
}
