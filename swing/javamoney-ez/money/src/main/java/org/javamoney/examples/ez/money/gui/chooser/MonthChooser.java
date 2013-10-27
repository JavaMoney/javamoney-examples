// MonthChooser

package org.javamoney.examples.ez.money.gui.chooser;

import static org.javamoney.examples.ez.common.utility.I18NHelper.getProperty;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.JComboBox;

/**
 * This class facilitates choosing a month.
 */
public
final
class
MonthChooser
extends JComboBox
{
  /**
   * Constructs a new chooser with the current month initially selected.
   */
  public
  MonthChooser()
  {
    this(new GregorianCalendar().get(Calendar.MONTH));
  }

  /**
   * Constructs a new chooser with the specified month initially selected.
   *
   * @param month The month to initially select.
   */
  public
  MonthChooser(int month)
  {
    String[] months = new DateFormatSymbols().getMonths();

    for(String monthName : months)
    {
      // There is a month with no displayable text that is not to be added.
      if(monthName.length() != 0)
      {
        addItem(monthName);
      }
    }

    setSelectedMonth(month);
    setToolTipText(getProperty("MonthChooser.tip"));
  }

  /**
   * This method returns the selected month.
   *
   * @return The selected month.
   */
  public
  int
  getSelectedMonth()
  {
    return getSelectedIndex();
  }

  /**
   * This method sets the selected month.
   *
   * @param month The month to select.
   */
  public
  void
  setSelectedMonth(int month)
  {
    setSelectedIndex(month);
  }
}
