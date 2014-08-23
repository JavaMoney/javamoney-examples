java functional
==================

JSR 354 Java Functional Examples

same examples using Java 8 with streams, lambda with money-api

---------

- **CreateCurrencyUnit** – The currency unit, it is represented using the CurrencyUnit's interface, it's possible make a instance some forms as inform the currency code or using java.util.Locale of country of origin.

- **CreateMonetaryCurrency** – The monetary value, this class has a strong relationship with a currency. The interface is MonetaryAmount and some implementations are:
Money: implementation that uses BigDecimal to represents the value.
FastMoney: this implementation uses long instead of BigDecimal, it is faster than Money, about 15 
times, however has a limited decimals places, just five.

- **FormatExample** – format the money

- **MonetaryFilterOperations** - You may do selection to just one or more currency unit, beyond from a value (greater than, lesser, than, greater and equal than, between, etc.).

- **MonetaryReducerOperations** - Reducer operations such, the greatest value, lesser value, sum.

- **MonetarySorterOperations** - Operation to do sort in a list or stream, so it's possible sort by CurrencyUnit or by value, in asc or desc way, and you can combine, in other words, I may sort by currency unit in ascending way and descending by value.

- **MonetaryGroupOperations** - From Stream or list is possible create a summary (an object that contains informations as size of list, sum, lesser value, greater value and average), a map where the key is CurrencyUnit and the value is a list of MonetaryAmount and a map where the key is CurrencyUnit and the value is a summary of this currency unit.


- **SimpleOperations** – simples operations using MonetaryAmount, that included arithmetic operations such sum, subtract and comparative operations such lesser equal, greater equal, etc.

