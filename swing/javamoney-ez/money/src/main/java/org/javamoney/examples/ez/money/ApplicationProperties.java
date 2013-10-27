// ApplicationProperties

package org.javamoney.examples.ez.money;

import static org.javamoney.examples.ez.common.utility.BoundsHelper.createCenteredScreenBounds;
import static org.javamoney.examples.ez.common.utility.BoundsHelper.getScreenBounds;
import static org.javamoney.examples.ez.money.FileKeys.PREFERENCES;
import static org.javamoney.examples.ez.money.utility.FileMapHelper.getFileMap;

import java.awt.Rectangle;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.Proxy;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.swing.JFrame;

import org.javamoney.examples.ez.money.gui.dialog.preferences.PreferencesKeys;
import org.javamoney.examples.ez.money.gui.table.RegisterTable;
import org.javamoney.examples.ez.money.importexport.ImportExportDateFormatKeys;
import org.javamoney.examples.ez.money.locale.Currency;
import org.javamoney.examples.ez.money.locale.CurrencyFormatKeys;
import org.javamoney.examples.ez.money.locale.CurrencySymbolKeys;
import org.javamoney.examples.ez.money.locale.DateFormatKeys;
import org.javamoney.examples.ez.money.report.CategoryReportSortByKeys;

import org.javamoney.examples.ez.common.net.ProxyConnectionTypeKeys;
import org.javamoney.examples.ez.common.net.ProxyWrapper;

/**
 * This class manages the application's properties. All methods in this class
 * are static.
 */
