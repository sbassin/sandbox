package org.sbassin.rest.converters;

import org.sbassin.model.Retailer;
import org.sbassin.rest.types.RetailerTO;

import com.google.common.base.Converter;

public class RetailerConverter extends Converter<Retailer, RetailerTO> {

    @Override
    protected Retailer doBackward(final RetailerTO transfer) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected RetailerTO doForward(final Retailer retailer) {
        final RetailerTO transfer = new RetailerTO();

        transfer.setId(retailer.getId());
        transfer.setName(retailer.getName());
        transfer.setAuditInformation(new AuditInformationConverter().convert(retailer.getAuditInformation()));
        return transfer;
    }

}
