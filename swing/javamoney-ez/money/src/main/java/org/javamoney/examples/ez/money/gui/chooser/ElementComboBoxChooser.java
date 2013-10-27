// ElementComboBoxChooser

package org.javamoney.examples.ez.money.gui.chooser;

import static org.javamoney.examples.ez.money.KeywordKeys.NONE;
import static org.javamoney.examples.ez.money.KeywordKeys.NOT_CATEGORIZED;
import static org.javamoney.examples.ez.money.utility.RenderHelper.setLookFor;

import java.awt.Component;
import java.util.Collection;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

import org.javamoney.examples.ez.money.model.DataCollection;
import org.javamoney.examples.ez.money.model.DataElement;
import org.javamoney.examples.ez.money.model.persisted.account.Account;
import org.javamoney.examples.ez.money.model.persisted.category.Category;
import org.javamoney.examples.ez.money.model.persisted.category.CategoryCollection;

import org.javamoney.examples.ez.common.gui.ComboBox;

/**
 * This class facilitates choosing a element from a combo box.
 */
public
class
ElementComboBoxChooser
extends ComboBox
{
  /**
   * Constructs a new chooser that will reference the selected collection.
   *
   * @param collection The collection this chooser will reference.
   */
  public
  ElementComboBoxChooser(DataCollection collection)
  {
    setAllowInactiveAccounts(true);
    setCollection(collection);

    displayElements();

    setMaximumRowCount(8);
    setRenderer(DEFAULT_RENDERER);

    // Add listeners.
    addPopupMenuListener(new PopupMenuHandler());
  }

  /**
   * This method returns true if the chooser can display inactive accounts,
   * otherwise false.
   *
   * @return true or false.
   */
  public
  boolean
  allowInactiveAccounts()
  {
    return itsAllowInactiveAccounts;
  }

  /**
   * This method inserts an option for none at the top of the list.
   */
  public
  final
  void
  addNoneOption()
  {
    insertItemAt(NONE.toString(), 0);
    setSelectedIndex(0);
  }

  /**
   * This method inserts an option for not categorized at the top of the list.
   */
  public
  final
  void
  addNotCategorizedOption()
  {
    insertItemAt(NOT_CATEGORIZED.toString(), 0);
    setSelectedIndex(0);
  }

  /**
   * This method puts all the elements from its collection into its
   * displayed list.
   */
  public
  final
  void
  displayElements()
  {
    displayElements(getCollection());
  }

  /**
   * This method puts all the elements from the specified collection into
   * its displayed list.
   *
   * @param collection The collection this chooser will reference.
   */
  public
  final
  void
  displayElements(DataCollection collection)
  {
    removeAllItems();
    setCollection(collection);

    if(collection != null)
    {
      addElements(getCollection().getCollection());
    }
  }

  /**
   * This method returns the collection currently displayed in the chooser.
   *
   * @return The collection currently displayed in the chooser.
   */
  public
  DataCollection
  getCollection()
  {
    return itsCollection;
  }

  /**
   * This method returns the selected item.
   *
   * @return The selected item.
   */
  @Override
  public
  final
  String
  getSelectedItem()
  {
    return (String)super.getSelectedItem();
  }

  /**
   * This method sets whether or not the chooser can display inactive accounts.
   *
   * @param value true or false.
   */
  public
  void
  setAllowInactiveAccounts(boolean value)
  {
    itsAllowInactiveAccounts = value;
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of private methods.
  //////////////////////////////////////////////////////////////////////////////

  private
  void
  addElements(Collection<DataElement> collection)
  {
    for(DataElement element : collection)
    {
      if(element instanceof Category)
      {
        Category category = (Category)element;

        addItem(category.getQIFName());

        // Recursively add category's children.
        if(category.isGroup() == true)
        {
          addElements(category.getSubcategories());
        }
      }
      else if(element instanceof Account)
      {
        if(((Account)element).isActive() == true || allowInactiveAccounts() == true)
        {
          addItem(element.getIdentifier());
        }
      }
      else
      {
        addItem(element.getIdentifier());
      }
    }
  }

  private
  String
  getTextForCategory(Category category)
  {
    Category group = category.getGroup();
    String text = "";

    // Build the indentation.
    while(group != null)
    {
      text += INDENT;
      group = group.getGroup();
    }

    // Build the name.
    if(category.hasGroup() == true)
    {
      text += ": " + category.getIdentifier();
    }
    else
    {
      text += category.getIdentifier();
    }

    return text;
  }

  private
  String
  getTextForCategory(String qif)
  {
    Category category = ((CategoryCollection)getCollection()).getCategoryFromQIF(qif);

    if(category != null)
    {
      qif = getTextForCategory(category);
    }

    return qif;
  }

  private
  void
  setCollection(DataCollection collection)
  {
    itsCollection = collection;
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of inner classes.
  //////////////////////////////////////////////////////////////////////////////

  final
  class
  CustomCellRenderHandler
  extends JLabel
  implements ListCellRenderer
  {
    public
    Component
    getListCellRendererComponent(JList list, Object item, int row,
        boolean isSelected, boolean hasFocus)
    {
      if(item != null)
      {
        // Customize look.
        setLookFor(this, row, isSelected);

        if(getCollection() instanceof CategoryCollection)
        {
          setText(getTextForCategory(item.toString()));
        }
        else
        {
          setText(item.toString());
        }
      }

      return this;
    }
  }

  private
  class
  PopupMenuHandler
  implements PopupMenuListener
  {
    public
    void
    popupMenuCanceled(PopupMenuEvent event)
    {
     setRenderer(DEFAULT_RENDERER);
    }

    public
    void
    popupMenuWillBecomeInvisible(PopupMenuEvent event)
    {
      setRenderer(DEFAULT_RENDERER);
    }

    public
    void
    popupMenuWillBecomeVisible(PopupMenuEvent event)
    {
      setRenderer(CUSTOM_RENDERER);
    }
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  private boolean itsAllowInactiveAccounts;
  private DataCollection itsCollection;

  private static final String INDENT = "            "; // 12 spaces.

  private final CustomCellRenderHandler CUSTOM_RENDERER = new CustomCellRenderHandler();
  private final DefaultListCellRenderer DEFAULT_RENDERER = new DefaultListCellRenderer();
}
