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

    private AuditInformationTO auditInformation;

    private Date createdAt;

    private Set<Event> events = new HashSet<Event>(0);

    private Integer id;

    private LocationTO location;

    private String zip;

    public AuditInformationTO getAuditInformation() {
        return auditInformation;
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

    public LocationTO getLocation() {
        return location;
    }

    public String getZip() {
        return zip;
    }

    public void setAuditInformation(final AuditInformationTO auditInformation) {
        this.auditInformation = auditInformation;
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

    public void setLocation(final LocationTO location) {
        this.location = location;
    }

    public void setZip(final String zip) {
        this.zip = zip;
    }

}
