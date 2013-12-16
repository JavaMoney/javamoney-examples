// DataIOManager

package org.javamoney.examples.ez.money.model;

import static org.javamoney.examples.ez.money.ApplicationProperties.UI_CURRENCY_SYMBOL;
import static org.javamoney.examples.ez.money.model.DataManager.getAccounts;
import static org.javamoney.examples.ez.money.model.DataManager.getExpenses;
import static org.javamoney.examples.ez.money.model.DataManager.getIncome;
import static org.javamoney.examples.ez.money.model.DataManager.getPayees;
import static org.javamoney.examples.ez.money.model.DataManager.getReminders;
import static org.javamoney.examples.ez.money.model.persisted.account.AccountTypeKeys.CASH;
import static org.javamoney.examples.ez.money.model.persisted.account.AccountTypeKeys.CREDIT;
import static org.javamoney.examples.ez.money.model.persisted.account.AccountTypeKeys.DEPOSIT;
import static org.javamoney.examples.ez.money.utility.CryptoHelper.getDecryptionCipher;
import static org.javamoney.examples.ez.money.utility.CryptoHelper.getEncryptionCipher;
import static org.javamoney.examples.ez.money.utility.DialogHelper.error;
import static org.javamoney.examples.ez.money.utility.PasswordHelper.readPassword;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.text.SimpleDateFormat;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;

import org.javamoney.examples.ez.money.locale.CurrencyFormat;
import org.javamoney.examples.ez.money.locale.CurrencyFormatKeys;
import org.javamoney.examples.ez.money.model.persisted.account.Account;
import org.javamoney.examples.ez.money.model.persisted.account.AccountTypeKeys;
import org.javamoney.examples.ez.money.model.persisted.category.Category;
import org.javamoney.examples.ez.money.model.persisted.category.CategoryCollection;
import org.javamoney.examples.ez.money.model.persisted.payee.Payee;
import org.javamoney.examples.ez.money.model.persisted.reminder.Reminder;
import org.javamoney.examples.ez.money.model.persisted.transaction.LabelKeys;
import org.javamoney.examples.ez.money.model.persisted.transaction.Transaction;
import org.javamoney.examples.ez.common.utility.I18NHelper;
import org.javamoney.moneta.Money;

/**
 * This class facilitates reading and writing the persisted data. All methods in
 * this class are static.
 */
