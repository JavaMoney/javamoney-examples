/*
 * JSR 354 JavaFX Binding Example
 */
package net.java.javamoney.examples.javafx;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

import org.javamoney.moneta.Money;
import org.javamoney.moneta.MoneyCurrency;

/**
 * @author Werner Keil
 */
public class Bill {
    
    // Define the property
    private DoubleProperty amountDue = new SimpleDoubleProperty();
    private double doubleValue = 10d;
    private MoneyCurrency currency =  MoneyCurrency.of("DKK");
    private Money newAmountDue = Money.of(currency, doubleValue);
            
    // Define a getter for the property's value
    public final double getAmountDue(){return amountDue.get();}
 
    public final Money getNewAmountDue() { return newAmountDue;}
    
    // Define a setter for the property's value
    public final void setAmountDue(double value){amountDue.set(value);}
 
     // Define a getter for the property itself
    public DoubleProperty amountDueProperty() {return amountDue;}
    
    
}
