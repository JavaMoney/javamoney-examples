// DialogHeader

package org.javamoney.examples.ez.common.gui;

import static java.awt.Color.GRAY;
import static java.awt.Color.WHITE;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.border.MatteBorder;

/**
 * This class facilitates using a panel specifically designed to be displayed at
 * the top of a dialog.
 */
public
final
class
DialogHeader
extends Panel
{
  /**
   * Constructs a new dialog header.
   *
   * @param title The text of the title.
   * @param description The text of the description.
   * @param icon The icon displayed.
   */
  public
  DialogHeader(String title, String description, Icon icon)
  {
    setHeader(new JLabel());
    setIcon(new JLabel(icon));

    buildPanel();
    setText(title, description);
  }

  /**
   * This method sets the icon to display.
   *
   * @param icon The icon to display.
   */
  public
  void
  setIcon(Icon icon)
  {
    getIcon().setIcon(icon);
  }

  /**
   * This method builds the text displayed.
   *
   * @param title The text of the title.
   * @param description The text of the description.
   */
  public
  void
  setText(String title, String description)
  {
    String text = null;

    text = "<html>";
    text += "<h4>" + title + "</h4>";
    text += "<blockquote>" + description +"</blockquote>";
    text += "</html>";

    getHeader().setText(text);
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of private methods.
  //////////////////////////////////////////////////////////////////////////////

  private
  void
  buildPanel()
  {
    getHeader().setBackground(WHITE);
    getHeader().setFont(getHeader().getFont().deriveFont(Font.PLAIN));
    getHeader().setOpaque(true);

    // Build panel.
    setFill(GridBagConstraints.BOTH);
    add(getHeader(), 0, 0, 1, 1, 100, 100);
    add(getIcon(), 1, 0, 1, 1, 0, 0);

    setBackground(WHITE);
    setBorder(new MatteBorder(0, 0, 1, 0, GRAY));
    setInsets(new Insets(5, 5, 5, 5));
  }

  private
  JLabel
  getHeader()
  {
    return itsHeader;
  }

  private
  JLabel
  getIcon()
  {
    return itsIcon;
  }

  private
  void
  setHeader(JLabel label)
  {
    itsHeader = label;
  }

  private
  void
  setIcon(JLabel label)
  {
    itsIcon = label;
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  private JLabel itsHeader;
  private JLabel itsIcon;
}
