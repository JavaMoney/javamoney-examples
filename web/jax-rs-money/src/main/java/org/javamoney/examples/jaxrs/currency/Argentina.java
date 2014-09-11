package org.javamoney.examples.jaxrs.currency;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.inject.Qualifier;


@Target({TYPE, METHOD, FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Qualifier
public @interface Argentina {

}
