package com.playtech.configserver.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.playtech.configserver.domain.ClientApiEntity;
import com.playtech.configserver.domain.ServiceOperations;
import com.playtech.configserver.repositories.ClientApiEntityRepository;
import com.playtech.configserver.repositories.ServiceOperationsRepository;

/**
 * Created by aleksandr.tavgen on 07/08/2017.
 */
@Controller
@RequestMapping("/clientapi_ops")
@CrossOrigin(maxAge = 3600)
public class ClientApiServicesOperationsController {
	private static final Logger logger = LoggerFactory.getLogger(ClientApiEntityController.class);
	private static final String CORS_HOST = "http://localhost:3004";

	private final ServiceOperationsRepository repo;

	@Autowired
	public ClientApiServicesOperationsController(ServiceOperationsRepository repo) {
		Assert.notNull(repo, "Repository must not be null!");
		this.repo = repo;
	}

	@RequestMapping(method= RequestMethod.GET, produces="application/json")
	public @ResponseBody
	ResponseEntity<List<String>> showAllServices() {
		logger.info( "Received GET request for List of Services for Client API");
		List<String> list = repo.findAll().stream()
								.map(entity -> entity.getService())
								.collect( Collectors.toList());
		return new ResponseEntity<List<String>>( list, HttpStatus.OK );
	}

	@RequestMapping(value="operations", method= RequestMethod.GET, produces="application/json")
	public @ResponseBody
	ResponseEntity<List<String>> showAllOperations() {
		logger.info( "Received GET request for List of Operations for Client API");
		List<String> list = repo.findAll().stream()
				.flatMap(entity -> entity.getOperations().stream()
									.map(element -> entity.getService()+"/" + element.getName()))
				.collect( Collectors.toList());

		return new ResponseEntity<List<String>>( list, HttpStatus.OK );
	}

	@RequestMapping(value = "/{service}",method= RequestMethod.GET, produces="application/json")
	public @ResponseBody
	ResponseEntity<List<String>> showOperationsForLicensee(@PathVariable("service") String service) {
		logger.info( "Received GET request for List of Operations for Client API Service");
		ServiceOperations operations = repo.findByService(service);
		List<String> list = operations.getOperations().stream()
										.map (entity -> entity.getName())
										.collect( Collectors.toList());
		return new ResponseEntity<List<String>>( list, HttpStatus.OK );
	}

}
