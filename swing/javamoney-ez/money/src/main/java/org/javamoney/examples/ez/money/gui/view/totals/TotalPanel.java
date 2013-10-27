// TotalPanel

package org.javamoney.examples.ez.money.gui.view.totals;

import static org.javamoney.examples.ez.common.utility.I18NHelper.getSharedProperty;

import org.javamoney.examples.ez.common.gui.Panel;

/**
 * This class is the base class for all panels that want to facilitate
 * displaying totals.
 */
class
TotalPanel
extends Panel
{
  /**
	 * 
	 */
	private static final long serialVersionUID = -8529402547589074354L;

/**
   * Constructs a new total panel.
   */
  protected
  TotalPanel()
  {
    String title = getSharedProperty("transaction_details");

    setTransactionDetailPanel(new TransactionDetailsPanel(title));
  }

  /**
   * This method returns this panel's transaction details panel.
   *
   * @return This panel's transaction details panel.
   */
  protected
  final
  TransactionDetailsPanel
  getTransactionDetailPanel()
  {
    return itsTransactionDetailPanel;
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of private methods.
  //////////////////////////////////////////////////////////////////////////////

  private
  void
  setTransactionDetailPanel(TransactionDetailsPanel panel)
  {
    itsTransactionDetailPanel = panel;
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  private TransactionDetailsPanel itsTransactionDetailPanel;
}
