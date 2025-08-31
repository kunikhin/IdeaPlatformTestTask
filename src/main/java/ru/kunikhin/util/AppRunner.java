package ru.kunikhin.util;

import lombok.RequiredArgsConstructor;
import ru.kunikhin.model.Ticket;
import ru.kunikhin.service.processor.TicketProcessor;
import ru.kunikhin.service.reader.TicketReader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class AppRunner {
    private final TicketReader ticketReader = new TicketReader();
    private TicketProcessor ticketProcessor = null;
    private final StringBuilder sb = new StringBuilder();
    private final String origin = "VVO";
    private final String destination = "TLV";

    public void run() {
        try {
            List<Ticket> tickets = ticketReader.read("tickets.json");
            ticketProcessor = new TicketProcessor(tickets);
        } catch (IOException e) {
            System.err.println("Ошибка при чтении JSON файла: " + e.getMessage());
        }

        Map<String, Duration> minFlightTimes = ticketProcessor.getMinimalFlightTimeByCarrier(origin, destination);
        sb.append("Минимальное время полета для каждого перевозчика:\n\n");
        for (Map.Entry<String, Duration> entry : minFlightTimes.entrySet()) {
            sb.append(entry.getKey()).append(": ").append(TicketProcessor.formatDuration(entry.getValue())).append("\n");
        }

        sb.append("\n").append("=".repeat(60)).append("\n\n");

        double priceDifference = ticketProcessor.getPriceDifferenceBetweenAverageAndMedian(origin, destination);
        sb.append("Разница между средней ценой и медианой: ").append(String.format("%.2f", priceDifference)).append(" руб.\n");

        try {
            Files.writeString(Paths.get("output.txt"), sb);
            System.out.println(sb);
        } catch (IOException e) {
            System.err.println("Ошибка записи: " + e.getMessage());
        }

    }
}
