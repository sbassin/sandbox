package org.sbassin.rest.services;

import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;
import org.joda.time.format.ISODateTimeFormat;
import org.sbassin.model.Event;
import org.sbassin.model.Event_;
import org.sbassin.rest.types.StatisticsTO;
import org.sbassin.rest.util.DistanceUtils;
import org.sbassin.rest.util.DistanceUtils.DistanceUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;

@Stateless
@Path("/reports")
@Produces(value = { MediaType.APPLICATION_JSON })
public class ReportingEndpoint {

    private static Logger logger = LoggerFactory.getLogger(ReportingEndpoint.class);

    /**
     * @return The timestamp for the event.
     */
    private static Function<Event, LocalDateTime> eventAt() {
        return new Function<Event, LocalDateTime>() {

            @Override
            public LocalDateTime apply(final Event event) {
                return event.getEventAt();
            }
        };
    }

    /**
     * @param hourOfDay
     *            Valid inputs are 0-23.
     * @return Whether or not the time fell into that hour.
     */
    private static Predicate<LocalDateTime> inHour(final int hourOfDay) {
        return new Predicate<LocalDateTime>() {

            @Override
            public boolean apply(final LocalDateTime dateTime) {
                return dateTime.getHourOfDay() == hourOfDay;
            }
        };
    }

    /**
     * @param dayOfYear
     *            Valid inputs are 0-365.
     * @return Whether or not the date fell on that day.
     */
    private static Predicate<LocalDateTime> onDayOfYear(final int dayOfYear) {
        return new Predicate<LocalDateTime>() {

            @Override
            public boolean apply(final LocalDateTime dateTime) {
                return dateTime.getDayOfYear() == dayOfYear;
            }
        };
    }

    @PersistenceContext(unitName = "EventsPU")
    private EntityManager em;

    @GET
    @Path("/distributionOfCustomerToStoreDistance")
    public Response distributionOfCustomerToStoreDistance() {
        final DistanceUnit distanceUnit = DistanceUnit.Miles;

        final List<Event> events = loadAllEvents(true, true);

        final DescriptiveStatistics stats = new DescriptiveStatistics(events.size());
        for (final Event event : events) {
            final double distance = DistanceUtils.distance(event.getCustomer().getLocation(), event
                    .getStore().getLocation(), distanceUnit);

            if (!Double.isNaN(distance)) {
                logger.info("Distance from Customer ({}) to Store ({}) for Event {} is {} {}.", event
                        .getCustomer().getLocation(), event.getStore().getLocation(), event.getId(),
                        distance, distanceUnit);
                // TODO Why so many NaN values?
                stats.addValue(distance);
            } else {
                logger.warn("Distance from Customer ({}) to Store ({}) for Event {} is {}.", event
                        .getCustomer().getLocation(), event.getStore().getLocation(), event.getId(),
                        distance);
            }
        }
        if(events.size() - stats.getN() > 0)
        logger.warn("Found {} NaN results.", events.size() - stats.getN());

        final StatisticsTO statistics = new StatisticsTO();
        statistics.setMax(stats.getMax());
        statistics.setMin(stats.getMin());
        statistics.setMean(stats.getMean());
        statistics.setStandardDeviation(stats.getStandardDeviation());
        statistics.setVariance(stats.getVariance());
        statistics.setSampleSize(stats.getN());
        statistics.setUnits(distanceUnit.name());

        return Response.ok(new GenericEntity<StatisticsTO>(statistics) {
        }).build();
    }

    @GET
    @Path("/hotShoppingDaysAcrossAllUsers")
    public Response hotShoppingDaysAcrossAllUsers() {
        final List<Event> events = loadAllEvents(false, false);

        final LocalDateTime firstTimeStamp = Iterables.getFirst(events, null).getEventAt();
        final LocalDateTime lastTimeStamp = Iterables.getLast(events).getEventAt();

        logger.info("Events are scattered from {} to {}.", firstTimeStamp, lastTimeStamp);

        final DateTimeFormatter dateFormatter = ISODateTimeFormat.date();
        final Map<String, Integer> eventCountByDay = Maps.newLinkedHashMap();
        for (int i = firstTimeStamp.getDayOfYear(); i < lastTimeStamp.getDayOfYear(); i++) {
            final String dayOfYear = dateFormatter.print(new LocalDate().withDayOfYear(i));
            final int eventCount = Collections2.filter(events, Predicates.compose(onDayOfYear(i), eventAt()))
                    .size();
            eventCountByDay.put(dayOfYear, eventCount);
        }

        return Response.ok(new GenericEntity<Map<String, Integer>>(eventCountByDay) {
        }).build();
    }

    @GET
    @Path("/hotShoppingTimesOfDayAcrossAllUsers")
    public Response hotShoppingTimesOfDayAcrossAllUsers() {
        final List<Event> events = loadAllEvents(false, false);

        final Map<String, Integer> eventCountByHourOfDay = Maps.newLinkedHashMap();

        final DateTimeFormatter hourOfDayFormatter = new DateTimeFormatterBuilder()
                .appendClockhourOfHalfday(1).appendLiteral(' ').appendHalfdayOfDayText().toFormatter();
        for (int i = 0; i < 24; i++) {
            final String hourOfDay = hourOfDayFormatter.print(new LocalTime(i, 0));
            final int eventCount = Collections2.filter(events, Predicates.compose(inHour(i), eventAt()))
                    .size();
            eventCountByHourOfDay.put(hourOfDay, eventCount);
        }

        return Response.ok(new GenericEntity<Map<String, Integer>>(eventCountByHourOfDay) {
        }).build();
    }

    private List<Event> loadAllEvents(final boolean fetchCustomer, final boolean fetchStore) {
        final CriteriaBuilder builder = em.getCriteriaBuilder();
        final CriteriaQuery<Event> query = builder.createQuery(Event.class);
        final Root<Event> event = query.from(Event.class);
        query.select(event);
        if (fetchCustomer) {
            event.fetch(Event_.customer);
        }
        if (fetchStore) {
            event.fetch(Event_.store);
        }
        query.orderBy(builder.asc(event.get("eventAt")));

        final List<Event> events = em.createQuery(query).getResultList();
        return events;
    }
}