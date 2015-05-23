package com.tim.one.common.packer;

import java.math.BigDecimal;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tim.one.bean.MessageType;
import com.tim.one.bean.mail.AbonoCuentaBean;
import com.tim.one.bean.mail.CreacionCuentaBean;
import com.tim.one.common.dispatcher.CommonMessageDispatcher;
import com.tim.one.common.helper.CommonMessagePackerHelper;
import com.tim.one.common.state.ApplicationState;
import com.tim.one.common.util.CurrencyUtil;
import com.tim.one.common.util.DateUtil;

@Service
public class CommonMessagePacker {

	@Autowired
	private DateUtil dateUtil;
	@Autowired
	private CommonMessageDispatcher messageDispatcher;
	@Autowired
	private CommonMessagePackerHelper messagePackerHelper;
	@Autowired
	private CurrencyUtil currencyUtil;
	@Autowired
@Qualifier("properties")
	private Properties properties;
	
	public void sendCreacionCuenta(String account, String bank, String email) {
		CreacionCuentaBean bean = messagePackerHelper.createCreacionCuentaBean();
		bean.setAccount(account);
		bean.setBankName(bank);
		bean.setDate(dateUtil.createDate().toString());
		bean.setEmail(email);
		
		messageDispatcher.message(bean);
	}
	
	public void sendCashout(BigDecimal amount, String bankName, String email, Integer id) {
		AbonoCuentaBean bean = messagePackerHelper.createAbonoCuentaBean();
		bean.setAmount(currencyUtil.format(amount));
		bean.setName(bankName);
		bean.setDate(dateUtil.createDate().toString());
		bean.setId(id.toString());
		bean.setEmail(email);
		bean.setType(MessageType.CASHOUT);

		messageDispatcher.message(bean);
	}
	
	public void sendBridgeToTrama(BigDecimal amount, Integer id) {
		AbonoCuentaBean bean = messagePackerHelper.createAbonoCuentaBean();
		bean.setAmount(currencyUtil.format(amount));
		bean.setName(properties.getProperty(ApplicationState.BRIDGE_TO_TRAMA_CONCEPT));
		bean.setDate(dateUtil.createDate().toString());
		bean.setId(id.toString());
		bean.setEmail(properties.getProperty(ApplicationState.ADMINISTRATOR_EMAIL));
		bean.setType(MessageType.BRIDGE_TRAMA);

		messageDispatcher.message(bean);
	}
	
}
