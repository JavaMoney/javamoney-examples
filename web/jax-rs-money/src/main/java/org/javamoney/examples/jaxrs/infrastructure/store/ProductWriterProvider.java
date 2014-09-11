package org.javamoney.examples.jaxrs.infrastructure.store;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import org.javamoney.examples.jaxrs.model.Product;

import com.google.gson.Gson;


@Provider
@Produces(MediaType.APPLICATION_JSON)
public class ProductWriterProvider implements MessageBodyWriter<Product> {

	private Gson gson = new Gson();

	@Override
	public boolean isWriteable(Class<?> type, Type genericType,
			Annotation[] annotations, MediaType mediaType) {
		return type == Product.class; 
	}

	@Override
	public long getSize(Product product, Class<?> type, Type genericType,
			Annotation[] annotations, MediaType mediaType) {
		return 0;
	}

	@Override
	public void writeTo(Product product, Class<?> type, Type genericType,
			Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, Object> httpHeaders,
			OutputStream entityStream) throws IOException,
			WebApplicationException {
			
		entityStream.write(gson.toJson(new ProductJson(product)).getBytes());
	}
	

}
