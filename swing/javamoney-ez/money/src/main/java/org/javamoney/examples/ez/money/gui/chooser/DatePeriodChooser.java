// DatePeriodChooser

package org.javamoney.examples.ez.money.gui.chooser;

import static org.javamoney.examples.ez.common.utility.ButtonHelper.buildButton;
import static org.javamoney.examples.ez.common.utility.DateHelper.getEndOfCurrentMonth;
import static org.javamoney.examples.ez.common.utility.DateHelper.getEndOfCurrentYear;
import static org.javamoney.examples.ez.common.utility.DateHelper.getEndOfLastMonth;
import static org.javamoney.examples.ez.common.utility.DateHelper.getEndOfLastYear;
import static org.javamoney.examples.ez.common.utility.DateHelper.getStartOfCurrentMonth;
import static org.javamoney.examples.ez.common.utility.DateHelper.getStartOfCurrentYear;
import static org.javamoney.examples.ez.common.utility.DateHelper.getStartOfFortnight;
import static org.javamoney.examples.ez.common.utility.DateHelper.getStartOfLastMonth;
import static org.javamoney.examples.ez.common.utility.DateHelper.getStartOfLastYear;
import static org.javamoney.examples.ez.common.utility.I18NHelper.getSharedProperty;
import static org.javamoney.examples.ez.money.ApplicationProperties.UI_DATE_FORMAT;
import static org.javamoney.examples.ez.money.IconKeys.ACTIONS;
import static org.javamoney.examples.ez.money.IconKeys.DATE;
import static org.javamoney.examples.ez.money.IconKeys.MENU_ARROW;
import static org.javamoney.examples.ez.money.utility.TransactionDateHelper.showDateDialog;

import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.SwingConstants;

import org.javamoney.examples.ez.common.gui.Link;
import org.javamoney.examples.ez.common.gui.Panel;
import org.javamoney.examples.ez.common.gui.PopupMenu;
import org.javamoney.examples.ez.common.utility.ActionSignaler;
import org.javamoney.examples.ez.common.utility.I18NHelper;

/**
 * This class facilitates choosing a date period.
 */
