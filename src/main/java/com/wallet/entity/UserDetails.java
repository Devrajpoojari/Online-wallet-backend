package com.wallet.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long userId;
	
	private long accountNumber;
	
	private String firstName;
	
	private String lastName;
	
	private long contactNumber;
	
	@Email
	private String emailId;
	
	@Size(min = 6,max = 12,message = "Password length should be min = 6 and max = 12")
	@Pattern(regexp = "^[a-zA-Z0-9]*$",message = "Password should be Alphanumeric")
	private String password;
	
	@Min(value = 0, message = "The value must be positive")
	private float balence;
	
	@OneToMany(cascade = CascadeType.MERGE)
	@JoinColumn(name="transaction_id")
	private List<TransactionDetails> transationDetils;

}
