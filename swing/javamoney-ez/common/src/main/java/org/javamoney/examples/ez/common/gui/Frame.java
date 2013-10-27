// Frame

package org.javamoney.examples.ez.common.gui;

import static org.javamoney.examples.ez.common.utility.BoundsHelper.createCenteredBounds;
import static org.javamoney.examples.ez.common.utility.BoundsHelper.getScreenBounds;

import java.awt.Rectangle;

import javax.swing.JFrame;

/**
 * This class facilitates providing common functionality for a frame.
 */
public
class
Frame
extends JFrame
{
  /**
   * Constructs a new frame.
   */
  public
  Frame()
  {
    setDefaultCloseOperation(EXIT_ON_CLOSE);
  }

  /**
   * This method creates and returns bounds that are centered to itself.
   *
   * @param width The width of the bounds.
   * @param height The height of the bounds.
   *
   * @return Bounds that are centered to itself.
   */
  public
  final
  Rectangle
  createBounds(int width, int height)
  {
    Rectangle bounds = getBounds();

    if(getExtendedState() == MAXIMIZED_BOTH)
    {
      bounds = getScreenBounds();
    }

    return createCenteredBounds(width, height, bounds);
  }

  /**
   * The method causes the frame to show itself.
   *
   * @param bounds The bounds of the frame.
   * param extendedState The extended state of the frame.
   */
  public
  final
  void
  showFrame(Rectangle bounds, int extendedState)
  {
    setBounds(bounds);
    setExtendedState(extendedState);

    super.setVisible(true);
  }
}
