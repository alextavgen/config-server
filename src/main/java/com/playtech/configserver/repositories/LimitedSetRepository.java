package com.playtech.configserver.repositories;

import com.playtech.configserver.domain.ErrorCode;
import com.playtech.configserver.domain.LimitedSet;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

/**
 * Created by aleksandr on 24/07/2017.
 */
@RepositoryRestResource(collectionResourceRel = "limited_set", path = "limited_set")
public interface LimitedSetRepository extends MongoRepository<LimitedSet, String>  {
    LimitedSet findByLicenseeIgnoreCaseAndUpdated(@Param ("licensee") String licensee,
                                            @Param ("updated") LocalDateTime updated);
    Stream<LimitedSet> findByLicenseeIgnoreCase(@Param ("licensee") String licensee);


}

