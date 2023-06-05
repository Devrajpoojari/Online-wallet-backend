package com.wallet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wallet.entity.TransactionDetails;

@Repository
public interface TransactionsRepo extends JpaRepository<TransactionDetails, Long> {

}
