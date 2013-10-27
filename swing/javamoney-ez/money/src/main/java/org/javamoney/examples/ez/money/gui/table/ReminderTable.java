// ReminderTable

package org.javamoney.examples.ez.money.gui.table;

import static org.javamoney.examples.ez.common.utility.I18NHelper.getSharedProperty;
import static org.javamoney.examples.ez.money.utility.RenderHelper.setLookFor;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellRenderer;

import org.javamoney.examples.ez.money.IconKeys;
import org.javamoney.examples.ez.money.gui.GUIConstants;
import org.javamoney.examples.ez.money.gui.table.comparator.ReminderComparator;
import org.javamoney.examples.ez.money.gui.table.model.ReminderModel;
import org.javamoney.examples.ez.money.model.persisted.reminder.Reminder;
import org.javamoney.examples.ez.money.utility.TransactionDateHelper;

import org.javamoney.examples.ez.common.gui.table.SortedDataTable;
import org.javamoney.examples.ez.common.utility.I18NHelper;

/**
 * This class facilitates displaying reminders in a table.
 */
public
final
class
ReminderTable
extends SortedDataTable<Reminder>
{
  /**
   * Constructs a new table.
   */
  public
  ReminderTable()
  {
    super(COLUMNS, new ReminderModel(), new ReminderComparator());

    // Customize table.
    setGridColor(GUIConstants.COLOR_TABLE_GRID);
    setPreferredWidths(new int[] {145, 80, 25});
    setRowHeight(GUIConstants.CELL_HEIGHT);
    setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    setSortableColumns(new int[] {DUE_BY_COLUMN, ID_COLUMN});

    // Add listeners.
    addMouseListener(new MouseHandler());
  }

  /**
   * This method adds all its reminders into the table.
   */
  @Override
  public
  void
  display()
  {
    clearRows();
    sort();

    for(Reminder reminder : getList())
    {
      ((ReminderModel)getModel()).addRow(reminder);
    }
  }

  /**
   * This method returns the object responsible for rendering the cell at the
   * specified row and column.
   *
   * @param row The cell's row.
   * @param column The cell's column.
   *
   * @return The object responsible for rendering the cell at the specified row
   * and column.
   */
  @Override
  public
  TableCellRenderer
  getCellRenderer(int row, int column)
  {
    return RENDER_HANDLER;
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of private methods.
  //////////////////////////////////////////////////////////////////////////////

  private
  static
  String
  getProperty(String key)
  {
    return I18NHelper.getProperty("ReminderTable." + key);
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of inner classes.
  //////////////////////////////////////////////////////////////////////////////

  private
  class
  CellRenderHandler
  extends JLabel
  implements TableCellRenderer
  {
    public
    Component
    getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
        boolean hasFocus, int row, int column)
    {
      Reminder reminder = get(row);

      // Customize look.
      setLookFor(this, row, isSelected);

      setText(value.toString());

      // Icon and tool tip.
      if(column == STATUS_COLUMN)
      {
        setToolTipText(TIP);

        if(reminder.isComplete() == true)
        {
          setIcon(IconKeys.TABLE_RECONCILED.getIcon());
        }
        else if(reminder.isOverdue() == true)
        {
          setIcon(IconKeys.WARNING.getIcon());
        }
        else
        {
          setIcon(null);
        }
      }
      else
      {
        setIcon(null);
        setToolTipText(null);
      }

      // Alignment
      if(column == ID_COLUMN)
      {
        setHorizontalAlignment(SwingConstants.LEFT);
      }
      else
      {
        setHorizontalAlignment(SwingConstants.CENTER);
      }

      return this;
    }
  }

  private
  class
  MouseHandler
  extends MouseAdapter
  {
    @Override
    public
    void
    mouseClicked(MouseEvent event)
    {
      if(event.getButton() == MouseEvent.BUTTON1 && event.getClickCount() == 2)
      {
        Reminder reminder = getSelectedElement();

        if(reminder != null)
        {
          if(getColumnIndexAtX(event.getX()) == DUE_BY_COLUMN)
          {
            Date date = TransactionDateHelper.showDateDialog(reminder.getDueBy());

            if(date != null)
            {
              reminder.setDueBy(date);
              display();
            }
          }
          else if(getColumnIndexAtX(event.getX()) == STATUS_COLUMN)
          {
            reminder.setIsComplete(!reminder.isComplete());
            repaint();
          }
        }
      }
    }
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  private final CellRenderHandler RENDER_HANDLER = new CellRenderHandler();

  private static final String[] COLUMNS = {
    getProperty("obligation"),
    getSharedProperty("due_by"),
    getProperty("status")
  };

  private static final String TIP = getProperty("tip");

  /**
   * The column for the reminder's due by date.
   */
  public static final int DUE_BY_COLUMN = 1;
  /**
   * The column for the reminder's identifier.
   */
  public static final int ID_COLUMN = 0;
  /**
   * The column for the reminder's status.
   */
  public static final int STATUS_COLUMN = 2;
}
