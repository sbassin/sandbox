package org.sbassin.rest.types;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class StoreTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String address;

	private String city;

	private Date createdAt;

	private Set<EventTO> events = new HashSet<EventTO>(0);

	private Integer id;

	private Float lat;

	private Float long_;

	private RetailerTO retailer;

	private String state;

	private Date updatedAt;

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

	public Set<EventTO> getEvents() {
		return events;
	}

	public Integer getId() {
		return id;
	}

	public Float getLat() {
		return lat;
	}

	public Float getLong_() {
		return long_;
	}

	public RetailerTO getRetailer() {
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

	public void setEvents(final Set<EventTO> events) {
		this.events = events;
	}

	public void setId(final Integer id) {
		this.id = id;
	}

	public void setLat(final Float lat) {
		this.lat = lat;
	}

	public void setLong_(final Float long_) {
		this.long_ = long_;
	}

	public void setRetailer(final RetailerTO retailer) {
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
