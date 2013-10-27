// CategoryTotalsFilterDialog

package org.javamoney.examples.ez.money.gui.dialog;

import static org.javamoney.examples.ez.common.utility.BorderHelper.createTitledBorder;
import static org.javamoney.examples.ez.common.utility.ButtonHelper.buildButton;
import static org.javamoney.examples.ez.common.utility.I18NHelper.getSharedProperty;
import static org.javamoney.examples.ez.money.IconKeys.DIALOG_FILTER;
import static org.javamoney.examples.ez.money.model.DataManager.getAccounts;
import static org.javamoney.examples.ez.money.model.DataManager.getExpenses;
import static org.javamoney.examples.ez.money.model.DataManager.getIncome;
import static org.javamoney.examples.ez.money.model.DataManager.getPayees;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.TreeSet;

import org.javamoney.examples.ez.money.gui.chooser.FilterElementChooser;
import org.javamoney.examples.ez.money.gui.chooser.YesNoRadioChooser;
import org.javamoney.examples.ez.money.model.DataElement;
import org.javamoney.examples.ez.money.model.dynamic.total.TotalFilter;

import org.javamoney.examples.ez.common.gui.CheckBox;
import org.javamoney.examples.ez.common.gui.DialogHeader;
import org.javamoney.examples.ez.common.gui.Panel;
import org.javamoney.examples.ez.common.gui.ScrollPane;
import org.javamoney.examples.ez.common.utility.I18NHelper;

/**
 * This class facilitates constraining category totals to a specific set of
 * criteria.
 */
