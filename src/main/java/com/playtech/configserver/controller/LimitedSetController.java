package com.playtech.configserver.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.playtech.configserver.domain.ErrorCode;
import com.playtech.configserver.domain.LimitedSet;
import com.playtech.configserver.repositories.ErrorCodeRepository;
import com.playtech.configserver.repositories.LimitedSetRepository;
import org.apache.tomcat.jni.Local;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by aleksandr on 24/07/2017.
 */
@Controller
@RequestMapping("/limited_set")
@CrossOrigin(maxAge = 3600)
public class LimitedSetController {

    private static final Logger logger = LoggerFactory.getLogger(LimitedSetController.class);
    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String CORS_HOST = "http://localhost:3004";

    private  LimitedSetRepository repo;

    private MongoTemplate mongoTemplate;

    @Autowired
    public LimitedSetController(LimitedSetRepository repo, MongoTemplate mongoTemplate) {
        Assert.notNull(repo, "Repository must not be null!");
        this.repo = repo;
        this.mongoTemplate = mongoTemplate;
    }


    @RequestMapping(value = "/licensees",method= RequestMethod.GET, produces="application/json")
    public @ResponseBody
    Set<String> getLicenseeSet() {
        logger.info( "Received GET request for List of Limited Sets");
        Query query = new Query();
        query.fields().include("licensee");
        Stream<LimitedSet> result = mongoTemplate.find(query, LimitedSet.class).stream();
        return result
                .map(set -> set.getLicensee())
                .distinct()
                .collect(Collectors.toSet());
    }

    @RequestMapping(value = "/licensees/{licensee}/{updated}",method= RequestMethod.GET, produces="application/json")
    public @ResponseBody
    LimitedSet getLimitedSetByLicenseeAndReview(@PathVariable("updated") String updated,
                                     @PathVariable("licensee") String licensee) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
        LocalDateTime dateTime = LocalDateTime.parse(updated, formatter);
        logger.info( "Received GET for Limited Sets for: " + licensee);
        return repo.findByLicenseeIgnoreCaseAndUpdated(licensee, dateTime);
        //return repo.findByUpdated(updated);
    }

    @RequestMapping(value = "/licensees/{licensee}",method= RequestMethod.GET, produces="application/json")
    public @ResponseBody
    List<String> getReviewListByLicensee(@PathVariable("licensee") String licensee) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
        logger.info( "Received GET for Reviews for Limited Sets for: " + licensee );
        return repo.findByLicenseeIgnoreCase(licensee)
                .map(e -> e.getUpdated())
                .map(e -> e.format(formatter))
                .collect(Collectors.toList());

    }

    @CrossOrigin(origins = CORS_HOST)
    @RequestMapping(value = "/licensees/{licensee}", method = RequestMethod.POST, consumes = "text/plain", produces="application/json")
    public @ResponseBody LimitedSet postNewLimitedSet(@PathVariable("licensee") String licensee, @RequestBody String corsRequest) {
        LimitedSet set = null;
        LimitedSet newSet = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            set = mapper.readValue(corsRequest, LimitedSet.class);
            logger.info( "Received POST for new Review for Limited Sets for:" + licensee );
            newSet = new LimitedSet(null, LocalDateTime.now().withNano(0),set.getLicensee(),set.getSet());
            repo.save(newSet);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return newSet;
    }

}
