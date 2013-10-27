// ActionSignaler

package org.javamoney.examples.ez.common.utility;

import static java.awt.event.ActionEvent.ACTION_PERFORMED;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.LinkedList;

/**
 * This class facilitates maintaining a list of action listeners that can be
 * signaled with events. The purpose of this class is to provide objects that
 * are not normally associated with actions the ability to be so.
 */
public
final
class
ActionSignaler
{
  /**
   * Constructs a new action signaler.
   */
  public
  ActionSignaler()
  {
    setListeners(new LinkedList<ActionListener>());
  }

  /**
   * This method adds the specified listener to the signaler's list.
   *
   * @param listener The listener to add.
   */
  public
  void
  addListener(ActionListener listener)
  {
    getListeners().add(listener);
  }

  /**
   * This method signals all the signaler's listeners.
   *
   * @param source The source of the event.
   * @param command The action command for the event.
   */
  public
  void
  sendSignal(Object source, String command)
  {
    ActionEvent event = new ActionEvent(source, ACTION_PERFORMED, command);

    for(ActionListener listener : getListeners())
    {
      listener.actionPerformed(event);
    }
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of private methods.
  //////////////////////////////////////////////////////////////////////////////

  private
  Collection<ActionListener>
  getListeners()
  {
    return itsListeners;
  }

  private
  void
  setListeners(Collection<ActionListener> collection)
  {
    itsListeners = collection;
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  private Collection<ActionListener> itsListeners;
}
