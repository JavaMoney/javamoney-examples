// CategoryTotalPieChartPanel

package org.javamoney.examples.ez.money.gui.view.totals;

import static org.javamoney.examples.ez.common.utility.BorderHelper.createTitledBorder;
import static org.javamoney.examples.ez.common.utility.I18NHelper.getSharedProperty;
import static org.javamoney.examples.ez.money.IconKeys.PIE_CHART_BLUE;
import static org.javamoney.examples.ez.money.IconKeys.PIE_CHART_BLUE_LEGEND;
import static org.javamoney.examples.ez.money.IconKeys.PIE_CHART_GRAY;
import static org.javamoney.examples.ez.money.IconKeys.PIE_CHART_GRAY_LEGEND;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.Image;
import java.awt.geom.Arc2D;

import javax.swing.JLabel;

import org.javamoney.examples.ez.money.locale.PercentFormat;
import org.javamoney.examples.ez.money.model.dynamic.total.IncomeExpenseTotal;

import org.javamoney.examples.ez.common.gui.Panel;
import org.javamoney.examples.ez.common.utility.I18NHelper;

/**
 * This class facilitates displaying category total details.
 */
final
class
CategoryTotalPieChartPanel
extends Panel
{
  /**
   * This method renders the category total data to its canvas.
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

    if(getCategoryTotal() != null)
    {
      int arc = (int)(360 * getCategoryTotal().getPercent());

      // Fill the pie as other, and then paint the percentage.
      if(arc < 360)
      {
        graphics.setClip(new Arc2D.Double(xCoord, yCoord, width, height, 0, 360, Arc2D.PIE));
        graphics.drawImage(OTHER, xCoord, yCoord, null);
      }

      graphics.setClip(new Arc2D.Double(xCoord, yCoord, width, height, 0, arc, Arc2D.PIE));
      graphics.drawImage(SELECTED, xCoord, yCoord, null);
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
   * Constructs a new pie chart panel.
   */
  protected
  CategoryTotalPieChartPanel()
  {
    createLabels();

    buildPanel();
  }

  /**
   * This method clears the category total detail information.
   */
  protected
  void
  clear()
  {
    updateView(null);
  }

  /**
   * This method updates its view with the specified category total.
   *
   * @param total The category total to obtain the data from.
   */
  protected
  void
  updateView(IncomeExpenseTotal total)
  {
    setCategoryTotal(total);

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
    panel.setAnchor(GridBagConstraints.WEST);
    panel.add(getProperty("selected") + gap, 0, 0, 1, 1, 0, 50);
    panel.add(getProperty("other") + gap, 0, 2, 1, 1, 0, 50);
    panel.add(PIE_CHART_BLUE_LEGEND.getIcon(), 1, 0, 1, 1, 0, 0);
    panel.add(PIE_CHART_GRAY_LEGEND.getIcon(), 1, 2, 1, 1, 0, 0);
    panel.addEmptyCellAt(2, 1);
    panel.add(getLabels()[TOTAL_SELECTED], 3, 0, 1, 1, 100, 0);
    panel.add(getLabels()[TOTAL_OTHER], 3, 2, 1, 1, 0, 0);

    panel.setBorder(createTitledBorder(getSharedProperty("legend"), false));

    return panel;
  }

  private
  IncomeExpenseTotal
  getCategoryTotal()
  {
    return itsCategoryTotal;
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
    return I18NHelper.getProperty("CategoryTotalPieChartPanel." + key);
  }

  private
  void
  setCategoryTotal(IncomeExpenseTotal total)
  {
    itsCategoryTotal = total;
  }

  private
  void
  updateInfo()
  {
    PercentFormat format = new PercentFormat();
    String other = "";
    String selected = "";

    if(getCategoryTotal() != null && getCategoryTotal().getAmount() != 0.0)
    {
      double percent = getCategoryTotal().getPercent();

      other = format.format(1 - percent);
      selected = format.format(percent);
    }

    getLabels()[TOTAL_OTHER].setText(other);
    getLabels()[TOTAL_SELECTED].setText(selected);
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  private IncomeExpenseTotal itsCategoryTotal;
  private JLabel[] itsLabels;

  private static final int TOTAL_OTHER = 0;
  private static final int TOTAL_SELECTED = 1;

  private static final Image OTHER = PIE_CHART_GRAY.getIcon().getImage();
  private static final Image NO_DATA = PIE_CHART_GRAY.getIcon().getImage();
  private static final Image SELECTED = PIE_CHART_BLUE.getIcon().getImage();
}
