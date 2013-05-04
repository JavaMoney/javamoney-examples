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

import net.neurotech.currency.ConversionException;
import net.neurotech.currency.YahooParser;
import net.neurotech.quotes.YahooCSVSource;

import org.apache.log4j.Logger;

/**
 * @author Werner Keil
 * @see YahooCSVSource
 */
public class YahooParserOffline extends YahooParser {
	//private Quote quote;
	//private String symbol;

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(YahooParserOffline.class);
	
	public YahooParserOffline() {
		super();
		logger.info("in constructor"); // TODO change to debug later.
	}
	
	private static final String stripQuotes(String q)
	{
		String s;

		s = q.substring(1, q.length() - 1);

		return s;
	}

	/* (non-Javadoc)
	 * @see net.neurotech.currency.YahooParser#getConverted(float, java.lang.String, java.lang.String)
	 */
	@Override
	public synchronized float getConverted(float arg0, String arg1, String arg2) throws ConversionException {
		// TODO Auto-generated method stub
		return super.getConverted(arg0, arg1, arg2);
	}

	/* (non-Javadoc)
	 * @see net.neurotech.currency.YahooParser#getResult()
	 */
	@Override
	public float getResult() {
		// TODO Auto-generated method stub
		return super.getResult();
	}

	/* (non-Javadoc)
	 * @see net.neurotech.currency.YahooParser#handleText(char[], int)
	 */
	@Override
	public void handleText(char[] arg0, int arg1) {
		// TODO Auto-generated method stub
		super.handleText(arg0, arg1);
	}

	/* (non-Javadoc)
	 * @see net.neurotech.currency.YahooParser#test()
	 */
	@Override
	public boolean test() {
		// TODO Auto-generated method stub
		return super.test();
	}
}
