package com.movie.book.service;

import java.util.List;

import com.movie.book.model.Movie;
import com.movie.book.model.Ticket;

public interface TicketService {
	
public List<Ticket> getAllTickets(int mid);
	
	
	public boolean addTicket(Ticket ticket);
	public boolean deleteTicket(int mid);
	public List<Ticket> getAllTickets();


}
