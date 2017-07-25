package com.playtech.configserver.domain;

import lombok.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by aleksandr on 24/07/2017.
 */
@Value
@Document(collection = "limited_sets")
public class LimitedSet {

    private @Id String id;

    private LocalDateTime updated;

    private String licensee;

    private List<ApiOperation> set;
}
