// ApplicationThread

package org.javamoney.examples.ez.money;

import static org.javamoney.examples.ez.money.ApplicationProperties.getFrameBounds;
import static org.javamoney.examples.ez.money.ApplicationProperties.getFrameState;

import org.javamoney.examples.ez.money.gui.MainFrame;
import org.javamoney.examples.ez.money.gui.dialog.ReminderAlarmDialog;
import org.javamoney.examples.ez.money.gui.view.ViewKeys;

/**
 * This class facilitates launching the main application frame in a separate
 * thread.
 */
public
final
class
ApplicationThread
extends Thread
{
  /**
   * This method returns the main application frame that the user interacts
   * with.
   *
   * @return The main application frame that the user interacts with.
   */
  public
  static
  MainFrame
  getFrame()
  {
    return itsFrame;
  }

  /**
   * This method launches the main application frame in a separate thread.
   */
  @Override
  public
  void
  run()
  {
    setFrame(new MainFrame());

    getFrame().showFrame(getFrameBounds(), getFrameState());

    getFrame().getViews().showView(ViewKeys.HOME);

    // Display any reminder alarms, if any.
    new ReminderAlarmDialog().showDialog();
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of private methods.
  //////////////////////////////////////////////////////////////////////////////

  private
  static
  void
  setFrame(MainFrame frame)
  {
    itsFrame = frame;
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  private static MainFrame itsFrame;
}
