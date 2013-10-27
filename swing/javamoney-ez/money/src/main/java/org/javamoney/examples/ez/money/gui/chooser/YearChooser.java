// YearChooser

package org.javamoney.examples.ez.money.gui.chooser;

import static org.javamoney.examples.ez.common.utility.ButtonHelper.buildButton;
import static org.javamoney.examples.ez.money.IconKeys.ARROW_BACK;
import static org.javamoney.examples.ez.money.IconKeys.ARROW_FORWARD;

import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JToolBar;

import org.javamoney.examples.ez.common.gui.Link;
import org.javamoney.examples.ez.common.gui.Panel;
import org.javamoney.examples.ez.common.utility.ActionSignaler;
import org.javamoney.examples.ez.common.utility.I18NHelper;

/**
 * This class facilitates choosing a year.
 */
public
final
class
YearChooser
extends Panel
{
  /**
   * Constructs a new chooser with the current year initially selected.
   */
  public
  YearChooser()
  {
    this(new GregorianCalendar().get(Calendar.YEAR));
  }

  /**
   * Constructs a new chooser with the year initially selected.
   *
   * @param year The year to initially select.
   */
  public
  YearChooser(int year)
  {
    setActionSignaler(new ActionSignaler());
    setLabel(new JLabel());
    setSelectedYear(year);

    // Build panel.
    setAnchor(GridBagConstraints.EAST);
    add(createButton(ACTION_PREVIOUS, ARROW_BACK.getIcon(),
        getProperty("tip_previous")), 0, 0, 1, 1, 50, 100);

    setAnchor(GridBagConstraints.CENTER);
    addEmptyCellAt(1, 0);
    add(getLabel(), 2, 0, 1, 1, 0, 0);
    addEmptyCellAt(3, 0);

    setAnchor(GridBagConstraints.WEST);
    add(createButton(ACTION_NEXT, ARROW_FORWARD.getIcon(),
        getProperty("tip_next")), 4, 0, 1, 1, 50, 0);
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
   * This method returns the current year.
   *
   * @return The current year.
   */
  public
  int
  getSelectedYear()
  {
    return itsYear;
  }

  /**
   * This method sets and displays the selected year. Invoking this method will
   * trigger an action event.
   *
   * @param year The selected year.
   */
  public
  void
  setSelectedYear(int year)
  {
    itsYear = constrainYear(year);

    getLabel().setText("" + getSelectedYear());

    getActionSignaler().sendSignal(this, "" + getSelectedYear());
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of private methods.
  //////////////////////////////////////////////////////////////////////////////

  private
  static
  int
  constrainYear(int year)
  {
    if(year < MIN_YEAR)
    {
      year = MIN_YEAR;
    }
    else if(year > MAX_YEAR)
    {
      year = MAX_YEAR;
    }

    return year;
  }

  private
  JToolBar
  createButton(String command, Icon icon, String tip)
  {
    JToolBar toolBar = new JToolBar();
    Link link = new Link();

    // Build link.
    buildButton(link, icon, new ActionHandler(), command, tip);

    // Build tool bar.
    toolBar.add(link);
    toolBar.setBorderPainted(false);
    toolBar.setFloatable(false);
    toolBar.setRollover(true);

    return toolBar;
  }

  private
  ActionSignaler
  getActionSignaler()
  {
    return itsActionSignaler;
  }

  private
  JLabel
  getLabel()
  {
    return itsLabel;
  }

  private
  static
  String
  getProperty(String key)
  {
    return I18NHelper.getProperty("YearChooser." + key);
  }

  private
  void
  setActionSignaler(ActionSignaler signaler)
  {
    itsActionSignaler = signaler;
  }

  private
  void
  setLabel(JLabel label)
  {
    itsLabel = label;
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
      if(event.getActionCommand().equals(ACTION_NEXT) == true)
      {
        setSelectedYear(getSelectedYear() + 1);
      }
      else
      {
        setSelectedYear(getSelectedYear() - 1);
      }
    }
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  private ActionSignaler itsActionSignaler;
  private JLabel itsLabel;
  private int itsYear;

  private static final String ACTION_NEXT = "Next";
  private static final String ACTION_PREVIOUS = "Previous";

  /**
   * The maximum year the chooser can display.
   */
  public static final int MAX_YEAR = 9999;
  /**
   * The minimum year the chooser can display.
   */
  public static final int MIN_YEAR = 1900;
}
