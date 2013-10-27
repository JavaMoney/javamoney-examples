// TextConstrainer

package org.javamoney.examples.ez.common.utility;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 * This class facilitates constraining the input of text. There are two text
 * attributes this class can constrain, the length of the text and the
 * characters that can be inputted.
 */
public
final
class
TextConstrainer
extends PlainDocument
{
  /**
   * Constructs a new constrainer.
   *
   * @param maxInput The maximum length of input the text can have.
   */
  public
  TextConstrainer(int maxInput)
  {
    this(maxInput, null);
  }

  /**
   * Constructs a new constrainer.
   *
   * @param maxInput The maximum length of input the text can have.
   * @param inputConstraints The characters the text can contain.
   */
  public
  TextConstrainer(int maxInput, String inputConstraints)
  {
    setConstraints(inputConstraints);
    setMaxLength(maxInput);
  }

  /**
   * Constructs a new constrainer.
   *
   * @param inputConstraints The characters the text can contain.
   */
  public
  TextConstrainer(String inputConstraints)
  {
    this(0, inputConstraints);
  }

  /**
   * This method returns the characters the text can contain. A null string
   * implies that the text can contain anything.
   *
   * @return The characters the text can contain.
   */
  public
  String
  getConstraints()
  {
    return itsConstraints;
  }

  /**
   * This method returns the maximum length of input the text can have. A value
   * of 0 implies that there is no limit.
   *
   * @return The maximum length of input the text can have.
   */
  public
  int
  getMaxLength()
  {
    return itsMaxLength;
  }

  /**
   * This method inserts text.
   *
   * @param offset The starting offset.
   * @param text The text to insert.
   * @param attributeSet The attributes for the inserted content.
   *
   * @throws BadLocationException If the given insert position is not a valid
   * position within the text.
   */
  @Override
  public
  void
  insertString(int offset, String text, AttributeSet attributeSet)
  throws BadLocationException
  {
    boolean valid = true;

    // If there is a max length specified and the text to insert exceeds it,
    // then truncate.
    if(getMaxLength() != 0 && (text.length() + getLength()) > getMaxLength())
    {
      text = text.substring(0, getMaxLength() - getLength());
    }

    // A null string implies that all characters are acceptable.
    if(getConstraints() != null)
    {
      for(int index = 0; index < text.length(); ++index)
      {
        // Is the character contained in the input constraints?
        if(getConstraints().indexOf(text.charAt(index)) == -1)
        {
          valid = false;
          break;
        }
      }
    }

    // Only insert if the text contains acceptable characters.
    if(valid == true)
    {
      super.insertString(offset, text, attributeSet);
    }
  }

  /**
   * This method sets the available characters the text can contain. A null
   * string implies that the text can contain anything.
   *
   * @param constraints The characters the text can contain.
   */
  public
  void
  setConstraints(String constraints)
  {
    itsConstraints = constraints;
  }

  /**
   * This method sets the maximum length of input the text can have. A value of
   * 0 implies that there is no limit.
   *
   * @param value The maximum length of input the text can have.
   */
  public
  void
  setMaxLength(int value)
  {
    itsMaxLength = Math.abs(value);
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  private String itsConstraints;
  private int itsMaxLength;
}
