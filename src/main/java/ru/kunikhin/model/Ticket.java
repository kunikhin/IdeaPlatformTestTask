package ru.kunikhin.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


@NoArgsConstructor
@Data
public class Ticket {
    @JsonProperty("origin")
    private String origin;

    @JsonProperty("origin_name")
    private String originName;

    @JsonProperty("destination")
    private String destination;

    @JsonProperty("destination_name")
    private String destinationName;

    @JsonProperty("departure_date")
    private String departureDate;

    @JsonProperty("departure_time")
    private String departureTime;

    @JsonProperty("arrival_date")
    private String arrivalDate;

    @JsonProperty("arrival_time")
    private String arrivalTime;

    @JsonProperty("carrier")
    private String carrier;

    @JsonProperty("stops")
    private byte stops;

    @JsonProperty("price")
    private int price;

    public LocalDateTime getDeparture() {
        return LocalDateTime.of(
                LocalDate.parse(departureDate, DateTimeFormatter.ofPattern("dd.MM.yy")),
                LocalTime.parse(departureTime, DateTimeFormatter.ofPattern("H:mm"))
        );
    }

    public LocalDateTime getArrival() {
        return LocalDateTime.of(
                LocalDate.parse(arrivalDate, DateTimeFormatter.ofPattern("dd.MM.yy")),
                LocalTime.parse(arrivalTime, DateTimeFormatter.ofPattern("H:mm"))
        );
    }
}