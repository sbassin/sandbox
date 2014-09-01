package org.sbassin.rest.types;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class OfferTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Date createdAt;

    private String description;

    private Date expiration;

    private Integer id;

    private String imageUrl;

    private String name;

    private Set<TaskTO> tasks = new HashSet<TaskTO>(0);

    private String terms;

    private Date updatedAt;

    public Date getCreatedAt() {
        return createdAt;
    }

    public String getDescription() {
        return description;
    }

    public Date getExpiration() {
        return expiration;
    }

    public Integer getId() {
        return id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getName() {
        return name;
    }

    public Set<TaskTO> getTasks() {
        return tasks;
    }

    public String getTerms() {
        return terms;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setCreatedAt(final Date createdAt) {
        this.createdAt = createdAt;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public void setExpiration(final Date expiration) {
        this.expiration = expiration;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public void setImageUrl(final String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setTasks(final Set<TaskTO> tasks) {
        this.tasks = tasks;
    }

    public void setTerms(final String terms) {
        this.terms = terms;
    }

    public void setUpdatedAt(final Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
