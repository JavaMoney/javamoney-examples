JSR 354 JavaMoney Examples
==========================

The current project structure is as follows:

- [Console examples](console)
  - [Simple Console Examples](console/javamoney-console-simple)
  - [Java 8 Console Examples](console/javamoney-console-java8)
  - [Java 10 Console Examples](console/javamoney-console-java10) **disabled on CI because we still support JavaFX 8**
  - [Java Functional Examples](console/functional-example): examples using Java 8 with streams, lambda and money-api
  - [Money Machine](console/moneymachine): Adopt JSR API Testing project for getting feedback on the API
- [JavaFX examples](javafx)
 - [JavaFX Demo application](javafx/money-fxdemo)
 - [JavaFX binding examples](javafx/money-javafx-binding)
- [Swing RCP examples](swing)
  - [An improved successor to EZ Money.](swing/javamoney-ez) 
- [Web examples](web)
  - [Demo for CDI Events with the Money and Currency API](web/javamoney-payment-cdi-event)
  - [Spring Trading App](web/javamoney-tradingapp)  currently **unstable**/disabled
  - [Java EE with JAX-RS](web/jax-rs-money) currently **unstable/broken**/disabled
  - [Example using JSF with MoneyAPI](web/jsf-money)

[![Build Status](https://api.travis-ci.org/JavaMoney/javamoney-examples.png?branch=master)](https://travis-ci.org/JavaMoney/javamoney-examples) [![License](http://img.shields.io/badge/license-Apache2-red.svg)](http://opensource.org/licenses/apache-2.0) 

[![Built with Maven](http://maven.apache.org/images/logos/maven-feather.png)](http://maven.org/)
