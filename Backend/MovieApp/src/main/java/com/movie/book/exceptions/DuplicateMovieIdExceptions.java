package com.movie.book.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code=HttpStatus.CONFLICT, reason="Movie ID present : Custom exception")
public class DuplicateMovieIdExceptions extends Exception {

	private static final long serialVersionUID = 1L;

}
