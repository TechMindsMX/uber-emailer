package com.tim.one.model

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

/**
 * @author josdem
 * @understands A class who mapped Integra users database values to the entity model
 * 
 */

@Entity
@Table(name="INTEGRA_USER")
class IntegraUser implements Persona {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id
	String integraUuid
	String timoneUuid
	String name
	String email
	String stpClabe
	
	@Column(columnDefinition="Decimal(38,2) default '0.00'")
	BigDecimal balance
	
	IntegraUser(){
		balance = new BigDecimal(0.00)
	}
	
}
