package org.javamoney.examples.jaxrs.infrastructure.store;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.stream.Collectors;

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
public class ProductCollectionWriterProvider implements MessageBodyWriter<Collection<Product>> {

	private Gson gson = new Gson();

	@Override
	public boolean isWriteable(Class<?> type, Type genericType,
			Annotation[] annotations, MediaType mediaType) {
		ParameterizedType parametrized = (ParameterizedType)genericType;
		return Collection.class.isAssignableFrom(type) && parametrized.getActualTypeArguments()[0] == Product.class; 
	}

	@Override
	public long getSize(Collection<Product> product, Class<?> type, Type genericType,
			Annotation[] annotations, MediaType mediaType) {
		return 0;
	}

	@Override
	public void writeTo(Collection<Product> products, Class<?> type,
			Type genericType, Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, Object> httpHeaders,
			OutputStream entityStream) throws IOException,
			WebApplicationException {

		entityStream.write(gson.toJson(
				products.stream().map(ProductJson::new)
						.collect(Collectors.toList())).getBytes());
	}
	

}
