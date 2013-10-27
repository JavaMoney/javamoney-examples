// OFXConstants

package org.javamoney.examples.ez.money.importexport;

import org.javamoney.examples.ez.common.utility.I18NHelper;

/**
 * This class provides constants pertaining to the OFX format.
 */
public
interface
OFXConstants
{
  /**
   * The OFX file extension.
   */
  public static final String FILE_EXTENSION = ".ofx,.qfx";
  /**
   * The OFX file description.
   */
  public static final String FILE_DESCRIPTION = I18NHelper.getProperty("OFXConstants.file_description");
}
