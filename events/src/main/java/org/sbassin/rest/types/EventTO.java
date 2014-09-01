package org.sbassin.rest.types;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class EventTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Date createdAt;

    private CustomerTO customer;

    private Date eventAt;

    private Integer id;

    private LocationTO location;

    private StoreTO store;

    private Date updatedAt;

    public Date getCreatedAt() {
        return createdAt;
    }

    public CustomerTO getCustomer() {
        return customer;
    }

    public Date getEventAt() {
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

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setCreatedAt(final Date createdAt) {
        this.createdAt = createdAt;
    }

    public void setCustomer(final CustomerTO customer) {
        this.customer = customer;
    }

    public void setEventAt(final Date eventAt) {
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

    public void setUpdatedAt(final Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
