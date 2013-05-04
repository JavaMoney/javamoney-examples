/*
 * Java Financial Library
 * Extension
 * 
 * Copyright 2005-2013, Werner Keil and individual contributors by the @author tag. 
 * See the copyright.txt in the distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,  
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.java.javamoney.examples.tradingapp.quotes;

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
