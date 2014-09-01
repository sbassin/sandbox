package org.sbassin.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;

import org.sbassin.model.Store;

/**
 * 
 */
@Stateless
@Path("/stores")
@Produces(value = { MediaType.APPLICATION_JSON })
public class StoreEndpoint
{
   @PersistenceContext(unitName = "EventsPU")
   private EntityManager em;

   @POST
   @Consumes(value = { MediaType.APPLICATION_JSON })
   public Response create(Store entity)
   {
      em.persist(entity);
      return Response.created(UriBuilder.fromResource(StoreEndpoint.class).path(String.valueOf(entity.getId())).build()).build();
   }

   @DELETE
   @Path("/{id:[0-9][0-9]*}")
   public Response deleteById(@PathParam("id") Integer id)
   {
      Store entity = em.find(Store.class, id);
      if (entity == null)
      {
         return Response.status(Status.NOT_FOUND).build();
      }
      em.remove(entity);
      return Response.noContent().build();
   }

   @GET
   @Path("/{id:[0-9][0-9]*}")
   public Response findById(@PathParam("id") Integer id)
   {
      TypedQuery<Store> findByIdQuery = em.createQuery("SELECT DISTINCT s FROM Store s LEFT JOIN FETCH s.events LEFT JOIN FETCH s.retailer WHERE s.id = :entityId ORDER BY s.id", Store.class);
      findByIdQuery.setParameter("entityId", id);
      Store entity;
      try
      {
         entity = findByIdQuery.getSingleResult();
      }
      catch (NoResultException nre)
      {
         entity = null;
      }
      if (entity == null)
      {
         return Response.status(Status.NOT_FOUND).build();
      }
      return Response.ok(entity).build();
   }

   @GET
   public List<Store> listAll()
   {
      final List<Store> results = em.createQuery("SELECT DISTINCT s FROM Store s LEFT JOIN FETCH s.events LEFT JOIN FETCH s.retailer ORDER BY s.id", Store.class).getResultList();
      return results;
   }
}