package com.playtech.configserver.domain;

import lombok.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * Created by aleksandr on 17/07/2017.
 */
@Value
@Document(collection = "error_code")
public class ErrorCode implements Serializable {

    @Id private Integer id;

    private Integer eId;

    private String code;

    private String descr;

    private String group;
}
