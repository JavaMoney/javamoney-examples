// Link

package org.javamoney.examples.ez.common.gui;

import static java.awt.Cursor.HAND_CURSOR;
import static java.awt.Cursor.getDefaultCursor;
import static java.awt.Cursor.getPredefinedCursor;
import static java.awt.event.MouseEvent.MOUSE_ENTERED;
import static java.awt.event.MouseEvent.MOUSE_EXITED;

import java.awt.Color;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;

/**
 * This class facilitates creating buttons that resemble HTML links.
 */
public
final
class
Link
extends JButton
{
  /**
   * Constructs a new link.
   */
  public
  Link()
  {
    this("");
  }

  /**
   * Constructs a new link.
   *
   * @param text The text of the link.
   */
  public
  Link(String text)
  {
    this(text, null);
  }

  /**
   * Constructs a new link.
   *
   * @param text The text of the link.
   * @param controller The controller that is notified when the link is clicked.
   */
  public
  Link(String text, ActionListener controller)
  {
    this(text, controller, text);
  }

  /**
   * Constructs a new link.
   *
   * @param text The text of the link.
   * @param controller The controller that is notified when the link is clicked.
   * @param command The action command associated with the link when it is
   * clicked.
   */
  public
  Link(String text, ActionListener controller, String command)
  {
    super(text);

    // Customize the button.
    setActionCommand(command);
    setBorderPainted(false);
    setContentAreaFilled(false);
    setFocusPainted(false);
    setHoveringColor(Color.BLUE);
    setMargin(new Insets(0, 0, 0, 0));
    setTextColor(getForeground());

    // Add listeners.
    addActionListener(controller);
    addMouseListener(new MouseController());
  }

  /**
   * This method returns the text displayed in the link.
   *
   * @return The text displayed in the link.
   */
  public
  final
  String
  getLinkText()
  {
    return itsLinkText;
  }

  /**
   * This method enables or disables the link.
   *
   * @param value true or false.
   */
  @Override
  public
  final
  void
  setEnabled(boolean value)
  {
    super.setEnabled(value);

    if(isEnabled() == true)
    {
      setForeground(getTextColor());
    }
    else
    {
      setForeground(Color.GRAY);
    }
  }

  /**
   * This method returns the color that is used when the cursor is hovering over
   * the link.
   *
   * @return The color that is used when the cursor is hovering over the link.
   */
  public
  final
  Color
  getHoveringColor()
  {
    return itsHoveringColor;
  }

  /**
   * This method returns the text color of the link.
   *
   * @return The text color of the link.
   */
  public
  final
  Color
  getTextColor()
  {
    return itsTextColor;
  }

  /**
   * This method sets the color that is used when the cursor is hovering over
   * the link.
   *
   * @param color The color to use.
   */
  public
  final
  void
  setHoveringColor(Color color)
  {
    itsHoveringColor = color;
  }

  /**
   * This method sets the text displayed in the link.
   *
   * @param text The text displayed in the link.
   */
  @Override
  public
  final
  void
  setText(String text)
  {
    itsLinkText = text;

    super.setText("<html><u>" + getLinkText() + "</u></html>");
  }

  /**
   * This method sets the text color of the link.
   *
   * @param color The color to use.
   */
  public
  final
  void
  setTextColor(Color color)
  {
    itsTextColor = color;
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of inner classes.
  //////////////////////////////////////////////////////////////////////////////

  private
  class
  MouseController
  extends MouseAdapter
  {
    public
    void
    doMouseEvent(MouseEvent event)
    {
      if(isEnabled() == true)
      {
        if(event.getID() == MOUSE_ENTERED)
        {
          setCursor(getPredefinedCursor(HAND_CURSOR));
          setForeground(getHoveringColor());
        }
        else if(event.getID() == MOUSE_EXITED)
        {
          setCursor(getDefaultCursor());
          setForeground(getTextColor());
        }
      }
    }

    @Override
    public
    void
    mouseEntered(MouseEvent event)
    {
      doMouseEvent(event);
    }

    @Override
    public
    void
    mouseExited(MouseEvent event)
    {
      doMouseEvent(event);
    }
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  private Color itsHoveringColor;
  private String itsLinkText;
  private Color itsTextColor;
}
