// RegisterBalancePanel

package org.javamoney.examples.ez.money.gui.view.register;

import static org.javamoney.examples.ez.common.utility.I18NHelper.getSharedProperty;
import static org.javamoney.examples.ez.money.model.persisted.account.AccountTypeKeys.CREDIT;
import static org.javamoney.examples.ez.money.utility.HTMLHelper.formatAmount;

import java.awt.GridBagConstraints;

import javax.swing.JLabel;

import org.javamoney.examples.ez.money.model.persisted.account.Account;

import org.javamoney.examples.ez.common.gui.Panel;
import org.javamoney.examples.ez.common.utility.I18NHelper;

/**
 * This class facilitates displaying the account's balances.
 */
final
class
RegisterBalancePanel
extends Panel
{
  /**
   * Constructs a new register balance panel.
   */
  protected
  RegisterBalancePanel()
  {
    setLabel(new JLabel());

    // Build panel.
    setAnchor(GridBagConstraints.WEST);
    add(getLabel(), 0, 0, 1, 1, 100, 100);

    // This spacer prevents shifting.
    addEmptyCellAt(0, 1, 55);
  }

  /**
   * This method displays the account's balances.
   *
   * @param account The account to display the balances for.
   * @param pending The amount total of transactions that is pending.
   */
  protected
  void
  displayFor(Account account, double pending)
  {
    if(account.getType() == CREDIT)
    {
      pending = -pending;

      displayBalance(account.getBalanceForUI(), pending);
    }
    else
    {
      displayBalance(account.getBalance(), pending);
    }
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of private methods.
  //////////////////////////////////////////////////////////////////////////////

  private
  void
  displayBalance(double balance, double pending)
  {
    String balanceText = null;

    // Build text.
    balanceText = "<html>";
    balanceText += "&nbsp;<b>" + getSharedProperty("balance") + ":</b> ";
    balanceText += formatAmount(balance);

    if(pending != 0.0)
    {
      balanceText += "&nbsp;&nbsp;<b>~&nbsp;&nbsp;";
      balanceText += getProperty("actual");
      balanceText += ":</b> " + formatAmount(balance - pending);
    }

    balanceText += "</html>";

    getLabel().setText(balanceText);
  }

  private
  JLabel
  getLabel()
  {
    return itsLabel;
  }

  private
  static
  String
  getProperty(String key)
  {
    return I18NHelper.getProperty("RegisterBalancePanel." + key);
  }

  private
  void
  setLabel(JLabel label)
  {
    itsLabel = label;
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  private JLabel itsLabel;
}
