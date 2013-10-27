// TransferTotalPanel

package org.javamoney.examples.ez.money.gui.view.totals;

import static org.javamoney.examples.ez.money.gui.GUIConstants.COLOR_BACKGROUND_FILL;

import java.awt.GridBagConstraints;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.javamoney.examples.ez.money.gui.table.TransferTotalTable;
import org.javamoney.examples.ez.money.model.dynamic.total.TransferTotal;

import org.javamoney.examples.ez.common.gui.Panel;
import org.javamoney.examples.ez.common.gui.ScrollPane;

/**
 * This class facilitates viewing transfer totals.
 */
final
class
TransferTotalPanel
extends TotalPanel
{
  /**
   * Constructs a new transfer total panel.
   */
  protected
  TransferTotalPanel()
  {
    setPieChartPanel(new TransferTotalPieChartPanel());
    setTable(new TransferTotalTable());

    buildPanel();
  }

  /**
   * This method clears all the transfer total details.
   */
  protected
  void
  clear()
  {
    getPieChartPanel().clear();
    getTable().clear();
    getTransactionDetailPanel().clear();
  }

  /**
   * This method returns the table that maintains this panel's transfer totals.
   *
   * @return The table that maintains this panel's transfer totals.
   */
  protected
  TransferTotalTable
  getTable()
  {
    return itsTable;
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of private methods.
  //////////////////////////////////////////////////////////////////////////////

  private
  void
  buildPanel()
  {
    // Build panel.
    setFill(GridBagConstraints.BOTH);
    add(createTablePanel(), 0, 0, 1, 1, 100, 100);
    add(getPieChartPanel(), 1, 0, 1, 4, 0, 0);
    addEmptyRowsAt(0, 1, 2);
    add(getTransactionDetailPanel(), 0, 3, 1, 1, 0, 0);

    // Add listeners.
    getTable().getSelectionModel().addListSelectionListener(new SelectionHandler());
  }

  private
  Panel
  createTablePanel()
  {
    Panel panel = new Panel();
    ScrollPane scrollPane = new ScrollPane(getTable());

    // Build scroll pane.
    scrollPane.setBackground(COLOR_BACKGROUND_FILL);

    // Build panel.
    panel.setFill(GridBagConstraints.BOTH);
    panel.add(scrollPane, 0, 0, 1, 1, 100, 100);
    panel.addEmptyCellAt(1, 0);

    return panel;
  }

  private
  TransferTotalPieChartPanel
  getPieChartPanel()
  {
    return itsPieChartPanel;
  }

  private
  void
  setPieChartPanel(TransferTotalPieChartPanel panel)
  {
    itsPieChartPanel = panel;
  }

  private
  void
  setTable(TransferTotalTable table)
  {
    itsTable = table;
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of inner classes.
  //////////////////////////////////////////////////////////////////////////////

  private
  class
  SelectionHandler
  implements ListSelectionListener
  {
    public
    void
    valueChanged(ListSelectionEvent event)
    {
      if(event.getValueIsAdjusting() == false && getTable().getSelectedRow() != -1)
      {
        TransferTotal total = getTable().get(getTable().getSelectedRow());

        getPieChartPanel().updateView(total);
        getTransactionDetailPanel().updateView(total.getTransactionDetails());
      }
    }
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  private TransferTotalPieChartPanel itsPieChartPanel;
  private TransferTotalTable itsTable;
}
