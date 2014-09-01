package org.sbassin.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "customers", uniqueConstraints = @UniqueConstraint(columnNames = "customer_id"))
public class Customer implements Serializable {
    private static final long serialVersionUID = 1L;

    @Embedded
    private AuditInformation auditInformation;

    @Id
    @Column(name = "customer_id", unique = true, nullable = false)
    private String customerId;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer")
    private Set<Event> events = new HashSet<Event>(0);

    @Embedded
    private Location location;

    @Column(name = "zip")
    private String zip;

    public AuditInformation getAuditInformation() {
        return auditInformation;
    }

    public String getCustomerId() {
        return customerId;
    }

    public Set<Event> getEvents() {
        return events;
    }

    public Location getLocation() {
        return location;
    }

    public String getZip() {
        return zip;
    }

    public void setAuditInformation(final AuditInformation auditInformation) {
        this.auditInformation = auditInformation;
    }

    public void setCustomerId(final String customerId) {
        this.customerId = customerId;
    }

    public void setEvents(final Set<Event> events) {
        this.events = events;
    }

    public void setLocation(final Location location) {
        this.location = location;
    }

    public void setZip(final String zip) {
        this.zip = zip;
    }

}
