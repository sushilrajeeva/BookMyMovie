package com.movie.book.exceptions;

public class MovieNotAvailableException extends Exception{
	
	private static final long serialVersionUID = 1L;

	public MovieNotAvailableException(String msg) {
		super(msg);
	}
}