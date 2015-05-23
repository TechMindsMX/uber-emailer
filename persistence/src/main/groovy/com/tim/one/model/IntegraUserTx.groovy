package com.tim.one.model

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

import com.tim.one.bean.IntegraTransactionType;

/**
 * @author josdem
 * @understands A class who mapped Integra users transactions
 * 
 */

@Entity
@Table(name="INTEGRA_USER_TX")
class IntegraUserTx implements PersonaTx{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id
	String origin
	String destination
	String uuid
	String reference
	Long timestamp
	IntegraTransactionType type
	
	@Column(columnDefinition="Decimal(38,2) default '0.00'")
	BigDecimal amount

}
