package org.javamoney.examples.jaxrs.infrastructure.bank;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.ws.rs.Consumes;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.Provider;

import org.apache.commons.io.IOUtils;
import org.javamoney.examples.jaxrs.model.BankAccount;

import com.google.gson.Gson;

@Provider
@Consumes(MediaType.APPLICATION_JSON)
public class BankAccountCollectionReaderProvider implements MessageBodyReader<Collection<BankAccount>> {

	private Gson gson = new Gson();

	@Override
	public boolean isReadable(Class<?> type, Type genericType,
			Annotation[] annotations, MediaType mediaType) {

		ParameterizedType parametrized = (ParameterizedType)genericType;
		@SuppressWarnings("rawtypes")
		Class bankAccount = (Class) parametrized.getActualTypeArguments()[0];
		return Collection.class.isAssignableFrom(type) && BankAccount.class.isAssignableFrom(bankAccount);
	}

	@Override
	public Collection<BankAccount> readFrom(
			Class<Collection<BankAccount>> type, Type genericType,
			Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, String> httpHeaders, InputStream entityStream)
			throws IOException, WebApplicationException {
		
		String json = IOUtils.toString(entityStream);
		BankAccountJS[] banks = gson.fromJson(json, BankAccountJS[].class);
		return Stream.of(banks).map(BankAccountJS::to).collect(Collectors.toList());
	}


}
