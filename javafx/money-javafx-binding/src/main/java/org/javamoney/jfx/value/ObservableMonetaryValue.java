/*
 * JSR 354 JavaFX Binding Example
 */
package org.javamoney.jfx.value;

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
