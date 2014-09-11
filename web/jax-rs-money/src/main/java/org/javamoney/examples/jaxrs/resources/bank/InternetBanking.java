package org.javamoney.examples.jaxrs.resources.bank;

import java.util.List;

import javax.money.MonetaryAmount;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.javamoney.examples.jaxrs.bank.FinancialActions;
import org.javamoney.examples.jaxrs.bank.HistoryBankAccount;
import org.javamoney.examples.jaxrs.model.BankAccount;

public interface InternetBanking extends FinancialActions {

	@POST
	@Path("/deposit/")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	MonetaryAmount deposit(BankAccount account);

	@POST
	@Path("/withDraw/")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	MonetaryAmount withDraw(BankAccount account);

	@POST
	@Path("/all/")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	List<MonetaryAmount> all(BankAccount account);

	@POST
	@Path("/extract/")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	List<HistoryBankAccount> extract(BankAccount account);


}
