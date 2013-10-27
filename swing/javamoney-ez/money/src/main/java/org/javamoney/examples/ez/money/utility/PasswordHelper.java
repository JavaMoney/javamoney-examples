// PasswordHelper

package org.javamoney.examples.ez.money.utility;

import static org.javamoney.examples.ez.money.FileKeys.PASSWORD;
import static org.javamoney.examples.ez.money.utility.CryptoHelper.getDecryptionCipher;
import static org.javamoney.examples.ez.money.utility.CryptoHelper.getEncryptionCipher;
import static org.javamoney.examples.ez.money.utility.FileMapHelper.getFileMap;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;

/**
 * This class facilitates reading and writing a password to the password file.
 * All methods in this class are static.
 */
public
final
class
PasswordHelper
{
  /**
   * This method returns a decrypted password from the password file, or null if
   * an error occurs.
   *
   * @return A decrypted password from the password file.
   */
  public
  static
  String
  readPassword()
  {
    String password = null;

    try
    {
      Cipher cipher = getDecryptionCipher(KEY);
      FileInputStream fStream = new FileInputStream(FILE);
      CipherInputStream cStream = new CipherInputStream(fStream, cipher);
      BufferedReader reader = new BufferedReader(new InputStreamReader(cStream));

      password = reader.readLine();
    }
    catch(Exception exception)
    {
      // Ignored.
    }

    return password;
  }

  /**
   * This method encrypts and writes the specified password to the password
   * file. This method returns the success of the operation.
   *
   * @param password The password to write to the password file.
   *
   * @return true or false.
   */
  public
  static
  boolean
  writePassword(String password)
  {
    boolean result = false;

    try
    {
      Cipher cipher = getEncryptionCipher(KEY);
      FileOutputStream fStream = new FileOutputStream(FILE);
      PrintStream stream = new PrintStream(new CipherOutputStream(fStream, cipher));

      stream.println(password);

      stream.close();
      result = true;
    }
    catch(Exception exception)
    {
      // Ignored.
    }

    return result;
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  private static final File FILE = getFileMap().get(PASSWORD);
  private static final String KEY = PasswordHelper.class.getSimpleName();
}
