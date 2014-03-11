// TransactionFilter

package org.javamoney.examples.ez.money.model.dynamic.transaction;

import static org.javamoney.examples.ez.common.utility.I18NHelper.getLanguage;
import static java.text.DateFormat.LONG;
import static java.text.DateFormat.getDateInstance;
import static org.javamoney.examples.ez.money.ApplicationProperties.UI_CURRENCY_FORMAT;
import static org.javamoney.examples.ez.money.ApplicationProperties.UI_DATE_FORMAT;
import static org.javamoney.examples.ez.money.model.dynamic.transaction.TransactionFilterFieldKeys.ALL;
import static org.javamoney.examples.ez.money.model.dynamic.transaction.TransactionFilterFieldKeys.AMOUNT;
import static org.javamoney.examples.ez.money.model.dynamic.transaction.TransactionFilterFieldKeys.CHECK;
import static org.javamoney.examples.ez.money.model.dynamic.transaction.TransactionFilterFieldKeys.DATE;
import static org.javamoney.examples.ez.money.model.dynamic.transaction.TransactionFilterFieldKeys.NOTES;

import java.text.DateFormat;
import java.text.DecimalFormat;
import org.javamoney.calc.function.MonetaryPredicate;
import org.javamoney.examples.ez.money.model.persisted.transaction.Transaction;

/**
 * This class facilitates providing a way to filter transactions.
 */
public
final
class
TransactionFilter implements MonetaryPredicate<Transaction>
{
  /**
   * Constructs a new filter.
   */
  public
  TransactionFilter()
  {
    setFilterField(ALL);
    setFilterText(null);

    updateFormats();
  }

  /**
   * This method returns true if the specified transaction can be reported on,
   * otherwise false.
   *
   * @param trans The transaction to check.
   *
   * @return true or false.
   */
  public
  boolean
  test(Transaction trans)
  {
    boolean result = false;

    if(getFilterText() != null)
    {
      String text = getFilterText().toLowerCase().trim();

      if(getFilterField() == ALL)
      {
        if(matchesPayee(trans, text) == true ||
            matchesCheck(trans, text) == true ||
            matchesAmount(trans, text) == true ||
            matchesDate(trans, text) == true ||
            matchesNotes(trans, text) == true)
        {
          result = true;
        }
      }
      else if(getFilterField() == AMOUNT)
      {
        result = matchesAmount(trans, text);
      }
      else if(getFilterField() == CHECK)
      {
        result = matchesCheck(trans, text);
      }
      else if(getFilterField() == DATE)
      {
        result = matchesDate(trans, text);
      }
      else if(getFilterField() == NOTES)
      {
        result = matchesNotes(trans, text);
      }
      else
      {
        result = matchesPayee(trans, text);
      }
    }
    else
    {
      result = true;
    }

    return result;
  }

  /**
   * This method returns the field to filter by.
   *
   * @return The field to filter by.
   */
  public
  TransactionFilterFieldKeys
  getFilterField()
  {
    return itsFilterField;
  }

  /**
   * This method returns the text being used by the filter. A value of null
   * indicates to not filter.
   *
   * @return The text being used by the filter.
   */
  public
  String
  getFilterText()
  {
    return itsFilterText;
  }

  /**
   * This method sets the field to filter by.
   *
   * @param field The field to filter by.
   */
  public
  void
  setFilterField(TransactionFilterFieldKeys field)
  {
    itsFilterField = field;
  }

  /**
   * This method sets the text to be used by the filter. A value of null
   * indicates to not filter.
   *
   * @param text The text to be used by the filter.
   */
  public
  void
  setFilterText(String text)
  {
    itsFilterText = text;
  }

  /**
   * This method updates the filter's internal formats to take in any possible
   * preference changes.
   */
  public
  void
  updateFormats()
  {
    setDateFormat(getDateInstance(LONG, getLanguage()));
    setDecimalFormat(new DecimalFormat("###.##", UI_CURRENCY_FORMAT.getDecimalFormatSymbols()));
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of private methods.
  //////////////////////////////////////////////////////////////////////////////

  private
  DateFormat
  getDateFormat()
  {
    return itsDateFormat;
  }

  private
  DecimalFormat
  getDecimalFormat()
  {
    return itsDecimalFormat;
  }

  private
  boolean
  matchesAmount(Transaction trans, String text)
  {
    String amount1 = UI_CURRENCY_FORMAT.format(Math.abs(trans.getAmount().getNumber().doubleValue()));
    String amount2 = getDecimalFormat().format(Math.abs(trans.getAmount().getNumber().doubleValue()));

    return amount1.startsWith(text) == true || amount2.startsWith(text) == true;
  }

  private
  boolean
  matchesDate(Transaction trans, String text)
  {
    String date1 = UI_DATE_FORMAT.format(trans.getDate());
    String date2 = getDateFormat().format(trans.getDate()).toLowerCase();

    return date1.startsWith(text) == true || date2.contains(text) == true;
  }

  private
  boolean
  matchesCheck(Transaction trans, String text)
  {
    String check = trans.getCheckNumber().toLowerCase();

    return check.startsWith(text) == true;
  }

  private
  boolean
  matchesNotes(Transaction trans, String text)
  {
    String notes = trans.getNotes().toLowerCase();

    return notes.contains(text) == true;
  }

  private
  boolean
  matchesPayee(Transaction trans, String text)
  {
    String payee = trans.getPayee().toLowerCase();

    return payee.startsWith(text) == true;
  }

  private
  void
  setDateFormat(DateFormat format)
  {
    itsDateFormat = format;
  }

  private
  void
  setDecimalFormat(DecimalFormat format)
  {
    itsDecimalFormat = format;
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  private DateFormat itsDateFormat;
  private DecimalFormat itsDecimalFormat;
  private TransactionFilterFieldKeys itsFilterField;
  private String itsFilterText;
}
