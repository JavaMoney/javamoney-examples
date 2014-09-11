package org.javamoney.examples.jaxrs.infrastructure;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.money.MonetaryAmount;
import javax.ws.rs.Consumes;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.Provider;

import org.apache.commons.io.IOUtils;
import org.javamoney.moneta.Money;


@Provider
@Consumes(MediaType.APPLICATION_JSON)
public class MonetaryAmountReaderProvider implements MessageBodyReader<MonetaryAmount> {


	@Override
	public boolean isReadable(Class<?> type, Type genericType,
			Annotation[] annotations, MediaType mediaType) {
		return MonetaryAmount.class.isAssignableFrom(type); 
	}

	@Override
	public MonetaryAmount readFrom(Class<MonetaryAmount> type,
			Type genericType, Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, String> httpHeaders, InputStream entityStream)
			throws IOException, WebApplicationException {

		String text = IOUtils.toString(entityStream);
		return Money.parse(text);
	}

}
