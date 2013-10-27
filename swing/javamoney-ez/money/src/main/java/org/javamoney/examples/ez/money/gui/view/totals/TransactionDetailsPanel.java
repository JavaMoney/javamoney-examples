// TransactionDetailsPanel

package org.javamoney.examples.ez.money.gui.view.totals;

import static org.javamoney.examples.ez.common.utility.BorderHelper.createTitledBorder;
import static org.javamoney.examples.ez.common.utility.ButtonHelper.buildButton;
import static org.javamoney.examples.ez.common.utility.I18NHelper.getSharedProperty;
import static org.javamoney.examples.ez.money.gui.GUIConstants.COLOR_BACKGROUND_FILL;

import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JMenuItem;

import org.javamoney.examples.ez.money.gui.table.TransactionDetailTable;
import org.javamoney.examples.ez.money.model.dynamic.total.TransactionDetail;
import org.javamoney.examples.ez.money.model.persisted.transaction.Transaction;
import org.javamoney.examples.ez.money.report.TransactionWriter;

import org.javamoney.examples.ez.common.gui.Panel;
import org.javamoney.examples.ez.common.gui.PopupMenu;
import org.javamoney.examples.ez.common.gui.ScrollPane;

/**
 * This class facilitates displaying transaction details.
 */
final
class
TransactionDetailsPanel
extends Panel
{
  /**
   * Constructs a new transactions detail panel.
   *
   * @param title The title to display in the panel's border.
   */
  public
  TransactionDetailsPanel(String title)
  {
    setTable(new TransactionDetailTable());

    buildPanel(title);

    // Add listeners.
    getTable().addMouseListener(createPopupMenu());
  }

  /**
   * This method clears the transaction details table.
   */
  public
  void
  clear()
  {
    getTable().clear();
  }

  /**
   * This method clears the transaction details table and then displays all the
   * details in the specified collection.
   *
   * @param collection The collection that contains the transaction details.
   */
  public
  void
  updateView(Collection<TransactionDetail> collection)
  {
    clear();

    for(TransactionDetail detail : collection)
    {
      getTable().add(detail);
    }

    getTable().display();
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of private methods.
  //////////////////////////////////////////////////////////////////////////////

  private
  void
  buildPanel(String title)
  {
    int height = 18;

    // Build panel.
    setFill(GridBagConstraints.BOTH);
    add(createDetailTablePanel(title), 0, 0, 1, height, 100, 100);

    // This spacer is to ensure the table has adequate height.
    addEmptyRowsAt(1, 0, height);
  }

  private
  Panel
  createDetailTablePanel(String title)
  {
    Panel panel = new Panel();
    ScrollPane scrollPane = new ScrollPane(getTable());

    // Build scroll pane.
    scrollPane.setBackground(COLOR_BACKGROUND_FILL);

    // Build panel.
    panel.setFill(GridBagConstraints.BOTH);
    panel.add(scrollPane, 0, 0, 1, 1, 100, 100);

    panel.setBorder(createTitledBorder(title, false));

    return panel;
  }

  private
  PopupMenu
  createPopupMenu()
  {
    PopupMenu menu = new PopupMenu();
    ActionHandler handler = new ActionHandler();
    JMenuItem item = new JMenuItem();

    // Build item.
    buildButton(item, getSharedProperty("print_transactions"), handler);

    // Add item to menu.
    menu.add(item);

    return menu;
  }

  private
  TransactionDetailTable
  getTable()
  {
    return itsTable;
  }

  private
  void
  setTable(TransactionDetailTable table)
  {
    itsTable = table;
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
      ArrayList<TransactionDetail> list = getTable().getSelectedElements();

      if(list.size() != 0)
      {
        Transaction[] transactions = new Transaction[list.size()];

        for(int len = 0; len < transactions.length; ++len)
        {
          transactions[len] = list.get(len).getTransaction();
        }

        TransactionWriter.generate(transactions);
      }
    }
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  private TransactionDetailTable itsTable;
}
