// AutoCompleteHandler

package org.javamoney.examples.ez.money.gui.view.register;

import static org.javamoney.examples.ez.money.ApplicationThread.getFrame;
import static org.javamoney.examples.ez.money.KeywordKeys.NOT_CATEGORIZED;
import static org.javamoney.examples.ez.money.model.DataManager.getExpenses;
import static org.javamoney.examples.ez.money.model.DataManager.getIncome;
import static org.javamoney.examples.ez.money.model.dynamic.transaction.TransactionTypeKeys.INCOME;
import static org.javamoney.examples.ez.money.utility.TransactionHelper.isIncome;
import static org.javamoney.examples.ez.money.utility.TransactionHelper.isSplit;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

import org.javamoney.examples.ez.money.gui.view.RegisterView;
import org.javamoney.examples.ez.money.gui.view.ViewKeys;
import org.javamoney.examples.ez.money.model.DataElement;
import org.javamoney.examples.ez.money.model.dynamic.transaction.TransactionTypeKeys;
import org.javamoney.examples.ez.money.model.persisted.category.Category;
import org.javamoney.examples.ez.money.model.persisted.transaction.Transaction;

import org.javamoney.examples.ez.common.gui.ComboBox;
import org.javamoney.examples.ez.common.utility.ComboBoxCompleter;

/**
 * This class facilitates auto-completing payees as they are being typed, and
 * selecting categories for those payees based on frequency.
 */
final
class
AutoCompleteHandler
{
  /**
   * Constructs a new handler that will reference the specified form.
   *
   * @param form The form to reference.
   */
  protected
  AutoCompleteHandler(Form form)
  {
    setForm(form);

    ComboBox comboBox = form.getPayFromChooser();

    // Add listeners.
    comboBox.addPopupMenuListener(new MenuHandler());
    comboBox.getTextField().addKeyListener(new KeyHandler());
    comboBox.getTextField().addFocusListener(new FocusHandler());
    new ComboBoxCompleter(comboBox);
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of private methods.
  //////////////////////////////////////////////////////////////////////////////

  private
  void
  buildFrequencyMap(Map<String, Integer> map, Collection<DataElement> collection)
  {
    for(DataElement element : collection)
    {
      Category category = (Category)element;

      map.put(category.getQIFName(), new Integer(0));

      if(category.isGroup() == true)
      {
        buildFrequencyMap(map, category.getSubcategories());
      }
    }
  }

  private
  boolean
  canAutoComplete()
  {
    String category = getForm().getPayToChooser().getSelectedItem();
    String payee = getForm().getPayFrom();
    boolean result = false;

    if(payee.length() != 0 && category.equals(NOT_CATEGORIZED.toString()) == true)
    {
      result = true;
    }

    return result;
  }

  private
  Map<String, Integer>
  createFrequencyMap()
  {
    Map<String, Integer> frequencyMap = new HashMap<String, Integer>();
    String payee = getForm().getPayFrom();
    boolean isIncome = getForm().getType() == TransactionTypeKeys.INCOME;

    // Build the map.
    buildFrequencyMap(frequencyMap, getCategoryCollection(getForm().getType()));

    // Find the frequencies.
    for(Transaction trans : getRegisterView().getAccount().getTransactions())
    {
      if(trans.getPayee().equals(payee) == true && isIncome == isIncome(trans))
      {
        if(trans.getCategory().length() != 0 && isSplit(trans) == false)
        {
          Integer count = frequencyMap.get(trans.getCategory());

          if(count == null)
          {
            frequencyMap.put(trans.getCategory(), new Integer(0));
          }
          else
          {
            frequencyMap.put(trans.getCategory(), new Integer(count.intValue() + 1));
          }
        }
      }
    }

    return frequencyMap;
  }

  private
  void
  doAutoComplete()
  {
    Map<String, Integer> frequencies = createFrequencyMap();
    String mostFrequent = null;
    int max = 0;

    for(Map.Entry<String, Integer> entry : frequencies.entrySet())
    {
      if(max < entry.getValue().intValue())
      {
        max = entry.getValue().intValue();
        mostFrequent = entry.getKey();
      }
    }

    if(max != 0 && mostFrequent != null)
    {
      getForm().getPayToChooser().setSelectedItem(mostFrequent);
    }
  }

  private
  Collection<DataElement>
  getCategoryCollection(TransactionTypeKeys key)
  {
    Collection<DataElement> collection = null;

    if(key == INCOME)
    {
      collection = getIncome().getCollection();
    }
    else
    {
      collection = getExpenses().getCollection();
    }

    return collection;
  }

  private
  Form
  getForm()
  {
    return itsForm;
  }

  private
  RegisterView
  getRegisterView()
  {
    return (RegisterView)getFrame().getViews().getView(ViewKeys.REGISTER);
  }

  private
  void
  setForm(Form panel)
  {
    itsForm = panel;
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of inner classes.
  //////////////////////////////////////////////////////////////////////////////

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
      if(canAutoComplete() == true)
      {
        doAutoComplete();
      }
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
      if(event.getKeyChar() == KeyEvent.VK_ENTER && canAutoComplete() == true)
      {
        doAutoComplete();
      }
    }
  }

  private
  class
  MenuHandler
  implements PopupMenuListener
  {
    public
    void
    popupMenuCanceled(PopupMenuEvent event)
    {
      // Ignored.
    }

    public
    void
    popupMenuWillBecomeInvisible(PopupMenuEvent event)
    {
      if(canAutoComplete() == true)
      {
        doAutoComplete();
      }
    }

    public
    void
    popupMenuWillBecomeVisible(PopupMenuEvent event)
    {
      // Ignored.
    }
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  private Form itsForm;
}
