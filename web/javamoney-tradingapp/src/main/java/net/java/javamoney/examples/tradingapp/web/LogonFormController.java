/*
 * JSR 354 Stock-Trading Example
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
package net.java.javamoney.examples.tradingapp.web;

import static net.java.javamoney.examples.tradingapp.Constants.MARKET;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;

import net.java.javamoney.examples.tradingapp.business.Credentials;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;


public class LogonFormController extends SimpleFormController {
    public ModelAndView onSubmit(Object command) throws ServletException {
    	ModelAndView mv = new ModelAndView(new RedirectView(getSuccessView()));
    	
    	if (command instanceof Credentials) {
        	Credentials c = (Credentials)command;
        	
        	//logger.info("Going to " + c.getMarket() + "...");
    	
        	Map<String, Object> model = new HashMap<String, Object>();
        	model.put(MARKET, c.getMarket());
        	
        	//ModelAndView mv = new ModelAndView(new RedirectView(getSuccessView()), MODEL, model);        	
        	mv.addObject(MARKET, c.getMarket());
        }
    	
    	return mv;
    }

}