package com.playtech.configserver.domain;

import lombok.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by aleksandr on 17/07/2017.
 */
@Value
@Document(collection = "error_codes")
public class ErrorCodes implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Indexed(unique=false)
    private Date review;

    @DBRef
    private List<ErrorCode> errorCodes;

}
