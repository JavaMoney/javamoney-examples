/*
 * JSR 354 JavaFX Binding Example
 */
package net.java.javamoney.javafx.property;

import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;

import javax.money.MonetaryAmount;

/**
 *
 * @author Werner Keil
 */
public abstract class MonetaryProperty extends ReadOnlyMonetaryProperty {

	@Override
	public void addListener(ChangeListener<? super MonetaryAmount> arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public MonetaryAmount getValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeListener(ChangeListener<? super MonetaryAmount> arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addListener(InvalidationListener arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeListener(InvalidationListener arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object getBean() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}
    
}
