// EditorHelper

package org.javamoney.examples.ez.money.utility;

import static javax.swing.SwingConstants.CENTER;
import static javax.swing.SwingConstants.TRAILING;
import static org.javamoney.examples.ez.money.model.persisted.transaction.Transaction.MAX_CHECK_LENGTH;
import static org.javamoney.examples.ez.money.model.persisted.transaction.Transaction.MAX_NOTES_LENGTH;

import javax.swing.DefaultCellEditor;
import javax.swing.JTextField;

import org.javamoney.examples.ez.money.gui.chooser.ElementComboBoxChooser;
import org.javamoney.examples.ez.money.model.persisted.category.CategoryCollection;

import org.javamoney.examples.ez.common.gui.CheckBox;
import org.javamoney.examples.ez.common.utility.TextConstrainer;

/**
 * This class facilitates creating editors for UI components. All methods in
 * this class are static.
 */
public
final
class
EditorHelper
{
  /**
   * This method creates a table cell editor for amounts.
   *
   * @return A table cell editor for amounts.
   */
  public
  static
  DefaultCellEditor
  createAmountCellEditor()
  {
    return new DefaultCellEditor(createAmountFieldEditor());
  }

  /**
   * This method creates a text field editor for amounts.
   *
   * @return A text field editor for amounts.
   */
  public
  static
  JTextField
  createAmountFieldEditor()
  {
    JTextField textField = createTextField(new TextConstrainer(13, "0123456789,."));

    textField.setHorizontalAlignment(TRAILING);

    return textField;
  }

  /**
   * This method creates a table cell editor for the specified categories.
   *
   * @param collection The categories to provide editing for.
   *
   * @return A table cell editor for categories.
   */
  public
  static
  DefaultCellEditor
  createCategoryCellEditor(CategoryCollection collection)
  {
    ElementComboBoxChooser chooser =
      new ElementComboBoxChooser(collection);

    chooser.addNotCategorizedOption();

    return new DefaultCellEditor(chooser);
  }

  /**
   * This method creates a text field editor for check numbers.
   *
   * @return A text field editor for check numbers.
   */
  public
  static
  JTextField
  createCheckNumberFieldEditor()
  {
    return createTextField(new TextConstrainer(MAX_CHECK_LENGTH));
  }

  /**
   * This method creates a text field editor for dates.
   *
   * @return A text field editor for dates.
   */
  public
  static
  JTextField
  createDateFieldEditor()
  {
    JTextField textField = createTextField(new TextConstrainer(8, "0123456789/."));

    textField.setHorizontalAlignment(TRAILING);

    return textField;
  }

  /**
   * This method creates a text field editor for notes.
   *
   * @return A text field editor for notes.
   */
  public
  static
  JTextField
  createNotesFieldEditor()
  {
    return createTextField(new TextConstrainer(MAX_NOTES_LENGTH));
  }

  /**
   * This method creates a table cell editor for selectable items.
   *
   * @return A table cell editor for selectable items.
   */
  public
  static
  DefaultCellEditor
  createSelectCellEditor()
  {
    CheckBox checkbox = new CheckBox();

    checkbox.setHorizontalAlignment(CENTER);

    return new DefaultCellEditor(checkbox);
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of private methods.
  //////////////////////////////////////////////////////////////////////////////

  private
  static
  JTextField
  createTextField(TextConstrainer constrainer)
  {
    JTextField textField = new JTextField();

    textField.setDocument(constrainer);

    return textField;
  }
}
