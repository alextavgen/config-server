package com.playtech.configserver.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.playtech.configserver.domain.ErrorCode;
import com.playtech.configserver.domain.LimitedSet;
import com.playtech.configserver.domain.Operation;
import com.playtech.configserver.domain.ServiceOperations;

/**
 * Created by aleksandr.tavgen on 07/08/2017.
 */
@RepositoryRestResource(collectionResourceRel = "service_operations", path = "service_operations")
public interface ServiceOperationsRepository extends MongoRepository<ServiceOperations, String> {
	ServiceOperations findByService (@Param("service") String service);
}
