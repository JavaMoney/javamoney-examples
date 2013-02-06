package net.java.javamoney.examples.tradingapp.repo;

import static net.java.javamoney.examples.tradingapp.Constants.PROPS_EXT;
import static net.java.javamoney.examples.tradingapp.Constants.TRADING;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import net.neurotech.quotes.QuoteFactory;

import org.apache.log4j.Logger;

/**
 * @author Werner Keil
 * @see QuoteFactory
 */
public abstract class QuoteManager {
	
	/**
	 * Singleton
	 */
	private QuoteManager() {}
	
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(QuoteManager.class);
	private static final Properties props = new Properties();
    private static QuoteFactory quoteFactory = null;
 
    public static final QuoteFactory getQuoteFactory() {
        
    	if (quoteFactory != null) {
    		return quoteFactory;
    	}
    	else {
	        try {
	        	InputStream in = QuoteManager.class.getResourceAsStream 
	        		("/" + TRADING + PROPS_EXT);
	        	props.load(in);
	        } catch (IOException ie) {
	        	logger.warn("Error loading properties", ie); 
	        }
	        
	        String quoteSource = props.getProperty(TRADING + ".quotesource", 
	        "net.neurotech.quotes.YahooCSVSource");
	        
	        quoteFactory = new QuoteFactory(quoteSource);
	        
	        return quoteFactory;
    	}
    }
	
}
