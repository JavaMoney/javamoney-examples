/*
 * JSR 354 JavaFX Binding Example
 */
package net.java.javamoney.javafx.value;

import javafx.beans.value.ObservableValue;

import javax.money.MonetaryAmount;

/**
 *
 * @author Werner Keil
 */
public interface ObservableMonetaryValue  extends ObservableValue<MonetaryAmount> {
    public int intValue();

    public long longValue();

    public float floatValue();

    public double doubleValue();
   
}
