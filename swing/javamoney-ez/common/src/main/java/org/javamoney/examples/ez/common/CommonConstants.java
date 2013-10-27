// CommonConstants

package org.javamoney.examples.ez.common;

/**
 * This interface provides common constants.
 */
public
interface
CommonConstants
{
  /**
   * Whether or not the platform is a Mac.
   */
  public static final boolean IS_MAC = System.getProperty("mrj.version") != null;
  /**
   * Whether or not the platform is Windows.
   */
  public static final boolean IS_WINDOWS = System.getProperty("os.name").startsWith("Windows") == true;
}
