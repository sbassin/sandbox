package org.sbassin.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;

import org.sbassin.model.Customer;

/**
 * 
 */
@Stateless
@Path("/customers")
@Produces(value = { MediaType.APPLICATION_JSON })
public class CustomerEndpoint {
    @PersistenceContext(unitName = "EventsPU")
    private EntityManager em;

    @POST
    @Consumes(value = { MediaType.APPLICATION_JSON })
    public Response create(final Customer entity) {
        em.persist(entity);
        return Response.created(
                UriBuilder.fromResource(CustomerEndpoint.class).path(String.valueOf(entity.getId())).build())
                .build();
    }

    @DELETE
    @Path("/{id:[0-9][0-9]*}")
    public Response deleteById(@PathParam("id") final Integer id) {
        final Customer entity = em.find(Customer.class, id);
        if (entity == null) {
            return Response.status(Status.NOT_FOUND).build();
        }
        em.remove(entity);
        return Response.noContent().build();
    }

    @GET
    @Path("/{id:[0-9][0-9]*}")
    public Response findById(@PathParam("id") final Integer id) {
        final TypedQuery<Customer> findByIdQuery = em
                .createQuery(
                        "SELECT DISTINCT c FROM Customer c LEFT JOIN FETCH c.events WHERE c.id = :entityId ORDER BY c.id",
                        Customer.class);
        findByIdQuery.setParameter("entityId", id);
        Customer entity;
        try {
            entity = findByIdQuery.getSingleResult();
        } catch (final NoResultException nre) {
            entity = null;
        }
        if (entity == null) {
            return Response.status(Status.NOT_FOUND).build();
        }
        return Response.ok(entity).build();
    }

    @GET
    public List<Customer> listAll() {
        final List<Customer> results = em.createQuery(
                "SELECT DISTINCT c FROM Customer c LEFT JOIN FETCH c.events ORDER BY c.id", Customer.class)
                .getResultList();
        return results;
    }
}