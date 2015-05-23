package com.tim.one.packer;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.tim.one.bean.MessageType;
import com.tim.one.bean.mail.AbonoCuentaBean;
import com.tim.one.bean.mail.CreacionCuentaBean;
import com.tim.one.helper.CommonMessagePackerHelper;
import com.tim.one.integration.MessageService;
import com.tim.one.state.ApplicationState;
import com.tim.one.util.CurrencyUtil;
import com.tim.one.util.DateUtil;

public class TestCommonMessagePacker {
	
	@InjectMocks
	private CommonMessagePacker commonMessagePacker = new CommonMessagePacker();
	
	@Mock
	private MessageService messageDispatcher;
	@Mock
	private CommonMessagePackerHelper commonMessagePackerHelper;
	@Mock
	private CreacionCuentaBean bean;
	@Mock
	private DateUtil dateUtil;
	@Mock
	private Date date;
	@Mock
	private AbonoCuentaBean abonoCuentaBean;
	@Mock
	private CurrencyUtil currencyUtil;
	@Mock
	private Properties properties;
	
	private Integer id = 1;

	private String account = "account";
	private String bank = "bank";
	private String email = "email";
	private String bankName = "bankName";
	private String brigeToTramaConcept = "brigeToTramaConcept";
	private String administratorEmail = "administratorEmail";
	
	private BigDecimal amount = new BigDecimal("100");

	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void shouldSendCreacionCuenta() throws Exception {
		when(commonMessagePackerHelper.createCreacionCuentaBean()).thenReturn(bean);
		when(dateUtil.createDate()).thenReturn(date);
		
		commonMessagePacker.sendCreacionCuenta(account, bank, email);
		
		verify(bean).setAccount(account);
		verify(bean).setBankName(bank);
		verify(bean).setDate(dateUtil.createDate().toString());
		verify(bean).setEmail(email);
		verify(bean).setType(MessageType.CREACION_CUENTA);
		
		verify(messageDispatcher).message(bean);
	}
	
	@Test
	public void shouldSendCashout() throws Exception {
		when(commonMessagePackerHelper.createAbonoCuentaBean()).thenReturn(abonoCuentaBean);
		when(currencyUtil.format(amount)).thenReturn(amount.toString());
		when(dateUtil.createDate()).thenReturn(date);
		
		commonMessagePacker.sendCashout(amount, bankName, email, id);
		
		verify(abonoCuentaBean).setAmount(amount.toString());
		verify(abonoCuentaBean).setName(bankName);
		verify(abonoCuentaBean).setDate(date.toString());
		verify(abonoCuentaBean).setId(id.toString());
		verify(abonoCuentaBean).setEmail(email);
		verify(abonoCuentaBean).setType(MessageType.CASHOUT);
		
		verify(messageDispatcher).message(abonoCuentaBean);
	}
	
	@Test
	public void shouldSendBridgeToTrama() throws Exception {
		when(properties.getProperty(ApplicationState.BRIDGE_TO_TRAMA_CONCEPT)).thenReturn(brigeToTramaConcept);
		when(properties.getProperty(ApplicationState.ADMINISTRATOR_EMAIL)).thenReturn(administratorEmail);
		
		when(commonMessagePackerHelper.createAbonoCuentaBean()).thenReturn(abonoCuentaBean);
		when(currencyUtil.format(amount)).thenReturn(amount.toString());
		when(dateUtil.createDate()).thenReturn(date);
		
		commonMessagePacker.sendBridgeToTrama(amount, id);
		
		verify(abonoCuentaBean).setAmount(amount.toString());
		verify(abonoCuentaBean).setName(brigeToTramaConcept);
		verify(abonoCuentaBean).setDate(date.toString());
		verify(abonoCuentaBean).setId(id.toString());
		verify(abonoCuentaBean).setEmail(administratorEmail);
		verify(abonoCuentaBean).setType(MessageType.BRIDGE_TRAMA);
		
		verify(messageDispatcher).message(abonoCuentaBean);
	}

}
