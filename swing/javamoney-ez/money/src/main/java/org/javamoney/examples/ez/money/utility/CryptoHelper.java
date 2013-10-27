// CryptoHelper

package org.javamoney.examples.ez.money.utility;

import static javax.crypto.Cipher.DECRYPT_MODE;
import static javax.crypto.Cipher.ENCRYPT_MODE;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * This class facilitates providing convenience methods into Java's cryptography
 * framework. All methods in this class are static.
 */
public
final
class
CryptoHelper
{
  /**
   * This method returns a decryption cipher.
   *
   * @param key The key used for decryption.
   *
   * @return A decryption cipher.
   */
  public
  static
  Cipher
  getDecryptionCipher(String key)
  {
    return getCipher(key, DECRYPT_MODE);
  }

  /**
   * This method returns an encryption cipher.
   *
   * @param key The key used for encryption.
   *
   * @return An encryption cipher.
   */
  public
  static
  Cipher
  getEncryptionCipher(String key)
  {
    return getCipher(key, ENCRYPT_MODE);
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of private methods.
  //////////////////////////////////////////////////////////////////////////////

  private
  static
  String
  formatKey(String key)
  {
    if(key.length() > SIZE)
    {
      key = key.substring(0, SIZE);
    }
    else
    {
      while(key.length() < SIZE)
      {
        key += "#";
      }
    }

    return key;
  }

  private
  static
  Cipher
  getCipher(String key, int mode)
  {
    Cipher cipher = null;

    key = formatKey(key);

    try
    {
      cipher = Cipher.getInstance(ALGORITHM);
      cipher.init(mode, new SecretKeySpec(key.getBytes(), ALGORITHM));
    }
    catch(Exception exception)
    {
      // Ignored.
    }

    return cipher;
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  private static final String ALGORITHM = "AES";
  private static final int SIZE = 16;
}
