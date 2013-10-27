// SortedDataTable

package org.javamoney.examples.ez.common.gui.table;

import static org.javamoney.examples.ez.common.CommonIconKeys.SORT_COLUMN_DOWN;
import static org.javamoney.examples.ez.common.CommonIconKeys.SORT_COLUMN_UP;
import static java.awt.Cursor.HAND_CURSOR;
import static java.awt.Cursor.getDefaultCursor;
import static java.awt.Cursor.getPredefinedCursor;
import static java.awt.event.MouseEvent.BUTTON1;
import static javax.swing.SwingConstants.CENTER;
import static javax.swing.SwingConstants.LEFT;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.Collections;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

/**
 * This class facilitates using a table and the elements that the table
 * displays. This class maintains its own data list and facilitates storing,
 * accessing, and sorting those elements. Columns that can be sorted will
 * respond to mouse clicks on the column's header, which will invoke a sort and
 * cause the table to redisplay the rows.
 *
 * @param <E> The type of elements that the table will store and display.
 */
public
abstract
class
SortedDataTable<E>
extends DataTable<E>
{
  /**
   * Constructs a new table.
   *
   * @param columns The table columns.
   * @param model The table model.
   * @param comparator The comparator that sort.
   */
  public
  SortedDataTable(String[] columns, DefaultTableModel model,
      DataTableComparator<E> comparator)
  {
    super(columns, model);

    setComparator(comparator);
    setSortableColumns(null);

    setRendererForHeaders(new SortRenderController());

    // Add listeners.
    getTableHeader().addMouseListener(new MouseController());
    getTableHeader().addMouseMotionListener(new MouseController());
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of protected methods.
  //////////////////////////////////////////////////////////////////////////////

  /**
   * This method returns the object that compares the table's data.
   *
   * @return The object that compares the table's data.
   */
  protected
  final
  DataTableComparator<E>
  getComparator()
  {
    return itsComparator;
  }

  /**
   * This method sets the columns that can be sorted. A value of null indicates
   * that all columns can be sorted.
   *
   * @param columns An array of columns.
   */
  protected
  final
  void
  setSortableColumns(int[] columns)
  {
    itsSortableColumns = columns;
  }

  /**
   * This method sorts the elements.
   */
  protected
  final
  void
  sort()
  {
    Collections.sort(getList(), getComparator());
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of private methods.
  //////////////////////////////////////////////////////////////////////////////

  private
  boolean
  canSortColumn(int column)
  {
    boolean result = false;

    if(getSortableColumns() == null)
    {
      result = true;
    }
    else
    {
      for(int len = 0; len < getSortableColumns().length; ++len)
      {
        if(column == getSortableColumns()[len])
        {
          result = true;
          break;
        }
      }
    }

    return result;
  }

  private
  int[]
  getSortableColumns()
  {
    return itsSortableColumns;
  }

  private
  void
  setComparator(DataTableComparator<E> comparator)
  {
    itsComparator = comparator;
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of inner classes.
  //////////////////////////////////////////////////////////////////////////////

  private
  class
  MouseController
  extends MouseAdapter
  implements MouseMotionListener
  {
    public
    void
    mouseDragged(MouseEvent event)
    {
      mouseEntered(event);
    }

    @Override
    public
    void
    mouseEntered(MouseEvent event)
    {
      int column = getColumnIndexAtX(event.getPoint().x);

      if(canSortColumn(column) == true)
      {
        getTableHeader().setCursor(getPredefinedCursor(HAND_CURSOR));
      }
      else
      {
        getTableHeader().setCursor(getDefaultCursor());
      }
    }

    @Override
    public
    void
    mouseExited(MouseEvent event)
    {
      getTableHeader().setCursor(getDefaultCursor());
    }

    @Override
    public
    void
    mouseClicked(MouseEvent event)
    {
      if(event.getButton() == BUTTON1)
      {
        int column = getColumnModel().getColumnIndexAtX(event.getX());

        column = getIndexForColumn(column);

        if(canSortColumn(column) == true)
        {
          // Has the column already been selected? If so, then flip the invert
          // sort switch.
          if(getComparator().getColumn() == column)
          {
            getComparator().setInvertSort(!getComparator().invertSort());
          }

          getComparator().setColumn(column);
          getTableHeader().repaint();
          display();
        }
      }
    }

    public
    void
    mouseMoved(MouseEvent event)
    {
      mouseEntered(event);
    }
  }

  private
  class
  SortRenderController
  implements TableCellRenderer
  {
    public
    Component
    getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
        boolean hasFocus, int row, int column)
    {
      JLabel label = (JLabel)getDefaultHeaderRenderer().getTableCellRendererComponent(table,
          value, isSelected, hasFocus, row, column);

      column = getIndexForColumn(column);

      label.setHorizontalAlignment(CENTER);
      label.setHorizontalTextPosition(LEFT);

      // Icon.
      if(getComparator().getColumn() == column)
      {
        if(getComparator().invertSort() == true)
        {
          label.setIcon(SORT_COLUMN_UP.getIcon());
        }
        else
        {
          label.setIcon(SORT_COLUMN_DOWN.getIcon());
        }

        label.setText("<html><b>" + label.getText() + "</b></html>");
      }

      return label;
    }
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  private DataTableComparator<E> itsComparator;
  private int[] itsSortableColumns;
}
