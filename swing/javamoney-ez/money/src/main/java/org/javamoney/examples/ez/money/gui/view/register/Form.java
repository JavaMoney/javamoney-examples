// Form

package org.javamoney.examples.ez.money.gui.view.register;

import static org.javamoney.examples.ez.common.CommonConstants.IS_MAC;
import static org.javamoney.examples.ez.common.utility.ButtonHelper.buildButton;
import static org.javamoney.examples.ez.common.utility.I18NHelper.getSharedProperty;
import static org.javamoney.examples.ez.money.KeywordKeys.NOT_CATEGORIZED;
import static org.javamoney.examples.ez.money.gui.view.register.FormButtonKeys.CANCEL;
import static org.javamoney.examples.ez.money.gui.view.register.FormButtonKeys.DATE_PICKER;
import static org.javamoney.examples.ez.money.gui.view.register.FormButtonKeys.EDIT;
import static org.javamoney.examples.ez.money.gui.view.register.FormButtonKeys.ENTER;
import static org.javamoney.examples.ez.money.gui.view.register.FormButtonKeys.NEW;
import static org.javamoney.examples.ez.money.gui.view.register.FormButtonKeys.NEXT;
import static org.javamoney.examples.ez.money.gui.view.register.FormButtonKeys.PENDING;
import static org.javamoney.examples.ez.money.gui.view.register.FormButtonKeys.SPLIT;
import static org.javamoney.examples.ez.money.gui.view.register.FormFieldKeys.AMOUNT;
import static org.javamoney.examples.ez.money.gui.view.register.FormFieldKeys.CHECK_NUMBER;
import static org.javamoney.examples.ez.money.gui.view.register.FormFieldKeys.DATE;
import static org.javamoney.examples.ez.money.gui.view.register.FormFieldKeys.NOTES;
import static org.javamoney.examples.ez.money.model.DataManager.getAccounts;
import static org.javamoney.examples.ez.money.model.DataManager.getExpenses;
import static org.javamoney.examples.ez.money.model.DataManager.getIncome;
import static org.javamoney.examples.ez.money.model.DataManager.getPayees;
import static org.javamoney.examples.ez.money.model.dynamic.transaction.TransactionTypeKeys.EXPENSE;
import static org.javamoney.examples.ez.money.model.dynamic.transaction.TransactionTypeKeys.INCOME;
import static org.javamoney.examples.ez.money.model.dynamic.transaction.TransactionTypeKeys.TRANSFER;
import static org.javamoney.examples.ez.money.model.persisted.transaction.Transaction.MAX_PAYEE_LENGTH;
import static org.javamoney.examples.ez.money.utility.EditorHelper.createAmountFieldEditor;
import static org.javamoney.examples.ez.money.utility.EditorHelper.createCheckNumberFieldEditor;
import static org.javamoney.examples.ez.money.utility.EditorHelper.createDateFieldEditor;
import static org.javamoney.examples.ez.money.utility.EditorHelper.createNotesFieldEditor;

import java.awt.GridBagConstraints;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTextField;

import org.javamoney.examples.ez.money.IconKeys;
import org.javamoney.examples.ez.money.gui.chooser.ElementComboBoxChooser;
import org.javamoney.examples.ez.money.model.DataCollection;
import org.javamoney.examples.ez.money.model.dynamic.transaction.TransactionTypeKeys;

import org.javamoney.examples.ez.common.gui.Link;
import org.javamoney.examples.ez.common.gui.Panel;
import org.javamoney.examples.ez.common.utility.ClipboardMenuController;
import org.javamoney.examples.ez.common.utility.I18NHelper;
import org.javamoney.examples.ez.common.utility.TextConstrainer;

/**
 * This class facilitates management of all the elements that make up the
 * transaction form.
 */
