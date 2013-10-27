// CategoryReportDialog

package org.javamoney.examples.ez.money.gui.dialog;

import static org.javamoney.examples.ez.common.utility.BorderHelper.createTitledBorder;
import static org.javamoney.examples.ez.common.utility.ButtonHelper.buildButton;
import static org.javamoney.examples.ez.common.utility.I18NHelper.getSharedProperty;
import static org.javamoney.examples.ez.money.ApplicationProperties.getCategoryReportSortByField;
import static org.javamoney.examples.ez.money.ApplicationProperties.includeDetailsInReport;
import static org.javamoney.examples.ez.money.ApplicationProperties.includeGroupsInReport;
import static org.javamoney.examples.ez.money.ApplicationProperties.setCategoryReportSortByField;
import static org.javamoney.examples.ez.money.ApplicationProperties.setIncludeDetailsInReport;
import static org.javamoney.examples.ez.money.ApplicationProperties.setIncludeGroupsInReport;
import static org.javamoney.examples.ez.money.report.TotalReportTypeKeys.EXPENSES;
import static org.javamoney.examples.ez.money.report.TotalReportTypeKeys.INCOME;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;

import org.javamoney.examples.ez.money.gui.chooser.CategoryTotalsFilterChooser;
import org.javamoney.examples.ez.money.gui.chooser.DatePeriodChooser;
import org.javamoney.examples.ez.money.locale.PercentFormat;
import org.javamoney.examples.ez.money.report.CategoryReport;
import org.javamoney.examples.ez.money.report.CategoryReportSortByKeys;
import org.javamoney.examples.ez.money.report.CategoryReportWriter;
import org.javamoney.examples.ez.money.report.TotalReportTypeKeys;

import org.javamoney.examples.ez.common.gui.CheckBox;
import org.javamoney.examples.ez.common.gui.ComboBox;
import org.javamoney.examples.ez.common.gui.Panel;
import org.javamoney.examples.ez.common.gui.RadioButton;
import org.javamoney.examples.ez.common.utility.I18NHelper;

/**
 * This class provides a dialog for customizing a category report.
 */
