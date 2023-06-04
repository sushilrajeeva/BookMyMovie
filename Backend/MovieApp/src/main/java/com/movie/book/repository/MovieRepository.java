package com.movie.book.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.movie.book.model.Movie;


@Repository
@Transactional 
public interface MovieRepository extends JpaRepository<Movie, Integer> {

	//Optional <Movie> findByMovieName(String movieName);
	@Query("SELECT m FROM Movie m WHERE m.movieName = :movieName")
	List<Movie> searchByMovieName(@Param("movieName") String str);
	
	@Query("SELECT m FROM Movie m WHERE m.movieName = :movieName and m.theatreName = :theatreName")
	Movie findMovie(@Param("movieName") String movieName, @Param("theatreName") String theatreName);
	



}
