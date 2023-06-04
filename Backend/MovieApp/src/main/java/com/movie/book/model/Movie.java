package com.movie.book.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="Movie")
public class Movie {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private int movieId;

	@Column
	private String movieName;

	@Column
	private String theatreName;

	@Column
	private int seatsAvailable;
	
	@Column
	private int bookedSeats;

	public int getBookedSeats() {
		return bookedSeats;
	}

	public void setBookedSeats(int bookedSeats) {
		this.bookedSeats = bookedSeats;
	}

	@Column
	@OneToMany(targetEntity = Ticket.class)
	private List<Ticket> ticketList;

	public Movie(int movieId, String movieName, String theatreName, int seatsAvailable, int bookedSeats,
			List<Ticket> ticketList) {
		super();
		this.movieId = movieId;
		this.movieName = movieName;
		this.theatreName = theatreName;
		this.seatsAvailable = seatsAvailable;
		this.bookedSeats = bookedSeats;
		this.ticketList = ticketList;
	}


	public Movie() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getSeatsAvailable() {
		return seatsAvailable;
	}

	public void setSeatsAvailable(int seatsAvailable) {
		this.seatsAvailable = seatsAvailable;
	}

	public int getMovieId() {
		return movieId;
	}

	public void setMovieId(int movieId) {
		this.movieId = movieId;
	}

	public String getMovieName() {
		return movieName;
	}

	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}

	public String getTheatreName() {
		return theatreName;
	}

	public void setTheatreName(String theatreName) {
		this.theatreName = theatreName;
	}
	public List<Ticket> getTicketList() {
		return ticketList;
	}

	public void setTicketList(List<Ticket> ticketList) {
		this.ticketList = ticketList;
	}

}
