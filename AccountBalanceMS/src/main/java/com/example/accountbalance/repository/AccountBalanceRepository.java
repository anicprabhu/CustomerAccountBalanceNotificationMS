package com.example.accountbalance.repository;

import org.springframework.data.couchbase.repository.CouchbaseRepository;

import com.example.accountbalance.model.AccountBalanceModel;

public interface AccountBalanceRepository extends CouchbaseRepository<AccountBalanceModel, String>{

}
