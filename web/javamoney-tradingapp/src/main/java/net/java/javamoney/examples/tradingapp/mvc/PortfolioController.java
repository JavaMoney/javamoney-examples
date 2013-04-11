package net.java.javamoney.examples.tradingapp.mvc;

import static net.java.javamoney.examples.tradingapp.Constants.MARKET;
import static net.java.javamoney.examples.tradingapp.Constants.MODEL;
import static net.java.javamoney.examples.tradingapp.Constants.PORTFOLIO;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.measure.unit.UnitFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.neurotech.quotes.Quote;
import net.neurotech.quotes.QuoteException;
import net.neurotech.quotes.QuoteFactory;

import org.apache.log4j.Logger;
import org.jscience.economics.money.Currency;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import net.java.javamoney.examples.tradingapp.domain.Market;
import net.java.javamoney.examples.tradingapp.domain.Portfolio;
import net.java.javamoney.examples.tradingapp.repo.QuoteManager;

public class PortfolioController implements Controller {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(PortfolioController.class);
    
    private Market market;
	private net.java.javamoney.examples.tradingapp.domain.Portfolio portfolio;
    private static QuoteFactory quoteFactory;
    private Properties props;
    
    public PortfolioController(Portfolio portfolio, Market market) {
        this.props = new Properties(); // TODO i18n, use ResourceBundle or Messages where possible
    	this.market = market;
    	this.portfolio = portfolio;        
        quoteFactory = QuoteManager.getQuoteFactory();
        
        try {
        	InputStream in = PortfolioController.class.getResourceAsStream 
        	 ("/currency.properties");
        	props.load(in);
        	
        	if (Currency.getReferenceCurrency() != null) {
        		labelCurrency(Currency.getReferenceCurrency());
        	}       	
        } catch (IOException ie) {
        	logger.warn("Error loading properties", ie); 
        }
    }

    public ModelAndView handleRequest(HttpServletRequest request,
            HttpServletResponse response) {
        Map<String, Object> model = new HashMap<String, Object>();
        
        String m = null;
        Object o;
        Enumeration e = request.getParameterNames();
        while (e.hasMoreElements()) {        	
        	o = e.nextElement();
        	//logger.info("Param: " + o);
        	if (String.valueOf(o).equals(MODEL)) {
        		o = request.getParameter(MODEL);
	        	if (o instanceof Map) {
	        		//logger.info("It's a Map");
	        		model = (Map)o;        		
	        	}
        	} else if (String.valueOf(o).equals(MARKET)) {
        		m = request.getParameter(MARKET);
        	}
        }
        
        if (m != null && m.length()>0) {
        	String mDetails = this.market.getKnownMarkets().get(m);
        	logger.info("Market: " + m + "=" + mDetails);
        	market.setSymbol(m);        	
        	//model.put(MARKET, m);
        	 
        	String cSymbol = null;
        	String mName = null;
        	StringTokenizer st = new StringTokenizer(mDetails, ";");
        	while (st.hasMoreElements()) {
        		if (mName == null) {
        			mName = st.nextToken();
        		} else {
	        		if (cSymbol == null) {
	        			cSymbol = st.nextToken();
	        		}
        		}
        	}
        	if (mName != null) {
        		market.setName(mName);
        	}
        	if (cSymbol != null) {
        		Currency curr = new Currency(cSymbol);
        		
        		// TODO retrieve this from Yahoo Service similar to quotes
        		Object oRate = market.getCurrencyExchangeRates().get(cSymbol);
        		if (oRate != null) {
        			Number exchangeRate;
        			if (oRate instanceof Number) {
        				exchangeRate = (Number)oRate;
        			} else {
        				try {
        					exchangeRate = Double.parseDouble(String.valueOf(oRate));
        				} catch (NumberFormatException nfe) {
        					logger.warn("Error during currency conversion", nfe);
        					exchangeRate = 0;
        				}
        			}
        			logger.info(exchangeRate);
        			curr.setExchangeRate(exchangeRate.doubleValue());
        		} else {
        			logger.warn("Could not find exchange rate for " + curr);
        			curr.setExchangeRate(0); // TODO kind of dodgy, should throw exception or something?
        		}
        		//curr.setExchangeRate(1.4);
        		
        		labelCurrency(cSymbol, curr);
        		//Currency.setReferenceCurrency(curr);
        		portfolio.setCurrency(curr);
        		
        		// TODO portfolio should directly store market!
        		market.setCurrency(curr);        		
        	}
        }
        List<PortfolioItemBean> portfolioItems = getPortfolioItems();
        model.put(MARKET, market);
        model.put("currency", portfolio.getCurrency());
        model.put("cash", portfolio.getCash() + "");
        model.put("amount", portfolio.getAmount());
        model.put("amountLocal", portfolio.getAmount().to(portfolio.getCurrency()));
        model.put("portfolioItems", portfolioItems);

        return new ModelAndView(PORTFOLIO, MODEL, model);
    }

    private void labelCurrency(String symbol, Currency curr) {
		//String label = market.getCurrencyLabels().get(cSymbol);
		String label = props.getProperty(symbol, "");
		if (label != null && label.length()>0) {
			UnitFormat.getInstance().label(curr, label);
		}
    }
    
    private void labelCurrency(Currency curr) {
    	labelCurrency(curr.getCode(), curr);
    }
    
    private List<PortfolioItemBean> getPortfolioItems() {
        List<PortfolioItemBean> portfolioItems = new ArrayList<PortfolioItemBean>();

        Iterator symbolIter = portfolio.getSymbolIterator();
        //QuoteFactory quoteFactory = new QuoteFactory();
         
        while (symbolIter.hasNext()) {
            String symbol = (String) symbolIter.next();

            int shares = portfolio.getNumberOfShares(symbol);            

            Quote quote = null;

            try {
                quote = quoteFactory.getQuote(symbol);
            } catch (QuoteException e) {
            	logger.warn("Error during quote", e);
                quote = new Quote(this.getClass().getName()) {
                };
            }

            PortfolioItemBean portfolioItem = new PortfolioItemBean(portfolio);
            portfolioItem.setSymbol(symbol);
            portfolioItem.setShares(shares);
            portfolioItem.setQuote(quote);
            portfolioItem.setCurrentValue(shares * quote.getValue());
            portfolioItem.setGainLoss(shares * quote.getPctChange());
            portfolioItems.add(portfolioItem);
        }
        return portfolioItems;
    }
}