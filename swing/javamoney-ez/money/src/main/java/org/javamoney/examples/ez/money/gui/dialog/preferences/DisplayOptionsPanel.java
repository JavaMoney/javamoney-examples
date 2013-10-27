// DisplayOptionsPanel

package org.javamoney.examples.ez.money.gui.dialog.preferences;

import static org.javamoney.examples.ez.common.utility.BorderHelper.createTitledBorder;
import static org.javamoney.examples.ez.common.utility.ButtonHelper.buildButton;
import static org.javamoney.examples.ez.common.utility.I18NHelper.getSharedProperty;
import static org.javamoney.examples.ez.money.ApplicationProperties.UI_CURRENCY_SYMBOL;
import static org.javamoney.examples.ez.money.ApplicationProperties.UI_DATE_FORMAT;
import static org.javamoney.examples.ez.money.ApplicationProperties.creditBalanceIsPositive;
import static org.javamoney.examples.ez.money.ApplicationProperties.getCurrencyFormat;
import static org.javamoney.examples.ez.money.ApplicationProperties.getDateWeekday;
import static org.javamoney.examples.ez.money.ApplicationProperties.setCreditBalanceIsPositive;
import static org.javamoney.examples.ez.money.ApplicationProperties.setCurrencyFormat;
import static org.javamoney.examples.ez.money.ApplicationProperties.setCurrencySymbol;
import static org.javamoney.examples.ez.money.ApplicationProperties.setDateFormat;
import static org.javamoney.examples.ez.money.ApplicationProperties.setDateWeekday;
import static org.javamoney.examples.ez.money.ApplicationProperties.setViewBalanceColumn;
import static org.javamoney.examples.ez.money.ApplicationProperties.setViewByMonth;
import static org.javamoney.examples.ez.money.ApplicationProperties.viewBalanceColumn;
import static org.javamoney.examples.ez.money.ApplicationProperties.viewByMonth;

import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormatSymbols;
import java.util.Calendar;

import org.javamoney.examples.ez.money.locale.CurrencyFormatKeys;
import org.javamoney.examples.ez.money.locale.CurrencySymbolKeys;
import org.javamoney.examples.ez.money.locale.DateFormatKeys;

import org.javamoney.examples.ez.common.gui.CheckBox;
import org.javamoney.examples.ez.common.gui.ComboBox;
import org.javamoney.examples.ez.common.gui.Panel;
import org.javamoney.examples.ez.common.utility.I18NHelper;

/**
 * This class facilitates managing locale options.
 */
