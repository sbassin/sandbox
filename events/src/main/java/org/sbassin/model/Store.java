package org.sbassin.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
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

@Entity
@Table(name = "stores")
public class Store implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "address")
    private String address;

    @Embedded
    private AuditInformation auditInformation;

    @Column(name = "city")
    private String city;

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

    @Column(name = "zip")
    private String zip;

    public String getAddress() {
        return address;
    }

    public AuditInformation getAuditInformation() {
        return auditInformation;
    }

    public String getCity() {
        return city;
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

    public String getZip() {
        return zip;
    }

    public void setAddress(final String address) {
        this.address = address;
    }

    public void setAuditInformation(final AuditInformation auditInformation) {
        this.auditInformation = auditInformation;
    }

    public void setCity(final String city) {
        this.city = city;
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

    public void setZip(final String zip) {
        this.zip = zip;
    }
}
