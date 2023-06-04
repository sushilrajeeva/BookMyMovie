package com.movie.book.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.movie.book.exceptions.DuplicateMovieIdExceptions;
import com.movie.book.exceptions.DuplicateMovieNameException;
import com.movie.book.model.Movie;
import com.movie.book.model.Ticket;
import com.movie.book.repository.MovieRepository;
import com.movie.book.service.MovieService;
import com.movie.book.service.TicketService;

//import com.moviebookingapp.kafka.Producer;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1.0")

public class MovieController {
	
	@Autowired
	private MovieService ms;
	
	@Autowired
	private MovieRepository mr;
	
	@Autowired
	private TicketService ts;
	
//	@Autowired
//	Producer producer;
	 Logger log = LoggerFactory.getLogger(MovieController.class);
	
	@PostMapping("/admin/addMovie")
	public ResponseEntity<?> addMovie(@RequestBody Movie movie) throws DuplicateMovieNameException, DuplicateMovieIdExceptions
	{
		log.info("Attempting to add new movie: {}", movie.getMovieName());
		//producer.sendMessage(movie.getMovieName());
		
		movie.setSeatsAvailable(100);
		movie.setBookedSeats(0);
		movie.setTicketList(new ArrayList<>());
		
		if(ms.addMovie(movie)!=null)
		{
			log.info("Movie added successfully: {}", movie.getMovieName());
			return new ResponseEntity<Movie>(movie, HttpStatus.CREATED);
		}
		log.info("Failed to add movie: {}", movie.getMovieName());
		return new ResponseEntity<String>("No Movie", HttpStatus.NO_CONTENT);
	}
	
	@GetMapping("/getAllMovies")
	public ResponseEntity<?> getMovies() 
	{
		
		log.info("Fetching all movies");
		List<Movie> movielist = ms.getAllMovies();
		log.info("Fetched {} movies", movielist != null ? movielist.size() : 0);
		if(movielist!=null)
		{
			for(Movie m: movielist)
			{
				List<Ticket> ticketlist = ts.getAllTickets(m.getMovieId());
				m.setTicketList(ticketlist);
			}

			return new ResponseEntity<List<Movie>>(movielist, HttpStatus.OK);
		}
		return new ResponseEntity<String>("No Movie list", HttpStatus.NO_CONTENT);
		
	}
	
	@GetMapping("/movieById/{mid}")
	public ResponseEntity<?> getMovieById(@PathVariable("mid") int movieId) 
	{
		log.info("Fetching movie by ID: {}", movieId);
		Movie movieidexists = ms.getMovieById(movieId);
		if(movieidexists !=null)
		{
			List<Ticket> ticketlist = ts.getAllTickets(movieidexists.getMovieId());
			movieidexists.setTicketList(ticketlist);
			log.info("Movie found by ID: {}", movieId);
			return new ResponseEntity<Movie>(movieidexists, HttpStatus.OK);
			
		}
		log.info("No movie found with ID: {}", movieId);
		return new ResponseEntity<String>("No Movie with this ID", HttpStatus.NO_CONTENT);
		
	}
	@GetMapping("/movieByName/{name}")
	public ResponseEntity<?> getMovieByName(@PathVariable("name") String name) 
	{
		log.info("Fetching movie by name: {}", name);
		List<Movie> movienameexists = ms.getMovieByName(name);
		if(movienameexists !=null)
		{
			log.info("Movies found by name: {}", name);
			return new ResponseEntity<List<Movie>>(movienameexists, HttpStatus.OK);
			
		}
		log.info("No movie found with name: {}", name);
		return new ResponseEntity<String>("No Movie with this Name", HttpStatus.NO_CONTENT);
		
	}
	
	@DeleteMapping("/admin/delete/{movieName}/{theatreName}")
	public ResponseEntity<?> deleteMovieById(@PathVariable("movieName") String movieName, @PathVariable("theatreName") String theatreName) 
	{
		log.info("Attempting to delete movie: {}", movieName);
		int mid = mr.findMovie(movieName, theatreName).getMovieId();
		if(ts.deleteTicket(mid) & ms.deleteMovie(movieName, theatreName))
		{
			log.info("Movie deleted successfully: {}", movieName);
			return new ResponseEntity<String>("Movie deleted",HttpStatus.OK);
		}
		log.error("Failed to delete movie: {}", movieName);
		return new ResponseEntity<String>("Movie not deleted", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@PutMapping("/admin/updateMovie/{mid}")
	public ResponseEntity<?> updateMovie(@PathVariable("mid") int mid, @RequestBody Movie movie) 
	{
		log.info("Updating movie ID {} with new information", mid);
		
		movie.setMovieId(mid);
		if(ms.updateMovie(movie))
	  {
			log.info("Movie updated successfully: {}", movie.getMovieName());
		  return new ResponseEntity<String>(movie.getMovieName(), HttpStatus.OK);
		  
	  }
		log.error("Failed to update movie ID: {}", mid);
	  return new ResponseEntity<String>("Movie could not be updated", HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
