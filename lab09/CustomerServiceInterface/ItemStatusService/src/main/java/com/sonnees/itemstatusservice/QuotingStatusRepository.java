package com.sonnees.itemstatusservice;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.ui.Model;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface QuotingStatusRepository extends ReactiveMongoRepository<QuotingStatus, UUID> {
    @Query("{idUser:?0}")
    Flux<QuotingStatus> checkStatusByIDUser(String idUser);

    @Query(value = "{_id: ?0}")
    @Update(update = "{$set: {status: ?1}}")
    Mono<Long> changeStatus(UUID id, Status status);

    @Query("{status:'SUCCESS'}")
    Flux<QuotingStatus> searchByStatus();
}
