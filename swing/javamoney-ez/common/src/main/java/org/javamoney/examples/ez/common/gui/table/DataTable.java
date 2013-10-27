// DataTable

package org.javamoney.examples.ez.common.gui.table;

import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;

import org.javamoney.examples.ez.common.gui.Table;

/**
 * This class facilitates using a table and the elements that the table
 * displays. This class maintains its own data list and facilitates storing and
 * accessing those elements.
 * <p>
 * Extending classes will need to implement display(), which should be where
 * they add their elements to their model that caters to the specific element
 * type.
 *
 * @param <E> The type of elements that the table will store and display.
 */
public
abstract
class
DataTable<E>
extends Table
{
  /**
   * Constructs a new table.
   *
   * @param columns The table columns.
   * @param model The table model.
   */
  public
  DataTable(String[] columns, DefaultTableModel model)
  {
    super(columns, model);

    setList(new ArrayList<E>());
  }

  /**
   * This method adds an element to the data list.
   *
   * @param element The element to add.
   */
  public
  final
  void
  add(E element)
  {
    getList().add(element);
  }

  /**
   * This method removes all elements in the data list and model.
   */
  public
  final
  void
  clear()
  {
    clearRows();
    getList().clear();
  }

  /**
   * This method is where extending classes should add their elements to their
   * model that caters to the specific element type.
   */
  public
  abstract
  void
  display();

  /**
   * This method returns an element from the data list.
   *
   * @param index The index of the element.
   *
   * @return An element from the data list.
   */
  public
  final
  E
  get(int index)
  {
    return getList().get(index);
  }

  /**
   * This method returns the amount of elements in the data list.
   *
   * @return The amount of elements in the data list.
   */
  public
  final
  int
  getElementCount()
  {
    return getList().size();
  }

  /**
   * This method returns the data list.
   *
   * @return The data list.
   */
  public
  final
  ArrayList<E>
  getList()
  {
    return itsList;
  }

  /**
   * This method returns the selected element. If more than one row is selected,
   * then the first selected row will be used to obtain the element.
   *
   * @return The selected element.
   */
  public
  final
  E
  getSelectedElement()
  {
    E element = null;
    int index = getSelectedRow();

    if(index != -1)
    {
      element = get(index);
    }

    return element;
  }

  /**
   * This method returns a list of selected elements.
   *
   * @return A list of selected elements.
   */
  public
  final
  ArrayList<E>
  getSelectedElements()
  {
    int[] rows = getSelectedRows();
    ArrayList<E> list = new ArrayList<E>(rows.length);

    for(int len = 0; len < rows.length; ++len)
    {
      list.add(get(rows[len]));
    }

    return list;
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of private methods.
  //////////////////////////////////////////////////////////////////////////////

  private
  void
  setList(ArrayList<E> list)
  {
    itsList = list;
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  private ArrayList<E> itsList;
}
