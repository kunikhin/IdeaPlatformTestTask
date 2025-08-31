package ru.kunikhin.service.processor;

import lombok.RequiredArgsConstructor;
import ru.kunikhin.model.Ticket;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class TicketProcessor {
    private final List<Ticket> tickets;

    public Map<String, Duration> getMinimalFlightTimeByCarrier(String origin, String destination) {
        List<Ticket> filteredTicketsByRoute = filterTicketsByRoute(origin, destination);

        Map<String, Duration> result = new HashMap<>();

        for (Ticket ticket : filteredTicketsByRoute) {
            Duration flightDuration = Duration.between(ticket.getDeparture(), ticket.getArrival());
            String carrier = ticket.getCarrier();

            if (!result.containsKey(carrier) || flightDuration.compareTo(result.get(carrier)) < 0) {
                result.put(carrier, flightDuration);
            }
        }
        return result;
    }

    public double getPriceDifferenceBetweenAverageAndMedian(String origin, String destination) {
        List<Integer> prices = filterTicketsByRoute(origin, destination).stream()
                .map(Ticket::getPrice)
                .collect(Collectors.toList());

        double average = prices.stream()
                .mapToInt(Integer::intValue)
                .average()
                .orElse(0.0);

        double median = calculateMedian(prices);

        return average - median;
    }

    private List<Ticket> filterTicketsByRoute(String origin, String destination) {
        return tickets.stream()
                .filter(ticket -> origin.equals(ticket.getOrigin()) && destination.equals(ticket.getDestination()))
                .collect(Collectors.toList());
    }

    private double calculateMedian(List<Integer> values) {
        List<Integer> sortedValues = new ArrayList<>(values);
        Collections.sort(sortedValues);
        int size = sortedValues.size();

        if (size % 2 == 0) {
            return (sortedValues.get(size / 2 - 1) + sortedValues.get(size / 2)) / 2.0;
        } else {
            return sortedValues.get(size / 2);
        }
    }

    public static String formatDuration(Duration duration) {
        return duration.toHours() + "ч " + duration.toMinutes() % 60 + "м";
    }
}
