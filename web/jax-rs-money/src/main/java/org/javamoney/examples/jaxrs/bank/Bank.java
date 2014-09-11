package org.javamoney.examples.jaxrs.bank;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.money.MonetaryAmount;

import org.javamoney.examples.jaxrs.model.BankAccount;
import org.javamoney.moneta.function.MonetaryFunctions;

@ApplicationScoped
public class Bank implements FinancialActions {
	
	private Map<String, List<HistoryBankAccount>> map;
	
	@PostConstruct
	public void init() {
		map = new HashMap<>();
	}

	@Override
	public MonetaryAmount deposit(BankAccount account) {
		map.putIfAbsent(account.getUser(), new ArrayList<>());
		HistoryBankAccount history = new HistoryBankAccount();
		history.setValue(account.getValue());
		history.setWhen(LocalDateTime.now());
		map.get(account.getUser()).add(history);
		return returnSum(account);
	}

	@Override
	public MonetaryAmount withDraw(BankAccount account) {
		map.putIfAbsent(account.getUser(), new ArrayList<>());
		HistoryBankAccount history = new HistoryBankAccount();
		history.setValue(account.getValue().negate());
		history.setWhen(LocalDateTime.now());
		map.get(account.getUser()).add(history);
		return returnSum(account);
	}
	
	@Override
	public List<MonetaryAmount> all(BankAccount account) {
		map.putIfAbsent(account.getUser(), new ArrayList<>());
		return map.get(account.getUser()).stream().map(h -> h.getValue())
				.collect(MonetaryFunctions.groupBySummarizingMonetary()).get()
				.entrySet().stream().map(s -> s.getValue().getSum())
				.collect(Collectors.toList());
	}

	
	@Override
	public List<HistoryBankAccount> extract(BankAccount account) {
		map.putIfAbsent(account.getUser(), new ArrayList<>());
		Predicate<HistoryBankAccount> isAfter = h -> h.getWhen().isAfter(account.getBegin());
		Predicate<HistoryBankAccount> isBefore = h -> h.getWhen().isBefore(account.getEnd());
		return map.get(account.getUser()).stream()
				.filter(isAfter.and(isBefore)).collect(Collectors.toList());
	}

	private MonetaryAmount returnSum(BankAccount account) {
		return map.get(account.getUser()).stream().map(h -> h.getValue())
				.filter(MonetaryFunctions.isCurrency(account.getValue()
						.getCurrency())).reduce(MonetaryFunctions.sum()).get();
	}

}
