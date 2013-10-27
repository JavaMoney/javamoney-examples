// TransferTotalPieChartPanel

package org.javamoney.examples.ez.money.gui.view.totals;

import static org.javamoney.examples.ez.common.utility.BorderHelper.createTitledBorder;
import static org.javamoney.examples.ez.common.utility.I18NHelper.getSharedProperty;
import static org.javamoney.examples.ez.money.IconKeys.PIE_CHART_BLUE;
import static org.javamoney.examples.ez.money.IconKeys.PIE_CHART_BLUE_LEGEND;
import static org.javamoney.examples.ez.money.IconKeys.PIE_CHART_GRAY;
import static org.javamoney.examples.ez.money.IconKeys.PIE_CHART_ORANGE;
import static org.javamoney.examples.ez.money.IconKeys.PIE_CHART_ORANGE_LEGEND;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.Image;
import java.awt.geom.Arc2D;

import javax.swing.JLabel;

import org.javamoney.examples.ez.money.locale.PercentFormat;
import org.javamoney.examples.ez.money.model.dynamic.total.TransferTotal;

import org.javamoney.examples.ez.common.gui.Panel;
import org.javamoney.examples.ez.common.utility.I18NHelper;

/**
 * This class facilitates displaying transfer total details.
 */
final
class
TransferTotalPieChartPanel
extends Panel
{
  /**
   * This method renders the transfer total data to its canvas.
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

    if(getTotal() != null)
    {
      double total = getTotal().getToTotal() + getTotal().getFromTotal();

      if(total != 0.0)
      {
        int arc = 360;
        double toPercent = getTotal().getToTotal() / total;

        arc = (int)(360 * toPercent);

        // Fill the pie as from, and then paint the to percentage.
        graphics.setClip(new Arc2D.Double(xCoord, yCoord, width, height, 0, 360, Arc2D.PIE));
        graphics.drawImage(FROM, xCoord, yCoord, null);

        graphics.setClip(new Arc2D.Double(xCoord, yCoord, width, height, 0, arc, Arc2D.PIE));
        graphics.drawImage(TO, xCoord, yCoord, null);
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
  TransferTotalPieChartPanel()
  {
    createLabels();

    buildPanel();
  }

  /**
   * This method clears the transfer total detail information.
   */
  protected
  void
  clear()
  {
    updateView(null);
  }

  /**
   * This method updates its view with the specified transfer total.
   *
   * @param total The transfer total to obtain the data from.
   */
  protected
  void
  updateView(TransferTotal total)
  {
    setTotal(total);
    updateInfo(total);
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
    panel.add(getSharedProperty("to") + gap, 0, 0, 1, 1, 0, 50);
    panel.add(getSharedProperty("from") + gap, 0, 2, 1, 1, 0, 50);
    panel.add(PIE_CHART_BLUE_LEGEND.getIcon(), 1, 0, 1, 1, 0, 0);
    panel.add(PIE_CHART_ORANGE_LEGEND.getIcon(), 1, 2, 1, 1, 0, 0);
    panel.addEmptyCellAt(2, 1);
    panel.add(getLabels()[TOTAL_TO], 3, 0, 1, 1, 100, 0);
    panel.add(getLabels()[TOTAL_FROM], 3, 2, 1, 1, 0, 0);

    panel.setBorder(createTitledBorder(getSharedProperty("legend"), false));

    return panel;
  }

  private
  static
  String
  getProperty(String key)
  {
    return I18NHelper.getProperty("TransferTotalPieChartPanel." + key);
  }

  private
  JLabel[]
  getLabels()
  {
    return itsLabels;
  }

  private
  TransferTotal
  getTotal()
  {
    return itsTotal;
  }

  private
  void
  setTotal(TransferTotal total)
  {
    itsTotal = total;
  }

  private
  void
  updateInfo(TransferTotal total)
  {
    PercentFormat format = new PercentFormat();
    String from = "";
    String to = "";

    if(total != null)
    {
      double totalTransfer = total.getToTotal() + total.getFromTotal();

      if(totalTransfer != 0.0)
      {
        double inPercent = total.getToTotal() / totalTransfer;

        to = format.format(inPercent);
        from = format.format(1 - inPercent);
      }
    }

    getLabels()[TOTAL_TO].setText(to);
    getLabels()[TOTAL_FROM].setText(from);
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  private JLabel[] itsLabels;
  private TransferTotal itsTotal;

  private static final int TOTAL_FROM = 0;
  private static final int TOTAL_TO = 1;

  private static final Image FROM = PIE_CHART_ORANGE.getIcon().getImage();
  private static final Image NO_DATA = PIE_CHART_GRAY.getIcon().getImage();
  private static final Image TO = PIE_CHART_BLUE.getIcon().getImage();
}
