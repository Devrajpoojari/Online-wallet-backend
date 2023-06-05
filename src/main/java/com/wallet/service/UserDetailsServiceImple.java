package com.wallet.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
	private UserDetetailsRepo detetailsRepo;

	@Autowired
	private TransactionsRepo transactionsRepo;

	@Override
	public UserDetails registerUser(UserDetails details) {

		details.setAccountNumber((long) Math.floor(Math.random() * 9_000_000_000L) + 1_000_000_000L);
		System.out.println(details);
		return detetailsRepo.save(details);
	}

	@Override
	public UserDetails autheticateUser(String userName, String password) throws ResourceNotFoundException {

		List<UserDetails> list = detetailsRepo.findAll();
		for (UserDetails u : list) {
			if (u.getEmailId().equalsIgnoreCase(userName) && u.getPassword().equalsIgnoreCase(password)) {
				return u;
			}
		}
		throw new ResourceNotFoundException("Invalid credentails");

	}

	@Override
	public String resetPasswordByEmail(String email, Long accountNumber, String newpassword)
			throws ResourceNotFoundException {
		UserDetails u = detetailsRepo.getUserDetalsByAccountNumber(accountNumber)
				.orElseThrow(() -> new ResourceNotFoundException("User Details Not Found "));
		if (u.getEmailId().equalsIgnoreCase(email)) {
			u.setPassword(newpassword);
			detetailsRepo.delete(u);

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
		if (amount > 100 && amount < 100000) {
			TransactionDetails details = new TransactionDetails(
					(long) Math.floor(Math.random() * 9_000_000_000L) + 1_000_000_000L, LocalDateTime.now().toString(),
					"WithDrawing amount of :" + userId, amount, TransactionMode.WITHDRAW);

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
		if (amount > 100 && amount < 100000) {
			TransactionDetails details = new TransactionDetails(
					(long) Math.floor(Math.random() * 9_000_000_000L) + 1_000_000_000L, LocalDateTime.now().toString(),
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

//		if (amount <= 100000 && amount >= 100 ) {
//			if (u1.getBalence() > amount) {
//				u2.setBalence(amount + u2.getBalence());
//				u1.setBalence(u1.getBalence() - amount);
//
//				TransactionDetails details1 = new TransactionDetails(
//						(long) Math.floor(Math.random() * 9_000_000_000L) + 1_000_000_000L,
//						LocalDateTime.now().toString(),
//						"Transfering amount from:" + senderAccountNumber + " to " + recieverAccountNumber, amount,
//						TransactionMode.TRANSFER);
//
//				TransactionDetails details2 = new TransactionDetails(
//						(long) Math.floor(Math.random() * 9_000_000_000L) + 1_000_000_000L,
//						LocalDateTime.now().toString(), "Recieved amount from:" + senderAccountNumber, amount,
//						TransactionMode.TRANSFER);
//				
//				List<TransactionDetails> list1 = u1.getTransationDetils();
//				list1.add(details1);
//				u1.setTransationDetils(list1);
//
//				List<TransactionDetails> lis2 = u2.getTransationDetils();
//				lis2.add(details2);
//				u2.setTransationDetils(lis2);
//				detetailsRepo.save(u1);
//				detetailsRepo.save(u2);
//
//			} else {
//				throw new Exception("insufficient balance In Sender Account");
//			}
//		}

		if (amount > 100 && amount < 100000) {
			TransactionDetails details = new TransactionDetails(
					(long) Math.floor(Math.random() * 9_000_000_000L) + 1_000_000_000L, LocalDateTime.now().toString(),
					"Transfering amount to :" + u2.getAccountNumber(), amount, TransactionMode.TRANSFER);

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
		if (amount > 100 && amount < 100000) {
			TransactionDetails details = new TransactionDetails(
					(long) Math.floor(Math.random() * 9_000_000_000L) + 1_000_000_000L, LocalDateTime.now().toString(),
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
}
