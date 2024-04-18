package com.sonnees.quotingservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sonnees.quotingservice.dto.Field;
import com.sonnees.quotingservice.dto.QuotingDTO;
import com.sonnees.quotingservice.dto.QuotingStatusDTO;
import com.sonnees.quotingservice.entity.Quoting;
import com.sonnees.quotingservice.repository.QuotingRepository;
import com.sonnees.quotingservice.service.QuotingService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RequestMapping("/api/v1/quoting")
@Slf4j
@AllArgsConstructor
@Controller
public class QuotingController {
    private ObjectMapper objectMapper;
    private QuotingRepository quotingRepository;
    private QuotingService quotingService;
    WebClient.Builder builder;

    @PostMapping("/create")
    @CrossOrigin("*")
    public Mono<ResponseEntity<String>> create(@RequestBody QuotingDTO info){
        log.info("** {}", "enter create");
        WebClient webClient = builder.build();
        Quoting quoting = new Quoting(info);

        return quotingService.calculateRepurchasePrice(info)
                        .flatMap(aDouble -> {
                            quoting.setPriceBy(aDouble);
                            return quotingRepository.save(quoting)
                                    .switchIfEmpty(Mono.defer(()->{
                                        log.error("** {}", "save failed");
                                        return Mono.just(ResponseEntity.status(500).body("Failed"));
                                    }).then(Mono.empty()))
                                    .flatMap(quoting1 -> {
                                        QuotingStatusDTO dto = new QuotingStatusDTO(quoting1.getId(), quoting1.getIdUser(), quoting1.getPriceBy());
                                        return webClient.post()
                                                .uri("http://localhost:8082/api/v1/status/create")
                                                .bodyValue(dto)
                                                .retrieve()
                                                .bodyToMono(Boolean.class)
                                                .flatMap(isValidToken -> {
                                                    log.info("** {}", isValidToken);
                                                    if(!isValidToken) return Mono.just(ResponseEntity.status(500).body("Create Status Failed"));
                                                    try {
                                                        return Mono.just(ResponseEntity.status(200).body(objectMapper.writeValueAsString(quoting)));
                                                    } catch (Exception e) {
                                                        return Mono.just(ResponseEntity.status(500).body("Error Json"));
                                                    }
                                                });
                                    });
                        });
    }

    @PostMapping("/check-price")
    @CrossOrigin("*")
    public Mono<ResponseEntity<String>> checkPrice(@RequestBody QuotingDTO info){
        log.info("** {}", "enter checkPrice");
        return quotingService.calculateRepurchasePrice(info)
                .flatMap(aDouble -> {
                    log.info("** repurchasePrice: {}",aDouble);
                    Field field = new Field();
                    field.setField(aDouble);
                    try {
                        return Mono.just(ResponseEntity.status(200).body(objectMapper.writeValueAsString(field)));
                    } catch (Exception e) {
                        return Mono.just(ResponseEntity.status(500).body("Error Json"));
                    }
                });
    }
}
