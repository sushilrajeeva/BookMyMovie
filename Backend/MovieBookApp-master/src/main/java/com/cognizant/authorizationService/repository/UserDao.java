package com.cognizant.authorizationService.repository;



import org.springframework.data.repository.CrudRepository;

import com.cognizant.authorizationService.model.User;



public interface UserDao extends CrudRepository<User, String>{

}