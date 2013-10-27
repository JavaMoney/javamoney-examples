// BudgetsView

package org.javamoney.examples.ez.money.gui.view;

import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.javamoney.examples.ez.money.gui.chooser.CategoryTotalsFilterChooser;
import org.javamoney.examples.ez.money.gui.chooser.MonthPeriodChooser;
import org.javamoney.examples.ez.money.gui.view.totals.BudgetPanel;

/**
 * This class facilitates viewing budgets.
 */
public
final
class
BudgetsView
extends View
{
  /**
   * Constructs a new view.
   */
  public
  BudgetsView()
  {
    super(ViewKeys.BUDGETS);

    setBudgetTotalPanel(new BudgetPanel());
    setFilterChooser(new CategoryTotalsFilterChooser());
    setMonthPeriodChooser(new MonthPeriodChooser());

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
    getBudgetTotalPanel().updateView(getMonthPeriodChooser().getStartDate(),
        getMonthPeriodChooser().getEndDate(), getFilterChooser().getFilter());
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
    add(getMonthPeriodChooser(), 1, 0, 1, height, 100, 0);
    add(getFilterChooser(), 0, height, 2, 1, 0, 0);
    add(getBudgetTotalPanel(), 0, height + 1, 2, 1, 100, 100);

    // Add listeners.
    getFilterChooser().addActionListener(handler);
    getMonthPeriodChooser().addActionListener(handler);
  }

  private
  BudgetPanel
  getBudgetTotalPanel()
  {
    return itsBudgetTotalPanel;
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
  void
  setBudgetTotalPanel(BudgetPanel panel)
  {
    itsBudgetTotalPanel = panel;
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
      updateView();
    }
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  private BudgetPanel itsBudgetTotalPanel;
  private CategoryTotalsFilterChooser itsFilterChooser;
  private MonthPeriodChooser itsMonthPeriodChooser;
}
