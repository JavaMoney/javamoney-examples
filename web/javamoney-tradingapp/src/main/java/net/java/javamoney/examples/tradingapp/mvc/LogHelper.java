package net.java.javamoney.examples.tradingapp.mvc;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class LogHelper {
    private static final Log logger = LogFactory.getLog(LogHelper.class);

	/**
	 * @return the logger
	 */
	public static final Log getLogger() {
		return logger;
	}

    
}
