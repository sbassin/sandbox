package org.sbassin.rest.types;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class StoreTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String address;

    private AuditInformationTO auditInformation;

    private String city;

    private Set<EventTO> events = new HashSet<EventTO>(0);

    private Integer id;

    private LocationTO location;

    private RetailerTO retailer;

    private String state;

    private String zip;

    public String getAddress() {
        return address;
    }

    public AuditInformationTO getAuditInformation() {
        return auditInformation;
    }

    public String getCity() {
        return city;
    }

    public Set<EventTO> getEvents() {
        return events;
    }

    public Integer getId() {
        return id;
    }

    public LocationTO getLocation() {
        return location;
    }

    public RetailerTO getRetailer() {
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

    public void setAuditInformation(final AuditInformationTO auditInformation) {
        this.auditInformation = auditInformation;
    }

    public void setCity(final String city) {
        this.city = city;
    }

    public void setEvents(final Set<EventTO> events) {
        this.events = events;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public void setLocation(final LocationTO location) {
        this.location = location;
    }

    public void setRetailer(final RetailerTO retailer) {
        this.retailer = retailer;
    }

    public void setState(final String state) {
        this.state = state;
    }

    public void setZip(final String zip) {
        this.zip = zip;
    }
}
