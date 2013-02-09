package net.java.javamoney.examples.javafx;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;

/**
 *
 * @author Werner Keil
 */
public class Main {
        public static void main(String[] args) {
 
        Bill bill1 = new Bill();
        Bill bill2 = new Bill();
        Bill bill3 = new Bill();
 
        NumberBinding total =
          Bindings.add(bill1.amountDueProperty().add(bill2.amountDueProperty()),
              bill3.amountDueProperty());
        total.addListener(new InvalidationListener() {
 
        public void invalidated(Observable o) {
                System.out.println("The binding is now invalid.");
            }
        });

        // First call makes the binding invalid
        bill1.setAmountDue(200.00);

        // The binding is now invalid
        bill2.setAmountDue(100.00);
        bill3.setAmountDue(75.00);

        // Make the binding valid...
        System.out.println(total.getValue());

        // Make invalid... 
        bill3.setAmountDue(150.00);

        // Make valid...
        System.out.println(total.getValue());
        
        // Calling Money-API directly
        System.out.println(bill1.getNewAmountDue());
        System.out.println(bill2.getNewAmountDue().add(bill1.getNewAmountDue()));
    }
}
