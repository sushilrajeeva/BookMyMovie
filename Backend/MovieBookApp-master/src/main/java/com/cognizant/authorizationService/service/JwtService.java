package com.cognizant.authorizationService.service;





import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cognizant.authorizationService.model.JwtRequest;
import com.cognizant.authorizationService.model.JwtResponse;
import com.cognizant.authorizationService.model.User;
import com.cognizant.authorizationService.model.UserDTO;
import com.cognizant.authorizationService.repository.UserDao;
import com.cognizant.authorizationService.util.JwtUtil;





@Service
public class JwtService implements UserDetailsService {
     
	@Lazy
	 @Autowired
	    private JwtUtil jwtUtil;
       
	@Lazy
	    @Autowired
	    private UserDao userDao;
        
	@Lazy
	    @Autowired
	    private AuthenticationManager authenticationManager;

	    public JwtResponse createJwtToken(JwtRequest jwtRequest) throws Exception {
	        String userName = jwtRequest.getUserName();
	        String userPassword = jwtRequest.getUserPassword();
	        
	        System.out.println("JWT Service is called!!!");
	        System.out.println("Username : " + userName + " Password : " + userPassword);
	        authenticate(userName, userPassword);
	        
	        System.out.println("Did i pass");

	        UserDetails userDetails = loadUserByUsername(userName);
	        String newGeneratedToken = jwtUtil.generateToken(userDetails);
	        
	        System.out.println("Everything is fine, Generated token -> " + newGeneratedToken);

	        User user = userDao.findById(userName).get();
	        UserDTO userDTO = new UserDTO();
	        userDTO.setUserName(user.getUserName());
	        userDTO.setUserPassword(user.getUserPassword());
	        System.out.println("Found user - " + user.getUserName());
	        return new JwtResponse(userDTO, newGeneratedToken);
	    }

	    @Override
	    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	        User user = userDao.findById(username).get();

	        if (user != null) {
	            return new org.springframework.security.core.userdetails.User(
	                    user.getUserName(),
	                    user.getUserPassword(),
	                    getAuthority(user)
	            );
	        } else {
	            throw new UsernameNotFoundException("User not found with username: " + username);
	        }
	    }

	    private Set<SimpleGrantedAuthority> getAuthority(User user) {
	        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
	        user.getRole().forEach(role -> {
	            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleName()));
	        });
	        return authorities;
	    }

	    private void authenticate(String userName, String userPassword) throws Exception {
	        try {
	            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, userPassword));
	        } catch (DisabledException e) {
	            throw new Exception("USER_DISABLED", e);
	        } catch (BadCredentialsException e) {
	            throw new Exception("INVALID_CREDENTIALS", e);
	        }
	    }

}
