package com.movie.book.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

//import com.movie.book.exceptions.NoTicketBookedException;
//import com.movie.book.exceptions.MovieNotAvailableException;

public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(NoTicketBookedException.class)
    public ResponseEntity<?> handlerNoTicketBookedException(NoTicketBookedException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
	@ExceptionHandler(MovieNotAvailableException.class)
    public ResponseEntity<?> handlerMovieNotAvailableException(MovieNotAvailableException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
