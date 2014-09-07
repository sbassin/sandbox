package org.sbassin.rest.services;

import static com.google.common.base.Predicates.compose;
import static com.google.common.collect.Collections2.filter;
import static com.google.common.collect.Iterables.getFirst;
import static com.google.common.collect.Iterables.getLast;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
import org.sbassin.rest.types.ListTO;
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
        Friday(DateTimeConstants.FRIDAY, 5),
        Monday(DateTimeConstants.MONDAY, 1),
        Saturday(DateTimeConstants.SATURDAY, 6),
        Sunday(DateTimeConstants.SUNDAY, 0),
        Thursday(DateTimeConstants.THURSDAY, 4),
        Tuesday(DateTimeConstants.TUESDAY, 2),
        Wednesday(DateTimeConstants.WEDNESDAY, 3);

        /** Help with sorting. */
        private final int dayNum;

        /** The representation of the day in Joda. */
        private final int jodaConstantValue;

        private DayOfWeek(final int jodaConstantValue, final int dayNum) {
            this.jodaConstantValue = jodaConstantValue;
            this.dayNum = dayNum;
        }

        public int getDayNum() {
            return dayNum;
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
                logger.trace("Distance from Customer ({}) to Store ({}) for Event {} is {} {}.", event
                        .getCustomer().getLocation(), event.getStore().getLocation(), event.getId(),
                        distance, distanceUnit);
                /*
                 * TODO Why so many NaN values? Seems like these distances are often on the order of a meter
                 * or two.
                 */
                stats.addValue(distance);
            } else {
                logger.trace("Distance from Customer ({}) to Store ({}) for Event {} is {}.", event
                        .getCustomer().getLocation(), event.getStore().getLocation(), event.getId(), distance);
                stats.addValue(0);
            }
        }
        if (events.size() - stats.getN() > 0) {
            logger.warn("Found {} NaN results.", events.size() - stats.getN());
        }

        final ListTO list = new ListTO();
        final Map<String, Object> mean = Maps.newLinkedHashMap();
        mean.put("defaultordering", 0);
        mean.put("statistic", "Mean");
        mean.put("value", stats.getMean() + " " + distanceUnit.name());
        list.getData().add(mean);

        final Map<String, Object> max = Maps.newLinkedHashMap();
        max.put("defaultordering", 1);
        max.put("statistic", "Maximum");
        max.put("value", stats.getMax() + " " + distanceUnit.name());
        list.getData().add(max);

        final Map<String, Object> min = Maps.newLinkedHashMap();
        min.put("defaultordering", 2);
        min.put("statistic", "Minimum");
        min.put("value", stats.getMin() + " " + distanceUnit.name());
        list.getData().add(min);

        final Map<String, Object> standardDeviation = Maps.newLinkedHashMap();
        standardDeviation.put("defaultordering", 3);
        standardDeviation.put("statistic", "Standard Deviation");
        standardDeviation.put("value", stats.getStandardDeviation() + " " + distanceUnit.name());
        list.getData().add(standardDeviation);

        final Map<String, Object> variance = Maps.newLinkedHashMap();
        variance.put("defaultordering", 4);
        variance.put("statistic", "Variance");
        variance.put("value", stats.getVariance());
        list.getData().add(variance);

        final Map<String, Object> sampleSize = Maps.newLinkedHashMap();
        sampleSize.put("defaultordering", 5);
        sampleSize.put("statistic", "Sample Size");
        sampleSize.put("value", stats.getN());
        list.getData().add(sampleSize);

        return Response.ok(new GenericEntity<ListTO>(list) {
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
        
        final ListTO list = new ListTO();
        for (final Entry<String, Integer> entry : eventCountByRetailer.entrySet()) {
            final Map<String, Object> entryMap = Maps.newLinkedHashMap();
            entryMap.put("retailer", entry.getKey());
            entryMap.put("events", entry.getValue());
            list.getData().add(entryMap);
        }

        return Response.ok(new GenericEntity<ListTO>(list) {
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
        final Map<LocalDate, Integer> eventCountByDate = Maps.newLinkedHashMap();
        for (int i = firstTimeStamp.getDayOfYear(); i < lastTimeStamp.getDayOfYear(); i++) {
            final LocalDate date = new LocalDate().withDayOfYear(i);
            final int eventCount = filter(events, compose(onDayOfYear(i), eventAt())).size();
            eventCountByDate.put(date, eventCount);
        }

        final DateTimeFormatter dateFormatter = ISODateTimeFormat.date();
        final ListTO list = new ListTO();
        for (final Entry<LocalDate, Integer> entry : eventCountByDate.entrySet()) {
            final Map<String, Object> entryMap = Maps.newLinkedHashMap();
            entryMap.put("defaultordering", entry.getKey().toDate().getTime());
            entryMap.put("date", dateFormatter.print(entry.getKey()));
            entryMap.put("events", entry.getValue());
            list.getData().add(entryMap);
        }

        return Response.ok(new GenericEntity<ListTO>(list) {
        }).build();
    }

    /**
     * @return A {@link Map} of days and the number of {@link Event}s on each day.
     */
    @GET
    @Path("/hotShoppingDaysAcrossAllUsers")
    public Response hotShoppingDaysAcrossAllUsers() {
        final List<Event> events = loadAllEvents(false, false);

        final Map<DayOfWeek, Integer> eventCountByDay = Maps.newLinkedHashMap();
        for (final DayOfWeek dayOfWeek : new DayOfWeek[] { DayOfWeek.Sunday, DayOfWeek.Monday,
                DayOfWeek.Tuesday, DayOfWeek.Wednesday, DayOfWeek.Thursday, DayOfWeek.Friday,
                DayOfWeek.Saturday }) {
            final int eventCount = filter(events,
                    compose(onDayOfWeek(dayOfWeek.getJodaConstantValue()), eventAt())).size();
            eventCountByDay.put(dayOfWeek, eventCount);
        }

        final ListTO list = new ListTO();
        for (final Entry<DayOfWeek, Integer> entry : eventCountByDay.entrySet()) {
            final Map<String, Object> entryMap = Maps.newLinkedHashMap();
            entryMap.put("defaultordering", entry.getKey().getDayNum());
            entryMap.put("day", entry.getKey().name());
            entryMap.put("events", entry.getValue());
            list.getData().add(entryMap);
        }

        return Response.ok(new GenericEntity<ListTO>(list) {
        }).build();
    }

    /**
     * @return A {@link Map} of hours and the number of {@link Event}s in each hour.
     */
    @GET
    @Path("/hotShoppingTimesOfDayAcrossAllUsers")
    public Response hotShoppingTimesOfDayAcrossAllUsers() {
        final List<Event> events = loadAllEvents(false, false);

        
        final Map<LocalTime, Integer> eventCountByHourOfDay = Maps.newLinkedHashMap();
        for (int i = 0; i < 24; i++) {
            final LocalTime hourOfDay = new LocalTime(i, 0);
            final int eventCount = filter(events, compose(inHour(i), eventAt())).size();
            eventCountByHourOfDay.put(hourOfDay, eventCount);
        }

        final DateTimeFormatter hourOfDayFormatter = new DateTimeFormatterBuilder()
        .appendClockhourOfHalfday(1).appendLiteral(' ').appendHalfdayOfDayText().toFormatter();
        final ListTO list = new ListTO();
        for (final Entry<LocalTime, Integer> entry : eventCountByHourOfDay.entrySet()) {
            final Map<String, Object> entryMap = Maps.newLinkedHashMap();
            entryMap.put("defaultordering", entry.getKey().getHourOfDay());
            entryMap.put("time", hourOfDayFormatter.print(entry.getKey()));
            entryMap.put("events", entry.getValue());
            list.getData().add(entryMap);
        }

        return Response.ok(new GenericEntity<ListTO>(list) {
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