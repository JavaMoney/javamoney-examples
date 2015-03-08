package javamoneyexamples.jsf.money.converter;

import java.io.IOException;
import java.text.NumberFormat;

import javax.money.MonetaryAmount;
import javax.money.format.AmountFormatContext;
import javax.money.format.MonetaryAmountFormat;
import javax.money.format.MonetaryParseException;

import org.javamoney.moneta.Money;

public class MonetaryFormatTable implements MonetaryAmountFormat {

	@Override
	public String queryFrom(MonetaryAmount amount) {
		return amount.toString();
	}

	@Override
	public AmountFormatContext getContext() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void print(Appendable appendable, MonetaryAmount amount) throws IOException {
		NumberFormat format = NumberFormat.getInstance();
		format.setMaximumFractionDigits(2);
		format.setMinimumFractionDigits(2);
		appendable.append(amount.getCurrency().toString());
		appendable.append(" ").append(format.format(amount.getNumber()));
	}

	@Override
	public MonetaryAmount parse(CharSequence text) throws MonetaryParseException {
		return Money.parse(text);
	}

}
