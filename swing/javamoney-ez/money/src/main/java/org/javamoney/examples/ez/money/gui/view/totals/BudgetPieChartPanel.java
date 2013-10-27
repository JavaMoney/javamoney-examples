// BudgetPieChartPanel

package org.javamoney.examples.ez.money.gui.view.totals;

import static org.javamoney.examples.ez.common.utility.BorderHelper.createTitledBorder;
import static org.javamoney.examples.ez.common.utility.I18NHelper.getSharedProperty;
import static org.javamoney.examples.ez.money.IconKeys.PIE_CHART_BLUE;
import static org.javamoney.examples.ez.money.IconKeys.PIE_CHART_BLUE_LEGEND;
import static org.javamoney.examples.ez.money.IconKeys.PIE_CHART_GRAY;
import static org.javamoney.examples.ez.money.IconKeys.PIE_CHART_GREEN;
import static org.javamoney.examples.ez.money.IconKeys.PIE_CHART_GREEN_LEGEND;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.Image;
import java.awt.geom.Arc2D;

import javax.swing.JLabel;

import org.javamoney.examples.ez.money.locale.PercentFormat;
import org.javamoney.examples.ez.money.model.dynamic.total.Budget;

import org.javamoney.examples.ez.common.gui.Panel;
import org.javamoney.examples.ez.common.utility.I18NHelper;

/**
 * This class facilitates displaying budget details.
 */
final
class
BudgetPieChartPanel
extends Panel
{
  /**
   * This method renders the budget data to its canvas.
   *
   * @param graphics The graphics context to render to.
   */
  @Override
  public
  void
  paint(Graphics graphics)
  {
    super.paint(graphics);

    int height = 175;
    int width = height;
    int xCoord = (getBounds().width / 2) - (width / 2);
    int yCoord = 10;

    if(getBudget() != null)
    {
      if(getBudget().getAmount() != 0)
      {
        int arc = (int)(360 * getBudget().getChangePercentage());

        // Fill the pie as balance, and then paint the difference percentage.
        graphics.setClip(new Arc2D.Double(xCoord, yCoord, width, height, 0, 360, Arc2D.PIE));
        graphics.drawImage(BALANCE, xCoord, yCoord, null);

        graphics.setClip(new Arc2D.Double(xCoord, yCoord, width, height, 0, arc, Arc2D.PIE));
        graphics.drawImage(DIFFERENCE, xCoord, yCoord, null);
      }
      else
      {
        String str = getProperty("no.budget");
        int strWidth = getFontMetrics(getFont()).stringWidth(str);

        // Clear previous paints.
        graphics.setColor(getBackground());
        graphics.fillArc(xCoord, yCoord, width, height, 0, 360);

        graphics.setClip(new Arc2D.Double(xCoord, yCoord, width, height, 0, 360, Arc2D.PIE));
        graphics.drawImage(NO_DATA, xCoord, yCoord, null);

        // Center the text.
        xCoord += width / 2 - (strWidth / 2);
        yCoord += height / 2;

        graphics.setColor(Color.BLACK);
        graphics.drawString(str, xCoord, yCoord);
      }
    }
    else
    {
      String str = getProperty("select");
      int strWidth = getFontMetrics(getFont()).stringWidth(str);

      // Clear previous paints.
      graphics.setColor(getBackground());
      graphics.fillArc(xCoord, yCoord, width, height, 0, 360);

      graphics.setClip(new Arc2D.Double(xCoord, yCoord, width, height, 0, 360, Arc2D.PIE));
      graphics.drawImage(NO_DATA, xCoord, yCoord, null);

      // Center the text.
      xCoord += width / 2 - (strWidth / 2);
      yCoord += height / 2;

      graphics.setColor(Color.BLACK);
      graphics.drawString(str, xCoord, yCoord);
    }
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of protected methods.
  //////////////////////////////////////////////////////////////////////////////

  /**
   * Constructs a new budget pie chart panel.
   */
  protected
  BudgetPieChartPanel()
  {
    createLabels();

    buildPanel();
  }

  /**
   * This method clears the budget detail information.
   */
  protected
  void
  clear()
  {
    updateView(null);
  }

  /**
   * This method updates its view with the specified budget.
   *
   * @param budget The budget to obtain the data from.
   */
  protected
  void
  updateView(Budget budget)
  {
    setBudget(budget);

    updateInfo();
    repaint();
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of private methods.
  //////////////////////////////////////////////////////////////////////////////

  private
  void
  buildPanel()
  {
    int height = 14;

    // Build panel.
    // This spacer is to ensure the pie chart has adequate height.
    addEmptyRowsAt(0, 0, height);
    addEmptyCellAt(1, 0, 24);

    setAnchor(GridBagConstraints.NORTHWEST);
    setFill(GridBagConstraints.HORIZONTAL);
    add(createLegendPanel(), 0, height, 2, 1, 100, 100);
  }

  private
  void
  createLabels()
  {
    itsLabels = new JLabel[2];

    for(int index = 0; index < getLabels().length; ++index)
    {
      getLabels()[index] = new JLabel();
    }
  }

  private
  Panel
  createLegendPanel()
  {
    Panel panel = new Panel();
    String gap = ": ";

    // Build panel.
    panel.setAnchor(GridBagConstraints.WEST);
    panel.add(getSharedProperty("difference") + gap, 0, 0, 1, 1, 0, 50);
    panel.add(getSharedProperty("balance") + gap, 0, 2, 1, 1, 0, 50);
    panel.add(PIE_CHART_BLUE_LEGEND.getIcon(), 1, 0, 1, 1, 0, 0);
    panel.add(PIE_CHART_GREEN_LEGEND.getIcon(), 1, 2, 1, 1, 0, 0);
    panel.addEmptyCellAt(2, 1);
    panel.add(getLabels()[BUDGET_DIFFERENCE], 3, 0, 1, 1, 100, 0);
    panel.add(getLabels()[BUDGET_BALANCE], 3, 2, 1, 1, 0, 0);

    panel.setBorder(createTitledBorder(getSharedProperty("legend"), false));

    return panel;
  }

  private
  Budget
  getBudget()
  {
    return itsBudget;
  }

  private
  JLabel[]
  getLabels()
  {
    return itsLabels;
  }

  private
  static
  String
  getProperty(String key)
  {
    return I18NHelper.getProperty("BudgetPieChartPanel." + key);
  }

  private
  void
  setBudget(Budget budget)
  {
    itsBudget = budget;
  }

  private
  void
  updateInfo()
  {
    PercentFormat format = new PercentFormat();
    String balance = "";
    String difference = "";

    if(getBudget() != null && getBudget().getAmount() != 0)
    {
      balance = format.format(getBudget().getBalancePercentage());
      difference = format.format(getBudget().getChangePercentage());
    }

    getLabels()[BUDGET_BALANCE].setText(balance);
    getLabels()[BUDGET_DIFFERENCE].setText(difference);
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  private Budget itsBudget;
  private JLabel[] itsLabels;

  private static final int BUDGET_BALANCE = 0;
  private static final int BUDGET_DIFFERENCE = 1;

  private static final Image BALANCE = PIE_CHART_GREEN.getIcon().getImage();
  private static final Image DIFFERENCE = PIE_CHART_BLUE.getIcon().getImage();
  private static final Image NO_DATA = PIE_CHART_GRAY.getIcon().getImage();
}
