package com.sonnees.itemstatusservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@RequestMapping("/api/v1/status")
@Slf4j
@AllArgsConstructor
@Controller
public class QuotingController {
    private ObjectMapper objectMapper;
    private QuotingStatusRepository quotingStatusRepository;

    @PostMapping("/create")
    @CrossOrigin("*")
    public Mono<ResponseEntity<Boolean>> create(@RequestBody QuotingStatusDTO info) {
        log.info("** {}", "enter status create");
        return quotingStatusRepository.save(new QuotingStatus(info))
                .flatMap(savedStatus -> {
                    log.info("** {}", "status saved successfully");
                    return Mono.just(ResponseEntity.ok(true));
                })
                .switchIfEmpty(Mono.defer(() -> {
                    log.error("** {}", "save failed");
                    return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false));
                }))
                .onErrorResume(throwable -> {
                    log.error("** {}", "error occurred during save", throwable);
                    return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false));
                });
    }

    @GetMapping("/check-status")
    @CrossOrigin("*")
    public Mono<ResponseEntity<String>> checkStatus(@RequestParam String idUser) {
        log.info("** {}", "enter checkStatus");
        return quotingStatusRepository.checkStatusByIDUser(idUser)
                .collectList()
                .flatMap(quotingStatuses -> {
                    try {
                        return Mono.just(ResponseEntity.ok(objectMapper.writeValueAsString(quotingStatuses)));
                    } catch (Exception e) {
                        return Mono.just(ResponseEntity.status(500).body("Error Json"));
                    }
                });
    }
}
