package com.tim.one.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.tim.one.bean.ClosedReasonType;
import com.tim.one.service.CatalogService;

/**
 * @author josdem
 * @understands A class who knows how get catalogs
 * 
 */

@Service
public class CatalogServiceImpl implements CatalogService {
	
	public List<String> getClosedReason() {
	  List<String> reasons = new ArrayList<String>();
	  for (ClosedReasonType type : ClosedReasonType.values()) {
	    reasons.add(type.getName());
	  }
		return reasons;
	}

}