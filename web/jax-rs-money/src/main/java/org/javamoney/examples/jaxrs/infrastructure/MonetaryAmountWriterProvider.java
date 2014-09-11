package org.javamoney.examples.jaxrs.infrastructure;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.money.MonetaryAmount;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;


@Provider
@Produces(MediaType.APPLICATION_JSON)
public class MonetaryAmountWriterProvider implements MessageBodyWriter<MonetaryAmount> {


	@Override
	public boolean isWriteable(Class<?> type, Type genericType,
			Annotation[] annotations, MediaType mediaType) {
		
		return MonetaryAmount.class.isAssignableFrom(type); 
	}

	@Override
	public long getSize(MonetaryAmount monetaryAmount, Class<?> type, Type genericType,
			Annotation[] annotations, MediaType mediaType) {
		return 0;
	}

	@Override
	public void writeTo(MonetaryAmount monetaryAmount, Class<?> type, Type genericType,
			Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, Object> httpHeaders,
			OutputStream entityStream) throws IOException,
			WebApplicationException {
			
		entityStream.write(monetaryAmount.toString().getBytes());
	}
	

}
