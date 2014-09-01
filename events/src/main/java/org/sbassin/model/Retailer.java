package org.sbassin.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "retailers")
@XmlRootElement
public class Retailer implements Serializable
{
   private static final long serialVersionUID = 1L;

   @Temporal(TemporalType.TIMESTAMP)
   @Column(name = "created_at", length = 19)
   private Date createdAt;

   @Id
   @GeneratedValue(strategy = IDENTITY)
   @Column(name = "id", unique = true, nullable = false)
   private Integer id;

   @Column(name = "name")
   private String name;

   @OneToMany(fetch = FetchType.LAZY, mappedBy = "retailer")
   private Set<Store> stores = new HashSet<Store>(0);

   @Temporal(TemporalType.TIMESTAMP)
   @Column(name = "updated_at", length = 19)
   private Date updatedAt;

   public Date getCreatedAt()
   {
      return createdAt;
   }

   public Integer getId()
   {
      return id;
   }

   public String getName()
   {
      return name;
   }

   public Set<Store> getStores()
   {
      return stores;
   }

   public Date getUpdatedAt()
   {
      return updatedAt;
   }

   public void setCreatedAt(final Date createdAt)
   {
      this.createdAt = createdAt;
   }

   public void setId(final Integer id)
   {
      this.id = id;
   }

   public void setName(final String name)
   {
      this.name = name;
   }

   public void setStores(final Set<Store> stores)
   {
      this.stores = stores;
   }

   public void setUpdatedAt(final Date updatedAt)
   {
      this.updatedAt = updatedAt;
   }
}
