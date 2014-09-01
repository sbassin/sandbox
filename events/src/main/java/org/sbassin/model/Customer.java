package org.sbassin.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "customers", uniqueConstraints = @UniqueConstraint(columnNames = "customer_id"))
@XmlRootElement
public class Customer implements Serializable {
    private static final long serialVersionUID = 1L;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", length = 19)
    private Date createdAt;

    @Column(name = "customer_id", unique = true)
    private String customerId;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer")
    private Set<Event> events = new HashSet<Event>(0);

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Embedded
    private Location location;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at", length = 19)
    private Date updatedAt;

    @Column(name = "zip")
    private String zip;

    public Date getCreatedAt() {
        return createdAt;
    }

    public String getCustomerId() {
        return customerId;
    }

    public Set<Event> getEvents() {
        return events;
    }

    public Integer getId() {
        return id;
    }

    public Location getLocation() {
        return location;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public String getZip() {
        return zip;
    }

    public void setCreatedAt(final Date createdAt) {
        this.createdAt = createdAt;
    }

    public void setCustomerId(final String customerId) {
        this.customerId = customerId;
    }

    public void setEvents(final Set<Event> events) {
        this.events = events;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public void setLocation(final Location location) {
        this.location = location;
    }

    public void setUpdatedAt(final Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setZip(final String zip) {
        this.zip = zip;
    }

}
