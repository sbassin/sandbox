package org.sbassin.rest.types;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import org.sbassin.model.Event;

@XmlRootElement
public class CustomerTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Date createdAt;

    private String customerId;

    private Set<Event> events = new HashSet<Event>(0);

    private Integer id;

    private LocationTO location;

    private Date updatedAt;

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

    public LocationTO getLocation() {
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

    public void setLocation(final LocationTO location) {
        this.location = location;
    }

    public void setUpdatedAt(final Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setZip(final String zip) {
        this.zip = zip;
    }

}
