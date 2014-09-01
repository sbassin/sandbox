package org.sbassin.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "events")
@XmlRootElement
public class Event implements Serializable
{
   private static final long serialVersionUID = 1L;

   @Temporal(TemporalType.TIMESTAMP)
   @Column(name = "created_at", length = 19)
   private Date createdAt;

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "customer_id", columnDefinition="varchar(255)")
   private Customer customer;

   @Temporal(TemporalType.TIMESTAMP)
   @Column(name = "event_at", length = 19)
   private Date eventAt;

   @Id
   @GeneratedValue(strategy = IDENTITY)
   @Column(name = "id", unique = true, nullable = false)
   private Integer id;

   @Column(name = "lat", precision = 12, scale = 0)
   private Float lat;

   @Column(name = "long", precision = 12, scale = 0)
   private Float long_;

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "store_id")
   private Store store;

   @Temporal(TemporalType.TIMESTAMP)
   @Column(name = "updated_at", length = 19)
   private Date updatedAt;

   public Date getCreatedAt()
   {
      return createdAt;
   }

   public Customer getCustomer()
   {
      return customer;
   }

   public Date getEventAt()
   {
      return eventAt;
   }

   public Integer getId()
   {
      return id;
   }

   public Float getLat()
   {
      return lat;
   }

   public Float getLong_()
   {
      return long_;
   }

   public Store getStore()
   {
      return store;
   }

   public Date getUpdatedAt()
   {
      return updatedAt;
   }

   public void setCreatedAt(final Date createdAt)
   {
      this.createdAt = createdAt;
   }

   public void setCustomer(final Customer customer)
   {
      this.customer = customer;
   }

   public void setEventAt(final Date eventAt)
   {
      this.eventAt = eventAt;
   }

   public void setId(final Integer id)
   {
      this.id = id;
   }

   public void setLat(final Float lat)
   {
      this.lat = lat;
   }

   public void setLong_(final Float long_)
   {
      this.long_ = long_;
   }

   public void setStore(final Store store)
   {
      this.store = store;
   }

   public void setUpdatedAt(final Date updatedAt)
   {
      this.updatedAt = updatedAt;
   }
}
