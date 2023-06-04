package com.movie.book.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class JwtResponse {
	private UserDTO user;
    private String jwtToken;
    
    public JwtResponse() {}

    @JsonCreator
    public JwtResponse(@JsonProperty("user") UserDTO user, @JsonProperty("jwtToken") String jwtToken) {
    	
        this.user = user;
        this.jwtToken = jwtToken;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }
}
