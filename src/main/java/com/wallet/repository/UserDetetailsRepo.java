package com.wallet.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.wallet.entity.UserDetails;

@Repository
public interface UserDetetailsRepo extends JpaRepository<UserDetails, Long> {

	Optional<UserDetails> getUserDetalsByAccountNumber(Long accountNumber);
	
	@Query(value = "Select u from UserDetails u where u.emailId=?1")
	UserDetails getUserByEmail(String emailId);
	
	@Query("select u from UserDetails u where u.firstName=:firstName")
	UserDetails getUserByUserName(String firstName);
	
	

}
