package org.sbassin.rest.types;

import java.io.Serializable;
import java.util.Date;

public class AuditInformationTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Date createdAt;

    private Date updatedAt;

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setCreatedAt(final Date createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(final Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
