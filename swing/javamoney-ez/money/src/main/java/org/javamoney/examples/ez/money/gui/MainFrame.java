// MainFrame

package org.javamoney.examples.ez.money.gui;

import static org.javamoney.examples.ez.common.CommonConstants.IS_MAC;
import static org.javamoney.examples.ez.common.utility.ButtonHelper.buildButton;
import static org.javamoney.examples.ez.common.utility.I18NHelper.getSharedProperty;
import static org.javamoney.examples.ez.common.utility.ResourceHelper.openURL;
import static javax.swing.JOptionPane.PLAIN_MESSAGE;
import static javax.swing.JOptionPane.showMessageDialog;
import static org.javamoney.examples.ez.money.IconKeys.WINDOWS_LOGO;
import static org.javamoney.examples.ez.money.gui.dialog.PreferencesDialog.showPreferencesDialog;
import static org.javamoney.examples.ez.money.utility.BackupHelper.makeBackup;
import static org.javamoney.examples.ez.money.utility.BackupHelper.restoreFromBackup;
import static org.javamoney.examples.ez.money.utility.DialogHelper.decide;
import static org.javamoney.examples.ez.money.utility.ShutdownHelper.doShutdown;
import static org.javamoney.examples.ez.money.utility.UpdateHelper.CURRENT_VERSION;
import static org.javamoney.examples.ez.money.utility.UpdateHelper.checkForUpdates;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;


import org.javamoney.examples.ez.money.gui.dialog.AccountStatementDialog;
import org.javamoney.examples.ez.money.gui.dialog.BudgetReportDialog;
import org.javamoney.examples.ez.money.gui.dialog.CategoryReportDialog;
import org.javamoney.examples.ez.money.gui.dialog.UninstallDialog;
import org.javamoney.examples.ez.money.gui.view.ViewsPanel;
import org.javamoney.examples.ez.money.importexport.CSVExporter;
import org.javamoney.examples.ez.money.importexport.CSVImporter;
import org.javamoney.examples.ez.money.importexport.OFXImporter;
import org.javamoney.examples.ez.money.importexport.QIFExporter;
import org.javamoney.examples.ez.money.importexport.QIFImporter;

//import com.apple.eawt.Application;
//import com.apple.eawt.ApplicationAdapter;
//import com.apple.eawt.ApplicationEvent;
import org.javamoney.examples.ez.common.gui.Frame;
import org.javamoney.examples.ez.common.gui.Panel;
import org.javamoney.examples.ez.common.utility.I18NHelper;

/**
 * This class is the main application frame that the user interacts with.
 */
