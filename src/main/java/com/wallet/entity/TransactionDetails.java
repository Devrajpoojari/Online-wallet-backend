package com.wallet.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class TransactionDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long tId;
	
	private String tDateAndTime;
	
	private String descriptions;
	
	private float amount;
	
	@Enumerated(EnumType.STRING)
	private TransactionMode type;
	
}
