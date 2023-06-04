package com.movie.book.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(code=HttpStatus.CONFLICT, reason="Movie Name present : Custom exception")
public class DuplicateMovieNameException extends Exception{

	private static final long serialVersionUID = 1L;

}
