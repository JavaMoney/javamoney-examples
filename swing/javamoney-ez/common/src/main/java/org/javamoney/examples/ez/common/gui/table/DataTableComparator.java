// DataTableComparator

package org.javamoney.examples.ez.common.gui.table;

import java.util.Comparator;

/**
 * This class facilitates sorting elements in a table.
 *
 * @param <E> The type of elements that will be sorted.
 */
public
abstract
class
DataTableComparator<E>
implements Comparator<E>
{
  /**
   * Constructs a new comparator.
   *
   * @param column The column to initially sort.
   */
  public
  DataTableComparator(int column)
  {
    setColumn(column);
  }

  /**
   * This implementation specific method returns the result of comparing two
   * elements.
   *
   * @param element1 The first element.
   * @param element2 The second element.
   *
   * @return The result of comparing two elements.
   */
  public
  abstract
  int
  compare(E element1, E element2);

  /**
   * This method returns the column to sort data by.
   *
   * @return The column to sort data by.
   */
  public
  final
  int
  getColumn()
  {
    return itsColumn;
  }

  /**
   * This method returns true if the sort should be inverted, otherwise false.
   *
   * @return true or false.
   */
  public
  final
  boolean
  invertSort()
  {
    return itsInvertSort;
  }

  /**
   * This method sets the column to sort data by.
   *
   * @param column The column to sort data by.
   */
  public
  final
  void
  setColumn(int column)
  {
    itsColumn = column;
  }

  /**
   * This method sets wether or not the sort should be inverted.
   *
   * @param value true or false.
   */
  public
  final
  void
  setInvertSort(boolean value)
  {
    itsInvertSort = value;
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  private int itsColumn;
  private boolean itsInvertSort;
}
