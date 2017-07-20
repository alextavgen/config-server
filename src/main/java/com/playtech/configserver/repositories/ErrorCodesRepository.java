package com.playtech.configserver.repositories;

import com.playtech.configserver.domain.ErrorCodes;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Date;
import java.util.List;

/**
 * Created by aleksandr on 17/07/2017.
 */
@RepositoryRestResource(collectionResourceRel = "error_codes", path = "errors_codes")
public interface ErrorCodesRepository extends CrudRepository<ErrorCodes, String> {
    List<ErrorCodes> findByReview(@Param("review") Date review);
}
