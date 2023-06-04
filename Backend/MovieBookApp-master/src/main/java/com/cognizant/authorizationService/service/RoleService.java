package com.cognizant.authorizationService.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognizant.authorizationService.model.Role;
import com.cognizant.authorizationService.repository.RoleDao;



@Service
public class RoleService {
	@Autowired
	private RoleDao roleDao;
	public Role createNewRole(Role role) {
		return roleDao.save(role);
	}

}