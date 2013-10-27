// SearchWidget

package org.javamoney.examples.ez.common.gui;

import static org.javamoney.examples.ez.common.CommonIconKeys.SEARCH;
import static org.javamoney.examples.ez.common.CommonIconKeys.SEARCH_CLEAR;
import static org.javamoney.examples.ez.common.CommonIconKeys.SEARCH_CLEAR_PRESSED;
import static org.javamoney.examples.ez.common.CommonIconKeys.SEARCH_NO_TEXT;
import static org.javamoney.examples.ez.common.CommonIconKeys.SEARCH_WITH_MENU;
import static java.awt.event.InputEvent.CTRL_DOWN_MASK;
import static java.awt.event.KeyEvent.CHAR_UNDEFINED;
import static java.awt.event.KeyEvent.VK_BACK_SPACE;
import static java.awt.event.KeyEvent.VK_DOWN;
import static java.awt.event.KeyEvent.VK_LEFT;
import static java.awt.event.KeyEvent.VK_RIGHT;
import static java.awt.event.KeyEvent.VK_UP;
import static java.awt.event.MouseEvent.BUTTON1;
import static java.lang.Character.isISOControl;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.SoftBevelBorder;

import org.javamoney.examples.ez.common.utility.ActionSignaler;
import org.javamoney.examples.ez.common.utility.ClipboardMenuController;

/**
 * This class facilitates providing a basic search tool to be used in the UI.
 */
