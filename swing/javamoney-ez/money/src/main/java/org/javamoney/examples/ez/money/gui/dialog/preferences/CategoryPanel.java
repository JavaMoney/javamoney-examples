// CategoryPanel

package org.javamoney.examples.ez.money.gui.dialog.preferences;

import static org.javamoney.examples.ez.money.KeywordKeys.NONE;
import static org.javamoney.examples.ez.money.model.DataTypeKeys.CATEGORY;
import static org.javamoney.examples.ez.money.utility.DialogHelper.buildMessage;
import static org.javamoney.examples.ez.money.utility.DialogHelper.choose;
import static org.javamoney.examples.ez.money.utility.IDHelper.MessageKeys.IN_USE;
import static org.javamoney.examples.ez.money.utility.IDHelper.MessageKeys.UNABLE_TO_REMOVE;

import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.javamoney.examples.ez.money.gui.chooser.ElementComboBoxChooser;
import org.javamoney.examples.ez.money.model.persisted.category.Category;
import org.javamoney.examples.ez.money.model.persisted.category.CategoryCollection;
import org.javamoney.examples.ez.money.utility.IDHelper;
import org.javamoney.examples.ez.money.utility.TransactionHelper;

import org.javamoney.examples.ez.common.gui.Panel;
import org.javamoney.examples.ez.common.utility.I18NHelper;

/**
 * This class facilitates managing the categories.
 */
