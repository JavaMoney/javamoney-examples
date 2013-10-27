// ButtonHelper

package org.javamoney.examples.ez.common.utility;

import java.awt.event.ActionListener;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.Icon;

/**
 * This class facilitates building buttons such as standard buttons, check
 * boxes, radio buttons, and menu items, by setting their most commonly used
 * attributes.
 */
public
final
class
ButtonHelper
{
  /**
   * This method builds the specified button according to the specified
   * parameters.
   *
   * @param button The button to build.
   * @param icon The icon to use.
   * @param handler The action listener that monitors the buttons events.
   * @param command The action command used when the button is clicked.
   * @param tip The tool tip to display.
   */
  public
  static
  void
  buildButton(AbstractButton button, Icon icon, ActionListener handler,
      String command, String tip)
  {
    buildButton(button, "", icon, handler, command, tip);
  }

  /**
   * This method builds the specified button according to the specified
   * parameters.
   *
   * @param button The button to build.
   * @param text The text displayed on the button.
   * @param handler The action listener that monitors the buttons events.
   */
  public
  static
  void
  buildButton(AbstractButton button, String text, ActionListener handler)
  {
    buildButton(button, text, handler, text);
  }

  /**
   * This method builds the specified button according to the specified
   * parameters.
   *
   * @param button The button to build.
   * @param text The text displayed on the button.
   * @param handler The action listener that monitors the buttons events.
   * @param selected The initial selected state of the button.
   */
  public
  static
  void
  buildButton(AbstractButton button, String text, ActionListener handler,
      boolean selected)
  {
    buildButton(button, text, null, handler, text, null, selected, null);
  }

  /**
   * This method builds the specified button according to the specified
   * parameters.
   *
   * @param button The button to build.
   * @param text The text displayed on the button.
   * @param handler The action listener that monitors the buttons events.
   * @param group The group the button should belong to.
   */
  public
  static
  void
  buildButton(AbstractButton button, String text, ActionListener handler,
      ButtonGroup group)
  {
    buildButton(button, text, null, handler, text, group, false, null);
  }

  /**
   * This method builds the specified button according to the specified
   * parameters.
   *
   * @param button The button to build.
   * @param text The text displayed on the button.
   * @param handler The action listener that monitors the buttons events.
   * @param command The action command used when the button is clicked.
   */
  public
  static
  void
  buildButton(AbstractButton button, String text, ActionListener handler,
      String command)
  {
    buildButton(button, text, null, handler, command, null);
  }

  /**
   * This method builds the specified button according to the specified
   * parameters.
   *
   * @param button The button to build.
   * @param text The text displayed on the button.
   * @param handler The action listener that monitors the buttons events.
   * @param command The action command used when the button is clicked.
   * @param tip The tool tip to display.
   */
  public
  static
  void
  buildButton(AbstractButton button, String text, ActionListener handler,
      String command, String tip)
  {
    buildButton(button, text, null, handler, command, tip);
  }

  /**
   * This method builds the specified button according to the specified
   * parameters.
   *
   * @param button The button to build.
   * @param text The text displayed on the button.
   * @param icon The icon to use.
   * @param handler The action listener that monitors the buttons events.
   */
  public
  static
  void
  buildButton(AbstractButton button, String text, Icon icon,
      ActionListener handler)
  {
    buildButton(button, text, icon, handler, text, null, false, null);
  }

  /**
   * This method builds the specified button according to the specified
   * parameters.
   *
   * @param button The button to build.
   * @param text The text displayed on the button.
   * @param icon The icon to use.
   * @param handler The action listener that monitors the buttons events.
   * @param command The action command used when the button is clicked.
   * @param tip The tool tip to display.
   */
  public
  static
  void
  buildButton(AbstractButton button, String text, Icon icon,
      ActionListener handler, String command, String tip)
  {
    buildButton(button, text, icon, handler, command, null, false, tip);
  }

  /**
   * This method builds the specified button according to the specified
   * parameters.
   *
   * @param button The button to build.
   * @param text The text displayed on the button.
   * @param icon The icon to use.
   * @param handler The action listener that monitors the buttons events.
   * @param command The action command used when the button is clicked.
   * @param group The group the button should belong to.
   * @param selected The initial selected state of the button.
   * @param tip The tool tip to display.
   */
  public
  static
  void
  buildButton(AbstractButton button, String text, Icon icon,
      ActionListener handler, String command, ButtonGroup group,
      boolean selected, String tip)
  {
    button.addActionListener(handler);
    button.setActionCommand(command);
    button.setIcon(icon);
    button.setSelected(selected);
    button.setText(text);
    button.setToolTipText(tip);

    if(group != null)
    {
      group.add(button);
    }
  }
}
