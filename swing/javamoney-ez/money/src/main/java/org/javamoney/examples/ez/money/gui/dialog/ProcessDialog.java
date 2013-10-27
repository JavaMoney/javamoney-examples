// ProcessDialog

package org.javamoney.examples.ez.money.gui.dialog;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JProgressBar;

import org.javamoney.examples.ez.common.gui.Panel;
import org.javamoney.examples.ez.common.utility.I18NHelper;

/**
 * This class facilitates doing a process, providing the user with a dialog that
 * indicates the progress, and the ability to cancel the process.
 */
public
abstract
class
ProcessDialog
extends ApplicationDialog
{
  /**
   * Constructs a new process dialog with the specified parameters.
   *
   * @param message The message to display to the user.
   * @param max The max value the process will have or 0 if indeterminate.
   */
  public
  ProcessDialog(String message, int max)
  {
    super(getProperty("title"), 0, 0);

    setCanProcess(true);
    setLabel(new JLabel(message));
    setProgressBar(new JProgressBar(0, max));

    if(max == 0)
    {
      getProgressBar().setIndeterminate(true);
      getProgressBar().setString("");
      getProgressBar().setStringPainted(true);
    }

    buildPanel();

    setMessage(message);
  }

  /**
   * This method starts the processing and displays the dialog.
   */
  public
  final
  void
  showDialog()
  {
    // Start the processing.
    new ProcessThread().start();

    runDialog();
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of protected methods.
  //////////////////////////////////////////////////////////////////////////////

  /**
   * This method returns true if the process should continue on, otherwise
   * false.
   *
   * @return true or false.
   */
  protected
  final
  boolean
  canProcess()
  {
    return itsCanProcess;
  }

  /**
   * This method is where implementing classes will do their work. This method
   * is invoked via an internal thread that is started when showDialog() is
   * called.
   */
  protected
  abstract
  void
  doProcess();

  /**
   * This method returns the current value of the process. The value being how
   * far along the process is.
   *
   * @return The current value of the process.
   */
  protected
  final
  int
  getValue()
  {
    return getProgressBar().getValue();
  }

  /**
   * This method sets the message to display to the user.
   *
   * @param message The message to display to the user.
   */
  protected
  final
  void
  setMessage(String message)
  {
    int width = getLabel().getFontMetrics(getLabel().getFont()).stringWidth(message);

    getLabel().setText(message);

    // Resize dialog to fit message.
    setSize(width + 50, 150);
  }

  /**
   * This method sets the current value of the process. The value being how far
   * along the process is.
   *
   * @param value The current value of the process.
   */
  protected
  final
  void
  setValue(int value)
  {
    getProgressBar().setValue(value);
  }

  /**
   * This method disposes the dialog, thus returning execution to the caller.
   * The dialog's accept flag will be true if the process was not canceled.
   */
  protected
  final
  void
  signalProcessIsDone()
  {
    setAccepted(canProcess() == true);
    dispose();
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of private methods.
  //////////////////////////////////////////////////////////////////////////////

  private
  void
  buildPanel()
  {
    Panel panel = getContentPane();

    // Build panel.
    panel.setAnchor(GridBagConstraints.SOUTH);
    panel.add(getLabel(), 0, 0, 1, 1, 100, 100);
    panel.addEmptyCellAt(0, 1);

    panel.setFill(GridBagConstraints.BOTH);
    panel.add(createProgressPanel(), 0, 2, 1, 1, 0, 0);
    panel.add(createCancelButtonPanel(new ActionHandler()), 0, 3, 1, 1, 0, 0);
  }

  private
  Panel
  createProgressPanel()
  {
    Panel panel = new Panel();

    getProgressBar().setStringPainted(true);

    // Build panel.
    panel.setFill(GridBagConstraints.HORIZONTAL);
    panel.add(getProgressBar(), 0, 0, 1, 1, 100, 100);

    panel.setInsets(new Insets(0, 25, 0, 25));

    return panel;
  }

  private
  JLabel
  getLabel()
  {
    return itsLabel;
  }

  private
  JProgressBar
  getProgressBar()
  {
    return itsProgressBar;
  }

  private
  static
  String
  getProperty(String key)
  {
    return I18NHelper.getProperty("ProcessDialog." + key);
  }

  private
  void
  setCanProcess(boolean value)
  {
    itsCanProcess = value;
  }

  private
  void
  setLabel(JLabel label)
  {
    itsLabel = label;
  }

  private
  void
  setProgressBar(JProgressBar progressBar)
  {
    itsProgressBar = progressBar;
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of inner classes.
  //////////////////////////////////////////////////////////////////////////////

  private
  class
  ActionHandler
  implements ActionListener
  {
    public
    void
    actionPerformed(ActionEvent event)
    {
      setAccepted(event.getActionCommand().equals(ACTION_OK));
      dispose();

      setCanProcess(wasAccepted() == true);
    }
  }

  private
  class
  ProcessThread
  extends Thread
  {
    @Override
    public
    void
    run()
    {
      doProcess();
    }
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  private boolean itsCanProcess;
  private JLabel itsLabel;
  private JProgressBar itsProgressBar;
}
