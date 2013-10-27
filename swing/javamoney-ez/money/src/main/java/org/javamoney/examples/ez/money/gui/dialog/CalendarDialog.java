// CalendarDialog

package org.javamoney.examples.ez.money.gui.dialog;

import static java.text.DateFormat.LONG;
import static java.text.DateFormat.getDateInstance;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import org.javamoney.examples.ez.money.gui.chooser.DayChooser;
import org.javamoney.examples.ez.money.gui.chooser.MonthChooser;
import org.javamoney.examples.ez.money.gui.chooser.YearChooser;

import org.javamoney.examples.ez.common.gui.Link;
import org.javamoney.examples.ez.common.gui.Panel;
import org.javamoney.examples.ez.common.utility.I18NHelper;

/**
 * This class facilitates choosing a calendar date from a dialog that has
 * selectable days, months, and years.
 */
public
final
class
CalendarDialog
extends ApplicationDialog
{
  /**
   * Constructs a new dialog initialized with the specified date.
   *
   * @param calendar The date to initialize the dialog.
   * @param firstDayOfWeek The weekday the day chooser should start with.
   */
  public
  CalendarDialog(GregorianCalendar calendar, int firstDayOfWeek)
  {
    super(getProperty("title"), 325, 375);

    setCalendar(calendar);
    setDateLabel(new JLabel());
    setDayChooser(new DayChooser(getCalendar(), firstDayOfWeek));
    setMonthChooser(new MonthChooser(getCalendar().get(Calendar.MONTH)));
    setYearChooser(new YearChooser(getCalendar().get(Calendar.YEAR)));

    buildPanel();
  }

  /**
   * This method runs the dialog and then returns the selected calendar date, or
   * null if the dialog was canceled.
   *
   * @return The selected calendar date, or null if the dialog was canceled.
   */
  public
  GregorianCalendar
  showDialog()
  {
    GregorianCalendar calendar = null;

    runDialog();

    if(wasAccepted() == true)
    {
      calendar = getCalendar();
    }

    return calendar;
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of private methods.
  //////////////////////////////////////////////////////////////////////////////

  private
  void
  buildPanel()
  {
    ActionHandler handler = new ActionHandler();
    Panel panel = getContentPane();

    // Build panel.
    panel.setInsets(new Insets(10, 10, 10, 10));
    panel.setFill(GridBagConstraints.BOTH);
    panel.add(createDateLabelPanel(), 0, 0, 2, 1, 0, 0);
    panel.add(createChooserPanel(), 0, 1, 2, 1, 100, 100);
    panel.add(createTodayLinkPanel(), 0, 2, 1, 1, 0, 0);
    panel.add(createOKCancelButtonPanel(handler), 1, 2, 1, 1, 0, 0);

    getDateLabel().setText(getDateFormat());

    // Add listeners.
    getDayChooser().addActionListener(handler);
    getMonthChooser().addActionListener(handler);
    getYearChooser().addActionListener(handler);
  }

  private
  Panel
  createChooserPanel()
  {
    Panel panel = new Panel();

    // Build panel.
    panel.setFill(GridBagConstraints.BOTH);
    panel.addEmptyCellAt(0, 0);
    panel.add(createMonthYearChooserPanel(), 0, 1, 1, 1, 0, 0);
    panel.addEmptyCellAt(0, 2);
    panel.add(getDayChooser(), 0, 3, 1, 1, 100, 100);

    panel.setBorder(BorderFactory.createEtchedBorder());

    return panel;
  }

  private
  Panel
  createDateLabelPanel()
  {
    Panel panel = new Panel();

    // Build panel.
    panel.setAnchor(GridBagConstraints.WEST);
    panel.add(getDateLabel(), 0, 0, 1, 1, 100, 100);

    return panel;
  }

  private
  Panel
  createMonthYearChooserPanel()
  {
    Panel panel = new Panel();

    // Build panel.
    panel.setFill(GridBagConstraints.HORIZONTAL);
    panel.add(getMonthChooser(), 0, 0, 1, 1, 50, 100);
    panel.add(getYearChooser(), 1, 0, 1, 1, 50, 0);

    return panel;
  }

  private
  Panel
  createTodayLinkPanel()
  {
    Panel panel = new Panel();
    Link link = new Link(ACTION_TODAY, new ActionHandler());

    // Build panel.
    panel.add(link, 0, 0, 1, 1, 100, 100);

    link.setToolTipText(getDateFormat());

    return panel;
  }

  private
  GregorianCalendar
  getCalendar()
  {
    return itsCalendar;
  }

  private
  String
  getDateFormat()
  {
    return getDateInstance(LONG).format(getCalendar().getTime());
  }

  private
  JLabel
  getDateLabel()
  {
    return itsDateLabel;
  }

  private
  DayChooser
  getDayChooser()
  {
    return itsDayChooser;
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
    return I18NHelper.getProperty("CalendarDialog." + key);
  }

  private
  YearChooser
  getYearChooser()
  {
    return itsYearChooser;
  }

  private
  void
  setCalendar(GregorianCalendar calendar)
  {
    itsCalendar = calendar;
  }

  private
  void
  selectToday()
  {
    GregorianCalendar calendar = new GregorianCalendar();

    // Setting a selected value invokes an action event. Unfortunately in this
    // case the days will be rendered three times.
    getDayChooser().setSelectedDay(calendar.get(Calendar.DATE));
    getMonthChooser().setSelectedMonth(calendar.get(Calendar.MONTH));
    getYearChooser().setSelectedYear(calendar.get(Calendar.YEAR));
  }

  private
  void
  setDateLabel(JLabel label)
  {
    itsDateLabel = label;
  }

  private
  void
  setDayChooser(DayChooser chooser)
  {
    itsDayChooser = chooser;
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

  private
  void
  updateCalendar()
  {
    getCalendar().set(Calendar.DATE, getDayChooser().getSelectedDay());
    getCalendar().set(Calendar.MONTH, getMonthChooser().getSelectedMonth());
    getCalendar().set(Calendar.YEAR, getYearChooser().getSelectedYear());

    getDateLabel().setText(getDateFormat());
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
      String command = event.getActionCommand();

      if(command.equals(ACTION_OK) == true || command.equals(ACTION_CANCEL) == true)
      {
        setAccepted(command.equals(ACTION_OK));
        dispose();
      }
      else if(command.equals(ACTION_TODAY) == true)
      {
        selectToday();
      }
      else if(command.equals(DayChooser.ACTION_DAY_CHOSEN) == true)
      {
        setAccepted(true);
        dispose();
      }
      else
      {
        if(event.getSource() == getMonthChooser())
        {
          getDayChooser().setMonth(getMonthChooser().getSelectedMonth());
        }
        else if(event.getSource() == getYearChooser())
        {
          getDayChooser().setYear(getYearChooser().getSelectedYear());
        }

        updateCalendar();
      }
    }
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  private GregorianCalendar itsCalendar;
  private JLabel itsDateLabel;
  private DayChooser itsDayChooser;
  private MonthChooser itsMonthChooser;
  private YearChooser itsYearChooser;

  private static final String ACTION_TODAY = getProperty("today");
}
