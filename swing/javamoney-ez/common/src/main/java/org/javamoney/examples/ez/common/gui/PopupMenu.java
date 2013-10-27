// PopupMenu

package org.javamoney.examples.ez.common.gui;

import static java.awt.event.MouseEvent.BUTTON1;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPopupMenu;

/**
 * This class facilitates using a popup menu and displaying the menu when
 * triggered. Components should add instances of this class as a mouse listener.
 */
public
final
class
PopupMenu
extends JPopupMenu
implements MouseListener
{
  /**
   * Constructs a new pop-up menu.
   */
  public
  PopupMenu()
  {
    setAllowFloatingY(true);
  }

  /**
   * This method returns true if a floating y coordinate is allowed, other
   * false.
   *
   * @return true or false
   */
  public
  boolean
  allowFloatingY()
  {
    return itsAllowFloatingY;
  }

  /**
   * This method returns true if the popup should behave like a normal menu,
   * otherwise false.
   *
   * @return true or false.
   */
  public
  boolean
  behaveLikeMenu()
  {
    return itsBehaveLikeMenu;
  }

  /**
   * This method is invoked when the specific mouse event occurs.
   *
   * @param event The mouse event that occurred.
   */
  public
  void
  mouseClicked(MouseEvent event)
  {
    // Ignored.
  }

  /**
   * This method is invoked when the specific mouse event occurs.
   *
   * @param event The mouse event that occurred.
   */
  public
  void
  mouseEntered(MouseEvent event)
  {
    // Ignored.
  }

  /**
   * This method is invoked when the specific mouse event occurs.
   *
   * @param event The mouse event that occurred.
   */
  public
  void
  mouseExited(MouseEvent event)
  {
    // Ignored.
  }

  /**
   * This method is invoked when the specific mouse event occurs.
   *
   * @param event The mouse event that occurred.
   */
  public
  void
  mousePressed(MouseEvent event)
  {
    mouseReleased(event);
  }

  /**
   * This method is invoked when the specific mouse event occurs.
   *
   * @param event The mouse event that occurred.
   */
  public
  void
  mouseReleased(MouseEvent event)
  {
    Component component = event.getComponent();

    if(component.hasFocus() == false)
    {
      component.requestFocus();
    }

    if(component.isEnabled() == true)
    {
      if(behaveLikeMenu() == true)
      {
        if(event.getButton() == BUTTON1)
        {
          show(component, 0, component.getHeight());
        }
      }
      else if(event.isPopupTrigger() == true)
      {
        int yCoordinate = 0;

        if(allowFloatingY() == true)
        {
          yCoordinate = event.getY();
        }

        show(component, event.getX(), yCoordinate);
      }
    }
  }

  /**
   * This method sets whether or not the popup should behave like a normal
   * menu.
   *
   * @param value true or false.
   */
  public
  void
  setBehaveLikeMenu(boolean value)
  {
    itsBehaveLikeMenu = value;
  }

  /**
   * This method sets whether or not a floating y coordinate is allowed. When
   * set to true, the pop-up's y coordinate will always appear at the y
   * coordinate of the mouse click. Otherwise the pop-up's y coordinate appears
   * at the top of the component.
   *
   * @param value true or false.
   */
  public
  void
  setAllowFloatingY(boolean value)
  {
    itsAllowFloatingY = value;
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  private boolean itsAllowFloatingY;
  private boolean itsBehaveLikeMenu;
}
