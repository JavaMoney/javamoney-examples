// LabelChooser

package org.javamoney.examples.ez.money.gui.chooser;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

import org.javamoney.examples.ez.money.model.persisted.transaction.LabelKeys;

import org.javamoney.examples.ez.common.gui.ComboBox;

/**
 * This class facilitates choosing a label from a combo box.
 */
public
final
class
LabelChooser
extends ComboBox
{
  /**
   * Constructs a new chooser.
   */
  public
  LabelChooser()
  {
    super(LabelKeys.values());

    setRenderer(new ListCellRenderHandler());
  }

  /**
   * This method returns the selected label.
   *
   * @return The selected label.
   */
  public
  LabelKeys
  getSelectedLabel()
  {
    return (LabelKeys)getSelectedItem();
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of inner classes.
  //////////////////////////////////////////////////////////////////////////////

  final
  class
  ListCellRenderHandler
  extends DefaultListCellRenderer
  {
    @Override
    public
    Component
    getListCellRendererComponent(JList list, Object item, int row,
        boolean isSelected, boolean hasFocus)
    {
      super.getListCellRendererComponent(list, item, row, isSelected, hasFocus);

      setIcon(((LabelKeys)item).getIcon());

      return this;
    }
  }
}
