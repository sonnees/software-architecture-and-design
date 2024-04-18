package com.sonnees.itemstatusservice;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

import java.util.UUID;

public interface QuotingStatusRepository extends ReactiveMongoRepository<QuotingStatus, UUID> {
    @Query("{idUser:?0}")
    Flux<QuotingStatus> checkStatusByIDUser(String idUser);
}
