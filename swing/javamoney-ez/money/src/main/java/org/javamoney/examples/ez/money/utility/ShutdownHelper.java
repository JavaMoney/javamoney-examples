// ShutdownHelper

package org.javamoney.examples.ez.money.utility;

import static java.awt.Frame.MAXIMIZED_BOTH;
import static org.javamoney.examples.ez.money.ApplicationThread.getFrame;

import java.util.Date;

import org.javamoney.examples.ez.money.ApplicationProperties;
import org.javamoney.examples.ez.money.model.DataManager;

/**
 * This class handles the program's shutdown process. It ensures that data is
 * written to file before the program exits. All methods in this class are
 * static.
 */
public
final
class
ShutdownHelper
{
  /**
   * This method causes the program to initiate the shutdown procedure and exit.
   */
  public
  static
  void
  doShutdown()
  {
    // Store properties.
    if(getFrame().getExtendedState() != MAXIMIZED_BOTH)
    {
      ApplicationProperties.setFrameBounds(getFrame().getBounds());
    }

    ApplicationProperties.setFrameState(getFrame().getExtendedState());
    ApplicationProperties.setLastRuntime(new Date());

    // Make it appear the application has already quit.
    getFrame().setVisible(false);

    // Write data.
    ApplicationProperties.write();

    if(ApplicationProperties.useDefaultDataFile() == true)
    {
      DataManager.write();
    }
    else
    {
      DataManager.write(ApplicationProperties.getDataFile(), true, ApplicationProperties.passwordRequired());
    }

    // Automatically create a backup?
    if(ApplicationProperties.autoBackup() == true)
    {
      DataManager.write(ApplicationProperties.getBackupFile(), true, false);
    }

    // Exit the application.
    System.exit(0);
  }
}
