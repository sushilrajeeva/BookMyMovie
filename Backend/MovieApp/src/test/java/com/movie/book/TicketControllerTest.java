package com.movie.book;
import com.movie.book.controller.TicketController;
import com.movie.book.model.Movie;
import com.movie.book.model.Ticket;
import com.movie.book.service.MovieService;
import com.movie.book.service.TicketService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class TicketControllerTest {

    @InjectMocks
    private TicketController ticketController;

    @Mock
    private TicketService ticketService;

    @Mock
    private MovieService movieService;

    @Test
    public void testAddTicket_Success() {
        Movie movie = new Movie();
        movie.setMovieId(1);
        movie.setSeatsAvailable(10);
        movie.setBookedSeats(0);

        when(movieService.getMovieById(1)).thenReturn(movie);
        when(movieService.updateMovie(any(Movie.class))).thenReturn(true);
        when(ticketService.addTicket(any(Ticket.class))).thenReturn(true);

        ResponseEntity<?> response = ticketController.addTicket(1, 5);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(movieService).getMovieById(1);
        verify(movieService).updateMovie(any(Movie.class));
        verify(ticketService).addTicket(any(Ticket.class));
    }

    @Test
    public void testAddTicket_Failure() {
        when(movieService.getMovieById(1)).thenReturn(null);

        ResponseEntity<?> response = ticketController.addTicket(1, 5);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        verify(movieService).getMovieById(1);
    }

    @Test
    public void testGetAllTickets_Success() {
        Ticket ticket = new Ticket();
        ticket.setSeatsBooked(5);
        ticket.setSeatsAvailable(95);
        List<Ticket> tickets = Arrays.asList(ticket);

        when(ticketService.getAllTickets()).thenReturn(tickets);

        ResponseEntity<?> response = ticketController.getAllTickets();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(ticketService).getAllTickets();
    }

    @Test
    public void testGetAllTickets_Failure() {
        when(ticketService.getAllTickets()).thenReturn(Collections.emptyList());

        ResponseEntity<?> response = ticketController.getAllTickets();

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(ticketService).getAllTickets();
    }
}

