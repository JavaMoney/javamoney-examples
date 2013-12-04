// HomeView

package org.javamoney.examples.ez.money.gui.view;

import static org.javamoney.examples.ez.common.utility.I18NHelper.getSharedProperty;
import static org.javamoney.examples.ez.money.ApplicationThread.getFrame;
import static org.javamoney.examples.ez.money.IconKeys.HOME_BACKGROUND;
import static org.javamoney.examples.ez.money.IconKeys.HOME_SPACER;
import static org.javamoney.examples.ez.money.IconKeys.HOME_TILE_HEADER;
import static org.javamoney.examples.ez.money.IconKeys.HOME_TILE_SUMMARY;
import static org.javamoney.examples.ez.money.IconKeys.HOME_TILE_TYPE;
import static org.javamoney.examples.ez.money.model.DataManager.getAccounts;
import static org.javamoney.examples.ez.money.utility.HTMLHelper.buildImageTag;
import static org.javamoney.examples.ez.money.utility.HTMLHelper.buildLinkTag;
import static org.javamoney.examples.ez.money.utility.HTMLHelper.buildStyleTag;
import static org.javamoney.examples.ez.money.utility.HTMLHelper.createWebPage;
import static org.javamoney.examples.ez.money.utility.HTMLHelper.formatAmount;

import java.awt.GridBagConstraints;

import javax.swing.JEditorPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

import org.javamoney.examples.ez.money.gui.dialog.PreferencesDialog;
import org.javamoney.examples.ez.money.gui.dialog.preferences.PreferencesKeys;
import org.javamoney.examples.ez.money.gui.view.home.ComparisonView;
import org.javamoney.examples.ez.money.gui.view.home.RemindersView;
import org.javamoney.examples.ez.money.model.DataElement;
import org.javamoney.examples.ez.money.model.persisted.account.Account;
import org.javamoney.examples.ez.money.model.persisted.account.AccountTypeKeys;

import org.javamoney.examples.ez.common.gui.Panel;
import org.javamoney.examples.ez.common.gui.ScrollPane;
import org.javamoney.examples.ez.common.utility.I18NHelper;

/**
 * This class represents the home view, and provides an account overview for all
 * the accounts.
 */
