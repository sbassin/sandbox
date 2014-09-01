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
@Table(name = "offers")
@XmlRootElement
public class Offer implements Serializable
{

   private static final long serialVersionUID = 1L;

   @Temporal(TemporalType.TIMESTAMP)
   @Column(name = "created_at", length = 19)
   private Date createdAt;

   @Column(name = "description", columnDefinition="text")
   private String description;

   @Temporal(TemporalType.TIMESTAMP)
   @Column(name = "expiration", length = 19)
   private Date expiration;

   @Id
   @GeneratedValue(strategy = IDENTITY)
   @Column(name = "id", unique = true, nullable = false)
   private Integer id;

   @Column(name = "image_url")
   private String imageUrl;

   @Column(name = "name")
   private String name;

   @OneToMany(fetch = FetchType.LAZY, mappedBy = "offer")
   private Set<Task> tasks = new HashSet<Task>(0);

   @Column(name = "terms", columnDefinition="text")
   private String terms;

   @Temporal(TemporalType.TIMESTAMP)
   @Column(name = "updated_at", length = 19)
   private Date updatedAt;

   public Date getCreatedAt()
   {
      return createdAt;
   }

   public String getDescription()
   {
      return description;
   }

   public Date getExpiration()
   {
      return expiration;
   }

   public Integer getId()
   {
      return id;
   }

   public String getImageUrl()
   {
      return imageUrl;
   }

   public String getName()
   {
      return name;
   }

   public Set<Task> getTasks()
   {
      return tasks;
   }

   public String getTerms()
   {
      return terms;
   }

   public Date getUpdatedAt()
   {
      return updatedAt;
   }

   public void setCreatedAt(final Date createdAt)
   {
      this.createdAt = createdAt;
   }

   public void setDescription(final String description)
   {
      this.description = description;
   }

   public void setExpiration(final Date expiration)
   {
      this.expiration = expiration;
   }

   public void setId(final Integer id)
   {
      this.id = id;
   }

   public void setImageUrl(final String imageUrl)
   {
      this.imageUrl = imageUrl;
   }

   public void setName(final String name)
   {
      this.name = name;
   }

   public void setTasks(final Set<Task> tasks)
   {
      this.tasks = tasks;
   }

   public void setTerms(final String terms)
   {
      this.terms = terms;
   }

   public void setUpdatedAt(final Date updatedAt)
   {
      this.updatedAt = updatedAt;
   }
}
