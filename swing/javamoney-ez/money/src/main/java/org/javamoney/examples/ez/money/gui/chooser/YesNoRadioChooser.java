// YesNoRadioChooser

package org.javamoney.examples.ez.money.gui.chooser;

import static org.javamoney.examples.ez.common.utility.ButtonHelper.buildButton;
import static org.javamoney.examples.ez.common.utility.I18NHelper.getSharedProperty;

import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;

import org.javamoney.examples.ez.common.gui.Panel;
import org.javamoney.examples.ez.common.gui.RadioButton;
import org.javamoney.examples.ez.common.utility.ActionSignaler;

/**
 * This class facilitates choosing yes or no from two radio buttons.
 */
public
final
class
YesNoRadioChooser
extends Panel
{
  /**
   * Constructs a new chooser.
   */
  public
  YesNoRadioChooser()
  {
    setActionSignaler(new ActionSignaler());

    createButtons();

    // Build panel.
    setAnchor(GridBagConstraints.WEST);
    add(getButtons()[YES], 0, 0, 1, 1, 0, 100);
    add(getButtons()[NO], 1, 0, 1, 1, 100, 0);
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
   * This method returns true if no is selected, otherwise false.
   *
   * @return true or false.
   */
  public
  boolean
  isNoSelected()
  {
    return getButtons()[NO].isSelected();
  }

  /**
   * This method returns true if yes is selected, otherwise false.
   *
   * @return true or false.
   */
  public
  boolean
  isYesSelected()
  {
    return getButtons()[YES].isSelected();
  }

  /**
   * This method enables or disables the chooser.
   *
   * @param value true or false.
   */
  @Override
  public
  void
  setEnabled(boolean value)
  {
    getButtons()[NO].setEnabled(value);
    getButtons()[YES].setEnabled(value);
  }

  /**
   * This method sets whether or not the no option is selected.
   *
   * @param value true or false.
   */
  public
  void
  setNoSelected(boolean value)
  {
    getButtons()[NO].setSelected(value);
    sendActionSignalFor(NO);
  }

  /**
   * This method sets whether or not the yes option is selected.
   *
   * @param value true or false.
   */
  public
  void
  setYesSelected(boolean value)
  {
    getButtons()[YES].setSelected(value);
    sendActionSignalFor(YES);
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of private methods.
  //////////////////////////////////////////////////////////////////////////////

  private
  void
  createButtons()
  {
    ActionHandler handler = new ActionHandler();
    ButtonGroup group = new ButtonGroup();

    itsButtons = new RadioButton[2];

    getButtons()[NO] = new RadioButton();
    getButtons()[YES] = new RadioButton();

    buildButton(getButtons()[NO], getSharedProperty("no"), handler, group);
    buildButton(getButtons()[YES], getSharedProperty("yes"), handler, group);
  }

  private
  ActionSignaler
  getActionSignaler()
  {
    return itsActionSignaler;
  }

  private
  RadioButton[]
  getButtons()
  {
    return itsButtons;
  }

  private
  void
  sendActionSignalFor(int button)
  {
    getActionSignaler().sendSignal(this, getButtons()[button].getActionCommand());
  }

  private
  void
  setActionSignaler(ActionSignaler signaler)
  {
    itsActionSignaler = signaler;
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
      if(event.getSource() == getButtons()[NO])
      {
        sendActionSignalFor(NO);
      }
      else
      {
        sendActionSignalFor(YES);
      }
    }
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  private ActionSignaler itsActionSignaler;
  private RadioButton[] itsButtons;

  private static final int NO = 0;
  private static final int YES = 1;
}