public
final
class
HomeView
extends View
{
  /**
	 * 
	 */
	private static final long serialVersionUID = -6127763031635419778L;
/**
   * Constructs a new view.
   */
  public
  HomeView()
  {
    super(ViewKeys.HOME);

    setComparisonView(new ComparisonView());
    setRemindersView(new RemindersView());
    setWebPage(createWebPage());

    buildPanel();

    // Add listeners.
    getWebPage().addHyperlinkListener(new HyperlinkHandler());
  }

  /**
   * This method updates the reminders view.
   */
  public
  void
  updateRemindersView()
  {
    getRemindersView().updateView();
  }

  /**
   * This method updates the view by redisplaying all the available accounts.
   */
  @Override
  public
  void
  updateView()
  {
    // Reset balance summary.
    setBalanceSummary(0.0);

    getComparisonView().updateView();
    getRemindersView().updateView();
    getWebPage().setText(buildWebPage());

    // This puts the cursor in the top left position.
    getWebPage().select(0, 0);
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of private methods.
  //////////////////////////////////////////////////////////////////////////////

  private
  String
  buildLinkTagFor(Account account)
  {
    return buildLinkTag(account.getIdentifier(), account.getIdentifier());
  }

  private
  String
  buildNoActiveAccountsTable()
  {
    String text = null;

    text = "<tr background=\"" + TILE_HEADER + "\" class=header>";
    text += "<td><b>" + getProperty("no_active.description") + "</b></td>";
    text += "</tr>";

    text += "<tr>";
    text += "<td>&nbsp;</td>"; // Empty cell.
    text += "</tr>";

    text += "<tr>";
    text += "<td><u>" + getProperty("no_active.options") + "</u></td>";
    text += "</tr>";

    text += "<tr>";
    text += "<td><ul><li>" + buildLinkTag(ACTION_EDIT, getProperty("preferences")) + "</li></ul></td>";
    text += "</tr>";

    // For some reason if there is not an extra row in the table the web page
    // does not take up enough space.
    text += "<tr>";
    text += "<td>&nbsp;</td>"; // Empty cell.
    text += "</tr>";

    return text;
  }

  private
  void
  buildPanel()
  {
    // Build panel.
    setFill(GridBagConstraints.BOTH);
    add(createWebPagePanel(), 0, 0, 3, 1, 0, 100);
    addEmptyCellAt(1, 1, 4);
    add(getComparisonView(), 0, 2, 1, 1, 40, 0);
    add(getRemindersView(), 2, 2, 1, 1, 60, 0);
  }

  private
  String
  buildSummary()
  {
    String balanceSummary = "<b>" + formatAmount(getBalanceSummary()) + "</b>";
    String text = null;

    text = "<tr><td>&nbsp;</td></tr>";
    text += "<td>&nbsp;</td>";
    text += "<td align=right background=\"" + TILE_SUMMARY + "\">" + balanceSummary + "</td>";
    text += "</tr>";

    return text;
  }

  private
  String
  buildTableData(AccountTypeKeys key)
  {
    String text = "";
    double subtotal = 0.0;
    int count = 0;

    // Build the header for this type.
    text += buildTableType(key);

    for(DataElement element : getAccounts().getCollection())
    {
      Account account = (Account)element;

      if(account.isActive() == true && account.getType() == key)
      {
        double balance = account.getBalanceForUI();

        text += "<tr>";
        text += "<td>" + buildLinkTagFor(account) + "</td>";
        text += "<td align=right>" + formatAmount(balance) + "</td>";
        text += "</tr>";

        subtotal += balance;
        setBalanceSummary(getBalanceSummary() + account.getBalance().doubleValue());

        ++count;
      }
    }

    if(count > 0)
    {
      text += "<tr>";
      text += "<td>&nbsp;</td>"; // Spacer cell.

      // Display a subtotal if there is more than one account in the group.
      if(count > 1)
      {
        text += "<td align=right><b>" + formatAmount(subtotal) + "</b></td>";
      }

      text += "</tr>";
    }
    else
    {
      // If there are no accounts in the group, then do not display the group.
      text = "";
    }

    return text;
  }

  private
  String
  buildTableHeader()
  {
    String text = null;

    text = "<tr background=\"" + TILE_HEADER + "\" class=header>";
    text += "<td nowrap><b>" + getSharedProperty("accounts") + "</b></td>";
    text += "<td align=right nowrap><b>" + getSharedProperty("balance") + "</b></td>";
    text += "</tr>";

    return text;
  }

  private
  String
  buildTableType(AccountTypeKeys type)
  {
    String text = null;

    text = "<tr>";
    text += "<td background=\"" + TILE_TYPE + "\" height=24px><i>( " + type.toString() + " )</i></td>";
    text += "<td background=\"" + TILE_TYPE + "\" height=24px>&nbsp;</td>";
    text += "</tr>";

    return text;
  }

  private
  String
  buildTables()
  {
    String text = null;

    // Build a single table for all the account types.
    text = "<table width=100%>";

    if(getAccounts().hasActiveAccounts() == true)
    {
      text += buildTableHeader();
      text += buildTableData(AccountTypeKeys.DEPOSIT);
      text += buildTableData(AccountTypeKeys.CASH);
      text += buildTableData(AccountTypeKeys.CREDIT);
      text += buildSummary();
      text += buildTableSpacers();
    }
    else
    {
      text += buildNoActiveAccountsTable();
    }

    text += "</table>";

    return text;
  }

  private
  String
  buildTableSpacers()
  {
    String text = null;

    // The JRE 1.5 and 1.6 display row widths differently. By having an image as
    // a spacer instead, they can appear to be the same.
    text = "<tr>";
    text += "<td nowrap>" + buildImageTag(HOME_SPACER) + "</td>";
    text += "<td width=25%>&nbsp;</td>";
    text += "<td width=75%>&nbsp;</td>"; // Spacer cell.
    text += "</tr>";

    return text;
  }

  private
  String
  buildWebPage()
  {
    String text = null;

    text = "<html>";
    text += buildStyleTag();
    text += "<body background=\"" + HOME_BACKGROUND + "\">";
    text += buildTables();
    text += "</body>";
    text += "</html>";

    return text;
  }

  private
  Panel
  createWebPagePanel()
  {
    Panel panel = new Panel();
    ScrollPane scrollPane = new ScrollPane(getWebPage());

    // Build panel.
    panel.setFill(GridBagConstraints.BOTH);
    panel.addEmptyCellAt(0, 0);
    panel.add(scrollPane, 1, 1, 1, 1, 100, 100);
    panel.addEmptyCellAt(2, 0);

    return panel;
  }

  private
  double
  getBalanceSummary()
  {
    return itsBalanceSummary;
  }

  private
  ComparisonView
  getComparisonView()
  {
    return itsComparisonView;
  }

  private
  static
  String
  getProperty(String key)
  {
    return I18NHelper.getProperty("HomeView." + key);
  }

  private
  RemindersView
  getRemindersView()
  {
    return itsRemindersView;
  }

  private
  JEditorPane
  getWebPage()
  {
    return itsWebPage;
  }

  private
  void
  setBalanceSummary(double sum)
  {
    itsBalanceSummary = sum;
  }

  private
  void
  setComparisonView(ComparisonView panel)
  {
    itsComparisonView = panel;
  }

  private
  void
  setRemindersView(RemindersView view)
  {
    itsRemindersView = view;
  }

  private
  void
  setWebPage(JEditorPane pane)
  {
    itsWebPage = pane;
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of inner classes.
  //////////////////////////////////////////////////////////////////////////////

  private
  class
  HyperlinkHandler
  implements HyperlinkListener
  {
    public
    void
    hyperlinkUpdate(HyperlinkEvent event)
    {
      if(event.getEventType() == HyperlinkEvent.EventType.ACTIVATED)
      {
        String command = event.getDescription();

        if(command.equals(ACTION_EDIT) == true)
        {
          PreferencesDialog.showPreferencesDialog(PreferencesKeys.ACCOUNTS);
        }
        else
        {
          // Get the account reference.
          Account account = (Account)getAccounts().get(command);

          // Show the account's register.
          getFrame().getViews().openRegisterFor(account);
        }
      }
    }
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  private double itsBalanceSummary;
  private ComparisonView itsComparisonView;
  private RemindersView itsRemindersView;
  private JEditorPane itsWebPage;

  private static final String ACTION_EDIT = "[EDIT]";
  private static final String TILE_HEADER = HOME_TILE_HEADER.toString();
  private static final String TILE_SUMMARY = HOME_TILE_SUMMARY.toString();
  private static final String TILE_TYPE = HOME_TILE_TYPE.toString();
}
