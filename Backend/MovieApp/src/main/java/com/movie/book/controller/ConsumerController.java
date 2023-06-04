package com.movie.book.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.movie.book.model.JwtResponse;
import com.movie.book.model.UserDTO;


@RestController
@RequestMapping("call/consumer")
public class ConsumerController {
	Logger log = LoggerFactory.getLogger(ConsumerController.class);
	@PostMapping(value = "/authenticate")
	public ResponseEntity<?> consumeLogin(@RequestBody UserDTO userdto) throws RestClientException, Exception {
		
		log.info("Consumer called for user: {}", userdto.getUserName());
//		@Autowired
//		private JwtController Jwt;
		
		String baseUrl = "http://localhost:9091/authenticate";

		RestTemplate restTemplate = new RestTemplate();
		

		ResponseEntity<JwtResponse> result = null;
		try {
			log.info("Sending login request for user: {}", userdto.getUserName());
			result = restTemplate.exchange(baseUrl, HttpMethod.POST, getHeaders(userdto),
					new ParameterizedTypeReference<JwtResponse>() {
					});
		} catch (Exception e) {
			log.error("Exception occurred during login for user: {}, {}", userdto.getUserName(), e.getMessage());
			return new ResponseEntity<String>("Login was not successful", HttpStatus.UNAUTHORIZED);

		}
		log.info("Login was successful for user: {}", userdto.getUserName());
		return new ResponseEntity<JwtResponse>(result.getBody(), HttpStatus.OK);

	}

	private static HttpEntity<UserDTO> getHeaders(UserDTO userdto) {
		
		
		HttpHeaders header = new HttpHeaders();
		
		System.out.println("Get headers is called");

		header.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
		header.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		System.out.println("Did i execute");
		return new HttpEntity<UserDTO>(userdto, header);
	}

}
