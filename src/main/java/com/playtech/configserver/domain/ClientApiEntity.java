package com.playtech.configserver.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Value;

/**
 * Created by aleksandr.tavgen on 03/08/2017.
 */
@Document(collection = "clientapi_entities")
@Value
public class ClientApiEntity {
	@Id
	private Object _id;
	private String property;
	private String schema;
	private String type;
	private String descr;

}
