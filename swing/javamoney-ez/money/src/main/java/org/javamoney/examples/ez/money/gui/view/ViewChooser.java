// ViewChooser

package org.javamoney.examples.ez.money.gui.view;

import static org.javamoney.examples.ez.common.utility.ButtonHelper.buildButton;
import static org.javamoney.examples.ez.common.utility.I18NHelper.getSharedProperty;
import static org.javamoney.examples.ez.money.ApplicationThread.getFrame;
import static org.javamoney.examples.ez.money.IconKeys.VIEWS_BUDGETS;
import static org.javamoney.examples.ez.money.IconKeys.VIEWS_HOME;
import static org.javamoney.examples.ez.money.IconKeys.VIEWS_TOTALS;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JToolBar;
import javax.swing.border.MatteBorder;

import org.javamoney.examples.ez.common.gui.Link;
import org.javamoney.examples.ez.common.gui.Panel;
import org.javamoney.examples.ez.common.utility.I18NHelper;

/**
 * This class facilitates choosing between the available views.
 */
final
class
ViewChooser
extends Panel
{
  /**
   * Constructs a new view chooser.
   */
  protected
  ViewChooser()
  {
    createLinks();

    // Build panel.
    setFill(GridBagConstraints.BOTH);
    add(createToolBarPanel(), 0, 0, 1, 1, 100, 100);

    setBorder(new MatteBorder(0, 0, 1, 0, Color.GRAY));
    setInsets(new Insets(10, 15, 10, 15));
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of private methods.
  //////////////////////////////////////////////////////////////////////////////

  private
  void
  createLinks()
  {
    ActionHandler handler = new ActionHandler();

    itsLinks = new Link[3];

    getLinks()[BUDGETS] = new Link();
    getLinks()[HOME] = new Link();
    getLinks()[TOTALS] = new Link();

    // Build links.
    buildButton(getLinks()[BUDGETS], getSharedProperty("budgets"),
        VIEWS_BUDGETS.getIcon(), handler);

    buildButton(getLinks()[HOME], getProperty("home"), VIEWS_HOME.getIcon(),
        handler);

    buildButton(getLinks()[TOTALS], getProperty("totals"),
        VIEWS_TOTALS.getIcon(), handler);
  }

  private
  Panel
  createToolBarPanel()
  {
    Panel panel = new Panel();
    JToolBar toolBar = new JToolBar();
    Dimension separator = new Dimension(50, 10);

    // Build tool bar.
    toolBar.setFloatable(false);

    toolBar.add(getLinks()[HOME]);
    toolBar.addSeparator(separator);
    toolBar.add(getLinks()[BUDGETS]);
    toolBar.addSeparator(separator);
    toolBar.add(getLinks()[TOTALS]);

    // Build panel.
    panel.setAnchor(GridBagConstraints.WEST);
    panel.add(toolBar, 0, 0, 1, 1, 100, 100);

    return panel;
  }

  private
  Link[]
  getLinks()
  {
    return itsLinks;
  }

  private
  static
  String
  getProperty(String key)
  {
    return I18NHelper.getProperty("ViewChooser." + key);
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
      Object source = event.getSource();

      if(source == getLinks()[BUDGETS])
      {
        getFrame().getViews().showView(ViewKeys.BUDGETS);
      }
      else if(source == getLinks()[HOME])
      {
        getFrame().getViews().showView(ViewKeys.HOME);
      }
      else
      {
        getFrame().getViews().showView(ViewKeys.TOTALS);
      }
    }
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  private Link[] itsLinks;

  private static final int BUDGETS = 0;
  private static final int HOME = 1;
  private static final int TOTALS = 2;
}
