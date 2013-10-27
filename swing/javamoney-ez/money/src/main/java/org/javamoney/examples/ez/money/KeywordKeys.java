// KeywordKeys

package org.javamoney.examples.ez.money;

import org.javamoney.examples.ez.common.utility.I18NHelper;

/**
 * This enumerated class provides keys for the project's keywords.
 */
public
enum
KeywordKeys
{
  /**
   * Used to indicate an option for none.
   */
  NONE(I18NHelper.getSharedProperty("none"), true),
  /**
   * Used to indicate that a transaction is not categorized.
   */
  NOT_CATEGORIZED(I18NHelper.getProperty("KeywordKeys.not_categorized"), true),
  /**
   * Used to indicate a combined total.
   */
  TOTAL(I18NHelper.getProperty("KeywordKeys.total"), false),
  /**
   * The Transfer from keyword for transfers. This keyword cannot be
   * internationalized.
   */
  TRANSFER_FROM("Transfer From", true),
  /**
   * The Transfer to keyword for transfers. This keyword cannot be
   * internationalized.
   */
  TRANSFER_TO("Transfer To", true);

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
  KeywordKeys(String identifier, boolean useBrackets)
  {
    if(useBrackets == true)
    {
      identifier = "[" + identifier + "]";
    }

    itsIdentifier = identifier;
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  private String itsIdentifier;
}
