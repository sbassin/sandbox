package org.sbassin.rest.converters;

import org.sbassin.model.AuditInformation;
import org.sbassin.rest.types.AuditInformationTO;

import com.google.common.base.Converter;

public class AuditInformationConverter extends Converter<AuditInformation, AuditInformationTO> {

    @Override
    protected AuditInformation doBackward(final AuditInformationTO transfer) {
        final AuditInformation auditInformation = new AuditInformation();

        auditInformation.setCreatedAt(transfer.getCreatedAt());
        auditInformation.setUpdatedAt(transfer.getUpdatedAt());
        return auditInformation;
    }

    @Override
    protected AuditInformationTO doForward(final AuditInformation auditInformation) {
        final AuditInformationTO transfer = new AuditInformationTO();

        transfer.setCreatedAt(auditInformation.getCreatedAt());
        transfer.setUpdatedAt(auditInformation.getUpdatedAt());
        return transfer;
    }

}
