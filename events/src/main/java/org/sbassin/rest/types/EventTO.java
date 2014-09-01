package org.sbassin.rest.types;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import org.joda.time.LocalDateTime;

@XmlRootElement
public class EventTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private AuditInformationTO auditInformation;

    private CustomerTO customer;

    private LocalDateTime eventAt;

    private Integer id;

    private LocationTO location;

    private StoreTO store;

    public AuditInformationTO getAuditInformation() {
        return auditInformation;
    }

    public CustomerTO getCustomer() {
        return customer;
    }

    public LocalDateTime getEventAt() {
        return eventAt;
    }

    public Integer getId() {
        return id;
    }

    public LocationTO getLocation() {
        return location;
    }

    public StoreTO getStore() {
        return store;
    }

    public void setAuditInformation(final AuditInformationTO auditInformation) {
        this.auditInformation = auditInformation;
    }

    public void setCustomer(final CustomerTO customer) {
        this.customer = customer;
    }

    public void setEventAt(final LocalDateTime eventAt) {
        this.eventAt = eventAt;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public void setLocation(final LocationTO location) {
        this.location = location;
    }

    public void setStore(final StoreTO store) {
        this.store = store;
    }
}
