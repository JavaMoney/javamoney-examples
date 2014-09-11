package org.javamoney.examples.jaxrs.resources.store;

import java.util.List;

import javax.money.MonetaryAmount;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.javamoney.examples.jaxrs.ecommerce.Ecommerce;
import org.javamoney.examples.jaxrs.model.Product;

/**
 * Make all operations in a ecommerce to a resource using jax-rs.
 * @author otaviojava
 */
public interface Store extends Ecommerce {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	List<Product> products();
	
	@POST
	@Path("/buy/")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	MonetaryAmount buy(List<Product> products);
	
	@POST
	@Path("/average/")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	MonetaryAmount average(List<Product> products);
	
	@POST
	@Path("/cheaper/")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	MonetaryAmount cheaper(List<Product> products);
	
	@POST
	@Path("/expensive/")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	MonetaryAmount expensive(List<Product> products);
	
	@POST
	@Path("/summary/")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	String summary(List<Product> products);
	
	
}
