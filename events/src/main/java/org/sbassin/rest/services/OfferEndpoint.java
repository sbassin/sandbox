package org.sbassin.rest.services;

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

import org.sbassin.model.Offer;

/**
 * 
 */
@Stateless
@Path("/offers")
@Produces(value = { MediaType.APPLICATION_JSON })
public class OfferEndpoint {
    @PersistenceContext(unitName = "EventsPU")
    private EntityManager em;

    @POST
    @Consumes(value = { MediaType.APPLICATION_JSON })
    public Response create(final Offer entity) {
        em.persist(entity);
        return Response.created(
                UriBuilder.fromResource(OfferEndpoint.class).path(String.valueOf(entity.getId())).build())
                .build();
    }

    @DELETE
    @Path("/{id:[0-9][0-9]*}")
    public Response deleteById(@PathParam("id") final Integer id) {
        final Offer entity = em.find(Offer.class, id);
        if (entity == null) {
            return Response.status(Status.NOT_FOUND).build();
        }
        em.remove(entity);
        return Response.noContent().build();
    }

    @GET
    @Path("/{id:[0-9][0-9]*}")
    public Response findById(@PathParam("id") final Integer id) {
        final TypedQuery<Offer> findByIdQuery = em
                .createQuery(
                        "SELECT DISTINCT o FROM Offer o LEFT JOIN FETCH o.tasks WHERE o.id = :entityId ORDER BY o.id",
                        Offer.class);
        findByIdQuery.setParameter("entityId", id);
        Offer entity;
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
    public List<Offer> listAll() {
        final List<Offer> results = em.createQuery(
                "SELECT DISTINCT o FROM Offer o LEFT JOIN FETCH o.tasks ORDER BY o.id", Offer.class)
                .getResultList();
        return results;
    }
}