// FormDateHandler

package org.javamoney.examples.ez.money.gui.view.register;

import static org.javamoney.examples.ez.common.utility.DateHelper.createCalendar;
import static java.awt.event.InputEvent.ALT_DOWN_MASK;
import static java.awt.event.InputEvent.SHIFT_DOWN_MASK;
import static java.awt.event.KeyEvent.VK_DOWN;
import static java.awt.event.KeyEvent.VK_UP;
import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;
import static org.javamoney.examples.ez.money.ApplicationProperties.UI_DATE_FORMAT;
import static org.javamoney.examples.ez.money.gui.view.register.FormFieldKeys.DATE;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.JTextField;

/**
 * This class facilitates managing the date in the form.
 */
final
class
FormDateHandler
{
  /**
   * Constructs a new handler that will monitor the specified form.
   *
   * @param form The form to monitor.
   */
  protected
  FormDateHandler(Form form)
  {
    // Add listeners.
    form.getField(DATE).addFocusListener(new FocusHandler());
    form.getField(DATE).addKeyListener(new KeyHandler());
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of private methods.
  //////////////////////////////////////////////////////////////////////////////

  private
  static
  Date
  getDate(JTextField field)
  {
    Date date = new Date();

    try
    {
      date = UI_DATE_FORMAT.parse(field.getText());
    }
    catch(Exception exception)
    {
      // Ignored.
    }

    return date;
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
      // Ignored.
    }

    public
    void
    focusLost(FocusEvent event)
    {
      JTextField field = (JTextField)event.getSource();

      field.setText(UI_DATE_FORMAT.format(getDate(field)));
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
    keyPressed(KeyEvent event)
    {
      JTextField field = (JTextField)event.getSource();
      int key = event.getKeyCode();

      if(key == VK_DOWN || key == VK_UP)
      {
        GregorianCalendar calendar = createCalendar(getDate(field));
        int modifier = event.getModifiersEx();
        int dateField = 0;

        if(modifier == ALT_DOWN_MASK)
        {
          dateField = YEAR;
        }
        else if(modifier == SHIFT_DOWN_MASK)
        {
          dateField = MONTH;
        }
        else
        {
          dateField = DAY_OF_MONTH;
        }

        if(key == VK_UP)
        {
          calendar.set(dateField, calendar.get(dateField) + 1);
        }
        else
        {
          calendar.set(dateField, calendar.get(dateField) - 1);
        }

        field.setText(UI_DATE_FORMAT.format(calendar.getTime()));
      }
    }
  }
}