public
final
class
ApplicationProperties
{
  /**
   * This method returns true if auto backup is enabled, otherwise false. The
   * default value is false.
   *
   * @return true or false.
   */
  public
  static
  boolean
  autoBackup()
  {
    return parseBoolean(Keys.AUTO_BACKUP, "false");
  }

  /**
   * This method returns true if credit balances should be displayed as
   * positive, otherwise false. The default value is true.
   *
   * @return true or false.
   */
  public
  static
  boolean
  creditBalanceIsPositive()
  {
    return parseBoolean(Keys.CREDIT_BALANCE_IS_POSITIVE, "true");
  }

  /**
   * This method returns true if categories should be exported for CSV,
   * otherwise false. The default value is false.
   *
   * @return true or false.
   */
  public
  static
  boolean
  exportCategoryForCSV()
  {
    return parseBoolean(Keys.CSV_EXPORT_CATEGORY, "false");
  }

  /**
   * This method returns the file to save the program data to. The default is a
   * non-existing file.
   *
   * @return The file to save the program data to.
   */
  public
  static
  File
  getBackupFile()
  {
    return new File(getProperty(Keys.BACKUP_FILE, ""));
  }

  /**
   * This method returns the field in which a category report will be sorted.
   * The default value is the category field.
   *
   * @return The field in which a category report will be sorted.
   */
  public
  static
  CategoryReportSortByKeys
  getCategoryReportSortByField()
  {
    String name = getProperty(Keys.CATEGORY_REPORT_SORT_BY, CategoryReportSortByKeys.CATEGORY.name());

    return CategoryReportSortByKeys.valueOf(name);
  }

  /**
   * This method returns the column order to be used when importing from a CSV
   * file. The default value is the declaration order of CSVColumnKeys.
   *
   * @return The column order.
   */
  public
  static
  int[]
  getCSVColumnOrder()
  {
    int[] order = new int[] {0, 1, 2, 3, 4};
    String values = getProperty(Keys.CSV_COLUMN_ORDER, "0,1,2,3,4");
    StringTokenizer tokens = new StringTokenizer(values, ",");

    try
    {
      int[] temp = new int[5];

      for(int len = 0; len < order.length; ++len)
      {
        temp[len] = Integer.parseInt(tokens.nextToken());
      }

      order = temp;
    }
    catch(Exception ignored)
    {
      // Ignored.
    }

    return order;
  }

  /**
   * This method returns the user's currency format. The default value is the US
   * dollar.
   *
   * @return The user's currency format.
   */
  public
  static
  CurrencyFormatKeys
  getCurrencyFormat()
  {
    String name = getProperty(Keys.CURRENCY_FORMAT, CurrencyFormatKeys.US_DOLLAR.name());

    return CurrencyFormatKeys.valueOf(name);
  }

  /**
   * This method returns the user's currency symbol. The default value is none.
   * <p>
   * <b>Note:</b> For performance reasons, the constant UI_CURRENCY_SYMBOL
   * should be used instead.
   *
   * @return The user's currency symbol.
   */
  public
  static
  CurrencySymbolKeys
  getCurrencySymbol()
  {
    String name = getProperty(Keys.CURRENCY_SYMBOL, CurrencySymbolKeys.NONE.name());

    return CurrencySymbolKeys.valueOf(name);
  }

  /**
   * This method returns the file to save the program data to. The default is a
   * non-existing file.
   *
   * @return The file to save the program data to.
   */
  public
  static
  File
  getDataFile()
  {
    return new File(getProperty(Keys.DATA_FILE, ""));
  }

  /**
   * This method returns the user's date format. The default value is M/D/Y.
   * <p>
   * <b>Note:</b> For performance reasons, the constant UI_DATE_FORMAT should be
   * used instead.
   *
   * @return The user's date format.
   */
  public
  static
  DateFormatKeys
  getDateFormat()
  {
    String name = getProperty(Keys.DATE_FORMAT, DateFormatKeys.MONTH_FIRST.name());

    return DateFormatKeys.valueOf(name);
  }

  /**
   * This method returns the day of week the calendar chooser should display as
   * its first day. The default value is Sunday.
   *
   * @return The day of week the calendar chooser should display as its first
   * day.
   */
  public
  static
  int
  getDateWeekday()
  {
    return parseInteger(Keys.DATE_WEEKDAY, Calendar.SUNDAY);
  }

  /**
   * This method returns the application frame's bounds. The default value is
   * 1024x768.
   *
   * @return The application frame's bounds.
   */
  public
  static
  Rectangle
  getFrameBounds()
  {
    Rectangle defaultBounds = getScreenBounds();
    Rectangle bounds = new Rectangle();

    if(defaultBounds.width > 1024)
    {
      defaultBounds = createCenteredScreenBounds(1024, 768);
    }

    bounds.height = parseInteger(Keys.FRAME_BOUNDS_H, defaultBounds.height);
    bounds.width = parseInteger(Keys.FRAME_BOUNDS_W, defaultBounds.width);
    bounds.x = parseInteger(Keys.FRAME_BOUNDS_X, defaultBounds.x);
    bounds.y = parseInteger(Keys.FRAME_BOUNDS_Y, defaultBounds.y);

    return bounds;
  }

  /**
   * This method returns the application frame's state. The default value is
   * normal.
   *
   * @return The application frame's state.
   */
  public
  static
  int
  getFrameState()
  {
    return parseInteger(Keys.FRAME_STATE, JFrame.NORMAL);
  }

  /**
   * This method returns the currency format to be used while importing and
   * exporting. The default value is #,###.##.
   *
   * @return The currency format to be used while importing and exporting.
   */
  public
  static
  CurrencyFormatKeys
  getImportExportCurrencyFormat()
  {
    String type = getProperty(Keys.IMPORT_EXPORT_CURRENCY_FORMAT,
        CurrencyFormatKeys.US_DOLLAR.name());

    return CurrencyFormatKeys.valueOf(type);
  }

  /**
   * This method returns the date format to be used while importing and
   * exporting. The default value is MM/dd/yyyy.
   *
   * @return The date format to be used while importing and exporting.
   */
  public
  static
  ImportExportDateFormatKeys
  getImportExportDateFormat()
  {
    String type = getProperty(Keys.IMPORT_EXPORT_DATE_FORMAT,
        ImportExportDateFormatKeys.MONTH_FIRST.name());

    return ImportExportDateFormatKeys.valueOf(type);
  }

  /**
   * This method returns the time the program last ran.
   *
   * @return The time the program last ran.
   */
  public
  static
  Date
  getLastRuntime()
  {
    Date date = new Date();
    String property = getProperty(Keys.LAST_RUNTIME, ImportExportDateFormatKeys.MONTH_FIRST.format(date));

    try
    {
      date = ImportExportDateFormatKeys.MONTH_FIRST.parse(property);
    }
    catch(ParseException parseException)
    {
      // Ignored.
    }

    return date;
  }

  /**
   * This method returns the last selected directory from the file dialog. The
   * default is the user's home directory.
   *
   * @return The last selected directory from the file dialog.
   */
  public
  static
  File
  getLastSelectedDirectory()
  {
    return new File(getProperty(Keys.LAST_SELECTED_DIR, ""));
  }

  /**
   * This method returns the index of the last used preference panel. The
   * default is the account panel.
   *
   * @return The index of the last used preference panel.
   */
  public
  static
  int
  getLastUsedPreferencePanelIndex()
  {
    return parseInteger(Keys.LAST_USED_PREFERENCE_PANEL_INDEX,
        PreferencesKeys.GENERAL.ordinal());
  }

  /**
   * This method returns the proxy to be used.
   *
   * @return The proxy to be used.
   */
  public
  static
  ProxyWrapper
  getProxy()
  {
    ProxyWrapper proxyWrapper = new ProxyWrapper();

    proxyWrapper.setAddress(getProxyAddress());
    proxyWrapper.setConnectionType(getProxyConnectionType());
    proxyWrapper.setPort(getProxyPort());
    proxyWrapper.setType(getProxyType());

    return proxyWrapper;
  }

  /**
   * This method returns the address used for the proxy. The default value is
   * none.
   *
   * @return The address used for the proxy.
   */
  public
  static
  String
  getProxyAddress()
  {
    return getProperty(Keys.PROXY_ADDRESS, "");
  }

  /**
   * This method returns the connection type used for the proxy. The default
   * value is DIRECT.
   *
   * @return The connection type used for the proxy.
   */
  public
  static
  ProxyConnectionTypeKeys
  getProxyConnectionType()
  {
    String type = getProperty(Keys.PROXY_CONNECTION_TYPE,
        ProxyConnectionTypeKeys.DIRECT.toString());

    return ProxyConnectionTypeKeys.valueOf(type);
  }

  /**
   * This method returns the port used for the proxy. The default value is -1.
   *
   * @return The port used for the proxy.
   */
  public
  static
  int
  getProxyPort()
  {
    return parseInteger(Keys.PROXY_PORT, -1);
  }

  /**
   * This method returns the type used for the proxy. The default value is HTTP.
   *
   * @return The type used for the proxy.
   */
  public
  static
  Proxy.Type
  getProxyType()
  {
    String type = getProperty(Keys.PROXY_TYPE, Proxy.Type.HTTP.toString());

    return Proxy.Type.valueOf(type);
  }

  /**
   * This method returns the column to be sorted in the account register. The
   * default is the date column.
   *
   * @return The column to be sorted in the account register.
   */
  public
  static
  int
  getRegisterColumnToSort()
  {
    return parseInteger(Keys.REGISTER_COLUMN_TO_SORT, RegisterTable.DATE_COLUMN);
  }

  /**
   * This method returns true if categories should be included in an account
   * statement, otherwise false. The default value is false.
   *
   * @return true or false.
   */
  public
  static
  boolean
  includeCategoriesInAccountStatement()
  {
    return parseBoolean(Keys.ACCOUNT_STATEMENT_INCLUDE_CATEGORIES, "false");
  }

  /**
   * This method returns true if check numbers should be included in an account
   * statement, otherwise false. The default value is true.
   *
   * @return true or false.
   */
  public
  static
  boolean
  includeCheckInAccountStatement()
  {
    return parseBoolean(Keys.ACCOUNT_STATEMENT_INCLUDE_CHECK, "true");
  }

  /**
   * This method returns true if transaction details should be included in a
   * report, otherwise false. The default value is false.
   *
   * @return true or false.
   */
  public
  static
  boolean
  includeDetailsInReport()
  {
    return parseBoolean(Keys.REPORT_INCLUDE_DETAILS, "false");
  }

  /**
   * This method returns true if category groups should be included in a report,
   * otherwise false. The default value is false.
   *
   * @return true or false.
   */
  public
  static
  boolean
  includeGroupsInReport()
  {
    return parseBoolean(Keys.REPORT_INCLUDE_GROUPS, "false");
  }

  /**
   * This method returns true if notes should be included in an account
   * statement, otherwise false. The default value is false.
   *
   * @return true or false.
   */
  public
  static
  boolean
  includeNotesInAccountStatement()
  {
    return parseBoolean(Keys.ACCOUNT_STATEMENT_INCLUDE_NOTES, "false");
  }

  /**
   * This method returns true if a transaction's reconciled status should be
   * included in an account statement, otherwise false. The default value is
   * false.
   *
   * @return true or false.
   */
  public
  static
  boolean
  includeReconciledStatusInAccountStatement()
  {
    return parseBoolean(Keys.ACCOUNT_STATEMENT_INCLUDE_RECONCILED, "false");
  }

  /**
   * This method returns true if the account register should invert its sort,
   * otherwise false. The default value is false.
   *
   * @return true or false.
   */
  public
  static
  boolean
  invertSortForRegister()
  {
    return parseBoolean(Keys.INVERT_SORT_FOR_REGISTER, "false");
  }

  /**
   * This method returns true if new transactions are to be marked as pending,
   * otherwise false. The default value is false.
   *
   * @return true or false.
   */
  public
  static
  boolean
  newTransactionsArePending()
  {
    return parseBoolean(Keys.NEW_TRANSACTIONS_PENDING, "false");
  }

  /**
   * This method returns true if a password is required, otherwise false. The
   * default value is false.
   *
   * @return true or false.
   */
  public
  static
  boolean
  passwordRequired()
  {
    return parseBoolean(Keys.PASSWORD_REQUIRED, "false");
  }

  /**
   * This method reads in the properties from file. This method returns true if
   * no error occurs during the read, otherwise false.
   *
   * @return true or false.
   */
  public
  static
  boolean
  read()
  {
    boolean result = false;

    try
    {
      FileInputStream stream = new FileInputStream(getFileMap().get(PREFERENCES));

      getProperties().loadFromXML(stream);

      stream.close();
      result = true;
    }
    catch(Exception exception)
    {
      // Ignored.
    }

    // Set performance constants.
    UI_DATE_FORMAT = getDateFormat();
    UI_CURRENCY = getCurrencyFormat().getCurrency();
    UI_CURRENCY_SYMBOL = getCurrencySymbol();

    return result;
  }

  /**
   * This method sets whether or not auto backup is enabled.
   *
   * @param value true or false.
   */
  public
  static
  void
  setAutoBackup(boolean value)
  {
    setProperty(Keys.AUTO_BACKUP, value);
  }

  /**
   * This method sets the file to save the program data to.
   *
   * @param file The file to save the program data to.
   */
  public
  static
  void
  setBackupFile(File file)
  {
    setProperty(Keys.BACKUP_FILE, file.toString());
  }

  /**
   * This method sets the field in which a category report will be sorted.
   *
   * @param key The field in which a category report will be sorted.
   */
  public
  static
  void
  setCategoryReportSortByField(CategoryReportSortByKeys key)
  {
    setProperty(Keys.CATEGORY_REPORT_SORT_BY, key.name());
  }

  /**
   * This method sets whether or not credit balances should be displayed as
   * positive.
   *
   * @param value true or false.
   */
  public
  static
  void
  setCreditBalanceIsPositive(boolean value)
  {
    setProperty(Keys.CREDIT_BALANCE_IS_POSITIVE, value);
  }

  /**
   * This method sets the column order to be used when importing from a CSV
   * file.
   *
   * @param order The column order.
   */
  public
  static
  void
  setCSVColumnOrder(int[] order)
  {
    String value = "";

    for(int len = 0; len < order.length; ++len)
    {
      value += order[len] + ",";
    }

    setProperty(Keys.CSV_COLUMN_ORDER, value);
  }

  /**
   * This method sets the user's currency format.
   *
   * @param key The user's currency format.
   */
  public
  static
  void
  setCurrencyFormat(CurrencyFormatKeys key)
  {
    UI_CURRENCY = key.getCurrency();

    setProperty(Keys.CURRENCY_FORMAT, key.name());
  }

  /**
   * This method sets the user's currency symbol.
   *
   * @param key The user's currency symbol.
   */
  public
  static
  void
  setCurrencySymbol(CurrencySymbolKeys key)
  {
    UI_CURRENCY_SYMBOL = key;

    setProperty(Keys.CURRENCY_SYMBOL, key.name());
  }

  /**
   * This method sets the file to save the program data to.
   *
   * @param file The file to save the program data to.
   */
  public
  static
  void
  setDataFile(File file)
  {
    setProperty(Keys.DATA_FILE, file.toString());
  }

  /**
   * This method sets the user's date format.
   *
   * @param key The user's date format.
   */
  public
  static
  void
  setDateFormat(DateFormatKeys key)
  {
    UI_DATE_FORMAT = key;

    setProperty(Keys.DATE_FORMAT, key.name());
  }

  /**
   * This method sets the day of week the calendar chooser should display as its
   * first day.
   *
   * @param day The day of week the calendar chooser should display as its first
   * day.
   */
  public
  static
  void
  setDateWeekday(int day)
  {
    setProperty(Keys.DATE_WEEKDAY, day);
  }

  /**
   * This method sets whether or not categories should be exported for CSV.
   *
   * @param value true or false.
   */
  public
  static
  void
  setExportCategoryForCSV(boolean value)
  {
    setProperty(Keys.CSV_EXPORT_CATEGORY, value);
  }

  /**
   * This method sets the application frame's bounds.
   *
   * @param bounds The application frame's bounds.
   */
  public
  static
  void
  setFrameBounds(Rectangle bounds)
  {
    setProperty(Keys.FRAME_BOUNDS_H, bounds.height);
    setProperty(Keys.FRAME_BOUNDS_W, bounds.width);
    setProperty(Keys.FRAME_BOUNDS_X, bounds.x);
    setProperty(Keys.FRAME_BOUNDS_Y, bounds.y);
  }

  /**
   * This method sets the application frame's state.
   *
   * @param state The application frame's state.
   */
  public
  static
  void
  setFrameState(int state)
  {
    setProperty(Keys.FRAME_STATE, state);
  }

  /**
   * This method sets the currency format to be used while importing and
   * exporting.
   *
   * @param key The currency format to be used while importing and exporting.
   */
  public
  static
  void
  setImportExportCurrencyFormat(CurrencyFormatKeys key)
  {
    setProperty(Keys.IMPORT_EXPORT_CURRENCY_FORMAT, key.name());
  }

  /**
   * This method sets the date format to be used while importing and
   * exporting.
   *
   * @param key The date format to be used while importing and exporting.
   */
  public
  static
  void
  setImportExportDateFormat(ImportExportDateFormatKeys key)
  {
    setProperty(Keys.IMPORT_EXPORT_DATE_FORMAT, key.name());
  }

  /**
   * This method sets whether or not categories should be included in an account
   * statement.
   *
   * @param value true or false.
   */
  public
  static
  void
  setIncludeCategoriesInAccountStatement(boolean value)
  {
    setProperty(Keys.ACCOUNT_STATEMENT_INCLUDE_CATEGORIES, value);
  }

  /**
   * This method sets whether or not check numbers should be included in an
   * account statement
   *
   * @param value true or false.
   */
  public
  static
  void
  setIncludeCheckInAccountStatement(boolean value)
  {
    setProperty(Keys.ACCOUNT_STATEMENT_INCLUDE_CHECK, value);
  }

  /**
   * This method sets whether or not transaction details should be included in a
   * report.
   *
   * @param value true or false.
   */
  public
  static
  void
  setIncludeDetailsInReport(boolean value)
  {
    setProperty(Keys.REPORT_INCLUDE_DETAILS, value);
  }

  /**
   * This method sets whether or not category groups should be included in a
   * report.
   *
   * @param value true or false.
   */
  public
  static
  void
  setIncludeGroupsInReport(boolean value)
  {
    setProperty(Keys.REPORT_INCLUDE_GROUPS, value);
  }

  /**
   * This method sets whether or not notes should be included in an account
   * statement.
   *
   * @param value true or false.
   */
  public
  static
  void
  setIncludeNotesInAccountStatement(boolean value)
  {
    setProperty(Keys.ACCOUNT_STATEMENT_INCLUDE_NOTES, value);
  }

  /**
   * This method sets whether or not a transaction's reconciled status should be
   * included in an account statement.
   *
   * @param value true or false.
   */
  public
  static
  void
  setIncludeReconciledStatusInAccountStatement(boolean value)
  {
    setProperty(Keys.ACCOUNT_STATEMENT_INCLUDE_RECONCILED, value);
  }

  /**
   * This method sets whether or not the account register should invert its
   * sort.
   *
   * @param value true or false.
   */
  public
  static
  void
  setInvertSortForRegister(boolean value)
  {
    setProperty(Keys.INVERT_SORT_FOR_REGISTER, value);
  }

  /**
   * This method sets the time the program last ran.
   *
   * @param date The time the program last ran.
   */
  public
  static
  void
  setLastRuntime(Date date)
  {
    setProperty(Keys.LAST_RUNTIME, ImportExportDateFormatKeys.MONTH_FIRST.format(date));
  }

  /**
   * This method sets the last selected directory from the file dialog.
   *
   * @param file The last selected directory from the file dialog.
   */
  public
  static
  void
  setLastSelectedDirectory(File file)
  {
    setProperty(Keys.LAST_SELECTED_DIR, file.toString());
  }

  /**
   * This method sets the index of the last used preference panel.
   *
   * @param index The index of the last used preference panel.
   */
  public
  static
  void
  setLastUsedPreferencePanelIndex(int index)
  {
    setProperty(Keys.LAST_USED_PREFERENCE_PANEL_INDEX, index);
  }

  /**
   * This method sets whether or not new transactions are to be marked as
   * pending.
   *
   * @param value true or false.
   */
  public
  static
  void
  setNewTransactionsArePending(boolean value)
  {
    setProperty(Keys.NEW_TRANSACTIONS_PENDING, value);
  }

  /**
   * This method sets whether or not a password is required.
   *
   * @param value true or false.
   */
  public
  static
  void
  setPasswordRequired(boolean value)
  {
    setProperty(Keys.PASSWORD_REQUIRED, value);
  }

  /**
   * This method sets the address used for the proxy.
   *
   * @param address The address used for the proxy.
   */
  public
  static
  void
  setProxyAddress(String address)
  {
    setProperty(Keys.PROXY_ADDRESS, address);
  }

  /**
   * This method sets the connection type used for the proxy.
   *
   * @param type The connection type used for the proxy.
   */
  public
  static
  void
  setProxyConnectionType(ProxyConnectionTypeKeys type)
  {
    setProperty(Keys.PROXY_CONNECTION_TYPE, type.toString());
  }

  /**
   * This method sets the port used for the proxy.
   *
   * @param port The port used for the proxy.
   */
  public
  static
  void
  setProxyPort(int port)
  {
    setProperty(Keys.PROXY_PORT, port);
  }

  /**
   * This method sets the type used for the proxy.
   *
   * @param type The type used for the proxy.
   */
  public
  static
  void
  setProxyType(Proxy.Type type)
  {
    setProperty(Keys.PROXY_TYPE, type.toString());
  }

  /**
   * This method sets the column to be sorted in the account register.
   *
   * @param column The column to be sorted in the account register.
   */
  public
  static
  void
  setRegisterColumnToSort(int column)
  {
    setProperty(Keys.REGISTER_COLUMN_TO_SORT, column);
  }

  /**
   * This method sets whether or not the program uses the default data file.
   *
   * @param value true or false.
   */
  public
  static
  void
  setUseDefaultDataFile(boolean value)
  {
    setProperty(Keys.USE_DEFAULT_DATA_FILE, value);
  }

  /**
   * This method sets whether or not to use the balance specified in a file to
   * import.
   *
   * @param value true or false.
   */
  public
  static
  void
  setUseImportBalance(boolean value)
  {
    setProperty(Keys.USE_IMPORT_BALANCE, value);
  }

  /**
   * This method sets whether or not a balance column should be displayed in the
   * register.
   *
   * @param value true or false.
   */
  public
  static
  void
  setViewBalanceColumn(boolean value)
  {
    setProperty(Keys.VIEW_BALANCE_COLUMN, value);
  }

  /**
   * This method sets whether or not the register view should provide a month by
   * month view.
   *
   * @param value true or false.
   */
  public
  static
  void
  setViewByMonth(boolean value)
  {
    setProperty(Keys.VIEW_BY_MONTH, value);
  }

  /**
   * This method returns true if the program uses the default data file,
   * otherwise false. The default value is true.
   *
   * @return true or false.
   */
  public
  static
  boolean
  useDefaultDataFile()
  {
    return parseBoolean(Keys.USE_DEFAULT_DATA_FILE, "true");
  }

  /**
   * This method returns true if the balance specified in a file to import file
   * should be used, otherwise false. The default value is false.
   *
   * @return true or false.
   */
  public
  static
  boolean
  useImportBalance()
  {
    return parseBoolean(Keys.USE_IMPORT_BALANCE, "false");
  }

  /**
   * This method returns true if a balance column should be displayed in the
   * register, otherwise false. The default value is false.
   *
   * @return true or false.
   */
  public
  static
  boolean
  viewBalanceColumn()
  {
    return parseBoolean(Keys.VIEW_BALANCE_COLUMN, "false");
  }

  /**
   * This method returns true if the register view should provide a month by
   * month view, otherwise false. The default value is false.
   *
   * @return true or false.
   */
  public
  static
  boolean
  viewByMonth()
  {
    return parseBoolean(Keys.VIEW_BY_MONTH, "false");
  }

  /**
   * This method writes the properties to file. This method returns true if no
   * error occurs during the write, otherwise false.
   *
   * @return true or false.
   */
  public
  static
  boolean
  write()
  {
    boolean result = false;

    try
    {
      FileOutputStream stream = new FileOutputStream(getFileMap().get(PREFERENCES));

      getProperties().storeToXML(stream, ApplicationProperties.class.getSimpleName());

      stream.flush();
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
  // Start of private methods.
  //////////////////////////////////////////////////////////////////////////////

  private
  static
  boolean
  parseBoolean(Keys key, String defaultValue)
  {
    return Boolean.parseBoolean(getProperty(key, defaultValue));
  }

  private
  static
  int
  parseInteger(Keys key, int defaultValue)
  {
    int value = defaultValue;

    try
    {
      value = Integer.parseInt(getProperty(key, "" + value));
    }
    catch(Exception exception)
    {
      // Ignored.
    }

    return value;
  }

  private
  static
  Properties
  getProperties()
  {
    return itsProperties;
  }

  private
  static
  String
  getProperty(Keys key, String defaultValue)
  {
    return getProperties().getProperty(key.name(), defaultValue);
  }

  private
  static
  void
  setProperty(Keys key, boolean value)
  {
    setProperty(key, new Boolean(value).toString());
  }

  private
  static
  void
  setProperty(Keys key, int value)
  {
    setProperty(key, new Integer(value).toString());
  }

  private
  static
  void
  setProperty(Keys key, String value)
  {
    getProperties().setProperty(key.name(), value);
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of inner classes.
  //////////////////////////////////////////////////////////////////////////////

  private
  enum
  Keys
  {
    ACCOUNT_STATEMENT_INCLUDE_CATEGORIES,
    ACCOUNT_STATEMENT_INCLUDE_CHECK,
    ACCOUNT_STATEMENT_INCLUDE_NOTES,
    ACCOUNT_STATEMENT_INCLUDE_RECONCILED,
    AUTO_BACKUP,
    BACKUP_FILE,
    CATEGORY_REPORT_SORT_BY,
    CREDIT_BALANCE_IS_POSITIVE,
    CSV_COLUMN_ORDER,
    CSV_EXPORT_CATEGORY,
    CURRENCY_FORMAT,
    CURRENCY_SYMBOL,
    DATA_FILE,
    DATE_FORMAT,
    DATE_WEEKDAY,
    FRAME_BOUNDS_H,
    FRAME_BOUNDS_W,
    FRAME_BOUNDS_X,
    FRAME_BOUNDS_Y,
    FRAME_STATE,
    IMPORT_EXPORT_CURRENCY_FORMAT,
    IMPORT_EXPORT_DATE_FORMAT,
    INVERT_SORT_FOR_REGISTER,
    LAST_RUNTIME,
    LAST_SELECTED_DIR,
    LAST_USED_PREFERENCE_PANEL_INDEX,
    NEW_TRANSACTIONS_PENDING,
    PASSWORD_REQUIRED,
    PROXY_ADDRESS,
    PROXY_CONNECTION_TYPE,
    PROXY_PORT,
    PROXY_TYPE,
    REGISTER_COLUMN_TO_SORT,
    REPORT_INCLUDE_DETAILS,
    REPORT_INCLUDE_GROUPS,
    USE_DEFAULT_DATA_FILE,
    USE_IMPORT_BALANCE,
    VIEW_BALANCE_COLUMN,
    VIEW_BY_MONTH;
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  private static Properties itsProperties = new Properties();

  /**
   * A constant to improve performance rather than obtaining the value via the
   * properties map since it is used frequently.
   */
  public static DateFormatKeys UI_DATE_FORMAT;
  /**
   * A constant to improve performance rather than obtaining the value via the
   * properties map since it is used frequently.
   */
  public static Currency UI_CURRENCY;
  /**
   * A constant to improve performance rather than obtaining the value via the
   * properties map since it is used frequently.
   */
  public static CurrencySymbolKeys UI_CURRENCY_SYMBOL;
}
