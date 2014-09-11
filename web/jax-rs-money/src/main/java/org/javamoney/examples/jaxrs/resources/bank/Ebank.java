package org.javamoney.examples.jaxrs.resources.bank;

import java.time.LocalDateTime;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.money.CurrencyUnit;
import javax.money.MonetaryAmount;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.javamoney.examples.jaxrs.bank.Bank;
import org.javamoney.examples.jaxrs.bank.HistoryBankAccount;
import org.javamoney.examples.jaxrs.currency.Brazil;
import org.javamoney.examples.jaxrs.model.BankAccount;
import org.javamoney.moneta.Money;

@Path("/bank")
@RequestScoped
public class Ebank implements InternetBanking {

	@Inject
	private Bank bank;
	
	@Inject
	@Brazil
	private CurrencyUnit unit;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public BankAccount example() {
		BankAccount account = new BankAccount();
		account.setBegin(LocalDateTime.now());
		account.setEnd(LocalDateTime.now());
		account.setUser("otaviojava");
		account.setValue(Money.of(12, unit));
		return account;
	}
	@Override
	public MonetaryAmount deposit(BankAccount account) {
		return bank.deposit(account);
	}

	@Override
	public MonetaryAmount withDraw(BankAccount account) {
		return bank.withDraw(account);
	}

	@Override
	public List<MonetaryAmount> all(BankAccount account) {
		return bank.all(account);
	}


	@Override
	public List<HistoryBankAccount> extract(BankAccount account) {
		return bank.extract(account);
	}

}
