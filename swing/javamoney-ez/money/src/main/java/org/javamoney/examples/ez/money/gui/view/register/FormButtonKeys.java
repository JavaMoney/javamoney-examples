// FormButtonKeys

package org.javamoney.examples.ez.money.gui.view.register;

import org.javamoney.examples.ez.common.utility.I18NHelper;

/**
 * This enumerated class provides keys for the form's buttons.
 */
enum
FormButtonKeys
{
  /**
   * The button to cancel the transaction.
   */
  CANCEL(I18NHelper.getSharedProperty("cancel")),
  /**
   * The button to choose a date. This key is not internationalized.
   */
  DATE_PICKER(""),
  /**
   * The button to edit the selected transaction.
   */
  EDIT(I18NHelper.getSharedProperty("edit")),
  /**
   * The button to accept the transaction.
   */
  ENTER(I18NHelper.getProperty("FormButtonKeys.enter")),
  /**
   * The button to start creating a new transaction.
   */
  NEW(I18NHelper.getProperty("FormButtonKeys.new")),
  /**
   * The button to select the next check number. This key is not
   * internationalized.
   */
  NEXT(""),
  /**
   * The button to indicate whether or not the transaction is reconciled.
   */
  PENDING(I18NHelper.getSharedProperty("pending")),
  /**
   * The button to split the transaction.
   */
  SPLIT(I18NHelper.getSharedProperty("split"));

  //////////////////////////////////////////////////////////////////////////////
  // Start of protected methods.
  //////////////////////////////////////////////////////////////////////////////

  /**
   * This method returns the enum constant's text.
   *
   * @return The enum constant's text.
   */
  protected
  String
  getText()
  {
    return itsText;
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of private methods.
  //////////////////////////////////////////////////////////////////////////////

  private
  FormButtonKeys(String text)
  {
    itsText = text;
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  private String itsText;
}
