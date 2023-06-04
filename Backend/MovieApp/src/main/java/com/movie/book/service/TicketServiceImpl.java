package com.movie.book.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.movie.book.model.Movie;
import com.movie.book.model.Ticket;
import com.movie.book.repository.TicketRepo;

import lombok.extern.slf4j.Slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
@Service
public class TicketServiceImpl implements TicketService{
	
	Logger log = LoggerFactory.getLogger(TicketServiceImpl.class);

	
	@Autowired
	private TicketRepo ticketRepo;

	@Override
	public List<Ticket> getAllTickets(int mid) {
		log.info("Fetching all tickets for movie ID {}", mid);
		List<Ticket> ticketlist = ticketRepo.getTicketList(mid);
		log.info("Fetched {} tickets for movie ID {}", ticketlist.size(), mid);
		return ticketlist;
	}

	@Override
	public boolean addTicket(Ticket ticket) {
		log.info("Adding new ticket for movie: {}", ticket.getMovieName());
		Ticket ticketObj = new Ticket();
		
		ticketObj.setMovieName(ticket.getMovieName());
		ticketObj.setMovie_id_fk(ticket.getMovie_id_fk());
		ticketObj.setTotalSeat(ticket.getTotalSeat());
		ticketObj.setSeatsAvailable(ticket.getSeatsAvailable());
		ticketObj.setSeatsBooked(ticket.getSeatsBooked());
		
		ticketRepo.saveAndFlush(ticketObj);
		
		log.info("Ticket added successfully for movie: {}", ticket.getMovieName());
		
		return true;
	}

	@Override
	public boolean deleteTicket(int mid) {
		log.info("Deleting ticket for movie ID {}", mid);
		ticketRepo.deleteTicketData(mid);
		log.info("Ticket deleted successfully for movie ID {}", mid);
		return true;
	}
	
	@Override
	public List<Ticket> getAllTickets() {
		log.info("Fetching all tickets");
		List<Ticket> ticketList = ticketRepo.findAll();
		if(ticketList != null && ticketList.size() > 0)
		{
			log.info("Fetched {} tickets", ticketList.size());
			return ticketList;
		}
		log.info("No tickets found");
		return null;
	}

}
