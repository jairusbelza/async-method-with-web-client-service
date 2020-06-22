package com.belza.asyncmethodservice.services;

import com.belza.asyncmethodservice.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.concurrent.CompletableFuture;

@Service
public class GithubLookupService {

    @Autowired
    private LoggingService loggingService;

    @Autowired
    private GenerateUUIDService generateUUIDService;

    @Autowired
    private WebClient webClient;

    @Value("${github.users.endpoint}")
    private String githubUsersEndpoint;

    @Async
    public CompletableFuture<User> findUser(String userToFind) {
        String uuid = generateUUIDService.generateUUID();
        loggingService.log(uuid, this.getClass().getName(), String.format("Looking up %s", userToFind));
        User result = webClient.get()
                .uri(String.format(githubUsersEndpoint, userToFind))
                .retrieve()
                .bodyToMono(User.class)
                .block();
        return CompletableFuture.completedFuture(result);
    }
}