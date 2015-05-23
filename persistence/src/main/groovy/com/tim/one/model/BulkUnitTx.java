package com.tim.one.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author josdem
 * @understands A class who groups all unit transactions to the entity model
 * 
 */

@Entity
@Table(name="BULK_UNIT_TX")
public class BulkUnitTx {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@OneToMany(mappedBy="bulk", cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
	private Set<UnitTx> units;
	
	private Long timestamp;
	
	@Transient
	private String projectName;
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Set<UnitTx> getUnits() {
		return units;
	}
	
	public void setUnits(Set<UnitTx> units) {
		this.units = units;
	}
	
	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}
	
	public String getProjectName() {
		return projectName;
	}
	
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

}
