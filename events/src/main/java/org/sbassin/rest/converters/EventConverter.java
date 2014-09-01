package org.sbassin.rest.converters;

import org.sbassin.model.Event;
import org.sbassin.rest.types.EventTO;

import com.google.common.base.Converter;

public class EventConverter extends Converter<Event, EventTO> {

    @Override
    protected Event doBackward(final EventTO transfer) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected EventTO doForward(final Event event) {
        final EventTO transfer = new EventTO();

        transfer.setId(event.getId());
        transfer.setLocation(new LocationConverter().convert(event.getLocation()));
        transfer.setEventAt(event.getEventAt());
        transfer.setAuditInformation(new AuditInformationConverter().convert(event.getAuditInformation()));
        return transfer;
    }
}
