package com.fabhotel.assignment.virtualwallet.repository;

import com.fabhotel.assignment.virtualwallet.models.WalletTransaction;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WalletTransactionRepository extends MongoRepository<WalletTransaction, String> {

    @Query(value = "{'walletId': ?0}, sort = {timestamp : 1}")
    Optional<List<WalletTransaction>> findWalletTransactionsByCustomerId(String walletId);
}
