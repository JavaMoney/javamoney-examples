// DayChooser

package org.javamoney.examples.ez.money.gui.chooser;

import static java.awt.event.MouseEvent.BUTTON1;
import static java.awt.event.MouseEvent.MOUSE_CLICKED;
import static java.awt.event.MouseEvent.MOUSE_ENTERED;
import static java.awt.event.MouseEvent.MOUSE_EXITED;
import static java.util.Calendar.DATE;
import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.DAY_OF_WEEK;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.SUNDAY;
import static java.util.Calendar.YEAR;
import static javax.swing.BorderFactory.createLineBorder;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DateFormatSymbols;
import java.util.GregorianCalendar;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;

import org.javamoney.examples.ez.common.gui.Panel;
import org.javamoney.examples.ez.common.utility.ActionSignaler;

/**
 * This class facilitates choosing days in a month.
 */
public
final
class
DayChooser
extends Panel
{
  /**
   * Constructs a new chooser that will initialize the days according to the
   * specified calendar.
   *
   * @param calendar The calendar to initialize the chooser.
   * @param firstDayOfWeek The weekday the chooser should start with.
   */
  public
  DayChooser(GregorianCalendar calendar, int firstDayOfWeek)
  {
    setActionSignaler(new ActionSignaler());
    setCalendar(calendar);

    getCalendar().setFirstDayOfWeek(firstDayOfWeek);

    createMonthDays();

    buildPanel();

    displayDays();
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
   * This method returns the selected day's month.
   *
   * @return The selected day's month.
   */
  public
  int
  getMonth()
  {
    return getCalendar().get(MONTH);
  }

  /**
   * This method returns the selected day.
   *
   * @return The selected day.
   */
  public
  int
  getSelectedDay()
  {
    return getCalendar().get(DATE);
  }

  /**
   * This method returns the selected day's year.
   *
   * @return The selected day's year.
   */
  public
  int
  getYear()
  {
    return getCalendar().get(YEAR);
  }

  /**
   * This method sets the selected day and configures the weekday given the
   * specified month and year. Invoking this method causes the chooser to
   * re-render the days.
   *
   * @param day The day to select.
   * @param month The month for configuring the weekday.
   * @param year The date's year for determining weekday.
   */
  public
  void
  setDate(int day, int month, int year)
  {
    int max = getDaysInMonth(year, month);

    if(day > max)
    {
      day = max;
    }

    getCalendar().set(DATE, day);
    getCalendar().set(MONTH, month);
    getCalendar().set(YEAR, year);

    displayDays();
  }

  /**
   * This method configures the weekday given the specified month. Invoking this
   * method causes the chooser to re-render the days.
   *
   * @param month The month for configuring the weekday.
   */
  public
  void
  setMonth(int month)
  {
    setDate(getSelectedDay(), month, getYear());
  }

  /**
   * This method sets the selected day. Invoking this method causes the chooser
   * to re-render the days.
   *
   * @param day The day to select.
   */
  public
  void
  setSelectedDay(int day)
  {
    setDate(day, getMonth(), getYear());
  }

  /**
   * This configures the weekday given the specified year. Invoking this method
   * causes the chooser to re-render the days.
   *
   * @param year The year for configuring the weekday.
   */
  public
  void
  setYear(int year)
  {
    setDate(getSelectedDay(), getMonth(), year);
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of private methods.
  //////////////////////////////////////////////////////////////////////////////

  private
  void
  buildPanel()
  {
    String[] days = new DateFormatSymbols().getShortWeekdays();
    int start = getCalendar().getFirstDayOfWeek();

    // Build panel.
    setFill(GridBagConstraints.BOTH);
    addSpacer(0, 0, 1, 1, 1, 16);

    // Weekdays.
    for(int len = 0, col = 1; len < MAX_COLUMNS; ++len)
    {
      add(createWeekdayLabel(days[start++]), col++, 0, 1, 1, 14, 0);

      if(start > MAX_COLUMNS)
      {
        start = SUNDAY; // Start the week over.
      }
    }

    addSpacer(8, 0, 1, 1, 1, 0);

    // Month days.
    for(int len = 0, row = 1; row <= MAX_ROWS; ++row)
    {
      for(int col = 1; col <= MAX_COLUMNS; ++col, ++len)
      {
        add(getMonthDays()[len], col, row, 1, 1, 0, 14);
      }
    }
  }

  private
  void
  clearMonthDays()
  {
    for(int len = 0; len < getMonthDays().length; ++len)
    {
      getMonthDays()[len].setBackground(getBackground());
      getMonthDays()[len].setBorder(BORDER_NORMAL);
      getMonthDays()[len].setEnabled(false);
      getMonthDays()[len].setForeground(getForeground());
    }
  }

  private
  void
  createMonthDays()
  {
    MouseHandler listener = new MouseHandler();

    itsMonthDays = new JLabel[MAX_COLUMNS * MAX_ROWS];

    for(int len = 0; len < getMonthDays().length; ++len)
    {
      getMonthDays()[len] = new JLabel("", SwingConstants.CENTER);
      getMonthDays()[len].addMouseListener(listener);
      getMonthDays()[len].setOpaque(true);
    }
  }

  private
  static
  JLabel
  createWeekdayLabel(String day)
  {
    JLabel label = new JLabel(day, SwingConstants.CENTER);

    label.setBorder(new MatteBorder(1, 0, 1, 0, Color.GRAY));
    label.setFont(label.getFont().deriveFont(Font.PLAIN));
    label.setOpaque(true);

    return label;
  }

  private
  void
  displayDays()
  {
    int dayOfWeek = getStartingWeekdayIndex();
    int max = getDaysInMonth(getYear(), getMonth());
    int day = getDaysInMonth(getYear(), getMonth() - 1);
    int len = 0;

    clearMonthDays();

    // Show trailing days in previous month.
    for(len = dayOfWeek - 1; len >= 0; --len, --day)
    {
      getMonthDays()[len].setText("" + day);
    }

    for(len = dayOfWeek, day = 1; day <= max; ++len, ++day)
    {
      getMonthDays()[len].setEnabled(true);
      getMonthDays()[len].setText("" + day);

      // If the day is the currently selected day, then highlight it.
      if(day == getSelectedDay())
      {
        selectLabel(getMonthDays()[len]);
      }
    }

    // Fill out the rest of the calendar with days from the next month.
    for(day = 1; len < getMonthDays().length; ++len, ++day)
    {
      getMonthDays()[len].setText("" + day);
    }
  }

  private
  ActionSignaler
  getActionSignaler()
  {
    return itsActionSignaler;
  }

  private
  GregorianCalendar
  getCalendar()
  {
    return itsCalendar;
  }

  private
  static
  int
  getDaysInMonth(int year, int month)
  {
    return new GregorianCalendar(year, month, 1).getActualMaximum(DAY_OF_MONTH);
  }

  private
  JLabel[]
  getMonthDays()
  {
    return itsMonthDays;
  }

  private
  int
  getStartingWeekdayIndex()
  {
    int day = new GregorianCalendar(getYear(), getMonth(), 1).get(DAY_OF_WEEK);

    // Put day in proper array index form.
    day -= getCalendar().getFirstDayOfWeek();

    if(day < 0)
    {
      day += 7;
    }

    return day;
  }

  private
  static
  void
  selectLabel(JLabel label)
  {
    label.setBackground(HIGHLIGHT_CELL_COLOR);
    label.setBorder(BORDER_SELECTED);
    label.setForeground(Color.WHITE);
  }

  private
  void
  setActionSignaler(ActionSignaler list)
  {
    itsActionSignaler = list;
  }

  private
  void
  setCalendar(GregorianCalendar calendar)
  {
    itsCalendar = new GregorianCalendar();

    getCalendar().set(DATE, calendar.get(DATE));
    getCalendar().set(MONTH, calendar.get(MONTH));
    getCalendar().set(YEAR, calendar.get(YEAR));
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of inner classes.
  //////////////////////////////////////////////////////////////////////////////

  private
  class
  MouseHandler
  extends MouseAdapter
  {
    /*
     * This method handles the specific mouse event. Firstly, it ignores all
     * events on days that are disabled or are already the selected day. If the
     * event is a click, then it sets that day to be the selected day. If the
     * event is a mouse entered or mouse exited, then it sets that day's border
     * for a UI effect.
     */
    public
    void
    doMouseEvent(MouseEvent event)
    {
      JLabel label = (JLabel)event.getSource();

      if(label.isEnabled() == true)
      {
        if(Integer.parseInt(label.getText()) != getSelectedDay())
        {
          if(event.getID() == MOUSE_CLICKED && event.getButton() == BUTTON1)
          {
            getCalendar().set(DATE, Integer.valueOf(label.getText()).intValue());
            displayDays();

            getActionSignaler().sendSignal(DayChooser.this, ACTION_DAY_SELECTED);
          }
          else if(event.getID() == MOUSE_ENTERED)
          {
            label.setBorder(BORDER_SELECTED);
          }
          else if(event.getID() == MOUSE_EXITED)
          {
            label.setBorder(BORDER_NORMAL);
          }
        }
        else if(event.getClickCount() == 2)
        {
          getActionSignaler().sendSignal(DayChooser.this, ACTION_DAY_CHOSEN);
        }
      }
    }

    @Override
    public
    void
    mouseClicked(MouseEvent event)
    {
      doMouseEvent(event);
    }

    @Override
    public
    void
    mouseEntered(MouseEvent event)
    {
      doMouseEvent(event);
    }

    @Override
    public
    void
    mouseExited(MouseEvent event)
    {
      doMouseEvent(event);
    }
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  private ActionSignaler itsActionSignaler;
  private GregorianCalendar itsCalendar;
  private JLabel[] itsMonthDays;

  private static final Border BORDER_NORMAL = createLineBorder(new Panel().getBackground());
  private static final Border BORDER_SELECTED = createLineBorder(Color.BLACK);

  private static final Color HIGHLIGHT_CELL_COLOR = new Color(0, 51, 102);

  private static final int MAX_COLUMNS = 7;
  private static final int MAX_ROWS = 6;

  /**
   * This is an action constant that indicates a day was chosen via a double
   * click.
   */
  public static final String ACTION_DAY_CHOSEN = "Day Chosen";
  /**
   * This is an action constant that indicates a day was clicked on.
   */
  public static final String ACTION_DAY_SELECTED = "Day Selected";
}
