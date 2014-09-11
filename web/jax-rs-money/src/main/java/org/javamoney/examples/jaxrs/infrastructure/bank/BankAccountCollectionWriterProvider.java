package org.javamoney.examples.jaxrs.infrastructure.bank;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.stream.Collectors;

import javax.ws.rs.Consumes;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import org.javamoney.examples.jaxrs.model.BankAccount;

import com.google.gson.Gson;

@Provider
@Consumes(MediaType.APPLICATION_JSON)
public class BankAccountCollectionWriterProvider implements
		MessageBodyWriter<Collection<BankAccount>> {

	private Gson gson = new Gson();

	@Override
	public boolean isWriteable(Class<?> type, Type genericType,
			Annotation[] annotations, MediaType mediaType) {
		ParameterizedType parametrized = (ParameterizedType) genericType;
		@SuppressWarnings("rawtypes")
		Class bankClass = (Class) parametrized.getActualTypeArguments()[0];
		return Collection.class.isAssignableFrom(type)
				&& BankAccount.class.isAssignableFrom(bankClass);
	}

	@Override
	public long getSize(Collection<BankAccount> t, Class<?> type,
			Type genericType, Annotation[] annotations, MediaType mediaType) {
		return 0;
	}

	@Override
	public void writeTo(Collection<BankAccount> types, Class<?> type,
			Type genericType, Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, Object> httpHeaders,
			OutputStream entityStream) throws IOException,
			WebApplicationException {

		entityStream.write(gson.toJson(
				types.stream().map(BankAccountJS::new)
						.collect(Collectors.toList())).getBytes());
	}

}
