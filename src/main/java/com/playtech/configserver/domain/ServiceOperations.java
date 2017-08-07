package com.playtech.configserver.domain;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Value;

/**
 * Created by aleksandr.tavgen on 07/08/2017.
 */
@Document(collection = "service_operations")
@Value
public class ServiceOperations {
	@Id
	private Object _id;
	private String service;
	private List<Operation> operations;
}
