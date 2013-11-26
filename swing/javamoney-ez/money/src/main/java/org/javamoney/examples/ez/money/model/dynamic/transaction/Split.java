// Split

package org.javamoney.examples.ez.money.model.dynamic.transaction;

import static org.javamoney.examples.ez.money.locale.CurrencyFormatKeys.US_DOLLAR;

import java.util.StringTokenizer;

import org.javamoney.examples.ez.money.locale.CurrencyFormat;
import org.javamoney.examples.ez.money.model.persisted.transaction.Transaction;

/**
 * This class facilitates managing a split in a transaction by breaking the spit
 * into a manageable array.
 */
public
final
class
Split
{
  /**
   * Constructs a new split.
   *
   * @param split The split to parse.
   * @param value The total amount of the split.
   */
  public
  Split(String split, double value)
  {
    setAmounts(new double[MAX_SPLIT]);
    setCategories(new String[MAX_SPLIT]);

    parse(split, value);
  }

  /**
   * Constructs a new split.
   *
   * @param trans The transaction that contains the split.
   */
  public
  Split(Transaction trans)
  {
    this(trans.getCategory(), trans.getAmount());
  }

  /**
   * This method returns the amount at the specified index.
   *
   * @param index The index of the item in the split.
   *
   * @return The amount at the specified index.
   */
  public
  double
  getAmount(int index)
  {
    return getAmounts()[index];
  }

  /**
   * This method returns the category at the specified index.
   *
   * @param index The index of the item in the split.
   *
   * @return The category at the specified index.
   */
  public
  String
  getCategory(int index)
  {
    return getCategories()[index];
  }

  /**
   * This method returns the amount of items in the split.
   *
   * @return The amount of items in the split.
   */
  public
  int
  size()
  {
    return itsSize;
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of private methods.
  //////////////////////////////////////////////////////////////////////////////

  private
  double[]
  getAmounts()
  {
    return itsAmounts;
  }

  private
  String[]
  getCategories()
  {
    return itsCategories;
  }

  private
  void
  parse(String split, double total)
  {
    StringTokenizer splits = new StringTokenizer(split, ITEM_SEPARATOR);
    int len = 0;

    for(len = 0; splits.hasMoreTokens() == true && len < MAX_SPLIT; ++len)
    {
      try
      {
        StringTokenizer tokens = new StringTokenizer(splits.nextToken(), AMOUNT_SEPARATOR);
        String category = "";

        // There can be an amount that is not categorized.
        if(tokens.countTokens() != 1)
        {
          category = tokens.nextToken();
        }

        getCategories()[len] = category;
        getAmounts()[len] = FORMAT.parse(tokens.nextToken());

        // Amounts in splits are always positive, so put in proper form.
        if(total < 0.0)
        {
          getAmounts()[len] = -getAmounts()[len];
        }
      }
      catch(Exception exception)
      {
        // Ignored.
      }
    }

    setSize(len);
  }

  private
  void
  setAmounts(double[] amounts)
  {
    itsAmounts = amounts;
  }

  private
  void
  setCategories(String[] categories)
  {
    itsCategories = categories;
  }

  private
  void
  setSize(int size)
  {
    itsSize = size;
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  private double[] itsAmounts;
  private String[] itsCategories;
  private int itsSize;

  private static final CurrencyFormat FORMAT = US_DOLLAR.getCurrency();

  /**
   * The maximum splits a transaction can have.
   */
  public static final int MAX_SPLIT = 15;
  /**
   * The token used to separate the amount from the category in a split.
   */
  public static final String AMOUNT_SEPARATOR = "=";
  /**
   * The character used to separate the amount from the category in a split.
   */
  public static final char AMOUNT_SEPARATOR_CHAR = '=';
  /**
   * The token used to separate the items in a split.
   */
  public static final String ITEM_SEPARATOR = ";";
  /**
   * The character used to separate the items in a split.
   */
  public static final char ITEM_SEPARATOR_CHAR = ';';
}
