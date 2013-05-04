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
package net.java.javamoney.examples.tradingapp.currency;

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
