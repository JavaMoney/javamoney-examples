// StartupHelper

package org.javamoney.examples.ez.money.utility;

import static org.javamoney.examples.ez.common.utility.BoundsHelper.createCenteredScreenBounds;
import static org.javamoney.examples.ez.common.utility.DateHelper.createCalendar;
import static org.javamoney.examples.ez.common.utility.DateHelper.getStartOfCurrentMonth;
import static org.javamoney.examples.ez.common.utility.I18NHelper.getProperty;
import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.MONTH;
import static org.javamoney.examples.ez.money.ApplicationProperties.getDataFile;
import static org.javamoney.examples.ez.money.ApplicationProperties.getLastRuntime;
import static org.javamoney.examples.ez.money.ApplicationProperties.passwordRequired;
import static org.javamoney.examples.ez.money.ApplicationProperties.setPasswordRequired;
import static org.javamoney.examples.ez.money.ApplicationProperties.useDefaultDataFile;
import static org.javamoney.examples.ez.money.FileKeys.DATA;
import static org.javamoney.examples.ez.money.model.DataManager.getReminders;
import static org.javamoney.examples.ez.money.utility.FileMapHelper.getFileMap;
import static org.javamoney.examples.ez.money.utility.PasswordHelper.readPassword;
import static org.javamoney.examples.ez.money.utility.StarterKitHelper.createStarterKit;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

import org.javamoney.examples.ez.money.ApplicationProperties;
import org.javamoney.examples.ez.money.gui.dialog.PasswordAuthenticationDialog;
import org.javamoney.examples.ez.money.model.DataElement;
import org.javamoney.examples.ez.money.model.DataManager;
import org.javamoney.examples.ez.money.model.persisted.reminder.Reminder;

import org.javamoney.examples.ez.common.gui.Panel;

/**
 * This class handles the program's startup process. It ensures that data is
 * read from file and any pre-startup processes are run. All methods in this
 * class are static.
 */
public
final
class
StartupHelper
{
  /**
   * This method runs the program's startup process.
   */
  public
  static
  void
  runStartup()
  {
    JFrame splash = createSplash();
    Panel panel = (Panel)splash.getContentPane();
    JProgressBar progress = new JProgressBar(0, 4);
    long timeTaken = System.currentTimeMillis();

    // Build panel.
    panel.add(getProperty("StartupHelper.loading"), 0, 0, 1, 1, 0, 50);

    panel.setFill(GridBagConstraints.HORIZONTAL);
    panel.add(progress, 0, 1, 1, 1, 100, 50);

    panel.setBorder(new SoftBevelBorder(BevelBorder.RAISED));
    panel.setInsets(new Insets(5, 10, 5, 10));

    // Show frame.
    splash.setVisible(true);

    // Do all startup processes and update progress bar after each action.
    progress.setBorderPainted(true);
    progress.setStringPainted(true);

    ApplicationProperties.read();
    progress.setValue(1);

    if(passwordRequired() == true)
    {
      checkPassword(splash);
    }

    if(useDefaultDataFile() == true)
    {
      if(getFileMap().get(DATA).exists() == true)
      {
        DataManager.read();
      }
      else
      {
        createStarterKit();
      }
    }
    else
    {
      DataManager.read(getDataFile(), true, passwordRequired());
    }

    progress.setValue(2);

    doPreStartupProcesses();
    progress.setValue(3);

    // Finish off the progress bar and dispose the splash.
    progress.setValue(4);

    timeTaken = System.currentTimeMillis() - timeTaken;

    try
    {
      // Sleep to see the splash if the time taken is less than 2.5 seconds.
      long sleep = 2500 - timeTaken;

      if(sleep > 0)
      {
        Thread.sleep(sleep);
      }
    }
    catch(InterruptedException interruptedException)
    {
      // Ignored.
    }

    splash.dispose();
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of private methods.
  //////////////////////////////////////////////////////////////////////////////

  private
  static
  void
  checkPassword(JFrame owner)
  {
    String password = readPassword();

    // If the password file was deleted, then there is no point to authenticate.
    if(password == null)
    {
      setPasswordRequired(false);
    }
    else
    {
      if(new PasswordAuthenticationDialog(owner, password).showDialog() == false)
      {
        System.exit(1);
      }
    }
  }

  private
  static
  JFrame
  createSplash()
  {
    JFrame splash = new JFrame();

    // Build frame.
    splash.setBounds(createCenteredScreenBounds(350, 85));
    splash.setContentPane(new Panel());
    splash.setResizable(false);
    splash.setUndecorated(true);

    return splash;
  }

  private
  static
  void
  doPreStartupProcesses()
  {
    doStartupForReminders();
  }

  private
  static
  void
  doStartupForReminders()
  {
    Date lastRuntime = getLastRuntime();
    Date currentMonth = getStartOfCurrentMonth();

    // If the last runtime is before the start of the current month, then mark
    // all reoccurring reminders as incomplete.
    if(lastRuntime.compareTo(currentMonth) < 0)
    {
      for(DataElement element : getReminders().getCollection())
      {
        Reminder reminder = (Reminder)element;

        // Only modify reminders that are already complete and whose due by
        // dates are before the current month.
        if(reminder.isComplete() == true && currentMonth.after(reminder.getDueBy()) == true)
        {
          GregorianCalendar calendar = createCalendar(reminder.getDueBy());

          // Set the complete by date to the next month while making sure the
          // day of month does not exceed the maximum.
          int day = calendar.get(DAY_OF_MONTH);

          calendar.set(DAY_OF_MONTH, 1);
          calendar.set(MONTH, calendar.get(MONTH) + 1);
          calendar.set(DAY_OF_MONTH, Math.min(day, calendar.getActualMaximum(DAY_OF_MONTH)));

          reminder.setDueBy(calendar.getTime());
          reminder.setIsComplete(false);
        }
      }
    }
  }
}
