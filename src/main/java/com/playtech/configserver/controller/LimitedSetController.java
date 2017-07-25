package com.playtech.configserver.controller;

import com.playtech.configserver.domain.ErrorCode;
import com.playtech.configserver.domain.LimitedSet;
import com.playtech.configserver.repositories.ErrorCodeRepository;
import com.playtech.configserver.repositories.LimitedSetRepository;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

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

    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String CORS_HOST = "http://localhost:3004";
    @Autowired
    private  LimitedSetRepository repo;
    @Autowired
    private MongoTemplate mongoTemplate;

    @RequestMapping(value = "/licensees",method= RequestMethod.GET, produces="application/json")
    public @ResponseBody
    Set<String> getLicenseeSet() {

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
        return repo.findByLicenseeIgnoreCaseAndUpdated(licensee, dateTime);
        //return repo.findByUpdated(updated);
    }

    @RequestMapping(value = "/licensees/{licensee}",method= RequestMethod.GET, produces="application/json")
    public @ResponseBody
    List<String> getReviewListByLicensee(@PathVariable("licensee") String licensee) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
        return repo.findByLicenseeIgnoreCase(licensee)
                .map(e -> e.getUpdated())
                .map(e -> e.format(formatter))
                .collect(Collectors.toList());

    }

}
