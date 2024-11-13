package com.wallet.controller;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wallet.entity.UserDetails;
import com.wallet.exceptions.ResourceNotFoundException;
import com.wallet.service.UserDetailsService;

@CrossOrigin
@RestController
@RequestMapping("/api")
@Validated
public class UserDetailsController {

	@Autowired
	private UserDetailsService userDetailsService;

	@PostMapping("/register")
	public ResponseEntity<?> register(@Valid @RequestBody UserDetails details) throws Exception {
		return new ResponseEntity<>(userDetailsService.registerUser(details), HttpStatus.CREATED);
	}

	@GetMapping("/login/{userName}/{password}")
	public ResponseEntity<?> login(@PathVariable String userName,
			@PathVariable @Pattern(regexp = "^[a-zA-Z0-9]*$",message = "Password should be alhanumeric") String password) throws ResourceNotFoundException {
		return new ResponseEntity<>(userDetailsService.autheticateUser(userName, password), HttpStatus.ACCEPTED);
	}

	@GetMapping("/reset/{email}/{accountNumber}/{password}")
	public ResponseEntity<?> resetPasswordByEmail(@PathVariable String email, @PathVariable Long accountNumber,
			@PathVariable String password) throws ResourceNotFoundException {
		return new ResponseEntity<>(userDetailsService.resetPasswordByEmail(email, accountNumber, password),
				HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> viewAccountDetailsByUserId(@PathVariable long id) throws ResourceNotFoundException {
		return new ResponseEntity<>(userDetailsService.viewAccountDetailsByEmail(id), HttpStatus.OK);
	}

	@GetMapping("/deposite/{amount}/{userId}")
	public ResponseEntity<?> depositeMoney(@PathVariable float amount, @PathVariable Long userId)
			throws ResourceNotFoundException, Exception {
		return new ResponseEntity<>(userDetailsService.depositeMoney(amount, userId), HttpStatus.OK);

	}

	@GetMapping("/withdraw/{amount}/{userId}")
	public ResponseEntity<?> withdrawMoney(@PathVariable float amount, @PathVariable Long userId)
			throws ResourceNotFoundException, Exception {
		return new ResponseEntity<>(userDetailsService.withdrawMoney(amount, userId), HttpStatus.OK);
	}

	@GetMapping("/transfer/{senderAccountNumber}/{recieverAccountNumber}/{amount}")
	public ResponseEntity<?> transferMoney(@PathVariable Long senderAccountNumber,
			@PathVariable Long recieverAccountNumber, @PathVariable float amount)
			throws ResourceNotFoundException, Exception {
		return new ResponseEntity<>(
				userDetailsService.transferMoney(senderAccountNumber, recieverAccountNumber, amount), HttpStatus.OK);
	}

	@GetMapping("/all/{id}")
	public ResponseEntity<?> getAll(@PathVariable Long id) throws ResourceNotFoundException {
		return ResponseEntity.ok(userDetailsService.getAllTransactionListById(id));
	}

}
