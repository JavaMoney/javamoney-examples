// FormTraversalHandler

package org.javamoney.examples.ez.money.gui.view.register;

import static org.javamoney.examples.ez.money.gui.view.register.FormButtonKeys.CANCEL;
import static org.javamoney.examples.ez.money.gui.view.register.FormButtonKeys.DATE_PICKER;
import static org.javamoney.examples.ez.money.gui.view.register.FormButtonKeys.ENTER;
import static org.javamoney.examples.ez.money.gui.view.register.FormButtonKeys.NEW;
import static org.javamoney.examples.ez.money.gui.view.register.FormButtonKeys.NEXT;
import static org.javamoney.examples.ez.money.gui.view.register.FormButtonKeys.PENDING;
import static org.javamoney.examples.ez.money.gui.view.register.FormButtonKeys.SPLIT;
import static org.javamoney.examples.ez.money.gui.view.register.FormFieldKeys.AMOUNT;
import static org.javamoney.examples.ez.money.gui.view.register.FormFieldKeys.CHECK_NUMBER;
import static org.javamoney.examples.ez.money.gui.view.register.FormFieldKeys.DATE;
import static org.javamoney.examples.ez.money.gui.view.register.FormFieldKeys.NOTES;

import java.awt.Component;
import java.awt.Container;
import java.awt.FocusTraversalPolicy;
import java.awt.Window;

import javax.swing.AbstractButton;
import javax.swing.JTextField;

/**
 * This class facilitates traversing the form via the keyboard.
 */
final
class
FormTraversalHandler
extends FocusTraversalPolicy
{
  /**
   * This method returns the next component to receive focus.
   *
   * @param container The container the component belongs to.
   * @param component The component that currently has the focus.
   *
   * @return The next component to receive focus.
   */
  @Override
  public
  Component
  getComponentAfter(Container container, Component component)
  {
    Component next = getButton(NEW);

    if(component == getButton(CANCEL))
    {
      next = getButton(NEW);
    }
    else if(component == getButton(DATE_PICKER))
    {
      next = getForm().getPayFromChooser();
    }
    else if(component == getButton(ENTER))
    {
      next = getButton(CANCEL);
    }
    else if(component == getButton(NEW))
    {
      next = getField(CHECK_NUMBER);
    }
    else if(component == getButton(NEXT))
    {
      next = getField(DATE);
    }
    else if(component == getButton(SPLIT))
    {
      next = getButton(PENDING);
    }
    else if(component == getButton(PENDING))
    {
      next = getField(NOTES);
    }
    else if(component == getField(AMOUNT))
    {
      next = getForm().getPayToChooser();
    }
    else if(component == getField(CHECK_NUMBER))
    {
      next = getField(DATE);
    }
    else if(component == getField(DATE))
    {
      next = getForm().getPayFromChooser();
    }
    else if(component == getField(NOTES))
    {
      next = getButton(ENTER);
    }
    else if(component == getForm().getPayFromChooser().getTextField())
    {
      next = getField(AMOUNT);
    }
    else if(component == getForm().getPayToChooser())
    {
      next = getButton(SPLIT);
    }

    if(next.isEnabled() == false)
    {
      next = component;
    }

    return next;
  }

  /**
   * This method returns the previous component to receive focus.
   *
   * @param container The container the component belongs to.
   * @param component The component that currently has the focus.
   *
   * @return The previous component to receive focus.
   */
  @Override
  public
  Component
  getComponentBefore(Container container, Component component)
  {
    Component previous = getButton(ENTER);

    if(component == getButton(CANCEL))
    {
      previous = getButton(ENTER);
    }
    else if(component == getButton(DATE_PICKER))
    {
      previous = getField(DATE);
    }
    else if(component == getButton(ENTER))
    {
      previous = getField(NOTES);
    }
    else if(component == getButton(NEW))
    {
      previous = getButton(CANCEL);
    }
    else if(component == getButton(NEXT))
    {
      previous = getField(CHECK_NUMBER);
    }
    else if(component == getButton(SPLIT))
    {
      previous = getForm().getPayToChooser();
    }
    else if(component == getButton(PENDING))
    {
      previous = getButton(SPLIT);
    }
    else if(component == getField(AMOUNT))
    {
      previous = getForm().getPayFromChooser();
    }
    else if(component == getField(CHECK_NUMBER))
    {
      previous = getButton(NEW);
    }
    else if(component == getField(DATE))
    {
      previous = getField(CHECK_NUMBER);
    }
    else if(component == getField(NOTES))
    {
      previous = getButton(PENDING);
    }
    else if(component == getForm().getPayFromChooser().getTextField())
    {
      previous = getField(DATE);
    }
    else if(component == getForm().getPayToChooser())
    {
      previous = getField(AMOUNT);
    }

    if(previous.isEnabled() == false)
    {
      previous = component;
    }

    return previous;
  }

  /**
   * This method returns the default component to receive focus.
   *
   * @param container The container the component belongs to.
   *
   * @return The default component to receive focus.
   */
  @Override
  public
  Component
  getDefaultComponent(Container container)
  {
    return getButton(NEW);
  }

  /**
   * This method returns the first component to receive focus.
   *
   * @param container The container the component belongs to.
   *
   * @return The first component to receive focus.
   */
  @Override
  public
  Component
  getFirstComponent(Container container)
  {
    return getButton(NEW);
  }

  /**
   * This method returns the initial component to receive focus.
   *
   * @param window The window the container belongs to.
   *
   * @return The initial component to receive focus.
   */
  @Override
  public
  Component
  getInitialComponent(Window window)
  {
    return getButton(NEW);
  }

  /**
   * This method returns the last component to receive focus.
   *
   * @param container The container the component belongs to.
   *
   * @return The last component to receive focus.
   */
  @Override
  public
  Component
  getLastComponent(Container container)
  {
    return getButton(CANCEL);
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of protected methods.
  //////////////////////////////////////////////////////////////////////////////

  /**
   * Constructs a new handler that will monitor the specified form.
   *
   * @param form The form to monitor.
   */
  protected
  FormTraversalHandler(Form form)
  {
    setForm(form);

    getForm().setFocusCycleRoot(true);
    getForm().setFocusTraversalPolicy(this);
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of private methods.
  //////////////////////////////////////////////////////////////////////////////

  private
  AbstractButton
  getButton(FormButtonKeys key)
  {
    return getForm().getButton(key);
  }

  private
  JTextField
  getField(FormFieldKeys key)
  {
    return getForm().getField(key);
  }

  private
  Form
  getForm()
  {
    return itsForm;
  }

  private
  void
  setForm(Form form)
  {
    itsForm = form;
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  private Form itsForm;
}
