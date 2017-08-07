package com.playtech.configserver.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;

import com.playtech.configserver.domain.ClientApiEntity;

/**
 * Created by aleksandr.tavgen on 03/08/2017.
 */
@Configuration
public class RestDataConfig extends RepositoryRestConfigurerAdapter {
	@Override
	public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config)
	{
		config.exposeIdsFor(ClientApiEntity.class);
	}
}