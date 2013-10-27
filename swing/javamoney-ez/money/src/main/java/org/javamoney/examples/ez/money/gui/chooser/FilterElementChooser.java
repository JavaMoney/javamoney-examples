// FilterElementChooser

package org.javamoney.examples.ez.money.gui.chooser;

import static java.awt.event.MouseEvent.BUTTON1;
import static java.util.Arrays.binarySearch;
import static org.javamoney.examples.ez.money.gui.GUIConstants.CELL_HEIGHT;
import static org.javamoney.examples.ez.money.gui.GUIConstants.COLOR_BACKGROUND_FILL;
import static org.javamoney.examples.ez.money.utility.RenderHelper.setLookFor;

import java.awt.Component;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

import org.javamoney.examples.ez.money.model.DataElement;
import org.javamoney.examples.ez.money.model.dynamic.SelectableElement;
import org.javamoney.examples.ez.money.model.dynamic.total.TotalFilter;
import org.javamoney.examples.ez.money.model.persisted.category.Category;

import org.javamoney.examples.ez.common.gui.CheckBox;

/**
 * This class facilitates providing a list for selecting elements.
 */
public
final
class
FilterElementChooser
extends JList
{
  /**
   * Constructs a new chooser for the specified collection.
   *
   * @param collection The collection of elements to display.
   */
  public
  FilterElementChooser(Collection<DataElement> collection)
  {
    super(new DefaultListModel());

    setAutoscrolls(true);
    setCellRenderer(new CellRenderer());
    setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

    addElements(collection);
    setBackground(COLOR_BACKGROUND_FILL);
    setFixedCellHeight(CELL_HEIGHT);

    // Add listeners.
    addKeyListener(new KeyHandler());
    addMouseListener(new MouseHandler());
  }

  /**
   * This method returns the selected values. If all the values are selected,
   * then CategoryTotalsFilter.ALL is returned. This signals to not constrain
   * the transactions. It is the equivalent of having nothing selected.
   * <p>
   * There is an exception to this when there is only one item in the list and
   * it is selected. In this case, an array containing the one value is
   * returned. This is to ensure that all transactions are constrained to that
   * value.
   *
   * @return The selected values.
   */
  @Override
  public
  String[]
  getSelectedValues()
  {
    String[] values = TotalFilter.ALL;
    Enumeration<?> enumeration = getDefaultListModel().elements();

    if(isEnabled() == true)
    {
      ArrayList<String> list = new ArrayList<String>();
      int size = getDefaultListModel().getSize();

      // Find selected elements.
      while(enumeration.hasMoreElements() == true)
      {
        SelectableElement element = (SelectableElement)enumeration.nextElement();

        if(element.isSelected() == true)
        {
          list.add(element.toString());
        }
      }

      if(list.size() != 0)
      {
        if(list.size() != size || (list.size() == 1 && size == 1))
        {
          values = new String[list.size()];

          for(int len = 0; len < list.size(); ++len)
          {
            values[len] = list.get(len);
          }
        }
      }
    }

    return values;
  }

  /**
   * This method selects all the elements contained in the specified array.
   *
   * @param values The array of elements to select.
   */
  public
  void
  selectValues(String[] values)
  {
    int max = getDefaultListModel().getSize();

    for(int len = 0; len < max; ++len)
    {
      SelectableElement element = getElementAt(len);

      if(binarySearch(values, element.toString()) >= 0)
      {
        element.setIsSelected(true);
      }
    }
  }

  /**
   * This method sets whether or not the chooser is enabled.
   *
   * @param value true or false.
   */
  @Override
  public
  void
  setEnabled(boolean value)
  {
    super.setEnabled(value);

    clearSelection();
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of private methods.
  //////////////////////////////////////////////////////////////////////////////

  private
  void
  addElement(Object object)
  {
    getDefaultListModel().addElement(object);
  }

  private
  void
  addElements(Collection<DataElement> collection)
  {
    for(DataElement element : collection)
    {
      if(element instanceof Category)
      {
        Category category = (Category)element;

        addElement(new SelectableElement(category.getQIFName()));
        addElements(category.getSubcategories());
      }
      else
      {
        addElement(new SelectableElement(element.toString()));
      }
    }
  }

  private
  SelectableElement
  getElementAt(int index)
  {
    return (SelectableElement)getDefaultListModel().getElementAt(index);
  }

  private
  DefaultListModel
  getDefaultListModel()
  {
    return (DefaultListModel)getModel();
  }

  private
  void
  selectElement()
  {
    SelectableElement element = (SelectableElement)getSelectedValue();

    if(element != null)
    {
      element.setIsSelected(!element.isSelected());
      repaint();
    }
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of inner classes.
  //////////////////////////////////////////////////////////////////////////////

  private
  class
  CellRenderer
  extends CheckBox
  implements ListCellRenderer
  {
    public
    Component
    getListCellRendererComponent(JList list, Object value, int index,
        boolean isSelected, boolean hasFocus)
    {
      SelectableElement element = (SelectableElement)value;

      // Customize look.
      setLookFor(this, index, isSelected, false);

      setEnabled(list.isEnabled());
      setHorizontalAlignment(SwingConstants.LEFT);
      setSelected(element.isSelected());
      setText(element.toString());

      return this;
    }
  }

  private
  class
  KeyHandler
  extends KeyAdapter
  {
    @Override
    public
    void
    keyReleased(KeyEvent event)
    {
      if(event.getKeyCode() == KeyEvent.VK_SPACE)
      {
        selectElement();
      }
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
      if(event.getButton() == BUTTON1)
      {
        int xCoordinate = event.getPoint().x;

        // Simulate that only the box itself is clickable.
        if(xCoordinate > 4 && xCoordinate < 20)
        {
          selectElement();
        }
      }
    }
  }
}
