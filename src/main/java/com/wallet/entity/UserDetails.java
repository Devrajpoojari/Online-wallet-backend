package com.wallet.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long userId;

	private long accountNumber;

	@NotNull(message = "First Name shouldn't be Null")
	@NotBlank(message = "First Name shouldn't be Blank")
	private String firstName;

	@NotNull(message = "Last Name shouldn't be Null")
	@NotBlank(message = "Last Name shouldn't be Blank")
	private String lastName;

	@Pattern(regexp = "^\\d{10}$",message = "Phone Number Must and Shoudl be 10")
	private String contactNumber;

	@Email(message = "Enter Valid Emial address")
	private String emailId;

	@Size(min = 6, max = 12, message = "Password length should be min : 6 and max : 12")
	@Pattern(regexp = "^[a-zA-Z0-9]*$", message = "Password should be Alphanumeric")
	private String password;

	@Min(value = 0, message = "The value must be positive")
	private float balence;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "transaction_id")
	private List<TransactionDetails> transationDetils;

}