public
final
class
SearchWidget
extends Panel
{
  /**
   * Constructs a new search widget.
   */
  public
  SearchWidget()
  {
    this(null);
  }

  /**
   * Constructs a new search widget.
   *
   * @param menu The menu that will handle the mouse clicks on the menu icon.
   */
  public
  SearchWidget(PopupMenu menu)
  {
    setActionSignaler(new ActionSignaler());
    setField(new SearchField());

    createLabels();

    buildPanel(menu != null);

    // Add listeners.
    new ClipboardMenuController(getField());
    getField().addFocusListener(new FocusHandler());
    getField().addKeyListener(new KeyHandler());
    getLabels()[LEFT_LABEL].addMouseListener(new MouseHandler());
    getLabels()[RIGHT_LABEL].addMouseListener(new MouseHandler());

    if(menu != null)
    {
      getLabels()[LEFT_LABEL].addMouseListener(menu);
    }
  }

  /**
   * This method adds the action listener to the widget.
   *
   * @param listener The action listener to add.
   */
  public
  void
  addActionListener(ActionListener listener)
  {
    getActionSignaler().addListener(listener);
  }

  /**
   * This method returns the text in the search field.
   *
   * @return The text in the search field.
   */
  public
  String
  getSearchText()
  {
    return getField().getText();
  }

  /**
   * This method returns true if the widget has focus, otherwise false.
   *
   * @return true or false.
   */
  @Override
  public
  boolean
  hasFocus()
  {
    return getField().hasFocus();
  }

  /**
   * This method requests that the search field gets the input focus.
   */
  @Override
  public
  void
  requestFocus()
  {
    getField().requestFocus();
  }

  /**
   * This method sets the text displayed in the search field.
   *
   * @param text The text displayed in the search field.
   */
  public
  void
  setSearchText(String text)
  {
    getField().setText(text);
    setRightIcon();

    sendSignal();
  }

  /**
   * This method sets the tool tip for the widget.
   *
   * @param tip The message to display.
   */
  @Override
  public
  void
  setToolTipText(String tip)
  {
    getField().setToolTipText(tip);
    getLabels()[LEFT_LABEL].setToolTipText(tip);
    getLabels()[RIGHT_LABEL].setToolTipText(tip);
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of private methods.
  //////////////////////////////////////////////////////////////////////////////

  private
  void
  buildPanel(boolean useMenu)
  {
    // Build panel.
    setFill(GridBagConstraints.HORIZONTAL);
    add(getLabels()[LEFT_LABEL], 0, 0, 1, 1, 0, 100);
    add(getField(), 1, 0, 1, 1, 100, 0);
    add(getLabels()[RIGHT_LABEL], 2, 0, 1, 1, 0, 0);

    setBackground(getField().getBackground());
    setBorder(NORMAL);

    getField().setBorder(null);
    getField().setColumns(12);

    if(useMenu == true)
    {
      getLabels()[LEFT_LABEL].setIcon(SEARCH_WITH_MENU.getIcon());
    }
    else
    {
      getLabels()[LEFT_LABEL].setIcon(SEARCH.getIcon());
    }

    getLabels()[RIGHT_LABEL].setIcon(SEARCH_NO_TEXT.getIcon());
  }

  private
  void
  createLabels()
  {
    itsLabels = new JLabel[2];

    getLabels()[LEFT_LABEL] = new JLabel();
    getLabels()[RIGHT_LABEL] = new JLabel();
  }

  private
  ActionSignaler
  getActionSignaler()
  {
    return itsActionSignaler;
  }

  private
  JTextField
  getField()
  {
    return itsField;
  }

  private
  JLabel[]
  getLabels()
  {
    return itsLabels;
  }

  private
  static
  boolean
  isValidKey(int key)
  {
    boolean result = false;

    // Control keys (except the back space) and arrow keys are invalid.
    if(key != CHAR_UNDEFINED)
    {
      if(isISOControl(key) == false || key == VK_BACK_SPACE)
      {
        if(key != VK_LEFT && key != VK_RIGHT && key != VK_UP && key != VK_DOWN)
        {
          result = true;
        }
      }
    }

    return result;
  }

  private
  void
  setActionSignaler(ActionSignaler signaler)
  {
    itsActionSignaler = signaler;
  }

  private
  void
  sendSignal()
  {
    getActionSignaler().sendSignal(this, ACTION_COMMAND);
  }

  private
  void
  setField(SearchField field)
  {
    itsField = field;
  }

  private
  void
  setRightIcon()
  {
    // Set the right icon according to the text in the field.
    if(getField().getText().length() == 0)
    {
      getLabels()[RIGHT_LABEL].setIcon(SEARCH_NO_TEXT.getIcon());
    }
    else
    {
      getLabels()[RIGHT_LABEL].setIcon(SEARCH_CLEAR.getIcon());
    }
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of inner classes.
  //////////////////////////////////////////////////////////////////////////////

  private
  class
  FocusHandler
  implements FocusListener
  {
    public
    void
    focusGained(FocusEvent event)
    {
      setRightIcon();
      setBorder(FOCUS);
    }

    public
    void
    focusLost(FocusEvent event)
    {
      // The clear icon is not visible when the text field does not have focus.
      if(getLabels()[RIGHT_LABEL].getIcon() != SEARCH_NO_TEXT.getIcon())
      {
        getLabels()[RIGHT_LABEL].setIcon(SEARCH_NO_TEXT.getIcon());
      }

      setBorder(NORMAL);
    }
  }

  private
  class
  KeyHandler
  extends KeyAdapter
  {
    @Override
    public
    void
    keyReleased(KeyEvent event)
    {
      int key = event.getKeyCode();

      if(isValidKey(key) == true)
      {
        setRightIcon();
        sendSignal();
      }
    }
  }

  private
  class
  MouseHandler
  extends MouseAdapter
  {
    @Override
    public
    void
    mouseClicked(MouseEvent event)
    {
      if(event.getSource() == getLabels()[RIGHT_LABEL])
      {
        if(event.getModifiersEx() != CTRL_DOWN_MASK && event.getButton() == BUTTON1)
        {
          // Clear the search field.
          if(getField().hasFocus() == true)
          {
            setSearchText("");
          }
        }
      }
      else
      {
        getField().requestFocus();
      }
    }

    @Override
    public
    void
    mousePressed(MouseEvent event)
    {
      if(event.getSource() == getLabels()[RIGHT_LABEL] && event.getButton() == BUTTON1)
      {
        // Icons are only displayed on the right when the text field has focus.
        if(getField().hasFocus() == true)
        {
          if(getLabels()[RIGHT_LABEL].getIcon() == SEARCH_CLEAR.getIcon())
          {
            getLabels()[RIGHT_LABEL].setIcon(SEARCH_CLEAR_PRESSED.getIcon());
          }
        }
      }
    }

    @Override
    public
    void
    mouseReleased(MouseEvent event)
    {
      if(event.getSource() == getLabels()[RIGHT_LABEL] && event.getButton() == BUTTON1)
      {
        // Icons are only displayed on the right when the text field has focus.
        if(getField().hasFocus() == true)
        {
          // The user clicked the clear button, but released the mouse elsewhere.
          if(getField().getBounds().contains(event.getPoint()) == false)
          {
            getLabels()[RIGHT_LABEL].setIcon(SEARCH_CLEAR.getIcon());
          }
        }
      }
    }
  }

  private
  class
  SearchField
  extends JTextField
  {
    @Override
    public
    void
    cut()
    {
      super.cut();

      setRightIcon();
      sendSignal();
    }

    @Override
    public
    void
    paste()
    {
      super.paste();

      // Its safe to assume a paste action will require the clear icon.
      if(getLabels()[RIGHT_LABEL].getIcon() != SEARCH_CLEAR.getIcon())
      {
        getLabels()[RIGHT_LABEL].setIcon(SEARCH_CLEAR.getIcon());
      }

      sendSignal();
    }
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  private ActionSignaler itsActionSignaler;
  private SearchField itsField;
  private JLabel[] itsLabels;

  private static final int LEFT_LABEL = 0;
  private static final int RIGHT_LABEL = 1;

  private static final String ACTION_COMMAND = SearchWidget.class.getSimpleName();

  // Borders used for widget.
  private static Border BEVEL_FOCUS = new SoftBevelBorder(BevelBorder.LOWERED,
      new Color(185, 211, 234),
      new Color(220, 233, 244),
      new Color(158, 184, 207),
      new Color(220, 233, 244));

  private static Border FOCUS = new CompoundBorder(
      new LineBorder(new Color(144, 183, 217), 1, true),
      BEVEL_FOCUS);

  private static Border NORMAL = new CompoundBorder(
      new LineBorder(new Panel().getBackground(), 1, true),
      new SoftBevelBorder(BevelBorder.LOWERED));
}