final
class
Form
extends Panel
{
  /**
   * Constructs a new form customized for the specified transaction type.
   *
   * @param type The transaction type.
   * @param handler The listener to be notified when action events occur.
   */
  protected
  Form(TransactionTypeKeys type, ActionListener handler)
  {
    setType(type);

    setPayFromChooser(new ElementComboBoxChooser(getPayFromCollection()));
    setPayToChooser(new ElementComboBoxChooser(getPayToCollection()));

    createButtons(handler);
    createFields();

    buildPanel();

    if(getType() != TRANSFER)
    {
      getPayFromChooser().setEditable(true);
      getPayFromChooser().getTextField().setDocument(new TextConstrainer(MAX_PAYEE_LENGTH));

      // Add listeners.
      new AutoCompleteHandler(this);
      new ClipboardMenuController(getPayFromChooser().getTextField());
    }

    // Add listeners.
    for(JTextField field : getFields())
    {
      new ClipboardMenuController(field);
    }

    new FormDateHandler(this);
    new FormTraversalHandler(this);
  }

  /**
   * This method clears all the editable fields in the form.
   */
  protected
  void
  clearFields()
  {
    for(JTextField field : getFields())
    {
      field.setText("");
    }

    if(getType() != TRANSFER)
    {
      getPayFromChooser().clearSelection();
      setPayToIsSplit(false);
    }
  }

  /**
   * This method enables or disables the elements of the from depending of the
   * specified value.
   *
   * @param value true or false.
   */
  protected
  void
  enableForm(boolean value)
  {
    // Enable buttons.
    for(AbstractButton button : getButtons())
    {
      button.setEnabled(value);
    }

    // Enable fields.
    for(JTextField field : getFields())
    {
      field.setEnabled(value);
    }

    // Enable choosers.
    getPayToChooser().setEnabled(value);
    getPayFromChooser().setEnabled(value);

    // Set defaults.
    getButton(NEW).setEnabled(true);

    // Splits are only for non-transfers.
    if(value == true && getType() == TRANSFER)
    {
      getButton(SPLIT).setEnabled(false);
    }
  }

  /**
   * This method returns the form button for the specified key.
   *
   * @param key The button's key.
   *
   * @return The form button for the specified key.
   */
  protected
  AbstractButton
  getButton(FormButtonKeys key)
  {
    return getButtons()[key.ordinal()];
  }

  /**
   * This method returns the form field for the specified key.
   *
   * @param key The field's key.
   *
   * @return The form field for the specified key.
   */
  protected
  JTextField
  getField(FormFieldKeys key)
  {
    return getFields()[key.ordinal()];
  }

  /**
   * This method returns where the money is being paid from, either an account
   * or payee.
   *
   * @return Where the money is being paid from, either an account or payee.
   */
  protected
  String
  getPayFrom()
  {
    return getPayFromChooser().getSelectedItem();
  }

  /**
   * This method returns the chooser for selecting where the money is being paid
   * from, either an account or payee.
   *
   * @return The chooser for selecting where the money is being paid from.
   */
  protected
  ElementComboBoxChooser
  getPayFromChooser()
  {
    return itsPayFromChooser;
  }

  /**
   * This method returns the where the money is being paid to, either an account
   * or category.
   *
   * @return Where the money is being paid to, either an account or category.
   */
  protected
  String
  getPayTo()
  {
    String payTo = getPayToChooser().getSelectedItem();

    if(payTo.equals(NOT_CATEGORIZED.toString()) == true)
    {
      payTo = "";
    }

    return payTo;
  }

  /**
   * This method returns the chooser for selecting where the money is being paid
   * to, either an account or category.
   *
   * @return The chooser for selecting where the money is being paid to.
   */
  protected
  ElementComboBoxChooser
  getPayToChooser()
  {
    return itsPayToChooser;
  }

  /**
   * This method returns the transaction type.
   *
   * @return The transaction type.
   */
  protected
  TransactionTypeKeys
  getType()
  {
    return itsType;
  }

  /**
   * This method sets whether or not the transaction is being paid out to
   * multiple categories.
   * <p>
   * <b>Note:</b> This should only apply to categories and not accounts.
   *
   * @param isSplit true or false.
   */
  protected
  void
  setPayToIsSplit(boolean isSplit)
  {
    if(isSplit == true)
    {
      getPayToChooser().removeAllItems();
      getPayToChooser().addItem(getProperty("split"));
    }
    else
    {
      getPayToChooser().displayElements(getPayToCollection());
      getPayToChooser().addNotCategorizedOption();
      getPayToChooser().setSelectedIndex(0);
    }
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of private methods.
  //////////////////////////////////////////////////////////////////////////////

  private
  void
  buildPanel()
  {
    String category = null;
    String gap = ": ";
    String payee = null;

    // Get correct category text.
    if(getType() == TRANSFER)
    {
      category = getSharedProperty("to") + gap;
    }
    else
    {
      category = getSharedProperty("category") + gap;
    }

    // Get correct payee text.
    if(getType() == EXPENSE)
    {
      payee = getSharedProperty("to") + gap;
    }
    else
    {
      payee = getSharedProperty("from") + gap;
    }

    // Build panel.
    setFill(GridBagConstraints.HORIZONTAL);
    add(getButton(NEXT), 6, 0, 1, 1, 0, 20);
    add(getButton(NEW), 0, 1, 1, 1, 0, 0);
    add(getField(DATE), 5, 1, 1, 1, 0, 20);
    add(getButton(DATE_PICKER), 6, 1, 1, 1, 0, 0);
    add(getButton(EDIT), 7, 2, 1, 1, 0, 20);
    add(getButton(SPLIT), 3, 3, 1, 1, 0, 20);
    add(getButton(ENTER), 7, 3, 1, 1, 0, 0);
    add(getButton(CANCEL), 7, 5, 1, 1, 0, 20);

    setAnchor(GridBagConstraints.EAST);
    setFill(GridBagConstraints.NONE);
    add(payee, 1, 2, 1, 1, 0, 0);
    add(category, 1, 3, 1, 1, 0, 0);
    add(getSharedProperty("notes") + gap, 1, 5, 1, 1, 0, 0);
    add(getSharedProperty("check_number") + gap, 4, 0, 1, 1, 0, 0);
    add(getSharedProperty("date") + gap, 4, 1, 1, 1, 0, 0);
    add(getSharedProperty("amount") + gap, 4, 2, 1, 1, 0, 0);

    setFill(GridBagConstraints.HORIZONTAL);
    add(getPayFromChooser(), 2, 2, 2, 1, 0, 0);
    add(getPayToChooser(), 2, 3, 1, 1, 100, 0);
    add(getField(NOTES), 2, 5, 2, 1, 0, 0);
    add(getField(CHECK_NUMBER), 5, 0, 1, 1, 0, 0);
    add(getField(AMOUNT), 5, 2, 1, 1, 0, 0);
    add(getButton(PENDING), 5, 3, 1, 1, 0, 0);

    // Aesthetic spacers.
    addEmptyCellAt(1, 6, 10);
    addEmptyCellAt(4, 4, 12);
    addEmptyCellAt(5, 5, 17);
  }

  private
  JButton
  createButton(FormButtonKeys key, String tip, ActionListener listener)
  {
    JButton button = new JButton();

    // Build Button.
    buildButton(button, key.getText(), listener, "", tip);

    if(IS_MAC == false)
    {
      button.setMnemonic(key.getText().charAt(0));
    }

    return button;
  }

  private
  void
  createButtons(ActionListener handler)
  {
    itsButtons = new AbstractButton[8];

    getButtons()[CANCEL.ordinal()] = createButton(CANCEL, null, handler);
    getButtons()[EDIT.ordinal()] = createButton(EDIT, null, handler);
    getButtons()[ENTER.ordinal()] = createButton(ENTER, null, handler);
    getButtons()[DATE_PICKER.ordinal()] = createLink(IconKeys.DATE, getSharedProperty("date_tip"), handler);
    getButtons()[NEW.ordinal()] = createButton(NEW, null, handler);
    getButtons()[NEXT.ordinal()] = createLink(IconKeys.FORM_NEXT_CHECK, getProperty("next_tip"), handler);
    getButtons()[PENDING.ordinal()] = new JCheckBox(PENDING.getText());
    getButtons()[SPLIT.ordinal()] = createButton(SPLIT, null, handler);
  }

  private
  void
  createFields()
  {
    itsFields = new JTextField[4];

    getFields()[AMOUNT.ordinal()] = createAmountFieldEditor();
    getFields()[CHECK_NUMBER.ordinal()] = createCheckNumberFieldEditor();
    getFields()[DATE.ordinal()] = createDateFieldEditor();
    getFields()[NOTES.ordinal()] = createNotesFieldEditor();
  }

  private
  Link
  createLink(IconKeys icon, String tip, ActionListener listener)
  {
    Link link = new Link();

    // Build link.
    buildButton(link, "", icon.getIcon(), listener, "", tip);

    return link;
  }

  private
  AbstractButton[]
  getButtons()
  {
    return itsButtons;
  }

  private
  JTextField[]
  getFields()
  {
    return itsFields;
  }

  private
  DataCollection
  getPayFromCollection()
  {
    return (getType() == TRANSFER) ? getAccounts() : getPayees();
  }

  private
  DataCollection
  getPayToCollection()
  {
    DataCollection collection = getExpenses();

    if(getType() == INCOME)
    {
      collection = getIncome();
    }
    else if(getType() == TRANSFER)
    {
      collection = getAccounts();
    }

    return collection;
  }

  private
  static
  String
  getProperty(String key)
  {
    return I18NHelper.getProperty("Form." + key);
  }

  private
  void
  setPayFromChooser(ElementComboBoxChooser chooser)
  {
    itsPayFromChooser = chooser;
  }

  private
  void
  setPayToChooser(ElementComboBoxChooser chooser)
  {
    itsPayToChooser = chooser;
  }

  private
  void
  setType(TransactionTypeKeys type)
  {
    itsType = type;
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  private AbstractButton[] itsButtons;
  private JTextField[] itsFields;
  private ElementComboBoxChooser itsPayFromChooser;
  private ElementComboBoxChooser itsPayToChooser;
  private TransactionTypeKeys itsType;
}
