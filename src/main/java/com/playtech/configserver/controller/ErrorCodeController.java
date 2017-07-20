package com.playtech.configserver.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.playtech.configserver.domain.ErrorCode;
import com.playtech.configserver.repositories.ErrorCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    private static final String CORS_HOST = "http://localhost:3004";

    private final ErrorCodeRepository repo;

    @Autowired
    public ErrorCodeController(ErrorCodeRepository repo) {
        Assert.notNull(repo, "Repository must not be null!");
        this.repo = repo;
    }

    @RequestMapping(value = "/{id}",method= RequestMethod.GET, produces="application/json")
    public @ResponseBody ErrorCode showErrorCode(@PathVariable("id") Integer id) {

        // Do null check for id
        ErrorCode user = repo.findById(id);
        return user;
    }

    @RequestMapping(method= RequestMethod.GET, produces="application/json")
    public @ResponseBody
    List<ErrorCode> showErrorCodeList() {

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
            repo.save(errCode);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return errCode;
    }

    @CrossOrigin(origins = CORS_HOST)
    @RequestMapping(method = RequestMethod.PUT, consumes = "text/plain", produces="application/json")
    public @ResponseBody ErrorCode putErrorCode(@RequestBody String corsRequest) {
        ErrorCode errCode = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            errCode = mapper.readValue(corsRequest, ErrorCode.class);
            repo.save(errCode);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return errCode;
    }

}
