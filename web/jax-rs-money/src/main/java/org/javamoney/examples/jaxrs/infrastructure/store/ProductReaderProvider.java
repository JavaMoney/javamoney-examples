package org.javamoney.examples.jaxrs.infrastructure.store;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.Consumes;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.Provider;

import org.apache.commons.io.IOUtils;
import org.javamoney.examples.jaxrs.model.Product;

import com.google.gson.Gson;


@Provider
@Consumes(MediaType.APPLICATION_JSON)
public class ProductReaderProvider implements MessageBodyReader<Product> {

	private Gson gson = new Gson();

	@Override
	public boolean isReadable(Class<?> type, Type genericType,
			Annotation[] annotations, MediaType mediaType) {
		return type == Product.class; 
	}

	@Override
	public Product readFrom(Class<Product> type,
			Type genericType, Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, String> httpHeaders, InputStream entityStream)
			throws IOException, WebApplicationException {

		String json = IOUtils.toString(entityStream);
		return gson.fromJson(json, ProductJson.class).to();
	}

}
