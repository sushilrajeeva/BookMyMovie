package com.movie.book;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.movie.book.controller.MovieController;
import com.movie.book.repository.MovieRepository;
import com.movie.book.service.MovieService;
import com.movie.book.service.TicketService;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.movie.book.model.Movie;

import java.util.Arrays;
import java.util.List;

public class MovieControllerTest {

    @InjectMocks
    MovieController movieController;

    @Mock
    MovieService ms;

    @Mock
    MovieRepository mr;

    @Mock
    TicketService ts;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddMovieSuccess() throws Exception {
        Movie movie = new Movie(); 
        when(ms.addMovie(any(Movie.class))).thenReturn(movie);

        ResponseEntity<?> response = movieController.addMovie(movie);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(movie, response.getBody());
    }

    @Test
    public void testAddMovieFailure() throws Exception {
        Movie movie = new Movie(); 
        when(ms.addMovie(any(Movie.class))).thenReturn(null);

        ResponseEntity<?> response = movieController.addMovie(movie);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertEquals("No Movie", response.getBody());
    }

    @Test
    public void testGetMoviesSuccess() {
        List<Movie> movies = Arrays.asList(new Movie());
        when(ms.getAllMovies()).thenReturn(movies);

        ResponseEntity<?> response = movieController.getMovies();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(movies, response.getBody());
    }

    @Test
    public void testGetMoviesFailure() {
        when(ms.getAllMovies()).thenReturn(null);

        ResponseEntity<?> response = movieController.getMovies();

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertEquals("No Movie list", response.getBody());
    }

    @Test
    public void testGetMovieByIdSuccess() {
        Movie movie = new Movie();
        when(ms.getMovieById(1)).thenReturn(movie);

        ResponseEntity<?> response = movieController.getMovieById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(movie, response.getBody());
    }

    @Test
    public void testGetMovieByIdFailure() {
        when(ms.getMovieById(1)).thenReturn(null);

        ResponseEntity<?> response = movieController.getMovieById(1);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertEquals("No Movie with this ID", response.getBody());
    }

    @Test
    public void testGetMovieByNameSuccess() {
        List<Movie> movies = Arrays.asList(new Movie());
        when(ms.getMovieByName("test")).thenReturn(movies);

        ResponseEntity<?> response = movieController.getMovieByName("test");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(movies, response.getBody());
    }

    @Test
    public void testGetMovieByNameFailure() {
        when(ms.getMovieByName("test")).thenReturn(null);

        ResponseEntity<?> response = movieController.getMovieByName("test");

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertEquals("No Movie with this Name", response.getBody());
    }

  

    @Test
    public void testDeleteMovieByIdFailure() {
        Movie movie = new Movie();
        when(mr.findMovie("test", "test")).thenReturn(movie);
        when(ms.deleteMovie("test", "test")).thenReturn(false);
        when(ts.deleteTicket(1)).thenReturn(false);

        ResponseEntity<?> response = movieController.deleteMovieById("test", "test");

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Movie not deleted", response.getBody());
    }

    @Test
    public void testUpdateMovieSuccess() {
        Movie movie = new Movie();
        when(ms.updateMovie(any(Movie.class))).thenReturn(true);

        ResponseEntity<?> response = movieController.updateMovie(1, movie);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(movie.getMovieName(), response.getBody());
    }

    @Test
    public void testUpdateMovieFailure() {
        Movie movie = new Movie();
        when(ms.updateMovie(any(Movie.class))).thenReturn(false);

        ResponseEntity<?> response = movieController.updateMovie(1, movie);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Movie could not be updated", response.getBody());
    }
}
