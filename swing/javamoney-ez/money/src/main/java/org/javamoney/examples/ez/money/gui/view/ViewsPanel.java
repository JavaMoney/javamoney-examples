// ViewsPanel

package org.javamoney.examples.ez.money.gui.view;

import static org.javamoney.examples.ez.money.gui.view.ViewKeys.BUDGETS;
import static org.javamoney.examples.ez.money.gui.view.ViewKeys.HOME;
import static org.javamoney.examples.ez.money.gui.view.ViewKeys.REGISTER;
import static org.javamoney.examples.ez.money.gui.view.ViewKeys.TOTALS;
import static org.javamoney.examples.ez.money.model.DataManager.getAccounts;

import java.awt.CardLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JPanel;

import org.javamoney.examples.ez.money.model.persisted.account.Account;

import org.javamoney.examples.ez.common.gui.Panel;

/**
 * This class facilitates managing the views available to the user.
 */
public
final
class
ViewsPanel
extends Panel
{
  /**
   * Constructs a new views panel.
   */
  public
  ViewsPanel()
  {
    setCardPanel(new JPanel(new CardLayout()));
    setView(null);
    setViewChooser(new ViewChooser());

    createViews();

    // Put the views into the cards.
    for(ViewKeys key : ViewKeys.values())
    {
      getCardPanel().add(getView(key), key.toString());
    }

    // Build panel.
    setFill(GridBagConstraints.BOTH);
    add(getViewChooser(), 0, 0, 1, 1, 100, 0);
    add(getCardPanel(), 0, 1, 1, 1, 0, 100);

    setInsets(new Insets(0, 10, 10, 10));
  }

  /**
   * This method returns the view for the specified key.
   *
   * @param key The key for obtaining a view.
   *
   * @return The view for the specified key.
   */
  public
  View
  getView(ViewKeys key)
  {
    return getViews()[key.ordinal()];
  }

  /**
   * This method sets the current view to the register view, which will be
   * updated for the specified account.
   *
   * @param account The account to display in the register view.
   */
  public
  void
  openRegisterFor(Account account)
  {
    RegisterView view = (RegisterView)getView(REGISTER);

    // Set the account the register will reference prior to updating it.
    view.setAccount(account);

    if(getView() == view.getKey())
    {
      view.updateView();
    }
    else
    {
      showView(view.getKey());
    }
  }

  /**
   * This method shows the specified view.
   *
   * @param view The view to show.
   */
  public
  void
  showView(ViewKeys view)
  {
    if(getView() != view)
    {
      setView(view);
      updateView();

      showCard(getView().toString());
    }
  }

  /**
   * This method updates the current view.
   */
  public
  void
  updateView()
  {
    getView(getView()).updateView();
    getViewChooser().setEnabled(getAccounts().size() != 0);

    if(getView() == REGISTER)
    {
      RegisterView view = (RegisterView)getView(REGISTER);

      // Was the account deleted?
      if(view.hasAccount() == false)
      {
        showView(HOME);
      }
    }
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of private methods.
  //////////////////////////////////////////////////////////////////////////////

  private
  void
  createViews()
  {
    itsViews = new View[ViewKeys.values().length];

    getViews()[BUDGETS.ordinal()] = new BudgetsView();
    getViews()[HOME.ordinal()] = new HomeView();
    getViews()[REGISTER.ordinal()] = new RegisterView();
    getViews()[TOTALS.ordinal()] = new TotalsView();
  }

  private
  JPanel
  getCardPanel()
  {
    return itsCardPanel;
  }

  private
  ViewKeys
  getView()
  {
    return itsView;
  }

  private
  ViewChooser
  getViewChooser()
  {
    return itsViewChooser;
  }

  private
  View[]
  getViews()
  {
    return itsViews;
  }

  private
  void
  setCardPanel(JPanel panel)
  {
    itsCardPanel = panel;
  }

  private
  void
  setView(ViewKeys key)
  {
    itsView = key;
  }

  private
  void
  setViewChooser(ViewChooser chooser)
  {
    itsViewChooser = chooser;
  }

  private
  void
  showCard(String card)
  {
    ((CardLayout)getCardPanel().getLayout()).show(getCardPanel(), card);
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  private JPanel itsCardPanel;
  private ViewKeys itsView;
  private ViewChooser itsViewChooser;
  private View[] itsViews;
}
