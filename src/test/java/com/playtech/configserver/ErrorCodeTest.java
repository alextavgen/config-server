package com.playtech.configserver;

import com.playtech.configserver.domain.ErrorCode;
import com.playtech.configserver.repositories.ErrorCodeRepository;
import org.junit.Test;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;

/**
 * Created by aleksandr on 17/07/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ErrorCodeTest {
    @Autowired
    private  ErrorCodeRepository repository;


    @Test
    public void testErrorCodes(){
        ErrorCode code = new ErrorCode(0,"ERR_OK", "Ok", "ims.org", 1L);
        ErrorCode code2 = new ErrorCode(7, "ERR_SYSTEM", "System Error", "ims.org", 2L);
        repository.save(code);
        repository.save(code2);

        Iterable<ErrorCode> errorCodes = repository.findAll();
        List<ErrorCode> target = new ArrayList<>();
        repository.findAll().forEach(target::add);
        assertTrue(target.get(0).equals(code));
        assertTrue(target.get(1).equals(code2));
    }
}