final
class
DisplayOptionsPanel
extends Panel
{
  /**
   * Constructs a new preferences panel.
   */
  public
  DisplayOptionsPanel()
  {
    createCheckBoxes();
    createChoosers();

    buildPanel();
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of private methods.
  //////////////////////////////////////////////////////////////////////////////

  private
  void
  buildPanel()
  {
    // Build panel.
    setFill(GridBagConstraints.BOTH);
    add(createOptionsPanel(), 0, 0, 1, 1, 100, 100);
  }

  private
  void
  createCheckBoxes()
  {
    itsCheckBoxes = new CheckBox[3];

    for(int len = 0; len < getCheckBoxes().length; ++len)
    {
      getCheckBoxes()[len] = new CheckBox();
    }
  }

  private
  void
  createChoosers()
  {
    ActionHandler handler = new ActionHandler();

    itsChoosers = new ComboBox[4];

    getChoosers()[CURRENCY_FORMAT] = new ComboBox(CurrencyFormatKeys.values());
    getChoosers()[CURRENCY_SYMBOL] = new ComboBox(CurrencySymbolKeys.values());
    getChoosers()[DATE_FORMAT] = new ComboBox(DateFormatKeys.values());
    getChoosers()[DATE_WEEKDAY] = new ComboBox(getWeekdays());

    getChoosers()[CURRENCY_FORMAT].setSelectedItem(getCurrencyFormat());
    getChoosers()[CURRENCY_SYMBOL].setSelectedItem(UI_CURRENCY_SYMBOL);
    getChoosers()[DATE_FORMAT].setSelectedItem(UI_DATE_FORMAT);
    getChoosers()[DATE_WEEKDAY].setSelectedIndex(getDateWeekday() - 1);

    // Add listeners.
    for(ComboBox comboBox : getChoosers())
    {
      comboBox.addActionListener(handler);
    }
  }

  private
  Panel
  createCurrencyFormatPanel()
  {
    Panel panel = new Panel();

    // Build panel.
    panel.setAnchor(GridBagConstraints.EAST);
    panel.add(getSharedProperty("currency_format") + ": ", 0, 0, 1, 1, 0, 100);

    panel.setAnchor(GridBagConstraints.WEST);
    panel.add(getChoosers()[CURRENCY_FORMAT], 1, 0, 1, 1, 100, 0);

    return panel;
  }

  private
  Panel
  createCurrencySymbolPanel()
  {
    Panel panel = new Panel();

    // Build panel.
    panel.setAnchor(GridBagConstraints.EAST);
    panel.add(getProperty("currency_symbol") + ": ", 0, 0, 1, 1, 0, 100);

    panel.setAnchor(GridBagConstraints.WEST);
    panel.add(getChoosers()[CURRENCY_SYMBOL], 1, 0, 1, 1, 100, 0);

    return panel;
  }

  private
  Panel
  createDateFormatPanel()
  {
    Panel panel = new Panel();

    // Build panel.
    panel.setAnchor(GridBagConstraints.EAST);
    panel.add(getSharedProperty("date_format") + ": ", 0, 0, 1, 1, 0, 100);

    panel.setAnchor(GridBagConstraints.WEST);
    panel.add(getChoosers()[DATE_FORMAT], 1, 0, 1, 1, 100, 0);

    return panel;
  }

  private
  Panel
  createDateWeekdayPanel()
  {
    Panel panel = new Panel();

    // Build panel.
    panel.setAnchor(GridBagConstraints.EAST);
    panel.add(getProperty("date_weekday") + ": ", 0, 0, 1, 1, 0, 100);

    panel.setAnchor(GridBagConstraints.WEST);
    panel.add(getChoosers()[DATE_WEEKDAY], 1, 0, 1, 1, 100, 0);

    return panel;
  }

  private
  Panel
  createOptionsPanel()
  {
    Panel panel = new Panel();
    ActionHandler handler = new ActionHandler();

    // Build check boxes.
    buildButton(getCheckBoxes()[CREDIT_BALANCES], getProperty("credit_balances"),
        handler, creditBalanceIsPositive());

    buildButton(getCheckBoxes()[BALANCE_COLUMN], getProperty("balance_column"),
        handler, viewBalanceColumn());

    buildButton(getCheckBoxes()[MONTH_VIEW], getProperty("month_view"),
        handler, viewByMonth());

    // Build panel.
    panel.setFill(GridBagConstraints.BOTH);
    panel.add(createCurrencyFormatPanel(), 0, 0, 1, 1, 50, 25);
    panel.add(createCurrencySymbolPanel(), 0, 1, 1, 1, 0, 25);
    panel.add(createDateFormatPanel(), 0, 2, 1, 1, 0, 25);
    panel.add(createDateWeekdayPanel(), 0, 3, 1, 1, 0, 25);
    panel.add(getCheckBoxes()[BALANCE_COLUMN], 1, 0, 1, 1, 50, 0);
    panel.add(getCheckBoxes()[MONTH_VIEW], 1, 1, 1, 1, 0, 0);
    panel.add(getCheckBoxes()[CREDIT_BALANCES], 1, 2, 1, 1, 0, 0);

    panel.setBorder(createTitledBorder(getProperty("title"), false));

    return panel;
  }

  private
  CheckBox[]
  getCheckBoxes()
  {
    return itsCheckBoxes;
  }

  private
  ComboBox[]
  getChoosers()
  {
    return itsChoosers;
  }

  private
  static
  String
  getProperty(String key)
  {
    return I18NHelper.getProperty("DisplayOptionsPanel." + key);
  }

  private
  static
  String[]
  getWeekdays()
  {
    String[] days = new DateFormatSymbols().getWeekdays();
    String[] weekdays = new String[2];

    weekdays[0] = days[Calendar.SUNDAY];
    weekdays[1] = days[Calendar.MONDAY];

    return weekdays;
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of inner classes.
  //////////////////////////////////////////////////////////////////////////////

  private
  class
  ActionHandler
  implements ActionListener
  {
    public
    void
    actionPerformed(ActionEvent event)
    {
      Object key = null;
      Object source = event.getSource();

      if(source == getCheckBoxes()[CREDIT_BALANCES])
      {
        setCreditBalanceIsPositive(getCheckBoxes()[CREDIT_BALANCES].isSelected());
      }
      else if(source == getCheckBoxes()[BALANCE_COLUMN])
      {
        setViewBalanceColumn(getCheckBoxes()[BALANCE_COLUMN].isSelected());
      }
      else if(source == getCheckBoxes()[MONTH_VIEW])
      {
        setViewByMonth(getCheckBoxes()[MONTH_VIEW].isSelected());
      }
      else if(source == getChoosers()[CURRENCY_FORMAT])
      {
        key = getChoosers()[CURRENCY_FORMAT].getSelectedItem();
        setCurrencyFormat((CurrencyFormatKeys)key);
      }
      else if(source == getChoosers()[CURRENCY_SYMBOL])
      {
        key = getChoosers()[CURRENCY_SYMBOL].getSelectedItem();
        setCurrencySymbol((CurrencySymbolKeys)key);
      }
      else if(source == getChoosers()[DATE_FORMAT])
      {
        key = getChoosers()[DATE_FORMAT].getSelectedItem();
        setDateFormat((DateFormatKeys)key);
      }
      else
      {
        setDateWeekday(getChoosers()[DATE_WEEKDAY].getSelectedIndex() + 1);
      }
    }
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  private CheckBox[] itsCheckBoxes;
  private ComboBox[] itsChoosers;

  private static final int BALANCE_COLUMN = 0;
  private static final int CREDIT_BALANCES = 1;
  private static final int MONTH_VIEW = 2;

  private static final int CURRENCY_FORMAT = 0;
  private static final int CURRENCY_SYMBOL = 1;
  private static final int DATE_FORMAT = 2;
  private static final int DATE_WEEKDAY = 3;
}
