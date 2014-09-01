package org.sbassin.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "retailers")
public class Retailer implements Serializable {
    private static final long serialVersionUID = 1L;

    @Embedded
    private AuditInformation auditInformation;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "name")
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "retailer")
    private Set<Store> stores = new HashSet<Store>(0);

    public AuditInformation getAuditInformation() {
        return auditInformation;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<Store> getStores() {
        return stores;
    }

    public void setAuditInformation(final AuditInformation auditInformation) {
        this.auditInformation = auditInformation;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setStores(final Set<Store> stores) {
        this.stores = stores;
    }
}
