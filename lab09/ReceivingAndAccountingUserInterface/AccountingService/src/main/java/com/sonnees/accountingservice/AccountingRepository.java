package com.sonnees.accountingservice;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AccountingRepository extends ReactiveMongoRepository<Accounting, UUID> {
}
