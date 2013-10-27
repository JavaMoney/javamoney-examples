// SplitDialog

package org.javamoney.examples.ez.money.gui.dialog;

import static org.javamoney.examples.ez.common.utility.BorderHelper.createTitledBorder;
import static org.javamoney.examples.ez.common.utility.ButtonHelper.buildButton;
import static org.javamoney.examples.ez.common.utility.I18NHelper.getSharedProperty;
import static org.javamoney.examples.ez.money.ApplicationProperties.UI_CURRENCY;
import static org.javamoney.examples.ez.money.KeywordKeys.NOT_CATEGORIZED;
import static org.javamoney.examples.ez.money.gui.table.SplitTable.AMOUNT_COLUMN;
import static org.javamoney.examples.ez.money.gui.table.SplitTable.CATEGORY_COLUMN;
import static org.javamoney.examples.ez.money.model.dynamic.transaction.Split.AMOUNT_SEPARATOR;
import static org.javamoney.examples.ez.money.model.dynamic.transaction.Split.ITEM_SEPARATOR;
import static org.javamoney.examples.ez.money.model.dynamic.transaction.Split.ITEM_SEPARATOR_CHAR;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import org.javamoney.examples.ez.money.IconKeys;
import org.javamoney.examples.ez.money.gui.GUIConstants;
import org.javamoney.examples.ez.money.gui.table.SplitTable;
import org.javamoney.examples.ez.money.locale.Currency;
import org.javamoney.examples.ez.money.locale.CurrencyFormatKeys;
import org.javamoney.examples.ez.money.model.dynamic.transaction.Split;
import org.javamoney.examples.ez.money.model.persisted.category.CategoryCollection;

import org.javamoney.examples.ez.common.gui.DialogHeader;
import org.javamoney.examples.ez.common.gui.Link;
import org.javamoney.examples.ez.common.gui.Panel;
import org.javamoney.examples.ez.common.gui.ScrollPane;
import org.javamoney.examples.ez.common.utility.I18NHelper;

/**
 * This class facilitates building a split for a transaction.
 */
