package com.playtech.configserver.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.playtech.configserver.domain.ClientApiEntity;
import com.playtech.configserver.domain.ErrorCode;

/**
 * Created by aleksandr.tavgen on 03/08/2017.
 */
@RepositoryRestResource(collectionResourceRel = "clientapi_entities", path = "clientapi_entities")
public interface ClientApiEntityRepository extends MongoRepository<ClientApiEntity, String> {

}