class
CategoryPanel
extends DataElementPanel
{
  /**
   * This method updates this panel's collection.
   */
  @Override
  public
  void
  updateView()
  {
    displayCollectables();
    getChooser().selectFirst();
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of protected methods.
  //////////////////////////////////////////////////////////////////////////////

  /**
   * Constructs a new preferences panel with the specified key.
   *
   * @param key The panel's key.
   */
  protected
  CategoryPanel(PreferencesKeys key)
  {
    super(key);

    buildPanel();
  }

  /**
   * This method prompts the user for a new unique identifier.
   */
  @Override
  protected
  void
  edit()
  {
    Category category = (Category)getChooser().getSelectedElement();
    String identifier = IDHelper.promptForEdit(CATEGORY, category.getIdentifier());

    if(identifier != null)
    {
      CategoryCollection collection = (CategoryCollection)getCollection();
      String oldQIF = category.getQIFName(); // Store for mass update.
      boolean result = collection.changeIdentifier(category.getGroup(), category, identifier);

      if(result == false)
      {
        // It is safe to assume it already existed.
        if((result = IDHelper.confirmMerge()) == true)
        {
          Category group = category.getGroup();

          collection.remove(category);
          category = (Category)collection.getFromGroup(group, identifier);
        }
      }

      if(result == true)
      {
        // Update all transactions, the view, and select the edited element.
        TransactionHelper.massUpdate(getMassUpdateFieldKey(), oldQIF, category.getQIFName());
        displayCollectables();
        getChooser().setSelectedCollectable(category);
      }
    }
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of private methods.
  //////////////////////////////////////////////////////////////////////////////

  private
  void
  add()
  {
    String identifier = IDHelper.promptForAdd(CATEGORY);

    if(identifier != null)
    {
      Category category = new Category(identifier);

      if(getCollection().add(category) == true)
      {
        // Update view and select the new element.
        displayCollectables();
        getChooser().setSelectedCollectable(category);
      }
      else
      {
        // It is safe to assume it already existed.
        IDHelper.showMessage(IN_USE, CATEGORY);
      }
    }
  }

  private
  void
  addSubcategory()
  {
    String identifier = IDHelper.promptForAdd(CATEGORY);

    if(identifier != null)
    {
      CategoryCollection collection = (CategoryCollection)getCollection();
      Category group = (Category)getChooser().getSelectedElement();
      Category category = new Category(identifier);

      if(collection.addToGroup(group, category) == true)
      {
        // Update view and select the new element.
        displayCollectables();
        getChooser().setSelectedCollectable(group);
      }
      else
      {
        // It is safe to assume it already existed.
        IDHelper.showMessage(IN_USE, CATEGORY);
      }
    }
  }

  private
  void
  buildPanel()
  {
    ActionHandler handler = new ActionHandler();

    // Build panel.
    setFill(GridBagConstraints.BOTH);
    add(createChooserPanel(handler), 0, 0, 1, 1, 100, 100);
    add(new BudgetPanel(getChooser()), 0, 1, 1, 1, 0, 0);
    add(createButtonPanel(handler), 0, 2, 1, 1, 0, 0);
  }

  private
  void
  changeGroup()
  {
    Object item = getGroup();

    if(item != null)
    {
      CategoryCollection collection = (CategoryCollection)getCollection();
      Category category = (Category)getChooser().getSelectedElement();
      String qif = category.getQIFName(); // Store for mass update.
      boolean result = false;

      // Is it a category or the option for none?
      if(item instanceof Category)
      {
        Category group = (Category)item;

        if(category.isInGroup(group) == false && category.isGroupFor(group) == false)
        {
          // Remove it from its current group and add it to the new group.
          collection.remove(category);
          collection.addToGroup(group, category);
          result = true;
        }
      }
      else if(category.hasGroup() == true)
      {
        // Remove it from its current group and make it a top-level category.
        collection.remove(category);
        collection.add(category);
        result = true;
      }

      if(result == true)
      {
        // Update all transactions.
        TransactionHelper.massUpdate(getMassUpdateFieldKey(), qif, category.getQIFName());

        displayCollectables();
        getChooser().setSelectedCollectable(category);
      }
    }
  }

  private
  Panel
  createGroupPanel(ElementComboBoxChooser chooser)
  {
    Panel panel = new Panel();
    String message = buildMessage(getProperty("group.title"),
        getProperty("group.description"));

    chooser.addNoneOption();

    // Build panel.
    panel.setAnchor(GridBagConstraints.WEST);
    panel.add(message, 0, 0, 1, 1, 100, 50);

    // This spacer is to ensure enough width for the combo box's elements.
    panel.addEmptyCellAt(1, 0, 5);

    panel.setFill(GridBagConstraints.HORIZONTAL);
    panel.add(chooser, 0, 1, 2, 1, 100, 50);

    return panel;
  }

  private
  void
  displayCollectables()
  {
    getChooser().displayCollectables();

    enableLinks();
    showProperChooserPanel();
  }

  private
  Object
  getGroup()
  {
    ElementComboBoxChooser chooser = new ElementComboBoxChooser(getCollection());
    Object group = null;
    String item = null;

    if(choose(createGroupPanel(chooser)) == true)
    {
      item = chooser.getSelectedItem();

      if(item.equals(NONE.toString()) == true)
      {
        group = NONE;
      }
      else
      {
        group = ((CategoryCollection)getCollection()).getCategoryFromQIF(item);
      }
    }

    return group;
  }

  private
  TransactionHelper.MassUpdateFieldKeys
  getMassUpdateFieldKey()
  {
    TransactionHelper.MassUpdateFieldKeys key = null;

    if(getKey() == PreferencesKeys.EXPENSES)
    {
      key = TransactionHelper.MassUpdateFieldKeys.EXPENSE;
    }
    else
    {
      key = TransactionHelper.MassUpdateFieldKeys.INCOME;
    }

    return key;
  }

  private
  static
  String
  getProperty(String key)
  {
    return I18NHelper.getProperty("CategoryPanel." + key);
  }

  private
  void
  remove()
  {
    if(IDHelper.confirmRemoval(CATEGORY) == true)
    {
      CategoryCollection collection = (CategoryCollection)getCollection();
      Category category = (Category)getChooser().getSelectedElement();
      Category group = category.getGroup(); // Store for mass update.
      String oldQIF = category.getQIFName(); // Store for mass update.

      if(collection.remove(category) == true)
      {
        String newQIF = "";

        if(group != null)
        {
          newQIF = group.getQIFName();
        }

        // Update all transactions, the view, and select the first element.
        TransactionHelper.massUpdate(getMassUpdateFieldKey(), oldQIF, newQIF);
        displayCollectables();
        getChooser().selectFirst();
      }
      else
      {
        IDHelper.showMessage(UNABLE_TO_REMOVE, CATEGORY);
      }
    }
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

      if(command.equals(ACTION_ADD) == true)
      {
        add();
      }
      else if(command.equals(ACTION_ADD_SUB) == true)
      {
        addSubcategory();
      }
      else if(command.equals(ACTION_CHANGE_GROUP) == true)
      {
        changeGroup();
      }
      else if(command.equals(ACTION_EDIT) == true)
      {
        edit();
      }
      else if(command.equals(ACTION_REMOVE) == true)
      {
        remove();
      }
    }
  }
}
