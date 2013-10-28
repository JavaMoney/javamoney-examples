// IconKeys

package org.javamoney.examples.ez.money;

import static org.javamoney.examples.ez.common.utility.ResourceHelper.createIcon;
import static org.javamoney.examples.ez.common.utility.ResourceHelper.getResource;

import java.net.URL;

import javax.swing.ImageIcon;

/**
 * This enumerated class provides keys for access to the project's icons.
 */
public
enum
IconKeys
{
  /**
   * The icon used to indicate a group of actions.
   */
  ACTIONS("Actions"),
  /**
   * An icon of an arrow pointing in the back direction.
   */
  ARROW_BACK("ArrowBack"),
  /**
   * An icon of an arrow pointing in the down direction.
   */
  ARROW_DOWN("ArrowDown"),
  /**
   * An icon of an arrow pointing in the forward direction.
   */
  ARROW_FORWARD("ArrowForward"),
  /**
   * An icon of an arrow pointing in the up direction.
   */
  ARROW_UP("ArrowUp"),
  /**
   * A common check box icon.
   */
  CHECK_BOX("CheckBox"),
  /**
   * The icon used to indicate a date.
   */
  DATE("Date"),
  /**
   * The icon used in the edit transactions dialog.
   */
  DIALOG_EDIT_TRANSACTIONS("DialogEditTransactions"),
  /**
   * The icon used in the export dialog.
   */
  DIALOG_EXPORT("DialogExport"),
  /**
   * The icon used in the transaction filter dialog.
   */
  DIALOG_FILTER("DialogFilter"),
  /**
   * The icon used in the import dialog.
   */
  DIALOG_IMPORT("DialogImport"),
  /**
   * The icon used in the password dialog.
   */
  DIALOG_PASSWORD("DialogPassword"),
  /**
   * The icon used in the reports dialog.
   */
  DIALOG_REPORT("DialogReport"),
  /**
   * The icon used in the reminder alarm dialog.
   */
  DIALOG_REMINDER_ALARM("DialogReminderAlarm"),
  /**
   * The icon used in the split dialog.
   */
  DIALOG_SPLIT("DialogSplit"),
  /**
   * The icon used in the uninstall dialog.
   */
  DIALOG_UNINSTALL("DialogUninstall"),
  /**
   * The icon used to indicate filtering options.
   */
  FILTER("Filter"),
  /**
   * The icon for selecting the next check number in the transaction form.
   */
  FORM_NEXT_CHECK("FormNextCheck"),
  /**
   * The image used as the background on the home page.
   */
  HOME_BACKGROUND("HomeBackground"),
  /**
   * The image spacer used on the home page.
   */
  HOME_SPACER("HomeSpacer"),
  /**
   * The icon used to tile the headers on the home page on the Mac platform.
   */
  HOME_TILE_HEADER("HomeTileHeader"),
  /**
   * The icon used to tile the summary on the home page.
   */
  HOME_TILE_SUMMARY("HomeTileSummary"),
  /**
   * The icon used to tile the types on the home page.
   */
  HOME_TILE_TYPE("HomeTileTypes"),
  /**
   * An icon for the color blue.
   */
  LABEL_BLUE("LabelBlue"),
  /**
   * An icon for the color gray.
   */
  LABEL_GRAY("LabelGray"),
  /**
   * An icon for the color green.
   */
  LABEL_GREEN("LabelGreen"),
  /**
   * An icon for no color.
   */
  LABEL_NONE("LabelNone"),
  /**
   * An icon for the color orange.
   */
  LABEL_ORANGE("LabelOrange"),
  /**
   * An icon for the color purple.
   */
  LABEL_PURPLE("LabelPurple"),
  /**
   * An icon for the color red.
   */
  LABEL_RED("LabelRed"),
  /**
   * An icon for the color yellow.
   */
  LABEL_YELLOW("LabelYellow"),
  /**
   * The icon for a menu.
   */
  MENU_ARROW("MenuArrow"),
  /**
   * A pie icon for the color blue.
   */
  PIE_CHART_BLUE("PieChartBlue"),
  /**
   * A pie legend icon for the color blue.
   */
  PIE_CHART_BLUE_LEGEND("PieChartBlueLegend"),
  /**
   * A pie icon for the color gray.
   */
  PIE_CHART_GRAY("PieChartGray"),
  /**
   * A pie legend icon for the color gray.
   */
  PIE_CHART_GRAY_LEGEND("PieChartGrayLegend"),
  /**
   * A pie icon for the color green.
   */
  PIE_CHART_GREEN("PieChartGreen"),
  /**
   * A pie legend icon for the color green.
   */
  PIE_CHART_GREEN_LEGEND("PieChartGreenLegend"),
  /**
   * A pie icon for the color orange.
   */
  PIE_CHART_ORANGE("PieChartOrange"),
  /**
   * A pie legend icon for the color orange.
   */
  PIE_CHART_ORANGE_LEGEND("PieChartOrangeLegend"),
  /**
   * A pie icon for the color red.
   */
  PIE_CHART_RED("PieChartRed"),
  /**
   * A pie legend icon for the color red.
   */
  PIE_CHART_RED_LEGEND("PieChartRedLegend"),
  /**
   * The icon for accounts in the preferences dialog.
   */
  PREFERENCES_ACCOUNTS("PreferencesAccounts"),
  /**
   * The icon used for the income and expense categories.
   */
  PREFERENCES_CATEGORIES("PreferencesCategories"),
  /**
   * The icon for the general options in the preferences dialog.
   */
  PREFERENCES_GENERAL("PreferencesGeneral"),
  /**
   * The icon for the network settings in the preferences dialog.
   */
  PREFERENCES_NETWORK("PreferencesNetwork"),
  /**
   * The icon for payees in the preferences dialog.
   */
  PREFERENCES_PAYEES("PreferencesPayees"),
  /**
   * The icon for reminders in the preferences dialog.
   */
  PREFERENCES_REMINDERS("PreferencesReminders"),
  /**
   * The reconciled icon for the register table.
   */
  TABLE_RECONCILED("TableReconciled"),
  /**
   * The icon for selecting the budgets view.
   */
  VIEWS_BUDGETS("ViewsBudgets"),
  /**
   * The icon for selecting the home view.
   */
  VIEWS_HOME("ViewsHome"),
  /**
   * The icon for selecting the totals view.
   */
  VIEWS_TOTALS("ViewsTotals"),
  /**
   * The icon used to warn the user.
   */
  WARNING("Warning"),
  /**
   * The image used as the project's banner on the Windows platform.
   */
  WINDOWS_BANNER("WindowsBanner"),
  
  /**
   * The icon used as the project's symbol on the Windows platform.
   */
  WINDOWS_LOGO("WindowsLogo");

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
    return itsURL;
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of private methods.
  //////////////////////////////////////////////////////////////////////////////

  private
  IconKeys(String fileName)
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

  private static final String RESOURCE_PATH = "org/javamoney/examples/ez/money/resources/";
}
