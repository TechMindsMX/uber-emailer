package com.tim.one.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.tim.one.bean.TransactionType;

/**
 * @author josdem
 * @understands A class who mapped STP change status request
 * 
 */

@Entity
@Table(name="STP_LOG_TX")
public class StpLogTx {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private Integer speiId;
	private Integer claveRastreo;
	private Integer estado;
	private Long timestamp;
	private TransactionType type;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getSpeiId() {
		return speiId;
	}

	public void setSpeiId(Integer speiId) {
		this.speiId = speiId;
	}
	
	public Integer getClaveRastreo() {
		return claveRastreo;
	}

	public void setClaveRastreo(Integer claveRastreo) {
		this.claveRastreo = claveRastreo;
	}
	
	public Integer getEstado() {
		return estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}
	
	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}
	
	public TransactionType getType() {
		return type;
	}
	
	public void setType(TransactionType type) {
		this.type = type;
	}

}
