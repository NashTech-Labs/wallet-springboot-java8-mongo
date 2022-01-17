package com.fabhotel.assignment.virtualwallet.repository;

import com.fabhotel.assignment.virtualwallet.models.Wallet;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletRepository extends MongoRepository<Wallet, String> {

}
