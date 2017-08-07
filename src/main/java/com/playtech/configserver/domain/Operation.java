package com.playtech.configserver.domain;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Value;

/**
 * Created by aleksandr.tavgen on 07/08/2017.
 */
@Value
public class Operation {
	private String name;
	private String description;
	private String type;
	private String swaggerSpec;
	private String requestSchema;
	private String responseSchema;
	private String requestExample;
}