public
final
class
CategoryTotalsFilterDialog
extends ApplicationDialog
{
  /**
   * Constructs a new dialog.
   */
  public
  CategoryTotalsFilterDialog(TotalFilter filter)
  {
    super(700, 425);

    setCheckBox(new CheckBox());
    setFilter(filter);
    setReconcileChooser(new YesNoRadioChooser());

    createChoosers();

    buildPanel();
  }

  /**
   * This method displays the dialog and returns true if it was accepted,
   * otherwise false.
   *
   * @return true or false.
   */
  public
  boolean
  showDialog()
  {
    runDialog();

    if(wasAccepted() == true)
    {
      getFilter().setAccounts(getChoosers()[ACCOUNTS].getSelectedValues());
      getFilter().setCategories(getChoosers()[CATEGORIES].getSelectedValues());
      getFilter().setPayees(getChoosers()[PAYEES].getSelectedValues());
    }

    return wasAccepted();
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
    panel.setFill(GridBagConstraints.BOTH);
    panel.add(createDialogHeader(), 0, 0, 1, 1, 0, 0);
    panel.add(createFilterPanel(), 0, 1, 1, 1, 100, 100);
    panel.add(createMoreOptionsPanel(), 0, 2, 1, 1, 0, 0);
    panel.add(createOKCancelButtonPanel(new ActionHandler()), 0, 3, 1, 1, 0, 0);
  }

  private
  Panel
  createChooserPanel(FilterElementChooser chooser, String title)
  {
    Panel panel = new Panel();
    ActionHandler handler = new ActionHandler();
    CheckBox checkBox = new CheckBox();

    // Build check box.
    buildButton(checkBox, getSharedProperty("all"), handler, title);
    checkBox.setSelected(chooser.isEnabled() == false);

    // Build panel.
    panel.setAnchor(GridBagConstraints.WEST);
    panel.add(checkBox, 0, 0, 1, 1, 100, 0);

    panel.setFill(GridBagConstraints.BOTH);
    panel.add(new ScrollPane(chooser), 0, 1, 1, 1, 0, 100);

    panel.setBorder(createTitledBorder(title + " " + getProperty("report_on")));

    return panel;
  }

  private
  void
  createChoosers()
  {
    Collection<DataElement> categories = new TreeSet<DataElement>();

    itsChoosers = new FilterElementChooser[3];

    // Combine expenses and income.
    categories.addAll(getExpenses().getCollection());
    categories.addAll(getIncome().getCollection());

    getChoosers()[ACCOUNTS] = new FilterElementChooser(getAccounts().getCollection());
    getChoosers()[CATEGORIES] = new FilterElementChooser(categories);
    getChoosers()[PAYEES] = new FilterElementChooser(getPayees().getCollection());

    getChoosers()[ACCOUNTS].selectValues(getFilter().getAccounts());
    getChoosers()[CATEGORIES].selectValues(getFilter().getCategories());
    getChoosers()[PAYEES].selectValues(getFilter().getPayees());

    // Determine initial enabled state.
    getChoosers()[ACCOUNTS].setEnabled(getFilter().getAccounts().length != 0);
    getChoosers()[CATEGORIES].setEnabled(getFilter().getCategories().length != 0);
    getChoosers()[PAYEES].setEnabled(getFilter().getPayees().length != 0);
  }

  private
  static
  DialogHeader
  createDialogHeader()
  {
    return new DialogHeader(getProperty("header.title"),
        getProperty("header.description"), DIALOG_FILTER.getIcon());
  }

  private
  Panel
  createFilterPanel()
  {
    Panel panel = new Panel();

    // Build panel.
    panel.setFill(GridBagConstraints.BOTH);
    panel.add(createChooserPanel(getChoosers()[ACCOUNTS], ACTION_ACCOUNTS), 0, 0, 1, 1, 50, 100);
    panel.addEmptyCellAt(1, 0);
    panel.add(createChooserPanel(getChoosers()[CATEGORIES], ACTION_CATEGORIES), 2, 0, 1, 1, 50, 0);
    panel.addEmptyCellAt(3, 0);
    panel.add(createChooserPanel(getChoosers()[PAYEES], ACTION_PAYEES), 4, 0, 1, 1, 50, 0);

    panel.setInsets(new Insets(10, 10, 10, 10));

    return panel;
  }

  private
  Panel
  createMoreOptionsPanel()
  {
    Panel panel = new Panel();
    ActionHandler handler = new ActionHandler();

    // Build check box.
    buildButton(getCheckBox(), getSharedProperty("reconciled") + ":", handler);

    getCheckBox().setSelected(getFilter().reconciledEnabled());
    getReconcileChooser().setEnabled(getCheckBox().isSelected());
    getReconcileChooser().setNoSelected(!getFilter().reconciledOnly());
    getReconcileChooser().setYesSelected(getFilter().reconciledOnly());

    // Build panel.
    panel.setAnchor(GridBagConstraints.WEST);
    panel.add(getCheckBox(), 0, 0, 1, 1, 0, 100);
    panel.add(getReconcileChooser(), 1, 0, 1, 1, 100, 0);

    panel.setBorder(createTitledBorder(getSharedProperty("more_options"), false));

    // Add listeners.
    getReconcileChooser().addActionListener(handler);

    return panel;
  }

  private
  CheckBox
  getCheckBox()
  {
    return itsCheckBox;
  }

  private
  FilterElementChooser[]
  getChoosers()
  {
    return itsChoosers;
  }

  private
  TotalFilter
  getFilter()
  {
    return itsFilter;
  }

  private
  static
  String
  getProperty(String key)
  {
    return I18NHelper.getProperty("CategoryTotalsFilterDialog." + key);
  }

  private
  YesNoRadioChooser
  getReconcileChooser()
  {
    return itsReconcileChooser;
  }

  private
  void
  setCheckBox(CheckBox checkBox)
  {
    itsCheckBox = checkBox;
  }

  private
  void
  setFilter(TotalFilter filter)
  {
    itsFilter = filter;
  }

  private
  void
  setReconcileChooser(YesNoRadioChooser chooser)
  {
    itsReconcileChooser = chooser;
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
      Object source = event.getSource();
      String command = event.getActionCommand();

      if(source == getCheckBox())
      {
        getFilter().setReconciledEnabled(getCheckBox().isSelected());
        getReconcileChooser().setEnabled(getCheckBox().isSelected());
      }
      else if(source == getReconcileChooser())
      {
        getFilter().setReconciledOnly(getReconcileChooser().isYesSelected());
      }
      else if(command.equals(ACTION_ACCOUNTS) == true)
      {
        getChoosers()[ACCOUNTS].setEnabled(!((CheckBox)source).isSelected());
      }
      else if(command.equals(ACTION_CATEGORIES) == true)
      {
        getChoosers()[CATEGORIES].setEnabled(!((CheckBox)source).isSelected());
      }
      else if(command.equals(ACTION_PAYEES) == true)
      {
        getChoosers()[PAYEES].setEnabled(!((CheckBox)source).isSelected());
      }
      else
      {
        setAccepted(command.equals(ACTION_OK));
        dispose();
      }
    }
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  private CheckBox itsCheckBox;
  private FilterElementChooser[] itsChoosers;
  private TotalFilter itsFilter;
  private YesNoRadioChooser itsReconcileChooser;

  private static final int ACCOUNTS = 0;
  private static final int CATEGORIES = 1;
  private static final int PAYEES = 2;

  private static final String ACTION_ACCOUNTS = getSharedProperty("accounts");
  private static final String ACTION_CATEGORIES = getSharedProperty("categories");
  private static final String ACTION_PAYEES = getSharedProperty("payees");
}
