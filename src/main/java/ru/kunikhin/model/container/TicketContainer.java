package ru.kunikhin.model.container;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kunikhin.model.Ticket;

import java.util.List;

@NoArgsConstructor
@Data
public class TicketContainer {
    @JsonProperty("tickets")
    private List<Ticket> tickets;
}
