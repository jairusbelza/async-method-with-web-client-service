package com.belza.asyncmethodservice;

import com.belza.asyncmethodservice.model.User;
import com.belza.asyncmethodservice.services.GenerateUUIDService;
import com.belza.asyncmethodservice.services.GithubLookupService;
import com.belza.asyncmethodservice.services.LoggingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public class AppRunner implements CommandLineRunner {
    @Autowired
    private LoggingService loggingService;

    @Autowired
    private GithubLookupService githubLookupService;

    @Autowired
    private GenerateUUIDService generateUUIDService;

    @Override
    public void run(String... args) throws Exception {
        // Start the clock
        long start = System.currentTimeMillis();

        // Kick of multiple, asynchronous lookups
        CompletableFuture<User> page1 = githubLookupService.findUser("jairusbelza");
        CompletableFuture<User> page2 = githubLookupService.findUser("CloudFoundry");
        CompletableFuture<User> page3 = githubLookupService.findUser("Spring-Projects");

        // Wait until they are all done
        CompletableFuture.allOf(page1, page2, page3).join();

        String uuid = generateUUIDService.generateUUID();

        // Print results, including elapsed time
        loggingService.log(uuid, this.getClass().getName(), "Elapsed time: " + (System.currentTimeMillis() - start));
        loggingService.log(uuid, this.getClass().getName(), "--> " + page1.get());
        loggingService.log(uuid, this.getClass().getName(), "--> " + page2.get());
        loggingService.log(uuid, this.getClass().getName(), "--> " + page3.get());
    }
}