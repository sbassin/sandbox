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

import org.sbassin.model.Retailer;
import org.sbassin.rest.converters.RetailerConverter;
import org.sbassin.rest.types.RetailerTO;

import com.google.common.collect.Lists;

/**
 * 
 */
@Stateless
@Path("/retailers")
@Produces(value = { MediaType.APPLICATION_JSON })
public class RetailerEndpoint {
    @PersistenceContext(unitName = "EventsPU")
    private EntityManager em;

    @POST
    @Consumes(value = { MediaType.APPLICATION_JSON })
    public Response create(final Retailer entity) {
        em.persist(entity);
        return Response.created(
                UriBuilder.fromResource(RetailerEndpoint.class).path(String.valueOf(entity.getId())).build())
                .build();
    }

    @DELETE
    @Path("/{id:[0-9][0-9]*}")
    public Response deleteById(@PathParam("id") final Integer id) {
        final Retailer entity = em.find(Retailer.class, id);
        if (entity == null) {
            return Response.status(Status.NOT_FOUND).build();
        }
        em.remove(entity);
        return Response.noContent().build();
    }

    @GET
    @Path("/{id:[0-9][0-9]*}")
    public Response findById(@PathParam("id") final Integer id) {
        final TypedQuery<Retailer> findByIdQuery = em.createQuery(
                "SELECT DISTINCT r FROM Retailer r LEFT JOIN FETCH r.stores WHERE r.id = :entityId",
                Retailer.class);
        findByIdQuery.setParameter("entityId", id);
        Retailer entity;
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
    public List<RetailerTO> listAll() {
        final List<Retailer> results = em.createQuery(
                "SELECT DISTINCT r FROM Retailer r LEFT JOIN FETCH r.stores ORDER BY r.id", Retailer.class)
                .getResultList();
        final List<RetailerTO> translatedResults = Lists.transform(results, new RetailerConverter());
        return translatedResults;
    }
}