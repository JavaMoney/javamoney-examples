// BudgetPanel

package org.javamoney.examples.ez.money.gui.dialog.preferences;

import static org.javamoney.examples.ez.common.utility.BorderHelper.createTitledBorder;
import static org.javamoney.examples.ez.common.utility.ButtonHelper.buildButton;
import static org.javamoney.examples.ez.common.utility.I18NHelper.getSharedProperty;
import static org.javamoney.examples.ez.money.ApplicationProperties.UI_CURRENCY;
import static org.javamoney.examples.ez.money.ApplicationProperties.UI_DATE_FORMAT;
import static org.javamoney.examples.ez.money.model.DataManager.getIncome;

import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Date;

import javax.swing.AbstractButton;
import javax.swing.JTextField;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import org.javamoney.examples.ez.money.model.persisted.category.Category;
import org.javamoney.examples.ez.money.utility.TransactionDateHelper;

import org.javamoney.examples.ez.common.gui.CheckBox;
import org.javamoney.examples.ez.common.gui.Link;
import org.javamoney.examples.ez.common.gui.Panel;
import org.javamoney.examples.ez.common.utility.ClipboardMenuController;
import org.javamoney.examples.ez.common.utility.I18NHelper;
import org.javamoney.examples.ez.common.utility.TextConstrainer;

/**
 * This class facilitates managing the expense's budget.
 */
