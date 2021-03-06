package com.playtech.configserver;

import com.playtech.configserver.domain.ApiOperation;
import com.playtech.configserver.domain.LimitedSet;
import com.playtech.configserver.repositories.LimitedSetRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
@EnableMongoRepositories
public class ConfigServerApplication {
	private static final Logger logger = LoggerFactory.getLogger(ConfigServerApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(ConfigServerApplication.class, args);
		logger.debug("--Config Server Started--");
	}
}
