/*
 * JSR 354 JavaFX Binding Example
 */
package net.java.javamoney.javafx.binding;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import javax.money.CurrencyUnit;
import javax.money.MonetaryAmount;

import org.javamoney.moneta.function.CurrencySupplier;

import net.java.javamoney.javafx.value.ObservableMonetaryValue;

/**
 *
 * @author Werner Keil
 */
public class MonetaryExpressionBase implements MonetaryExpression, Observable,
        ObservableMonetaryValue, ObservableValue<MonetaryAmount>, CurrencySupplier {

	private MonetaryAmount amount;
	
    @Override
    public void addListener(InvalidationListener il) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeListener(InvalidationListener il) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int intValue() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public long longValue() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public float floatValue() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double doubleValue() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addListener(ChangeListener<? super MonetaryAmount> cl) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeListener(ChangeListener<? super MonetaryAmount> cl) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public MonetaryAmount getValue() {
        return amount;
    }

	@Override
	public CurrencyUnit getCurrency() {
		// TODO Auto-generated method stub
		return null;
	}
}
