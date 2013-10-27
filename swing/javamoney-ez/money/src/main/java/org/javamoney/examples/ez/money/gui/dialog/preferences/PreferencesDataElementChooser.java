// PreferencesDataElementChooser

package org.javamoney.examples.ez.money.gui.dialog.preferences;

import static javax.swing.tree.TreeSelectionModel.SINGLE_TREE_SELECTION;
import static org.javamoney.examples.ez.money.gui.GUIConstants.CELL_HEIGHT;
import static org.javamoney.examples.ez.money.gui.GUIConstants.COLOR_BACKGROUND_FILL;
import static org.javamoney.examples.ez.money.gui.GUIConstants.COLOR_SELECTION_BACKGROUND;
import static org.javamoney.examples.ez.money.gui.GUIConstants.COLOR_SELECTION_BORDER;
import static org.javamoney.examples.ez.money.gui.GUIConstants.COLOR_SELECTION_TEXT;
import static org.javamoney.examples.ez.money.gui.dialog.preferences.PreferencesKeys.ACCOUNTS;
import static org.javamoney.examples.ez.money.gui.dialog.preferences.PreferencesKeys.EXPENSES;
import static org.javamoney.examples.ez.money.gui.dialog.preferences.PreferencesKeys.INCOME;
import static org.javamoney.examples.ez.money.gui.dialog.preferences.PreferencesKeys.PAYEES;
import static org.javamoney.examples.ez.money.gui.dialog.preferences.PreferencesKeys.REMINDERS;
import static org.javamoney.examples.ez.money.model.DataManager.getAccounts;
import static org.javamoney.examples.ez.money.model.DataManager.getExpenses;
import static org.javamoney.examples.ez.money.model.DataManager.getIncome;
import static org.javamoney.examples.ez.money.model.DataManager.getPayees;
import static org.javamoney.examples.ez.money.model.DataManager.getReminders;

import java.util.Collection;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import org.javamoney.examples.ez.money.model.DataCollection;
import org.javamoney.examples.ez.money.model.DataElement;
import org.javamoney.examples.ez.money.model.persisted.category.Category;

/**
 * This class facilitates choosing a element from a tree for the preferences
 * panels.
 */
final
class
PreferencesDataElementChooser
extends JTree
{
  /**
   * Constructs a new chooser for the specified preferences panel.
   */
  protected
  PreferencesDataElementChooser(PreferencesKeys key)
  {
    setKey(key);

    // Customize look.
    getTreeCellRenderer().setBackgroundNonSelectionColor(COLOR_BACKGROUND_FILL);
    getTreeCellRenderer().setBackgroundSelectionColor(COLOR_SELECTION_BACKGROUND);
    getTreeCellRenderer().setBorderSelectionColor(COLOR_SELECTION_BORDER);
    getTreeCellRenderer().setTextSelectionColor(COLOR_SELECTION_TEXT);

    getTreeCellRenderer().setClosedIcon(null);
    getTreeCellRenderer().setLeafIcon(null);
    getTreeCellRenderer().setOpenIcon(null);

    getSelectionModel().setSelectionMode(SINGLE_TREE_SELECTION);

    setBackground(COLOR_BACKGROUND_FILL);
    setRootVisible(false);
    setRowHeight(CELL_HEIGHT);
    setShowsRootHandles(true);

    // Initially display all the elements and select the first one.
    displayCollectables();
    selectFirst();
  }

  /**
   * This method puts all the elements for its preferences panel into its
   * chooser.
   */
  protected
  void
  displayCollectables()
  {
    DefaultMutableTreeNode root = new DefaultMutableTreeNode("");

    setCollection(getCollectableCollection());

    // Clear.
    ((DefaultTreeModel)getModel()).setRoot(null);

    // Add.
    addCollectables(root, getCollection().getCollection());

    // Display.
    ((DefaultTreeModel)getModel()).setRoot(root);

    // Expand all rows.
    for(int len = 0; len < getRowCount(); ++len)
    {
      expandRow(len);
    }
  }

  /**
   * This method returns this chooser's element collection.
   *
   * @return This chooser's element collection.
   */
  protected
  DataCollection
  getCollection()
  {
    return itsCollection;
  }

  /**
   * This method returns this chooser's key.
   *
   * @return This chooser's key.
   */
  protected
  PreferencesKeys
  getKey()
  {
    return itsKey;
  }

  /**
   * This method returns the selected element.
   *
   * @return This method returns the selected element.
   */
  protected
  DataElement
  getSelectedElement()
  {
    DefaultMutableTreeNode node = (DefaultMutableTreeNode)getLastSelectedPathComponent();
    DataElement element = null;

    if(node != null)
    {
      element = (DataElement)node.getUserObject();
    }

    return element;
  }

  /**
   * This method returns the amount of elements that are being referenced in
   * the chooser.
   *
   * @return The amount of elements that are being referenced in the
   * chooser.
   */
  protected
  int
  length()
  {
    return getCollection().size();
  }

  /**
   * This method causes the first element in the chooser to become selected,
   * unless the chooser is empty.
   */
  protected
  void
  selectFirst()
  {
    setSelectionRow(0);
  }

  /**
   * This method selects the specified element.
   * <p>
   * <b>Note:</b> This method will not work as expected if the row that contains
   * the specified element is not expanded.
   *
   * @param element The element to select.
   */
  protected
  void
  setSelectedCollectable(DataElement element)
  {
    TreePath[] paths = getPathBetweenRows(0, getRowCount());
    int index = 0;

    // Iterate all the expanded tree paths.
    for(TreePath path : paths)
    {
      DefaultMutableTreeNode node = (DefaultMutableTreeNode)path.getLastPathComponent();
      DataElement elementAt = (DataElement)node.getUserObject();

      if(elementAt == element)
      {
        break;
      }

      ++index;
    }

    setSelectionRow(index);
  }

  /**
   * This method sets the selection row and then scrolls to that roll if
   * contained in a scroll pane.
   *
   * @param row The row to select and scroll to.
   */
  @Override
  public
  void
  setSelectionRow(int row)
  {
    super.setSelectionRow(row);

    scrollRowToVisible(row);
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of private methods.
  //////////////////////////////////////////////////////////////////////////////

  private
  void
  addCollectables(DefaultMutableTreeNode root, Collection<DataElement> collection)
  {
    for(DataElement element : collection)
    {
      DefaultMutableTreeNode leaf = new DefaultMutableTreeNode(element);
      root.add(leaf);

      if(element instanceof Category)
      {
        Category category = (Category)element;

        if(category.isGroup() == true)
        {
          addCollectables(leaf, category.getSubcategories());
        }
      }
    }
  }

  private
  DataCollection
  getCollectableCollection()
  {
    DataCollection collection = null;

    if(getKey() == ACCOUNTS)
    {
      collection = getAccounts();
    }
    else if(getKey() == EXPENSES)
    {
      collection = getExpenses();
    }
    else if(getKey() == INCOME)
    {
      collection = getIncome();
    }
    else if(getKey() == PAYEES)
    {
      collection = getPayees();
    }
    else if(getKey() == REMINDERS)
    {
      collection = getReminders();
    }

    return collection;
  }

  private
  DefaultTreeCellRenderer
  getTreeCellRenderer()
  {
    return (DefaultTreeCellRenderer)getCellRenderer();
  }

  private
  void
  setCollection(DataCollection collection)
  {
    itsCollection = collection;
  }

  private
  void
  setKey(PreferencesKeys key)
  {
    itsKey = key;
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  private DataCollection itsCollection;
  private PreferencesKeys itsKey;
}
