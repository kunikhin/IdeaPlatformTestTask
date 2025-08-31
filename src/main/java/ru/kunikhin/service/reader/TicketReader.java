
package ru.kunikhin.service.reader;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import ru.kunikhin.model.Ticket;
import ru.kunikhin.model.container.TicketContainer;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
public class TicketReader{

    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<Ticket> read(String filePath) throws IOException {
        return objectMapper.readValue(
                    new File(filePath),
                    TicketContainer.class)
                .getTickets();
    }

    public void printTickets(List<Ticket> tickets) {
        System.out.println("Найдено билетов: " + tickets.size());
        System.out.println("\n" + "-".repeat(50) + "\n");
        for (Ticket ticket : tickets) {
            LocalDateTime departure = ticket.getDeparture();
            LocalDateTime arrival = ticket.getArrival();

            Duration flightDuration = Duration.between(departure, arrival);
            long hours = flightDuration.toHours();
            long minutes = flightDuration.toMinutes() % 60;

            System.out.println("Перевозчик: " + ticket.getCarrier());
            System.out.println("Маршрут: " + ticket.getOriginName() + " → " + ticket.getDestinationName());
            System.out.println("Вылет: " + departure.format(java.time.format.DateTimeFormatter.ofPattern("dd.MM.yy HH:mm")));
            System.out.println("Прилет: " + arrival.format(java.time.format.DateTimeFormatter.ofPattern("dd.MM.yy HH:mm")));
            System.out.println("В пути: " + hours + "ч " + minutes + "м");
            System.out.println("Пересадок: " + ticket.getStops());
            System.out.println("Цена: " + ticket.getPrice() + " руб.");
            System.out.println("\n" + "-".repeat(50) + "\n");
        }
    }
}
