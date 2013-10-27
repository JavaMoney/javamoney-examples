// PayeesPanel

package org.javamoney.examples.ez.money.gui.dialog.preferences;

import static org.javamoney.examples.ez.money.model.DataTypeKeys.PAYEE;
import static org.javamoney.examples.ez.money.utility.IDHelper.MessageKeys.IN_USE;
import static org.javamoney.examples.ez.money.utility.IDHelper.MessageKeys.UNABLE_TO_REMOVE;

import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.javamoney.examples.ez.money.model.persisted.payee.Payee;
import org.javamoney.examples.ez.money.utility.IDHelper;
import org.javamoney.examples.ez.money.utility.TransactionHelper;

/**
 * This class facilitates managing the payees.
 */
public
final
class
PayeesPanel
extends DataElementPanel
{
  /**
   * Constructs a new preferences panel.
   */
  public
  PayeesPanel()
  {
    super(PreferencesKeys.PAYEES);

    buildPanel();
  }

  /**
   * This method updates this panel's collection.
   */
  @Override
  public
  void
  updateView()
  {
    displayCollectables();
    getChooser().selectFirst();
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of protected methods.
  //////////////////////////////////////////////////////////////////////////////

  /**
   * This method prompts the user for a new unique identifier.
   */
  @Override
  protected
  void
  edit()
  {
    Payee payee = (Payee)getChooser().getSelectedElement();
    String identifier = IDHelper.promptForEdit(PAYEE, payee.getIdentifier());

    if(identifier != null)
    {
      String temp = payee.getIdentifier(); // Store for mass update.
      boolean result = getCollection().changeIdentifier(payee, identifier);

      if(result == false)
      {
        // It is safe to assume it already existed.
        if((result = IDHelper.confirmMerge()) ==    true)
        {
          getCollection().remove(payee);

          // Since elements are not case sensitive, add it again just incase it
          // doesn't exist but failed to change.
          getCollection().add(new Payee(identifier));

          payee = (Payee)getCollection().get(identifier);
        }
      }

      if(result == true)
      {
        // Update all transactions, the view, and select the edited element.
        TransactionHelper.massUpdate(TransactionHelper.MassUpdateFieldKeys.PAYEE,
            temp, identifier);

        displayCollectables();
        getChooser().setSelectedCollectable(payee);
      }
    }
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of private methods.
  //////////////////////////////////////////////////////////////////////////////

  private
  void
  add()
  {
    String identifier = IDHelper.promptForAdd(PAYEE);

    if(identifier != null)
    {
      Payee payee = new Payee(identifier);

      if(getCollection().add(payee) == true)
      {
        // Update view and select the new element.
        displayCollectables();
        getChooser().setSelectedCollectable(payee);
      }
      else
      {
        // It is safe to assume it already existed.
        IDHelper.showMessage(IN_USE, PAYEE);
      }
    }
  }

  private
  void
  buildPanel()
  {
    ActionHandler handler = new ActionHandler();

    // Build panel.
    setFill(GridBagConstraints.BOTH);
    add(createChooserPanel(handler), 0, 0, 1, 1, 100, 100);
    add(createButtonPanel(handler), 0, 1, 1, 1, 0, 0);
  }

  private
  void
  displayCollectables()
  {
    getChooser().displayCollectables();
    enableLinks();
    showProperChooserPanel();
  }

  private
  void
  remove()
  {
    if(IDHelper.confirmRemoval(PAYEE) == true)
    {
      Payee payee = (Payee)getChooser().getSelectedElement();

      if(getCollection().remove(payee) == true)
      {
        // Update all transactions and the view.
        TransactionHelper.massUpdate(TransactionHelper.MassUpdateFieldKeys.PAYEE,
            payee.getIdentifier(), "");

        displayCollectables();

        if(getChooser().length() != 0)
        {
          getChooser().selectFirst();
        }
      }
      else
      {
        IDHelper.showMessage(UNABLE_TO_REMOVE, PAYEE);
      }
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
      String command = event.getActionCommand();

      if(command.equals(ACTION_ADD) == true)
      {
        add();
      }
      else if(command.equals(ACTION_EDIT) == true)
      {
        edit();
      }
      else if(command.equals(ACTION_REMOVE) == true)
      {
        remove();
      }
    }
  }
}
