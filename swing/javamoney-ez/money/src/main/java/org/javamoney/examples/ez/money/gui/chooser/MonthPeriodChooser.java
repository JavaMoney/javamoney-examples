// MonthPeriodChooser

package org.javamoney.examples.ez.money.gui.chooser;

import static org.javamoney.examples.ez.common.utility.ButtonHelper.buildButton;
import static org.javamoney.examples.ez.common.utility.DateHelper.getEndOfMonth;
import static org.javamoney.examples.ez.common.utility.DateHelper.getStartOfMonth;
import static org.javamoney.examples.ez.common.utility.I18NHelper.getSharedProperty;

import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import org.javamoney.examples.ez.common.gui.CheckBox;
import org.javamoney.examples.ez.common.gui.Panel;
import org.javamoney.examples.ez.common.utility.ActionSignaler;
import org.javamoney.examples.ez.common.utility.I18NHelper;

/**
 * This class facilitates choosing a month or all months in any year.
 */
public
final
class
MonthPeriodChooser
extends Panel
{
  /**
   * Constructs a new chooser.
   */
  public
  MonthPeriodChooser()
  {
    setActionSignaler(new ActionSignaler());
    setCheckBox(new CheckBox());
    setMonthChooser(new MonthChooser());
    setYearChooser(new YearChooser());

    // Build panel.
    buildPanel();
  }

  /**
   * This method adds the action listener to the chooser.
   *
   * @param listener The action listener to add.
   */
  public
  void
  addActionListener(ActionListener listener)
  {
    getActionSignaler().addListener(listener);
  }

  /**
   * This method returns the chooser's end date.
   *
   * @return The end date.
   */
  public
  Date
  getEndDate()
  {
    int month = getMonthChooser().getSelectedMonth();

    if(getCheckBox().isSelected() == true)
    {
      month = 11;
    }

    return getEndOfMonth(month, getYearChooser().getSelectedYear());
  }

  /**
   * This method returns the chooser's start date.
   *
   * @return The start date.
   */
  public
  Date
  getStartDate()
  {
    int month = getMonthChooser().getSelectedMonth();

    if(getCheckBox().isSelected() == true)
    {
      month = 0;
    }

    return getStartOfMonth(month, getYearChooser().getSelectedYear());
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of private methods.
  //////////////////////////////////////////////////////////////////////////////

  private
  void
  buildPanel()
  {
    ActionHandler handler = new ActionHandler();

    // Build check box.
    buildButton(getCheckBox(), getSharedProperty("all"), handler);

    // Build panel.
    setAnchor(GridBagConstraints.EAST);
    add(getProperty("view") + ": ", 0, 0, 1, 1, 100, 100);

    setFill(GridBagConstraints.HORIZONTAL);
    add(getMonthChooser(), 1, 0, 1, 1, 0, 0);

    setAnchor(GridBagConstraints.WEST);
    add(getCheckBox(), 2, 0, 1, 1, 0, 0);
    addEmptyCellAt(3, 0, 4);
    add(getYearChooser(), 4, 0, 1, 1, 0, 0);
    addEmptyCellAt(5, 0, 6);

    // Add listeners.
    getMonthChooser().addActionListener(handler);
    getYearChooser().addActionListener(handler);
  }

  private
  ActionSignaler
  getActionSignaler()
  {
    return itsActionSignaler;
  }

  private
  CheckBox
  getCheckBox()
  {
    return itsCheckBox;
  }

  private
  MonthChooser
  getMonthChooser()
  {
    return itsMonthChooser;
  }

  private
  static
  String
  getProperty(String key)
  {
    return I18NHelper.getProperty("MonthPeriodChooser." + key);
  }

  private
  YearChooser
  getYearChooser()
  {
    return itsYearChooser;
  }

  private
  void
  sendSignal()
  {
    getActionSignaler().sendSignal(this, getClass().getSimpleName());
  }

  private
  void
  setActionSignaler(ActionSignaler signaler)
  {
    itsActionSignaler = signaler;
  }

  private
  void
  setCheckBox(CheckBox checkbox)
  {
    itsCheckBox = checkbox;
  }

  private
  void
  setMonthChooser(MonthChooser chooser)
  {
    itsMonthChooser = chooser;
  }

  private
  void
  setYearChooser(YearChooser chooser)
  {
    itsYearChooser = chooser;
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
      // If "All" is selected, then disable the month chooser.
      getMonthChooser().setEnabled(getCheckBox().isSelected() == false);

      sendSignal();
    }
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  private ActionSignaler itsActionSignaler;
  private CheckBox itsCheckBox;
  private MonthChooser itsMonthChooser;
  private YearChooser itsYearChooser;
}
