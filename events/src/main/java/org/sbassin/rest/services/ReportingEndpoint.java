package org.sbassin.rest.services;

import static com.google.common.base.Predicates.compose;
import static com.google.common.collect.Collections2.filter;
import static com.google.common.collect.Iterables.getFirst;
import static com.google.common.collect.Iterables.getLast;

import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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
import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;
import org.joda.time.format.ISODateTimeFormat;
import org.sbassin.model.Event;
import org.sbassin.model.Event_;
import org.sbassin.rest.types.StatisticsTO;
import org.sbassin.util.DistanceUtils;
import org.sbassin.util.DistanceUtils.DistanceUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Maps;

@Stateless
@Path("/reports")
@Produces(value = { MediaType.APPLICATION_JSON })
public class ReportingEndpoint {

    enum DayOfWeek {
        Friday(DateTimeConstants.FRIDAY),
        Monday(DateTimeConstants.MONDAY),
        Saturday(DateTimeConstants.SATURDAY),
        Sunday(DateTimeConstants.SUNDAY),
        Thursday(DateTimeConstants.THURSDAY),
        Tuesday(DateTimeConstants.TUESDAY),
        Wednesday(DateTimeConstants.WEDNESDAY);

        /** The representation of the day in Joda. */
        private final int jodaConstantValue;

        private DayOfWeek(final int jodaConstantValue) {
            this.jodaConstantValue = jodaConstantValue;
        }

        public int getJodaConstantValue() {
            return jodaConstantValue;
        }
    }

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
     * @param dayOfWeek
     *            Valid inputs are 0-365.
     * @return Whether or not the date fell on that day.
     */
    private static Predicate<LocalDateTime> onDayOfWeek(final int dayOfWeek) {
        return new Predicate<LocalDateTime>() {

            @Override
            public boolean apply(final LocalDateTime dateTime) {
                return dateTime.getDayOfWeek() == dayOfWeek;
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

    /**
     * @return Statistics based on the distances between registered customers and the stores they visit.
     */
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
                /*
                 * TODO Why so many NaN values? Seems like these distances are often on the order of a meter
                 * or two.
                 */
                stats.addValue(distance);
            } else {
                logger.warn("Distance from Customer ({}) to Store ({}) for Event {} is {}.", event
                        .getCustomer().getLocation(), event.getStore().getLocation(), event.getId(), distance);
                stats.addValue(0);
            }
        }
        if (events.size() - stats.getN() > 0) {
            logger.warn("Found {} NaN results.", events.size() - stats.getN());
        }

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

    /**
     * @return The most visited retailers along with the amount of time each retailer has been visited.
     */
    @GET
    @Path("/hotRetailers")
    public Response hotRetailers() {
        final Query query = em
                .createNativeQuery("select r.name, count(1) from events e join stores s on e.store_id = s.id join retailers r on s.retailer_id = r.id group by r.id order by count(1) desc");
        @SuppressWarnings("unchecked")
        final List<Object[]> results = query.getResultList();

        final Map<String, Integer> eventCountByRetailer = Maps.newLinkedHashMap();
        for (final Object[] result : results) {
            final String retailerName = String.valueOf(result[0]);
            final int eventCount = Integer.valueOf(String.valueOf(result[1]));
            eventCountByRetailer.put(retailerName, eventCount);
        }

        return Response.ok(new GenericEntity<Map<String, Integer>>(eventCountByRetailer) {
        }).build();
    }

    /**
     * @return A {@link Map} of dates and the number of {@link Event}s on each date.
     */
    @GET
    @Path("/hotShoppingDatesAcrossAllUsers")
    public Response hotShoppingDatesAcrossAllUsers() {
        final List<Event> events = loadAllEvents(false, false);

        final LocalDateTime firstTimeStamp = getFirst(events, null).getEventAt();
        final LocalDateTime lastTimeStamp = getLast(events).getEventAt();

        logger.info("Events are scattered from {} to {}.", firstTimeStamp, lastTimeStamp);

        /* Note: This will break when crossing year boundaries. */
        final DateTimeFormatter dateFormatter = ISODateTimeFormat.date();
        final Map<String, Integer> eventCountByDate = Maps.newLinkedHashMap();
        for (int i = firstTimeStamp.getDayOfYear(); i < lastTimeStamp.getDayOfYear(); i++) {
            final String date = dateFormatter.print(new LocalDate().withDayOfYear(i));
            final int eventCount = filter(events, compose(onDayOfYear(i), eventAt())).size();
            eventCountByDate.put(date, eventCount);
        }

        return Response.ok(new GenericEntity<Map<String, Integer>>(eventCountByDate) {
        }).build();
    }

    /**
     * @return A {@link Map} of days and the number of {@link Event}s on each day.
     */
    @GET
    @Path("/hotShoppingDaysAcrossAllUsers")
    public Response hotShoppingDaysAcrossAllUsers() {
        final List<Event> events = loadAllEvents(false, false);

        final Map<String, Integer> eventCountByDay = Maps.newLinkedHashMap();
        for (final DayOfWeek dayOfWeek : new DayOfWeek[] { DayOfWeek.Sunday, DayOfWeek.Monday,
                DayOfWeek.Tuesday, DayOfWeek.Wednesday, DayOfWeek.Thursday, DayOfWeek.Friday,
                DayOfWeek.Saturday }) {
            final int eventCount = filter(events,
                    compose(onDayOfWeek(dayOfWeek.getJodaConstantValue()), eventAt())).size();
            eventCountByDay.put(dayOfWeek.name(), eventCount);
        }

        return Response.ok(new GenericEntity<Map<String, Integer>>(eventCountByDay) {
        }).build();
    }

    /**
     * @return A {@link Map} of hours and the number of {@link Event}s in each hour.
     */
    @GET
    @Path("/hotShoppingTimesOfDayAcrossAllUsers")
    public Response hotShoppingTimesOfDayAcrossAllUsers() {
        final List<Event> events = loadAllEvents(false, false);

        final Map<String, Integer> eventCountByHourOfDay = Maps.newLinkedHashMap();

        final DateTimeFormatter hourOfDayFormatter = new DateTimeFormatterBuilder()
                .appendClockhourOfHalfday(1).appendLiteral(' ').appendHalfdayOfDayText().toFormatter();
        for (int i = 0; i < 24; i++) {
            final String hourOfDay = hourOfDayFormatter.print(new LocalTime(i, 0));
            final int eventCount = filter(events, compose(inHour(i), eventAt())).size();
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