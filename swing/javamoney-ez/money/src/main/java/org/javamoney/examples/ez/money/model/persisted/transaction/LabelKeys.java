// LabelKeys

package org.javamoney.examples.ez.money.model.persisted.transaction;

import javax.swing.ImageIcon;

import org.javamoney.examples.ez.money.IconKeys;

import org.javamoney.examples.ez.common.utility.I18NHelper;

/**
 * This enumerated class provides keys for a transaction's label.
 */
public
enum
LabelKeys
{
  // Declared in order they should appear in chooser.
  /**
   * No color.
   */
  NONE(I18NHelper.getSharedProperty("none"), IconKeys.LABEL_NONE.getIcon()),
  /**
   * The color red.
   */
  RED(I18NHelper.getProperty("LabelKeys.red"), IconKeys.LABEL_RED.getIcon()),
  /**
   * The color orange.
   */
  ORANGE(I18NHelper.getProperty("LabelKeys.orange"), IconKeys.LABEL_ORANGE.getIcon()),
  /**
   * The color yellow.
   */
  YELLOW(I18NHelper.getProperty("LabelKeys.yellow"), IconKeys.LABEL_YELLOW.getIcon()),
  /**
   * The color green.
   */
  GREEN(I18NHelper.getProperty("LabelKeys.green"), IconKeys.LABEL_GREEN.getIcon()),
  /**
   * The color blue.
   */
  BLUE(I18NHelper.getProperty("LabelKeys.blue"), IconKeys.LABEL_BLUE.getIcon()),
  /**
   * The color magenta.
   */
  PURPLE(I18NHelper.getProperty("LabelKeys.purple"), IconKeys.LABEL_PURPLE.getIcon()),
  /**
   * The color gray.
   */
  GRAY(I18NHelper.getProperty("LabelKeys.gray"), IconKeys.LABEL_GRAY.getIcon());

  //////////////////////////////////////////////////////////////////////////////
  // Start of public methods.
  //////////////////////////////////////////////////////////////////////////////

  /**
   * This method returns the icon.
   *
   * @return The icon.
   */
  public
  ImageIcon
  getIcon()
  {
    return itsIcon;
  }

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
  LabelKeys(String identifier, ImageIcon icon)
  {
    itsIcon = icon;
    itsIdentifier = identifier;
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  private ImageIcon itsIcon;
  private String itsIdentifier;
}
