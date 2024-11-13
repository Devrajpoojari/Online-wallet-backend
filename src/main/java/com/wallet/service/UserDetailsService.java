package com.wallet.service;

import java.util.List;

import com.wallet.entity.TransactionDetails;
import com.wallet.entity.UserDetails;
import com.wallet.exceptions.ResourceNotFoundException;

public interface UserDetailsService {

	UserDetails registerUser(UserDetails details) throws Exception;

	UserDetails autheticateUser(String userName, String password) throws ResourceNotFoundException;

	String resetPasswordByEmail(String email, Long accountNumber, String newpassword) throws ResourceNotFoundException;

	UserDetails viewAccountDetailsByEmail(long userId) throws ResourceNotFoundException;

	UserDetails withdrawMoney(float amount, Long userId) throws ResourceNotFoundException, Exception;

	UserDetails depositeMoney(float amount, Long userId) throws ResourceNotFoundException, Exception;

	String transferMoney(Long senderAccountNumber, Long recieverAccountNumber, float amount)
			throws ResourceNotFoundException, Exception;
	
	List<TransactionDetails> getAllTransactionListById(Long id) throws ResourceNotFoundException;
	
	List<TransactionDetails> getTransactionsByType(String type, Long id) throws ResourceNotFoundException;

}