public
final
class
SplitDialog
extends ApplicationDialog
{
  /**
   * Constructs a new split dialog that will reference the specified split, have
   * the specified transaction total, and provide the specified categories to be
   * selected.
   *
   * @param collection The categories to provide in the table to be selected.
   * @param split The split to reference.
   * @param total The total amount of the transaction.
   */
  public
  SplitDialog(CategoryCollection collection, String split, double total)
  {
    super(575, 425);

    setAllowEvents(true);
    setTable(new SplitTable(collection));
    setTotal(total);

    createLabels();

    buildPanel();

    initializeTable(split, getTotal());

    // Add listeners.
    getTable().getModel().addTableModelListener(new TableModelHandler());
  }

  /**
   * This method returns the formatted split, or null if the dialog was
   * cancelled or the split is empty.
   *
   * @return The formatted split.
   */
  public
  String
  getSplit()
  {
    String str = "";

    if(wasAccepted() == true)
    {
      for(int len = 0; len < getTable().getRowCount(); ++len)
      {
        try
        {
          String amount = (String)getTable().getValueAt(len, AMOUNT_COLUMN);
          String category = (String)getTable().getValueAt(len, CATEGORY_COLUMN);

          if(category.equals(NOT_CATEGORIZED.toString()) == true)
          {
            category = "";
          }

          str += ITEM_SEPARATOR + category + AMOUNT_SEPARATOR +
            US_DOLLAR.format(UI_CURRENCY.parse(amount));
        }
        catch(Exception exception)
        {
          // Ignored.
        }
      }
    }

    if(str.length() == 0)
    {
      str = null;
    }

    return str;
  }

  /**
   * This method returns the combined amount of all the splits.
   *
   * @return The combined amount of all the splits.
   */
  public
  double
  getTotal()
  {
    return itsTotal;
  }

  /**
   * This method returns true if the dialog was accepted, otherwise false.
   *
   * @return true or false.
   */
  public
  boolean
  showDialog()
  {
    runDialog();

    return wasAccepted();
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of private methods.
  //////////////////////////////////////////////////////////////////////////////

  private
  boolean
  allowEvents()
  {
    return itsAllowEvents;
  }

  private
  void
  buildPanel()
  {
    ActionHandler handler = new ActionHandler();

    // Build panel.
    getContentPane().setFill(GridBagConstraints.BOTH);
    getContentPane().add(createDialogHeader(), 0, 0, 1, 1, 100, 0);
    getContentPane().add(createTablePanel(), 0, 1, 1, 1, 0, 100);
    getContentPane().add(createOptionsPanel(), 0, 2, 1, 1, 0, 0);
    getContentPane().add(createDetailsPanel(), 0, 3, 1, 1, 0, 0);
    getContentPane().add(createOKCancelButtonPanel(handler), 0, 4, 1, 1, 0, 0);
  }

  private
  Panel
  createDetailsPanel()
  {
    Panel panel = new Panel();
    Panel details = new Panel();
    String gap = ": ";

    // Build details panel.
    details.setAnchor(GridBagConstraints.EAST);
    details.add(getProperty("sum") + gap, 0, 0, 1, 1, 0, 33);
    details.add(getProperty("unassigned") + gap, 0, 1, 1, 1, 0, 33);
    details.add(getProperty("total") + gap, 0, 2, 1, 1, 0, 34);

    details.setAnchor(GridBagConstraints.WEST);

    for(int len = 0; len < getLabels().length; ++len)
    {
      details.add(getLabels()[len], 1, len, 1, 1, 100, 0);
    }

    details.setBorder(createTitledBorder(getProperty("details")));

    // Build panel.
    panel.setFill(GridBagConstraints.BOTH);
    panel.add(details, 0, 0, 1, 1, 100, 100);

    panel.setInsets(new Insets(0, 15, 0, 15));

    return panel;
  }

  private
  static
  DialogHeader
  createDialogHeader()
  {
    String description = getProperty("description");
    String title = getProperty("title");

    return new DialogHeader(title, description, IconKeys.DIALOG_SPLIT.getIcon());
  }

  private
  void
  createLabels()
  {
    itsLabels = new JLabel[3];

    for(int len = 0; len < getLabels().length; ++len)
    {
      getLabels()[len] = new JLabel(UI_CURRENCY.format(0));
    }
  }

  private
  Panel
  createOptionsPanel()
  {
    Panel panel = new Panel();
    ActionHandler handler = new ActionHandler();
    Link add = new Link();
    Link remove = new Link();

    // Build buttons.
    buildButton(add, ACTION_ADD, handler);
    buildButton(remove, ACTION_REMOVE, handler);

    // Build panel.
    panel.setAnchor(GridBagConstraints.EAST);
    panel.add(add, 0, 0, 1, 1, 50, 50);

    panel.setAnchor(GridBagConstraints.CENTER);
    panel.addEmptyCellAt(1, 0);

    panel.setAnchor(GridBagConstraints.WEST);
    panel.add(remove, 2, 0, 1, 1, 50, 50);

    return panel;
  }

  private
  Panel
  createTablePanel()
  {
    Panel panel = new Panel();
    ScrollPane scrollPane = new ScrollPane(getTable());

    // Build scroll pane.
    scrollPane.setBackground(GUIConstants.COLOR_BACKGROUND_FILL);

    // Build panel.
    panel.setFill(GridBagConstraints.BOTH);
    panel.add(scrollPane, 0, 0, 1, 1, 100, 100);

    panel.setInsets(new Insets(7, 15, 0, 15));

    return panel;
  }

  private
  JLabel[]
  getLabels()
  {
    return itsLabels;
  }

  private
  static
  String
  getProperty(String key)
  {
    return I18NHelper.getProperty("SplitDialog." + key);
  }

  private
  SplitTable
  getTable()
  {
    return itsTable;
  }

  private
  void
  initializeTable(String category, double total)
  {
    Split split = null;
    double sum = 0.0;

    // Put into proper split form.
    if(category.length() == 0 || category.charAt(0) != ITEM_SEPARATOR_CHAR)
    {
      category = ITEM_SEPARATOR + category + AMOUNT_SEPARATOR + US_DOLLAR.format(0);
    }

    split = new Split(category, total);

    for(int len = 0; len < split.size(); ++len)
    {
      double amount = Math.abs(split.getAmount(len));

      category = split.getCategory(len);

      if(category.length() == 0)
      {
        category = NOT_CATEGORIZED.toString();
      }

      getTable().addSplit(category, UI_CURRENCY.format(amount));
      sum += amount;
    }

    getLabels()[SUM].setText(UI_CURRENCY.format(sum));
    getLabels()[TOTAL].setText(UI_CURRENCY.format(total));
    getLabels()[UNASSIGNED].setText(UI_CURRENCY.format(total - sum));
  }

  private
  void
  setAllowEvents(boolean value)
  {
    itsAllowEvents = value;
  }

  private
  void
  setTable(SplitTable table)
  {
    itsTable = table;
  }

  private
  void
  setTotal(double total)
  {
    itsTotal = total;
  }

  private
  void
  updateDetails()
  {
    double total = 0.0;

    // Stop responding to events.
    setAllowEvents(false);

    for(int len = 0; len < getTable().getRowCount(); ++len)
    {
      try
      {
        String str = (String)getTable().getValueAt(len, AMOUNT_COLUMN);
        double amount = UI_CURRENCY.parse(str);

        total += amount;
        getTable().setAmountAt(UI_CURRENCY.format(amount), len);
      }
      catch(Exception exception)
      {
        getTable().setAmountAt(UI_CURRENCY.format(0), len);
      }
    }

    getLabels()[SUM].setText(UI_CURRENCY.format(total));

    if(total > getTotal())
    {
      getLabels()[TOTAL].setText(UI_CURRENCY.format(total));
      getLabels()[UNASSIGNED].setText(UI_CURRENCY.format(0.0));
      setTotal(total);
    }
    else
    {
      getLabels()[UNASSIGNED].setText(UI_CURRENCY.format(getTotal() - total));
    }

    // Resume responding to events.
    setAllowEvents(true);
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

      if(command.equals(ACTION_ADD) == true)
      {
        if(getTable().addSplit() == true)
        {
          getTable().doEditCategory();
        }
      }
      else if(command.equals(ACTION_REMOVE) == true)
      {
        getTable().removeSelectedRows();
        updateDetails();
      }
      else
      {
        setAccepted(command.equals(ACTION_OK));
        dispose();
      }
    }
  }

  private
  class
  TableModelHandler
  implements TableModelListener
  {
    public
    void
    tableChanged(TableModelEvent event)
    {
      if(event.getColumn() == AMOUNT_COLUMN && event.getType() == TableModelEvent.UPDATE)
      {
        if(allowEvents() == true)
        {
          updateDetails();
        }
      }
    }
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  private boolean itsAllowEvents;
  private JLabel[] itsLabels;
  private SplitTable itsTable;
  private double itsTotal;

  private static final Currency US_DOLLAR = CurrencyFormatKeys.US_DOLLAR.getCurrency();

  private static final String ACTION_ADD = getProperty("add");
  private static final String ACTION_REMOVE = getSharedProperty("remove");

  private static final int SUM = 0;
  private static final int TOTAL = 2;
  private static final int UNASSIGNED = 1;
}