public
final
class
DataIOManager
{
  /**
   * This method reads all the data and then returns the success of the
   * operation.
   * <p>
   * <b>Note:</b> This method expects the file to be in a specific format.
   *
   * @param file The file to have the data read from.
   * @param showError Whether or not to show an error message on failure.
   * @param decrypt Whether or not the data needs to be decrypted.
   *
   * @return true or false.
   */
  protected
  static
  boolean
  read(File file, boolean showError, boolean decrypt)
  {
    boolean result = false;

    try
    {
      BufferedReader reader = createReader(file, decrypt);
      String line = "";

      while((line = reader.readLine()) != null)
      {
        if(line.equals(HEADER_ACCOUNTS) == true)
        {
          readAccounts(reader);
        }
        else if(line.equals(HEADER_EXPENSES) == true)
        {
          readCategoryCollection(getExpenses(), reader);
        }
        else if(line.equals(HEADER_INCOME) == true)
        {
          readCategoryCollection(getIncome(), reader);
        }
        else if(line.equals(HEADER_PAYEES) == true)
        {
          readPayees(reader);
        }
        else if(line.equals(HEADER_REMINDERS) == true)
        {
          readReminders(reader);
        }
        else if(line.equals(HEADER_TRANSACTIONS) == true)
        {
          readTransactions(reader);
        }
      }

      reader.close();
      result = true;
    }
    catch(Exception exception)
    {
      if(showError == true)
      {
        error(getProperty("read.title"),
            getProperty("read.description") + "<br><br><b>" + file + "</b>");
      }
    }

    return result;
  }

  /**
   * This method writes all the data and then returns the success of the
   * operation.
   *
   * @param file The file to have the data written to.
   * @param showError Whether or not to show an error message on failure.
   * @param encrypt Whether or not the data needs to be encrypted.
   *
   * @return true or false.
   */
  protected
  static
  boolean
  write(File file, boolean showError, boolean encrypt)
  {
    boolean result = false;

    try
    {
      PrintStream stream = createPrintStream(file, encrypt);

      writeAccounts(stream);
      writeCategoryCollection(getExpenses(), stream);
      writeCategoryCollection(getIncome(), stream);
      writePayess(stream);
      writeReminders(stream);
      writeTransactions(stream);

      stream.close();
      result = true;
    }
    catch(Exception exception)
    {
      if(showError == true)
      {
        error(getProperty("write.title"),
            getProperty("write.description") + "<br><br><b>" + file + "</b>");
      }
    }

    return result;
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of private methods.
  //////////////////////////////////////////////////////////////////////////////

  private
  static
  PrintStream
  createPrintStream(File file, boolean encrypt)
  throws Exception
  {
    FileOutputStream fStream = new FileOutputStream(file);
    PrintStream pStream = null;

    if(encrypt == true)
    {
      Cipher cipher = getEncryptionCipher(readPassword());
//TODO try with resource (Java7)
      pStream = new PrintStream(new CipherOutputStream(fStream, cipher));
    }
    else
    {
      pStream = new PrintStream(fStream);
    }

    return pStream;
  }

  private
  static
  BufferedReader
  createReader(File file, boolean decrypt)
  throws Exception
  {
    FileInputStream fStream = new FileInputStream(file);
    InputStreamReader reader = null;

    if(decrypt == true)
    {
      Cipher cipher = getDecryptionCipher(readPassword());
      CipherInputStream cStream = new CipherInputStream(fStream, cipher);

      reader = new InputStreamReader(cStream);
    }
    else
    {
      reader = new InputStreamReader(fStream);
    }

    return new BufferedReader(reader);
  }

  private
  static
  String
  getProperty(String key)
  {
    return I18NHelper.getProperty("DataIOManager." + key);
  }

  private
  static
  boolean
  parseBoolean(String value)
  {
    return Boolean.parseBoolean(value);
  }

  private
  static
  void
  readAccounts(BufferedReader stream)
  throws Exception
  {
    Account account = new Account(DEPOSIT, "");
    String line = "";
    char key;

    while((line = stream.readLine()) != null)
    {
      key = Character.toUpperCase(line.charAt(0));

      // Remove the key from the line.
      line = line.substring(1);

      if(key == END_OF_COLLECTION)
      {
        break;
      }
      else if(key == END_OF_ENTRY)
      {
        getAccounts().add(account);
      }
      else if(key == ACCOUNT_ACTIVE)
      {
        account.setIsActive(parseBoolean(line));
      }
      else if(key == ACCOUNT_BALANCE)
      {
        account.setBalance(Money.of(UI_CURRENCY_SYMBOL.getCurrency(), US_DOLLAR.parse(line)));
      }
      else if(key == ACCOUNT_KEY)
      {
        if(line.equals(AccountTypeKeys.CASH.name()) == true)
        {
          account = new Account(CASH, "");
        }
        else if(line.equals(AccountTypeKeys.CREDIT.name()) == true)
        {
          account = new Account(CREDIT, "");
        }
        else
        {
          account = new Account(DEPOSIT, "");
        }
      }
      else if(key == UID)
      {
        account.setIdentifier(line);
      }
    }
  }

  private
  static
  void
  readCategoryCollection(CategoryCollection collection, BufferedReader stream)
  throws Exception
  {
    Category category = new Category("");
    Category group = null;
    String line = "";
    char key;

    while((line = stream.readLine()) != null)
    {
      key = Character.toUpperCase(line.charAt(0));

      // Remove the key from the line.
      line = line.substring(1);

      if(key == END_OF_COLLECTION)
      {
        break;
      }
      else if(key == END_OF_ENTRY)
      {
        if(group != null)
        {
          collection.addToGroup(group, category);
        }
        else
        {
          collection.add(category);
        }
      }
      else if(key == CATEGORY_BUDGET)
      {
        category.setBudget(Integer.parseInt(line));
        category.setIsBudgeted(true);
      }
      else if(key == CATEGORY_DATE)
      {
        category.setRolloverStartDate(DATE_FORMAT.parse(line));
      }
      else if(key == CATEGORY_GROUP)
      {
        group = collection.getCategoryFromQIF(line);
      }
      else if(key == CATEGORY_ROLLOVER)
      {
        category.setHasRolloverBudget(parseBoolean(line));
      }
      else if(key == UID)
      {
        category = new Category(line);
      }
    }
  }

  private
  static
  void
  readPayees(BufferedReader stream)
  throws Exception
  {
    Payee payee = new Payee("");
    String line = "";
    char key;

    while((line = stream.readLine()) != null)
    {
      key = Character.toUpperCase(line.charAt(0));

      // Remove the key from the line.
      line = line.substring(1);

      if(key == END_OF_COLLECTION)
      {
        break;
      }
      else if(key == END_OF_ENTRY)
      {
        getPayees().add(payee);
      }
      else if(key == UID)
      {
        payee = new Payee(line);
      }
    }
  }

  private
  static
  void
  readReminders(BufferedReader stream)
  throws Exception
  {
    Reminder reminder = new Reminder("");
    String line = "";
    char key;

    while((line = stream.readLine()) != null)
    {
      key = Character.toUpperCase(line.charAt(0));

      // Remove the key from the line.
      line = line.substring(1);

      if(key == END_OF_COLLECTION)
      {
        break;
      }
      else if(key == END_OF_ENTRY)
      {
        getReminders().add(reminder);
      }
      else if(key == REMINDER_COMPLETE)
      {
        reminder.setIsComplete(parseBoolean(line));
      }
      else if(key == REMINDER_DAYS_TO_ALARM)
      {
        reminder.setDaysToAlarm(Integer.parseInt(line));
      }
      else if(key == REMINDER_DUE_BY)
      {
        reminder.setDueBy(DATE_FORMAT.parse(line));
      }
      else if(key == UID)
      {
        reminder = new Reminder(line);
      }
    }
  }

  private
  static
  void
  readTransactions(BufferedReader stream)
  throws Exception
  {
    Account account = new Account(DEPOSIT, "");
    String amount = "";
    String category = "";
    String date = "";
    String label = LabelKeys.NONE.name();
    String line = "";
    String notes = "";
    String number = "";
    String payee = "";
    String reconciled = "false";
    char key;

    while((line = stream.readLine()) != null)
    {
      key = Character.toUpperCase(line.charAt(0));

      // Remove the key from the line.
      line = line.substring(1);

      if(key == END_OF_COLLECTION)
      {
        break;
      }
      else if(key == END_OF_ENTRY)
      {
        Transaction trans = new Transaction(number, DATE_FORMAT.parse(date),
            payee, Money.of(UI_CURRENCY_SYMBOL.getCurrency(), US_DOLLAR.parse(amount)), category, notes);

        trans.setIsReconciled(parseBoolean(reconciled));
        trans.setLabel(LabelKeys.valueOf(label));

        account.getTransactions().add(trans);

        amount = "";
        category = "";
        date = "";
        label = LabelKeys.NONE.name();
        notes = "";
        number = "";
        payee = "";
        reconciled = "false";
      }
      else if(key == TRANS_ACCOUNT)
      {
        account = (Account)getAccounts().get(line);
      }
      else if(key == TRANS_AMOUNT)
      {
        amount = line.substring(1);
      }
      else if(key == TRANS_CATEGORY)
      {
        category = line;
      }
      else if(key == TRANS_CHECK_NUMBER)
      {
        number = line;
      }
      else if(key == TRANS_DATE)
      {
        date = line;
      }
      else if(key == TRANS_LABEL)
      {
        label = line;
      }
      else if(key == TRANS_NOTES)
      {
        notes = line;
      }
      else if(key == TRANS_PAYEE)
      {
        payee = line;
      }
      else if(key == TRANS_RECONCILED)
      {
        reconciled = line;
      }
    }
  }

  private
  static
  void
  writeAccounts(PrintStream stream)
  {
    stream.println(HEADER_ACCOUNTS);

    for(DataElement element : getAccounts().getCollection())
    {
      Account account = (Account)element;

      stream.println(ACCOUNT_KEY + account.getType().name());
      stream.println(UID + account.getIdentifier());
      stream.println(ACCOUNT_BALANCE + US_DOLLAR.format(account.getBalance().getNumber().doubleValue(), false));
      stream.println(ACCOUNT_ACTIVE + String.valueOf(account.isActive()));
      stream.println(END_OF_ENTRY);
    }

    stream.println(END_OF_COLLECTION);
  }

  private
  static
  void
  writeCategory(Category category, PrintStream stream)
  {
    stream.println(UID + category.getIdentifier());
    stream.println(CATEGORY_GROUP + category.getGroupName());

    if(category.isBudgeted() == true)
    {
      stream.println(CATEGORY_BUDGET + String.valueOf(category.getBudget()));
      stream.println(CATEGORY_DATE + DATE_FORMAT.format(category.getRolloverStartDate()));
      stream.println(CATEGORY_ROLLOVER + String.valueOf(category.hasRolloverBudget()));
    }

    stream.println(END_OF_ENTRY);

    if(category.isGroup() == true)
    {
      for(DataElement element : category.getSubcategories())
      {
        writeCategory((Category)element, stream);
      }
    }
  }

  private
  static
  void
  writeCategoryCollection(CategoryCollection collection, PrintStream stream)
  {
    if(collection == getExpenses())
    {
      stream.println(HEADER_EXPENSES);
    }
    else
    {
      stream.println(HEADER_INCOME);
    }

    for(DataElement element : collection.getCollection())
    {
      writeCategory((Category)element, stream);
    }

    stream.println(END_OF_COLLECTION);
  }

  private
  static
  void
  writePayess(PrintStream stream)
  {
    stream.println(HEADER_PAYEES);

    for(DataElement element : getPayees().getCollection())
    {
      stream.println(UID + element.getIdentifier());
      stream.println(END_OF_ENTRY);
    }

    stream.println(END_OF_COLLECTION);
  }

  private
  static
  void
  writeReminders(PrintStream stream)
  {
    stream.println(HEADER_REMINDERS);

    for(DataElement element : getReminders().getCollection())
    {
      Reminder reminder = (Reminder)element;

      stream.println(UID + reminder.getIdentifier());
      stream.println(REMINDER_DUE_BY + DATE_FORMAT.format(reminder.getDueBy()));
      stream.println(REMINDER_DAYS_TO_ALARM + String.valueOf(reminder.getDaysToAlarm()));
      stream.println(REMINDER_COMPLETE + String.valueOf(reminder.isComplete()));

      stream.println(END_OF_ENTRY);
    }

    stream.println(END_OF_COLLECTION);
  }

  private
  static
  void
  writeTransactions(PrintStream stream)
  {
    stream.println(HEADER_TRANSACTIONS);

    for(DataElement element : getAccounts().getCollection())
    {
      Account account = (Account)element;

      for(Transaction trans : account.getTransactions())
      {
        stream.println(TRANS_ACCOUNT + account.getIdentifier());
        stream.println(TRANS_AMOUNT +  USD + US_DOLLAR.format(trans.getAmount().getNumber().doubleValue(), false));
        stream.println(TRANS_CHECK_NUMBER + trans.getCheckNumber());
        stream.println(TRANS_DATE + DATE_FORMAT.format(trans.getDate()));
        stream.println(TRANS_CATEGORY + trans.getCategory());
        stream.println(TRANS_LABEL + trans.getLabel().name());
        stream.println(TRANS_NOTES + trans.getNotes());
        stream.println(TRANS_PAYEE + trans.getPayee());
        stream.println(TRANS_RECONCILED + String.valueOf(trans.isReconciled()));
        stream.println(END_OF_ENTRY);
      }
    }

    stream.println(END_OF_COLLECTION);
  }

  //////////////////////////////////////////////////////////////////////////////
  // Start of class members.
  //////////////////////////////////////////////////////////////////////////////

  private static final String USD = "$";
  private static final CurrencyFormat US_DOLLAR = CurrencyFormatKeys.US_DOLLAR.getFormat();
  private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("M/d/yyyy");

  // Field keys.
  private static final char ACCOUNT_ACTIVE = 'A';
  private static final char ACCOUNT_BALANCE = 'B';
  private static final char ACCOUNT_KEY = 'K';
  private static final char CATEGORY_BUDGET = 'B';
  private static final char CATEGORY_DATE = 'D';
  private static final char CATEGORY_GROUP = 'G';
  private static final char CATEGORY_ROLLOVER = 'R';
  private static final char END_OF_COLLECTION = '*';
  private static final char END_OF_ENTRY = '^';
  private static final String HEADER_ACCOUNTS = "!Accounts";
  private static final String HEADER_EXPENSES = "!Expenses";
  private static final String HEADER_INCOME = "!Income";
  private static final String HEADER_PAYEES = "!Payees";
  private static final String HEADER_REMINDERS = "!Reminders";
  private static final String HEADER_TRANSACTIONS = "!Transactions";
  private static final char REMINDER_COMPLETE = 'C';
  private static final char REMINDER_DAYS_TO_ALARM = 'A';
  private static final char REMINDER_DUE_BY = 'D';
  private static final char TRANS_ACCOUNT = '@';
  private static final char TRANS_AMOUNT = '\u00A4';
  private static final char TRANS_CHECK_NUMBER = '#';
  private static final char TRANS_CATEGORY = 'G';
  private static final char TRANS_DATE = 'D';
  private static final char TRANS_LABEL = 'L';
  private static final char TRANS_NOTES = 'N';
  private static final char TRANS_PAYEE = 'P';
  private static final char TRANS_RECONCILED = 'R';
  private static final char UID = 'U';
}
