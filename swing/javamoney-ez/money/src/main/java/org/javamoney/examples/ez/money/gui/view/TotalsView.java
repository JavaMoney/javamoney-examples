// TotalsView

package org.javamoney.examples.ez.money.gui.view;

import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.javamoney.examples.ez.money.gui.chooser.CategoryTotalsFilterChooser;
import org.javamoney.examples.ez.money.gui.chooser.DatePeriodChooser;
import org.javamoney.examples.ez.money.gui.view.totals.TotalsPanel;
import org.javamoney.examples.ez.money.report.TotalReportTypeKeys;

import org.javamoney.examples.ez.common.gui.ComboBox;
import org.javamoney.examples.ez.common.gui.Panel;
import org.javamoney.examples.ez.common.utility.I18NHelper;

/**
 * This class facilitates viewing totals.
 */
public
final
class
TotalsView
extends View
{
  /**
   * Constructs a new view.
   */
  public
  TotalsView()
  {
    super(ViewKeys.TOTALS);

    setDatePeriodChooser(new DatePeriodChooser());
    setFilterChooser(new CategoryTotalsFilterChooser());
    setTotalsChooser(new ComboBox(TotalReportTypeKeys.values()));
    setTotalsPanel(new TotalsPanel());

    buildPanel();
  }

  /**
   * This method updates the view.
   */
  @Override
  public
  void
  updateView()
  {
    getDatePeriodChooser().updateView();

    getTotalsPanel().updateView(getDatePeriodChooser().getStartDate(),
        getDatePeriodChooser().getEndDate(), getFilterChooser().getFilter());
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of private methods.
  //////////////////////////////////////////////////////////////////////////////

  private
  void
  buildPanel()
  {
    ActionHandler handler = new ActionHandler();
    int height = 4;

    // Build panel.
    addEmptyRowsAt(0, 0, height);

    setFill(GridBagConstraints.BOTH);
    add(createToolsPanel(), 1, 0, 1, height, 100, 0);
    add(getFilterChooser(), 0, height, 2, 1, 0, 0);
    add(getTotalsPanel(), 0, height + 1, 2, 1, 0, 100);

    // Add listeners.
    getDatePeriodChooser().addActionListener(handler);
    getFilterChooser().addActionListener(handler);
    getTotalsChooser().addActionListener(handler);
  }

  private
  Panel
  createChooserPanel()
  {
    Panel panel = new Panel();

    // Build panel.
    panel.setAnchor(GridBagConstraints.EAST);
    panel.add(getProperty("show") + ": ", 0, 0, 1, 1, 0, 0);

    panel.setFill(GridBagConstraints.HORIZONTAL);
    panel.add(getTotalsChooser(), 1, 0, 1, 1, 0, 0);

    // This spacer is to ensure adequate width for the panel chooser.
    panel.addEmptyCellAt(1, 1, 20);

    return panel;
  }

  private
  Panel
  createToolsPanel()
  {
    Panel panel = new Panel();

    // Build panel.
    panel.setFill(GridBagConstraints.BOTH);
    panel.addEmptyCellAt(0, 0);
    panel.add(createChooserPanel(), 0, 1, 1, 1, 0, 100);
    panel.add(getDatePeriodChooser(), 1, 1, 1, 1, 100, 0);

    return panel;
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
  static
  String
  getProperty(String key)
  {
    return I18NHelper.getProperty("TotalsView." + key);
  }

  private
  ComboBox
  getTotalsChooser()
  {
    return itsTotalsChooser;
  }

  private
  TotalsPanel
  getTotalsPanel()
  {
    return itsTotalsPanel;
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
  setTotalsChooser(ComboBox chooser)
  {
    itsTotalsChooser = chooser;
  }

  private
  void
  setTotalsPanel(TotalsPanel panel)
  {
    itsTotalsPanel = panel;
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
      if(event.getSource() == getTotalsChooser())
      {
        TotalReportTypeKeys key = (TotalReportTypeKeys)getTotalsChooser().getSelectedItem();

        getTotalsPanel().showTotalsFor(key);
      }
      else
      {
        updateView();
      }
    }
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  private DatePeriodChooser itsDatePeriodChooser;
  private CategoryTotalsFilterChooser itsFilterChooser;
  private ComboBox itsTotalsChooser;
  private TotalsPanel itsTotalsPanel;
}
