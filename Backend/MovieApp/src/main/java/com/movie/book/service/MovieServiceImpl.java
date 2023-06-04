package com.movie.book.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//import com.movie.book.exceptions.DuplicateMovieIdExceptions;
import com.movie.book.exceptions.DuplicateMovieNameException;
import com.movie.book.exceptions.MovieNotAvailableException;
import com.movie.book.model.Movie;
import com.movie.book.repository.MovieRepository;


@Service
public class MovieServiceImpl implements MovieService {
	
	Logger log = LoggerFactory.getLogger(MovieServiceImpl.class);
	
	@Autowired
	private MovieRepository movieRepo;

	@Override
	public List<Movie> getAllMovies() {
		log.info("Fetching all movies");
		List<Movie> movielist = movieRepo.findAll();
		if(movielist != null && movielist.size() > 0)
		{
			log.info("Found movies");
			return movielist;
		}
		log.info("No movies found");
		return null;
	}

	@Override
	public Movie addMovie(Movie movie) throws DuplicateMovieNameException {
		log.info("Adding a new movie");
		Movie movieObj = movieRepo.findMovie(movie.getMovieName(), movie.getTheatreName());
		
		if(movieObj!=null)
		{
			log.error("Error: Movie already exists");
			throw new DuplicateMovieNameException();
		}
		log.info("Movie added successfully");
		return movieRepo.saveAndFlush(movie);
	}

	@Override
	public boolean deleteMovie(String movieName, String theatreName) {
		
		log.info("Deleting a movie");
		Movie movieObj = movieRepo.findMovie(movieName, theatreName);
		
		if(movieObj!=null)
		{
		log.info("Movie deleted successfully");
		movieRepo.deleteById(movieObj.getMovieId());
		return true;
		}
		log.error("Error: Movie not found to delete");
		return false;
	}

	@Override
	public boolean updateMovie(Movie movie) {
		
		log.info("Updating a movie");
		
		System.out.println("My movie ID is -> " + movie.getMovieId());
		
		 Movie movie1 = getMovieById(movie.getMovieId());
		 
		 System.out.println("From service impl update");
		 
		 System.out.println(movie1);
		 
		if(movie1!=null)
		{
			Movie movieObj = movie1;
			movieObj.setMovieName(movie.getMovieName());
			movieRepo.save(movieObj);
			log.info("Movie updated successfully");
			return true;
		}

		log.error("Movie not found to update");

		return false;
	}

	@Override
	public Movie getMovieById(int mid) {
		log.info("Fetching movie by ID");
		Optional<Movie> movie = movieRepo.findById(mid);
		if(movie.isPresent())
		{
			log.info("Found movie by ID");
			return movie.get();
		}
		log.error("Error: No movie found with the given ID");
		
		return null;
	}

	@Override
	public List<Movie> getMovieByName(String name) {
		log.info("Searching movie by name");
	    List<Movie> movies = movieRepo.searchByMovieName(name);
	    if (movies != null && !movies.isEmpty()) {
	        log.info("Movies found by name");
	    } else {
	        log.info("No movies found by name");
	    }
	    return movies;
	}


//	@Override
//	public List<Movie> searchByMovieName(String str) throws MovieNotAvailableException {
//		log.info("searching movie by name");
//		List<Movie> movies = movieRepo.searchByMovieName(str);
//		if(movies.size()==0) {
//			log.error("no movie found with "+str+" name");
//			throw new MovieNotAvailableException("Searched movie is not available");
//			
//		}
//		log.info("movie found with "+str+" name");
//		return movies;
//	}

	
	

}
