package org.sbassin.rest.types;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RetailerTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Date createdAt;

    private Integer id;

    private String name;

    private Set<StoreTO> stores = new HashSet<StoreTO>(0);

    private Date updatedAt;

    public Date getCreatedAt() {
        return createdAt;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<StoreTO> getStores() {
        return stores;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setCreatedAt(final Date createdAt) {
        this.createdAt = createdAt;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setStores(final Set<StoreTO> stores) {
        this.stores = stores;
    }

    public void setUpdatedAt(final Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
