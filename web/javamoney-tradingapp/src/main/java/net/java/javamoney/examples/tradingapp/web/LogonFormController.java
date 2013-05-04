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