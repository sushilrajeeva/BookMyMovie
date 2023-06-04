package com.movie.book.exceptions;

public class NoTicketBookedException extends Exception{

	private static final long serialVersionUID = 1L;

	public NoTicketBookedException(String msg) {
		super(msg);
	}

}

