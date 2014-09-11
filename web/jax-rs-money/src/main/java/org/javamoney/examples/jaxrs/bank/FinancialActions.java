package org.javamoney.examples.jaxrs.bank;

import java.util.List;

import javax.money.MonetaryAmount;

import org.javamoney.examples.jaxrs.model.BankAccount;

public interface FinancialActions {

	/**
	 * insert money from bank account.
	 * @param account a bank account
	 * @return the result of operation in the specific currency.
	 */
	MonetaryAmount deposit(BankAccount account);
	
	/**
	 * Remove money from bank account.
	 * @param account a bank account
	 * @return the result of operation in the specific currency.
	 */
	MonetaryAmount withDraw(BankAccount account);
	
	/**
	 * Show all money and currencies in this count.
	 * @param account - the bank account
	 * @return all currency available to this bank account
	 */
	List<MonetaryAmount> all(BankAccount account);
	
	/**
	 * show history of a user between a period.
	 * @param account - account bank and begin and end should not be null
	 * @return the historic bank account
	 */
	List<HistoryBankAccount> extract(BankAccount account);
	
}
