package net.java.javamoney.examples.tradingapp.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.neurotech.quotes.Quote;
import net.neurotech.quotes.QuoteException;
import net.neurotech.quotes.QuoteFactory;

import org.jscience.physics.amount.Amount;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractWizardFormController;
import org.springframework.web.servlet.view.RedirectView;

import net.java.javamoney.examples.tradingapp.domain.Portfolio;
import net.java.javamoney.examples.tradingapp.domain.Trade;
import net.java.javamoney.examples.tradingapp.repo.QuoteManager;

public class TradeFormController extends AbstractWizardFormController {

    private Portfolio portfolio;
    private QuoteFactory quoteFactory;

    public TradeFormController(Portfolio portfolio) {
        this.portfolio = portfolio;
        setPages(new String[] { "trade", "trade-confirm" });
        setCommandName("trade");
        
        this.quoteFactory = QuoteManager.getQuoteFactory();        
    }

    protected Object formBackingObject(HttpServletRequest request) {
        Trade trade = new Trade();
        trade.setBuySell(Trade.BUY);
        return trade;
    }

    protected void onBind(HttpServletRequest request, Object command,
            BindException errors) {

        Trade trade = (Trade) command;

        if (symbolIsInvalid(trade.getSymbol())) {
            errors.rejectValue("symbol", "error.trade.invalid-symbol",
                    new Object[] { trade.getSymbol() },
                    "Invalid ticker symbol.");
        } else {
            Quote quote = null;
            try {
                quote = quoteFactory.getQuote(trade.getSymbol());
            } catch (QuoteException e) {
                throw new RuntimeException(e);
            }
            trade.setPrice(Amount.valueOf(quote.getValue(), portfolio.getCurrency()));
            trade.setSymbol(trade.getSymbol().toUpperCase());
        }
    }

    protected void validatePage(Object command, Errors errors, int page) {
        Trade trade = (Trade) command;

        if (tradeIsBuy(trade)) {
            if (insufficientFunds(trade)) {
                errors.reject("error.trade.insufficient-funds",
                        "Insufficient funds.");
            }
        } else if (tradeIsSell(trade)) {
            if (portfolio.contains(trade.getSymbol()) == false) {
                errors.rejectValue("symbol", "error.trade.dont-own",
                        "You don't own this stock.");
            } else if (notEnoughShares(trade)) {
                errors.rejectValue("quantity", "error.trade.not-enough-shares",
                        "Not enough shares.");
            }
        }
    }

    protected ModelAndView processFinish(HttpServletRequest request,
            HttpServletResponse response, Object command, BindException errors) {
        Trade trade = (Trade) command;

        if (trade.isBuySell() == Trade.BUY) {
            portfolio.buyStock(trade.getSymbol(), trade.getShares(), trade
                    .getPrice());
        } else {
            portfolio.sellStock(trade.getSymbol(), trade.getShares(), trade
                    .getPrice());
        }
        return new ModelAndView("trade-acknowledge", "trade", trade);
    }

    protected ModelAndView processCancel(HttpServletRequest request,
            HttpServletResponse response, Object command, BindException errors) {
        return new ModelAndView(new RedirectView("portfolio.htm"));
    }

    private boolean notEnoughShares(Trade trade) {
        return portfolio.getNumberOfShares(trade.getSymbol()) < trade
                .getShares();
    }

    private boolean insufficientFunds(Trade trade) {
        return portfolio.canBuy(trade.getShares(), trade.getPrice()) == false;
    }

    private boolean tradeIsBuy(Trade trade) {
        return trade.isBuySell() == Trade.BUY;
    }

    private boolean tradeIsSell(Trade trade) {
        return trade.isBuySell() == Trade.SELL;
    }

    private boolean symbolIsInvalid(String symbol) {
        if (symbol == null || symbol.equals("")) {
            return true;
        }
            
        //QuoteFactory quoteFactory = new QuoteFactory();
        
        try {
            quoteFactory.getQuote(symbol);
            return false;
        } catch (QuoteException e) {
            return true;
        }
    }
}