public
final
class
CategoryReportDialog
extends WebReportDialog
{
  /**
   * Constructs a new dialog for customizing a category report.
   */
  public
  CategoryReportDialog()
  {
    super(625, 425);

    setDatePeriodChooser(new DatePeriodChooser());
    setFilterChooser(new CategoryTotalsFilterChooser());
    setTypeChooser(new ComboBox());

    createCheckBoxes();
    createRadioButtons();

    buildPanel();
  }

  /**
   * This method shows a dialog for customizing a category report.
   */
  public
  void
  showDialog()
  {
    runDialog();
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of private methods.
  //////////////////////////////////////////////////////////////////////////////

  private
  void
  buildPanel()
  {
    Panel panel = getContentPane();

    // Build panel.
    panel.setFill(GridBagConstraints.BOTH);
    panel.add(createDialogHeader(), 0, 0, 1, 1, 0, 0);
    panel.add(createCustomizeReportPanel(), 0, 1, 1, 1, 100, 100);

    panel.add(createOKCancelButtonPanel(getSharedProperty("create"), getSharedProperty("close"),
        new ActionHandler()), 0, 2, 1, 1, 0, 0);
  }

  private
  void
  createCheckBoxes()
  {
    ActionHandler handler = new ActionHandler();

    itsCheckBoxes = new CheckBox[2];

    for(int len = 0; len < getCheckBoxes().length; ++len)
    {
      getCheckBoxes()[len] = new CheckBox();
    }

    buildButton(getCheckBoxes()[INCLUDE_DETAILS], getProperty("details"), handler);
    buildButton(getCheckBoxes()[INCLUDE_GROUPS], getProperty("groups"), handler);

    getCheckBoxes()[INCLUDE_DETAILS].setSelected(includeDetailsInReport());
    getCheckBoxes()[INCLUDE_GROUPS].setSelected(includeGroupsInReport());
  }

  private
  Panel
  createCustomizeReportPanel()
  {
    Panel panel = new Panel();

    // Build panel.
    panel.setFill(GridBagConstraints.BOTH);
    panel.add(createTypeChooserPanel(), 0, 0, 1, 1, 0, 0);
    panel.addEmptyCellAt(1, 0);
    panel.addEmptyCellAt(2, 0);

    panel.add(getDatePeriodChooser(), 0, 1, 1, 1, 50, 100);
    panel.add(createSortByPanel(), 3, 1, 1, 2, 50, 0);

    panel.addEmptyCellAt(0, 3);
    panel.add(createMoreOptionsPanel(), 0, 3, 4, 1, 0, 0);

    panel.setInsets(new Insets(10, 15, 5, 15));

    getDatePeriodChooser().setBorder(createTitledBorder(getSharedProperty("report_period"), false));

    return panel;
  }

  private
  Panel
  createMoreOptionsPanel()
  {
    Panel panel = new Panel();

    // Build panel.
    panel.setAnchor(GridBagConstraints.WEST);
    panel.add(getCheckBoxes()[INCLUDE_DETAILS], 0, 0, 1, 1,50, 50);
    panel.add(getCheckBoxes()[INCLUDE_GROUPS], 0, 1, 1, 1, 0, 50);
    panel.add(getFilterChooser(), 1, 0, 1, 1, 50, 0);

    panel.setBorder(createTitledBorder(getSharedProperty("more_options"), false));

    return panel;
  }

  private
  void
  createRadioButtons()
  {
    ActionHandler handler = new ActionHandler();
    ButtonGroup group = new ButtonGroup();

    itsRadioButtons = new RadioButton[4];

    for(int len = 0; len < getRadioButtons().length; ++len)
    {
      getRadioButtons()[len] = new RadioButton();
    }

    buildButton(getRadioButtons()[AMOUNT], getSharedProperty("amount"), handler, group);
    buildButton(getRadioButtons()[CATEGORY], getSharedProperty("category"), handler, group);
    buildButton(getRadioButtons()[GROUP], getSharedProperty("group"), handler, group);
    buildButton(getRadioButtons()[PERCENT], PercentFormat.SYMBOL, handler, group);

    getRadioButtons()[getCategoryReportSortByField().ordinal()].setSelected(true);
  }

  private
  CategoryReport
  createReport()
  {
    return CategoryReport.createReport(getDatePeriodChooser().getStartDate(),
        getDatePeriodChooser().getEndDate(), getFilterChooser().getFilter());
  }

  private
  Panel
  createSortByPanel()
  {
    Panel panel = new Panel();

    // Build panel.
    panel.setAnchor(GridBagConstraints.WEST);
    panel.add(getRadioButtons()[AMOUNT], 0, 0, 1, 1, 100, 25);
    panel.add(getRadioButtons()[CATEGORY], 0, 1, 1, 1, 0, 25);
    panel.add(getRadioButtons()[GROUP], 0, 2, 1, 1, 0, 25);
    panel.add(getRadioButtons()[PERCENT], 0, 3, 1, 1, 0, 25);

    panel.setBorder(createTitledBorder(getProperty("sort_by")));

    return panel;
  }

  private
  Panel
  createTypeChooserPanel()
  {
    Panel panel = new Panel();

    getTypeChooser().addItem(EXPENSES);
    getTypeChooser().addItem(INCOME);

    // Build panel.
    panel.setAnchor(GridBagConstraints.EAST);
    panel.addEmptyCellAt(0, 0);
    panel.add(getProperty("report_for") + ": ", 0, 1, 1, 1, 0, 100);

    panel.setAnchor(GridBagConstraints.WEST);
    panel.add(getTypeChooser(), 1, 1, 1, 1, 100, 0);
    panel.addEmptyRowsAt(0, 2, 2);

    return panel;
  }

  private
  void
  generateReport()
  {
    TotalReportTypeKeys type = null;

    if(getTypeChooser().getSelectedIndex() == 0)
    {
      type = EXPENSES;
    }
    else
    {
      type = INCOME;
    }

    CategoryReportWriter.generate(type, createReport());
  }

  private
  CheckBox[]
  getCheckBoxes()
  {
    return itsCheckBoxes;
  }

  private
  DatePeriodChooser
  getDatePeriodChooser()
  {
    return itsDatePeriodChooser;
  }

  private
  CategoryTotalsFilterChooser
  getFilterChooser()
  {
    return itsFilterChooser;
  }

  private
  RadioButton[]
  getRadioButtons()
  {
    return itsRadioButtons;
  }

  private
  static
  String
  getProperty(String key)
  {
    return I18NHelper.getProperty("CategoryReportDialog." + key);
  }

  private
  ComboBox
  getTypeChooser()
  {
    return itsTypeChooser;
  }

  private
  void
  setDatePeriodChooser(DatePeriodChooser chooser)
  {
    itsDatePeriodChooser = chooser;
  }

  private
  void
  setFilterChooser(CategoryTotalsFilterChooser chooser)
  {
    itsFilterChooser = chooser;
  }

  private
  void
  setTypeChooser(ComboBox comboBox)
  {
    itsTypeChooser = comboBox;
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

      if(source == getCheckBoxes()[INCLUDE_DETAILS])
      {
        setIncludeDetailsInReport(!includeDetailsInReport());
      }
      else if(source == getCheckBoxes()[INCLUDE_GROUPS])
      {
        setIncludeGroupsInReport(!includeGroupsInReport());
      }
      else if(source == getRadioButtons()[AMOUNT])
      {
        setCategoryReportSortByField(CategoryReportSortByKeys.AMOUNT);
      }
      else if(source == getRadioButtons()[CATEGORY])
      {
        setCategoryReportSortByField(CategoryReportSortByKeys.CATEGORY);
      }
      else if(source == getRadioButtons()[GROUP])
      {
        setCategoryReportSortByField(CategoryReportSortByKeys.GROUP);
      }
      else if(source == getRadioButtons()[PERCENT])
      {
        setCategoryReportSortByField(CategoryReportSortByKeys.PERCENT);
      }
      else
      {
        setAccepted(event.getActionCommand().equals(ACTION_OK));

        if(wasAccepted() == true)
        {
          generateReport();
        }
        else
        {
          dispose();
        }
      }
    }
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  private CheckBox[] itsCheckBoxes;
  private DatePeriodChooser itsDatePeriodChooser;
  private CategoryTotalsFilterChooser itsFilterChooser;
  private RadioButton[] itsRadioButtons;
  private ComboBox itsTypeChooser;

  private static final int INCLUDE_DETAILS = 0;
  private static final int INCLUDE_GROUPS = 1;

  private static final int AMOUNT = 0;
  private static final int CATEGORY = 1;
  private static final int GROUP = 2;
  private static final int PERCENT = 3;
}
