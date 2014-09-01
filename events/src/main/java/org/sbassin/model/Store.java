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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "stores")
@XmlRootElement
public class Store implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "address")
    private String address;

    @Column(name = "city")
    private String city;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", length = 19)
    private Date createdAt;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "store")
    private Set<Event> events = new HashSet<Event>(0);

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Embedded
    private Location location;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "retailer_id")
    private Retailer retailer;

    @Column(name = "state")
    private String state;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at", length = 19)
    private Date updatedAt;

    @Column(name = "zip")
    private String zip;

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public Date getCreatedAt() {
        return createdAt;
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

    public Retailer getRetailer() {
        return retailer;
    }

    public String getState() {
        return state;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public String getZip() {
        return zip;
    }

    public void setAddress(final String address) {
        this.address = address;
    }

    public void setCity(final String city) {
        this.city = city;
    }

    public void setCreatedAt(final Date createdAt) {
        this.createdAt = createdAt;
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

    public void setRetailer(final Retailer retailer) {
        this.retailer = retailer;
    }

    public void setState(final String state) {
        this.state = state;
    }

    public void setUpdatedAt(final Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setZip(final String zip) {
        this.zip = zip;
    }
}
