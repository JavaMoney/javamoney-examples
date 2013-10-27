// SelectableComponentPanel

package org.javamoney.examples.ez.money.gui;

import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComponent;

import org.javamoney.examples.ez.common.gui.CheckBox;
import org.javamoney.examples.ez.common.gui.Panel;

/**
 * This class facilitates wrapping a component in a panel so that a toggle can
 * be provided to enable or disable the component.
 */
public
final
class
SelectableComponentPanel
extends Panel
{
  /**
   * Constructs a new panel.
   *
   * @param component The component to monitor.
   */
  public
  SelectableComponentPanel(JComponent component)
  {
    this(component, false);
  }

  /**
   * Constructs a new panel.
   *
   * @param component The component to monitor.
   * @param enabled Whether or not the component is initially enabled.
   */
  public
  SelectableComponentPanel(JComponent component, boolean enabled)
  {
    setCheckBox(new CheckBox());
    setComponent(component);

    getCheckBox().setSelected(enabled);
    getComponent().setEnabled(enabled);

    // Build panel.
    setFill(GridBagConstraints.BOTH);
    add(getCheckBox(), 0, 0, 1, 1, 0, 100);
    add(getComponent(), 1, 0, 1, 1, 100, 0);

    // Add listeners.
    getCheckBox().addActionListener(new ActionController());
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of private methods.
  //////////////////////////////////////////////////////////////////////////////

  private
  CheckBox
  getCheckBox()
  {
    return itsCheckBox;
  }

  private
  JComponent
  getComponent()
  {
    return itsComponent;
  }

  private
  void
  setCheckBox(CheckBox checkBox)
  {
    itsCheckBox = checkBox;
  }

  private
  void
  setComponent(JComponent component)
  {
    itsComponent = component;
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of inner classes.
  //////////////////////////////////////////////////////////////////////////////

  private
  class
  ActionController
  implements ActionListener
  {
    public
    void
    actionPerformed(ActionEvent event)
    {
      getComponent().setEnabled(!getComponent().isEnabled());
    }
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  private CheckBox itsCheckBox;
  private JComponent itsComponent;
}
