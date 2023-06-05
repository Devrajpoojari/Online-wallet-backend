package com.wallet.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wallet.entity.UserDetails;

@Repository
public interface UserDetetailsRepo extends JpaRepository<UserDetails, Long> {

	Optional<UserDetails> getUserDetalsByAccountNumber(Long accountNumber);
	

}
