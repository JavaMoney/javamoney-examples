// DataMerger

package org.javamoney.examples.ez.money.importexport;

import static org.javamoney.examples.ez.money.importexport.QIFConstants.CATEGORY_SEPARATOR;
import static org.javamoney.examples.ez.money.model.DataManager.getAccounts;
import static org.javamoney.examples.ez.money.model.DataManager.getExpenses;
import static org.javamoney.examples.ez.money.model.DataManager.getIncome;
import static org.javamoney.examples.ez.money.model.DataManager.getPayees;
import static org.javamoney.examples.ez.money.model.persisted.account.AccountTypeKeys.DEPOSIT;

import java.util.StringTokenizer;

import org.javamoney.examples.ez.money.model.dynamic.transaction.Split;
import org.javamoney.examples.ez.money.model.persisted.account.Account;
import org.javamoney.examples.ez.money.model.persisted.category.Category;
import org.javamoney.examples.ez.money.model.persisted.category.CategoryCollection;
import org.javamoney.examples.ez.money.model.persisted.payee.Payee;
import org.javamoney.examples.ez.money.model.persisted.transaction.Transaction;
import org.javamoney.examples.ez.money.utility.TransactionHelper;

/**
 * This class facilitates merging the data from the QIF file into the program's
 * collections. All methods in this class are static.
 */
final
class
DataMerger
{
  /**
   * This method merges the data in the transaction into the program's
   * collections.
   *
   * @param trans The transaction containing the data to merge.
   * @param isTransfer Whether or not the transaction is a transfer.
   */
  protected
  static
  void
  mergeWithCollections(Transaction trans, boolean isTransfer)
  {
    String category = trans.getCategory();
    String payee = trans.getPayee();

    if(payee.length() != 0)
    {
      if(isTransfer == true)
      {
        getAccounts().add(new Account(DEPOSIT, payee));
      }
      else
      {
        getPayees().add(new Payee(payee));
      }
    }

    if(TransactionHelper.isSplit(category) == true)
    {
      addSplit(category, trans.getAmount().doubleValue());
    }
    else
    {
      addCategory(category, trans.getAmount().doubleValue());
    }
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of private methods.
  //////////////////////////////////////////////////////////////////////////////

  private
  static
  void
  addCategory(String category, double amount)
  {
    if(TransactionHelper.isExpense(amount) == true)
    {
      addCategoryToCollection(getExpenses(), category);
    }
    else
    {
      addCategoryToCollection(getIncome(), category);
    }
  }

  private
  static
  void
  addCategoryToCollection(CategoryCollection collection, String identifier)
  {
    if(identifier.length() != 0)
    {
      StringTokenizer tokens = new StringTokenizer(identifier, CATEGORY_SEPARATOR);
      Category category = null;
      String token = tokens.nextToken();

      collection.add(new Category(token));

      // Get reference to newly added category.
      category = (Category)collection.get(token);

      while(tokens.hasMoreTokens() == true)
      {
        token = tokens.nextToken();

        collection.addToGroup(category, new Category(token));

        // Get reference to newly added category.
        category = (Category)collection.getFromGroup(category, token);
      }
    }
  }

  private
  static
  void
  addSplit(String category, double amount)
  {
    Split split = new Split(category, amount);

    for(int len = 0; len < split.size(); ++len)
    {
      addCategory(split.getCategory(len), amount);
    }
  }
}
