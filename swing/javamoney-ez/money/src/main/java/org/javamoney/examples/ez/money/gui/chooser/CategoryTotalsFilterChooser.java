// CategoryTotalsFilterChooser

package org.javamoney.examples.ez.money.gui.chooser;

import static org.javamoney.examples.ez.common.utility.ButtonHelper.buildButton;
import static org.javamoney.examples.ez.money.IconKeys.FILTER;

import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;

import org.javamoney.examples.ez.money.gui.dialog.CategoryTotalsFilterDialog;
import org.javamoney.examples.ez.money.model.dynamic.total.TotalFilter;

import org.javamoney.examples.ez.common.gui.CheckBox;
import org.javamoney.examples.ez.common.gui.Link;
import org.javamoney.examples.ez.common.gui.Panel;
import org.javamoney.examples.ez.common.utility.ActionSignaler;
import org.javamoney.examples.ez.common.utility.I18NHelper;

/**
 * This class facilitates providing a way to configure the filter used for the
 * category totals.
 */
public
final
class
CategoryTotalsFilterChooser
extends Panel
{
  /**
   * Constructs a new chooser.
   */
  public
  CategoryTotalsFilterChooser()
  {
    setActionSignaler(new ActionSignaler());
    setFilter(new TotalFilter());

    createButtons();

    // Build panel.
    setAnchor(GridBagConstraints.EAST);
    add(getButtons()[ENABLE], 0, 0, 1, 1, 100, 100);
    addEmptyCellAt(1, 0);
    add(getButtons()[OPTIONS], 2, 0, 1, 1, 0, 0);
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
   * This method returns the filter.
   *
   * @return The filter.
   */
  public
  TotalFilter
  getFilter()
  {
    return itsFilter;
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of private methods.
  //////////////////////////////////////////////////////////////////////////////

  private
  void
  createButtons()
  {
    ActionHandler handler = new ActionHandler();

    itsButtons = new AbstractButton[2];

    getButtons()[ENABLE] = new CheckBox();
    getButtons()[OPTIONS] = new Link();

    // Build buttons.
    buildButton(getButtons()[ENABLE], getProperty("enable"), handler);
    buildButton(getButtons()[OPTIONS], FILTER.getIcon(), handler, "", getProperty("filter_tip"));

    getButtons()[ENABLE].setSelected(false);
    getButtons()[OPTIONS].setEnabled(false);
  }

  private
  ActionSignaler
  getActionSignaler()
  {
    return itsActionSignaler;
  }

  private
  AbstractButton[]
  getButtons()
  {
    return itsButtons;
  }

  private
  static
  String
  getProperty(String key)
  {
    return I18NHelper.getProperty("CategoryTotalsFilterChooser." + key);
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
  setFilter(TotalFilter filter)
  {
    itsFilter = filter;
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

      if(source == getButtons()[ENABLE])
      {
        getFilter().setEnabled(!getFilter().isEnabled());
        getButtons()[OPTIONS].setEnabled(getFilter().isEnabled());

        sendSignal();
      }
      else if(new CategoryTotalsFilterDialog(getFilter()).showDialog() == true)
      {
        sendSignal();
      }
    }
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  private ActionSignaler itsActionSignaler;
  private AbstractButton[] itsButtons;
  private TotalFilter itsFilter;

  private static final int ENABLE = 0;
  private static final int OPTIONS = 1;
}
