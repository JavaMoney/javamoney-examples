// HTMLHelper

package org.javamoney.examples.ez.money.utility;

import static org.javamoney.examples.ez.money.ApplicationProperties.UI_CURRENCY;
import static org.javamoney.examples.ez.money.ApplicationProperties.UI_CURRENCY_SYMBOL;
import static org.javamoney.examples.ez.money.locale.CurrencySymbolKeys.RUBLE;

import java.awt.Insets;

import javax.swing.JEditorPane;

import org.javamoney.examples.ez.money.IconKeys;

/**
 * This class provides convenience methods for working with HTML formatted text.
 * All methods in this class are static.
 */
public
final
class
HTMLHelper
{
  /**
   * This method returns an image tag.
   *
   * @param key The image to display.
   *
   * @return An image tag that will display the specified image.
   */
  public
  static
  String
  buildImageTag(IconKeys key)
  {
    return "<img src=\"" + key.toString() + "\">";
  }

  /**
   * This method returns a link tag.
   *
   * @param command The link's action command.
   * @param label The link's displayed text.
   *
   * @return A link tag that will have the specified action command and
   * label.
   */
  public
  static
  String
  buildLinkTag(String command, String label)
  {
    return "<a href=\"" + command + "\">" + label + "</a>";
  }

  /**
   * This method returns a style tag to provide a common look and feel for HTML
   * formatted pages.
   *
   * @return A style tag to provide a common look and feel for HTML formatted
   * pages.
   */
  public
  static
  String
  buildStyleTag()
  {
    String text = null;

    text = "<style type=\"text/css\">";
    text += "a{color:#000099;}";
    text += "body{font-family:arial;font-size:10px}";
    text += "tr{color:black;}";
    text += "tr.header{color:white;}";
    text += "</style>";

    return text;
  }

  /**
   * This method creates and returns an editor pane for displaying HTML
   * formatted text.
   *
   * @return An editor pane for displaying HTML formatted text.
   */
  public
  static
  JEditorPane
  createWebPage()
  {
    JEditorPane pane = new JEditorPane("text/html", "");

    pane.setEditable(false);
    pane.setMargin(new Insets(0, 0, 0, 0));

    return pane;
  }

  /**
   * This method returns an HTML formatted string representation of the
   * specified amount in the format of #,###.## or (#,###.##), depending on
   * whether or not the amount is negative.
   *
   * @param amount The amount to format.
   *
   * @return An HTML formatted string representation of the specified amount.
   */
  public
  static
  String
  formatAmount(double amount)
  {
    return formatAmount(amount, true);
  }

  /**
   * This method returns an HTML formatted string representation of the
   * specified amount in the format of #,###.## or (#,###.##), depending on
   * whether or not the amount is negative.
   *
   * @param amount The amount to format.
   * @param showSymbol Whether or not to display the currency symbol.
   *
   * @return An HTML formatted string representation of the specified amount.
   */
  public
  static
  String
  formatAmount(double amount, boolean showSymbol)
  {
    String symbol = (showSymbol == true) ? UI_CURRENCY_SYMBOL.getSymbol() : "";
    String text = null;
    String balance = UI_CURRENCY.format(amount);
    String color = "";

    // Due to rounding inadequacies, testing for amount < 0 can be inaccurate.
    if(balance.charAt(0) == '(')
    {
      color = NEGATIVE_COLOR;
    }

    text = "<font color=" + color + ">" + balance + "</font>";

    if(UI_CURRENCY_SYMBOL == RUBLE)
    {
      text += symbol;
    }
    else
    {
      text = symbol + text;
    }

    return text;
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  private static final String NEGATIVE_COLOR = "#990000";
}
