package org.sbassin.rest.converters;

import org.sbassin.model.Retailer;
import org.sbassin.rest.types.RetailerTO;

import com.google.common.base.Converter;

public class RetailerConverter extends Converter<Retailer, RetailerTO> {

	@Override
	protected Retailer doBackward(RetailerTO transfer) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected RetailerTO doForward(Retailer retailer) {
		RetailerTO transfer = new RetailerTO();

		transfer.setId(retailer.getId());
		transfer.setName(retailer.getName());
		transfer.setCreatedAt(retailer.getCreatedAt());
		transfer.setUpdatedAt(retailer.getUpdatedAt());
		return transfer;
	}

}
