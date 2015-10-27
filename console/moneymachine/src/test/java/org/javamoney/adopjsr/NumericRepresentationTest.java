package org.javamoney.adopjsr;


import org.javamoney.moneta.FastMoney;
import org.javamoney.moneta.Money;
import org.junit.Test;

import javax.money.MonetaryAmount;
import javax.money.NumberValue;
import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class NumericRepresentationTest{

    NumericRepresentation np = new NumericRepresentation();

    @Test
    public void testGetBigDecimal() throws Exception{
        BigDecimal bd = new BigDecimal("123435443565465409683468327434324.3748634");
        MonetaryAmount amount = Money.of(bd, "INR");
        BigDecimal bd2 = np.getBigDecimal(amount);
        assertEquals("Invalid BigDecimal value", bd.stripTrailingZeros(), bd2.stripTrailingZeros());
    }

    @Test
    public void testGetLongTruncated() throws Exception{
        BigDecimal bd = new BigDecimal(
                "1234354435654654092372883687236483274632894623894693284632864832648329462836473276352763527615382176521785321876352178654327684532765218745823745327865327654723684683468327434324.3748634");
        MonetaryAmount amount = Money.of(bd, "CHF");
        assertEquals("Invalid truncated long", amount.getNumber().longValue(), np.getLongTruncated(amount));
    }

    @Test
    public void testGetPrecision() throws Exception{
        BigDecimal bd = new BigDecimal(
                "1234354435654654092372883687236483274632894623894693284632864832648329462836473276352763527615382176521785321876352178654327684532765218745823745327865327654723684683468327434324.3748634");
        MonetaryAmount amount = Money.of(bd, "CHF");
        assertEquals("Invalid precision for " + bd, amount.getNumber().getPrecision(), np.getPrecision(amount));
    }

    @Test
    public void testGetScale() throws Exception{
        BigDecimal bd = new BigDecimal(
                "1234354435654654092372883687236483274632894623894693284632" +
                        ".8648326483294628364732763527635276153821765217853218763521786543276845327652187458237453278653276547236846834683274343243748634");
        MonetaryAmount amount = Money.of(bd, "CHF");
        assertEquals("Invalid scale for " + bd, amount.getNumber().getScale(), np.getScale(amount));
    }

    @Test
    public void testGetFractionDenominator() throws Exception{
        BigDecimal bd = new BigDecimal("0.1234523");
        MonetaryAmount amount = Money.of(bd, "USD");
        assertEquals("Invalid fraction denominator for " + bd, amount.getNumber().getAmountFractionDenominator(),
                     np.getFractionDenominator(amount));
    }

    @Test
    public void testGetFractionNumerator() throws Exception{
        BigDecimal bd = new BigDecimal("0.1234553");
        MonetaryAmount amount = Money.of(bd, "USD");
        assertEquals("Invalid fraction numerator for " + bd, amount.getNumber().getAmountFractionDenominator(),
                     np.getFractionNumerator(amount));
    }

    @Test
    public void testGetNumberType() throws Exception{
        BigDecimal bd = new BigDecimal("0.1234523");
        MonetaryAmount[] amounts = new MonetaryAmount[]{Money.of(bd, "USD"), FastMoney.of(2334434354L, "CHF")};
        for(MonetaryAmount amount : amounts){
            assertEquals("Invalid number type for " + amount, amount.getNumber().getNumberType(),
                         np.getNumberType(amount));
        }
    }

    @Test
    public void testGetNumber() throws Exception{
        BigDecimal bd = new BigDecimal("0.1234523");
        MonetaryAmount[] amounts = new MonetaryAmount[]{Money.of(bd, "USD"), FastMoney.of(2334434354L, "CHF")};
        for(MonetaryAmount amount : amounts){
            assertEquals("Invalid number type for " + amount, amount.getNumber().getNumberType(),
                         np.getNumberType(amount));
        }
    }

    @Test
    public void testGetJDKNumber() throws Exception{
        BigDecimal bd = new BigDecimal("0.1234523");
        MonetaryAmount[] amounts = new MonetaryAmount[]{Money.of(bd, "USD"), FastMoney.of(2334434354L, "CHF")};
        for(MonetaryAmount amount : amounts){
            assertTrue("JDK Number can be more easily extracted for " + amount,
                       np.getNumber(amount) instanceof NumberValue);
            assertEquals("Invalid JDK Number for " + amount, amount.getNumber(), np.getNumber(amount));
        }
    }
}