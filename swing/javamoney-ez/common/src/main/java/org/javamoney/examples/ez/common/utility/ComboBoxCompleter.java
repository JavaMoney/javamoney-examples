// ComboBoxCompleter

package org.javamoney.examples.ez.common.utility;

import static java.awt.event.KeyEvent.CHAR_UNDEFINED;
import static java.awt.event.KeyEvent.VK_DOWN;
import static java.awt.event.KeyEvent.VK_ENTER;
import static java.awt.event.KeyEvent.VK_LEFT;
import static java.awt.event.KeyEvent.VK_RIGHT;
import static java.awt.event.KeyEvent.VK_UP;
import static java.lang.Character.isISOControl;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JComboBox;
import javax.swing.JTextField;

/**
 * This class facilitates automatically completing the text being typed in a
 * combo box's editor with the first match found in the combo box's items list.
 */
public
final
class
ComboBoxCompleter
{
  /**
   * Constructs a new completer.
   *
   * @param comboBox The combo box to monitor.
   */
  public
  ComboBoxCompleter(JComboBox comboBox)
  {
    this(comboBox, false, false);
  }

  /**
   * Constructs a new completer.
   *
   * @param comboBox The combo box to monitor.
   * @param caseSensitive Whether or not to use case sensitive comparing.
   * @param useTypedText Whether or not to use the text that is typed or the
   * text of the items.
   */
  public
  ComboBoxCompleter(JComboBox comboBox, boolean caseSensitive, boolean useTypedText)
  {
    setComboBox(comboBox);
    setIsCaseSensitive(caseSensitive);
    setUsesTypedText(useTypedText);

    // Add listeners.
    getTextField().addKeyListener(new KeyController());
  }

  /**
   * This method returns true if case sensitive comparing is in effect, otherwise
   * false.
   *
   * @return true or false.
   */
  public
  boolean
  isCaseSensitive()
  {
    return itsIsCaseSensitive;
  }

  /**
   * This method sets the whether or not to use sensitive comparing.
   *
   * @param value true or false.
   */
  public
  void
  setIsCaseSensitive(boolean value)
  {
    itsIsCaseSensitive = value;
  }

  /**
   * This method sets whether or not to use the text that is typed or the text
   * of the items.
   *
   * @param value true or false.
   */
  public
  void
  setUsesTypedText(boolean value)
  {
    itsUsesTypedText = value;
  }

  /**
   * This method returns true if the text that is typed should be used when
   * completing, otherwise false.
   *
   * @return true or false.
   */
  public
  boolean
  usesTypedText()
  {
    return itsUsesTypedText;
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of private methods.
  //////////////////////////////////////////////////////////////////////////////

  /*
   * This method iterates all the items in the combo box and compares each
   * item's text to the current text in the combo box's editor until a match is
   * found. If a match is found, then the combo box's editor's text will be
   * completed to that of the item's text. The text that was added from the
   * item's text will be highlighted.
   */
  private
  void
  findFirstMatchAndComplete()
  {
    JTextField field = getTextField();
    String text = field.getText();

    // Only iterate if there is some text to compare with.
    if(text.length() != 0)
    {
      boolean match = false;

      for(int index = 0; index < getComboBox().getItemCount(); ++index)
      {
        String item = getComboBox().getItemAt(index).toString();

        if(isCaseSensitive() == true)
        {
          match = item.startsWith(text);
        }
        else
        {
          match = item.toLowerCase().startsWith(text.toLowerCase());
        }

        if(match == true)
        {
          // Display the item's actual text or as is typed?
          if(usesTypedText() == true)
          {
            field.setText(text + item.substring(text.length()));
          }
          else
          {
            field.setText(item);
          }

          // Highlight added text.
          field.setCaretPosition(item.length());
          field.moveCaretPosition(text.length());

          break;
        }
      }
    }
  }

  private
  JComboBox
  getComboBox()
  {
    return itsComboBox;
  }

  private
  JTextField
  getTextField()
  {
    return (JTextField)getComboBox().getEditor().getEditorComponent();
  }

  private
  static
  boolean
  isValidKey(int key)
  {
    boolean result = false;

    // Control or arrow keys are invalid.
    if(isISOControl(key) == false && key != CHAR_UNDEFINED)
    {
      if(key != VK_LEFT && key != VK_RIGHT && key != VK_UP && key != VK_DOWN)
      {
        result = true;
      }
    }

    return result;
  }

  private
  void
  processKey(int key)
  {
    if(isValidKey(key) == true)
    {
      findFirstMatchAndComplete();
    }
    else if(key == VK_ENTER)
    {
      JTextField field = getTextField();

      // Remove any highlighting.
      field.setCaretPosition(field.getText().length());
    }
  }

  private
  void
  setComboBox(JComboBox comboBox)
  {
    itsComboBox = comboBox;
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of inner classes.
  //////////////////////////////////////////////////////////////////////////////

  private
  class
  KeyController
  extends KeyAdapter
  {
    @Override
    public
    void
    keyReleased(KeyEvent event)
    {
      processKey(event.getKeyCode());
    }
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  private JComboBox itsComboBox;
  private boolean itsIsCaseSensitive;
  private boolean itsUsesTypedText;
}
