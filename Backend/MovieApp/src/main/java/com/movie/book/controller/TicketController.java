package com.movie.book.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.movie.book.model.Movie;
import com.movie.book.model.Ticket;
import com.movie.book.service.MovieService;
import com.movie.book.service.TicketService;

//import com.moviebookingapp.kafka.Producer;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("api/v1.0/ticket")
public class TicketController {
	
	@Autowired
	private TicketService ts;
	
	@Autowired
	private MovieService ms;
	
//	@Autowired
//	Producer producer;
	
	Logger log = LoggerFactory.getLogger(TicketController.class);
	
	@PostMapping("/add/{movieId}/{seatsBooked}")
	public ResponseEntity<?> addTicket(@PathVariable("movieId")int movieId, @PathVariable("seatsBooked")int seatsBooked)
	{
		log.info("Attempting to add new ticket for movie ID {} with {} seats booked", movieId, seatsBooked);
		Movie m1 = ms.getMovieById(movieId);
		
		
		if(m1 !=null)
		{
			
			Ticket ticket = new Ticket();
			ticket.setMovie_id_fk(m1.getMovieId());
			ticket.setMovieName(m1.getMovieName());
			ticket.setTotalSeat(100);
			
			int seatsAvailable = m1.getSeatsAvailable();
			
			if(seatsAvailable-seatsBooked < 0) {
				log.info("Ticket cannot be created as seats requested are more than available seats");
				return new ResponseEntity<String>("Ticket cannot be created as seats you are trying to book is more than available seats", HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			ticket.setSeatsAvailable(seatsAvailable - seatsBooked);
			ticket.setSeatsBooked(seatsBooked);
			
			m1.setSeatsAvailable(seatsAvailable - seatsBooked);
			m1.setBookedSeats(m1.getBookedSeats()+seatsBooked);
			
//			m1.setMovieName(ticket.getMovieName());
//			
//			ticket.setMovie_id_fk(ticket.getMovie_id_fk());
//			//ticket.setTotalSeat(m1.));
//			ticket.setSeatsAvailable(ticket.getSeatsAvailable());
//			ticket.setSeatsBooked(ticket.getSeatsBooked());
//			
			if(ms.updateMovie(m1) && ts.addTicket(ticket))
			{
				//producer.sendMessage(ticket.getMovieName());
				log.info("Ticket added successfully for movie ID {} with {} seats booked", movieId, seatsBooked);
				return new ResponseEntity<Ticket>(ticket, HttpStatus.CREATED);
			}
		}
		log.info("Failed to add ticket for movie ID {} with {} seats booked", movieId, seatsBooked);
		return new ResponseEntity<String>("Ticket cannot be created", HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	
	@GetMapping("/admin/getAllTickets")
	public ResponseEntity<?> getAllTickets() 
	{
		log.info("Fetching all tickets");
		
		List<Ticket> ticketList = ts.getAllTickets();
		
		log.info("Fetched {} tickets", ticketList != null ? ticketList.size() : 0);
		if(ticketList!=null && !ticketList.isEmpty())
		{
			

			return new ResponseEntity<List<Ticket>>(ticketList, HttpStatus.OK);
		}
		return new ResponseEntity<String>("Ticket list is Empty", HttpStatus.NO_CONTENT);
		
	}

}
