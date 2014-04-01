// Account

package org.javamoney.examples.ez.money.model.persisted.account;

import static org.javamoney.examples.ez.money.ApplicationProperties.UI_CURRENCY_SYMBOL;
import static org.javamoney.examples.ez.money.ApplicationProperties.creditBalanceIsPositive;
import static org.javamoney.examples.ez.money.model.persisted.account.AccountTypeKeys.CREDIT;

import javax.money.MonetaryAmount;

import org.javamoney.examples.ez.money.model.DataElement;
import org.javamoney.examples.ez.money.model.persisted.transaction.Transaction;
import org.javamoney.examples.ez.money.model.persisted.transaction.TransactionSet;
import org.javamoney.moneta.Money;

/**
 * This class encompasses all the elements that make up an account and
 * facilitates the management of transactions.
 * 
 * @author Werner Keil
 */
public final class Account extends DataElement {
	/**
	 * Constructs a new account.
	 * 
	 * @param type
	 *            The type of account.
	 * @param identifier
	 *            The identifier.
	 */
	public Account(AccountTypeKeys type, String identifier) {
		this(type, identifier, 0.0);
	}

	/**
	 * Constructs a new account.
	 * 
	 * @param type
	 *            The type of account.
	 * @param identifier
	 *            The unique identifier.
	 * @param balance
	 *            The starting balance.
	 */
	public Account(AccountTypeKeys type, String identifier, double balance) {
		this(type, identifier, Money.of(balance,
				UI_CURRENCY_SYMBOL.getCurrency()));
	}

	/**
	 * Constructs a new account.
	 * 
	 * @param type
	 *            The type of account.
	 * @param identifier
	 *            The unique identifier.
	 * @param balance
	 *            The starting balance.
	 */
	public Account(AccountTypeKeys type, String identifier,
			MonetaryAmount balance) {
		super(identifier);

		setBalance(balance);
		setIsActive(true);
		setTransactions(new TransactionSet());
		setType(type);
	}

	/**
	 * This method adds all the transactions from the specified account.
	 * 
	 * @param account
	 *            The account to add the transactions from.
	 */
	public void addAll(Account account) {
		getTransactions().addAll(account.getTransactions());
	}

	/**
	 * This method adds the transaction and then returns the result of the
	 * operation.
	 * <p>
	 * <b>Note:</b> If the transaction causes the balance to exceed the maximum,
	 * then the transaction will be removed.
	 * 
	 * @param trans
	 *            The transaction to add.
	 * 
	 * @return true or false.
	 */
	public boolean addTransaction(Transaction trans) {
		boolean result = getTransactions().add(trans);

		if (result == true) {
			result = setBalance(getBalance().add(trans.getAmount()));

			// Remove the transaction if the balance could not be set.
			if (result == false) {
				getTransactions().remove(trans);
			}
		}

		return result;
	}

	/**
	 * This method returns the balance.
	 * 
	 * @return The balance.
	 */
	public MonetaryAmount getBalance() {
		return itsBalance;
	}

	/**
	 * This method returns the balance customized for the UI.
	 * <p>
	 * <b>Note:</b> A balance can have different meanings amongst the account
	 * types. A deposit account's balance for example, represents how much money
	 * is available. A credit account's balance however, represents how much is
	 * owed. For non-credit accounts, this method will always return the actual
	 * balance. Otherwise, the result will depend on whether or not the user
	 * chooses to display credit card balances as positive (amount owed).
	 * 
	 * @return The balance customized for the UI.
	 */
	public double getBalanceForUI() {
		double balance = getBalance().getNumber().doubleValue();

		if (getType() == CREDIT && creditBalanceIsPositive() == true) {
			balance = -balance;
		}

		return balance;
	}

	/**
	 * This method returns the transactions.
	 * 
	 * @return The transactions.
	 */
	public TransactionSet getTransactions() {
		return itsTransactions;
	}

	/**
	 * This method returns the type.
	 * 
	 * @return The type.
	 */
	public AccountTypeKeys getType() {
		return itsType;
	}

	/**
	 * This method returns true if the account is active, otherwise false.
	 * 
	 * @return true or false.
	 */
	public boolean isActive() {
		return itsIsActive;
	}

	/**
	 * This method removes the transaction and then returns the result of the
	 * operation.
	 * <p>
	 * <b>Note:</b> If the transaction causes the balance to exceed the maximum,
	 * then the transaction will be added back.
	 * 
	 * @param trans
	 *            The transaction to remove.
	 * 
	 * @return true or false.
	 */
	public boolean removeTransaction(Transaction trans) {
		boolean result = getTransactions().remove(trans);

		if (result == true) {
			result = setBalance(getBalance().subtract(trans.getAmount()));

			// Remove the transaction if the balance could not be set.
			if (result == false) {
				getTransactions().add(trans);
			}
		}

		return result;
	}

	/**
	 * This method sets the balance as long as the amount does not exceed the
	 * maximum balance. This method returns true if the balance was set,
	 * otherwise false.
	 * 
	 * @param value
	 *            The balance.
	 * 
	 * @return true or false.
	 */
	public boolean setBalance(MonetaryAmount value) {
		boolean result = exceedsThreshold(value);

		if (result == false) {
			itsBalance = value;
		}

		return result == false;
	}

	/**
	 * This method sets the account's active state to that of the passed in
	 * parameter.
	 * 
	 * @param value
	 *            true or false.
	 */
	public void setIsActive(boolean value) {
		itsIsActive = value;
	}

	// ////////////////////////////////////////////////////////////////////////////
	// Start of private methods.
	// ////////////////////////////////////////////////////////////////////////////

	private static boolean exceedsThreshold(MonetaryAmount value) {
		boolean result = false;
		if (value != null && value.getNumber() != null) {
			if (value.getNumber().doubleValue() >= MAX_BALANCE
					|| value.getNumber().doubleValue() <= -MAX_BALANCE) {
				result = true;
			}
		}
		return result;
	}

	private void setTransactions(TransactionSet set) {
		itsTransactions = set;
	}

	private void setType(AccountTypeKeys type) {
		itsType = type;
	}

	// ////////////////////////////////////////////////////////////////////////////
	// Start of class members.
	// ////////////////////////////////////////////////////////////////////////////

	private MonetaryAmount itsBalance;
	private boolean itsIsActive;
	private TransactionSet itsTransactions;
	private AccountTypeKeys itsType;

	/**
	 * The maximum positive and negative amount before an overflow occurs.
	 */
	public static final double MAX_BALANCE = 99999999.99;
}
