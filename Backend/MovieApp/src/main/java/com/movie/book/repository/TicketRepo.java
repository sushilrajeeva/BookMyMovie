package com.movie.book.repository;

import java.util.List;


import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.movie.book.model.Ticket;


@Repository
@Transactional
public interface TicketRepo  extends JpaRepository<Ticket, Integer>
{
	@Query(value="select r from Ticket r where r.movie_id_fk = :movieId")
	public List<Ticket> getTicketList(int movieId);
	
	@Modifying
	@Query(value="delete from Ticket where movie_id_fk = :movieId")
	public void deleteTicketData(int movieId);

}
