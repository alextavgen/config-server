package com.playtech.configserver.repositories;

import com.playtech.configserver.domain.ErrorCode;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


import java.util.List;

/**
 * Created by aleksandr on 17/07/2017.
 */
@RepositoryRestResource(collectionResourceRel = "error_code", path = "error_code")
public interface ErrorCodeRepository extends MongoRepository<ErrorCode, String> {
    List<ErrorCode> findByCode(@Param("code") String code);
    List<ErrorCode> findById(@Param("id") String id);
}