package com.movie.book.service;

import java.util.List;

import com.movie.book.exceptions.DuplicateMovieIdExceptions;
import com.movie.book.exceptions.DuplicateMovieNameException;
import com.movie.book.model.Movie;
import com.movie.book.exceptions.MovieNotAvailableException;

public interface MovieService {

	public List<Movie> getAllMovies();

	public Movie addMovie(Movie movie) throws DuplicateMovieIdExceptions, DuplicateMovieNameException;

	public boolean deleteMovie(String movieName, String theatreName);

	public boolean updateMovie(Movie movie);

	public Movie getMovieById(int mid);
	
	public List<Movie> getMovieByName(String name);
	
	//public List<Movie> searchByMovieName(String str) throws MovieNotAvailableException;

}
