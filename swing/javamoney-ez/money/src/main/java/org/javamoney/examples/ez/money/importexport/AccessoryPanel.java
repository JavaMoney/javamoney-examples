// AccessoryPanel

package org.javamoney.examples.ez.money.importexport;

import static org.javamoney.examples.ez.common.utility.BorderHelper.createTitledBorder;
import static org.javamoney.examples.ez.common.utility.ButtonHelper.buildButton;
import static org.javamoney.examples.ez.common.utility.I18NHelper.getSharedProperty;
import static org.javamoney.examples.ez.money.ApplicationProperties.exportCategoryForCSV;
import static org.javamoney.examples.ez.money.ApplicationProperties.getImportExportCurrencyFormat;
import static org.javamoney.examples.ez.money.ApplicationProperties.getImportExportDateFormat;
import static org.javamoney.examples.ez.money.ApplicationProperties.setExportCategoryForCSV;
import static org.javamoney.examples.ez.money.ApplicationProperties.setImportExportCurrencyFormat;
import static org.javamoney.examples.ez.money.ApplicationProperties.setImportExportDateFormat;
import static org.javamoney.examples.ez.money.ApplicationProperties.setUseImportBalance;
import static org.javamoney.examples.ez.money.ApplicationProperties.useImportBalance;

import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.javamoney.examples.ez.money.locale.CurrencyFormatKeys;

import org.javamoney.examples.ez.common.gui.CheckBox;
import org.javamoney.examples.ez.common.gui.ComboBox;
import org.javamoney.examples.ez.common.gui.Panel;
import org.javamoney.examples.ez.common.utility.I18NHelper;

/**
 * This class facilitates providing extra options for importing and exporting.
 */
final
class
AccessoryPanel
extends Panel
{
  /**
   * Constructs a new accessory panel for the specified action type and format.
   *
   * @param type The action type.
   * @param format The format the file is in.
   */
  protected
  AccessoryPanel(ImportExportTypeKeys type, ImportExportFormatKeys format)
  {
    setCheckBox(new CheckBox());

    createComboBoxes();

    // Build check box.
    buildButton(getCheckBox(), getSharedProperty("include_categories"),
        new ActionHandler(), exportCategoryForCSV());

    buildPanel(type, format);
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of private methods.
  //////////////////////////////////////////////////////////////////////////////

  private
  void
  buildPanel(ImportExportTypeKeys type, ImportExportFormatKeys format)
  {
    // Build panel.
    setAnchor(GridBagConstraints.NORTHEAST);
    addEmptyCellAt(0, 0);

    if(format != ImportExportFormatKeys.OFX)
    {
      add(getSharedProperty("currency_format") + ": ", 0, 1, 1, 1, 0, 0);
      add(getSharedProperty("date_format") + ": ", 0, 2, 1, 1, 0, 0);

      setAnchor(GridBagConstraints.NORTHWEST);
      add(getComboBoxes()[CURRENCY], 1, 1, 1, 1, 100, 0);
      add(getComboBoxes()[DATE], 1, 2, 1, 1, 0, 0);
      addEmptyCellAt(0, 3);
    }

    if(format == ImportExportFormatKeys.CSV)
    {
      setFill(GridBagConstraints.BOTH);
      add(new CSVColumnPanel(), 0, 4, 2, 1, 0, 100);

      if(type == ImportExportTypeKeys.EXPORT)
      {
        add(getCheckBox(), 0, 5, 2, 1, 0, 0);
      }

      addEmptyCellAt(0, 6);
    }
    else if(type == ImportExportTypeKeys.IMPORT)
    {
      setAnchor(GridBagConstraints.NORTHWEST);
      add(createCheckBox(ACTION_USE_BALANCE, useImportBalance()), 0, 4, 2, 1, 0, 0);
      addSpacer(0, 5, 1, 1, 0, 100);
    }
    else
    {
      addSpacer(0, 4, 1, 1, 0, 100);
    }

    setBorder(createTitledBorder(getSharedProperty("more_options"), false));
  }

  private
  CheckBox
  createCheckBox(String text, boolean isSelected)
  {
    CheckBox checkbox = new CheckBox();

    // Build checkbox.
    buildButton(checkbox, text, new ActionHandler());

    checkbox.setSelected(isSelected);

    return checkbox;
  }

  private
  void
  createComboBoxes()
  {
    ActionHandler handler = new ActionHandler();

    itsComboBoxes = new ComboBox[2];

    getComboBoxes()[CURRENCY] = new ComboBox(CurrencyFormatKeys.values());
    getComboBoxes()[DATE] = new ComboBox(ImportExportDateFormatKeys.values());

    getComboBoxes()[CURRENCY].setSelectedItem(getImportExportCurrencyFormat());
    getComboBoxes()[DATE].setSelectedItem(getImportExportDateFormat());

    // Add listeners.
    getComboBoxes()[CURRENCY].addActionListener(handler);
    getComboBoxes()[DATE].addActionListener(handler);
  }

  private
  CheckBox
  getCheckBox()
  {
    return itsCheckBox;
  }

  private
  ComboBox[]
  getComboBoxes()
  {
    return itsComboBoxes;
  }

  private
  static
  String
  getProperty(String key)
  {
    return I18NHelper.getProperty("AccessoryPanel." + key);
  }

  private
  void
  setCheckBox(CheckBox checkBox)
  {
    itsCheckBox = checkBox;
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
      Object source = event.getSource();

      if(source == getCheckBox())
      {
        setExportCategoryForCSV(getCheckBox().isSelected());
      }
      else if(source == getComboBoxes()[CURRENCY])
      {
        CurrencyFormatKeys key = (CurrencyFormatKeys)getComboBoxes()[CURRENCY].getSelectedItem();

        setImportExportCurrencyFormat(key);
      }
      else if(source == getComboBoxes()[DATE])
      {
        ImportExportDateFormatKeys key = (ImportExportDateFormatKeys)getComboBoxes()[DATE].getSelectedItem();

        setImportExportDateFormat(key);
      }
      else
      {
        setUseImportBalance(!useImportBalance());
      }
    }
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  private CheckBox itsCheckBox;
  private ComboBox[] itsComboBoxes;

  private static final int CURRENCY = 0;
  private static final int DATE = 1;

  private static final String ACTION_USE_BALANCE = getProperty("balance");
}
