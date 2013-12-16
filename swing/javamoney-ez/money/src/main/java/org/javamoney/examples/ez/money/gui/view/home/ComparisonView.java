// ComparisonView

package org.javamoney.examples.ez.money.gui.view.home;

import static org.javamoney.examples.ez.common.utility.BorderHelper.createTitledBorder;
import static org.javamoney.examples.ez.common.utility.DateHelper.getStartOfCurrentMonth;
import static org.javamoney.examples.ez.common.utility.I18NHelper.getSharedProperty;
import static org.javamoney.examples.ez.money.ApplicationProperties.UI_CURRENCY_FORMAT;
import static org.javamoney.examples.ez.money.IconKeys.PIE_CHART_BLUE;
import static org.javamoney.examples.ez.money.IconKeys.PIE_CHART_BLUE_LEGEND;
import static org.javamoney.examples.ez.money.IconKeys.PIE_CHART_GRAY;
import static org.javamoney.examples.ez.money.IconKeys.PIE_CHART_RED;
import static org.javamoney.examples.ez.money.IconKeys.PIE_CHART_RED_LEGEND;
import static org.javamoney.examples.ez.money.model.DataManager.getAccounts;
import static org.javamoney.examples.ez.money.utility.TransactionDateHelper.isOnOrAfter;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.Image;
import java.awt.geom.Arc2D;

import javax.swing.JLabel;

import org.javamoney.examples.ez.money.locale.PercentFormat;
import org.javamoney.examples.ez.money.model.DataElement;
import org.javamoney.examples.ez.money.model.persisted.account.Account;
import org.javamoney.examples.ez.money.model.persisted.transaction.Transaction;
import org.javamoney.examples.ez.money.utility.TransactionHelper;

import org.javamoney.examples.ez.common.gui.Panel;
import org.javamoney.examples.ez.common.utility.I18NHelper;

/**
 * This class provides a very basic comparison view of expenses versus income
 * for the current month.
 */
