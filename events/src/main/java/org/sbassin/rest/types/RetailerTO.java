package org.sbassin.rest.types;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RetailerTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private AuditInformationTO auditInformation;

    private Integer id;

    private String name;

    private Set<StoreTO> stores = new HashSet<StoreTO>(0);

    public AuditInformationTO getAuditInformation() {
        return auditInformation;
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

    public void setAuditInformation(final AuditInformationTO auditInformation) {
        this.auditInformation = auditInformation;
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

}
