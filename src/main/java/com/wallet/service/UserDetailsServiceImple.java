package com.wallet.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.wallet.entity.TransactionDetails;
import com.wallet.entity.TransactionMode;
import com.wallet.entity.UserDetails;
import com.wallet.exceptions.ResourceNotFoundException;
import com.wallet.repository.TransactionsRepo;
import com.wallet.repository.UserDetetailsRepo;

@Service
public class UserDetailsServiceImple implements UserDetailsService {

	@Autowired
	private UserDetetailsRepo detetailsRepo; // Interface DI

	@Autowired
	private TransactionsRepo transactionsRepo;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	private final static Logger logger = LoggerFactory.getLogger(UserDetailsServiceImple.class);

	@Override
	public UserDetails registerUser(UserDetails details) throws Exception {

		UserDetails obj = detetailsRepo.getUserByEmail(details.getEmailId());
		if (obj != null) {
			logger.error("User Already Present in the databse");
			throw new Exception("User Already exist");
		}

		details.setAccountNumber((long) Math.floor(Math.random() * 9_000_000_000L) + 1_000_000_000L);
		details.setPassword(bCryptPasswordEncoder.encode(details.getPassword()));
		logger.info("Registering the user in the application");
		return detetailsRepo.save(details);
	}

	@Override
	public UserDetails autheticateUser(String userName, String password) throws ResourceNotFoundException {

		UserDetails u = detetailsRepo.getUserByUserName(userName);

		if (u.getFirstName().equalsIgnoreCase(userName) && u.getPassword().equalsIgnoreCase(password)) {
			logger.info("Authentication is successfull");
			return u;
		}
		throw new ResourceNotFoundException("Invalid credentails");

	}

	@Override
	public String resetPasswordByEmail(String email, Long accountNumber, String newpassword)
			throws ResourceNotFoundException {
		UserDetails u = detetailsRepo.getUserDetalsByAccountNumber(accountNumber)
				.orElseThrow(() -> new ResourceNotFoundException("User Details Not Found"));
		if (u.getEmailId().equalsIgnoreCase(email)) {
			u.setPassword(newpassword);
		}
		detetailsRepo.save(u);
		return "Password Updated Successfully";
	}

	@Override
	public UserDetails viewAccountDetailsByEmail(long userId) throws ResourceNotFoundException {
		return detetailsRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User details Not Found "));
	}

	@Override
	public UserDetails withdrawMoney(float amount, Long userId) throws Exception {
		UserDetails u = detetailsRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User Doesn't exists by Id :" + userId));
		if (amount >= 100 && amount <= 100000) {
			TransactionDetails details = new TransactionDetails(u.getAccountNumber(), LocalDateTime.now().toString(),
					"WithDrawing amount of :" + u.getFirstName(), amount, TransactionMode.WITHDRAW);

			List<TransactionDetails> tr = u.getTransationDetils();
			tr.add(details);

			if (u.getBalence() > amount) {
				amount = u.getBalence() - amount;
			} else {
				throw new Exception("insufficient balance");
			}
			u.setBalence(amount);
			u.setTransationDetils(tr);
			detetailsRepo.save(u);
		}
		return u;
	}

	@Override
	public UserDetails depositeMoney(float amount, Long userId) throws Exception {

		UserDetails u = detetailsRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User Doesn't exists by Id :" + userId));
		if (amount >= 100 && amount <= 100000) {
			TransactionDetails details = new TransactionDetails(u.getAccountNumber(), LocalDateTime.now().toString(),
					"Depositing amount to : " + u.getFirstName(), amount, TransactionMode.DEPOSITE);

			List<TransactionDetails> tr = u.getTransationDetils();
			tr.add(details);
			amount = u.getBalence() + amount;
			u.setBalence(amount);
			u.setTransationDetils(tr);
			detetailsRepo.save(u);
		} else {
			throw new Exception("Please depostie between 100 to 100000");
		}
		return u;
	}

	@Override
	public String transferMoney(Long senderAccountNumber, Long recieverAccountNumber, float amount) throws Exception {
		UserDetails u = detetailsRepo.getUserDetalsByAccountNumber(senderAccountNumber)
				.orElseThrow(() -> new ResourceNotFoundException("Sender Account Doesn't exists"));
		UserDetails u2 = detetailsRepo.getUserDetalsByAccountNumber(recieverAccountNumber)
				.orElseThrow(() -> new ResourceNotFoundException("Reciever Account Doesn't exists"));

		if (amount >= 100 && amount <= 100000) {
			TransactionDetails details = new TransactionDetails(u.getAccountNumber(), LocalDateTime.now().toString(),
					"Transfering amount to :" + u2.getFirstName(), amount, TransactionMode.TRANSFER);

			List<TransactionDetails> tr = u.getTransationDetils();
			tr.add(details);
			float amt = 0;
			if (u.getBalence() > amount) {
				amt = u.getBalence() - amount;
			} else {
				throw new Exception("insufficient balance");
			}
			u.setBalence(amt);
			u.setTransationDetils(tr);
			detetailsRepo.save(u);
		}
		if (amount >= 100 && amount <= 100000) {
			TransactionDetails details = new TransactionDetails(u.getAccountNumber(), LocalDateTime.now().toString(),
					"Amount Received from : " + u.getFirstName(), amount, TransactionMode.DEPOSITE);

			List<TransactionDetails> tr = u2.getTransationDetils();
			tr.add(details);
			amount = u2.getBalence() + amount;
			u2.setBalence(amount);
			u2.setTransationDetils(tr);
			detetailsRepo.save(u2);
		} else {
			throw new Exception("Please depostie between 100 to 100000");
		}

		return "Amout Transfered Successfully";
	}

	@Override
	public List<TransactionDetails> getAllTransactionListById(Long id) throws ResourceNotFoundException {
		UserDetails u = detetailsRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User Transactions are not present"));
		return u.getTransationDetils();
	}

	@Override
	public List<TransactionDetails> getTransactionsByType(String type, Long id) throws ResourceNotFoundException {
		UserDetails u = detetailsRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User Transactions are not present"));
		return u.getTransationDetils().stream().filter(a -> a.getType().toString().equalsIgnoreCase(type))
				.collect(Collectors.toList());
	}

}
