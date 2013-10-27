// BoundsHelper

package org.javamoney.examples.ez.common.utility;

import static java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment;

import java.awt.Rectangle;

/**
 * This class facilitates providing convenience methods for bounds. All methods
 * in this class are static.
 */
public
final
class
BoundsHelper
{
  /**
   * This method creates and returns bounds.
   *
   * @param width The width of the bounds.
   * @param height The height of the bounds.
   * @param area The area to center the bounds to.
   *
   * @return Bounds.
   */
  public
  static
  Rectangle
  createCenteredBounds(int width, int height, Rectangle area)
  {
    Rectangle rectangle = new Rectangle(0, 0, width, height);
    int x = area.x + (area.width / 2);
    int y = area.y + (area.height / 2);

    rectangle.setLocation(x - (width / 2), y - (height / 2));

    return rectangle;
  }

  /**
   * This method creates and returns bounds.
   *
   * @param width The width of the bounds.
   * @param height The height of the bounds.
   *
   * @return Bounds.
   */
  public
  static
  Rectangle
  createCenteredScreenBounds(int width, int height)
  {
    return createCenteredBounds(width, height, getScreenBounds());
  }

  /**
   * This method returns the bounds for the current screen resolution.
   *
   * @return Bounds.
   */
  public
  static
  Rectangle
  getScreenBounds()
  {
    return getLocalGraphicsEnvironment().getMaximumWindowBounds();
  }
}
