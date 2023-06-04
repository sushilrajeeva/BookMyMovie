package com.movie.book;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

import com.movie.book.model.Ticket;
import com.movie.book.repository.TicketRepo;
import com.movie.book.service.TicketServiceImpl;

public class TicketServiceImplTest {

    @InjectMocks
    private TicketServiceImpl ticketService;

    @Mock
    private TicketRepo ticketRepo;

    private Ticket testTicket;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        testTicket = new Ticket();
        testTicket.setTransactionId(1);
        testTicket.setMovieName("Pathan");
        testTicket.setMovie_id_fk(1);
        testTicket.setSeatsAvailable(80);
        testTicket.setSeatsBooked(20);
        testTicket.setTotalSeat(100);
    }

    @Test
    public void testGetAllTicketsSuccess() {
        when(ticketRepo.findAll()).thenReturn(Arrays.asList(testTicket, testTicket));

        List<Ticket> tickets = ticketService.getAllTickets();
        assertEquals(2, tickets.size());
        verify(ticketRepo, times(1)).findAll();
    }

    @Test
    public void testGetAllTicketsFailure() {
        when(ticketRepo.findAll()).thenReturn(Collections.emptyList());

        List<Ticket> tickets = ticketService.getAllTickets();
        assertNull(tickets);
        verify(ticketRepo, times(1)).findAll();
    }

    @Test
    public void testAddTicketSuccess() {
        when(ticketRepo.saveAndFlush(any(Ticket.class))).thenReturn(testTicket);

        boolean isAdded = ticketService.addTicket(testTicket);
        assertEquals(true, isAdded);
        verify(ticketRepo, times(1)).saveAndFlush(any(Ticket.class));
    }

    
    
    @Test
    public void testDeleteTicketSuccess() {
        doNothing().when(ticketRepo).deleteTicketData(anyInt());

        boolean isDeleted = ticketService.deleteTicket(1);
        assertEquals(true, isDeleted);
        verify(ticketRepo, times(1)).deleteTicketData(anyInt());
    }
    
    
}
