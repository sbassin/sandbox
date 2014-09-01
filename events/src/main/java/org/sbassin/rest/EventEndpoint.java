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

import org.sbassin.model.Event;

/**
 * 
 */
@Stateless
@Path("/events")
@Produces(value = { MediaType.APPLICATION_JSON })
public class EventEndpoint {
    @PersistenceContext(unitName = "EventsPU")
    private EntityManager em;

    @POST
    @Consumes(value = { MediaType.APPLICATION_JSON })
    public Response create(final Event entity) {
        em.persist(entity);
        return Response.created(
                UriBuilder.fromResource(EventEndpoint.class).path(String.valueOf(entity.getId())).build())
                .build();
    }

    @DELETE
    @Path("/{id:[0-9][0-9]*}")
    public Response deleteById(@PathParam("id") final Integer id) {
        final Event entity = em.find(Event.class, id);
        if (entity == null) {
            return Response.status(Status.NOT_FOUND).build();
        }
        em.remove(entity);
        return Response.noContent().build();
    }

    @GET
    @Path("/{id:[0-9][0-9]*}")
    public Response findById(@PathParam("id") final Integer id) {
        final TypedQuery<Event> findByIdQuery = em
                .createQuery(
                        "SELECT DISTINCT e FROM Event e LEFT JOIN FETCH e.customer LEFT JOIN FETCH e.store WHERE e.id = :entityId ORDER BY e.id",
                        Event.class);
        findByIdQuery.setParameter("entityId", id);
        Event entity;
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
    public List<Event> listAll() {
        final List<Event> results = em
                .createQuery(
                        "SELECT DISTINCT e FROM Event e LEFT JOIN FETCH e.customer LEFT JOIN FETCH e.store ORDER BY e.id",
                        Event.class).getResultList();
        return results;
    }
}