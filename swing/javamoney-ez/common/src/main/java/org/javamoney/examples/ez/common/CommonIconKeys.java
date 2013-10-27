// CommonIconKeys

package org.javamoney.examples.ez.common;

import static org.javamoney.examples.ez.common.utility.ResourceHelper.createIcon;
import static org.javamoney.examples.ez.common.utility.ResourceHelper.getResource;

import java.net.URL;

import javax.swing.ImageIcon;

/**
 * This enumerated class provides keys for convenient access to the project's
 * icons.
 */
public
enum
CommonIconKeys implements IconHolder
{
  /**
   * An icon symbolic for approval.
   */
  DIALOG_ACCEPT("DialogAccept"),
  /**
   * An icon symbolic for cancel.
   */
  DIALOG_CANCEL("DialogCancel"),
  /**
   * An icon for indicating a search field.
   */
  SEARCH("Search"),
  /**
   * An icon for indicating clearing the search field.
   */
  SEARCH_CLEAR("SearchClear"),
  /**
   * An icon for indicating clearing the search field that is pressed.
   */
  SEARCH_CLEAR_PRESSED("SearchClearPressed"),
  /**
   * An icon used in the search widget to indicate there is no text to clear.
   */
  SEARCH_NO_TEXT("SearchNoText"),
  /**
   * An icon for indicating a search field with a menu option.
   */
  SEARCH_WITH_MENU("SearchWithMenu"),
  /**
   * An icon for indicating how a table is sorting the data.
   */
  SORT_COLUMN_DOWN("SortColumnDown"),
  /**
   * An icon for indicating how a table is sorting the data.
   */
  SORT_COLUMN_UP("SortColumnUp");

  //////////////////////////////////////////////////////////////////////////////
  // Start of public methods.
  //////////////////////////////////////////////////////////////////////////////

  /**
   * This method returns the icon associated with the enum constant.
   *
   * @return The icon associated with the enum constant.
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
    return itsURL;
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of private methods.
  //////////////////////////////////////////////////////////////////////////////

  private
  CommonIconKeys(String fileName)
  {
    URL resource = getResource(RESOURCE_PATH + fileName + ".png");

    itsIcon = createIcon(resource);
    itsURL = resource.toString();
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  private ImageIcon itsIcon;
  private String itsURL;

  private static final String RESOURCE_PATH = "org/javamoney/examples/ez/common/resources/";
}
