package org.javamoney.examples.jaxrs.infrastructure;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.money.MonetaryAmount;
import javax.ws.rs.Consumes;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.Provider;

import org.apache.commons.io.IOUtils;
import org.javamoney.moneta.Money;

import com.google.gson.Gson;


@Provider
@Consumes(MediaType.APPLICATION_JSON)
public class MonetaryAmountCollectionReaderProvider implements MessageBodyReader<Collection<MonetaryAmount>> {

	private Gson gson = new Gson();

	@Override
	public boolean isReadable(Class<?> type, Type genericType,
			Annotation[] annotations, MediaType mediaType) {
		ParameterizedType parametrized = (ParameterizedType)genericType;
		@SuppressWarnings("rawtypes")
		Class monetaryClass = (Class) parametrized.getActualTypeArguments()[0];
		return Collection.class.isAssignableFrom(type) && MonetaryAmount.class.isAssignableFrom(monetaryClass);
	}

	@Override
	public Collection<MonetaryAmount> readFrom(Class<Collection<MonetaryAmount>> type,
			Type genericType, Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, String> httpHeaders, InputStream entityStream)
			throws IOException, WebApplicationException {

		String json = IOUtils.toString(entityStream);
		String[] texts = gson.fromJson(json, String[].class);
		return Stream.of(texts).map(Money::parse).collect(Collectors.toList());
	}

}
