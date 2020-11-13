package com.github.erikpedro.quarkusFood.resource;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import com.github.erikpedro.quarkusFood.model.Prato;
import com.github.erikpedro.quarkusFood.model.Restaurante;

@Path("/restaurantes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestauranteResource {

    @GET
    @Tag(name = "Restaurante")
    public List<Restaurante> buscar() {
        return Restaurante.listAll();
    }
    
    
    @POST
    @Tag(name = "Restaurante")
    @Transactional
    public void adicionar (Restaurante dto) {
    	dto.persist();
    }
    
    
    @PUT
    @Path("{id}")
    @Tag(name = "Restaurante")
    @Transactional
    public void alterar (@PathParam("id") Long id, Restaurante dto) {
    	Optional<Restaurante> restauranteOp = Restaurante.findByIdOptional(id);
    	if(!restauranteOp.isPresent()) {
    		throw new NotFoundException();
    	}
    	Restaurante restaurante = restauranteOp.get();
    	restaurante.nome = dto.nome;
    	restaurante.persist();
    }
    
    
    
    @DELETE
    @Path("{id}")
    @Tag(name = "Restaurante")
    @Transactional
    public void delete(@PathParam("id") Long id) {
        Optional<Restaurante> restauranteOp = Restaurante.findByIdOptional(id);
     	if(!restauranteOp.isPresent()) {
    		throw new NotFoundException();
    	}
     	Restaurante restaurante = restauranteOp.get();
    	restaurante.delete();
    
    }
    
    
    
    
    @GET
    @Path("{idRestaurante}/pratos")
    @Tag(name = "Prato")
    public List<Restaurante> buscaPratos(@PathParam("idRestaurante") Long idRestaurante){
    	Optional<Restaurante> restauranteOp = Restaurante.findByIdOptional(idRestaurante);
    	if(restauranteOp.isPresent()) {
    		throw new NotFoundException("Restaurante nao existe");
    	}
      	
    return Prato.list("restaurante", restauranteOp);
    }
    
    
    @POST
    @Path("{idRestaurante}/pratos")
    @Tag(name = "Prato")
    @Transactional
    public Response adicionarPrato(@PathParam("idRestaurante") Long idRestaurante, Prato dto) {
    	Optional<Restaurante> restauranteOp = Restaurante.findByIdOptional(idRestaurante);
    	if(restauranteOp.isPresent()) {
    		throw new NotFoundException("Restaurante nao existe");
    	}
    	
    	Prato prato = new Prato();
    	prato.nome = dto.nome;
    	prato.descricao = dto.descricao;
    	
    	prato.preco = dto.preco;
    	prato.restaurante = restauranteOp.get();
    	prato.persist();

    	
    	return Response.status(Status.CREATED).build();
    }
    
    
	@PUT
	@Path("{idRestaurante}/pratos/{id}")
	@Tag(name = "Prato")
	@Transactional
	public void atualizar(@PathParam("idRestaurante") Long idRestaurante, @PathParam("id") Long id, Prato dto) {
		Optional<Restaurante> restauranteOp = Restaurante.findByIdOptional(idRestaurante);
		if (restauranteOp.isPresent()) {
			throw new NotFoundException("Restaurante nao existe");
		}

		Optional<Prato> pratoOp = Prato.findByIdOptional(id);
		if (pratoOp.isPresent()) {
			throw new NotFoundException("Restaurante nao existe");

		}

		Prato prato = pratoOp.get();
		prato.preco = dto.preco;
		prato.persist();

	}
    
    
    
	@DELETE
	@Path("{idRestaurante}/pratos/{id}")
	@Tag(name = "Prato")
	@Transactional
	public void delete(@PathParam("idRestaurante") Long idRestaurante, @PathParam("id") Long id, Prato dto) {
		Optional<Restaurante> restauranteOp = Restaurante.findByIdOptional(idRestaurante);
		if (restauranteOp.isPresent()) {
			throw new NotFoundException("Restaurante nao existe");
		}

		Optional<Prato> pratoOp = Prato.findByIdOptional(id);
		Prato prato = pratoOp.get();
    	prato.delete();

	}
    
    
}