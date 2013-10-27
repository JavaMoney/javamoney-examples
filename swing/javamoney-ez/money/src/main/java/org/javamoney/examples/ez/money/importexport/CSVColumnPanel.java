// CSVColumnPanel

package org.javamoney.examples.ez.money.importexport;

import static org.javamoney.examples.ez.common.utility.BorderHelper.createTitledBorder;
import static org.javamoney.examples.ez.common.utility.ButtonHelper.buildButton;
import static javax.swing.ListSelectionModel.SINGLE_SELECTION;
import static org.javamoney.examples.ez.money.ApplicationProperties.getCSVColumnOrder;
import static org.javamoney.examples.ez.money.ApplicationProperties.setCSVColumnOrder;
import static org.javamoney.examples.ez.money.IconKeys.ARROW_DOWN;
import static org.javamoney.examples.ez.money.IconKeys.ARROW_UP;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.JList;
import javax.swing.JToolBar;

import org.javamoney.examples.ez.common.gui.Link;
import org.javamoney.examples.ez.common.gui.Panel;
import org.javamoney.examples.ez.common.gui.ScrollPane;
import org.javamoney.examples.ez.common.utility.I18NHelper;

/**
 * This class facilitates providing a way to specify the column order in a CSV
 * file.
 */
final
class
CSVColumnPanel
extends Panel
{
  /**
   * Constructs a new panel.
   */
  protected
  CSVColumnPanel()
  {
    setList(new JList(new DefaultListModel()));

    addColumnsToList();

    // Build panel.
    setFill(GridBagConstraints.BOTH);
    add(new ScrollPane(getList()), 0, 0, 1, 1, 100, 100);
    add(createButtonPanel(), 0, 1, 1, 1, 0, 0);

    setBorder(createTitledBorder(getProperty("title"), false));
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of private methods.
  //////////////////////////////////////////////////////////////////////////////

  private
  void
  addColumnsToList()
  {
    for(int index : getCSVColumnOrder())
    {
      CSVColumnKeys key = CSVColumnKeys.valueOf(index);

      ((DefaultListModel)getList().getModel()).addElement(key);
    }

    getList().setSelectedIndex(0);
    getList().setSelectionMode(SINGLE_SELECTION);
  }

  private
  final
  Link
  createButton(Icon icon, String command, ActionListener listener, String tip)
  {
    Link link = new Link();

    // Build link.
    buildButton(link, icon, listener, command, tip);

    return link;
  }

  private
  Panel
  createButtonPanel()
  {
    ActionHandler handler = new ActionHandler();
    JToolBar toolBar = new JToolBar();
    Panel panel = new Panel();
    Dimension separator = new Dimension(15, 10);

    // Build tool bar.
    toolBar.setBorderPainted(false);
    toolBar.setFloatable(false);
    toolBar.setRollover(true);

    toolBar.add(createButton(ARROW_UP.getIcon(), ACTION_UP, handler,
        getProperty("up_tip")));

    toolBar.addSeparator(separator);

    toolBar.add(createButton(ARROW_DOWN.getIcon(), ACTION_DOWN, handler,
        getProperty("down_tip")));

    // Build panel.
    panel.add(toolBar, 0, 0, 1, 1, 100, 100);

    return panel;
  }

  private
  JList
  getList()
  {
    return itsList;
  }

  private
  static
  String
  getProperty(String key)
  {
    return I18NHelper.getProperty("CSVColumnPanel." + key);
  }

  private
  void
  moveColumn(String action)
  {
    DefaultListModel model = (DefaultListModel)getList().getModel();
    Object object = getList().getSelectedValue();
    int index = getList().getSelectedIndex();

    if(action.equals(ACTION_DOWN) == true)
    {
      ++index;

      if(index < model.getSize())
      {
        model.remove(index - 1);
        model.add(index, object);
        getList().setSelectedIndex(index);
        storeCSVColumnOrder();
      }
    }
    else
    {
      --index;

      if(index > -1)
      {
        model.remove(index + 1);
        model.add(index, object);
        getList().setSelectedIndex(index);
        storeCSVColumnOrder();
      }
    }
  }

  private
  void
  setList(JList list)
  {
    itsList = list;
  }

  private
  void
  storeCSVColumnOrder()
  {
    DefaultListModel model = (DefaultListModel)getList().getModel();
    int[] order = new int[CSVColumnKeys.values().length];

    for(int len = 0; len < model.getSize(); ++len)
    {
      CSVColumnKeys key = (CSVColumnKeys)model.get(len);

      order[len] = key.ordinal();
    }

    setCSVColumnOrder(order);
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
      moveColumn(event.getActionCommand());
    }
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  private JList itsList;

  private static final String ACTION_DOWN = "D";
  private static final String ACTION_UP = "U";
}