public
final
class
MainFrame
extends Frame
{
  /**
   * Constructs a new main application frame.
   */
  public
  MainFrame()
  {
    setContentPane(new ViewsPanel());
    setIconImage(WINDOWS_LOGO.getIcon().getImage());
    setTitle(APPLICATION_NAME);

    buildMenus();
//    buildMacMenus();

    // Add listeners.
    addWindowListener(new WindowHandler());
  }

  /**
   * This method returns the panel that manages the views.
   *
   * @return The panel that manages the views.
   */
  public
  ViewsPanel
  getViews()
  {
    return (ViewsPanel)getContentPane();
  }

  /**
   * This method updates all necessary components to reflect a data change.
   */
  public
  void
  signalDataChange()
  {
    getViews().updateView();
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of private methods.
  //////////////////////////////////////////////////////////////////////////////

//  private
//  void
//  buildMacMenus()
//  {
//    Application application = Application.getApplication();
//
//    application.addApplicationListener(new MacApplicationHandler());
//    application.addPreferencesMenuItem();
//    application.setEnabledPreferencesMenu(true);
//  }

  private
  void
  buildMenus()
  {
    JMenuBar menuBar = new JMenuBar();
    ActionHandler handler = new ActionHandler();
    JMenu backup = new JMenu(getProperty("menu.backup"));
    JMenu file = new JMenu(getProperty("menu.file"));
    JMenu help = new JMenu(getProperty("menu.help"));
    JMenu importExport = new JMenu(getProperty("menu.import_export"));
    JMenu reports = new JMenu(getProperty("menu.reports"));

    backup.add(createMenuItem(ACTION_BACKUP, handler));
    backup.add(createMenuItem(ACTION_RESTORE, handler));

    file.add(backup);
    file.addSeparator();
    file.add(importExport);
    file.addSeparator();
    file.add(reports);

    // Platform specific.
    if(IS_MAC == false)
    {
      file.addSeparator();
      file.add(createMenuItem(ACTION_PREFERENCES, handler));
      file.addSeparator();
      file.add(createMenuItem(ACTION_QUIT, handler));

      help.add(createMenuItem(ACTION_ABOUT, handler));
      help.addSeparator();
    }

    help.add(createMenuItem(ACTION_WEB, handler));
    help.addSeparator();
    help.add(createMenuItem(ACTION_UPDATE, handler));
    help.addSeparator();
    help.add(createMenuItem(ACTION_UNINSTALL, handler));

    importExport.add(createMenuItem(ACTION_IMPORT_QIF, handler));
    importExport.add(createMenuItem(ACTION_EXPORT_QIF, handler));
    importExport.addSeparator();
    importExport.add(createMenuItem(ACTION_IMPORT_CSV, handler));
    importExport.add(createMenuItem(ACTION_EXPORT_CSV, handler));
    importExport.addSeparator();
    importExport.add(createMenuItem(ACTION_IMPORT_OFX, handler));

    reports.add(createMenuItem(ACTION_STATEMENT, handler));
    reports.add(createMenuItem(ACTION_BUDGET_REPORT, handler));
    reports.add(createMenuItem(ACTION_CATEGORY_REPORT, handler));

    menuBar.add(file);
    menuBar.add(help);

    // Provide access to the menus.
    setJMenuBar(menuBar);
  }

  private
  static
  JMenuItem
  createMenuItem(String action, ActionHandler handler)
  {
    JMenuItem item = new JMenuItem();

    // Build item.
    buildButton(item, action, handler);

    return item;
  }

  private
  static
  String
  getProperty(String key)
  {
    return I18NHelper.getProperty("MainFrame." + key);
  }

  private
  void
  restore()
  {
    if(decide(getProperty("restore.title"),
        getProperty("restore.description")) == true)
    {
      restoreFromBackup();
    }
  }

  private
  void
  showAbout()
  {
    Panel panel = new Panel();
    JLabel name = new JLabel(APPLICATION_NAME);
    JLabel version = new JLabel();

    // Customize look to be the same as the Mac's about box.
    name.setFont(name.getFont().deriveFont(14.0f));
    name.setFont(name.getFont().deriveFont(Font.BOLD));

    version.setFont(version.getFont().deriveFont(10.0f));
    version.setFont(version.getFont().deriveFont(Font.PLAIN));
    version.setText(getProperty("about.version") + " " + CURRENT_VERSION.toString());

    // Build panel.
    panel.addEmptyCellAt(0, 0);
    panel.add(WINDOWS_LOGO.getIcon(), 0, 1, 1, 1, 100, 0);
    panel.addEmptyCellAt(0, 2);
    panel.add(name, 0, 3, 1, 1, 0, 0);
    panel.addEmptyCellAt(0, 4);

    panel.setAnchor(GridBagConstraints.NORTH);
    panel.add(version, 0, 5, 1, 1, 0, 100);
    panel.addEmptyCellAt(0, 6);

    showMessageDialog(this, panel, "", PLAIN_MESSAGE);
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
      String command = event.getActionCommand();

      if(command.equals(ACTION_ABOUT) == true)
      {
        showAbout();
      }
      else if(command.equals(ACTION_BACKUP) == true)
      {
        makeBackup();
      }
      else if(command.equals(ACTION_BUDGET_REPORT) == true)
      {
        new BudgetReportDialog().showDialog();
      }
      else if(command.equals(ACTION_CATEGORY_REPORT) == true)
      {
        new CategoryReportDialog().showDialog();
      }
      else if(command.equals(ACTION_EXPORT_CSV) == true)
      {
        new CSVExporter().doExport();
      }
      else if(command.equals(ACTION_EXPORT_QIF) == true)
      {
        new QIFExporter().doExport();
      }
      else if(command.equals(ACTION_IMPORT_CSV) == true)
      {
        new CSVImporter().doImport();
      }
      else if(command.equals(ACTION_IMPORT_OFX) == true)
      {
        new OFXImporter().doImport();
      }
      else if(command.equals(ACTION_IMPORT_QIF) == true)
      {
        new QIFImporter().doImport();
      }
      else if(command.equals(ACTION_PREFERENCES) == true)
      {
        showPreferencesDialog();
      }
      else if(command.equals(ACTION_QUIT) == true)
      {
        doShutdown();
      }
      else if(command.equals(ACTION_RESTORE) == true)
      {
        restore();
      }
      else if(command.equals(ACTION_STATEMENT) == true)
      {
        new AccountStatementDialog().showDialog();
      }
      else if(command.equals(ACTION_UNINSTALL) == true)
      {
        new UninstallDialog().showDialog();
      }
      else if(command.equals(ACTION_UPDATE) == true)
      {
        checkForUpdates();
      }
      else if(command.equals(ACTION_WEB) == true)
      {
        openURL(URL);
      }
    }
  }

//  private
//  class
//  MacApplicationHandler
//  extends ApplicationAdapter
//  {
//    @Override
//    public
//    void
//    handlePreferences(ApplicationEvent event)
//    {
//      showPreferencesDialog();
//    }
//
//    @Override
//    public
//    void
//    handleQuit(ApplicationEvent event)
//    {
//      doShutdown();
//    }
//  }

  private
  class
  WindowHandler
  extends WindowAdapter
  {
    @Override
    public
    void
    windowClosed(WindowEvent event)
    {
      doShutdown();
    }

    @Override
    public
    void
    windowClosing(WindowEvent event)
    {
      doShutdown();
    }
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  private static final String ACTION_ABOUT = getProperty("option.about");
  private static final String ACTION_BACKUP = getProperty("option.backup");
  private static final String ACTION_BUDGET_REPORT = getProperty("option.budget_report");
  private static final String ACTION_CATEGORY_REPORT = getProperty("option.category_report");
  private static final String ACTION_EXPORT_CSV = getProperty("option.export_csv");
  private static final String ACTION_EXPORT_QIF = getProperty("option.export_qif");
  private static final String ACTION_IMPORT_CSV = getProperty("option.import_csv");
  private static final String ACTION_IMPORT_OFX = getProperty("option.import_ofx");
  private static final String ACTION_IMPORT_QIF = getProperty("option.import_qif");
  private static final String ACTION_PREFERENCES = getProperty("option.preferences");
  private static final String ACTION_QUIT = getProperty("option.quit");
  private static final String ACTION_RESTORE = getProperty("option.restore");
  private static final String ACTION_STATEMENT = getProperty("option.statement");
  private static final String ACTION_UNINSTALL = getProperty("option.uninstall");
  private static final String ACTION_UPDATE = getProperty("option.update");
  private static final String ACTION_WEB = getProperty("option.web");
  private static final String URL = getSharedProperty("url");

  private static final String APPLICATION_NAME = "JavaMoney EZ";
}
