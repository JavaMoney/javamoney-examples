// ComboBox

package org.javamoney.examples.ez.common.gui;

import javax.swing.JComboBox;
import javax.swing.JTextField;

/**
 * This class is designed to provide convenience methods for a combo box.
 */
public
class
ComboBox
extends JComboBox
{
  /**
   * Constructs a new combo box.
   */
  public
  ComboBox()
  {
    // Avoid implicit super constructor error.
  }

  /**
   * Constructs a new combo box.
   *
   * @param selections The selections to be added.
   */
  public
  ComboBox(Object[] selections)
  {
    super(selections);
  }

  /**
   * This method clears the current selection even if the combo box is not
   * editable.
   */
  public
  final
  void
  clearSelection()
  {
    setSelection("");
  }

  /**
   * This method returns the text field that is the editor component.
   *
   * @return The text field that is the editor component.
   */
  public
  final
  JTextField
  getTextField()
  {
    return (JTextField)getEditor().getEditorComponent();
  }

  /**
   * This method sets the current selection even if the combo box is not
   * editable.
   *
   * @param selection The item to use as the current selection.
   */
  public
  final
  void
  setSelection(Object selection)
  {
    boolean editable = isEditable();

    setEditable(true);
    setSelectedItem(selection);
    setEditable(editable);
  }
}
