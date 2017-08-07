package com.playtech.configserver.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.playtech.configserver.domain.ClientApiEntity;
import com.playtech.configserver.repositories.ClientApiEntityRepository;


/**
 * Created by aleksandr.tavgen on 03/08/2017.
 */
@Controller
@RequestMapping("/clientapi_entities")
@CrossOrigin(maxAge = 3600)
public class ClientApiEntityController {
	private static final Logger logger = LoggerFactory.getLogger(ClientApiEntityController.class);
	private static final String CORS_HOST = "http://localhost:3004";

	private final ClientApiEntityRepository repo;

	@Autowired
	public ClientApiEntityController(ClientApiEntityRepository repo) {
		Assert.notNull(repo, "Repository must not be null!");
		this.repo = repo;
	}

	@RequestMapping(method= RequestMethod.GET, produces="application/json")
	public @ResponseBody
	List<ClientApiEntity> showErrorCodeList() {
		logger.info( "Received GET request for List of Client Entities");
		List<ClientApiEntity> list = repo.findAll();
		return list;
	}
}
