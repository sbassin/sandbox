package org.sbassin.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

@Entity
@Table(name = "events")
public class Event implements Serializable {
    private static final long serialVersionUID = 1L;

    @Embedded
    private AuditInformation auditInformation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", columnDefinition = "varchar(255)")
    private Customer customer;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
    @Column(name = "event_at", length = 19, columnDefinition = "datetime")
    private LocalDateTime eventAt;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Embedded
    private Location location;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    public AuditInformation getAuditInformation() {
        return auditInformation;
    }

    public Customer getCustomer() {
        return customer;
    }

    public LocalDateTime getEventAt() {
        return eventAt;
    }

    public Integer getId() {
        return id;
    }

    public Location getLocation() {
        return location;
    }

    public Store getStore() {
        return store;
    }

    public void setAuditInformation(final AuditInformation auditInformation) {
        this.auditInformation = auditInformation;
    }

    public void setCustomer(final Customer customer) {
        this.customer = customer;
    }

    public void setEventAt(final LocalDateTime eventAt) {
        this.eventAt = eventAt;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public void setLocation(final Location location) {
        this.location = location;
    }

    public void setStore(final Store store) {
        this.store = store;
    }
}
