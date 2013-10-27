// ScrollPane

package org.javamoney.examples.ez.common.gui;

import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.Point;

import javax.swing.JComponent;
import javax.swing.JScrollPane;

/**
 * This class facilitates using a scroll pane.
 */
public
final
class
ScrollPane
extends Panel
{
  /**
   * Constructs a new scroll pane.
   *
   * @param component The component to provide a view for.
   */
  public
  ScrollPane(JComponent component)
  {
    setScrollPane(new JScrollPane(component));

    // Build the panel.
    setFill(GridBagConstraints.BOTH);
    add(getScrollPane(), 0, 0, 1, 1, 100, 100);

    getScrollPane().setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_ALWAYS);
  }

  /**
   * This method sets the background color.
   *
   * @param color The background color.
   */
  @Override
  public
  void
  setBackground(Color color)
  {
    if(getScrollPane() != null)
    {
      getScrollPane().getViewport().setBackground(color);
    }

    super.setBackground(color);
  }

  /**
   * This method causes the scroll pane to scroll vertically.
   *
   * @param height The vertical amount to scroll.
   */
  public
  void
  scroll(int height)
  {
    getScrollPane().getViewport().setViewPosition(new Point(0, height));
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of private methods.
  //////////////////////////////////////////////////////////////////////////////

  private
  JScrollPane
  getScrollPane()
  {
    return itsScrollPane;
  }

  private
  void
  setScrollPane(JScrollPane scrollPane)
  {
    itsScrollPane = scrollPane;
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  private JScrollPane itsScrollPane;
}
