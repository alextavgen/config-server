package com.playtech.configserver.domain;

import lombok.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by aleksandr on 24/07/2017.
 */
@Document(collection = "api_ops")
@Value
public class ApiOperation {
    private @Id Integer id;
    private String service;
    private String operation;
}
