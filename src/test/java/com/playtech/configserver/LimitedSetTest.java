package com.playtech.configserver;

import com.playtech.configserver.domain.ApiOperation;
import com.playtech.configserver.domain.ErrorCode;
import com.playtech.configserver.domain.LimitedSet;
import com.playtech.configserver.repositories.ErrorCodeRepository;
import com.playtech.configserver.repositories.LimitedSetRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Created by aleksandr on 24/07/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class LimitedSetTest {
    @Autowired
    private LimitedSetRepository repository;


    @Test
    public void testLimitedSetRepo(){
        ApiOperation op1 = new ApiOperation(1, "Chat", "GetMessages");
        ApiOperation op2 = new ApiOperation(2, "Chat", "SendMessages");
        ApiOperation op3 = new ApiOperation(3, "Wallet", "Withdraw");
        ApiOperation op4 = new ApiOperation(4,"Wallet", "GetTransactionHistory");
        ApiOperation op5 = new ApiOperation(5, "Login", "Login");
        ApiOperation op6 = new ApiOperation(6, "Login", "Logout");

        List<ApiOperation> opList1 = Arrays.asList(op1, op2, op3);
        List<ApiOperation> opList2 = Arrays.asList(op3, op4, op5);
        List<ApiOperation> opList3 = Arrays.asList(op1, op4, op6);

        LimitedSet set = new LimitedSet("12", LocalDateTime.now(), "Coral", opList1);
        LimitedSet set2 = new LimitedSet("2", LocalDateTime.now(), "Coral", opList2);
        LimitedSet set3 = new LimitedSet("3", LocalDateTime.now(), "LadBrokes",opList3);

        repository.save(set);
        repository.save(set2);
        repository.save(set3);

        Iterable<LimitedSet> sets = repository.findAll();
        List<LimitedSet> target = new ArrayList<>();
        repository.findAll().forEach(target::add);
        assertTrue(target.get(0).equals(set));
        assertTrue(target.get(1).equals(set2));
    }
}

