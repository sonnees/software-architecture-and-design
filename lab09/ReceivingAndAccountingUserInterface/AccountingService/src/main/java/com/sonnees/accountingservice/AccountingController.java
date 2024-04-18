package com.sonnees.accountingservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RequestMapping("/api/v1/accounting")
@Slf4j
@AllArgsConstructor
@Controller
public class AccountingController {
    private ObjectMapper objectMapper;
    private AccountingRepository repository;
    WebClient.Builder builder;

    @PostMapping("/create")
    @CrossOrigin("*")
    public Mono<ResponseEntity<String>> create(@RequestBody AccountingDTO info){
        log.info("** {}", "enter create");
        WebClient webClient = builder.build();

        return repository.save(new Accounting(info))
                .switchIfEmpty(Mono.defer(()->{
                    return Mono.just(ResponseEntity.status(500).body("Failed save"));
                }).then(Mono.empty()))
                .flatMap(receiving -> {
                    return webClient.get()
                            .uri("http://localhost:8082/api/v1/status/change-status?id="+info.getId()+"&status="+Status.PAYED)
                            .retrieve()
                            .bodyToMono(Boolean.class)
                            .flatMap(isValidToken -> {
                                return Mono.just(ResponseEntity.ok("Success"));
                            });
                });
    }
}
