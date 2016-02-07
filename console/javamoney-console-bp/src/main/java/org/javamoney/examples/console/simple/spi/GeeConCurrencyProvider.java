package org.javamoney.examples.console.simple.spi;

import org.javamoney.moneta.CurrencyUnitBuilder;

import javax.annotation.Priority;
import javax.money.CurrencyContext;
import javax.money.CurrencyContextBuilder;
import javax.money.CurrencyQuery;
import javax.money.CurrencyUnit;
import javax.money.spi.CurrencyProviderSpi;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Priority(2000)
public class GeeConCurrencyProvider implements CurrencyProviderSpi{

    private Set<CurrencyUnit> currencies = new HashSet<>();
    private CurrencyContext context = CurrencyContextBuilder.of("GeeCon").build();

    public GeeConCurrencyProvider(){
        currencies.add(CurrencyUnitBuilder.of("GeeCon1",context ).setDefaultFractionDigits(1).build());
        currencies.add(CurrencyUnitBuilder.of("GeeCon2",context ).setDefaultFractionDigits(2).build());
        currencies.add(CurrencyUnitBuilder.of("GeeCon3",context ).setDefaultFractionDigits(3).build());
        currencies = Collections.unmodifiableSet(currencies);
    }

    @Override
    public Set<CurrencyUnit> getCurrencies(CurrencyQuery query) {
        if(Boolean.TRUE.equals(query.getBoolean("GeeCon"))) {
            return currencies;
        }
        return Collections.emptySet();
    }

	@Override
	public String getProviderName() {
		return "GeeCon";
	}

	@Override
	public boolean isCurrencyAvailable(CurrencyQuery query) {
		// TODO Auto-generated method stub
		return false;
	}
}