public
final
class
ComparisonView
extends Panel
{
  /**
	 * 
	 */
	private static final long serialVersionUID = 1810428160527929576L;
/**
   * Constructs a new comparison  view.
   */
  public
  ComparisonView()
  {
    setTotals(new double[2]);

    createLabels();

    buildPanel();
  }

  /**
   * This method renders the view specific data to its canvas.
   *
   * @param graphics The graphics context to render to.
   */
  @Override
  public
  void
  paint(Graphics graphics)
  {
    super.paint(graphics);

    double total = getTotals()[MONTH_EXPENSE] + getTotals()[MONTH_INCOME];
    int height = 174;
    int width = height;
    int xCoord = 200;
    int yCoord = 15;

    if(total != 0.0)
    {
      int arc = (int)(360 * (getTotals()[MONTH_EXPENSE] / total));

      // Fill the pie as income, and then paint the expenses.
      graphics.setClip(new Arc2D.Double(xCoord, yCoord, width, height, 0, 360, Arc2D.PIE));
      graphics.drawImage(INCOME, xCoord, yCoord, null);

      graphics.setClip(new Arc2D.Double(xCoord, yCoord, width, height, 0, arc, Arc2D.PIE));
      graphics.drawImage(EXPENSE, xCoord, yCoord, null);
    }
    else
    {
      String str = getProperty("no_data");
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

  /**
   * This method updates the view.
   */
  public
  void
  updateView()
  {
    resetTotals();

    // Iterate the accounts and total up the transactions.
    for(DataElement element : getAccounts().getCollection())
    {
      Account account = (Account)element;

      if(account.isActive() == true)
      {
        for(Transaction trans : account.getTransactions())
        {
          addToTotals(trans);
        }
      }
    }

    updateLabels();
    repaint();
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of private methods.
  //////////////////////////////////////////////////////////////////////////////

  private
  void
  addToTotals(Transaction trans)
  {
    if(TransactionHelper.isTransfer(trans) == false)
    {
      double amount = Math.abs(trans.getAmount().getNumber().doubleValue());
      int monthIndex = MONTH_EXPENSE;

      if(TransactionHelper.isIncome(trans) == true)
      {
        monthIndex = MONTH_INCOME;
      }

      if(isOnOrAfter(trans, getStartOfCurrentMonth()) == true)
      {
        getTotals()[monthIndex] += amount;
      }
    }
  }

  private
  void
  buildPanel()
  {
    int height = 12;

    // Build panel.
    // This spacer is to ensure sufficient height for the pie chart.
    addEmptyRowsAt(0, 0, height);

    setAnchor(GridBagConstraints.SOUTHWEST);
    add(createLegendPanel(), 1, 0, 1, height, 100, 100);

    setBorder(createTitledBorder(getProperty("comparison"), false));
  }

  private
  void
  createLabels()
  {
    itsLabels = new JLabel[2];

    for(int len = 0; len < getLabels().length; ++len)
    {
      getLabels()[len] = new JLabel();
    }
  }

  private
  Panel
  createLegendPanel()
  {
    Panel panel = new Panel();
    String gap = ": ";

    // Build panel.
    panel.setAnchor(GridBagConstraints.EAST);
    panel.add(getSharedProperty("income") + gap, 0, 0, 1, 1, 0, 50);
    panel.addEmptyCellAt(2, 1);
    panel.add(getSharedProperty("expenses") + gap, 0, 2, 1, 1, 0, 50);
    panel.addEmptyCellAt(2, 3);

    panel.setAnchor(GridBagConstraints.CENTER);
    panel.add(PIE_CHART_BLUE_LEGEND.getIcon(), 1, 0, 1, 1, 0, 0);
    panel.add(PIE_CHART_RED_LEGEND.getIcon(), 1, 2, 1, 1, 0, 0);

    panel.setAnchor(GridBagConstraints.WEST);
    panel.add(getLabels()[MONTH_INCOME], 3, 0, 1, 1, 100, 0);
    panel.addEmptyCellAt(3, 1, 8);
    panel.add(getLabels()[MONTH_EXPENSE], 3, 2, 1, 1, 0, 0);

    panel.setBorder(createTitledBorder(getSharedProperty("legend"), false));

    return panel;
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
    return I18NHelper.getProperty("ComparisonView." + key);
  }

  private
  double[]
  getTotals()
  {
    return itsTotals;
  }

  private
  void
  resetTotals()
  {
    for(int len = 0; len < getTotals().length; ++len)
    {
      getTotals()[len] = 0.0;
    }
  }

  private
  void
  setTotals(double[] array)
  {
    itsTotals = array;
  }

  private
  void
  updateLabels()
  {
    double total = getTotals()[MONTH_EXPENSE] + getTotals()[MONTH_INCOME];

    if(total != 0.0)
    {
      PercentFormat pFormat = new PercentFormat();
      double expensePercent = getTotals()[MONTH_EXPENSE] / total;
      double incomePercent = getTotals()[MONTH_INCOME] / total;

      getLabels()[MONTH_EXPENSE].setText(pFormat.format(expensePercent));
      getLabels()[MONTH_EXPENSE].setToolTipText(UI_CURRENCY_FORMAT.format(getTotals()[MONTH_EXPENSE]));
      getLabels()[MONTH_INCOME].setText(pFormat.format(incomePercent));
      getLabels()[MONTH_INCOME].setToolTipText(UI_CURRENCY_FORMAT.format(getTotals()[MONTH_INCOME]));
    }
    else
    {
      getLabels()[MONTH_EXPENSE].setText("");
      getLabels()[MONTH_EXPENSE].setToolTipText(null);
      getLabels()[MONTH_INCOME].setText("");
      getLabels()[MONTH_INCOME].setToolTipText(null);
    }
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  private JLabel[] itsLabels;
  private double[] itsTotals;

  private static final int MONTH_EXPENSE = 0;
  private static final int MONTH_INCOME = 1;

  private static final Image EXPENSE = PIE_CHART_RED.getIcon().getImage();
  private static final Image INCOME = PIE_CHART_BLUE.getIcon().getImage();
  private static final Image NO_DATA = PIE_CHART_GRAY.getIcon().getImage();
}
