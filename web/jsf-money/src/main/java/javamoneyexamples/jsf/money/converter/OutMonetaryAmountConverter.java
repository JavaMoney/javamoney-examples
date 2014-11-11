package javamoneyexamples.jsf.money.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.money.CurrencyUnit;
import javax.money.MonetaryAmount;
import javax.money.MonetaryCurrencies;

import org.javamoney.moneta.Money;

@FacesConverter("outMonetaryAmount")
public class OutMonetaryAmountConverter implements Converter {

	private static final CurrencyUnit DOLLAR = MonetaryCurrencies.getCurrency("USD");
		
	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
			return Money.of(Double.parseDouble(value), DOLLAR);
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {

		if (MonetaryAmount.class.isInstance(value)) {
			return new MonetaryFormatTable().format(MonetaryAmount.class.cast(value));
		}
		return null;
	}

}
