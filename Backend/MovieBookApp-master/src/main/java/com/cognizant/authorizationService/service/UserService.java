package com.cognizant.authorizationService.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cognizant.authorizationService.model.Role;
import com.cognizant.authorizationService.model.User;
import com.cognizant.authorizationService.repository.RoleDao;
import com.cognizant.authorizationService.repository.UserDao;



@Service
public class UserService {
	@Autowired
	private UserDao userdao;
	
	@Autowired
	private RoleDao roleDao;
	
	@Autowired
    private PasswordEncoder passwordEncoder;
  
	 
	
	public void initRolesAndUser() {
		Role adminRole=new Role();
		adminRole.setRoleName("Admin");
		adminRole.setRoleDesc("Admin Role");
		roleDao.save(adminRole);
		
		Role userRole=new Role();
		userRole.setRoleName("User");
		userRole.setRoleDesc("Default Role for user");
		roleDao.save(userRole);
		
		User adminUser=new User();
		adminUser.setUserFirstName("admin");
		adminUser.setUserLastName("admin");
		adminUser.setUserName("admin123");
		adminUser.setEmail("admin@gmail.com");
		adminUser.setUserPassword(getEncodedPassword("admin@pass"));
		adminUser.setUserConfirmedPassword(getEncodedPassword("admin@pass"));
		adminUser.setContactNumber("9999999999");
		Set<Role> adminRoles=new HashSet<>();
		adminRoles.add(adminRole);
		adminUser.setRole(adminRoles);
		userdao.save(adminUser);
		
	User user=new User();
	user.setUserFirstName("mohit");
	user.setUserLastName("sahu");
	user.setUserName("mohit123");
	user.setEmail("mohit@gmail.com");
	user.setUserPassword(getEncodedPassword("mohit@123"));
	user.setUserConfirmedPassword(getEncodedPassword("mohit@123"));
	user.setContactNumber("9876543210");
	Set<Role> userRoles=new HashSet<>();
	userRoles.add(userRole);
	user.setRole(userRoles);
	userdao.save(user);
//	
		
		
		
	}
	public HttpStatus registerNewUser(User user) {
        Role role = roleDao.findById("User").get();
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(role);
        String upass=user.getUserPassword();
        System.out.println("User pass = " + upass);
        String uConfpass=user.getUserConfirmedPassword();

        System.out.println("User conf pass = " + uConfpass);
        if(upass.equals(uConfpass)) {
        user.setRole(userRoles);
        user.setUserPassword(getEncodedPassword(user.getUserPassword()));
        user.setUserConfirmedPassword(getEncodedPassword(user.getUserConfirmedPassword()));
         userdao.save(user);
         return HttpStatus.OK;
        }
        else return HttpStatus.BAD_REQUEST;

       
    }
	
	public String getEncodedPassword(String password) {
        return passwordEncoder.encode(password);
    }
}

