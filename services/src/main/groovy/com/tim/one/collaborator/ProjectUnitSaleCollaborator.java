package com.tim.one.collaborator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.tim.one.exception.FormParamsException;
import com.tim.one.model.ProjectFinancialData;
import com.tim.one.model.ProjectUnitSale;

/**
 * @author josdem
 * @understands A class who knows how to create ProjectUnitSale based in string
 *              delimited by ","
 * 
 */

@Component
public class ProjectUnitSaleCollaborator {
	
	public Set<ProjectUnitSale> createUnitSales(ProjectFinancialData projectFinancialData, String section, String unitSale, String capacity, String codeSection) throws FormParamsException {
		Set<ProjectUnitSale> projectUnitSales = new HashSet<ProjectUnitSale>();
		if (section != null && unitSale != null && capacity != null){
			String[] sections = section.split(",");
			String[] unitSales = unitSale.split(",");
			String[] unities = capacity.split(",");
			String[] codeSections = codeSection.split(",");
			if ((sections.length != unitSales.length) || (sections.length != unities.length)) {
				throw new FormParamsException();
			}
			
			for (int i = 0; i < sections.length; i++) {
				ProjectUnitSale projectUnitSale = new ProjectUnitSale();
				projectUnitSale.setSection(sections[i]);
				projectUnitSale.setUnitSale(new BigDecimal(unitSales[i]));
				projectUnitSale.setUnit(new Integer(unities[i]));
				if (codeSection.length() > 0){
					if ((sections.length != codeSections.length)){
						throw new FormParamsException();
					}
					projectUnitSale.setCodeSection(codeSections[i]);
				}
				projectUnitSale.setProjectFinancialData(projectFinancialData);
				projectUnitSales.add(projectUnitSale);
			}
		}
		return projectUnitSales;
	}

	public List<Integer> getIds(Set<ProjectUnitSale> projectUnitSales) {
		List<Integer> ids = new ArrayList<Integer>();
		for (ProjectUnitSale projectUnitSale : projectUnitSales) {
			ids.add(projectUnitSale.getId());
		}
		return ids;
	}

	public ProjectUnitSale getProjectUnitsale(Integer id, Set<ProjectUnitSale> projectUnitsales) {
		for (ProjectUnitSale projectUnitSale : projectUnitsales) {
			if(projectUnitSale.getId().equals(id)){
				return projectUnitSale;
			}
		}
		return null;
	}

}
