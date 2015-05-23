package com.tim.one.common.helper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.tim.one.bean.TransactionBean;
import com.tim.one.bean.TransferUserLimitBean;
import com.tim.one.model.BulkUnitTx;
import com.tim.one.model.ProjectTx;
import com.tim.one.model.ProviderTx;
import com.tim.one.model.StpLogTx;
import com.tim.one.model.TramaTx;
import com.tim.one.model.TransferUserLimit;
import com.tim.one.model.UnitTx;
import com.tim.one.model.UserTx;

@Component
public class TransactionHelper {

	public UserTx createUserTx() {
		return new UserTx();
	}

	public TramaTx createTramaTx() {
		return new TramaTx();
	}

	public UnitTx createUnitTx() {
		return new UnitTx();
	}

	public List<UnitTx> createUnits() {
		return new ArrayList<UnitTx>();
	}

	public BulkUnitTx createBulkUnit() {
		return new BulkUnitTx();
	}

	public ProviderTx createProviderTx() {
		return new ProviderTx();
	}

	public ProjectTx createProjectTx() {
		return new ProjectTx();
	}

	public TransferUserLimit createTransferUserLimit() {
		return new TransferUserLimit();
	}

	public TransferUserLimitBean createTransferUserLimitBean() {
		return new TransferUserLimitBean();
	}

	public TransactionBean createTransactionBean() {
		return new TransactionBean();
	}

	public StpLogTx createStpLogTx() {
		return new StpLogTx();
	}

}
