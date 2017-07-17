package com.playtech.configserver.controller;

import com.playtech.configserver.domain.ErrorCode;
import com.playtech.configserver.repositories.ErrorCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by aleksandr on 17/07/2017.
 */
@Controller
@RequestMapping("/error_code")
public class ErrorCodeController {

    private final ErrorCodeRepository repo;

    @Autowired
    public ErrorCodeController(ErrorCodeRepository repo) {
        Assert.notNull(repo, "Repository must not be null!");
        this.repo = repo;
    }

    @RequestMapping(value = "/{id}",method= RequestMethod.GET, produces="application/json")
    public @ResponseBody ErrorCode showUserForm(@PathVariable("id") Integer id) {

        // Do null check for id
        ErrorCode user = repo.findByEId(id);
        return user;
    }
}
