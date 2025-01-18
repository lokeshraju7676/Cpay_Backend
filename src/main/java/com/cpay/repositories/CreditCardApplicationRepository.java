package com.cpay.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.cpay.entities.CreditCardApplication;

@Repository
public interface CreditCardApplicationRepository extends CrudRepository<CreditCardApplication, Long> {

	Iterable<CreditCardApplication> findByUsername(String username);
}
