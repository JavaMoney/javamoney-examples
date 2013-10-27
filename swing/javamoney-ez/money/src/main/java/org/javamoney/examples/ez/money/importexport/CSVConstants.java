// CSVConstants

package org.javamoney.examples.ez.money.importexport;

import org.javamoney.examples.ez.common.utility.I18NHelper;

/**
 * This class provides constants pertaining to the CSV format.
 */
public
interface
CSVConstants
{
  /**
   * The CSV file extension.
   */
  public static final String FILE_EXTENSION = ".csv";
  /**
   * The CSV file description.
   */
  public static final String FILE_DESCRIPTION = I18NHelper.getProperty("CSVConstants.file_description");
  /**
   * A constant for a quotation mark.
   */
  public static final String QUOTE = "\"";
  /**
   * The CSV field separator.
   */
  public static final String SEPARATOR = ",";
}