final
class
BudgetPanel
extends Panel
{
  /**
   * Constructs a new preferences panel.
   *
   * @param chooser The chooser that contains the categories.
   */
  protected
  BudgetPanel(PreferencesDataElementChooser chooser)
  {
    setAmountField(new JTextField());
    setChooser(chooser);

    createButtons();

    // Build panel.
    setFill(GridBagConstraints.BOTH);
    add(createInfoPanel(), 0, 0, 1, 1, 100, 100);

    // Initialize the panel with the selected budget.
    displayBudgetInfo();

    // Add listeners.
    getAmountField().addFocusListener(new FocusHandler());
    getAmountField().addKeyListener(new KeyHandler());
    getChooser().addTreeSelectionListener(new SelectionHandler());
    new ClipboardMenuController(getAmountField());
  }

  /**
   * This method updates this panel's collection.
   */
  protected
  void
  updateView()
  {
    getChooser().selectFirst();
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of private methods.
  //////////////////////////////////////////////////////////////////////////////

  private
  void
  createButtons()
  {
    ActionHandler handler = new ActionHandler();

    itsButtons = new AbstractButton[3];

    getButtons()[DATE] = new Link();
    getButtons()[ENABLE] = new CheckBox();
    getButtons()[ROLLOVER] = new CheckBox();

    // Build check boxes.
    buildButton(getButtons()[DATE], "", handler);
    buildButton(getButtons()[ENABLE], getProperty("enable"), handler);
    buildButton(getButtons()[ROLLOVER], getProperty("rollover"), handler);
  }

  private
  Panel
  createInfoPanel()
  {
    Panel panel = new Panel();

    // Build field.
    getAmountField().setDocument(new TextConstrainer(13, "0123456789,."));
    getAmountField().setHorizontalAlignment(JTextField.TRAILING);

    // Build panel.
    panel.setAnchor(GridBagConstraints.WEST);
    panel.add(getButtons()[ENABLE], 0, 0, 1, 1, 0, 50);
    panel.add(getButtons()[ROLLOVER], 0, 1, 1, 1, 0, 50);
    panel.add(getButtons()[DATE], 1, 1, 2, 1, 0, 0);

    panel.setAnchor(GridBagConstraints.EAST);
    panel.add(getProperty("budget") + ": ", 2, 0, 1, 1, 100, 0);

    panel.setFill(GridBagConstraints.HORIZONTAL);
    panel.add(getAmountField(), 3, 0, 1, 1, 0, 0);

    // Aesthetic spacers.
    panel.addEmptyCellAt(3, 1, 15);
    panel.addEmptyCellAt(4, 1);

    panel.setBorder(createTitledBorder(getSharedProperty("more_options")));

    return panel;
  }

  private
  void
  displayBudgetInfo()
  {
    Category category = (Category)getChooser().getSelectedElement();
    String amount = "";
    Date date = new Date();
    boolean canBeBudgeted = false;
    boolean hasRollover = false;
    boolean isBudgeted = false;

    if(category != null)
    {
      canBeBudgeted = category.canBeBudgeted();

      if(canBeBudgeted == true && category.isBudgeted() == true)
      {
        amount = UI_CURRENCY.format(Math.abs(category.getBudget()));
        date = category.getRolloverStartDate();
        hasRollover = category.hasRolloverBudget();
        isBudgeted = true;
      }
    }

    // Display budget data.
    getButtons()[DATE].setEnabled(hasRollover);
    getButtons()[DATE].setText(UI_DATE_FORMAT.format(date));
    getButtons()[ENABLE].setEnabled(canBeBudgeted);
    getButtons()[ENABLE].setSelected(isBudgeted);
    getButtons()[ROLLOVER].setEnabled(isBudgeted);
    getButtons()[ROLLOVER].setSelected(hasRollover);

    getAmountField().setEnabled(isBudgeted);
    getAmountField().setText(amount);
  }

  private
  JTextField
  getAmountField()
  {
    return itsAmountField;
  }

  private
  AbstractButton[]
  getButtons()
  {
    return itsButtons;
  }

  private
  PreferencesDataElementChooser
  getChooser()
  {
    return itsChooser;
  }

  private
  static
  String
  getProperty(String key)
  {
    return I18NHelper.getProperty("BudgetPanel." + key);
  }

  private
  void
  setAmountField(JTextField textField)
  {
    itsAmountField = textField;
  }

  private
  void
  setBudget()
  {
    Category category = (Category)getChooser().getSelectedElement();
    int amount = Math.abs(category.getBudget());

    try
    {
      amount = (int)UI_CURRENCY.parse(getAmountField().getText());
    }
    catch(Exception exception)
    {
      // Ignored.
    }

    // Budgets for income categories are negative behind the scenes and positive
    // in the UI so the sign needs to be flipped.
    if(getChooser().getCollection() == getIncome())
    {
      amount = -amount;
    }

    category.setBudget(amount);
  }

  private
  void
  setChooser(PreferencesDataElementChooser chooser)
  {
    itsChooser = chooser;
  }

  private
  void
  setIsBudgeted()
  {
    Category category = (Category)getChooser().getSelectedElement();

    category.setIsBudgeted(!category.isBudgeted());
    displayBudgetInfo();
  }

  private
  void
  setRolloverBudget()
  {
    Category category = (Category)getChooser().getSelectedElement();

    category.setHasRolloverBudget(!category.hasRolloverBudget());
    displayBudgetInfo();
  }

  private
  void
  setRolloverStartDate()
  {
    Category category = (Category)getChooser().getSelectedElement();
    Date date = TransactionDateHelper.showDateDialog(category.getRolloverStartDate());

    if(date != null)
    {
      category.setRolloverStartDate(date);
      getButtons()[DATE].setText(UI_DATE_FORMAT.format(date));
    }
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

      if(source == getButtons()[DATE])
      {
        setRolloverStartDate();
      }
      else if(source == getButtons()[ENABLE])
      {
        setIsBudgeted();
      }
      else if(source == getButtons()[ROLLOVER])
      {
        setRolloverBudget();
      }
    }
  }

  private
  class
  FocusHandler
  extends FocusAdapter
  {
    @Override
    public
    void
    focusLost(FocusEvent event)
    {
      Category category = (Category)getChooser().getSelectedElement();

      getAmountField().setText(UI_CURRENCY.format(Math.abs(category.getBudget())));
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
    keyReleased(KeyEvent event)
    {
      setBudget();
    }
  }

  private
  class
  SelectionHandler
  implements TreeSelectionListener
  {
    public
    void
    valueChanged(TreeSelectionEvent event)
    {
      displayBudgetInfo();
    }
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  private JTextField itsAmountField;
  private AbstractButton[] itsButtons;
  private PreferencesDataElementChooser itsChooser;

  private static final int DATE = 0;
  private static final int ENABLE = 1;
  private static final int ROLLOVER = 2;
}
