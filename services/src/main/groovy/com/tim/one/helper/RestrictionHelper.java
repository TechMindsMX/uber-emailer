package com.tim.one.helper;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

@Component
public class RestrictionHelper {

	public Disjunction createDisjunction() {
		return Restrictions.disjunction();
	}

	public Criterion createRestriction(String string, String keyword) {
		return Restrictions.like(string, "%" + keyword + "%");
	}
	
}
