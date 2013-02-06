package net.java.javamoney.examples.tradingapp.repo;

import static net.java.javamoney.examples.tradingapp.Constants.PROPS_EXT;
import static net.java.javamoney.examples.tradingapp.Constants.TRADING;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import net.neurotech.currency.ConvertSource;

import org.apache.log4j.Logger;

/**
 * @author Werner Keil
 * @see ConvertSource
 */
public abstract class CurrencyManager {
	
	/**
	 * Singleton
	 */
	private CurrencyManager() {}
	
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(CurrencyManager.class);
	private static final Properties props = new Properties();
    private static ConvertSource myConverter = null;
      
    public static final ConvertSource getConvertSource() {
        
    	if (myConverter != null) {
    		return myConverter;
    	}
    	else {
	        try {
	        	InputStream in = CurrencyManager.class.getResourceAsStream 
	        	("/" + TRADING + PROPS_EXT);
	        	props.load(in);
	        } catch (IOException ie) {
	        	logger.warn("Error loading properties", ie); 
	        }
	        
	        String sourceName = props.getProperty(TRADING + ".currencysource", 
	        "net.neurotech.currency.YahooParser");
	        
	        //myConverter = new QuoteFactory(sourceName);
	        myConverter = new YahooParserOffline();
	        
	        return myConverter;
    	}
    }
	
}