public
final
class
DatePeriodChooser
extends Panel
{
  /**
   * Constructs a new date period chooser with its initial period set to the
   * current month.
   */
  public
  DatePeriodChooser()
  {
    setActionSignaler(new ActionSignaler());

    createDates();
    createLinks();

    buildPanel();

    updateDateLinks();
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
   * This method returns the end of the date period.
   *
   * @return The end of the date period.
   */
  public
  Date
  getEndDate()
  {
    return getDates()[END];
  }

  /**
   * This method returns the start of the date period.
   *
   * @return The start of the date period.
   */
  public
  Date
  getStartDate()
  {
    return getDates()[START];
  }

  /**
   * This method updates the view.
   */
  public
  void
  updateView()
  {
    String end = ACTION_ALL;
    String start = ACTION_ALL;

    if(getDates()[END] != null)
    {
      end = UI_DATE_FORMAT.format(getDates()[END]);
    }

    if(getDates()[START] != null)
    {
      start = UI_DATE_FORMAT.format(getDates()[START]);
    }

    // Update the link's text.
    getLinks()[END].setText(end);
    getLinks()[START].setText(start);
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of private methods.
  //////////////////////////////////////////////////////////////////////////////

  private
  void
  buildPanel()
  {
    String gap = ": ";

    // Build panel.
    setAnchor(GridBagConstraints.EAST);
    add(getProperty("start_on") + gap, 0, 0, 1, 1, 100, 100);
    add(getSharedProperty("thru") + gap, 3, 0, 1, 1, 0, 0);

    setAnchor(GridBagConstraints.WEST);
    add(getLinks()[START], 1, 0, 1, 1, 0, 0);
    add(getLinks()[END], 4, 0, 1, 1, 0, 0);
    add(createDateQuickPicksPanel(), 6, 0, 1, 1, 0, 0);

    // These spacers provide aesthetic gaps.
    addEmptyCellAt(1, 1, 11);
    addEmptyCellAt(2, 0);
    addEmptyCellAt(4, 1, 11);
    addEmptyCellAt(5, 0);
    addEmptyCellAt(7, 0);
  }

  private
  Panel
  createDateQuickPicksPanel()
  {
    Panel panel = new Panel();
    PopupMenu menu = new PopupMenu();
    ActionHandler handler = new ActionHandler();
    JLabel view = new JLabel(getProperty("periods"));

    // Add items to menu.
    menu.add(createMenuItem(ACTION_THIS_MONTH, handler));
    menu.add(createMenuItem(ACTION_THIS_YEAR, handler));
    menu.addSeparator();
    menu.add(createMenuItem(ACTION_LAST_MONTH, handler));
    menu.add(createMenuItem(ACTION_LAST_YEAR, handler));
    menu.addSeparator();
    menu.add(createMenuItem(ACTION_FORTNIGHT, handler));
    menu.addSeparator();
    menu.add(createMenuItem(ACTION_ALL, handler));
    menu.setBehaveLikeMenu(true);

    view.addMouseListener(menu);
    view.setHorizontalTextPosition(SwingConstants.LEFT);
    view.setIcon(MENU_ARROW.getIcon());

    // Build panel.
    panel.add(ACTIONS.getIcon(), 0, 0, 1, 1, 0, 100);
    panel.add(view, 1, 0, 1, 1, 100, 0);

    return panel;
  }

  private
  void
  createDates()
  {
    itsDates = new Date[2];

    getDates()[END] = getEndOfCurrentMonth();
    getDates()[START] = getStartOfCurrentMonth();
  }

  private
  void
  createLinks()
  {
    ActionHandler handler = new ActionHandler();

    itsLinks = new Link[2];

    getLinks()[END] = new Link();
    getLinks()[START] = new Link();

    // Build links.
    buildButton(getLinks()[END], DATE.getIcon(), handler, "",
        getProperty("end_tip"));

    buildButton(getLinks()[START], DATE.getIcon(), handler, "",
        getProperty("start_tip"));
  }

  private
  static
  JMenuItem
  createMenuItem(String command, ActionHandler handler)
  {
    JMenuItem item = new JMenuItem();

    // Build items.
    buildButton(item, command, handler);

    return item;
  }

  private
  ActionSignaler
  getActionSignaler()
  {
    return itsActionSignaler;
  }

  private
  Date[]
  getDates()
  {
    return itsDates;
  }

  private
  Link[]
  getLinks()
  {
    return itsLinks;
  }

  private
  static
  String
  getProperty(String key)
  {
    return I18NHelper.getProperty("DatePeriodChooser." + key);
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
  setDate(int index)
  {
    Date date = showDateDialog(getDates()[index]);

    if(date != null && date.equals(getDates()[index]) == false)
    {
      getDates()[index] = date;
      updateDateLinks();
    }
  }

  private
  void
  updateDateLinks()
  {
    updateView();
    sendSignal();
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
      Object source = event.getSource();

      if(source == getLinks()[END])
      {
        setDate(END);
      }
      else if(source == getLinks()[START])
      {
        setDate(START);
      }
      else if(command.equals(ACTION_ALL) == true)
      {
        getDates()[END] = null;
        getDates()[START] = null;
        updateDateLinks();
      }
      else if(command.equals(ACTION_FORTNIGHT) == true)
      {
        getDates()[END] = new Date();
        getDates()[START] = getStartOfFortnight();
        updateDateLinks();
      }
      else if(command.equals(ACTION_LAST_MONTH) == true)
      {
        getDates()[END] = getEndOfLastMonth();
        getDates()[START] = getStartOfLastMonth();
        updateDateLinks();
      }
      else if(command.equals(ACTION_LAST_YEAR) == true)
      {
        getDates()[END] = getEndOfLastYear();
        getDates()[START] = getStartOfLastYear();
        updateDateLinks();
      }
      else if(command.equals(ACTION_THIS_MONTH) == true)
      {
        getDates()[END] = getEndOfCurrentMonth();
        getDates()[START] = getStartOfCurrentMonth();
        updateDateLinks();
      }
      else if(command.equals(ACTION_THIS_YEAR) == true)
      {
        getDates()[END] = getEndOfCurrentYear();
        getDates()[START] = getStartOfCurrentYear();
        updateDateLinks();
      }
    }
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  private ActionSignaler itsActionSignaler;
  private Date[] itsDates;
  private Link[] itsLinks;

  private static final int END = 1;
  private static final int START = 0;

  private static final String ACTION_ALL = getSharedProperty("all");
  private static final String ACTION_FORTNIGHT = getProperty("option.fortnight");
  private static final String ACTION_LAST_MONTH = getProperty("option.last_month");
  private static final String ACTION_LAST_YEAR = getProperty("option.last_year");
  private static final String ACTION_THIS_MONTH = getProperty("option.this_month");
  private static final String ACTION_THIS_YEAR = getProperty("option.this_year");
}
