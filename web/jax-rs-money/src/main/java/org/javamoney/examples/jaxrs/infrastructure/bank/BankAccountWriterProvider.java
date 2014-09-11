package org.javamoney.examples.jaxrs.infrastructure.bank;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

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
public class BankAccountWriterProvider implements MessageBodyWriter<BankAccount> {

	private Gson gson = new Gson();
	
	@Override
	public boolean isWriteable(Class<?> type, Type genericType,
			Annotation[] annotations, MediaType mediaType) {
		return BankAccount.class.isAssignableFrom(type);
	}

	@Override
	public long getSize(BankAccount t, Class<?> type, Type genericType,
			Annotation[] annotations, MediaType mediaType) {
		return 0;
	}

	@Override
	public void writeTo(BankAccount t, Class<?> type, Type genericType,
			Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, Object> httpHeaders,
			OutputStream entityStream) throws IOException,
			WebApplicationException {
		entityStream.write(gson.toJson(new BankAccountJS(t)).getBytes());
	}

}
