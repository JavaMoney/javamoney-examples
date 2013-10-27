// BudgetReportDialog

package org.javamoney.examples.ez.money.gui.dialog;

import static org.javamoney.examples.ez.common.utility.BorderHelper.createTitledBorder;
import static org.javamoney.examples.ez.common.utility.ButtonHelper.buildButton;
import static org.javamoney.examples.ez.common.utility.I18NHelper.getSharedProperty;
import static org.javamoney.examples.ez.money.ApplicationProperties.includeDetailsInReport;
import static org.javamoney.examples.ez.money.ApplicationProperties.setIncludeDetailsInReport;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.javamoney.examples.ez.money.gui.chooser.CategoryTotalsFilterChooser;
import org.javamoney.examples.ez.money.gui.chooser.MonthPeriodChooser;
import org.javamoney.examples.ez.money.report.BudgetReport;
import org.javamoney.examples.ez.money.report.BudgetReportWriter;

import org.javamoney.examples.ez.common.gui.CheckBox;
import org.javamoney.examples.ez.common.gui.Panel;
import org.javamoney.examples.ez.common.utility.I18NHelper;

/**
 * This class provides a dialog for customizing a budget report.
 */
public
final
class
BudgetReportDialog
extends WebReportDialog
{
  /**
   * Constructs a new dialog for customizing a budget report.
   */
  public
  BudgetReportDialog()
  {
    super(600, 350);

    setFilterChooser(new CategoryTotalsFilterChooser());
    setMonthPeriodChooser(new MonthPeriodChooser());

    createCheckBoxes();

    buildPanel();
  }

  /**
   * This method shows a dialog for customizing a budget report.
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
        new ActionHandler()), 0, 3, 1, 1, 0, 0);
  }

  private
  void
  createCheckBoxes()
  {
    ActionHandler handler = new ActionHandler();

    itsCheckBoxes = new CheckBox[1];

    for(int len = 0; len < getCheckBoxes().length; ++len)
    {
      getCheckBoxes()[len] = new CheckBox();
    }

    buildButton(getCheckBoxes()[INCLUDE_DETAILS], getProperty("details"), handler);

    getCheckBoxes()[INCLUDE_DETAILS].setSelected(includeDetailsInReport());
  }

  private
  Panel
  createCustomizeReportPanel()
  {
    Panel panel = new Panel();

    // Build panel.
    panel.setFill(GridBagConstraints.BOTH);
    panel.add(createDateChooserPanel(), 0, 0, 1, 1, 100, 100);
    panel.addEmptyCellAt(0, 1);
    panel.add(createMoreOptionsPanel(), 0, 2, 1, 1, 0, 0);

    panel.setInsets(new Insets(20, 15, 5, 75));

    return panel;
  }

  private
  Panel
  createDateChooserPanel()
  {
    Panel panel = new Panel();

    // Build panel.
    panel.setAnchor(GridBagConstraints.NORTH);
    panel.add(getMonthPeriodChooser(), 0, 0, 1, 1, 100, 100);

    panel.setBorder(createTitledBorder(getSharedProperty("report_period"), false));

    return panel;
  }

  private
  Panel
  createMoreOptionsPanel()
  {
    Panel panel = new Panel();

    // Build panel.
    panel.setAnchor(GridBagConstraints.WEST);
    panel.add(getCheckBoxes()[INCLUDE_DETAILS], 0, 0, 1, 1, 50, 0);
    panel.add(getFilterChooser(), 1, 0, 1, 1, 50, 0);
    panel.addSpacer(0, 1, 1, 1, 0, 100);

    panel.setBorder(createTitledBorder(getSharedProperty("more_options"), false));

    return panel;
  }

  private
  BudgetReport
  createReport()
  {
    return BudgetReport.createReport(getMonthPeriodChooser().getStartDate(),
        getMonthPeriodChooser().getEndDate(), getFilterChooser().getFilter());
  }

  private
  CheckBox[]
  getCheckBoxes()
  {
    return itsCheckBoxes;
  }

  private
  CategoryTotalsFilterChooser
  getFilterChooser()
  {
    return itsFilterChooser;
  }

  private
  MonthPeriodChooser
  getMonthPeriodChooser()
  {
    return itsMonthPeriodChooser;
  }

  private
  static
  String
  getProperty(String key)
  {
    return I18NHelper.getProperty("BudgetReportDialog." + key);
  }

  private
  void
  setFilterChooser(CategoryTotalsFilterChooser chooser)
  {
    itsFilterChooser = chooser;
  }

  private
  void
  setMonthPeriodChooser(MonthPeriodChooser chooser)
  {
    itsMonthPeriodChooser = chooser;
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
      else
      {
        setAccepted(event.getActionCommand().equals(ACTION_OK));

        if(wasAccepted() == true)
        {
          BudgetReportWriter.generate(createReport());
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
  private CategoryTotalsFilterChooser itsFilterChooser;
  private MonthPeriodChooser itsMonthPeriodChooser;

  private static final int INCLUDE_DETAILS = 0;
}
