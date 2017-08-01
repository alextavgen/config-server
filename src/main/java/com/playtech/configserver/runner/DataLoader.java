package com.playtech.configserver.runner;

import com.playtech.configserver.domain.ApiOperation;
import com.playtech.configserver.domain.LimitedSet;
import com.playtech.configserver.repositories.LimitedSetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * Created by aleksandr on 24/07/2017.
 */
@Component
public class DataLoader implements ApplicationRunner {

    @Autowired
    LimitedSetRepository repository;

    public void init() throws InterruptedException {
        ApiOperation op1 = new ApiOperation("1","Chat", "GetMessages");
        ApiOperation op2 = new ApiOperation("2","Chat", "SendMessages");
        ApiOperation op3 = new ApiOperation("3","Wallet", "Withdraw");
        ApiOperation op4 = new ApiOperation("4","Wallet", "GetTransactionHistory");
        ApiOperation op5 = new ApiOperation("5", "Login", "Login");
        ApiOperation op6 = new ApiOperation("6", "Login", "Logout");

        List<ApiOperation> opList1 = Arrays.asList(op1, op2, op3);
        List<ApiOperation> opList2 = Arrays.asList(op3, op4, op5);
        List<ApiOperation> opList3 = Arrays.asList(op1, op4, op6);

        LimitedSet set = new LimitedSet("12", LocalDateTime.now().withNano(0), "Coral", opList1);
        Thread.sleep(1000);

        LimitedSet set2 = new LimitedSet("2", LocalDateTime.now().withNano(0), "Coral",  opList2);
        LimitedSet set3 = new LimitedSet("3", LocalDateTime.now().withNano(0), "LadBrokes", opList3);

        repository.save(set);
        repository.save(set2);
        repository.save(set3);
    }

    public void run(ApplicationArguments args) {
        /*try {
            init();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
    }
}