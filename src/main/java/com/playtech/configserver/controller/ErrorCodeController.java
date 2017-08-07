package com.playtech.configserver.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.playtech.configserver.ConfigServerApplication;
import com.playtech.configserver.domain.ErrorCode;
import com.playtech.configserver.repositories.ErrorCodeRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/**
 * Created by aleksandr on 17/07/2017.
 */
@Controller
@RequestMapping("/error_code")
@CrossOrigin(maxAge = 3600)
public class ErrorCodeController {

    private static final Logger logger = LoggerFactory.getLogger(ErrorCodeController.class);
    private static final String CORS_HOST = "http://localhost:3004";

    private final ErrorCodeRepository repo;

    @Autowired
    public ErrorCodeController(ErrorCodeRepository repo) {
        Assert.notNull(repo, "Repository must not be null!");
        this.repo = repo;
    }

    @RequestMapping(value = "/{id}",method= RequestMethod.GET, produces="application/json")
    public @ResponseBody ErrorCode showErrorCode(@PathVariable("id") Integer id) {
        logger.info( "Received GET request with id:" + id );
        // Do null check for id
        ErrorCode user = repo.findById(id);
        return user;
    }

    @RequestMapping(method= RequestMethod.GET, produces="application/json")
    public @ResponseBody
    List<ErrorCode> showErrorCodeList() {
        logger.info( "Received GET request for List of Error Codes");
        List<ErrorCode> list = repo.findAll();
        return list;
    }

    @CrossOrigin(origins = CORS_HOST)
    @RequestMapping(method = RequestMethod.POST, consumes = "text/plain", produces="application/json")
    public @ResponseBody ErrorCode postErrorCode(@RequestBody String corsRequest) {
        ErrorCode errCode = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            errCode = mapper.readValue(corsRequest, ErrorCode.class);
            logger.info( "Received POST request with id:" + errCode.getId()  );
            repo.save(errCode);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return errCode;
    }

    @CrossOrigin(origins = CORS_HOST)
    @RequestMapping(method = RequestMethod.PUT, consumes = "application/json", produces="application/json")
    public @ResponseBody ErrorCode putErrorCode(@RequestBody String corsRequest) {
        ErrorCode errCode = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            errCode = mapper.readValue(corsRequest, ErrorCode.class);
            logger.info( "Received PUT request with id:" + errCode.getId()  );
            repo.deleteErrorCodeById( errCode.getId() );
            repo.save(errCode);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return errCode;
    }
    @CrossOrigin(origins = CORS_HOST)
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, consumes = "application/json", produces="application/json")
    public ResponseEntity<?> deleteErrorCode(@PathVariable("id") Integer id) {
        try{
            Integer idReturned = repo.deleteErrorCodeById(id);
            logger.info( "Received DELETE request with id:" + id  );
            return new ResponseEntity<>(idReturned, HttpStatus.OK);
        }catch(Exception ex){
            String errorMessage;
            errorMessage = ex.toString() ;
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }

    }
}
