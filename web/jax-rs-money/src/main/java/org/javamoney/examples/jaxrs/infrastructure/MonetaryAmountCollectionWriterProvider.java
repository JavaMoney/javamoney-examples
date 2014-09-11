package org.javamoney.examples.jaxrs.infrastructure;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.money.MonetaryAmount;
import javax.ws.rs.Consumes;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import com.google.gson.Gson;


@Provider
@Consumes(MediaType.APPLICATION_JSON)
public class MonetaryAmountCollectionWriterProvider implements MessageBodyWriter<Collection<MonetaryAmount>> {

	private Gson gson = new Gson();

	@Override
	public boolean isWriteable(Class<?> type, Type genericType,
			Annotation[] annotations, MediaType mediaType) {
		ParameterizedType parametrized = (ParameterizedType)genericType;
		@SuppressWarnings("rawtypes")
		Class monetaryClass = (Class) parametrized.getActualTypeArguments()[0];
		return Collection.class.isAssignableFrom(type) && MonetaryAmount.class.isAssignableFrom(monetaryClass);
	}

	@Override
	public long getSize(Collection<MonetaryAmount> t, Class<?> type,
			Type genericType, Annotation[] annotations, MediaType mediaType) {
		return 0;
	}

	@Override
	public void writeTo(Collection<MonetaryAmount> types, Class<?> type,
			Type genericType, Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, Object> httpHeaders,
			OutputStream entityStream) throws IOException,
			WebApplicationException {
		List<String> collect = types.stream().map(MonetaryAmount::toString).collect(Collectors.toList());
		entityStream.write(gson.toJson(collect).getBytes());
	}


}
